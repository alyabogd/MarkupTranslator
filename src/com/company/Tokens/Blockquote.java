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

    public void addToken(Token t) throws RecursiveBlockquoteException {
        if (t instanceof Blockquote)
            throw new RecursiveBlockquoteException("Unable to hold blockquote in blockquote");
        tokens.add(t);
    }

    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }
}
