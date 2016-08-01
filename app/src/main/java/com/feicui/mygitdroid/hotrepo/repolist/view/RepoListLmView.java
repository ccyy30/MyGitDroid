package com.feicui.mygitdroid.hotrepo.repolist.view;

import com.feicui.mygitdroid.hotrepo.repolist.modle.Repo;

import java.util.ArrayList;

/**
 * 上拉加载更多视图
 * Created by Administrator on 2016/7/28 0028.
 */
public interface RepoListLmView {

    void showLoadMoreLoading();
    void hideLoadMore();
    void showLoadMoreErro(String erroMsg);
    void addLoadMoreData(ArrayList<Repo> datas);

}
