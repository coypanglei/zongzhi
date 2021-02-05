package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneNewContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StandardAddressOneNewPresenter extends ReworkBasePresenter<StandardAddressOneNewContract.Model, StandardAddressOneNewContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public StandardAddressOneNewPresenter(StandardAddressOneNewContract.Model model, StandardAddressOneNewContract.View rootView) {
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
     * 获取用户可以访问的 网格列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param keyword       keyword
     */
    public void getGridList(boolean pullToRefresh, boolean isLoadMore, String keyword) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getGridList(pageSize, ignoreNumber, keyword), mErrorHandler, userGideBean -> {
            mRootView.showPopup(userGideBean.getList(), isLoadMore);
            handlePaginLoadMore((userGideBean == null || userGideBean.getList() == null) ? 0 : userGideBean.getList().size());
        });

    }

    /**
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param departmentId  departmentId
     */
    public void getDepartmentDownInfo(boolean pullToRefresh, boolean isLoadMore, String departmentId) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getDepartmenDownInfo(departmentId, pageSize, ignoreNumber), mErrorHandler, standardAddressStairBean -> {
            List<StandardAddressStairBean.AddressTypeBean> addressType = standardAddressStairBean.getAddressType();
            StandardAddressStairBean.AddressTypeBean addressTypeBean = new StandardAddressStairBean.AddressTypeBean();
            addressTypeBean.setId("");
            addressTypeBean.setName(mRootView.getContext().getString(R.string.all));
            addressType.add(0, addressTypeBean);
            mRootView.showPopup(addressType, isLoadMore);
            handlePaginLoadMore(standardAddressStairBean == null || standardAddressStairBean.getAddressType() == null ? 0 : standardAddressStairBean.getAddressType().size());
        });
    }

    /**
     * 获取网格员 某 项信息  单元/ 门牌  楼宇  小区  等
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param gridId        网格id
     * @param dId           层级id
     * @param name          要搜索的文字
     */
    public void getGriddingFourRace(boolean pullToRefresh, boolean isLoadMore, String gridId, String dId, String name) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getGriddingFourRace(dId, gridId, name, pageSize, ignoreNumber), mErrorHandler, standardAddressStairBean -> {
                try {
                    mRootView.setNewData(standardAddressStairBean, isLoadMore, standardAddressStairBean.getCount());
                    handlePaginLoadMore((standardAddressStairBean == null || standardAddressStairBean.getStandardAddress() == null)
                            ? 0 : standardAddressStairBean.getStandardAddress().size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
