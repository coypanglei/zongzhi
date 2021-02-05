package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.StaffListContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 19:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StaffListPresenter extends ReworkBasePresenter<StaffListContract.Model, StaffListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StaffListPresenter(StaffListContract.Model model, StaffListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取员工列表
     *
     * @param pullToRefresh
     * @param isLoadMore
     */
    public void getStaffList(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("Id", UserInfoUtil.getUserInfo().getUid());
        commonGetData(mModel.getStaffList(map), mErrorHandler, staffListBean -> {
            try {
                mRootView.setData(staffListBean.getList(), isLoadMore);
                handlePaginLoadMore((staffListBean == null || staffListBean.getList() == null) ? 0 : staffListBean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 上报温度
     *
     * @param contents contents
     */
    public void sendStaffHeat(String contents) {
        Map<String, Object> map = new HashMap<>();
        map.put("", contents);
        map.put("Id", UserInfoUtil.getUserInfo().getUid());
        commonGetData(mModel.sendStaffHeat(map), mErrorHandler, o -> {
        });
    }
}
