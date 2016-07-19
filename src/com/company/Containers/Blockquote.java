package com.company.Containers;


public class Blockquote extends TokensContainer {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{start blackquote: \n");
        sb.append(super.toString());
        sb.append("end blockquote} ");
        return sb.toString();
    }
}
