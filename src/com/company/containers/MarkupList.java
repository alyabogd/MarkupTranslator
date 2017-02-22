package com.company.containers;

import com.company.tokens.ListElement;
import com.company.tokens.Token;

public class MarkupList extends TokensContainer {

    public enum Types {
        ORDERED,
        NON_ORDERED
    }

    private Types type;

    public MarkupList(Types t) {
        this.type = t;
        typeOfContainer = TypesOfContainers.MARKUP_LIST;
    }

    public ListElement getLastListElement() {
        if (tokens.isEmpty()) {
            return null;
        }
        return (ListElement) tokens.get(tokens.size() - 1);
    }

    public Types getType() {
        return type;
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
