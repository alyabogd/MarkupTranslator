package com.company.Containers;

public class Paragraph extends TokensContainer {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ start paragraph: \n");
        sb.append(super.toString());
        sb.append(" end paragraph }\n");
        return sb.toString();
    }
}
