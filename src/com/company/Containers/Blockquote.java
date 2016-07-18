package com.company.Containers;

import com.company.Tokens.Token;

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
