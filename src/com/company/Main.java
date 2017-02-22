package com.company;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {
        throw new IllegalAccessError("Main class");
    }

    public static void main(String[] args) {
       if (args.length == 0){
           LOGGER.log(Level.INFO, "no args");
       } else {
           File f = new File(args[0]);
           MarkupTranslator.translate(f);
       }
    }
}
