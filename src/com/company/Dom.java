package com.company;

import com.company.Containers.TokensContainer;
import com.sun.istack.internal.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Dom implements Iterable<TokensContainer> {

    private List<TokensContainer> containers = new LinkedList<>();

    public List<TokensContainer> getContainers() {
        return containers;
    }

    public void addContainer(TokensContainer t) {
        containers.add(t);
    }

    @Nullable
    public TokensContainer getLastElement() {
        if (isEmpty()) {
            return null;
        }
        return containers.get(containers.size() - 1);
    }

    public boolean isEmpty() {
        return containers.isEmpty();
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
