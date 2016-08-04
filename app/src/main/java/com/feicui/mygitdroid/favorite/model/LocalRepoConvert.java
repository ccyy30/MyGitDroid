package com.feicui.mygitdroid.favorite.model;

import android.support.annotation.NonNull;

import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * 本类负责将Repo仓库类型转换成LocalRepo仓库类型
 * Created by Administrator on 2016/8/4 0004.
 */
public class LocalRepoConvert {

    private LocalRepoConvert(){}
    //转换类型后，默认是未分类
    public static LocalRepo convertToLocalRepo(Repo repo){
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStarCount(repo.getStarCount());
        localRepo.setForkCount(repo.getForkCount());
        localRepo.setRepoGroup(null);
        return localRepo;
    }

    public static @NonNull
    List<LocalRepo> converAll(@NonNull List<Repo> repos) {
        ArrayList<LocalRepo> localRepos = new ArrayList<LocalRepo>();
        for (Repo repo : repos) {
            localRepos.add(convertToLocalRepo(repo));
        }
        return localRepos;
    }
}
