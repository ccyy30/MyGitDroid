package com.feicui.mygitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.text.GetChars;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.favorite.dao.DBHelper;
import com.feicui.mygitdroid.favorite.dao.LocalRepoDao;
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

    private RepoGroupDao repoGroupDao;
    private LocalRepoDao localRepoDao;
    private ActivityUtils activityUtils;
    private FavoriteAdapter favoriteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this,view);
        repoGroupDao = new RepoGroupDao(DBHelper.getInstance(getContext()));
        localRepoDao = new LocalRepoDao(DBHelper.getInstance(getContext()));
        favoriteAdapter = new FavoriteAdapter(getContext());
        listView.setAdapter(favoriteAdapter);
        //注册上下文菜单，点击listview时弹出上下文菜单
        registerForContextMenu(listView);
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
        List<RepoGroup> datas = repoGroupDao.queryForAll();
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
                LogUtils.i(item.getItemId()+"");
                //更新listview中的数据
                setData(item.getItemId());
                return true;
            }
        });
        popupMenu.show();
    }
    //更新listview中的数据
    private void setData(int itemId) {
        switch (itemId){
            case R.id.repo_group_all:
                favoriteAdapter.addAll(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                favoriteAdapter.addAll(localRepoDao.queryForNoGroup());
                break;
            default:
                favoriteAdapter.addAll(localRepoDao.queryForGroupId(itemId));
                break;
        }
    }

    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //判断如果是点击了listview弹出的上下文菜单的话,因为点击其他按钮也可能弹出上下文菜单，这里为了区分
        if(v.getId() == R.id.listView){
            //加载菜单的布局
            getActivity().getMenuInflater().inflate(R.menu.menu_context_favorite,menu);
            //获取菜单中的子菜单，因为子菜单在布局中只有未分类一个item，所以还需要将其他的类别添加到子菜单中
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            //获取类别数据
            List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
            //添加类别数据到子菜单
            for(RepoGroup repoGroup:repoGroups){
                subMenu.add(R.id.menu_group_move,(int)repoGroup.getId(),Menu.NONE,repoGroup.getName());
            }
        }

    }

    //当点击上下文菜单子选项时

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //获取itemId进行判断
        //如果点击的是删除
        int id = item.getItemId();
        if(id == R.id.delete){
            LogUtils.i("删除");
            return true;
        }
        if(id == R.id.sub_menu_move){
            LogUtils.i("移动至");
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
