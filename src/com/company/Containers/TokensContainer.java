package com.company.Containers;

import com.company.Tokens.Token;

import java.util.*;

public abstract class TokensContainer implements Iterable<Token> {
    protected List<Token> tokens = new ArrayList<>();

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token t){
        tokens.add(t);
    }

    public void addToken(List<? extends Token> t){
        tokens.addAll(t);
    }

    public void sort(){
        Collections.sort(tokens);
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            sb.append(t.toString());
        }
        return sb.toString();
    }
}
