package com.company;

import com.company.Containers.Blockquote;
import com.company.Containers.MarkupList;
import com.company.Containers.Paragraph;
import com.company.Containers.TokensContainer;
import com.company.Tokens.Image;
import com.company.Tokens.Links.Link;
import com.company.Tokens.ListElement;
import com.company.Tokens.Phrase;
import com.company.Tokens.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;


public class HtmlWriter {

    private PrintWriter printWriter;

    public HtmlWriter(File file) throws FileNotFoundException {
        printWriter = new PrintWriter(file);
    }

    public HtmlWriter(String fileName) throws FileNotFoundException {
        printWriter = new PrintWriter(fileName);
    }


    public void makeHtml(Dom dom) {
        /**
         * <!DOCTYPE html>
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
        switch (tc.getTypeOfContainer()){
            case BLOCKQUOTE:
                writeBlockquote(((Blockquote) tc));
                return;
            case MARKUP_LIST:
                writeMarkupList(((MarkupList) tc));
                return;
            case PARAGRAPH:
                writeParagraph(((Paragraph) tc));
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
        switch (markupList.getType()) {
            case ORDRED:
                printWriter.println("<ol>");
                break;
            case NON_ORDERED:
                printWriter.println("<ul>");
                break;
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
        switch (markupList.getType()) {
            case ORDRED:
                printWriter.println("</ol>");
                break;
            case NON_ORDERED:
                printWriter.println("</ul>");
                break;
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
        switch (t.getTypeOfTokens()){
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
                writePhrase(((Phrase) t));
                return;
            default:
                throw new IllegalArgumentException("not a token in writeToken()");
        }
    }

    private void writePhrase(Phrase p) {
        for (Text t : p) {
            final String s;
            final int level = t.getHeaderLevel();
            if (level != 0) {
                s = "h" + String.valueOf(level);
                printWriter.print("<" + s + ">");
            } else {
                s = "";
            }

            if (t.isBold()) {
                printWriter.print("<b>");
            }
            if (t.isItalics()) {
                printWriter.print("<i>");
            }

            printWriter.print(t.getWording());

            if (t.isItalics()) {
                printWriter.print("</i>");
            }
            if (t.isBold()) {
                printWriter.print("</b>");
            }
            if (level != 0) {
                printWriter.print("</" + s + ">");
            }
        }


    }


}
