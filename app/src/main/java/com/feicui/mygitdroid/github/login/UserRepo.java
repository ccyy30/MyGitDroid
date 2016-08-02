package com.feicui.mygitdroid.github.login;

import com.feicui.mygitdroid.github.login.model.User;

/**
 * 用户登录时缓存用户信息和token的仓库类
 * Created by Administrator on 2016/7/29 0029.
 */
public class UserRepo {

    private UserRepo(){}

    private static String token;

    private static User user;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserRepo.token = token;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserRepo.user = user;
    }

    //判断token是否存在
    public static boolean hasToken(){
        return token != null;
    }

    //判断用户是否是游客登陆
    public static boolean isEmpty(){
        return token == null || user == null;
    }

    //清理缓存
    public void clear(){
        token = null;
        user = null;
    }
}
