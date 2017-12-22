package com.yaoxiaowen.download.sample.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * YaoWen(43194) create at tongcheng work pc,
 * time:  2017/4/17 15:10  qq:2669932513
 */
public class Utils_Parse {
    /**
     * 保留两位小数。四舍五入保留。
     * 输入结果 实例：
     * 0.00258 -> 0.0
     * 100 -> 100.0
     * 12345.679 -> 12345.68
     */
    public static float getTwoDecimals(float flo) {
//        return (float)(Math.round(flo*100)/100);
        float result = -1;

        BigDecimal b = new BigDecimal(flo);
        result = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return result;
    }

    public static String getTwoDecimalsStr(float flo) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(flo);
    }

    //保留一位小数
    public static float getOneDecimals(float flo){
        float result = -1;
        BigDecimal b = new BigDecimal(flo);
        result = b.setScale(1,  BigDecimal.ROUND_HALF_UP).floatValue();
        return result;
    }
    public static String getOneDecimalsStr(float flo){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(flo);
    }
}
