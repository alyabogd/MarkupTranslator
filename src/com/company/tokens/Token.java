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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (begin != token.begin) return false;
        if (end != token.end) return false;
        return typeOfTokens == token.typeOfTokens;
    }

    @Override
    public int hashCode() {
        int result = begin;
        result = 31 * result + end;
        result = 31 * result + typeOfTokens.hashCode();
        return result;
    }
}
