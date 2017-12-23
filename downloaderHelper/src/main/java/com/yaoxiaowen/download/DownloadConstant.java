package com.yaoxiaowen.download;


/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/23 13:34
 * @since 1.0.0
 */
public class DownloadConstant {
    /**
     * 下载过程会通过发送广播, 广播通过intent携带文件数据的 信息。
     * intent 的key值就是该字段
     * eg : FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
     */
    public static final String EXTRA_INTENT_DOWNLOAD = "yaoxiaowen_download_extra";
}
