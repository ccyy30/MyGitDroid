package com.feicui.mygitdroid.hotrepo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicui.mygitdroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class RepoListFragment extends Fragment {

    @BindView(R.id.lvRepos)
    ListView listView;
    //刷新控件
    @BindView(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrClassicFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        //测试数据
        String[] datas = {
                "aaaaaaaaaa",
                "bbbbbbbbbb",
                "fsfsdffd",
                "tesfrtge"
        };

        ArrayAdapter adapter = new ArrayAdapter(
                getContext(),
                android.R.layout.simple_list_item_1,
                datas
        );

        listView.setAdapter(adapter);

        initPullToRefresh();
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
                //做具体的业务处理(数据刷新加载)

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

}
