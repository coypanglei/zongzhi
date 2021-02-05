package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformation;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformationDetailBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface BusinessInformationDetailContract {

    interface View extends IView {

        void setBusinessInformationDetailListData(BusinessInformationDetailBean itemBean, boolean isLoadMore);
    }


    interface Model extends IModel {

        Observable<BaseBean<BusinessInformationDetailBean>> getBusinessInformationDetailListData(int pageSize, int ignoreNumber, String id);
    }
}
