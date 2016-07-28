package com.feicui.mygitdroid.hotrepo.repolist;

import android.os.AsyncTask;

import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.hotrepo.repolist.view.RepoListPtrView;
import com.feicui.mygitdroid.hotrepo.repolist.view.RepoListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class RepoListPresenter {

    private RepoListView repoListView;
    private int count;

    public RepoListPresenter(RepoListView repoListView){
        this.repoListView = repoListView;
    }

    //下拉刷新业务
    public void ptrRefresh() {
        new PtrRefreshTask().execute();
    }

    //上拉加载更多业务
    public void loadMore(){
        new LoadMoreTask().execute();
    }

    private class PtrRefreshTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for(int x = 0 ; x < 20; x++){
                datas.add("测试数据" + (count++));
            }
            //停止刷新
            repoListView.stopRefresh();
            //更新数据
            repoListView.addRefreshData(datas);
            //显示内容视图
            repoListView.showContentView();
        }
    }

    private class LoadMoreTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtils.i("444444");
            //加载进度条
            repoListView.showLoadMoreLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas = new ArrayList<String>();
            for(int x = 0 ; x < 20; x++){
                datas.add("测试数据" + (count++));
            }
            //隐藏进度条
            repoListView.hideLoadMore();
            //添加数据
            repoListView.addLoadMoreData(datas);
        }
    }
}
