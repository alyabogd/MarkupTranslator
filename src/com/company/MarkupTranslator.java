package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class MarkupTranslator {

    public static void translate(File f) {
        try {
            MarkdownReader markdownReader = new MarkdownReader(f);
            Dom dom = markdownReader.makeDom();
            File parent = new File(f.getParent());
            String outName = f.getName().substring(0, f.getName().length() - 3) + ".html";
            File output = new File(parent, outName);
            output.createNewFile();
            HtmlWriter htmlWriter = new HtmlWriter(output);
            htmlWriter.makeHtml(dom);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
