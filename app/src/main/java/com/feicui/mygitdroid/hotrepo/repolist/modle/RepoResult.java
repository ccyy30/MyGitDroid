package com.feicui.mygitdroid.hotrepo.repolist.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 搜索仓库响应结果
 * 作者：yuanchao on 2016/8/1 0001 11:01
 * 邮箱：yuanchao@feicuiedu.com
 */
public class RepoResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<Repo> repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }


    //    {
//        "total_count": 2074901,
//            "incomplete_results": false,
//            "items": [{}]
//    }
}
