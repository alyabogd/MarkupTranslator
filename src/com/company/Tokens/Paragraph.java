package com.company.Tokens;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Paragraph extends Token implements Iterable<Token>{

    private List<Token> tokens = new ArrayList<>();

    public void addToken(Token t){
        tokens.add(t);
        //TODO validate input
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token t: tokens) {
            sb.append(t.toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
