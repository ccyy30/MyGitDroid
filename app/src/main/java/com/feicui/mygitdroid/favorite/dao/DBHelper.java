package com.feicui.mygitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicui.mygitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "repo_favorite.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //创建表
            TableUtils.createTableIfNotExists(connectionSource,RepoGroup.class);
            //初始化数据
            new RepoGroupDao(context).createOrUpdate(RepoGroup.getRepoGroupList(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //删除表
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            //再创建
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
