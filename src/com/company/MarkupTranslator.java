package com.company;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarkupTranslator {

    private static final Logger LOGGER = Logger.getLogger(MarkupTranslator.class.getName());

    private MarkupTranslator() {
        throw new IllegalAccessError("Utility class");
    }

    public static void translate(File f) {
        try {
            MarkdownReader markdownReader = new MarkdownReader(f);
            Dom dom = markdownReader.makeDom();
            File parent = new File(f.getParent());
            String outName = f.getName().split("\\.")[0] + ".html";
            File output = new File(parent, outName);
            boolean isCreated = output.createNewFile();
            if (!isCreated) {
                LOGGER.warning("unable to create file");
            }
            HtmlWriter htmlWriter = new HtmlWriter(output);
            htmlWriter.makeHtml(dom);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
