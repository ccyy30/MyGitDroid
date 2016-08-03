package com.feicui.mygitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.favorite.dao.DBHelper;
import com.feicui.mygitdroid.favorite.dao.RepoGroupDao;
import com.feicui.mygitdroid.favorite.model.RepoGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的收藏fragment
 * Created by Administrator on 2016/8/3 0003.
 */
public class FavoriteFragment extends Fragment{

    //左上方显示的仓库类别文字
    @BindView(R.id.tvGroupType)
    TextView tvGroupType;

    //右上方弹出popMenu的ImageButton
    @BindView(R.id.btnFilter)
    ImageButton btnFilter;

    //显示的列表
    @BindView(R.id.listView)
    ListView listView;

    private RepoGroupDao dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        dao = new RepoGroupDao(DBHelper.getInstance(getContext()));

    }

    //点击右上角按钮
    @OnClick(R.id.btnFilter)
    public void showPopupMenu(View view){
        //点击弹出popmenu菜单
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        //添加menu菜单，菜单上只有全部和未分类
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        //菜单中继续添加其他的分类
        Menu menu = popupMenu.getMenu();
        //先从数据库获取数据
        List<RepoGroup> datas = dao.queryForAll();
        //添加菜单条目，参数1组id   2条目id   3排序id   4标题
        for(RepoGroup repoGroup:datas){
            menu.add(Menu.NONE,(int)repoGroup.getId(),Menu.NONE,repoGroup.getName());
        }

        //设置点击popmenu中条目的监听
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //获取到点击的标题
                String title = item.getTitle().toString();
                //点击后切换左上角仓库类别
                tvGroupType.setText(title);
                //TODO 更新listview中的数据

                return true;
            }
        });
        popupMenu.show();
    }

}
