package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@ActivityScope
public class InformationTypeSecondPresenter extends ReworkBasePresenter<InformationTypeSecondContract.Model, InformationTypeSecondContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public InformationTypeSecondPresenter(InformationTypeSecondContract.Model model, InformationTypeSecondContract.View rootView) {
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

    public void getGridResource(boolean pullToRefresh, boolean isLoadMore, String departmentId,
                                String standardAddressId, String id, String query, Map map, String source) {
        try {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getGridResource(id, query, departmentId,
                    standardAddressId, pageSize, ignoreNumber, map),
                    mErrorHandler, informationTypeSecondBean -> {
                        try {
                            mRootView.setData(informationTypeSecondBean.getElementList(),
                                    isLoadMore, informationTypeSecondBean.getCount(), informationTypeSecondBean.getType());
                            handlePaginLoadMore((informationTypeSecondBean == null ||
                                    informationTypeSecondBean.getElementList() == null) ? 0
                                    : informationTypeSecondBean.getElementList().size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGetNewGuidForApp() {
        commonGetData(mModel.getGetNewGuidForApp(), mErrorHandler, id -> {
            try {
                mRootView.setGotoNewGuid(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取网格信息
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param keyword       keyword
     */
    public void getGridList(boolean pullToRefresh, boolean isLoadMore, String keyword) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getGridList(pageSize, ignoreNumber, keyword), mErrorHandler, userGideBean -> {
            if (userGideBean != null && userGideBean.getList() != null) {
                List<UserGideBean.ListBean> list = userGideBean.getList();
                if (list.size() > 1) {
                    UserGideBean.ListBean bean = new UserGideBean.ListBean();
                    bean.setId(UserInfoUtil.getUserInfo().getDepartmentId());
                    bean.setName("全部");
                    list.add(0, bean);
                }
                mRootView.showPopup(list, isLoadMore);
            }
            handlePaginLoadMore((userGideBean == null || userGideBean.getList() == null) ? 0 : userGideBean.getList().size());
        });
    }
    /**
     * 获取用户可以访问的 网格列表
     */
    public void getDepartments(String id, boolean addChild) {
        commonGetData(mModel.getDepartments(id), mErrorHandler, departmentsBeans -> {
            mRootView.showTreePopup(departmentsBeans,addChild);
        });

    }

    /**
     * getStandardAddressByGridId
     *
     * @param pullToRefresh 是否下拉刷新
     * @param isLoadMore    是否加载更多
     * @param isSearchBtn   是否是搜索按钮
     * @param departmentId  网格id
     * @param keyWord       搜索关键字
     */
    public void getStandardAddressByGridId(boolean pullToRefresh, boolean isLoadMore, boolean isSearchBtn, String departmentId, String keyWord) {
        handlePaging(pullToRefresh, isLoadMore);
        commonGetData(mModel.getStandardAddressByGridId(pageSize, ignoreNumber, departmentId, keyWord), mErrorHandler, beans -> {
            if (!isLoadMore && !isSearchBtn) {
                StandardAddressStairBean.StandardAddressBean standardAddressBean = new StandardAddressStairBean.StandardAddressBean();
                standardAddressBean.setId("");
                standardAddressBean.setName(mRootView.getContext().getString(R.string.all));
                standardAddressBean.setFullPath(mRootView.getContext().getString(R.string.all));
                beans.getStandardAddress().add(0, standardAddressBean);
            }
            mRootView.showPopup(beans.getStandardAddress(), isLoadMore);
            handlePaginLoadMore((beans.getStandardAddress() == null) ? 0 : beans.getStandardAddress().size());
        });
    }
}
