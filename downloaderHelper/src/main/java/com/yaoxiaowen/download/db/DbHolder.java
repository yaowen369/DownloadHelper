package com.yaoxiaowen.download.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.yaoxiaowen.download.DownloadConstant;
import com.yaoxiaowen.download.bean.FileInfo;

import java.io.File;

/**
 * @author   www.yaoxiaowen.com
 * time:  2017/12/19 19:15
 * @since 1.0.0
 */
public class DbHolder {
    private Context context;
    private SQLiteDatabase mDb;

    public DbHolder(Context context) {
        this.context = context;
        mDb = new DbOpenHelper(context).getWritableDatabase();
    }

    public void saveFile(FileInfo downloadFile){
        if (null == downloadFile){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DownloadConstant.Db.id, downloadFile.getId());
        values.put(DownloadConstant.Db.downloadUrl, downloadFile.getDownloadUrl());
        values.put(DownloadConstant.Db.filePath, downloadFile.getFilePath());
        values.put(DownloadConstant.Db.size, downloadFile.getSize());
        values.put(DownloadConstant.Db.downloadLocation, downloadFile.getDownloadLocation());
        values.put(DownloadConstant.Db.downloadStatus, downloadFile.getDownloadStatus());

        if (has(downloadFile.getId())){
            mDb.update(DownloadConstant.Db.NAME_TABALE, values, DownloadConstant.Db.id + " = ?", new String[]{downloadFile.getId()});
        }else {
            mDb.insert(DownloadConstant.Db.NAME_TABALE, null, values);
        }

    }//end of "saveFile(..."


    public void updateState(String id, int state){
        if (TextUtils.isEmpty(id)){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(DownloadConstant.Db.downloadStatus, state);
        mDb.update(DownloadConstant.Db.NAME_TABALE, values, DownloadConstant.Db.id + " = ?", new String[]{id});
    }


    public FileInfo getFileInfo(String id){
        Cursor cursor = mDb.query(DownloadConstant.Db.NAME_TABALE, null, " " + DownloadConstant.Db.id + " = ? ", new String[]{id}, null, null, null);
        FileInfo downloadFile = null;
        while (cursor.moveToNext()){
            downloadFile = new FileInfo();
            downloadFile.setId( cursor.getString(cursor.getColumnIndex( DownloadConstant.Db.id)) );
            downloadFile.setDownloadUrl( cursor.getString(cursor.getColumnIndex( DownloadConstant.Db.downloadUrl)) );
            downloadFile.setFilePath( cursor.getString(cursor.getColumnIndex( DownloadConstant.Db.filePath)) );
            downloadFile.setSize( cursor.getLong( cursor.getColumnIndex(DownloadConstant.Db.size)) );
            downloadFile.setDownloadLocation( cursor.getLong( cursor.getColumnIndex(DownloadConstant.Db.downloadLocation)));
            downloadFile.setDownloadStatus( cursor.getInt(cursor.getColumnIndex(DownloadConstant.Db.downloadStatus)) );

            File file = new File(downloadFile.getFilePath());
            if (!file.exists()){
                deleteFileInfo(id);
                return null;
            }
        }
        cursor.close();
        return downloadFile;
    }


    /**
     * 根据id 来删除对应的文件信息
     * @param id
     */
    public void deleteFileInfo(String id){
        if (has(id)){
            mDb.delete(DownloadConstant.Db.NAME_TABALE, DownloadConstant.Db.id + " = ?", new String[]{id});
        }
    }

    private boolean has(String id){
        Cursor cursor = mDb.query(DownloadConstant.Db.NAME_TABALE, null,  " " + DownloadConstant.Db.id + " = ? ", new String[]{id}, null, null, null);
        boolean has = cursor.moveToNext();
        cursor.close();
        return has;
    }
}
