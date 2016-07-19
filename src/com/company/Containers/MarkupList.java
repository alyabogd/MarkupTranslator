package com.company.Containers;

import com.company.Tokens.ListElement;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarkupList extends TokensContainer {

    public enum Types {
        ORDRED,
        NON_ORDERED
    }

    private Types type;

    public MarkupList(Types t) {
        this.type = t;
    }

    @Nullable
    public ListElement getLastListElement() {
        if (tokens.isEmpty()) {
            return null;
        }
        return (ListElement) tokens.get(tokens.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ List ").append(type).append(" : ");
        for (Token le : tokens) {
            sb.append("\n").append("    ").append(le.toString());
        }
        sb.append(" }");
        return sb.toString();
    }
}
