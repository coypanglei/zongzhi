package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyAnswerItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 09:58
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AnswerContract {

    interface View extends IView {
        void setAnswerListData(PartyAnswerItemBean data, boolean isLoadMore);
    }


    interface Model extends IModel {
        Observable<BaseBean<PartyAnswerItemBean>> getPartyAnswerData(int pageSize,int ignoreNumber);
    }
}
