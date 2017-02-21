package com.company;

import com.company.tokens.Phrase;

import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MarkdownTextParser {

    private static final Logger LOGGER = Logger.getLogger(MarkdownTextParser.class.getName());
    private static final Pattern LINE_PROCESSOR_PATTERN = Pattern.compile("(.*?)(\\*\\*|\\*|__|_|`)");
    private static final Pattern LINE_TAIL_PATTERN = Pattern.compile("(\\*\\*|\\*|__|_|`)?([^*_`]*)$");
    private boolean isItalic;
    private char openItalic;
    private boolean isBold;
    private char openBold;
    private boolean isMonospace;

    public MarkdownTextParser() {
        clean();
    }

    public void clean() {
        isItalic = false;
        isBold = false;
        isMonospace = false;

        openItalic = '0';
        openBold = '0';
    }

    public Phrase parse(String line, int beginIndex, int endIndex) {
        Phrase phrase = new Phrase(beginIndex, endIndex);
        if (line == null || line.length() == 0) {
            return phrase;
        }

        clean();

        Matcher lineMatcher = LINE_PROCESSOR_PATTERN.matcher(line);

        while (lineMatcher.find()) {
            final String wording = lineMatcher.group(1);
            final String operator = lineMatcher.group(2);

            if (wording.length() > 0) {
                final Text text = new Text(wording);
                setUpTextStyle(text);
                phrase.addText(text);
            }

            switch (operator) {
                case "**":
                case "__":
                    final char boldOpeningSymbol = operator.charAt(0);
                    if (!isBold) {
                        isBold = true;
                        openBold = boldOpeningSymbol;
                        break;
                    }
                    if (isBold && openBold == boldOpeningSymbol) {
                        isBold = false;
                        break;
                    }
                    // case when bold was opened by opposite symbols
                    final String boldOperator = String.valueOf(openBold) + openBold;
                    clearLastTexts(phrase, Text.Properties.BOLD, false, boldOperator);
                    openBold = boldOpeningSymbol;
                    break;
                case "*":
                case "_":
                    final char italicOpeningSymbol = operator.charAt(0);
                    if (!isItalic) {
                        isItalic = true;
                        openItalic = italicOpeningSymbol;
                        break;
                    }
                    if (isItalic && openItalic == italicOpeningSymbol) {
                        isItalic = false;
                        break;
                    }
                    // case when italic was opened by opposite symbols
                    clearLastTexts(phrase, Text.Properties.ITALIC, false, String.valueOf(openItalic));
                    openItalic = italicOpeningSymbol;
                    break;
                case "`":
                    isMonospace = !isMonospace;
                    break;
                default:
                    LOGGER.log(Level.WARNING, "pattern recognized inappropriate operator " + operator);
            }
        }

        final Matcher lineTailMatcher = LINE_TAIL_PATTERN.matcher(line);
        if (lineTailMatcher.find()) {
            final String tail = lineTailMatcher.group(2);
            if (tail.length() > 0) {
                final Text text = new Text(tail);
                setUpTextStyle(text);
                phrase.addText(text);
            }
        }

        if (isBold) {
            final String operator = String.valueOf(openBold) + openBold;
            clearLastTexts(phrase, Text.Properties.BOLD, false, operator);
        }
        if (isItalic) {
            clearLastTexts(phrase, Text.Properties.ITALIC, false, String.valueOf(openItalic));
        }
        if (isMonospace) {
            clearLastTexts(phrase, Text.Properties.MONOSPACE, false, "`");
        }
        return phrase;
    }

    private void setUpTextStyle(Text text) {
        text.setState(Text.Properties.BOLD, isBold);
        text.setState(Text.Properties.ITALIC, isItalic);
        text.setState(Text.Properties.MONOSPACE, isMonospace);
    }


    /**
     * Makes given property of last elements of phrase equal to value while they are not.
     * Method appends string operator before wording of last found text block. ( where it is in the original md file )
     */
    private void clearLastTexts(Phrase phrase, Text.Properties property, boolean value, String operator) {
        final ListIterator<Text> iterator = phrase.getTexts().listIterator(phrase.getTexts().size());
        while (iterator.hasPrevious()) {
            final Text currentText = iterator.previous();
            if (currentText.getState(property) == !value) {
                currentText.setState(property, value);
            } else {
                currentText.setWording(currentText.getWording() + operator);
                break;
            }
        }
    }
}
