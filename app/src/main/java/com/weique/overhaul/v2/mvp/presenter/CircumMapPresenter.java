package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ObjectUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import javax.inject.Inject;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.CircumMapContract;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;
import com.weique.overhaul.v2.mvp.ui.activity.map.CircumMapActivity;
import com.weique.overhaul.v2.mvp.ui.popupwindow.PointSetPopup;
import com.weique.overhaul.v2.mvp.ui.popupwindow.PrefectureScreenPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ================================================
 * ================================================
 */
@ActivityScope
public class CircumMapPresenter extends ReworkBasePresenter<CircumMapContract.Model, CircumMapContract.View> {
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

    private PrefectureScreenPopup screenPopup;

    private PointSetPopup pointSetPopup;

    private List<PointsListBean.ElementPointsBean> elementPoints = new ArrayList<>();

    /**
     * 筛选的id
     */
    private String mEventualId = "";

    @Inject
    public CircumMapPresenter(CircumMapContract.Model model, CircumMapContract.View rootView) {
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
     * 获取所有坐标点
     */
    public void getPoints(Map<String, Object> map) {
        commonGetData(mModel.getAllAddressPoints(map), mErrorHandler, points -> {
            try {
                if (points.getElementPoints() == null || points.getElementPoints().size() <= 0) {
                    ArmsUtils.makeText("当前位置未获取到数据");
                    return;
                }

                elementPoints = points.getElementPoints();
                if (ObjectUtils.isNotEmpty(mEventualId)) {
                    List<PointsListBean.ElementPointsBean> Points = new ArrayList<>();
                    for (PointsListBean.ElementPointsBean bean : elementPoints) {
                        if (mEventualId.equals(bean.getElementTypeId())) {
                            Points.add(bean);
                        }
                    }
                    mRootView.setPoitons(Points);
                } else {
                    mRootView.setPoitons(points.getElementPoints());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 获取区域网格
     */
    public void getDepartmentsPoints(Map<String, Object> map) {
        commonGetData(mModel.getDepartmentsPoints(map), mErrorHandler, points -> {
            try {
                if (points == null || points.size() <= 0) {
                    ArmsUtils.makeText("当前位置未获取到网格");
                    return;
                }
                mRootView.setDepartmentsPoints(points);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 搜索弹框
     */
    public void showPopup(int id, int width) {
        if (screenPopup == null) {
            screenPopup = new PrefectureScreenPopup(mRootView.getActivity(), iRepositoryManager, mErrorHandler, mRootView, width);
            screenPopup.setListener(new PrefectureScreenPopup.OnPrefectureScreenListener() {
                @Override
                public void onSureClick(String eventualId) {
                    Timber.e(eventualId);
                    mEventualId = eventualId;
                    if (ObjectUtils.isNotEmpty(mEventualId)) {
                        List<PointsListBean.ElementPointsBean> Points = new ArrayList<>();
                        for (PointsListBean.ElementPointsBean bean : elementPoints) {
                            if (eventualId.equals(bean.getElementTypeId())) {
                                Points.add(bean);
                            }
                        }
                        mRootView.setTypeId(Points);
                    } else {
                        mRootView.setTypeId(elementPoints);
                    }

                }
            });
        }

        if (!screenPopup.isShowing()) {

            screenPopup.showPopupWindow(id);
        }

    }


    /**
     * 点集合弹窗
     */
    public void showPointsPopup(int width, List ids) {
        PointSetPopup pointSetPopup = new PointSetPopup(mRootView.getActivity(), iRepositoryManager, mErrorHandler, mRootView, width);

        pointSetPopup.getPointsList(ids);
        if (!pointSetPopup.isShowing()) {
            pointSetPopup.showPopupWindow();
        }
        pointSetPopup.setListener(PointsDetailBean -> {
            Map<String, Object> map = new HashMap<>();
            map.put("ElementId",PointsDetailBean.getElementTypeId());
            commonGetData(mModel.getDataStructureInJson(map), mErrorHandler, DataStructureInJson -> {
                try {
                    Timber.e("DataStructureInJson" + DataStructureInJson);
                    mRootView.setClickPoint(DataStructureInJson,PointsDetailBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }

    /**
     * 点击获取点的详情
     */
    public void clickPoint(String elementTypeId, CircumMapActivity.MyItem myItem) {
        Map<String, Object> map = new HashMap<>();
        map.put("ElementId", elementTypeId);
        commonGetData(mModel.getDataStructureInJson(map), mErrorHandler, DataStructureInJson -> {
            try {
                Timber.e("DataStructureInJson" + DataStructureInJson);
                mRootView.setClickPoint(DataStructureInJson, myItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
