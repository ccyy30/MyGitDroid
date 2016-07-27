package com.feicui.mygitdroid.splash;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.splash.pager.Pager2;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class SplashPagerFragment  extends Fragment{

    //指示器
    @BindView(R.id.indicator)
    CircleIndicator circleIndicator;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private SplashPagerAdapter adapter;
    @BindView(R.id.content)
    FrameLayout frameLayout;//fragment的根布局，用于更改背景颜色
    //三种背景颜色
    @BindColor(R.color.colorGreen)
    int green;
    @BindColor(R.color.colorRed)
    int red;
    @BindColor(R.color.colorYellow)
    int yellow;
    //手机动画用到的三个控件
    @BindView(R.id.layoutPhone)
    FrameLayout layoutPhone;//整个手机
    @BindView(R.id.ivPhoneFont)
    ImageView ivPhoneFont;//手机字体
    //屏幕宽和高
    private int screenW;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;
        return inflater.inflate(R.layout.fragment_splash_pager,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //注意，fragment要使用两个参数的
        ButterKnife.bind(this,view);
        adapter = new SplashPagerAdapter(getContext());
        //给viewPager设置滚动的监听，在监听中更改背景颜色的变化
        viewPager.addOnPageChangeListener(colorChange);
        viewPager.addOnPageChangeListener(phoneChange);
        viewPager.setAdapter(adapter);
        //指示器需要指定viewpager对象才能正常工作
        circleIndicator.setViewPager(viewPager);
    }

    //颜色动画
    private ViewPager.OnPageChangeListener colorChange = new ViewPager.OnPageChangeListener() {
        private ArgbEvaluator evaluator = new ArgbEvaluator();//颜色拾取器
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //判断索引，如果是第一页绿色，第二页红色，第三页黄色的效果
            switch(position){
                case 0://第一页到第二页
                    //positionOffset参数是翻页时的比例（翻页到百分之多少像素）
                    //positionOffsetPixels是翻了多少像素
                    int colorOne = (int) evaluator.evaluate(positionOffset,green,red);
                    frameLayout.setBackgroundColor(colorOne);
                    break;
                case 1://第二页到第三页
                    int colorTwo = (int) evaluator.evaluate(positionOffset,red,yellow);
                    frameLayout.setBackgroundColor(colorTwo);
                    break;
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //手机动画
    private ViewPager.OnPageChangeListener phoneChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            switch(position){
                case 0: //第一页到第二页
                    //手机字体透明度
                    ivPhoneFont.setAlpha(positionOffset);

                    //缩放动画,不能缩的太小
                    float scale = 0.3f + 0.7f * positionOffset;//最小到0.3倍
                    layoutPhone.setScaleX(scale);
                    layoutPhone.setScaleY(scale);

                    //手机从中间到左边靠边来回移动的动画,但是左边不能移出去
                    layoutPhone.setTranslationX((int)((positionOffset - 1) * (screenW / 3)));
                    break;
                case 1://第二页到第三页
//                    Log.i("positionOffsetPixels",positionOffsetPixels+"");
                    //从画面中往左移出屏幕
                    layoutPhone.setTranslationX(-positionOffsetPixels);
                    break;
            }

        }

        @Override
        public void onPageSelected(int position) {
            switch(position){
                case 2:
                    //每次到最后一页播放Pager2类中定义好的动画
                    Pager2 pager2 = (Pager2) adapter.getView(position);
                    pager2.animation();
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
