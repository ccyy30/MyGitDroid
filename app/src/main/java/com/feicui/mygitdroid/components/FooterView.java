package com.feicui.mygitdroid.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicui.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 上拉加载更多的视图
 * Created by Administrator on 2016/7/28 0028.
 */
public class FooterView extends FrameLayout{

    //三种加载的状态
    private static final int STATE_LOADING = 0;
    private static final int STATE_COMPLETE = 1;
    private static final int STATE_ERROR = 2;
    //默认正在加载状态
    private int status;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tv_complete)
    TextView tvComplete;

    @BindView(R.id.tv_error)
    TextView tvError;

    public FooterView(Context context) {
        this(context,null);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化视图
    private void init(){
        inflate(getContext(), R.layout.content_load_footer,this);
        ButterKnife.bind(this);
    }

    //判断是否正在加载
    public boolean isLoading(){
        return status == STATE_LOADING;
    }

    //判断是否加载完成（是否有数据）
    public boolean isComplete(){
        return status == STATE_COMPLETE;
    }

    //设置点击错误文字的监听
    public void setErrorClickListener(OnClickListener listener){
        tvError.setOnClickListener(listener);
    }

    //显示正在加载中视图
    public void showLoading(){
        status = STATE_LOADING;
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        tvComplete.setVisibility(View.GONE);
    }


    //如果没有数据，则显示的视图
    public void showComplete(){
        status = STATE_COMPLETE;
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        tvComplete.setVisibility(View.VISIBLE);
    }

    public void showError(){
        status = STATE_ERROR;
        progressBar.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvComplete.setVisibility(View.GONE);
    }
}
