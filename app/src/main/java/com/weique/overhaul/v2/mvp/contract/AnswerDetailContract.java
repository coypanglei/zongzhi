package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.QuestionDetailAnswerItemBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 14:07
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface AnswerDetailContract {

    interface View extends IView {
        void setAnswerDetailListData(List<QuestionDetailAnswerItemBean> data);

    }


    interface Model extends IModel {
        Observable<BaseBean<List<QuestionDetailAnswerItemBean>>> getPartyAnswerDetailData(String SubjectId);

    }
}
