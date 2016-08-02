package com.feicui.mygitdroid.github.hotuser.view;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;
import com.feicui.mygitdroid.github.login.model.User;

import java.util.List;

/**
 * 下拉刷新视图
 * Created by Administrator on 2016/7/28 0028.
 */
public interface UserListPtrView {

    void addRefreshData(List<User> datas);
    void stopRefresh();
    void showContentView();
    void showErrorView();
    void showEmptyView();
    void showMessage(String msg);

}
