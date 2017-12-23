package com.yaoxiaowen.download.db;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yaoxiaowen.download.config.InnerConstant;
import com.yaoxiaowen.download.utils.LogUtils;


/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/19 19:15
 * @since 1.0.0
 */
public class DbOpenHelper extends SQLiteOpenHelper{
    public static final String TAG = "DbOpenHelper";

    public DbOpenHelper(Context context) {
        super(context, InnerConstant.Db.NAME_DB, null, getVersionCode(context));
    }

    private static int getVersionCode(Context context){
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        }catch (Exception e){
            LogUtils.e(TAG, "创建数据库失败 ");
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String info = "create table if not exists " + InnerConstant.Db.NAME_TABALE
                + "(" +
                InnerConstant.Db.id + " varchar(500)," +
                InnerConstant.Db.downloadUrl + " varchar(100)," +
                InnerConstant.Db.filePath + " varchar(100)," +
                InnerConstant.Db.size + " integer," +
                InnerConstant.Db.downloadLocation + " integer," +
                InnerConstant.Db.downloadStatus + " integer)";

        LogUtils.i(TAG, "onCreate() -> info=" + info);
        db.execSQL(info);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //对于下载来讲，其实是不存在这种升级数据库的业务的.所以我们直接删除重新建表
        db.execSQL("drop table if exists " + InnerConstant.Db.NAME_TABALE);
        onCreate(db);
    }
}
