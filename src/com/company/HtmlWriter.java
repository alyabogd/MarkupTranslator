package com.company;

import com.company.containers.Blockquote;
import com.company.containers.MarkupList;
import com.company.containers.Paragraph;
import com.company.containers.TokensContainer;
import com.company.tokens.Image;
import com.company.tokens.links.Link;
import com.company.tokens.ListElement;
import com.company.tokens.Phrase;
import com.company.tokens.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import static com.company.Text.Properties.*;


public class HtmlWriter {

    private PrintWriter printWriter;

    /*private static final String MONOSPACE = "MONOSPACE";
    private static final String ITALICS = "ITALICS";
    private static final String BOLD = "BOLD";*/


    public HtmlWriter(File file) throws FileNotFoundException {
        printWriter = new PrintWriter(file);
    }

    public HtmlWriter(String fileName) throws FileNotFoundException {
        printWriter = new PrintWriter(fileName);
    }


    public void makeHtml(Dom dom) {
        /*
         <!DOCTYPE html>
         <html>
         <head>
         <title>Page Title</title>
         </head>
         <body>

         <h1>My First Heading</h1>
         <p>My first paragraph.</p>

         </body>
         </html>
         */
        printWriter.println("<!DOCTYPE html>");
        printWriter.println("<html>");
        printWriter.println("<head>");
        printWriter.println("<title>" + dom.getFileName() + "</title>");
        printWriter.println("</head> \n");
        printWriter.println("<body>");
        writeDom(dom);
        printWriter.println("</body>");
        printWriter.println("</html>");
        printWriter.close();
    }

    private void writeDom(Dom dom) {
        for (TokensContainer tc : dom) {
            writeTokenContainer(tc);
        }
    }

    private void writeTokenContainer(TokensContainer tc) {
        switch (tc.getTypeOfContainer()) {
            case BLOCKQUOTE:
                writeBlockquote((Blockquote) tc);
                return;
            case MARKUP_LIST:
                writeMarkupList((MarkupList) tc);
                return;
            case PARAGRAPH:
                writeParagraph((Paragraph) tc);
                return;
            default:
                throw new IllegalArgumentException("not a container in writeTokenContainer()");
        }
    }

    private void writeBlockquote(Blockquote blockquote) {
        printWriter.println("<blockquote>");
        writeTokens(blockquote.getTokens());
        printWriter.println("</blockquote>");
    }

    private void writeMarkupList(MarkupList markupList) {
        if (markupList.getType() == MarkupList.Types.ORDERED) {
            printWriter.println("<ol>");
        } else { // MarkupList.Types.NON_ORDERED
            printWriter.println("<ul>");
        }

        for (Token le : markupList) {
            assert le instanceof ListElement;
            printWriter.print("<li>");
            writeTokenContainer(((ListElement) le).getTitle());
            printWriter.println("</li>");
            if (((ListElement) le).getDescription() != null) {
                writeTokenContainer(((ListElement) le).getDescription());
            }
        }
        if (markupList.getType() == MarkupList.Types.ORDERED) {
            printWriter.println("</ol>");
        } else { // MarkupList.Types.NON_ORDERED
            printWriter.println("</ul>");
        }
    }

    private void writeParagraph(Paragraph paragraph) {
        printWriter.print("<p>");
        writeTokens(paragraph.getTokens());
        printWriter.println("</p>");
    }

    private void writeTokens(List<Token> tokens) {
        for (Token t : tokens) {
            writeToken(t);
        }
    }

    private void writeToken(Token t) {
        switch (t.getTypeOfTokens()) {
            case LINK:
                printWriter.print("<a href=" + ((Link) t).getSrc() + ">");
                writePhrase(((Link) t).getText());
                printWriter.print("</a>");
                return;
            case IMAGE:
                printWriter.print("<img src=\"" + ((Image) t).getSrc() + "\"");
                printWriter.print(" alt=\"" + ((Image) t).getAltText().getSimpleText() + "\" />");
                return;
            case PHRASE:
                writePhrase((Phrase) t);
                return;
            default:
                throw new IllegalArgumentException("not a token in writeToken()");
        }
    }

    private void writePhrase(Phrase phrase) {
        for (Text text : phrase) {
            final String headingLevelTag;
            final int level = text.getHeaderLevel();
            if (level != 0) {
                headingLevelTag = "h" + level;
                printWriter.print("<" + headingLevelTag + ">");
            } else {
                headingLevelTag = "";
            }

            writeTagsForTextStyle(text, false);

            printWriter.print(text.getWording());

            writeTagsForTextStyle(text, true);

            if (level != 0) {
                printWriter.print("</" + headingLevelTag + ">");
            }
        }
    }

    private void writeTagsForTextStyle(Text text, boolean isClosingTag) {
        final String modifier = isClosingTag ? "/" : "";
        final StringBuilder tags = new StringBuilder();
        // opening: monospace -> bold -> italic
        final List<Text.Properties> order = new ArrayList<>();
        order.add(MONOSPACE);
        order.add(BOLD);
        order.add(ITALIC);

        if (isClosingTag) {
            Collections.reverse(order);
        }
        for(Text.Properties type: order) {
            if (type == MONOSPACE && text.isMonospace()) {
                tags.append("<").append(modifier).append("code>");
            }
            if (type == BOLD && text.isBold()) {
                tags.append("<").append(modifier).append("b>");
            }
            if (type == ITALIC && text.isItalics()) {
                tags.append("<").append(modifier).append("i>");
            }
        }

        printWriter.print(tags.toString());
    }


}
