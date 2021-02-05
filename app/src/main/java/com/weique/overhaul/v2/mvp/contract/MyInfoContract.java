package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 14:50
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MyInfoContract {

    interface View extends IView {
        void setUserInfo(GlobalUserInfoBean userInfo);

        void updatePicture(String url);

        Activity getContext();

        void goToPhotoAlbum();

        void setSaveSuccess();
    }


    interface Model extends IModel {
        /**
         * 获取用户信息
         *
         * @return Observable
         */
        Observable<BaseBean<GlobalUserInfoBean>> getUserInfoDetail();


        /**
         * updateUserPicture
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<Object>> updateUserPicture(Map<String, Object> map);

        Observable<BaseBean<Object>> saveUserInfo(GlobalUserInfoBean mMyInfoBeanChanged);
    }
}
