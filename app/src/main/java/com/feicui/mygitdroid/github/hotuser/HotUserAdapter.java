package com.feicui.mygitdroid.github.hotuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.github.login.model.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class HotUserAdapter extends BaseAdapter{

    private ArrayList<User> users;
    private LayoutInflater inflater;

    public void addAll(Collection<User> users){
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void clear(){
        users.clear();
    }

    public HotUserAdapter(Context context){
        users = new ArrayList<User>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.layout_item_user,null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh = (ViewHolder) view.getTag();
        User user = users.get(i);
        //加载仓库头像
        ImageLoader.getInstance().displayImage(user.getAvatar(),vh.ivIcon);
        vh.tvLoginName.setText(user.getLogin());
        return view;
    }

    static class ViewHolder{
        @BindView(R.id.ivIcon) ImageView ivIcon;
        @BindView(R.id.tvLoginName) TextView tvLoginName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
