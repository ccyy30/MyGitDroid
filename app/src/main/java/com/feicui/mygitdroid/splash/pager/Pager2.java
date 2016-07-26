package com.feicui.mygitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.feicui.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 这里为了做动画的时候方便，所以写了一个类单独来处理视图
 */
public class Pager2 extends FrameLayout{

    @BindView(R.id.ivBubble1)
    ImageView ivBubble1;
    @BindView(R.id.ivBubble2)
    ImageView ivBubble2;
    @BindView(R.id.ivBubble3)
    ImageView ivBubble3;

    public Pager2(Context context) {
        this(context,null);
    }

    public Pager2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化布局
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2,this,true);
        //要先加载视图再绑定！
        ButterKnife.bind(this);
        //先让三个ImageView隐藏
        ivBubble1.setVisibility(View.GONE);
        ivBubble2.setVisibility(View.GONE);
        ivBubble3.setVisibility(View.GONE);
    }

    //播放动画
    public void animation(){
        //三个ImageView轮流播放动画，要有时间间隔
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInRight).duration(500).playOn(ivBubble1);
            }
        },50);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInRight).duration(500).playOn(ivBubble2);
            }
        },500);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble3.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInRight).duration(500).playOn(ivBubble3);
            }
        },950);
    }
}
