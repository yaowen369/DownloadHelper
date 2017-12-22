package com.yaoxiaowen.download.utils;


import android.support.annotation.IntRange;

import com.yaoxiaowen.download.DownloadConstant;
import com.yaoxiaowen.download.DownloadStatus;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/19 19:50
 * @since 1.0.0
 */
public class DebugUtils {
    public static String getStatusDesc(@IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL) int status){
        switch (status){
            case DownloadStatus.WAIT:
                return " wait ";
            case DownloadStatus.PREPARE:
                return " prepare ";
            case DownloadStatus.LOADING:
                return " loading ";
            case DownloadStatus.PAUSE:
                return " pause ";
            case DownloadStatus.COMPLETE:
                return " complete ";
            case DownloadStatus.FAIL:
                return " fail ";
            default:
                return "  错误的未知状态 ";
        }
    }

    public static String getRequestDictateDesc(@IntRange(from = DownloadConstant.Request.loading, to = DownloadConstant.Request.pause) int dictate){
        switch (dictate){
            case DownloadConstant.Request.loading:
                return " loading ";
            case DownloadConstant.Request.pause:
                return " pause ";
            default:
                return " dictate描述错误  ";
        }
    }
}
