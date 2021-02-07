package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.AddressAddAlertContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
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
public class AddressAddAlertPresenter extends ReworkBasePresenter<AddressAddAlertContract.Model, AddressAddAlertContract.View> {
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
    public AddressAddAlertPresenter(AddressAddAlertContract.Model model, AddressAddAlertContract.View rootView) {
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
     * 提交详情
     *
     * @param standardAddressBean standardAddressBean
     * @param state               state
     */
    public void submitStandardAddress(StandardAddressBean standardAddressBean, int state) {
        try {
            if (state == EventBusConstant.ADD) {
                commonGetData(mModel.submitStandardAddressAdd(standardAddressBean), mErrorHandler, standardAddressStairBaseBean -> {
                    EventBus.getDefault().post(new EventBusBean(state, "", standardAddressStairBaseBean), RouterHub.APP_STANDARDADDRESSONENEWACTIVITY);
                    mRootView.killMyself();
                });
            } else if (state == EventBusConstant.ALERT) {
                commonGetData(mModel.submitStandardAddressAlert(standardAddressBean), mErrorHandler, standardAddressStairBaseBean -> {
                    EventBus.getDefault().post(new EventBusBean(state, "", standardAddressStairBaseBean), RouterHub.APP_STANDARDADDRESSONENEWACTIVITY);
                    EventBus.getDefault().post(new EventBusBean(state, "", standardAddressBean), RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
                    mRootView.killMyself();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param departmentId departmentId
     */
    public void getDepartmentDownInfo(String departmentId) {
        commonGetData(mModel.getDepartmenDownInfo(departmentId, pageSize, ignoreNumber), mErrorHandler, standardAddressStairBean -> {
            List<StandardAddressStairBean.AddressTypeBean> addressType = standardAddressStairBean.getAddressType();
            mRootView.showPopup(addressType);
        });
    }

    public void getGridByDepartmentId(String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        Observable<BaseBean<GridInformationBean>> observable = mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
        commonGetData(observable, mErrorHandler,
                gridInformationBean -> {
                    if (gridInformationBean == null || StringUtil.isNullString(gridInformationBean.getPointsInJSON())) {
                        ArmsUtils.makeText("获取用户区域信息失败");
                        return;
                    }
                    mRootView.setGrid(gridInformationBean.getPointsInJSON());
                });
    }
}
