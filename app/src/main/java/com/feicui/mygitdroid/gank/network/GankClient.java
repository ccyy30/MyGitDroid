package com.feicui.mygitdroid.gank.network;


import com.feicui.mygitdroid.commons.LoggingInterceptor;
import com.feicui.mygitdroid.gank.model.GankResult;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankClient implements GankApi{

    private static GankClient sClient;

    public static GankClient getInstance(){
        if (sClient == null) {
            sClient = new GankClient();
        }
        return sClient;
    }

    private final GankApi gankApi;

    private GankClient(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gankApi = retrofit.create(GankApi.class);

    }

    @Override public Call<GankResult> getDailyData(int year, int month, int day) {
        return gankApi.getDailyData(year, month, day);
    }
}
