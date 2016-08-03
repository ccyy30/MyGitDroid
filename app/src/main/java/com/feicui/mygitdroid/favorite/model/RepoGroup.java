package com.feicui.mygitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3 0003.
 */
//ORM框架建表的表名
@DatabaseTable(tableName = "repo_group")
public class RepoGroup {
    //添加主键
    @DatabaseField(id = true)
    private long id;

    //修改字段名称
    @DatabaseField(columnName = "NAME")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    //解析repogroup.json，转换成集合
    private static List<RepoGroup> repoGroupList;

    public static List<RepoGroup> getRepoGroupList(Context context){
        if(repoGroupList != null){
            return repoGroupList;
        }
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("repogroup.json");
            //流转换成String
            String json = IOUtils.toString(is);
            repoGroupList = gson.fromJson(json,new TypeToken<List<RepoGroup>>(){}.getType());
            return repoGroupList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
