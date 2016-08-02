package com.feicui.mygitdroid.github.hotuser;

import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.github.hotuser.view.UserListView;
import com.feicui.mygitdroid.github.network.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class HotUserPresenter {

    private UserListView userListView;
    private int pageId;

    public HotUserPresenter(UserListView userListView){
        this.userListView = userListView;
    }

    private Call<UsersResult> usersResultPtrCall;
    private Call<UsersResult> usersResultLmCall;

    //下拉刷新
    public void ptrRefresh(){
        userListView.hideLoadMore();
        pageId = 1;
        if(usersResultPtrCall != null)usersResultPtrCall.cancel();
        usersResultPtrCall = GitHubClient.getInstance().searchUser("followers:" + ">1000",pageId);
        usersResultPtrCall.enqueue(usersResultCallback);
    }

    private Callback<UsersResult> usersResultCallback = new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.stopRefresh();
            if(response.isSuccessful()){
                UsersResult result = response.body();
                if(result.getTotalCount() <= 0){//说明没有找到结果
                    userListView.showEmptyView();
                    userListView.showMessage("没有查找到结果");
                    return;
                }
                userListView.showContentView();
                LogUtils.i(result.getUserList().toString());
                userListView.addRefreshData(result.getUserList());
                //下拉后让pageId为2
                pageId = 2;
            }
        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {
            //停止刷新
            userListView.stopRefresh();
            userListView.showMessage(t.getMessage());
            userListView.showErrorView();
        }
    };

    //上拉加载
    public void loadMore(){
        userListView.stopRefresh();
        //加载进度条
        userListView.showLoadMoreLoading();
        if(usersResultLmCall != null)usersResultLmCall.cancel();
        usersResultLmCall = GitHubClient.getInstance().searchUser("followers:" + ">1000",pageId);
        usersResultLmCall.enqueue(loadMoreCallback);
    }

    private Callback<UsersResult> loadMoreCallback = new Callback<UsersResult>() {
        @Override
        public void onResponse(Call<UsersResult> call, Response<UsersResult> response) {
            userListView.hideLoadMore();
            if(response.isSuccessful()){
                UsersResult result = response.body();
                LogUtils.i(result.getUserList().toString());
                userListView.addRefreshData(result.getUserList());
                //每次上拉加载让pageId++，id越大页码越大
                pageId++;
            }
        }

        @Override
        public void onFailure(Call<UsersResult> call, Throwable t) {
            //停止刷新
            userListView.hideLoadMore();
            userListView.showMessage(t.getMessage());
            userListView.showLoadMoreErro(t.getMessage());
        }
    };
}
