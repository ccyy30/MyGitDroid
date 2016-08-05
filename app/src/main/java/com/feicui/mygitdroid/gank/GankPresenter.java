package com.feicui.mygitdroid.gank;

import android.support.annotation.NonNull;


import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.gank.model.GankItem;
import com.feicui.mygitdroid.gank.model.GankResult;
import com.feicui.mygitdroid.gank.network.GankClient;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：yuanchao on 2016/8/5 0005 11:22
 * 邮箱：yuanchao@feicuiedu.com
 */
public class GankPresenter {

    private Call<GankResult> call;
    private GankView gankView;

    public GankPresenter(@NonNull GankView gankView) {
        this.gankView = gankView;
    }

    /**
     * 获取每日干货数据,通过日期
     */
    public void getGanks(Calendar calendar) {
        // 得到year,monty,day
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
        //下面这段代码在这里会出现bug，原因未知
        LogUtils.i(call+"-------------------------");
//        if (call != null)call.cancel();
        call = GankClient.getInstance().getDailyData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        call.enqueue(callback);
    }

    private Callback<GankResult> callback = new Callback<GankResult>() {
        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            if(response.isSuccessful()){
                GankResult gankResult = response.body();
                if (gankResult == null) {
                    gankView.showMessage("未知错误!");
                    gankView.showEmptyView();
                    return;
                }
                // 没有数据的情况
                if (gankResult.isError()
                        || gankResult.getResults() == null
                        || gankResult.getResults().getAndroidItems() == null
                        || gankResult.getResults().getAndroidItems().isEmpty()) {
                    gankView.showEmptyView();
                    return;
                }
                List<GankItem> gankItems = gankResult.getResults().getAndroidItems();
                // 将获取到的今日敢货数据交付给视图
                gankView.hideEmptyView();
                gankView.addData(gankItems);
            }
        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            gankView.showMessage("Error:" + t.getMessage());
            gankView.showEmptyView();
        }
    };
}