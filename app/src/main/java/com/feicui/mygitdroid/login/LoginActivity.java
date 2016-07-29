package com.feicui.mygitdroid.login;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.network.GitHubApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {

    //做GIF动画的第三方库
    @BindView(R.id.gifImageView)
    GifImageView gifImageView;

    //授权登陆的webView
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
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
                webView.setVisibility(View.VISIBLE);
                gifImageView.setVisibility(View.GONE);
            }
        }
    };
}
