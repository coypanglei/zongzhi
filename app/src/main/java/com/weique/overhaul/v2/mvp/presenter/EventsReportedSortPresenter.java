package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.contract.EventsReportedSortContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;

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
@ActivityScope
public class EventsReportedSortPresenter extends ReworkBasePresenter<EventsReportedSortContract.Model, EventsReportedSortContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EventsReportedSortPresenter(EventsReportedSortContract.Model model, EventsReportedSortContract.View rootView) {
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
     * 获取 事件类型列表
     *
     * @param typeId typeId 上级id
     * @param source 上级跳转界面
     */
    public void getEvents(String typeId, boolean needAdd, String source) {
        commonGetData(mModel.getEvents(typeId), mErrorHandler, bean -> {
            try {
                if (bean == null || bean.getListBean() == null || bean.getListBean().size() <= 0) {
                    ArmsUtils.makeText("无资源信息");
                    return;
                }
                //添加需要全部
                if (!needAdd && RouterHub.APP_EVENTSREPORTEDACTIVITY .equals(source)) {
                    EventsReportedSortBean.ListBean listBean =
                            new EventsReportedSortBean.ListBean("", "全部", "QUANBU",
                                    "0", "", "", "", true, 2,"");
                    bean.getListBean().add(0, listBean);
                }
                mRootView.setData(bean.getListBean(), needAdd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
