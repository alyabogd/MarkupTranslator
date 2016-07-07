package com.company.Tokens.Links;

import com.company.Tokens.Text;
import com.company.Tokens.Token;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Link extends Token {

    private List<Text> text;
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

    public List<Text> getText() {
        return text;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public static class LinkFactory {
        public static Link createLink(List<Text> text, String src) {
            Link link = new Link();
            link.text = text;
            link.src = src;
            return link;
        }

        public static Link createLink(Text text, String src){
            return LinkFactory.createLink(Collections.singletonList(text), src);
        }

        public static Link createLink(String wording, String src) {
            return LinkFactory.createLink(new Text(wording), src);
        }

        public static Link createReferencedLink(List<Text> text, String id){
            Link link = new Link();
            link.text = text;
            link.id = id;
            return link;
        }

        public static Link createReferencedLink(Text text, String id) {
            return LinkFactory.createReferencedLink(Collections.singletonList(text), id);
        }

        public static Link createReferencedLink(String wording, String id) {
            return LinkFactory.createReferencedLink(new Text(wording), id);
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
