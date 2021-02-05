package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class EventsReportedPresenter extends ReworkBasePresenter<EventsReportedContract.Model, EventsReportedContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EventsReportedPresenter(EventsReportedContract.Model model, EventsReportedContract.View rootView) {
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
     * 获取事件列表 已上报 或是暂存
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param name          name 搜索关键字
     * @param type          0 提交人 1 处理人
     * @param status        订单状态
     * @param sortType      分类状态
     */
    public void getEvents(boolean pullToRefresh, boolean isLoadMore,
                          String name, int type,
                          int status, String sortType) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getEventRecord(name, type, status, sortType, pageSize, ignoreNumber), mErrorHandler, bean -> {
            try {
                mRootView.setNewData(bean.getList(), isLoadMore);
                handlePaginLoadMore((bean == null || bean.getList() == null) ? 0 : bean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取事件列表 已上报 或是暂存
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param name          name 搜索关键字
     * @param type          0 提交人 1 处理人
     * @param sortType      分类状态
     */
    public void getEvents(boolean pullToRefresh, boolean isLoadMore,
                          String name, int type, String sortType) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getEventRecord(name, type, sortType, pageSize, ignoreNumber), mErrorHandler, bean -> {
            try {
                mRootView.setNewData(bean.getList(), isLoadMore);
                handlePaginLoadMore((bean == null || bean.getList() == null) ? 0 : bean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取网格信息
     */
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
