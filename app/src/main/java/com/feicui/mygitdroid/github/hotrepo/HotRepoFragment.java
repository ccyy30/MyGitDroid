package com.feicui.mygitdroid.github.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HotRepoFragment extends Fragment{

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private HotRepoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_repo,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        //getChildFragmentManager(),在Fragment中获取管理器
        adapter = new HotRepoAdapter(getChildFragmentManager(),getContext());
        viewPager.setAdapter(adapter);
        //viewpager上方的标题栏，需要指定绑定的viewpager对象
        tabLayout.setupWithViewPager(viewPager);
    }


}
