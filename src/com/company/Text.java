package com.company;

import com.sun.istack.internal.Nullable;

public class Text {
    public enum Properties {
        NONE, //in order to HEADER_ONE.ordinal() == 1 and so on
        HEADER_ONE,
        HEADER_TWO,
        HEADER_THREE,
        HEADER_FOUR,
        HEADER_FIVE,
        HEADER_SIX,
        ITALIC,
        BOLD,
    }

    private String wording;
    private boolean[] state = new boolean[Properties.values().length];

    public Text(String wording){
        this.wording = wording;
    }

    public void setState(Properties property, boolean value){
        state[property.ordinal()] = value;
        //TODO validate input i.e. text cannot be both HEADING_ONE and HEADING_THREE
    }

    public boolean isItalics(){
        return state[Properties.ITALIC.ordinal()];
    }

    public boolean isBold(){
        return state[Properties.BOLD.ordinal()];
    }

    public int getHeaderLevel(){ //0 - for non-heading // 1 - 6 for corresponding headers
        if (state[Properties.HEADER_ONE.ordinal()]){
            return 1;
        }
        if (state[Properties.HEADER_TWO.ordinal()]){
            return 2;
        }
        if (state[Properties.HEADER_THREE.ordinal()]){
            return 3;
        }
        if (state[Properties.HEADER_FOUR.ordinal()]){
            return 4;
        }
        if (state[Properties.HEADER_FIVE.ordinal()]){
            return 5;
        }
        if (state[Properties.HEADER_SIX.ordinal()]){
            return 6;
        }
        return 0;
    } //?????????

    @Nullable
    public String getWording(){
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{Text : ");
        for(int i = 0; i < Properties.values().length; i++){
            if (state[i]){
                sb.append(Properties.values()[i]).append(" ");
            }
        }
        sb.append(" \" ").append(wording).append(" \" }");
        return sb.toString();
    }
}
