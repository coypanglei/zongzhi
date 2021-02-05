package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerIntegratedWithComponent;
import com.weique.overhaul.v2.mvp.contract.IntegratedWithContract;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.presenter.IntegratedWithPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuGridAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 综合办里界面
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_INTEGRATEDWITHACTIVITY)
public class IntegratedWithActivity extends BaseActivity<IntegratedWithPresenter> implements IntegratedWithContract.View {
    public static final String LIST = "LIST";
    @Autowired(name = LIST)
    ArrayList<HomeMenuItemBean> mMenuData;
    @Inject
    HomeMenuGridAdapter homeMenuGridAdapter;
    @Inject
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.rcv)
    RecyclerView rcv;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIntegratedWithComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_integrated_with;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.integrated_with));
        ARouter.getInstance().inject(this);
        initList();
    }

    private void initList() {
        ArmsUtils.configRecyclerView(rcv, gridLayoutManager);
        rcv.setAdapter(homeMenuGridAdapter);
        rcv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .colorResId(R.color.white)
                .sizeResId(R.dimen.dp_15)
                .build());
        homeMenuGridAdapter.setOnItemClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                HomeMenuItemBean homeMenuItemBean = (HomeMenuItemBean) adapter.getItem(position);
                if (homeMenuItemBean.getTarget().equals(RouterHub.APP_INTEGRATEDWITHACTIVITY)) {
                    if (mMenuData != null && mMenuData.size() > 0) {
                        ARouter.getInstance().build(homeMenuItemBean.getTarget())
                                .withParcelableArrayList(IntegratedWithActivity.LIST, mMenuData)
                                .navigation();
                    }
                } else if (homeMenuItemBean.getTarget().equals(RouterHub.APP_CAPTUREACTIVITY)) {
                    mPresenter.getPermissionCamera();
                } else if (homeMenuItemBean.getTarget().equals(RouterHub.APP_SIGNINACTIVITY)) {
                    mPresenter.getPermission();
                } else if (homeMenuItemBean.getTarget().equals(RouterHub.APP_CHATSELECTACTIVITY)) {
                    if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                        ARouter.getInstance()
                                .build(RouterHub.APP_CHATSELECTACTIVITY)
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_CHATSELECTACTIVITY)
                                .withString(ARouerConstant.TITLE, getString(R.string.video_hs))
                                .navigation();
                    }
                } else {
                    if (homeMenuItemBean.getTarget().equals(RouterHub.APP_ADDRESSBOOKACTIVITY)) {
                        if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                            ARouter.getInstance().build(RouterHub.APP_ADDRESSLOOKLISTACTIVITY)
                                    .navigation();
                        } else {
                            ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                    .withString(ARouerConstant.TITLE, getString(R.string.book_address))
                                    .navigation();
                        }
                    } else {
                        launchActivityByRouter(homeMenuItemBean.getTarget().trim());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Iterator<HomeMenuItemBean> iterator = mMenuData.iterator();
        while (iterator.hasNext()) {
            HomeMenuItemBean next = iterator.next();
            if (!next.isDisplay()) {
                iterator.remove();
            }
        }
        homeMenuGridAdapter.setNewData(mMenuData);
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
     * 获取context
     *
     * @return Activity
     */
    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 更新界面数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constant.REQUEST_CODE_SCAN:
                        if (null != data) {
                            Bundle bundle = data.getExtras();
                            if (bundle == null) {
                                return;
                            }
                            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                                String result = bundle.getString(CodeUtils.RESULT_STRING);
                                if (TextUtils.isEmpty(result)) {
                                    ArmsUtils.makeText("二维码内容无效");
                                } else {
                                    ARouter.getInstance().build(RouterHub.APP_SCANRESULTACTIVITY)
                                            .withString(ARouerConstant.SOURCE, result)
                                            .navigation();
                                }
                            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                                ArmsUtils.makeText("解析二维码失败");
                            }
                        }
                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
