package com.feicui.mygitdroid.gank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.favorite.model.LocalRepo;
import com.feicui.mygitdroid.gank.model.GankItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class GankAdapter extends BaseAdapter {

    private ArrayList<GankItem> datas;
    private LayoutInflater inflater;

    public GankAdapter(Context context){
        datas = new ArrayList<GankItem>();
        inflater = LayoutInflater.from(context);
    }

    public void addAll(List<GankItem> gankItems){
        datas.clear();
        datas.addAll(gankItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public GankItem getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.layout_item_gank,null);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh = (ViewHolder) view.getTag();
        GankItem gankItem = datas.get(i);
        vh.gank_item.setText(gankItem.getDesc());
        return view;
    }

    static class ViewHolder{

        @BindView(R.id.gank_item)TextView gank_item;

        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
