package com.company.tokens;


import com.company.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Phrase extends Token implements Iterable<Text> {
    private List<Text> texts = new LinkedList<>();

    public Phrase(List<Text> l){
        texts = l;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(Text t){
        texts.add(t);
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(int begin, int end) {
        this.begin = begin;
        this.end = end;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(List<Text> texts, int begin, int end) {
        this(begin, end);
        this.texts = texts;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(Text t, int begin, int end) {
        this(begin, end);
        this.texts.add(t);
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public List<Text> getTexts() {
        return texts;
    }

    public void addText(Text t) {
        texts.add(t);
    }

    public void setStyle(Text.Properties style) {
        for (Text t : texts) {
            t.setState(style, true);
        }
    }

    public void setHeader(int header) {
        if (header == 0 || header > 6)
            return;
        for (Text t : texts) {
            t.setState(Text.Properties.values()[header], true);
        }
    }



    public String getSimpleText(){
        StringBuilder sb = new StringBuilder();
        for (Text t: texts){
            sb.append(t.getWording()).append(" ");
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Text t : texts) {
            sb.append(t.toString());
        }
        return sb.toString();
    }

    @Override
    public Iterator<Text> iterator() {
        return texts.iterator();
    }
}
