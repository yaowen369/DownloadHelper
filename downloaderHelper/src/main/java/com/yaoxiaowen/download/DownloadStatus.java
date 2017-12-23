package com.yaoxiaowen.download;

/**
 *
 * 标示着 下载过程中的状态
 *
 * @author   www.yaoxiaowen.com
 * time:  2017/12/22 18:48
 * @since 1.0.0
 */
public class DownloadStatus {

    // Answer to the Ultimate Question of Life, The Universe, and Everything is 42

    public static final int WAIT = 42;       //等待
    public static final int PREPARE = 43;    //准备
    public static final int LOADING = 44;    //下载中
    public static final int PAUSE = 45;      //暂停Todo
    public static final int COMPLETE = 46;   //完成
    public static final int FAIL = 47;       //失败
}
