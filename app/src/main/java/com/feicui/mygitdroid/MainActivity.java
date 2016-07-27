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

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.hotrepo.HotRepoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @BindView(R.id.navigationView)
    NavigationView navigationView;

    private HotRepoFragment hotRepoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
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
