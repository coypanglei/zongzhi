package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/06/2019 14:33
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PartyCenterNewsContract {

    interface View extends IView {
        BaseFragment getFragment();
    }


    interface Model extends IModel {
        Observable<BaseBean<PartyCenterRecommendedBean>> getPartyMainData();
    }
}
