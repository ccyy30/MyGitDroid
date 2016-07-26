package com.feicui.mygitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.mygitdroid.splash.pager.Pager0;
import com.feicui.mygitdroid.splash.pager.Pager1;
import com.feicui.mygitdroid.splash.pager.Pager2;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class SplashPagerAdapter extends PagerAdapter {

    private View[] views;

    public SplashPagerAdapter(Context context){
        views = new View[3];
        views[0] = new Pager0(context);
        views[1] = new Pager1(context);
        views[2] = new Pager2(context);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views[position],0);
        return views[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
