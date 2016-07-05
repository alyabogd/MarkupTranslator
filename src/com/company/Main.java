package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
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
        }
    }
}
