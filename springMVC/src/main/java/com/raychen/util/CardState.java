package com.raychen.util;

/**
 * Created by raychen on 2017/2/27.
 */
public enum CardState {
    UNACTIVATE, NORMAL, SUSPEND, STOP, ERROR;

    public CardState getState(byte i){
        switch (i){
            case 0: return UNACTIVATE;
            case 1: return NORMAL;
            case 2: return SUSPEND;
            case -1: return STOP;
            default: return ERROR;
        }
    }

    public byte getValue(){
        switch (this){
            case NORMAL: return 1;
            case UNACTIVATE: return 0;
            case STOP: return -1;
            case SUSPEND: return 2;
            default: return -2;
        }
    }
}
