package com.company.tokens;

import com.company.containers.TokensContainer;
import com.sun.istack.internal.Nullable;


public class ListElement extends Token {
    private TokensContainer title;
    private TokensContainer description;

    public ListElement(TokensContainer tc) {
        this.title = tc;
    }

    @Nullable
    public TokensContainer getDescription() {
        return description;
    }

    public void setDescription(TokensContainer description) {
        this.description = description;
    }

    public TokensContainer getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title.toString() + "\n    description: " + (description == null ? "" : description.toString());
    }
}
