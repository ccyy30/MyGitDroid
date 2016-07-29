package com.feicui.mygitdroid.login.model;

import com.google.gson.annotations.SerializedName;

/**
 * 个人用户信息响应结果
 */
public class User {
    // 登录所用的账号
    private String login;
    // 用户名
    private String name;
    private int id;
    // 用户头像路径
    @SerializedName("avatar_url")
    private String avatar;

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                '}';
    }

    //    {
//        "login": "octocat",
//            "id": 1,
//            "avatar_url": "https://github.com/images/error/octocat_happy.gif",
//            "name": "monalisa octocat",
//    }
}
