package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerGuideComponent;
import com.weique.overhaul.v2.mvp.contract.GuideContract;
import com.weique.overhaul.v2.mvp.presenter.GuidePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 引导页
 * ================================================
 *
 * @author GK
 * Description:
 * <p>
 * ================================================
 */
@Route(path = RouterHub.APP_GUIDEACTIVITY)
public class GuideActivity extends BaseActivity<GuidePresenter> implements GuideContract.View {

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @BindView(R.id.banner_guide_foreground)
    BGABanner mForegroundBanner;
    @BindView(R.id.jump_layout)
    LinearLayout jumpLayout;

    @Inject
    ACache aCache;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGuideComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guide;
    }

    /**
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.jump_layout, () -> {
                if (UserInfoUtil.getUserInfo() == null) {
                    ARouterUtils.navigation(this, RouterHub.APP_LOGINACTIVITY);
                } else {
                    ARouterUtils.navigation(this, RouterHub.APP_MAINACTIVITY);
                }
                killMyself();
            });
            processLogic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processLogic() {
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        // 设置数据源
        try {
            mBackgroundBanner.setData(
                    R.drawable.background1,
                    R.drawable.background2,
                    R.drawable.background3);

            mForegroundBanner.setData(
                    R.drawable.front,
                    R.drawable.front1,
                    R.drawable.front2);
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
    public void killMyself() {
        finish();
    }


    @Override
    public Activity getActivity() {
        return this;
    }
}
