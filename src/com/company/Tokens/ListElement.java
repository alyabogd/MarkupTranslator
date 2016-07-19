package com.company.Tokens;

import com.company.Containers.TokensContainer;
import com.company.Text;

public class ListElement extends Token {
    private Phrase title;
    private TokensContainer description;

    public ListElement(String wording){
        this.title = new Phrase(new Text(wording));
    }

    public ListElement(Text t){
        this.title = new Phrase(t);
    }

    public ListElement(Phrase title) {
        this.title = title;
    }

    public TokensContainer getDescription() {
        return description;
    }

    public void setDescription(TokensContainer description) {
        this.description = description;
    }

    public Phrase getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title.toString();
    }
}
