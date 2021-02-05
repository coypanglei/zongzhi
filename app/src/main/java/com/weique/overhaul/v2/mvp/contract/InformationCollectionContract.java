package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:39
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface InformationCollectionContract {

    interface View extends IView {

        Context getActivity();

        /**
         * 传递数据给 界面显示
         *
         * @param beans beans
         */
        void setData(List<InformationTypeOneSecondBean.ElementTypeListBean> beans);
    }


    interface Model extends IModel {

        Observable<BaseBean<List<InformationTypeOneSecondBean.ElementTypeListBean>>> getElementType();
    }
}
