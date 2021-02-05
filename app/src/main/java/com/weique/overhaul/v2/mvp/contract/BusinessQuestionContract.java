package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInterViewListBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessQuestion;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface BusinessQuestionContract {

    interface View extends IView {

        void setBusinessQuestionData(BusinessQuestion itemBean, boolean isLoadMore);
    }


    interface Model extends IModel {

        Observable<BaseBean<BusinessQuestion>> getBusinessQuestionData(int pageSize, int ignoreNumber);
    }
}
