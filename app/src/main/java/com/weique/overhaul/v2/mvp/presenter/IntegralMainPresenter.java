package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.IntegralMainContract;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;

import java.util.HashMap;
import java.util.Map;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class IntegralMainPresenter extends ReworkBasePresenter<IntegralMainContract.Model, IntegralMainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IntegralMainPresenter(IntegralMainContract.Model model, IntegralMainContract.View rootView) {
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
     * 获取积分总和
     */
    public void getIntegralDetails(boolean pullToRefresh, boolean isLoadMore, String time) {
        Map<String, Object> map = new HashMap();
        handlePaging(pullToRefresh, isLoadMore);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        if (ObjectUtils.isNotEmpty(time)) {
            map.put("dateTime", time);
        }
        commonGetData(mModel.GetIntegralDetails(map), mErrorHandler, Integral -> {
            mRootView.getIntegralDetails(Integral, isLoadMore);
            handlePaginLoadMore((Integral == null || Integral.getList() == null) ? 0 : Integral.getList().size());
        });
    }


}
