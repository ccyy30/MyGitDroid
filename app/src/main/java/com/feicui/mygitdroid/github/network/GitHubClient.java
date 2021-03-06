package com.feicui.mygitdroid.github.network;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.ReposResult;
import com.feicui.mygitdroid.github.hotuser.UsersResult;
import com.feicui.mygitdroid.github.login.model.AccessTokenResult;
import com.feicui.mygitdroid.github.login.model.User;
import com.feicui.mygitdroid.github.repoinfo.RepoContentResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class GitHubClient implements GitHubApi{

    private OkHttpClient okHttpClient;
    private static GitHubClient gitHubClient;
    private Retrofit retrofit;
    private Gson gson;
    public static final String BASE_URL = "https://api.github.com/";

    private GitHubClient(){
        //设置gson转换时使用不严格模式
        gson = new GsonBuilder().setLenient().create();

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                //添加拦截器，每次发送请求时都需要在头中添加token
                .addInterceptor(new TokenInterceptor())
                .build();

        //添加一个转换器，可以自动转换JSON到对象.addConverterFactory(GsonConverterFactory.create(gson))
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        gitHubApi = retrofit.create(GitHubApi.class);
    }

    public static GitHubClient getInstance(){
        if(gitHubClient == null){
            gitHubClient = new GitHubClient();
        }
        return gitHubClient;
    }

    private GitHubApi gitHubApi;

    public OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }


    @Override
    public Call<AccessTokenResult> getAccessToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getAccessToken(clientId,clientSecret,code);
    }

    @Override
    public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }

    @Override
    public Call<ReposResult> searchRepos(@Query("q") String query, @Query("page") int pageId) {
        return gitHubApi.searchRepos(query,pageId);
    }

    @Override
    public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return gitHubApi.getReadme(owner,repo);
    }

    @Override
    public Call<ResponseBody> getMarkDown(@Body RequestBody body) {
        return gitHubApi.getMarkDown(body);
    }

    @Override
    public Call<UsersResult> searchUser(@Query("q") String q,@Query("page") int pageId) {
        return gitHubApi.searchUser(q,pageId);
    }
}
