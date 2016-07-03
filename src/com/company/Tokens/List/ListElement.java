package com.company.Tokens.List;

import com.company.Tokens.Text;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListElement {
    private Text title;
    private List<Token> description = new ArrayList<>();

    public ListElement(Text title, List<Token> description) {
        this.title = title;
        this.description = description;
    }

    public ListElement(Text title) {
        this.title = title;
    }

    public Text getTitle() {
        return title;
    }

    public void addDescription(Token t){
        description.add(t);
    }

    @Nullable
    public List<Token> getDescription() {
        return description;
    }
}
