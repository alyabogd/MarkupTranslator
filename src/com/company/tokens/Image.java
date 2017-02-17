package com.company.tokens;

import com.company.Text;
import com.company.tokens.links.LinkSpecification;
import com.sun.istack.internal.Nullable;

public class Image extends Token{

    private Phrase altText;
    private String src;
    private String id; //in case one has a referenced src

    private Image() {}

    @Nullable
    public String getSrc() {
        return src;
    }

    public void setSrc(LinkSpecification ls) {
        if (ls.getId().equals(this.id)) {
            this.src = ls.getAdress();
        } else {
            throw new IllegalArgumentException("id are not the same");
        }
    }

    public Phrase getAltText() {
        return altText;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public static class ImageFactory{
        public static Image createImage(Phrase altText, String src, int begin, int end){
            Image image = new Image();
            image.altText = altText;
            image.src = src;
            image.begin = begin;
            image.end = end;
            image.typeOfTokens = TypesOfTokens.IMAGE;
            return image;
        }

        public static Image createImage(String altWording, String src, int begin, int end){
            return createImage(new Phrase(new Text(altWording)), src, begin, end);
        }

        public static Image createImage(String src, int begin, int end){
            return createImage("", src, begin, end);
        }

        public static Image createReferencedImage(Phrase altText, String id, int begin, int end){
            Image image = new Image();
            image.altText = altText;
            image.id = id;
            image.begin = begin;
            image.end = end;
            image.typeOfTokens = TypesOfTokens.IMAGE;
            return image;
        }

        public static Image createReferencedImage(String altWording, String id, int begin, int end) {
            return createReferencedImage(new Phrase(new Text(altWording)), id, begin, end);
        }

        public static Image createReferencedImage(String id, int begin, int end) {
            return createReferencedImage("", id, begin, end);
        }
    }

    @Override
    public String toString() {
        String s =  "{ Image: alttext - " + altText + "; ";
        if (src == null){
            s += "[id " + id + "]} ";
        } else {
            s += "[src " + src + "]} ";
        }
        return s;
    }
}
