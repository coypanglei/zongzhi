package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 09:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EventsReportedCrudContract {

    interface View extends IView {

        Activity getContext();


        void setData(Object eventsReportedLookBean);

        void setUpdatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans, boolean isInDynamic);

        /**
         * 去相册
         */
        void goToPhotoAlbum(int adapterPosition, int max, String type, boolean isInDynamic);

        void next();

        /**
         * 上传 语音文件成功
         *
         * @param uploadFileRsponseBeans uploadFileRsponseBeans
         */
        void setRecordInfo(List<UploadFileRsponseBean> uploadFileRsponseBeans);

    }


    interface Model extends IModel {


        Observable<BaseBean<Object>> getEventRecordInfo(String id, String custId);


        Observable<BaseBean<Object>> createEventRecord(EventsReportedLookBean eventsReportedLookBean);

        Observable<BaseBean<Object>> editEventRecord(EventsReportedLookBean eventsReportedLookBean);

        Observable<BaseBean<Object>> createEventRecordSnap(EventsReportedLookBean eventsReportedLookBean);

        Observable<BaseBean<Object>> delEventRecord(String custId, String id);

        Observable<BaseBean<Object>> invalid(String id);

    }
}
