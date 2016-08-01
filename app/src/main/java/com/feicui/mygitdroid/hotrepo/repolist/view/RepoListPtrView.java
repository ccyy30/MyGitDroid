package com.feicui.mygitdroid.hotrepo.repolist.view;

import com.feicui.mygitdroid.hotrepo.repolist.modle.Repo;
import com.feicui.mygitdroid.hotrepo.repolist.modle.RepoResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新视图
 * Created by Administrator on 2016/7/28 0028.
 */
public interface RepoListPtrView {

    void addRefreshData(List<Repo> datas);
    void stopRefresh();
    void showContentView();
    void showErrorView();
    void showEmptyView();
    void showMessage(String msg);

}
