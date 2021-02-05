package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.TaskListHomeContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2020 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class TaskListHomePresenter extends ReworkBasePresenter<TaskListHomeContract.Model, TaskListHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    Gson gson;

    @Inject
    public TaskListHomePresenter(TaskListHomeContract.Model model, TaskListHomeContract.View rootView) {
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
     * 获取任务列表信息
     */
    public void getTaskList(boolean pullToRefresh, boolean isLoadMore) {
        handlePaging(pullToRefresh, isLoadMore);
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        commonGetData(mModel.getTaskList(map), mErrorHandler, taskListBean -> {
            try {
                mRootView.setNewData(taskListBean.getList(), isLoadMore);
                handlePaginLoadMore((taskListBean == null || taskListBean.getList() == null) ? 0 : taskListBean.getList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void GetDepartment() {
        commonGetData(mModel.getGetDepartment(), mErrorHandler, gridInformationBean -> {
            try {
                mRootView.gotoMapTv(gridInformationBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     */
    public void getPermission() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> {
                try {
                    if (AppUtils.isLocationEnabled(mRootView.getContext())) {
                        ARouter.getInstance().build(RouterHub.APP_SIGNINACTIVITY)
                                .navigation();
                    } else {
                        new CommonDialog.Builder(mRootView.getContext()).setTitle("提醒")
                                .setContent("需要打开GPS 定位服务")
                                .setPositiveButton("去设置", (v, commonDialog) -> {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    mRootView.getContext().startActivity(intent);
                                })
                                .setNegativeButton("取消", null).create().show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Constant.PERMISSIONS_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取GUID
     *
     * @param missionConditionsBean missionConditionsBean
     */
    public void getGetNewGuidForApp(TaskListBean.ListBean.MissionConditionsBean missionConditionsBean) {
        commonGetData(mModel.getGetNewGuidForApp(), mErrorHandler, id -> {
            try {
                mRootView.setGotoNewGuid(id, missionConditionsBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 根据元素id获取 其动态表单
     *
     * @param informationDetailBean informationDetailBean
     * @param type                  0.信息采集 1.事件上报
     */
    public void getDataStructureInJson(InformationDetailBean informationDetailBean, @EnumConstant.DynamicBeanByType int type) {
        commonGetData(mModel.getDataStructureInJson(informationDetailBean.getElementTypeId(), type), mErrorHandler, s -> {
            try {
                if (StringUtil.isNotNullString(s)) {
                    mRootView.setDynamicForm(s, informationDetailBean);
                } else {
                    ArmsUtils.makeText("获取表单数据失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 根据元素id获取 其动态表单
     *
     * @param missionConditionsBean missionConditionsBean
     * @param type                  0.信息采集 1.事件上报
     */
    public void getDataStructureInJson(TaskListBean.ListBean.MissionConditionsBean missionConditionsBean, @EnumConstant.DynamicBeanByType int type) {
        commonGetData(mModel.getDataStructureInJson(missionConditionsBean.getElementTypeId(), type),
                mErrorHandler, s -> {
                    try {
                        if (StringUtil.isNotNullString(s)) {
                            mRootView.setDynamicForm(s, missionConditionsBean, type);
                        } else {
                            ArmsUtils.makeText("获取表单数据失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
