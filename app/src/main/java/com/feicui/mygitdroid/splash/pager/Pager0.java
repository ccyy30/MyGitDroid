package com.feicui.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicui.mygitdroid.R;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 这里为了做动画的时候方便，所以写了一个类单独来处理视图
 */
public class Pager0 extends FrameLayout{


    public Pager0(Context context) {
        this(context,null);
    }

    public Pager0(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化布局
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0,this,true);
    }
}
