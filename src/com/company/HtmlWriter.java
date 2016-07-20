package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HtmlWriter {

    private PrintWriter printWriter;

    public HtmlWriter(File file) throws FileNotFoundException {
        printWriter = new PrintWriter(file);
    }

    public HtmlWriter(String fileName) throws FileNotFoundException {
        printWriter = new PrintWriter(fileName);
    }

    public void makeHtml(Dom dom){
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
        printWriter.println("<title>" + dom.getFileName() +"</title>");
        printWriter.println("</head> \n");
        printWriter.println("<body>");
        writeDom();
        printWriter.println("</body>");
        printWriter.println("</html>");
        printWriter.close();
    }

    public void writeDom(){
        printWriter.println("<p> hello </p>");
    }


}
