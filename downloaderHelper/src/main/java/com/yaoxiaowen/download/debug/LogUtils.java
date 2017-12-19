package com.yaoxiaowen.download.debug;

import android.util.Log;

/**
 * author：yaowen on 17/5/14 12:54
 * email：yaowen369@gmail.com
 * www.yaoxiaowen.com
 *
 * 在实际使用当中要注意,Log的TAG太长了,会输不出来.
 * 官方要求是不超过23个字符的。
 *  http://blog.csdn.net/voiceofnet/article/details/49866047
 *
 *  另外,使用Log工具不要使用v,d级别的,原因是因为部分型号手机会屏蔽掉 v,d等低级别的log,导致无法输出
 *  因为v,d级别的log,本工具中使用了  Deprecated 标记
 */

public class LogUtils {

    public static final String PREFIX = "weny ";

    /**
     *  Log.v 级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    @Deprecated
    public static void v(String msg) {
        Log.v(getTAG(), msg);
    }

    /**
     *  Log.v(TAG, msg)
     * @param TAG
     * @param msg
     */
    @Deprecated
    public static void v(String TAG, String msg) {
        Log.v(PREFIX + TAG, msg);
    }

    /**
     *  Log.d级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    @Deprecated
    public static void d(String msg) {
        Log.d(getTAG(), msg);
    }


    /**
     *  Log.d(TAG, msg);
     * @param msg
     */
    @Deprecated
    public static void d(String TAG, String msg) {
        String NewTAG = PREFIX + TAG;
        Log.d(NewTAG, msg);
    }

    /**
     *  Log.i 级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    public static void i(String msg) {
        Log.i(getTAG(), msg);
    }

    /**
     *  Log.i(TAG, msg);
     * @param msg
     */
    public static void i(String TAG, String msg) {
        Log.i(PREFIX + TAG, msg);
    }


    /**
     * Log.w 级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    public static void w(String msg) {
        Log.w(getTAG(), msg);
    }

    /**
     * Log.w(TAG, msg)
     * @param TAG
     * @param msg
     */
    public static void w(String TAG, String msg) {
        Log.w(PREFIX + TAG, msg);
    }

    /**
     * Log.e 级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    public static void e(String msg) {
        Log.e(getTAG(), msg);
    }

    /**
     * Log.e(TAG, msg);
     * @param TAG
     * @param msg
     */
    public static void e(String TAG, String msg) {
        Log.e(PREFIX + TAG, msg);
    }

    /**
     * Log.wft 级别,TAG使用调用栈中计算出的的TAG, 形式:PREFIX + className + methodName() + lineNum
     * @param msg
     */
    public static void wtf(String msg){
        Log.wtf(getTAG(), msg);
    }

    /**
     *  Log.wtf(TAG, msg)
     * @param TAG
     * @param msg
     */
    public static void wtf(String TAG, String msg){
        Log.wtf(PREFIX + TAG, msg);
    }

    /**
     *  返回值是我们默认的TAG, PREFIX + className + methodName() + lineNum;
     * @return  eg : weny DispatchActivity initData() 37
     *          eg : weny DispatchActivity$1 onClick() 58
     *          eg:  weny DispatchActivity$InnerMyOnClickView onClick() 69
     */
    private static String getTAG() {
        StringBuilder sb = new StringBuilder();

        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        sb.append(PREFIX + " ");

        StringBuilder classNameSb = new StringBuilder();

        /**
         * 我们在这里并没有使用 比较简单的方式来获取。
         * StackTraceElement[] stackTrace = new Throwable().getStackTrace();
         * stackTrace[3].getClassName()
         * stackTrace[3].getMethodName( )
         *
         * 原因是那样简单写,没办法 正确的得到 内部类的 一些信息。
         * 所以我们遍历了调用栈,采用硬编码来判断
         */
        for (StackTraceElement element : stackTrace){
            classNameSb.delete(0, classNameSb.length());
            classNameSb.append(element.getClassName());
            if (classNameSb.indexOf("com.yaoxiaowen.download")>=0
                    && !classNameSb.toString().contains("LogUtils")){
                int index = classNameSb.lastIndexOf(".");
                sb.append(classNameSb.subSequence(index+1, classNameSb.length()));
                sb.append(" ");
                sb.append(element.getMethodName() + "()");
                sb.append(" " + element.getLineNumber() + " ");
                break;
            }
        }

        return sb.toString();
    }

}
