package com.company.containers;

import com.company.tokens.Token;

import java.util.*;

public abstract class TokensContainer implements Iterable<Token> {

    public enum TypesOfContainers {
        BLOCKQUOTE,
        MARKUP_LIST,
        PARAGRAPH
    }

    protected TypesOfContainers typeOfContainer;

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

    public TypesOfContainers getTypeOfContainer() {
        return typeOfContainer;
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) {
            sb.append(t.getBegin()).append(" - ").append(t.getEnd()).append(" ").append(t.toString()).append("\n");
        }
        return sb.toString();
    }
}
