package com.feicui.mygitdroid.gank;


import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.gank.model.GankItem;
import com.feicui.mygitdroid.gank.model.GankResult;
import com.feicui.mygitdroid.gank.network.GankClient;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GankPresenter {

    private Call<GankResult> gankResultCall;
    private GankView gankView;

    public GankPresenter(GankView gankView){
        this.gankView = gankView;
    }

    //获取每日干货
    public void getGanks(Calendar calendar){
        if (gankResultCall != null)gankResultCall.cancel();
        LogUtils.i(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH) + 1)+"-"+calendar.get(Calendar.DAY_OF_MONTH));
        gankResultCall = GankClient.getInstance().getDailyData(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH));
        gankResultCall.enqueue(gankResultCallback);
    }

    private Callback<GankResult> gankResultCallback = new Callback<GankResult>(){

        @Override
        public void onResponse(Call<GankResult> call, Response<GankResult> response) {
            if(response.isSuccessful()){
                GankResult result = response.body();
                if(result == null){
                    gankView.showMessage("未知错误！");
                    return;
                }
                //如果当前没有干货
                if(result.isError()
                        || result.getResults() == null
                        || result.getResults().getAndroidItems() == null
                        || result.getResults().getAndroidItems().isEmpty()){
                        gankView.showEmptyView();
                    return;
                }
                //如果有数据
                List<GankItem> gankItems = result.getResults().getAndroidItems();
                gankView.hideEmptyView();
                gankView.addData(gankItems);
            }
        }

        @Override
        public void onFailure(Call<GankResult> call, Throwable t) {
            //出现错误出现空白视图
            gankView.showMessage(t.getMessage());
            gankView.showEmptyView();
        }
    };

}