package com.feicui.mygitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.hotrepo.HotRepoFragment;
import com.feicui.mygitdroid.login.LoginActivity;
import com.feicui.mygitdroid.login.UserRepo;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigationView)
    NavigationView navigationView;

    //显示登陆状态的按钮
    private Button btLogin;
    //用户头像
    private ImageView ivIcon;

    private ActivityUtils activityUtils;
    private HotRepoFragment hotRepoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //判断是否是游客身份
        if(UserRepo.isEmpty()){
            //将侧拉菜单中的账号名称更改为“登陆GitHub”
            btLogin.setText(R.string.login_github);
            return;
        }
        //如果是用户身份
        //title改成用户名
        getSupportActionBar().setTitle(UserRepo.getUser().getName());
        //登陆状态改为“切换账号”
        btLogin.setText(R.string.switch_account);
        //头像改为用户头像,由于用户头像是url，所以使用universalimageloader库
        String imgUrl = UserRepo.getUser().getAvatar();
        if(imgUrl != null){
            ImageLoader.getInstance().displayImage(imgUrl,ivIcon);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.hot_repo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        toggle.syncState();
        //设置点击左边toolbar上按钮的监听，就是toggle对象，因为ActionBarDrawerToggle
        //已经默认实现了监听
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        //获取Navigation的头部视图中的用户头像和登陆状态控件
        btLogin = ButterKnife.findById(navigationView.getHeaderView(0),R.id.btnLogin);
        ivIcon = ButterKnife.findById(navigationView.getHeaderView(0),R.id.ivIcon);
        //点击登陆状态按钮切换到登陆页面
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        //默认显示热门仓库的fragment
        hotRepoFragment = new HotRepoFragment();
        changeFragment(hotRepoFragment);
    }

    //切换fragment的函数
    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    //侧滑菜单的监听
    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()){
                //热门仓库
                case R.id.github_hot_repo:

                    break;
            }
            return true;
        }
    };

    //处理返回键
    @Override
    public void onBackPressed() {
        //如果抽屉是打开状态，则关闭
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}
