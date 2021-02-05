package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerMyParticipateEventDetailComponent;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.MyParticipateEventDetailItemBean;
import com.weique.overhaul.v2.mvp.presenter.MyParticipateEventDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyParticipateEventDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 事件详情
 *
 * @author  GK
 */
@Route(path = RouterHub.APP_MYPARTICIPATEEVENTDETAILACTIVITY)
public class MyParticipateEventDetailActivity extends BaseActivity<MyParticipateEventDetailPresenter> implements MyParticipateEventDetailContract.View {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_point)
    LinearLayout layoutPoint;
    @BindView(R.id.item_label)
    LinearLayout itemLabel;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private MyParticipateEventDetailAdapter adapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyParticipateEventDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_participate_event_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("事件详情");
        initRecycler();
    }

    private void initRecycler() {

        try {
            List<MyParticipateEventDetailItemBean> list = new ArrayList<>();
            list.add(new MyParticipateEventDetailItemBean(0));
            list.add(new MyParticipateEventDetailItemBean(0));
            list.add(new MyParticipateEventDetailItemBean(1));
            list.add(new MyParticipateEventDetailItemBean(0));


            adapter = new MyParticipateEventDetailAdapter(list);
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4));
            recyclerView.setAdapter(adapter);
            recyclerView.setClipToPadding(false);
            adapter.setNewData(list);

            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        switch (view.getId()) {


                            default:
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        finish();
    }


}
