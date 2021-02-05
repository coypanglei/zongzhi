package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ARouterUtils;

import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerUpEventComponent;
import com.weique.overhaul.v2.mvp.contract.UpEventContract;
import com.weique.overhaul.v2.mvp.presenter.UpEventPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.UpEventPreviewAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
@Route(path = RouterHub.APP_UPEVENTACTIVITY)
public class UpEventActivity extends BaseActivity<UpEventPresenter> implements UpEventContract.View {
    @Inject
    public UpEventPreviewAdapter upEventPreviewAdapter;
    @BindView(R.id.toolbar_submit)
    RelativeLayout toolbarSubmit;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.event_describe)
    EditText eventDescribe;
    @BindView(R.id.event_list)
    RecyclerView eventList;
    @BindView(R.id.event_label)
    RecyclerView eventLabel;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUpEventComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_up_event;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ArmsUtils.configRecyclerView(eventList,new GridLayoutManager(this, 4));
        eventList.setAdapter(upEventPreviewAdapter);

    }

    @OnClick({R.id.event_type_view, R.id.photo_media_view, R.id.record_view,})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                //事件类型选择
                case R.id.event_type_view:
                    break;
                //上传图片/视频
                case R.id.photo_media_view:
                    break;
                //录音
                case R.id.record_view:
                    break;
                default:
            }
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
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * Activity
     *
     * @return Activity
     */
    @Override
    public Activity getActivity() {
        return this;
    }

}
