package com.raychen.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raychen on 2017/2/27.
 */
public class CommonUtil {

    public static Integer generateCode(int len){
        int tmp = (int) (Math.random()*9) + 1;
        for (int i = 0; i < len-1; i++) {
            int s = (int) (Math.random()*10);
            tmp = tmp * 10 + s;
        }
        return tmp;
    }

    public static boolean judgeOneYear(String lastedChargeTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date()));
        return false;
    }

    public static void main(String[] args) {
        judgeOneYear("");
    }
}
