package com.feicui.mygitdroid.login.view;

/**
 * 登陆视图
 * 视图中只需要显示进度条不需要隐藏进度条，因为当webView加载完毕的监听已经写了
 * Created by Administrator on 2016/7/29 0029.
 */
public interface LoginView {

    //跳转到主界面
    void navigationToHome();
    //显示错误信息
    void showMessage(String msg);
    //授权过程中显示进度条
    void showProgress();
    //如果请求失败重新加载webView
    void resetView();

}
