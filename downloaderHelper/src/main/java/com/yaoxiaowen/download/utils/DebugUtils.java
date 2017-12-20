package com.yaoxiaowen.download.utils;


import android.support.annotation.IntRange;

import com.yaoxiaowen.download.Constant;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/19 19:50
 * @since 1.0.0
 */
public class DebugUtils {
    public static String getStatusDesc(@IntRange(from = Constant.Status.WAIT, to = Constant.Status.UNKNOWN) int status){
        switch (status){
            case Constant.Status.WAIT:
                return " wait ";
            case Constant.Status.PREPARE:
                return " prepare ";
            case Constant.Status.LOADING:
                return " loading ";
            case Constant.Status.PAUSE:
                return " pause ";
            case Constant.Status.COMPLETE:
                return " complete ";
            case Constant.Status.FAIL:
                return " fail ";
            case Constant.Status.UNKNOWN:
                return " unknown ";
            default:
                return "  错误的未知状态 ";
        }
    }
}
