package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerMyWorkRecordComponent;
import com.weique.overhaul.v2.mvp.contract.MyWorkRecordContract;
import com.weique.overhaul.v2.mvp.model.entity.TabEntity;
import com.weique.overhaul.v2.mvp.presenter.MyWorkRecordPresenter;
import com.weique.overhaul.v2.mvp.ui.fragment.WorkRecordFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.ScreeningPopup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 个人中心--工作记录
 *
 * @author GK
 */
@Route(path = RouterHub.APP_MYWORKRECORDACTIVITY)
public class MyWorkRecordActivity extends BaseActivity<MyWorkRecordPresenter> implements MyWorkRecordContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_2)
    CommonTabLayout mTabLayout_2;
    @BindView(R.id.screening)
    TextView screening;
    @BindView(R.id.input_layout)
    RelativeLayout layout;
    @BindView(R.id.fl_change)
    FrameLayout flChange;
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private ScreeningPopup screeningPopup;

    private String[] mTitles = {"今日工作", "历史工作"};
    private int[] mIconUnselectIds = {
            R.drawable.my_group_null, R.drawable.my_group_null
    };
    private int[] mIconSelectIds = {
            R.drawable.my_group, R.drawable.my_group
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyWorkRecordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_work_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("工作记录");
        addTabToTabLayout();

    }

    private void addTabToTabLayout() {
        try {
            mFragments2.add(WorkRecordFragment.newInstance());
            mFragments2.add(WorkRecordFragment.newInstance());

            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            }
            mTabLayout_2.setTabData(mTabEntities, this, R.id.fl_change, mFragments2);
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


    @OnClick(R.id.screening)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (screeningPopup == null) {
                screeningPopup = new ScreeningPopup(this);
            }

            screening.setTextColor(getResources().getColor(R.color.colorPrimary));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.down_arrows);
            screening.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

            screeningPopup.showPopupWindow(layout);

            screeningPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    screening.setTextColor(getResources().getColor(R.color.black));
                    Drawable drawable = ContextCompat.getDrawable(MyWorkRecordActivity.this, R.drawable.up_arrows);
                    screening.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
