package com.feicui.mygitdroid.hotrepo.repolist.view;

import java.util.ArrayList;

/**
 * 下拉刷新视图
 * Created by Administrator on 2016/7/28 0028.
 */
public interface RepoListPtrView {

    void addRefreshData(ArrayList<String> datas);
    void stopRefresh();
    void showContentView();
    void showErrorView();
    void showEmptyView();
    void showMessage(String msg);

}
