package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {
       if (args.length == 0){
           System.out.println("no args");
       } else {
           File f = new File(args[0]);
           MarkupTranslator.translate(f);
       }
    }
}
