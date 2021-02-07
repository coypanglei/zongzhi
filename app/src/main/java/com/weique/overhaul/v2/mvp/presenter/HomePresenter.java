package com.weique.overhaul.v2.mvp.presenter;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@FragmentScope
public class HomePresenter extends ReworkBasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager iRepositoryManager;

    public static final int MAX_ITEM_NUMBER = 1000;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void getHomeModuleLabel(String url) {
        commonGetData(mModel.getHomeModuleLabel(url), mErrorHandler, homeMenuItemBeans -> {
            try {
                if (homeMenuItemBeans == null || homeMenuItemBeans.size() <= 0) {
                    mRootView.setMenuData(null, null, null, url);
                    return;
                }
                List<HomeMenuItemBean> newItems = null;
                List<Boolean> booleans = null;
                //临时
                List<HomeMenuItemBean> tempItems = new ArrayList<>();
                tempItems.addAll(homeMenuItemBeans);
                Iterator<HomeMenuItemBean> iterator = tempItems.iterator();
                while (iterator.hasNext()) {
                    if (!iterator.next().isDisplay()) {
                        iterator.remove();
                    }
                }
                newItems = new ArrayList<>();
                if (tempItems.size() <= MAX_ITEM_NUMBER) {
                    newItems.addAll(tempItems);
                } else {
                    for (HomeMenuItemBean homeMenuItemBean : tempItems) {
                        if (homeMenuItemBean.isDisplay()) {
                            newItems.add(homeMenuItemBean);
                            if (newItems.size() >= MAX_ITEM_NUMBER) {
                                break;
                            }
                        }
                    }
                    newItems.add(new HomeMenuItemBean(
                            mRootView.getActivity().getResources().getString(R.string.more),
                            MAX_ITEM_NUMBER,
                            R.drawable.home_icon_gengduo,
                            RouterHub.APP_INTEGRATEDWITHACTIVITY));
                }
                //计算指示器数量
                booleans = new ArrayList<>();
                if (newItems.size() > 0) {
                    int indicatorNumber = (int) Math.ceil(newItems.size() / 5D);
                    if (indicatorNumber > 0) {
                        for (int i = 0; i < indicatorNumber; i++) {
                            if (i == 0) {
                                booleans.add(true);
                            } else {
                                booleans.add(false);
                            }
                        }
                    }
                }
                mRootView.setMenuData(tempItems, newItems, booleans, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getNotice() {
        //跑马灯使用之后去5条
        commonGetData(mModel.getNotice(5, 0), mErrorHandler, announcementListBean -> {
            try {
                List<String> strings = new ArrayList<>();
                List<AnnouncementListBean.ListBean> list = announcementListBean.getList();
                if (list != null && list.size() > 0) {
                    for (AnnouncementListBean.ListBean listBean : list) {
                        if (StringUtil.isNotNullString(listBean.getSummary())) {
                            strings.add(listBean.getSummary());
                        } else if (StringUtil.isNotNullString(listBean.getTitle())) {
                            strings.add(listBean.getTitle());
                        }
                    }
                }
                mRootView.startRun(strings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取相机权限
     */
    public void getPermissionCamera() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                try {
                    ARouter.getInstance().build(RouterHub.APP_CAPTUREACTIVITY).navigation(mRootView.getActivity(), Constant.REQUEST_CODE_SCAN);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }, Manifest.permission.CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void getPermission() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, new CommonPermissionUtil.PermissionLisenter() {
                @Override
                public void getPermissionSuccess() {
                    try {
                        if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                            ARouter.getInstance().build(RouterHub.APP_SIGNINACTIVITY)
                                    .navigation();
                        } else {
                            new CommonDialog.Builder(mRootView.getActivity()).setTitle("提醒")
                                    .setContent("需要打开GPS 定位服务")
                                    .setPositiveButton("去设置", (v, commonDialog) -> {
                                        //跳转GPS设置界面
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        mRootView.getActivity().startActivity(intent);
                                    })
                                    .setNegativeButton("取消", null).create().show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, Constant.PERMISSIONS_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSignStatus() {
        commonGetData(mModel.getSignStatus(), mErrorHandler, b -> {
            mRootView.setSignBtnStatus(b);
        });
    }


    public void gridOperatorInformation() {
        commonGetData(mModel.gridOperatorInformation(), mErrorHandler, gridInformationBean -> {
            try {
                if (gridInformationBean != null && StringUtil.isNotNullString(gridInformationBean.getPointsInJSON())) {
                    mRootView.getGridInfoSuccess(gridInformationBean.getPointsInJSON());
                } else {
                    ArmsUtils.makeText("获取用户网格信息失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getIsInGrid() {
        commonGetData(mModel.getIsInGrid(), mErrorHandler, o -> {
            mRootView.setAddressCanChanged(o);
        });
    }
}
