package com.feicui.mygitdroid.login;

import com.feicui.mygitdroid.commons.LogUtils;
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
            }else{
                LogUtils.i("服务器连接失败");
            }

        }

        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            LogUtils.i(t.getMessage());
        }
    };

}
