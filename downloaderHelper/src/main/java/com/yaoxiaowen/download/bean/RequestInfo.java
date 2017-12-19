package com.yaoxiaowen.download.bean;


import android.support.annotation.IntRange;

import java.io.Serializable;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:29
 * @since 1.0.0
 */
public class RequestInfo implements Serializable{

    public static final int REQUEST_START = 0; //请求的下载状态
    public static final int REQUEST_PAUSE = 1; //请求的暂停状态

    @IntRange(from = REQUEST_START, to = REQUEST_PAUSE)
    private int dictate;   //下载的控制状态

    private DownloadInfo downloadInfo;

    public RequestInfo() {
    }

    public int getDictate() {
        return dictate;
    }

    public void setDictate(int dictate) {
        this.dictate = dictate;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }


    @Override
    public String toString() {
        return "RequestInfo{" +
                "dictate=" + dictate +
                ", downloadInfo=" + downloadInfo +
                '}';
    }
}
