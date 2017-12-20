package com.yaoxiaowen.download.bean;


import android.support.annotation.IntRange;

import com.yaoxiaowen.download.Constant;

import java.io.Serializable;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:29
 * @since 1.0.0
 */
public class RequestInfo implements Serializable{


    @IntRange(from = Constant.Request.loading, to = Constant.Request.pause)
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
