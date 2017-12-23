package com.yaoxiaowen.download.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yaoxiaowen.download.DownloadConstant;
import com.yaoxiaowen.download.DownloadStatus;
import com.yaoxiaowen.download.FileInfo;
import com.yaoxiaowen.download.DownloadHelper;
import com.yaoxiaowen.download.sample.utils.Utils_Parse;
import com.yaoxiaowen.download.utils.DebugUtils;

import java.io.File;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/20 20:23
 * @since 1.0.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    
    public static final String TAG = "weny SimpleMainActivity";


    //Todo 同程旅游的下载地址 ：     "http://s.ly.com/tTV79";
    //为什么下载不下来，这个 网页做了什么, 回头要研究

    //淘宝 app 下载地址
    private static final String firstUrl = "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk";
    private File firstFile;
    private String firstName = "豌豆荚.apk";
    private static final String FIRST_ACTION = "download_helper_first_action";


    //美团 app 下载地址
    private static final String secondUrl = "http://www.meituan.com/mobile/download/meituan/android/meituan?from=new";
    private File secondFile;
    private String secondName = "美团.apk";
    private static final String SECOND_ACTION = "download_helper_second_action";

    // 12306 APP 下载地址
    private static final String thirdUrl = "http://dynamic.12306.cn/otn/appDownload/androiddownload";
    private File thirdFile;
    private String thirdName = "12306.apk";
    private static final String THIRD_ACTION = "download_helper_third_action";

    private DownloadHelper mDownloadHelper;
    private File dir;

    private static final String START = "开始";
    private static final String PAUST = "暂停";


    private static int textColor1 = Color.parseColor("#333333");
    private static int textColor2 = Color.parseColor("#666666");
    private static int textColor3 = Color.parseColor("#999999");
    private static int textColorBlock = Color.parseColor("#000000");
    private static int textColorRandarRed = Color.parseColor("#FF0000");
    private static int textColorGreen = Color.parseColor("#46BCFF");

    private TextView firstTitle;
    private ProgressBar firstProgressBar;
    private Button firstBtn;

    private TextView secondTitle;
    private ProgressBar secondProgressBar;
    private Button secondBtn;

    private TextView thirdTitle;
    private ProgressBar thirdProgressBar;
    private Button thirdBtn;

    private Button deleteAllBtn;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent){
                switch (intent.getAction()){
                    case FIRST_ACTION: {
                        FileInfo firstFileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        updateTextview(firstTitle, firstProgressBar, firstFileInfo, firstName, firstBtn);
                    }
                    break;
                    case SECOND_ACTION: {
                        FileInfo secondFileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        updateTextview(secondTitle, secondProgressBar, secondFileInfo, secondName, secondBtn);
                    }
                    break;
                    case THIRD_ACTION: {
                        FileInfo thirdFileInfo = (FileInfo) intent.getSerializableExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD);
                        updateTextview(thirdTitle, thirdProgressBar, thirdFileInfo, thirdName, thirdBtn);
                    }
                    break;
                    default:

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
        firstFile = new File(getDir(), firstName);
        secondFile = new File(getDir(), secondName);
        thirdFile = new File(getDir(), thirdName);

        mDownloadHelper = DownloadHelper.getInstance();

        IntentFilter filter = new IntentFilter();
        filter.addAction(FIRST_ACTION);
        filter.addAction(SECOND_ACTION);
        filter.addAction(THIRD_ACTION);

        registerReceiver(receiver, filter);
    }

    private void initView(){
        firstTitle = (TextView) findViewById(R.id.firstTitle);
        firstProgressBar = (ProgressBar) findViewById(R.id.firstProgressBar);
        firstBtn = (Button) findViewById(R.id.firstBtn);
        firstBtn.setText(START);

        secondTitle = (TextView) findViewById(R.id.secondTitle);
        secondProgressBar = (ProgressBar) findViewById(R.id.secondProgressBar);
        secondBtn = (Button) findViewById(R.id.secondBtn);
        secondBtn.setText(START);

        thirdTitle = (TextView) findViewById(R.id.thirdTitle);
        thirdProgressBar = (ProgressBar) findViewById(R.id.thirdProgressBar);
        thirdBtn = (Button) findViewById(R.id.thirdBtn);
        thirdBtn.setText(START);

        deleteAllBtn = (Button) findViewById(R.id.deleteAllBtn);
    }

    private void initListener(){
        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);

        deleteAllBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.firstBtn:
                onFirstApkClick();
                break;
            case R.id.secondBtn:
                onSecondApkClick();
                break;
            case R.id.thirdBtn:
                onThirdApkClick();
                break;
            case R.id.deleteAllBtn:
                deleteAllFile();
                break;
        }
    }



    private File getDir(){
        if (dir!=null && dir.exists()){
            return dir;
        }

        dir = new File(getExternalCacheDir(), "download");
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }

    private void onFirstApkClick(){
        String firstContent = firstBtn.getText().toString().trim();
        if (TextUtils.equals(firstContent, START)){
            mDownloadHelper.addTask(firstUrl, firstFile, FIRST_ACTION).submit(MainActivity.this);
            firstBtn.setText(PAUST);
            firstBtn.setBackgroundResource(R.drawable.shape_btn_orangle);

        }else {
            mDownloadHelper.pauseTask(firstUrl, firstFile, FIRST_ACTION).submit(MainActivity.this);
            firstBtn.setText(START);
            firstBtn.setBackgroundResource(R.drawable.shape_btn_blue);
        }
    }

    private void onSecondApkClick(){
        String secondContent = secondBtn.getText().toString().trim();
        if (TextUtils.equals(secondContent, START)){
            mDownloadHelper.addTask(secondUrl, secondFile, SECOND_ACTION).submit(MainActivity.this);
            secondBtn.setText(PAUST);
            secondBtn.setBackgroundResource(R.drawable.shape_btn_orangle);
        }else {
            mDownloadHelper.pauseTask(secondUrl, secondFile, SECOND_ACTION).submit(MainActivity.this);
            secondBtn.setText(START);
            secondBtn.setBackgroundResource(R.drawable.shape_btn_blue);
        }
    }

    private void onThirdApkClick(){
        String thirdContent = thirdBtn.getText().toString().trim();
        if (TextUtils.equals(thirdContent, START)){
            mDownloadHelper.addTask(thirdUrl, thirdFile, THIRD_ACTION).submit(MainActivity.this);
            thirdBtn.setText(PAUST);
            thirdBtn.setBackgroundResource(R.drawable.shape_btn_orangle);
        }else {
            mDownloadHelper.pauseTask(thirdUrl, thirdFile, THIRD_ACTION).submit(MainActivity.this);
            thirdBtn.setText(START);
            thirdBtn.setBackgroundResource(R.drawable.shape_btn_blue);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    private void updateTextview(TextView textView, ProgressBar progressBar,  FileInfo fileInfo, String fileName, Button btn){
        float pro = (float) (fileInfo.getDownloadLocation()*1.0/ fileInfo.getSize());
        int progress = (int)(pro*100);
        float downSize = fileInfo.getDownloadLocation() / 1024.0f / 1024;
        float totalSize = fileInfo.getSize()  / 1024.0f / 1024;

//        StringBuilder sb = new StringBuilder();
        ////        sb.append(fileName  + "\t  ( " + progress + "% )" + "\n");
//        sb.append("状态: " + DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t ");
//        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n");

        // 我们将字体颜色设置的好看一些而已
        int count = 0;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(fileName);
        sb.setSpan(new ForegroundColorSpan(textColorBlock), 0, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();
        sb.append("\t  ( " + progress + "% )" + "\n");
        sb.setSpan(new ForegroundColorSpan(textColor3), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();
        sb.append("状态:");
        sb.setSpan(new ForegroundColorSpan(textColor2), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(DebugUtils.getStatusDesc(fileInfo.getDownloadStatus()) + " \t \t\t \t\t\t");
        sb.setSpan(new ForegroundColorSpan(textColorGreen), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        count = sb.length();

        sb.append(Utils_Parse.getTwoDecimalsStr(downSize) + "M/" + Utils_Parse.getTwoDecimalsStr(totalSize) + "M\n");
        sb.setSpan(new ForegroundColorSpan(textColorRandarRed), count, sb.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);



        textView.setText(sb);


        progressBar.setProgress(progress);

        if (fileInfo.getDownloadStatus() == DownloadStatus.COMPLETE){
            btn.setText("下载完成");
            btn.setBackgroundColor(0xff5c0d);
        }
    }

    private void deleteAllFile(){
       if (firstFile!=null && firstFile.exists()){
           firstFile.delete();
       }

       if (secondFile!=null && secondFile.exists()){
           secondFile.delete();
       }

       if (thirdFile!=null && thirdFile.exists()){
           thirdFile.delete();
       }
    }
}
