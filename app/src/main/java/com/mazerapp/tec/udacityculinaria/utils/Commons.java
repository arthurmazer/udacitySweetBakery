package com.mazerapp.tec.udacityculinaria.utils;

public class Commons {


    public static String getStringDouble(double value){
        int intValue = (int) value;

        if (value - intValue == 0){
            return String.valueOf(intValue);
        }else{
            return String.valueOf(value);
        }

    }


}
