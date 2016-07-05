package com.company;

import com.company.Tokens.Paragraph;
import com.company.Tokens.Text;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownReader {

    private static final Pattern HEADINGS_PATTERN = Pattern.compile("^(#{1,6})\\w+");

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


        return dom;
    }

    public List<Text> makeText(String line) {
        List<Text> paragraph = new LinkedList<>();
        if (line == null || line.length() == 0)
            return paragraph;
        int ifHeading = 0; //0 - for non-heading// 1-6 for corresponding headers
        int pointer = 0;
        Matcher m = HEADINGS_PATTERN.matcher(line);
        if (m.lookingAt()) {
            ifHeading = m.group(1).length();
            pointer = m.end(1);
        }
        boolean isItalics = false;
        char openItalics = '0';
        boolean isBold = false;
        char openBold = '0';

        do {
            StringBuffer sb = new StringBuffer();

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


}
