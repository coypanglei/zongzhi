package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.EventsReportedFContract;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2021 14:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EventsReportedFPresenter extends ReworkBasePresenter<EventsReportedFContract.Model, EventsReportedFContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager mRepositoryManager;

    @Inject
    public EventsReportedFPresenter(EventsReportedFContract.Model model, EventsReportedFContract.View rootView) {
        super(model, rootView);
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
        Map<String, Object> map = new HashMap<>(8);
        map.put("Name", name);
        map.put("type", type);
        map.put("SortType", sortType);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("status", status);
        map.put("PageSize", pageSize);
        Observable<BaseBean<EventsReportedBean>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getEventRecord(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, bean -> {
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
        Map<String, Object> map = new HashMap<>(8);
        map.put("Name", name);
        map.put("type", type);
        map.put("SortType", sortType);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        Observable<BaseBean<EventsReportedBean>> observable = mRepositoryManager.obtainRetrofitService(EventsReportedService.class)
                .getEventRecord(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler, bean -> {
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
        Observable<BaseBean<GridInformationBean>> observable =
                mRepositoryManager.obtainRetrofitService(InformationService.class)
                        .getDepartment(SignUtil.paramSign(null));
        commonGetData(observable, mErrorHandler, gridInformationBean -> {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getIsInGrid() {
        Observable<BaseBean<Boolean>> isInGrid = mRepositoryManager.obtainRetrofitService(EventsReportedService.class).getIsInGrid(SignUtil.paramSign(null));
        commonGetData(isInGrid, mErrorHandler, o -> {
            mRootView.setAddressCanChanged(o);
        });
    }
}
