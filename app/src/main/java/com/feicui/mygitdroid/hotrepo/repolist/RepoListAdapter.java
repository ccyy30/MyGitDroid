package com.feicui.mygitdroid.hotrepo.repolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.hotrepo.repolist.modle.Repo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/1 0001.
 */
public class RepoListAdapter extends BaseAdapter {

    private ArrayList<Repo> repos;
    private LayoutInflater inflater;

    public void addAll(Collection<Repo> repos){
        this.repos.clear();
        this.repos.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear(){
       repos.clear();
    }

    public RepoListAdapter(Context context){
        repos = new ArrayList<Repo>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return repos.size();
    }

    @Override
    public Object getItem(int i) {
        return repos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.layout_item_repo,null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh = (ViewHolder) view.getTag();
        Repo repo = repos.get(i);
        //加载仓库头像
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),vh.ivIcon);
        vh.tvRepoInfo.setText(repo.getDescription());
        vh.tvRepoName.setText(repo.getName());
        vh.tvRepoStars.setText(repo.getStarCount()+"");
        return view;
    }

    static class ViewHolder{
        @BindView(R.id.ivIcon)ImageView ivIcon;
        @BindView(R.id.tvRepoName)TextView tvRepoName;
        @BindView(R.id.tvRepoInfo)TextView tvRepoInfo;
        @BindView(R.id.tvRepoStars)TextView tvRepoStars;

        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
