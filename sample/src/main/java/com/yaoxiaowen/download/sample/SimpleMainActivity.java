package com.yaoxiaowen.download.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yaoxiaowen.download.DownloadConstant;
import com.yaoxiaowen.download.DownloadStatus;
import com.yaoxiaowen.download.bean.FileInfo;
import com.yaoxiaowen.download.execute.DownloadHelper;
import com.yaoxiaowen.download.sample.utils.Utils_Parse;
import com.yaoxiaowen.download.sample.utils.Utils_Toast;
import com.yaoxiaowen.download.utils.DebugUtils;
import com.yaoxiaowen.download.utils.LogUtils;

import java.io.File;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 20:23
 * @since 1.0.0
 */
public class SimpleMainActivity extends AppCompatActivity {
    
    public static final String TAG = "weny SimpleMainActivity";


    //同程旅游 app 下载地址
    private static final String firstUrl = "http://gdown.baidu.com/data/wisegame/e44001b8cb260aa5/wangyiyunyinle_103.apk";
    private File firstFile;
    private static final String FIRST_ACTION = "download_helper_first_action";





    private DownloadHelper mDownloadHelper;



    private static final String START = "开始";
    private static final String PAUST = "暂停";



    private TextView firstTitle;
    private ProgressBar firstProgressBar;
    private Button firstBtn;
    private Button deleteAllBtn;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case FIRST_ACTION:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_main);

        initData();
        initView();
        initListener();
    }

    private void initData(){
        firstFile = new File(getDir(), "网易云.apk");

        mDownloadHelper = DownloadHelper.getInstance();

        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);

        registerReceiver(receiver, filter);
    }

    private void initView(){
        firstTitle = (TextView) findViewById(R.id.firstTitle);
        firstProgressBar = (ProgressBar) findViewById(R.id.firstProgressBar);
        firstBtn = (Button) findViewById(R.id.firstBtn);
        deleteAllBtn = (Button) findViewById(R.id.deleteAllBtn);

        firstBtn.setText(START);
    }

    private void initListener(){

        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "开始下载");
                String content = firstBtn.getText().toString().trim();
                if (TextUtils.equals(content, START)){
                    downFirstApk();
                }else if (TextUtils.equals(content, PAUST)){
                    pauseFirstApk();
                }
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firstFile.exists()){
                    boolean result = firstFile.delete();
                    String resultStr = result ? "成功" : "失败";
                    Utils_Toast.show(getBaseContext(), "删除 firstFile  " + resultStr);

                } else {
                    Utils_Toast.show(getBaseContext(), "不存在 firstFile ");
                }
            }
        });
    }


    private File getDir(){
        File dir = new File(getExternalCacheDir(), "download");
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    private void downFirstApk(){
//        mDownloadHelper.addTask(firstUrl, firstFile, FIRST_ACTION).submit(this);
        mDownloadHelper.addTask(firstUrl, firstFile, FIRST_ACTION).submit(SimpleMainActivity.this);
    }

    private void pauseFirstApk(){
        LogUtils.i(TAG, "pauseFirstApk() -> SimpleMainActivity.this=" + SimpleMainActivity.this);
        mDownloadHelper.pauseTask(firstUrl, firstFile, FIRST_ACTION).submit(SimpleMainActivity.this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    private void updateTextview(TextView textView, ProgressBar progressBar,  FileInfo fileInfo, String fileName){
        float pro = (float) (fileInfo.getDownloadLocation()*1.0/ fileInfo.getSize());
        int progress = (int)(pro*100);
        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
        float totalSize = fileInfo.getSize()  / 1024.0f / 1024;

        StringBuilder sb = new StringBuilder();
        sb.append(fileName);
        sb.append(" 当前状态: " + DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t ");
        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n" + "( " + progress + "% )");
        textView.setText(sb.toString());
        progressBar.setProgress(progress);
    }
}
