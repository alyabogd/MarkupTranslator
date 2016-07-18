package com.company.Tokens;

import com.sun.istack.internal.Nullable;

public class Image extends Token{

    private Text altText;
    private String src;
    private String id; //in case one has a referenced src

    @Nullable
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Text getAltText() {
        return altText;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public static class ImageFactory{
        public static Image createImage(Text altText, String src, int begin, int end){
            Image image = new Image();
            image.altText = altText;
            image.src = src;
            image.begin = begin;
            image.end = end;
            return image;
        }

        public static Image createImage(String altWording, String src, int begin, int end){
            return createImage(new Text(altWording), src, begin, end);
        }

        public static Image createImage(String src, int begin, int end){
            return createImage("", src, begin, end);
        }

        public static Image createReferencedImage(Text altText, String id, int begin, int end){
            Image image = new Image();
            image.altText = altText;
            image.id = id;
            image.begin = begin;
            image.end = end;
            return image;
        }

        public static Image createReferencedImage(String altWording, String id, int begin, int end) {
            return createReferencedImage(new Text(altWording), id, begin, end);
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
