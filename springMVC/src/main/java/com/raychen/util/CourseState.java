package com.raychen.util;

/**
 * Created by raychen on 2017/3/8.
 */
public enum CourseState {
    NORMAL,UNCHECKED,DENIED,ERROR;
    public CourseState getState(byte i){
        switch (i){
            case 0: return UNCHECKED;
            case 1: return NORMAL;
            case -1: return DENIED;
            default: return ERROR;
        }
    }

    public byte getValue(){
        switch (this){
            case NORMAL: return 1;
            case UNCHECKED: return 0;
            case DENIED: return -1;
            default: return -2;
        }
    }
}
