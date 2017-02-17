package com.company.tokens.links;

import com.company.tokens.Phrase;
import com.company.Text;
import com.company.tokens.Token;
import com.sun.istack.internal.Nullable;

import java.util.Collections;

public class Link extends Token {

    public enum TypesOfLinks{
        INLINE,
        REFERENCED
    }

    private Phrase text;
    private String src;
    private String id; //in case one is a referenced link

    private Link() {}

    public void setSrc(LinkSpecification ls) {
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

        private LinkFactory() {
            throw new IllegalAccessError("Utility class");
        }

        public static Link createLink(Phrase text, String src, int begin, int end) {
            Link link = new Link();
            link.text = text;
            link.src = src;
            link.begin = begin;
            link.end = end;
            link.typeOfTokens = TypesOfTokens.LINK;
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
            link.typeOfTokens = TypesOfTokens.LINK;
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
