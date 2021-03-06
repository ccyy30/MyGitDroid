package com.feicui.mygitdroid.github.hotrepo.repolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.mygitdroid.R;
import com.feicui.mygitdroid.commons.ActivityUtils;
import com.feicui.mygitdroid.commons.LogUtils;
import com.feicui.mygitdroid.components.FooterView;
import com.feicui.mygitdroid.favorite.dao.DBHelper;
import com.feicui.mygitdroid.favorite.dao.LocalRepoDao;
import com.feicui.mygitdroid.favorite.model.LocalRepo;
import com.feicui.mygitdroid.favorite.model.LocalRepoConvert;
import com.feicui.mygitdroid.github.hotrepo.Language;
import com.feicui.mygitdroid.github.hotrepo.repolist.modle.Repo;
import com.feicui.mygitdroid.github.hotrepo.repolist.view.RepoListView;
import com.feicui.mygitdroid.github.repoinfo.RepoInfoActivity;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class RepoListFragment extends Fragment implements RepoListView {

    @BindView(R.id.lvRepos)
    ListView listView;
    //刷新控件
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @BindView(R.id.errorView)
    TextView tvErrorView;

    @BindView(R.id.emptyView)
    TextView tvEmptyView;

    private RepoListPresenter repoListPresenter;
    private ActivityUtils activityUtils;
    private RepoListAdapter repoListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this,view);
        repoListAdapter = new RepoListAdapter(getContext());
        Language language = (Language)getArguments().getSerializable(KEY_LANGUAGE);
        repoListPresenter = new RepoListPresenter(this,language);

        //初始化下拉刷新
        initPullToRefresh();
        //初始化上拉加载更多
        initLoadMore();
        //进入页面的时候如果发现没有数据则自动刷新
        if(repoListAdapter.getCount() == 0){
            ptrClassicFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrClassicFrameLayout.autoRefresh();
                }
            },200);
        }

        //由于模拟器上在移除上拉加载视图时出现异常，解决方案是在setAdapter之前先加入一个footView
        //在设置适配器后再移除
        listView.addFooterView(footerView);
        listView.setAdapter(repoListAdapter);
        //设置条目点击监听，跳转到仓库详情页
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RepoInfoActivity.open(getContext(), (Repo) repoListAdapter.getItem(i));
            }
        });
        //设置条目长按，添加到收藏
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获取当前长按的仓库对象
                Repo repo = repoListAdapter.getItem(i);
                //将Repo转换成LocalRepo（本地仓库）
                LocalRepo localRepo = LocalRepoConvert.convertToLocalRepo(repo);
                //添加到收藏，默认是未分类
                new LocalRepoDao(DBHelper.getInstance(getContext())).createOrUpdate(localRepo);
                activityUtils.showToast("收藏成功");
                return true;
            }
        });
        listView.removeFooterView(footerView);
    }
    //上拉加载的视图
    private FooterView footerView;

    //初始化上拉加载更多
    private void initLoadMore() {
        //创建上拉加载更多的视图
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            //listview滚动到底部调用
            @Override
            public void onLoadMore() {
                //上拉加载的业务
                repoListPresenter.loadMore();
            }
            // 是否正在加载中
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean isLoading() {
                //如果listview的尾部视图存在并且视图正在加载
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }
            // 是否已加载完成所有数据
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    //初始化刷新控件的样式
    private void initPullToRefresh() {
        //设置间隔两次刷新如果太短则不会触发新的刷新
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        //设置关闭头部刷新视图的时间
        ptrClassicFrameLayout.setDurationToCloseHeader(1500);
        //下拉刷新监听处理
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            //当下拉时，触发此函数
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //做具体的业务处理(数据下拉刷新)
                repoListPresenter.ptrRefresh();
            }
        });
        //修改下拉刷新时头部视图的效果
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(getContext());
        //刷新头部视图上所使用的文字，注意不能有！号，否则空指针
        storeHouseHeader.initWithString("hello world");
        //设置间距
        storeHouseHeader.setPadding(0,60,0,60);
        //设置头部视图
        ptrClassicFrameLayout.setHeaderView(storeHouseHeader);
        //添加一个默认的动画处理
        ptrClassicFrameLayout.addPtrUIHandler(storeHouseHeader);
        //设置背景颜色
        ptrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    @Override
    public void addRefreshData(List<Repo> datas) {
        repoListAdapter.addAll(datas);
    }

    @Override
    public void stopRefresh() {
        //停止刷新视图
        ptrClassicFrameLayout.refreshComplete();
    }

    @Override
    public void showContentView() {
        //显示刷新视图
        ptrClassicFrameLayout.setVisibility(View.VISIBLE);
        //隐藏错误和空白视图
        tvErrorView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        tvErrorView.setVisibility(View.VISIBLE);
        tvEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        ptrClassicFrameLayout.setVisibility(View.GONE);
        tvErrorView.setVisibility(View.GONE);
        tvEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showLoadMoreLoading() {
        //如果视图没有加在列表上则添加
        if(listView.getFooterViewsCount() == 0){
            LogUtils.i("555555");
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void hideLoadMore() {
        if(listView.getFooterViewsCount() > 0){
            listView.removeFooterView(footerView);
        }
    }

    @Override
    public void showLoadMoreErro(String erroMsg) {
        if(listView.getFooterViewsCount() == 0){
            listView.addFooterView(footerView);
        }
        footerView.showError();
        activityUtils.showToast(erroMsg);
    }

    @Override
    public void addLoadMoreData(ArrayList<Repo> datas) {
        repoListAdapter.addAll(datas);
        repoListAdapter.notifyDataSetChanged();
    }

    private static final String KEY_LANGUAGE = "key_language";

    public static RepoListFragment getInstance(Language language) {
        RepoListFragment fragment = new RepoListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(args);
        return fragment;
    }
}
