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
import com.yaoxiaowen.download.FileInfo;
import com.yaoxiaowen.download.DownloadHelper;
import com.yaoxiaowen.download.sample.utils.Utils_Parse;
import com.yaoxiaowen.download.sample.utils.Utils_Toast;
import com.yaoxiaowen.download.utils.DebugUtils;

import java.io.File;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 20:23
 * @since 1.0.0
 */
public class SimpleMainActivity extends AppCompatActivity {
    
    public static final String TAG = "weny SimpleMainActivity";


    //淘宝 app 下载地址
    private static final String url = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
    private File mFile;
    private static final String BC_ACTION = "download_helper_first_action";
    private String appName = "豌豆荚.apk";

    private static final String START = "开始";
    private static final String PAUST = "暂停";


    private TextView textView;
    private ProgressBar progressBar;
    private Button btn;
    private Button deleteBtn;

    private DownloadHelper mDownloadHelper;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                switch (intent.getAction()) {
                    case BC_ACTION:{
                        /**
                         * 我们接收到的FileInfo对象，包含了下载文件的各种信息。
                         * 然后我们就可以做我们想做的事情了。
                         * 比如更新进度条，改变状态等。
                         */
                        FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);

                        float pro = (float) (fileInfo.getDownloadLocation()*1.0/ fileInfo.getSize());
                        int progress = (int)(pro*100);
                        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
                        float totalSize = fileInfo.getSize()  / 1024.0f / 1024;

                        StringBuilder sb = new StringBuilder();
                        sb.append(appName);
                        sb.append(" 当前状态: " + DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t ");
                        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n" + "( " + progress + "% )");
                        textView.setText(sb.toString());
                        progressBar.setProgress(progress);
                    }
                        break;
                }
            }
        }
    };


//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (null != intent) {
//                switch (intent.getAction()) {
//                    case BC_ACTION:{
//                        /**
//                         * 我们接收到的FileInfo对象，包含了下载文件的各种信息。
//                         * 然后我们就可以做我们想做的事情了。
//                         * 比如更新进度条，改变状态等。
//                         */
//                        com.yaoxiaowen.download.FileInfo fileInfo =
//                                (FileInfo) intent.getSerializableExtra(
//                                        com.yaoxiaowen.download.config.InnerConstant.EXTRA_INTENT_DOWNLOAD);
//
//                    }
//                    break;
//                    default:
//                }
//            }
//        }//end of "onReceive(..."
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_main);

        initData();
        initView();
        initListener();
    }

    private void initData(){
        mFile = new File(getDir(), appName);

        mDownloadHelper = DownloadHelper.getInstance();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BC_ACTION);
        registerReceiver(receiver, filter);
    }

    private void initView(){
        textView = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn = (Button) findViewById(R.id.btn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        btn.setText(START);
    }

    private void initListener(){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "开始下载");
                String content = btn.getText().toString().trim();
                if (TextUtils.equals(content, START)){
                    downFirstApk();
                }else if (TextUtils.equals(content, PAUST)){
                    pauseFirstApk();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mFile.exists()){
                    boolean result = mFile.delete();
                    String resultStr = result ? "成功" : "失败";
                    Utils_Toast.show(getBaseContext(), "删除 mFile  " + resultStr);

                } else {
                    Utils_Toast.show(getBaseContext(), "不存在 mFile ");
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
        DownloadHelper.getInstance().addTask(url, mFile, BC_ACTION).submit(SimpleMainActivity.this);

    }

    private void pauseFirstApk(){
        DownloadHelper.getInstance().pauseTask(url, mFile, BC_ACTION).submit(SimpleMainActivity.this);
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
