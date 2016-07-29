package com.feicui.mygitdroid.login;

import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.login.model.AccessTokenResult;
import com.feicui.mygitdroid.login.model.User;
import com.feicui.mygitdroid.network.GitHubApi;
import com.feicui.mygitdroid.network.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class LoginPresenter {

    private Call<AccessTokenResult> tokenResultCall;
    private Call<User> userCall;

    public void login(String code){
        if(tokenResultCall != null)tokenResultCall.cancel();
        tokenResultCall = GitHubClient.getInstance().getAccessToken(GitHubApi.CLIENT_ID,GitHubApi.CLIENT_SECRET,code);
        tokenResultCall.enqueue(tokenResultCallback);
    }

    private Callback<AccessTokenResult> tokenResultCallback = new Callback<AccessTokenResult>() {
        @Override
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            if(response.isSuccessful()){
                AccessTokenResult result = response.body();
                if(result == null){
                    LogUtils.i("没有获取到token");
                    return;
                }
                LogUtils.i("成功获取到Token--" + result.getAccessToken());
                //缓存token，以后的每个请求根据文档中的要求都必须需要token，并且将token添加到请求头中
                //所以这里添加了拦截器，从这里开始如果再次有请求发送，则会自动将token添加到消息头中
                UserRepo.setToken(result.getAccessToken());
                if(userCall != null)userCall.cancel();
                userCall = GitHubClient.getInstance().getUserInfo();
                userCall.enqueue(userCallback);
            }else{
                LogUtils.i("服务器连接失败");
            }
        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            LogUtils.i(t.getMessage());
        }
    };

    private Callback<User> userCallback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if(response.isSuccessful()){
                User user = response.body();
                if(user == null){
                    LogUtils.i("没有获取到用户信息");
                    return;
                }
                LogUtils.i("成功获取到用户信息--" + user);
                //缓存用户信息
                UserRepo.setUser(user);
            }else{
                LogUtils.i("服务器连接失败");
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            LogUtils.i(t.getMessage());
        }
    };
}
