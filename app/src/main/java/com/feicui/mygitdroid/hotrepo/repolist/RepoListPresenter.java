package com.feicui.mygitdroid.hotrepo.repolist;

import android.os.AsyncTask;

import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.hotrepo.Language;
import com.feicui.mygitdroid.hotrepo.repolist.modle.RepoResult;
import com.feicui.mygitdroid.hotrepo.repolist.view.RepoListPtrView;
import com.feicui.mygitdroid.hotrepo.repolist.view.RepoListView;
import com.feicui.mygitdroid.network.GitHubClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class RepoListPresenter {

    private RepoListView repoListView;
    private Call<RepoResult> repoResultCall;
    private int pageId;
    private Language language;

    public RepoListPresenter(RepoListView repoListView,Language language){
        this.repoListView = repoListView;
        this.language = language;
    }

    //下拉刷新业务
    public void ptrRefresh() {
        //下拉刷新先隐藏上拉的视图
        repoListView.hideLoadMore();
        pageId = 1;//下拉刷新默认要最新的数据，所以页id为1表示最新
        if(repoResultCall != null)repoResultCall.cancel();
        repoResultCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(),pageId);
        repoResultCall.enqueue(repoResultCallback);
    }

    private Callback<RepoResult> repoResultCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.stopRefresh();
            if(response.isSuccessful()){
                RepoResult result = response.body();
                if(result.getTotalCount() <= 0){//说明没有找到结果
                    repoListView.showEmptyView();
                    repoListView.showMessage("没有查找到结果");
                    return;
                }
                repoListView.showContentView();
                LogUtils.i(result.getRepoList().toString());
                repoListView.addRefreshData(result.getRepoList());
                //下拉后让pageId为2
                pageId = 2;
            }
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            //停止刷新
            repoListView.stopRefresh();
            repoListView.showMessage(t.getMessage());
            repoListView.showErrorView();
        }
    };

    //上拉加载更多业务
    public void loadMore(){
        repoListView.stopRefresh();
        //加载进度条
        repoListView.showLoadMoreLoading();
        if(repoResultCall != null)repoResultCall.cancel();
        //注意这里不加"language:" + 的话搜索出来的没有顺序，还会有警告
        repoResultCall = GitHubClient.getInstance().searchRepos("language:" + language.getPath(),pageId);
        repoResultCall.enqueue(loadMoreCallback);
    }

    private Callback<RepoResult> loadMoreCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            repoListView.hideLoadMore();
            if(response.isSuccessful()){
                RepoResult result = response.body();
                LogUtils.i(result.getRepoList().toString());
                repoListView.addRefreshData(result.getRepoList());
                //每次上拉加载让pageId++，id越大页码越大
                pageId++;
            }
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            //停止刷新
            repoListView.hideLoadMore();
            repoListView.showMessage(t.getMessage());
            repoListView.showLoadMoreErro(t.getMessage());
        }
    };
}
