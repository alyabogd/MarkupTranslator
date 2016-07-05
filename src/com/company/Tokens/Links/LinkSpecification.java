package com.company.Tokens.Links;

import com.company.Tokens.Token;

public class LinkSpecification extends Token {
    private String id;
    private String adress;

    public LinkSpecification(String id, String adress){
        this.id = id;
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }
}
