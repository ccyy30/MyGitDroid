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

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HotRepoListFragment extends Fragment {

    @BindView(R.id.lvRepos)
    ListView listView;

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
    }
}
