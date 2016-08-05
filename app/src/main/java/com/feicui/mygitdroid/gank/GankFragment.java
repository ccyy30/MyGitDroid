package com.feicui.mygitdroid.gank;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.gank.model.GankItem;
import com.feicui.mygitdroid.gank.model.GankResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

// 此Fragment用来展示单日的干货数据
public class GankFragment extends Fragment implements GankView{

    //导航条的日期文字
    @BindView(R.id.tvDate) TextView tvDate;
    //显示干货的列表
    @BindView(R.id.content) ListView content;
    @BindView(R.id.emptyView) FrameLayout emptyView;

    private ActivityUtils activityUtils;
    private Unbinder unbinder;

    private GankAdapter gankAdapter;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private GankPresenter gankPresenter;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        calendar=Calendar.getInstance(Locale.CHINA);
        date = new Date(System.currentTimeMillis());
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gank, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        gankAdapter = new GankAdapter(getContext());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        tvDate.setText(simpleDateFormat.format(date));
        content.setAdapter(gankAdapter);
        //设置监听单击浏览
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GankItem gankItem = gankAdapter.getItem(i);
                //在新的浏览器中打开URL
                activityUtils.startBrowser(gankItem.getUrl());
            }
        });

        //获取每日干货业务类
        gankPresenter = new GankPresenter(this);
        gankPresenter.getGanks(calendar);
    }

    //右上角按钮点击
    @OnClick(R.id.btnFilter)
    public void showDateDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),//注意这里不能 + 1
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    //日期选择器监听
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            //根据时间重新获取干货数据
            calendar.set(year,month,day);
            //更新左上角时间
            tvDate.setText(simpleDateFormat.format(calendar.getTime()));
            gankPresenter.getGanks(calendar);
        }
    };

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView() {
        emptyView.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    @Override
    public void addData(List<GankItem> gankItems) {
        gankAdapter.addAll(gankItems);
    }

}
