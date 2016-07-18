package com.company.Containers;

import com.company.Exceptions.RecursiveBlockquoteException;
import com.company.Tokens.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Blockquote extends TokensContainer implements Iterable<Token> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{start blackquote: \n");
        sb.append(super.toString());
        sb.append("end blockquote} ");
        return sb.toString();
    }
}
