package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/04/2019 14:17
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PartyCenterTopTableContract {

    interface View extends IView {
        BaseFragment getFragment();
    }


    interface Model extends IModel {
        /**
         * getPartyTableData
         * @param typeId  typeId
         * @return Observable
         */
        Observable<BaseBean<PartyCenterRecommendedBean>> getPartyTableData(int typeId);
    }
}
