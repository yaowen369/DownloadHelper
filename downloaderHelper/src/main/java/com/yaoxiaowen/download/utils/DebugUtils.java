package com.yaoxiaowen.download.utils;



import com.yaoxiaowen.download.config.InnerConstant;
import com.yaoxiaowen.download.DownloadStatus;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/19 19:50
 * @since 1.0.0
 */
public class DebugUtils {
    public static String getStatusDesc( int status){
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

    public static String getRequestDictateDesc( int dictate){
        switch (dictate){
            case InnerConstant.Request.loading:
                return " loading ";
            case InnerConstant.Request.pause:
                return " pause ";
            default:
                return " dictate描述错误  ";
        }
    }
}
