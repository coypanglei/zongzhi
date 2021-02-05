package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.InformationListForAddressDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2020 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class InformationListForAddressDetailPresenter extends ReworkBasePresenter<InformationListForAddressDetailContract.Model, InformationListForAddressDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InformationListForAddressDetailPresenter(InformationListForAddressDetailContract.Model model, InformationListForAddressDetailContract.View rootView) {
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

    public void getInformationListByStarters(boolean pullToRefresh, boolean isLoadMore, String sId, String eId) {
        handlePaging(pullToRefresh, isLoadMore);
        Map<String, Object> map = new HashMap<>();
        map.put("StandardAddressId", sId);
        map.put("ElementId", eId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        commonGetData(mModel.getInformationListByStarters(map), mErrorHandler, list -> {
            mRootView.setInformationData(list, isLoadMore);
            handlePaginLoadMore(list == null ? 0 : list.size());
        });
    }

    public void getDataStructureInJson(InformationTypeOneSecondBean.ElementListBean listBean, String departmentId) {
        commonGetData(mModel.getDataStructureInJson(listBean.getElementTypeId()), mErrorHandler, s -> {
            if (StringUtil.isNotNullString(s)) {
                ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                        .withString(ARouerConstant.ID, listBean.getId())
                        .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                        .withString(ARouerConstant.TITLE, listBean.getName())
                        .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, s)
                        .withString(InformationDynamicFormSelectActivity.TYPE_ID, listBean.getElementTypeId())
                        .navigation();
            } else {
                ArmsUtils.makeText("获取表单数据失败");
            }
        });
    }
}
