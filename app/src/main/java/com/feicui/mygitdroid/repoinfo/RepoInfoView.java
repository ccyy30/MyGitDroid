package com.feicui.mygitdroid.repoinfo;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public interface RepoInfoView {

    void showMessage(String msg);
    void showProgress();
    void hideProgress();
    void showData(String data);

}
