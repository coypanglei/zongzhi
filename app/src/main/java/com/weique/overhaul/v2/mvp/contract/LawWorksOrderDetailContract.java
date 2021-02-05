package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/23/2020 13:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LawWorksOrderDetailContract {

    interface View extends IView {
        Activity getContext();

        void goToPhotoAlbum(int max);

        /**
         * 更新  图片
         *
         * @param uploadFileRsponseBeans
         */
        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);

        void setLawDetailListData(LawWorksOrderDetailBean itemBean);

        void setOrderData(String itemBean);

        void setOrderIntoData(String itemBean);

        Activity getActivity();
    }


    interface Model extends IModel {

        Observable<BaseBean<LawWorksOrderDetailBean>>  getLawDetailListData(String id);

        Observable<BaseBean<String>> getOrderData(String id);

        Observable<BaseBean<String>> getOrderIntoData(String courtOrderID, String content, String imgsInJson);

    }
}
