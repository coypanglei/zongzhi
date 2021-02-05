package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/26/2019 10:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface StandardAddressLookContract {

    interface View extends IView {

        void setData(StandardAddressBean standardAddressBean);

        void gotoMapTv(GridInformationBean gridInformationBean);

        void setInfoData(ScanResultBean itemBean);

        void setInformationTypeStarters(List<InformationTypeOneSecondBean.ElementTypeListBean> beans,boolean showPopup);

        Context getContext();

        void setElementList(InformationTypeOneSecondBean beans);
    }


    interface Model extends IModel {

        /**
         * 获取标准地址详情
         *
         * @param detailId getAddressDetail
         * @return Observable
         */
        Observable<BaseBean<StandardAddressBean>> getAddressDetail(String detailId);

        /**
         * 移除 已添加的地址信息
         *
         * @param id id
         * @return return
         */
        Observable<BaseBean<Object>> deleteItem(String id);

        Observable<BaseBean<GridInformationBean>> getGetDepartment();

        Observable<BaseBean<ScanResultBean>> getInfoByStandardId(int pageSize, int ignoreNumber, String id);

        Observable<BaseBean<List<InformationTypeOneSecondBean.ElementTypeListBean>>> getInformationTypeStartersList(String standardAddressId);

        Observable<BaseBean<InformationTypeOneSecondBean>> getElementTypes(String elementId);

        Observable<BaseBean<String>> getGetNewGuidForApp();
    }
}
