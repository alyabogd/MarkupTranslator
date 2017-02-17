package com.company.tokens.links;

import com.company.tokens.Token;

public class LinkSpecification extends Token {
    private String id;
    private String adress;

    public LinkSpecification(String id, String adress, int begin, int end){
        this.id = id;
        this.adress = adress;
        this.begin = begin;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public String getAdress() {
        return adress;
    }

    @Override
    public String toString() {
        return "{ LinkSpecifiation for id " + id + " : " + adress + "} ";
    }
}
