package com.company;

import com.company.containers.TokensContainer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Dom implements Iterable<TokensContainer> {

    private String fileName;
    private List<TokensContainer> containers = new LinkedList<>();

    public Dom(String fileName) {
        this.fileName = fileName;
    }

    public List<TokensContainer> getContainers() {
        return containers;
    }

    public void addContainer(TokensContainer t) {
        containers.add(t);
    }

    public TokensContainer getLastElement() {
        if (isEmpty()) {
            return null;
        }
        return containers.get(containers.size() - 1);
    }

    public boolean isEmpty() {
        return containers.isEmpty();
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public Iterator<TokensContainer> iterator() {
        return containers.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TokensContainer t : containers) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }
}
