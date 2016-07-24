package com.company.Tokens;


import com.company.Text;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Phrase extends Token implements Iterable<Text> {
    private List<Text> phrase = new LinkedList<>();

    public Phrase(List<Text> l){
        phrase = l;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(Text t){
        phrase.add(t);
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(int begin, int end) {
        this.begin = begin;
        this.end = end;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(List<Text> phrase, int begin, int end) {
        this(begin, end);
        this.phrase = phrase;
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public Phrase(Text t, int begin, int end) {
        this(begin, end);
        this.phrase.add(t);
        this.typeOfTokens = TypesOfTokens.PHRASE;
    }

    public List<Text> getPhrase() {
        return phrase;
    }

    public void addText(Text t) {
        phrase.add(t);
    }

    public void setStyle(Text.Properties style) {
        for (Text t : phrase) {
            t.setState(style, true);
        }
    }

    public void setHeader(int header) {
        if (header == 0 || header > 6)
            return;
        for (Text t : phrase) {
            t.setState(Text.Properties.values()[header], true);
        }
    }



    public String getSimpleText(){
        StringBuilder sb = new StringBuilder();
        for (Text t: phrase){
            sb.append(t.getWording()).append(" ");
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return phrase.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Text t : phrase) {
            sb.append(t.toString());
        }
        return sb.toString();
    }

    @Override
    public Iterator<Text> iterator() {
        return phrase.iterator();
    }
}
