package com.feicui.mygitdroid.network;

import com.feicui.mygitdroid.login.model.AccessTokenResult;
import com.feicui.mygitdroid.login.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public interface GitHubApi {

    //授权后回调的协议头，网页上注册的是fc://，后面用来判断
    public static final String CALLBACK_SCHEMA = "fc";
    //GitHub上注册应用时生成的clientid
    public static final String CLIENT_ID = "b1dbc20268fe668720a0";
    public static final String CLIENT_SECRET = "798bff0e5d2cfa655e8709dd8ac29d7869e5ca75";
    /*我们应用中使用的scope包括：

    1.user : 读写用户信息；
    2.public_repo : 该用户公有库的访问权限，关注(starring)其他公有库的权限;
    3.repo : 该用户公有和私有库的访问权限*/
    final String AUTH_SCOPE = "user,public_repo,repo";
    //授权url,参数拼接在后面
    public String AUTH_URL = "https://github.com/login/oauth/authorize?client_id="+CLIENT_ID+"&scope="+AUTH_SCOPE;

    /* 获取访问令牌token的API**/
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getAccessToken(@Field("client_id") String clientId,
                                           @Field("client_secret") String clientSecret,
                                           @Field("code") String code);

    /** 获取用户信息*/
    @GET("user")
    Call<User> getUserInfo();
}
