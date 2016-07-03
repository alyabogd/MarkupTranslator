package com.company.Tokens;

public class Text extends Token {
    enum Properties {
        ITALIC,
        BOLD,
        HEADER_ONE,
        HEADER_TWO,
        HEADER_THREE,
        HEADER_FOUR,
        HEADER_FIVE,
        HEADER_SIX
    }

    private final String wording;
    private boolean[] state = new boolean[Properties.values().length];

    public Text(String wording){
        this.wording = wording;
    }

    public void setState(Properties property, boolean value){
        state[property.ordinal()] = value;
    }

    public boolean[] getState(){
        return state;
    }

    public String getWording(){
        return wording;
    }
}
