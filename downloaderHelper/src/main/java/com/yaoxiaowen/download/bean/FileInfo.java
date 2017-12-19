package com.yaoxiaowen.download.bean;

import android.support.annotation.IntRange;

import com.yaoxiaowen.download.Constant;

import java.io.Serializable;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:29
 * @since 1.0.0
 *
 */
public class FileInfo implements Serializable{
    private String id;
    private String downloadUrl;
    private String filePath;  //文件存放的位置
    //Todo 对于long类型，jvm将其当成两个32位int值存放，所以我认为这里应该添加 volatile
    private long size;   //文件的总尺寸
    private long downloadLocation; // 记录下载的位置

    @IntRange(from = Constant.status.WAIT, to = Constant.status.FAIL)
    private int downStatus = Constant.status.PAUSE;   //下载的状态信息

    public FileInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(long downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public int getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(int downStatus) {
        this.downStatus = downStatus;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id='" + id + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", filePath='" + filePath + '\'' +
                ", size=" + size +
                ", downloadLocation=" + downloadLocation +
                ", downStatus=" + downStatus +
                '}';
    }
}
