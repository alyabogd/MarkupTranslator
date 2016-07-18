package com.company;

import com.company.Tokens.Image;
import com.company.Tokens.Links.Link;
import com.company.Tokens.Links.LinkSpecification;
import com.company.Tokens.Text;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.io.*;
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
            while((s = reader.readLine()) != null){
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dom;
    }

    public Token traverseString(String line){

    }

    private  List<Text> makeText(String line) {
        List<Text> paragraph = new LinkedList<>();
        if (line == null || line.length() == 0)
            return paragraph;
        int ifHeading = 0; //0 - for non-heading// 1-6 for corresponding headers
        int pointer = 0;
        final Matcher m = HEADINGS_PATTERN.matcher(line);
        if (m.lookingAt()) {
            ifHeading = m.group(1).length();
            pointer = m.end(1);
        }
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
                if (ifHeading != 0) {
                    currentText.setState(Text.Properties.values()[ifHeading], true);
                }
                if (isBold) {
                    currentText.setState(Text.Properties.BOLD, true);
                }
                if (isItalics) {
                    currentText.setState(Text.Properties.ITALIC, true);
                }
                paragraph.add(currentText);
            }
        } while (pointer < line.length());

        if (isBold || isItalics) {
            //TODO throw new NotClosedException
        }

        return paragraph;
    }

    @Nullable
    private Link makeLink(String line) {
        final Matcher inlineMatcher = INLINE_LINK_PATTERN.matcher(line);
        final Matcher referencedMatcher = REFERENCED_LINK_PATTERN.matcher(line);
        if (inlineMatcher.find()) {
            final String activeText = inlineMatcher.group(1);
            final String website = inlineMatcher.group(2);
            return Link.LinkFactory.createLink(makeText(activeText), website);
        }
        if (referencedMatcher.find()) {
            final String activeText = referencedMatcher.group(1);
            final String id = referencedMatcher.group(2);
            return Link.LinkFactory.createReferencedLink(makeText(activeText), id);
        }
        return null;
    }

    @Nullable
    private Image makeImage(String line){
        final Matcher inlineMatcher = INLINE_IMAGE_PATTERN.matcher(line);
        final Matcher referencedMatcher = REFERENCED_IMAGE_PATTERN.matcher(line);
        if (inlineMatcher.find()){
            final String altText = inlineMatcher.group(1);
            final String src = inlineMatcher.group(2);
            return Image.ImageFactory.createImage(altText, src);
        }
        if (referencedMatcher.find()){
            final String altText = inlineMatcher.group(1);
            final String id = inlineMatcher.group(2);
            return Image.ImageFactory.createReferencedImage(altText, id);
        }
        return null;
    }

    @Nullable
    private LinkSpecification makeLinkSpecificatinon(String line){
        final Matcher linkSpecificationMatcher = LINK_SPECIFICATION_PATTERN.matcher(line);
        if (linkSpecificationMatcher.find()){
            final String id = linkSpecificationMatcher.group(1);
            final  String src = linkSpecificationMatcher.group(2);
            return new LinkSpecification(id, src);
        }
        return null;
    }


}
