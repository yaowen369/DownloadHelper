package com.yaoxiaowen.download.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.session.PlaybackState;
import android.text.TextUtils;

import com.yaoxiaowen.download.Constant;
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
        values.put(Constant.db.id, downloadFile.getId());
        values.put(Constant.db.downloadUrl, downloadFile.getDownloadUrl());
        values.put(Constant.db.filePath, downloadFile.getFilePath());
        values.put(Constant.db.size, downloadFile.getSize());
        values.put(Constant.db.downloadLocation, downloadFile.getDownloadLocation());
        values.put(Constant.db.downloadStatus, downloadFile.getDownStatus());

        if (has(downloadFile.getId())){
            mDb.update(Constant.db.NAME_TABALE, values, Constant.db.id + " = ?", new String[]{downloadFile.getId()});
        }else {
            mDb.insert(Constant.db.NAME_TABALE, null, values);
        }

    }//end of "saveFile(..."


    public void updateState(String id, int state){
        if (TextUtils.isEmpty(id)){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(Constant.db.downloadStatus, state);
        mDb.update(Constant.db.NAME_TABALE, values, Constant.db.id + " = ?", new String[]{id});
    }


    public FileInfo getFileInfo(String id){
        Cursor cursor = mDb.query(Constant.db.NAME_TABALE, null, " " + Constant.db.id + " = ? ", new String[]{id}, null, null, null);
        FileInfo downloadFile = null;
        while (cursor.moveToNext()){
            downloadFile = new FileInfo();
            downloadFile.setId( cursor.getString(cursor.getColumnIndex( Constant.db.id)) );
            downloadFile.setDownloadUrl( cursor.getString(cursor.getColumnIndex( Constant.db.downloadUrl)) );
            downloadFile.setFilePath( cursor.getString(cursor.getColumnIndex( Constant.db.filePath)) );
            downloadFile.setSize( cursor.getLong( cursor.getColumnIndex(Constant.db.size)) );
            downloadFile.setDownloadLocation( cursor.getLong( cursor.getColumnIndex(Constant.db.downloadLocation)));
            downloadFile.setDownStatus( cursor.getInt(cursor.getColumnIndex(Constant.db.downloadStatus)) );

            //Todo 这一步没明白，为什么要这样做,建立文件干嘛啊
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
            mDb.delete(Constant.db.NAME_TABALE, Constant.db.id + " = ?", new String[]{id});
        }
    }

    private boolean has(String id){
        Cursor cursor = mDb.query(Constant.db.NAME_TABALE, null,  " " + Constant.db.id + " = ? ", new String[]{id}, null, null, null);
        boolean has = cursor.moveToNext();
        cursor.close();
        return has;
    }
}
