package com.feicui.mygitdroid.gank;

import com.feicui.mygitdroid.gank.model.GankItem;
import com.feicui.mygitdroid.gank.model.GankResult;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public interface GankView {

    void showEmptyView();

    void hideEmptyView();

    void showMessage(String msg);

    void addData(List<GankItem> gankItems);
}
