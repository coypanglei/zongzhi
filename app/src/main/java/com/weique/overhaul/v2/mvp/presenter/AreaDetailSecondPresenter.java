package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.AreaDetailSecondContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 15:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AreaDetailSecondPresenter extends ReworkBasePresenter<AreaDetailSecondContract.Model, AreaDetailSecondContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AreaDetailSecondPresenter(AreaDetailSecondContract.Model model, AreaDetailSecondContract.View rootView) {
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

    public void getAreaDetailsListData(String id) {
        commonGetData(mModel.getAreaDetailsListData(pageSize, ignoreNumber, id), mErrorHandler, itemBean -> {
            mRootView.setAreaDetailsListData(itemBean);
        });
    }

    public void getAreaDetailsSecondListData(String id, String startDate, String endDate, String type) {
        String startTime = "";
        String endTime = "";
        int compareTo = startDate.compareTo(endDate);
        if (compareTo > 0) {
            //"date1 大于 date2";
            startTime = endDate;
            endTime = startDate;
        } else if (compareTo == 0) {
//            ArmsUtils.makeText("至少选择时间间隔要大于一天");
//            return;
        } else if (compareTo < 0) {
            startTime = startDate;
            endTime = endDate;
        }
        commonGetData(mModel.getAreaDetailsSecondListData(id, startTime, endTime, type), mErrorHandler, itemBean -> {
            mRootView.setAreaDetailsSecondListData(itemBean);
        });
    }
}
