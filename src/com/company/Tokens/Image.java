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
        public static Image createImage(Text altText, String src){
            Image image = new Image();
            image.altText = altText;
            image.src = src;
            return image;
        }

        public static Image createImage(String altWording, String src){
            return createImage(new Text(altWording), src);
        }

        public static Image createImage(String src){
            return createImage("", src);
        }

        public static Image createReferencedImage(Text altText, String id){
            Image image = new Image();
            image.altText = altText;
            image.id = id;
            return image;
        }

        public static Image createReferencedImage(String altWording, String id) {
            return createReferencedImage(new Text(altWording), id);
        }

        public static Image createReferencedImage(String id) {
            return createReferencedImage("", id);
        }
    }
}
