package com.company.tokens;

public abstract class Token implements Comparable<Token> {

    public enum TypesOfTokens{
        LINK,
        IMAGE,
        PHRASE
    }

    protected int begin;
    protected int end;
    protected TypesOfTokens typeOfTokens;

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public TypesOfTokens getTypeOfTokens() {
        return typeOfTokens;
    }

    @Override
    public int compareTo(Token o) {
        return (this.begin - o.begin) > 0 ? 1 : (this.begin - o.begin) < 0 ? -1 : 0;
    }
}
