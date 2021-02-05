package com.weique.overhaul.v2.mvp.ui.fragment.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterTableComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterTableContract;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterTablePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MsgContentFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 房间中心--子fragment
 *
 * @author  GK
 */
public class PartyCenterTableFragment extends BaseFragment<PartyCenterTablePresenter> implements PartyCenterTableContract.View {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private MsgContentFragmentAdapter adapter;
    private List<String> names;
    private String type;


    public static PartyCenterTableFragment newInstance(String i) {
        PartyCenterTableFragment fragment = new PartyCenterTableFragment();
        fragment.type = i;
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterTableComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party_center_table, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            initData();
            adapter = new MsgContentFragmentAdapter(getChildFragmentManager(), type);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

            // 更新适配器数据
            adapter.setList(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        names = new ArrayList<>();
        names.add("关注");
        names.add("推荐");
        names.add("热点");
        names.add("视频");
        names.add("小说");
        names.add("娱乐");
        names.add("问答");
        names.add("图片");
        names.add("科技");
        names.add("懂车帝");
        names.add("体育");
        names.add("财经");
        names.add("军事");
        names.add("国际");
        names.add("健康");
        names.add("健康1");
        names.add("健康2");
        names.add("健康3");
        names.add("健康4");
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }


}
