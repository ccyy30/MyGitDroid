package com.feicui.mygitdroid.github.repoinfo;

import android.util.Base64;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;
import com.feicui.mygitdroid.github.network.GitHubClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class RepoInfoPresenter {

    private Call<RepoContentResult> contentResultCall;
    private Call<ResponseBody> markdownCall;
    private RepoInfoView repoInfoView;
    public RepoInfoPresenter(RepoInfoView repoInfoView){
        this.repoInfoView = repoInfoView;
    }

    public void getReadme(Repo repo){
        repoInfoView.showProgress();
        //两个参数如square,retrofit
        if(contentResultCall != null)contentResultCall.cancel();
        contentResultCall = GitHubClient.getInstance().getReadme(repo.getOwner().getLogin(),repo.getName());
        contentResultCall.enqueue(contentResultCallback);
    }

    private Callback<RepoContentResult> contentResultCallback = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            if(response.isSuccessful()){
                RepoContentResult result = response.body();
                //获取readme
                String content = result.getContent();
                //Base64解密
                byte[] data = Base64.decode(content,Base64.DEFAULT);
                //将结果作为第二个请求的请求体，来获取MARKDOWN
                MediaType type = MediaType.parse("text/plain");//指定文件类型
                RequestBody body = RequestBody.create(type,data);
                if(markdownCall != null)markdownCall.cancel();
                markdownCall = GitHubClient.getInstance().getMarkDown(body);
                markdownCall.enqueue(markdownCallback);
            }
        }

        private Callback<ResponseBody> markdownCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                repoInfoView.hideProgress();
                if (response.isSuccessful()){
                    try {
                        //获取markdown
                        String data = response.body().string();
                        //设置数据到webView
                        repoInfoView.showData(data);
                    } catch (IOException e) {
                        onFailure(call,e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                repoInfoView.hideProgress();
                repoInfoView.showMessage(t.getMessage());
            }
        };

        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
            repoInfoView.hideProgress();
            repoInfoView.showMessage(t.getMessage());
        }
    };

}
