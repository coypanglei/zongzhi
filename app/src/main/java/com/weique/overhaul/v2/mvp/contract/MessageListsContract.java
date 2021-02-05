package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.MessageListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/10/2020 11:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MessageListsContract {

    interface View extends IView {

        void setNewData(List<MessageListBean.ListBean> list, boolean isLoadMore);

        Context getActivity();
    }


    interface Model extends IModel {

        /**
         * 消息列表
         *
         * @param pageSize
         * @param ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<MessageListBean>> getMessage(
                int pageSize,
                int ignoreNumber);
    }
}
