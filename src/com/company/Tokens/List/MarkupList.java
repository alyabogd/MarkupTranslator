package com.company.Tokens.List;

import com.company.Tokens.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MarkupList extends Token implements Iterable<ListElement> {

    public enum Types {
        ORDRED,
        NON_ORDERED
    }

    private Types type;
    private List<ListElement> elements = new ArrayList<>();

    public MarkupList(Types t) {
        this.type = t;
    }

    public void add(ListElement element) {
        elements.add(element);
    }

    public List<ListElement> getElements() {
        return elements;
    }

    @Override
    public Iterator<ListElement> iterator() {
        return elements.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ List ").append(type).append(" : ");
        for (ListElement le: elements) {
            sb.append("\n").append("    ").append(le.toString());
        }
        sb.append(" }");
        return sb.toString();
    }
}
