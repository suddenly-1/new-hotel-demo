package com.suddenly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtil {

    public static String getUUID19(){
        String numStr = "" ;
        String trandStr = String.valueOf((Math.random() * 9 + 1) * 1000000);
        String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
        numStr = trandStr.toString().substring(0, 4);
        numStr = numStr + dataStr ;
        return numStr ;
    }

    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() ;
    }

}
