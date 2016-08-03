package com.feicui.mygitdroid.favorite.dao;

import com.feicui.mygitdroid.favorite.model.LocalRepo;
import com.feicui.mygitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
public class LocalRepoDao {

    private Dao<LocalRepo,Long> dao;

    public LocalRepoDao(DBHelper dbHelper){
        try {
            dao = dbHelper.getDao(LocalRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新本地仓库
    public void createOrUpdate(LocalRepo table){
        try {
            dao.createOrUpdate(table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //添加和更新多个本地仓库
    public void createOrUpdate(List<LocalRepo> tables){
        for(LocalRepo table:tables){
            createOrUpdate(table);
        }
    }

    //查询全部的本地仓库
    public List<LocalRepo> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //根据类别id查询本地仓库
    public List<LocalRepo> queryForGroupId(long groupId){
        try {
            return dao.queryForEq(LocalRepo.COLUMN_GROUP_ID,groupId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询未分类的本地仓库
    public List<LocalRepo> queryForNoGroup(){
        try {
            return dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //删除本地仓库
    public void deleteLocalRepo(LocalRepo localRepo){
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
