package com.raychen.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raychen on 2017/2/27.
 */
public class CommonUtil {

    public static int getCardLevel(double consume){
        if (consume == 0) return 0;
        else if (consume<100) return 1;
        else if (consume<1000) return 2;
        else if (consume<5000) return 3;
        else if (consume<10000) return 4;
        else return 5;
    }

    public static double getDiscountValue(double money, int level){
        return money*(1-level*0.05);
    }

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
