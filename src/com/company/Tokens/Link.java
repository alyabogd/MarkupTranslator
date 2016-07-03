package com.company.Tokens;

import com.sun.istack.internal.Nullable;

public class Link extends Token {

    private Text text;
    private String src;
    private String id; //in case one is a referenced link

    @Nullable
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Text getText() {
        return text;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public static class LinkFactory{
       public static Link createLink(Text text, String src){
           Link link = new Link();
           link.text = text;
           link.src = src;
           return link;
       }

       public static Link createLink(String wording, String src){
           return LinkFactory.createLink(new Text(wording), src);
       }

       public static Link createReferencedLink(Text text, String id){
           Link link = new Link();
           link.text = text;
           link.id = id;
           return link;
       }

       public static Link createReferencedLink(String wording, String id){
           Link link = new Link();
           link.text = new Text(wording);
           link.id = id;
           return link;
       }
   } //LinkFactory
}
