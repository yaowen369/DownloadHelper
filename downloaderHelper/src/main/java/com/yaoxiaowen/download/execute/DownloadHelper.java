package com.yaoxiaowen.download.execute;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.yaoxiaowen.download.Constant;
import com.yaoxiaowen.download.bean.DownloadInfo;
import com.yaoxiaowen.download.bean.RequestInfo;
import com.yaoxiaowen.download.service.DownloadService;
import com.yaoxiaowen.download.utils.LogUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 18:10
 * @since 1.0.0
 */
public class DownloadHelper {
    
    public static final String TAG = "DownloadHelper";

    private volatile static DownloadHelper SINGLETANCE;

    private static ArrayList<RequestInfo> requests = new ArrayList<>();

    private DownloadHelper(){
    }

    public static DownloadHelper getInstance(){
        if (SINGLETANCE == null){
            synchronized (DownloadHelper.class){
                if (SINGLETANCE == null){
                    SINGLETANCE = new DownloadHelper();
                }
            }
        }
        return SINGLETANCE;
    }

    /**
     * 开始执行下载任务
     * @param context
     */
    public synchronized void submit(Context context){
        if (requests.isEmpty()){
            LogUtils.i("没有下载任务可供执行");
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(Constant.SERVICE_INTENT_EXTRA, requests);
        context.startService(intent);
        requests.clear();
    }// end of "submit(..."

    public DownloadHelper addTask(String url, File file){
        return addTask(url, file, null);
    }

    public DownloadHelper addTask(String url, File file, String action){
        requests.add(createRequest(url, file, action, Constant.Request.loading));
        return this;
    }


    private RequestInfo createRequest(String url, File file, String action, int dictate){
        RequestInfo request = new RequestInfo();
        request.setDictate(dictate);
        request.setDownloadInfo(new DownloadInfo(url, file, action));
        return request;
    }
}
