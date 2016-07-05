package com.company;

import java.io.*;

public class MarkdownReader {

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




}
