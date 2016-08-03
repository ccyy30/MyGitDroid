package com.feicui.mygitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicui.mygitdroid.favorite.model.LocalRepo;
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

    private static DBHelper dbHelp;

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelp == null) {
            dbHelp = new DBHelper(context.getApplicationContext());
        }
        return dbHelp;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //创建仓库类别表
            TableUtils.createTableIfNotExists(connectionSource,RepoGroup.class);
            //创建本地仓库表
            TableUtils.createTableIfNotExists(connectionSource,LocalRepo.class);
            //初始化数据
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getRepoGroupList(context));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getLocalRepoList(context));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //删除表
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);
            //再创建
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
