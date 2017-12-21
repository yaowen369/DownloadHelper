package com.yaoxiaowen.download.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yaoxiaowen.download.Constant;
import com.yaoxiaowen.download.bean.FileInfo;
import com.yaoxiaowen.download.execute.DownloadHelper;
import com.yaoxiaowen.download.utils.LogUtils;

import java.io.File;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 20:23
 * @since 1.0.0
 */
public class MainActivity extends AppCompatActivity {
    
    public static final String TAG = "weny MainActivity";


    //网易云音乐的下载地址
    private static final String firstUrl = "http://gdown.baidu.com/data/wisegame/e44001b8cb260aa5/wangyiyunyinle_103.apk";
    private File firstFile;

    private DownloadHelper mDownloadHelper;

    private static final String FIRST_ACTION = "download_helper_first_action";

    private TextView firstTitle;
    private ProgressBar firstProgressBar;
    private Button firstBtn;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "接受到广播 intent = " + intent);
            if (null != intent){
                switch (intent.getAction()){
                    case FIRST_ACTION:
                        FileInfo firstFileInfo = (FileInfo)intent.getSerializableExtra(Constant.DOWNLOAD_EXTRA);
                        float firstPro = (float) (firstFileInfo.getDownloadLocation()*1.0/ firstFileInfo.getSize());
                        int firstProgress = (int)(firstPro*100);
                        float firstDownSize = firstFileInfo.getDownloadLocation() / 1024.0f / 1024;
                        float firstSize = firstFileInfo.getSize()  / 1024.0f / 1024;


                        firstTitle.setText("网易云apk正在下载\n" + firstDownSize + "M/" + firstSize + "M\n" + "( " + firstProgress + "% )");

                        firstProgressBar.setProgress(firstProgress);
                        break;

                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void initListener(){

        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "开始下载");
                downFirstApk();
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
        mDownloadHelper.addTask(firstUrl, firstFile, FIRST_ACTION).submit(MainActivity.this);
        LogUtils.i(TAG, "downFirstApk() -> MainActivity.this=" + MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
