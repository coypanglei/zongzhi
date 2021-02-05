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
 * Created by MVPArmsTemplate on 03/10/2020 15:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EnterpriseIssueEditContract {

    interface View extends IView {

        /**
         * 设置问题类型 列表数据
         *
         * @param objects objects
         */
        void setIssueTypeData(List<String> objects);

        Activity getContext();

        void goToPhotoAlbum(int max);

        /**
         * 更新  图片
         *
         * @param uploadFileRsponseBeans
         */
        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);
    }


    interface Model extends IModel {

        /**
         * 获取问题类型列表
         *
         * @return Observable
         */
        Observable<BaseBean<List<String>>> getIssueTypeList();

        /**
         * 提交 企业提问
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<Object>> submitInfo(Map<String, Object> map);

    }
}
