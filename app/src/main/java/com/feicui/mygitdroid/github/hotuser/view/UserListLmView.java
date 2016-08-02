package com.feicui.mygitdroid.github.hotuser.view;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;
import com.feicui.mygitdroid.github.login.model.User;

import java.util.ArrayList;

/**
 * 上拉加载更多视图
 * Created by Administrator on 2016/7/28 0028.
 */
public interface UserListLmView {

    void showLoadMoreLoading();
    void hideLoadMore();
    void showLoadMoreErro(String erroMsg);
    void addLoadMoreData(ArrayList<User> datas);

}
