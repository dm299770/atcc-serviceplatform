package com.acv.cloud.frame.util;

import java.text.NumberFormat;

public class FloatToPercentformat {
    /**
     * 将double类型数据转换为百分比格式，并保留小数点前IntegerDigits位和小数点后FractionDigits位
     * @param f
     * @param IntegerDigits
     * @param FractionDigits
     * @return
     */
    public static String getPercentFormat(float f,int IntegerDigits,int FractionDigits){
        NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(IntegerDigits);//小数点前保留几位
        nf.setMinimumFractionDigits(FractionDigits);// 小数点后保留几位
        String str = nf.format(f);
        return str;
    }

    public static void main(String[] args){
        float f = 0.6434f;
        System.out.println(getPercentFormat(f,2,2));
    }

}
