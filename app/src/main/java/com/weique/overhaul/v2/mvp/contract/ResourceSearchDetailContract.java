package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/04/2019 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ResourceSearchDetailContract {

    interface View extends IView {
        void setResourceSearchDetailListData(ResourceSearchDetailItemBean data, boolean isLoadMore);

    }


    interface Model extends IModel {

        Observable<BaseBean<ResourceSearchDetailItemBean>> getResourceSearchDetailListData(int pageSize,int ignoreNumber, String resourceId, String elementTypeId);
    }
}
