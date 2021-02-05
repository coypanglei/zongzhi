package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface BusinessQuestionDetailContract {

    interface View extends IView {

        void setIssueData(EnterpriseIssueDetailBean o);
    }


    interface Model extends IModel {

        /**
         * 获取问题详情
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<EnterpriseIssueDetailBean>> getQuestionDetail(Map<String, Object> map);

        /**
         * i提交回复
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<Object>> submitFeedback(Map<String, Object> map);
    }
}
