package com.company;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        /*try {
            BufferedReader reader = new BufferedReader(new FileReader("input.md"));
            int c;
            StringBuilder sb = new StringBuilder();
            System.out.println((char)reader.read());
            System.out.println(reader.readLine());

            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String s = "#heaing one with *italics* and _another italics_ and **bold** and " +
                "__another bold__ ";
        String difficult = "###heading three with _**one**_ and __*two*__";
        String tryingLinks = "[this *is* __a__ link](www.website.com";

        try {
            MarkdownReader markdownReader = new MarkdownReader("input.md");
            Dom dom = markdownReader.makeDom();
            System.out.println(dom.toString());
            HtmlWriter htmlWriter = new HtmlWriter("myoutput.html");
            htmlWriter.makeHtml(dom);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        /*try {
            PrintWriter pw = new PrintWriter("myOut.txt");
            pw.print("hello world");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
