package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface BusinessVisitContract {

    interface View extends IView {

        void goToPhotoAlbum(int max);

        Activity getContext();

        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);
    }


    interface Model extends IModel {

        /**
         * 提交走访记录
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<Object>> submit(Map<String, Object> map);
    }
}
