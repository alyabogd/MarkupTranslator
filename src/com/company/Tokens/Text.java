package com.company.Tokens;

public class Text extends Token {
    public enum Properties {
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
        //TODO validate input i.e. text cannot be both HEADING_ONE and HEADING_THREE
    }

    public boolean[] getState(){
        return state;
    }

    public String getWording(){
        return wording;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Text : ");
        for(int i = 0; i < Properties.values().length; i++){
            if (state[i]){
                sb.append(Properties.values()[i]);
            }
        }
        sb.append(" \" ").append(wording).append(" \" }");
        return sb.toString();
    }
}
