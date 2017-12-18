package com.yaoxiaowen.download;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:23
 * @since 1.0.0
 */
public class Constant {

    //标示下载状态
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_PREPARE = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_PAUSE = 3;
    public static final int STATUS_COMPLETE = 4;
    public static final int STATUS_FAIL = 5;

    //用于intent数据的传递
    public static final String DOWNLOAD_EXTRA = "downloadExtra";
}
