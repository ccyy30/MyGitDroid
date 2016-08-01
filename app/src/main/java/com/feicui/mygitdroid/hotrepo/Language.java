package com.feicui.mygitdroid.hotrepo;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 作者：yuanchao on 2016/8/1 0001 10:09
 * 邮箱：yuanchao@feicuiedu.com
 */
public class Language implements Serializable{
    // 用于搜索
    private String path;
    // 用于显示
    private String name;

    private static List<Language> languages;

    public static List<Language> getDefaultLanguages(Context context) {
        if (languages != null) return languages;
        try {
            InputStream inputStream = context.getAssets().open("langs.json");
            String content = IOUtils.toString(inputStream);
            Gson gson = new Gson();
            languages = gson.fromJson(content, new TypeToken<List<Language>>() {
            }.getType());
            return languages;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }


    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }
}
