package com.company.Tokens;

public abstract class Token implements Comparable<Token> {
    protected int begin;
    protected int end;

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public int compareTo(Token o) {
        return (this.begin - o.begin) > 0 ? 1 : (this.begin - o.begin) < 0 ? -1 : 0;
    }
}
