package com.company.Tokens.Links;

import com.company.Tokens.Phrase;
import com.company.Tokens.Text;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Link extends Token {

    private Phrase text;
    private String src;
    private String id; //in case one is a referenced link

    public void setSrc(LinkSpecification ls) throws IllegalArgumentException {
        if (ls.getId().equals(this.id)) {
            this.src = ls.getAdress();
        } else {
            throw new IllegalArgumentException("id are not the same");
        }
    }

    @Nullable
    public String getSrc() {
        return src;
    }

    public Phrase getText() {
        return text;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public static class LinkFactory {
        public static Link createLink(Phrase text, String src, int begin, int end) {
            Link link = new Link();
            link.text = text;
            link.src = src;
            link.begin = begin;
            link.end = end;
            return link;
        }

        public static Link createLink(Text text, String src, int begin, int end){
            return LinkFactory.createLink(new Phrase(Collections.singletonList(text)), src, begin, end);
        }

        public static Link createLink(String wording, String src, int begin, int end) {
            return LinkFactory.createLink(new Text(wording), src, begin, end);
        }

        public static Link createReferencedLink(Phrase text, String id, int begin, int end){
            Link link = new Link();
            link.text = text;
            link.id = id;
            link.begin = begin;
            link.end = end;
            return link;
        }

        public static Link createReferencedLink(Text text, String id, int begin, int end) {
            return LinkFactory.createReferencedLink(new Phrase(Collections.singletonList(text)), id, begin, end);
        }

        public static Link createReferencedLink(String wording, String id, int begin, int end) {
            return LinkFactory.createReferencedLink(new Text(wording), id, begin, end);
        }
    } //LinkFactory

    @Override
    public String toString() {
        String s =  "{ Link: " + text + " ";
        if (src == null){
            s += "[id " + id + "]} ";
        } else {
            s += "[src " + src + "]} ";
        }
        return s;
    }
}
