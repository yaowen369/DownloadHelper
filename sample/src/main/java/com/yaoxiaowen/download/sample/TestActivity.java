package com.yaoxiaowen.download.sample;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yaoxiaowen.download.utils.LogUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TestActivity extends AppCompatActivity {


    public static final String TAG = "TestActivity";

    private String mSelectUrl = Constanst.TONG_CHENG_URL;
    private String mSelectName = Constanst.TONG_CHENG_NAME;

    private EditText mTestInputEt;
    private Button mTestBtn;
    private TextView mTestFirstTv;
    private TextView mTestSecondTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mTestInputEt = (EditText) findViewById(R.id.testInputEt);
        mTestBtn = (Button) findViewById(R.id.testBtn);
        mTestFirstTv = (TextView) findViewById(R.id.testFirstTv);
        mTestSecondTv = (TextView) findViewById(R.id.testSecondTv);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.testBtn:
                setSelectAndDisplay();
                testRedir();
                break;
        }

    }


    private void setSelectAndDisplay(){
        String input = mTestInputEt.getText().toString().trim();
        switch (input){
            case "0":
                mSelectUrl = Constanst.TONG_CHENG_URL;
                mSelectName = Constanst.TONG_CHENG_NAME;
                break;
            case "1":
                mSelectUrl = Constanst.WAN_DOU_JIA_URL;
                mSelectName = Constanst.WAN_DOU_JIA_NAME;
                break;
            case "2":
                mSelectUrl = Constanst.MEI_TUAN_URL;
                mSelectName = Constanst.MEI_TUAN_NAME;
                break;
            case "3":
                mSelectUrl = Constanst.TRAIN_12306_URL;
                mSelectName = Constanst.TRAIN_12306_NAME;
                break;
                default:
                    LogUtils.i(TAG, " 你输入了其他数字 ");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("selectUrl = " + mSelectUrl)
                .append("\t selectName=" + mSelectName);

        LogUtils.i(TAG, sb.toString());

        mTestFirstTv.setText(sb.toString());
    }


    private void testRedir(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                StringBuilder sb = new StringBuilder();

                try {
                    url = new URL(mSelectUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    final String realUrl = conn.getURL().toString();
                    long totalSize = conn.getContentLength();

                    sb.append("realUrl = " + realUrl)
                            .append("\t totalSize=" + totalSize);


                    int responseCode = conn.getResponseCode();
                    String responseMsg = conn.getResponseMessage();

                    if (responseCode == 302){
                        String location = conn.getHeaderField("Location");
                        sb.append("\t location=" + location);
                    }

                    sb.append("\t responseCode=" + responseCode)
                            .append("\t responseMsg=" + responseMsg);

                    conn.disconnect();

                }catch (Exception e){
                    LogUtils.i(TAG, "  发生错误 ");
                    sb.append(" 发生错误 ");
                    e.printStackTrace();
                }

                LogUtils.i(TAG, sb.toString());
//                mTestSecondTv.setText(sb.toString());
            }
        }).start();
    }
}
