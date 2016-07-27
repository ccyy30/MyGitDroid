package com.feicui.mygitdroid.hotrepo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HotRepoAdapter  extends FragmentPagerAdapter{

    public HotRepoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new HotRepoListFragment();
    }

    @Override
    public int getCount() {
        return 10;
    }
    //返回标题栏数据
    @Override
    public CharSequence getPageTitle(int position) {
        return "java" + position;
    }
}
