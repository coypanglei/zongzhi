package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface InspectionMapContract {

    interface View extends IView {
        void setLatLng(InspectionLatLng inspectionLatLng ,boolean isLoadMore);

        void setIsInspectionComplete(String itemBean);
    }


    interface Model extends IModel {
        Observable<BaseBean<InspectionLatLng>> getLatLng(String id);

        Observable<BaseBean<String>> setIsInspectionComplete(String taskId, String inspectionRecordId);
    }
}
