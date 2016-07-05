package com.company.Tokens;

import com.company.Exceptions.RecursiveBlockquoteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Blockquote extends Token implements Iterable<Token> {
    private List<Token> tokens = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token t) {
        tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{start blackquote: \n");
        for (Token t: tokens){
            sb.append("    ").append(t.toString()).append("\n");
        }
        sb.append("end blockquote} ");
        return sb.toString();
    }
}
