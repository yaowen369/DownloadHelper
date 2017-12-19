package com.yaoxiaowen.download;

import com.yaoxiaowen.download.bean.FileInfo;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/18 21:23
 * @since 1.0.0
 */
public class Constant {

    public static class status{
        //标示下载过程中 各种 状态
        public static final int WAIT = 0;
        public static final int PREPARE = 1;
        public static final int LOADING = 2;
        public static final int PAUSE = 3;
        public static final int COMPLETE = 4;
        public static final int FAIL = 5;
    }

    //db 数据库中用到的字段
    public static class db{
        public static final String id = "id";
        public static final String downloadUrl = "downloadUrl";
        public static final String filePath = "filePath";
        public static final String size = "size";
        public static final String downloadLocation = "downloadLocation";
        public static final String downloadStatus = "downloadStatus";

        public static final String NAME_TABALE = "download_info";
        public static final String NAME_DB = "download.db";
    }

    //用于intent数据的传递
    public static final String DOWNLOAD_EXTRA = "downloadExtra";
}
