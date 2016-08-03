package com.feicui.mygitdroid.favorite.dao;

import android.content.Context;

import com.feicui.mygitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class RepoGroupDao {

//    private DBHelper dbHelper;
    private Dao<RepoGroup,Long> dao;

//    public RepoGroupDao(Context context){
//        if(dbHelper == null){
//            dbHelper = new DBHelper(context);
//            try {
//                dao = dbHelper.getDao(RepoGroup.class);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    public RepoGroupDao(DBHelper dbHelp) {
        try {
            // 创建出仓库类别表的Dao
            dao = dbHelp.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询所有的仓库类别
    public List<RepoGroup> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //根据id进行查询
    public RepoGroup queryForId(long id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新仓库类别表
    public void createOrUpdate(RepoGroup table){
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新多个仓库类别表
    public void createOrUpdate(List<RepoGroup> tables){
        for(RepoGroup table:tables){
            createOrUpdate(table);
        }
    }
}
