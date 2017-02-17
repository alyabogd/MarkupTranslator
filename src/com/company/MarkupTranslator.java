package com.company;

import java.io.File;
import java.io.IOException;

public class MarkupTranslator {

    private MarkupTranslator() {
        throw new IllegalAccessError("Utility class");
    }

    public static void translate(File f) {
        try {
            MarkdownReader markdownReader = new MarkdownReader(f);
            Dom dom = markdownReader.makeDom();
            File parent = new File(f.getParent());
            String outName = f.getName().substring(0, f.getName().length() - 3) + ".html";
            File output = new File(parent, outName);
            boolean isCreated = output.createNewFile();
            if (!isCreated) {
                System.err.print("unable to create file");
            }
            HtmlWriter htmlWriter = new HtmlWriter(output);
            htmlWriter.makeHtml(dom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
