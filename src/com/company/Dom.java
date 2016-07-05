package com.company;

import com.company.Tokens.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dom implements Iterable<Token> {

    private List<Token> tokens = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token t){
        tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token t: tokens) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }
}
