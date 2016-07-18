package com.company;

import com.company.Containers.Blockquote;
import com.company.Containers.Paragraph;
import com.company.Containers.TokensContainer;
import com.company.Tokens.Image;
import com.company.Tokens.Links.Link;
import com.company.Tokens.Links.LinkSpecification;
import com.company.Tokens.Phrase;
import com.company.Tokens.Text;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownReader {

    private static final Pattern HEADINGS_PATTERN = Pattern.compile("^(#{1,6})\\w+");
    private static final Pattern INLINE_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\(([^\\)]*)\\)");
    private static final Pattern REFERENCED_LINK_PATTERN = Pattern.compile("\\[([^\\]]+)\\]\\[([^\\)]*)\\]");
    private static final Pattern INLINE_IMAGE_PATTERN = Pattern.compile("!\\[([^\\]]*)\\]\\(([^\\)]*)\\)");
    private static final Pattern REFERENCED_IMAGE_PATTERN = Pattern.compile("!\\[([^\\]]*)\\]\\[([^\\)]*)\\]");
    private static final Pattern LINK_SPECIFICATION_PATTERN = Pattern.compile("\\[([^\\]]+)\\]:\\s?(.+)");

    private BufferedReader reader;

    public MarkdownReader(String fileName) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(fileName));
    }

    public MarkdownReader(File file) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
    }

    public MarkdownReader(FileDescriptor fd) {
        reader = new BufferedReader(new FileReader(fd));
    }

    public Dom makeDom() {
        Dom dom = new Dom();
        String s;
        try {
            while ((s = reader.readLine()) != null) {
                TokensContainer tc = traverseString(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dom;
    }

    public TokensContainer traverseString(String line) {
        TokensContainer container;
        if (line.startsWith(">")) {
            container = new Blockquote();
        } else {
            container = new Paragraph();
        }

        final Matcher inlineLinkMatcher = INLINE_LINK_PATTERN.matcher(line);
        final Matcher referencedLinkMatcher = REFERENCED_LINK_PATTERN.matcher(line);
        final Matcher linkSpecificationMatcher = LINK_SPECIFICATION_PATTERN.matcher(line);
        final Matcher inlineImageMatcher = INLINE_IMAGE_PATTERN.matcher(line);
        final Matcher referencedImageMatcher = REFERENCED_IMAGE_PATTERN.matcher(line);

        while (inlineLinkMatcher.find()) {
            if (inlineLinkMatcher.start() > 0 && line.charAt(inlineLinkMatcher.start() - 1) == '!') {
                continue;
            }
            final Phrase activeText = makeText(inlineLinkMatcher.group(1),
                    inlineLinkMatcher.start(1), inlineLinkMatcher.end(1));
            final String website = inlineLinkMatcher.group(2);
            container.addToken(Link.LinkFactory.createLink(activeText, website,
                    inlineLinkMatcher.start(), inlineLinkMatcher.end()));
        }
        while (referencedLinkMatcher.find()) {
            if (referencedLinkMatcher.start() > 0 && line.charAt(referencedLinkMatcher.start() - 1) == '!') {
                continue;
            }
            final Phrase activeText = makeText(referencedLinkMatcher.group(1),
                    referencedLinkMatcher.start(1), referencedLinkMatcher.end(1));
            final String id = referencedLinkMatcher.group(2);
            container.addToken(Link.LinkFactory.createReferencedLink(activeText, id,
                    referencedLinkMatcher.start(), referencedLinkMatcher.end()));
        }
        while (linkSpecificationMatcher.find()) {
            final String id = linkSpecificationMatcher.group(1);
            final String src = linkSpecificationMatcher.group(2);
            LinkSpecification ls = new LinkSpecification(id, src,
                    linkSpecificationMatcher.start(), linkSpecificationMatcher.end());
            container.addToken(ls);
            for (Token t : container) {
                if (t instanceof Link && ((Link) t).getId().equals(ls.getId())) {
                    ((Link) t).setSrc(ls);
                    break;
                }
                if (t instanceof Image && ((Image) t).getId().equals(ls.getId())) {
                    ((Image) t).setSrc(ls);
                    break;
                }
            }
        }
        while (inlineImageMatcher.find()) {
            final Phrase altText = makeText(inlineImageMatcher.group(1),
                    inlineImageMatcher.start(1), inlineImageMatcher.end(1));
            final String src = inlineImageMatcher.group(2);
            container.addToken(Image.ImageFactory.createImage(altText, src,
                    inlineImageMatcher.start(), inlineImageMatcher.end()));
        }
        while (referencedImageMatcher.find()) {
            final Phrase altText = makeText(referencedImageMatcher.group(1),
                    referencedImageMatcher.start(1), referencedImageMatcher.end(1));
            final String id = referencedImageMatcher.group(2);
            container.addToken(Image.ImageFactory.createReferencedImage(altText, id,
                    referencedImageMatcher.start(), referencedImageMatcher.end()));
        }
        container.sort();


        int ifHeading = 0; //0 - for non-heading// 1-6 for corresponding headers
        final Matcher m = HEADINGS_PATTERN.matcher(line);
        if (m.lookingAt()) {
            ifHeading = m.group(1).length();
        }


        List<Phrase> phrases = new LinkedList<>();
        for (int i = 1; i < container.getTokens().size(); i++) {
            final Phrase p = makeText(line.substring(container.getTokens().get(i - 1).getEnd(),
                    container.getTokens().get(i).getBegin()), container.getTokens().get(i - 1).getEnd(),
                    container.getTokens().get(i).getBegin());
            p.setHeader(ifHeading);
            if (!p.isEmpty()) {
                phrases.add(p);

            }
        }
        if (container.getTokens().get(0).getBegin() != 0) { //first is exist
            final Phrase t = makeText(line.substring(ifHeading, container.getTokens().get(0).getBegin()),
                    ifHeading, container.getTokens().get(0).getBegin());
            t.setHeader(ifHeading);
            phrases.add(t);
        }

        if (container.getTokens().get(container.getTokens().size() - 1).getEnd() < line.length()) { //last is exist
            final Phrase t = makeText(
                    line.substring(container.getTokens().get(container.getTokens().size() - 1).getEnd(),
                            line.length()), container.getTokens().get(container.getTokens().size() - 1).getEnd(),
                    line.length());
            t.setHeader(ifHeading);
            phrases.add(t);
        }

        container.addToken(phrases);
        container.sort();
        for (Token t : container) {
            System.out.println(t.getBegin() + " - " + t.getEnd() + " " + t.toString());
        }
        System.out.println();
        /*for (Phrase t : phrases) {
            System.out.println(t.getBegin() + " - " + t.getEnd() + " " + t.toString());
        }*/
        return container;
    }


    private Phrase makeText(String line, int begin, int end) {
        Phrase phrase = new Phrase(begin, end);
        if (line == null || line.length() == 0)
            return phrase;
        int pointer = 0;

        boolean isItalics = false;
        char openItalics = '0';
        boolean isBold = false;
        char openBold = '0';

        do {
            //pointer now is on * or _ or neither. i should check for italics or bold opening or closing
            if (pointer < line.length() && line.charAt(pointer) == '*') {
                if (pointer + 1 < line.length() && line.charAt(pointer + 1) == '*') { //BOLD case '**'
                    if (isBold && openBold == '*') { //closing
                        isBold = false;
                    } else { //opening
                        isBold = true;
                        openBold = '*';
                    }
                    pointer += 2;
                } else { //ITALICS case '*'
                    if (isItalics && openItalics == '*') { //closing
                        isItalics = false;
                    } else { //opening
                        isItalics = true;
                        openItalics = '*';
                    }
                    pointer++;
                }
            }

            if (pointer < line.length() && line.charAt(pointer) == '_') {
                if (pointer + 1 < line.length() && line.charAt(pointer + 1) == '_') { //BOLD case '__'
                    if (isBold && openBold == '_') { //closing
                        isBold = false;
                    } else { //opening
                        isBold = true;
                        openBold = '_';
                    }
                    pointer += 2;
                } else { //ITALICS case '_'
                    if (isItalics && openItalics == '_') { //closing
                        isItalics = false;
                    } else { //opening
                        isItalics = true;
                        openItalics = '_';
                    }
                    pointer++;
                }
            }
            ////
            final StringBuffer sb = new StringBuffer();
            while (pointer < line.length() && line.charAt(pointer) != '*' && line.charAt(pointer) != '_') {
                sb.append(line.charAt(pointer));
                pointer++; //TODO i think there is a better way to copy text until special symbol rather than do it by char
            }
            if (sb.length() > 0) {
                final Text currentText = new Text(sb.toString());
                if (isBold) {
                    currentText.setState(Text.Properties.BOLD, true);
                }
                if (isItalics) {
                    currentText.setState(Text.Properties.ITALIC, true);
                }
                phrase.addText(currentText);
            }
        } while (pointer < line.length());

        if (isBold || isItalics) {
            //TODO throw new NotClosedException
        }

        return phrase;
    }


}
