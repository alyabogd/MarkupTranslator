package com.company.Containers;

import com.company.Tokens.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Paragraph extends TokensContainer{

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ start paragraph: \n");
        sb.append(super.toString());
        sb.append(" end paragraph }\n");
        return sb.toString();
    }
}
