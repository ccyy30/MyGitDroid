package com.feicui.mygitdroid.hotrepo.repolist.modle;
import com.feicui.mygitdroid.login.model.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者：yuanchao on 2016/8/1 0001 11:03
 * 邮箱：yuanchao@feicuiedu.com
 */
public class Repo implements Serializable{

    private int id;
    // 仓库名称
    private String name;
    // 仓库全名
    @SerializedName("full_name")
    private String fullName;
    // 仓库描述
    private String description;
    // 本仓库的star数量 (在GitHub上被关注的数量)
    @SerializedName("stargazers_count")
    private int starCount;
    // 本仓库的fork数量 (在GitHub上被拷贝的数量)
    @SerializedName("forks_count")
    private int forkCount;

    // 该仓库的拥有者
    private User owner;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStarCount() {
        return starCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public User getOwner() {
        return owner;
    }

    //    {
//        "id": 29028775,
//            "name": "react-native",
//            "full_name": "facebook/react-native",
//            "owner": {
//                  "login": "facebook",
//                "id": 69631,
//                "avatar_url": "https://avatars.githubusercontent.com/u/69631?v=3",
//              },
//        "description": "A framework for building native apps with React.",
//            "stargazers_count": 33961,
//            "forks_count": 7122,
//    },
}
