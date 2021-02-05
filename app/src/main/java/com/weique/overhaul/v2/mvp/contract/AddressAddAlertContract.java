package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface AddressAddAlertContract {
    interface View extends IView {
        void showPopup(List<? extends BaseSearchPopupBean> addressType);
    }

    interface Model extends IModel {


        /**
         * 提交标准地址详情 修改
         *
         * @param standardAddressBean standardAddressBean
         * @return Observable
         */
        Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> submitStandardAddressAlert(StandardAddressBean standardAddressBean);

        /**
         * 提交标准地址详情 添加
         *
         * @param standardAddressBean standardAddressBean
         * @return Observable
         */
        Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> submitStandardAddressAdd(StandardAddressBean standardAddressBean);

        Observable<BaseBean<StandardAddressStairBean>> getDepartmenDownInfo(String departmentId, int pageSize, int ignoreNumber);
    }
}
