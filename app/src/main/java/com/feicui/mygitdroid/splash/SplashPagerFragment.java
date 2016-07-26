package com.feicui.mygitdroid.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.mygitdroid.R;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class SplashPagerFragment  extends Fragment{

    //指示器
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private SplashPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        //指示器需要指定viewpager对象才能正常工作
        circleIndicator.setViewPager(viewPager);
    }
}
