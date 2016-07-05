package com.company;

import com.company.Tokens.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        /*Pattern HEADINGS_PATTENR = Pattern.compile("^#{1,6}\\w+");
        Matcher m = HEADINGS_PATTENR.matcher("#heading one");
        System.out.println(m.lookingAt());*/

        String s = "#heaing one with *italics* and _another italics_ and **bold** and " +
                "__another bold__ ";
        String difficult = "###heading three with _**one**_ and __*two*__";

        try {
            MarkdownReader markdownReader = new MarkdownReader("input.md");
            List<Text> list = markdownReader.makeText(difficult);
            System.out.println(list.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
