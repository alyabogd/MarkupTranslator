package com.company;

import com.company.containers.Blockquote;
import com.company.containers.MarkupList;
import com.company.containers.Paragraph;
import com.company.containers.TokensContainer;
import com.company.tokens.Image;
import com.company.tokens.links.Link;
import com.company.tokens.links.LinkSpecification;
import com.company.tokens.ListElement;
import com.company.tokens.Phrase;
import com.company.tokens.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownReader {

    private static final Logger LOGGER = Logger.getLogger(MarkdownReader.class.getName());

    private static final Pattern HEADINGS_PATTERN = Pattern.compile("^(\\s*#{1,6})\\s*\\w+");
    private static final Pattern INLINE_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\(([^\\)]*)\\)");
    private static final Pattern REFERENCED_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\[([^\\)]*)\\]");
    private static final Pattern INLINE_IMAGE_PATTERN = Pattern.compile("!\\[([^\\]]*)\\]\\(([^\\)]*)\\)");
    private static final Pattern REFERENCED_IMAGE_PATTERN = Pattern.compile("!\\[([^\\]]*)\\]\\[([^\\)]*)\\]");
    private static final Pattern LINK_SPECIFICATION_PATTERN = Pattern.compile("\\s*\\[([^\\]]+)\\]:\\s?(.+)");
    private static final Pattern NON_ORDERED_LIST_ELEMENT_PATTERN = Pattern.compile("\\s*\\*\\s+([^\\*]+)");
    private static final Pattern ORDERED_LIST_ELEMENT_PATTERN = Pattern.compile("\\s*\\d{1,3}\\.\\s+([^\\*]+)");
    private static final Pattern BLACKQUOTE_PATTERN = Pattern.compile("\\s*>\\s*(.*)");
    private static final Pattern HEADER_ONE_PATTERN = Pattern.compile("={3,100}\\s*");
    private static final Pattern HEADER_TWO_PATTERN = Pattern.compile("-{3,100}\\s*");
    private static final Pattern MONOCPACE_PATTERN = Pattern.compile("`{3,100}\\s*");

    private final MarkdownTextParser markdownTextParser = new MarkdownTextParser();

    private BufferedReader reader;
    private String fileName;
    private boolean monospaceGlobal = false;

    public MarkdownReader(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(fileName));
        this.fileName = fileName.substring(0, fileName.length() - 3);
    }

    public MarkdownReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
        this.fileName = file.getName().substring(0, file.getName().length() - 3);
    }

    public Dom makeDom() {
        Dom dom = new Dom(fileName);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                processString(dom, line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return dom;
    }

    private void processString(Dom dom, String line) {
        boolean appliedPattern = tryApplyPatterns(dom, line);
        if (appliedPattern) {
            return;
        }

        final TokensContainer tc = traverseString(line);
        if (!tc.getTokens().isEmpty() && tc.getTokens().get(0) instanceof LinkSpecification) {
            final LinkSpecification ls = (LinkSpecification) tc.getTokens().get(0);
            applyLinkSpecifiction(ls, dom);
            return;
        }

        if (!dom.isEmpty()
                && dom.getLastElement().getTypeOfContainer() == TokensContainer.TypesOfContainers.MARKUP_LIST
                && ((MarkupList) dom.getLastElement()).getLastListElement().getDescription() == null) {
            ((MarkupList) dom.getLastElement()).getLastListElement().setDescription(tc);
            return;
        }

        dom.addContainer(tc);
    }

    private boolean tryApplyPatterns(Dom dom, String line) {
        final boolean appliedHeaderOne = tryApplyHeading(dom, HEADER_ONE_PATTERN.matcher(line), 1);
        if (appliedHeaderOne) {
            return true;
        }

        final boolean appliedHeaderTwo = tryApplyHeading(dom, HEADER_TWO_PATTERN.matcher(line), 2);
        if (appliedHeaderTwo) {
            return true;
        }

        final Matcher monospaceMatcher = MONOCPACE_PATTERN.matcher(line);
        if (monospaceMatcher.matches()) {
            monospaceGlobal = !monospaceGlobal;
            return true;
        }

        final boolean appliedNonOrderedList =
                tryApplyList(dom, NON_ORDERED_LIST_ELEMENT_PATTERN.matcher(line), MarkupList.Types.NON_ORDERED);
        if (appliedNonOrderedList) {
            return true;
        }

        final boolean appliedOrderedList =
                tryApplyList(dom, ORDERED_LIST_ELEMENT_PATTERN.matcher(line), MarkupList.Types.ORDERED);
        if (appliedOrderedList) {
            return true;
        }

        return false;
    }

    private boolean tryApplyHeading(Dom dom, Matcher headingMatcher, int headerLevel) {
        if (headingMatcher.matches() && !dom.isEmpty() &&
                dom.getLastElement().getTypeOfContainer() == TokensContainer.TypesOfContainers.PARAGRAPH) {
            (dom.getLastElement()).getTokens().stream()
                    .filter(t -> t.getTypeOfTokens() == Token.TypesOfTokens.PHRASE)
                    .forEach(t -> ((Phrase) t).setHeader(headerLevel));
            return true;
        }
        return false;
    }

    private boolean tryApplyList(Dom dom, Matcher listMatcher, MarkupList.Types typeOfList) {
        if (listMatcher.matches()) {
            if (dom.getLastElement().getTypeOfContainer() != TokensContainer.TypesOfContainers.MARKUP_LIST) {
                dom.addContainer(new MarkupList(typeOfList));
            }
            if (dom.getLastElement().getTypeOfContainer() == TokensContainer.TypesOfContainers.MARKUP_LIST &&
                    ((MarkupList) dom.getLastElement()).getType() != typeOfList) {
                dom.addContainer(new MarkupList(typeOfList));
            }
            final ListElement le = new ListElement(traverseString(listMatcher.group(1)));
            dom.getLastElement().addToken(le);
            return true;
        }
        return false;
    }

    private void applyLinkSpecifiction(LinkSpecification ls, Dom currentDom) {
        for (TokensContainer container : currentDom) {
            for (Token t : container) {
                if (t.getTypeOfTokens() == Token.TypesOfTokens.LINK
                        && ((Link) t).getId() != null && ((Link) t).getId().equals(ls.getId())) {
                    ((Link) t).setSrc(ls);
                    return;
                }
                if (t.getTypeOfTokens() == Token.TypesOfTokens.IMAGE
                        && ((Image) t).getId() != null && ((Image) t).getId().equals(ls.getId())) {
                    ((Image) t).setSrc(ls);
                    return;
                }
            }
        }
    }

    private List<Link> getLinks(String line, int ifHeading) {
        List<Link> links = new LinkedList<>();
        links.addAll(getSpecialLinks(Link.TypesOfLinks.INLINE, line, ifHeading));
        links.addAll(getSpecialLinks(Link.TypesOfLinks.REFERENCED, line, ifHeading));
        return links;
    }

    private List<Link> getSpecialLinks(Link.TypesOfLinks type, String line, int ifHeading) {
        Matcher m;
        List<Link> specialLinks = new LinkedList<>();
        if (type == Link.TypesOfLinks.INLINE) {
            m = INLINE_LINK_PATTERN.matcher(line);
        } else {
            m = REFERENCED_LINK_PATTERN.matcher(line);
        }
        while (m.find()) {
            if (m.start() > 0 && line.charAt(m.start() - 1) == '!') {
                continue;
            }
            final Phrase activeText = markdownTextParser.parse(m.group(1), m.start(1), m.end(1));
            activeText.setHeader(ifHeading);
            final String websiteOrId = m.group(2);
            if (type == Link.TypesOfLinks.INLINE) {
                specialLinks.add(Link.LinkFactory.createLink(activeText, websiteOrId, m.start(), m.end()));
            } else {
                specialLinks.add(Link.LinkFactory.createReferencedLink(activeText, websiteOrId, m.start(), m.end()));
            }
        }
        return specialLinks;
    }

    private List<LinkSpecification> getLinkSpecifications(String line) {
        List<LinkSpecification> linkSpecifications = new LinkedList<>();
        final Matcher linkSpecificationMatcher = LINK_SPECIFICATION_PATTERN.matcher(line);
        while (linkSpecificationMatcher.find()) {
            final String id = linkSpecificationMatcher.group(1);
            final String src = linkSpecificationMatcher.group(2);
            LinkSpecification ls = new LinkSpecification(id, src,
                    linkSpecificationMatcher.start(), linkSpecificationMatcher.end());
            linkSpecifications.add(ls);
        }
        return linkSpecifications;
    }

    private List<Image> getImages(String line, int ifHeading) {
        List<Image> images = new LinkedList<>();
        images.addAll(getSpecialImage(Link.TypesOfLinks.INLINE, line, ifHeading));
        images.addAll(getSpecialImage(Link.TypesOfLinks.REFERENCED, line, ifHeading));
        return images;
    }

    private List<Image> getSpecialImage(Link.TypesOfLinks type, String line, int ifHeading) {
        Matcher m;
        List<Image> specialImages = new LinkedList<>();
        if (type == Link.TypesOfLinks.INLINE) {
            m = INLINE_IMAGE_PATTERN.matcher(line);
        } else {
            m = REFERENCED_IMAGE_PATTERN.matcher(line);
        }
        while (m.find()) {
            final Phrase altText = markdownTextParser.parse(m.group(1), m.start(1), m.end(1));
            altText.setHeader(ifHeading);
            final String adressOrId = m.group(2);
            if (type == Link.TypesOfLinks.INLINE) {
                specialImages.add(Image.ImageFactory.createImage(altText, adressOrId, m.start(), m.end()));
            } else {
                specialImages.add(Image.ImageFactory.createReferencedImage(altText, adressOrId, m.start(), m.end()));
            }
        }
        return specialImages;
    }

    private TokensContainer traverseString(String line) {
        TokensContainer container;
        final Matcher blackquoteMatcher = BLACKQUOTE_PATTERN.matcher(line);
        String activeText;
        if (blackquoteMatcher.matches()) {
            container = new Blockquote();
            activeText = blackquoteMatcher.group(1);
        } else {
            container = new Paragraph();
            activeText = line;
        }
        int ifHeading = 0; //0 - for non-heading// 1-6 for corresponding headers
        final Matcher m = HEADINGS_PATTERN.matcher(activeText);
        if (m.lookingAt()) {
            ifHeading = m.group(1).length();
            activeText = activeText.substring(ifHeading, activeText.length());
        }

        container.addToken(getLinks(activeText, ifHeading));
        container.addToken(getLinkSpecifications(activeText));
        container.addToken(getImages(activeText, ifHeading));
        container.sort();


        List<Phrase> phrases = new ArrayList<>();
        int beginTextIndex;
        int endTextIndex;
        final int tokensNumber = container.getTokens().size();

        for (int i = 0; i <= tokensNumber; ++i) {

            beginTextIndex = (i == 0) ? 0 : container.getTokens().get(i - 1).getEnd();
            endTextIndex = (i == tokensNumber) ? activeText.length() : container.getTokens().get(i).getBegin();

            if (beginTextIndex == endTextIndex) {
                continue;
            }

            final Phrase phrase = markdownTextParser.parse(activeText.substring(beginTextIndex, endTextIndex),
                    beginTextIndex, endTextIndex);
            phrase.setHeader(ifHeading);
            phrases.add(phrase);
        }

        container.addToken(phrases);
        container.sort();

        return container;
    }
}
