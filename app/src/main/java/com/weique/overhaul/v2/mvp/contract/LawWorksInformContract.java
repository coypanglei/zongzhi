package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksInformBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 15:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LawWorksInformContract {

    interface View extends IView {

        Context getContext();

        void setNewData(List<LawWorksInformBean> informBeans, boolean isLoadMore);
    }


    interface Model extends IModel {

        Observable<BaseBean<List<LawWorksInformBean>>> getInformList(int pageSize, int ignoreNumber);

        Observable<BaseBean<LawWorksInformBean>> getInformDetail(String id);
    }
}
