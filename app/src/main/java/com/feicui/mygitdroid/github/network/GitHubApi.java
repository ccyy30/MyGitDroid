package com.feicui.mygitdroid.github.network;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.ReposResult;
import com.feicui.mygitdroid.github.hotuser.UsersResult;
import com.feicui.mygitdroid.github.login.model.AccessTokenResult;
import com.feicui.mygitdroid.github.login.model.User;
import com.feicui.mygitdroid.github.repoinfo.RepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    /** 搜索仓库*/
    @GET("search/repositories")
    Call<ReposResult> searchRepos(@Query("q")String query, @Query("page")int pageId);

    /*  获取readme，进行过Base64加密处理，需要解密**/
    @GET("/repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(
            @Path("owner")String owner,
            @Path("repo")String repo
    );

    /*  根据readme返回的RepoContentResult来获取MarkDown
    *   它以纯文本的形式text/plain接收Markdown文档，并返回该文档对应的原始文件
    *   响应

        例如：

        Status: 200 OK
        Content-Type: text/html
        X-RateLimit-Limit: 5000
        X-RateLimit-Remaining: 4999
<p>Hello world github/linguist#1 <strong>cool</strong>, and #1!</p>
    * **/
    @POST("/markdown/raw")
    Call<ResponseBody> getMarkDown(@Body RequestBody body);

    /*  获取用户列表**/
    @GET("/search/users")
    Call<UsersResult> searchUser(@Query("q")String q,@Query("page")int pageId);
}
