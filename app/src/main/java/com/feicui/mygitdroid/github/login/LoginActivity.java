package com.feicui.mygitdroid.github.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicui.mygitdroid.github.MainActivity;
import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.github.login.view.LoginView;
import com.feicui.mygitdroid.github.network.GitHubApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity implements LoginView{

    //做GIF动画的第三方库
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    //授权登陆的webView
    @BindView(R.id.webView)
    WebView webView;

    private LoginPresenter loginPresenter;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
        initWebView();

    }

    private void initWebView() {
        //每次进入webView先清理之前保存过的cookie
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //设置webView属性
        webView.loadUrl(GitHubApi.AUTH_URL);//要加载的网页
        webView.setFocusable(true);//请求焦点
        webView.setFocusableInTouchMode(true);
        //设置webView监听
        webView.setWebChromeClient(webChromeClient);
        //url重定向时刷新的监听
        webView.setWebViewClient(webViewClient);
    }

    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            //重定向需要检测是不是我们之前注册的回调URL，如果是，说明授权成功，可以获取临时授权码
            if(uri.getScheme().equals(GitHubApi.CALLBACK_SCHEMA)){
                //获取code
                String code = uri.getQueryParameter("code");
                LogUtils.i(code);
                //授权登陆业务
                loginPresenter.login(code);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress >= 100){//加载完毕
                //显示webview，隐藏动画控件
//                webView.setVisibility(View.VISIBLE);//一直都是显示的 ，没有必要再写
                gifImageView.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void navigationToHome() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetView() {
        initWebView();
    }
}
