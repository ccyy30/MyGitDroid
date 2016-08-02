package com.feicui.mygitdroid.github.repoinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 仓库详情页
* **/
public class RepoInfoActivity extends AppCompatActivity implements RepoInfoView{

    private ActivityUtils activityUtils;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvRepoInfo)
    TextView tvRepoInfo;
    @BindView(R.id.tvRepoStars) TextView tvRepoStars;
    @BindView(R.id.tvRepoName) TextView tvRepoName;
    @BindView(R.id.webView)
    WebView webView; // 用来展示readme的
    @BindView(R.id.progressBar)
    ProgressBar progressBar; // loading
    private RepoInfoPresenter repoInfoPresenter;

    //传递过来的仓库对象
    private Repo repo;
    private static final String KEY_REPO = "key_repo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        repo = (Repo) getIntent().getSerializableExtra(KEY_REPO);
        setContentView(R.layout.activity_repo_info);
    }

    //跳转到本页时传递仓库
    public static void open(Context context, Repo repo){
        Intent intent = new Intent(context,RepoInfoActivity.class);
        intent.putExtra(KEY_REPO,repo);
        context.startActivity(intent);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        repoInfoPresenter = new RepoInfoPresenter(this);
        //设置toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(repo.getName());
        //设置上半部仓库信息
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),ivIcon);
        tvRepoInfo.setText(repo.getDescription());
        tvRepoName.setText(repo.getFullName());
        tvRepoStars.setText(String.format("start: %d  fork: %d", repo.getStarCount(), repo.getForkCount()));
        //网络请求下半部分readme中的数据，显示在webview上
        repoInfoPresenter.getReadme(repo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData(String data) {
        webView.loadData(data,"text/html","utf-8");
    }
}
