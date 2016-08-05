package com.feicui.mygitdroid.gank.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class GankResult {

    private boolean error;

    private List<String> category;

    private Results results;

    public List<String> getCategory() {
        return category;
    }

    public Results getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }

    public static class Results {

        @SerializedName("Android")
        private List<GankItem> androidItems;

        public List<GankItem> getAndroidItems() {
            return androidItems;
        }
    }
}
