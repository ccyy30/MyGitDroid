package com.feicui.mygitdroid.hotrepo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicui.mygitdroid.hotrepo.repolist.RepoListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HotRepoAdapter  extends FragmentPagerAdapter{

    private List<Language> languages;

    public HotRepoAdapter(FragmentManager fm,Context context) {
        super(fm);
        languages = Language.getDefaultLanguages(context);
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }
    //返回标题栏数据
    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
