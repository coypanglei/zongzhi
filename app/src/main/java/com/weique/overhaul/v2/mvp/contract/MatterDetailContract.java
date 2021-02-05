package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.HelperListItemBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MatterDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setAccept();

        Activity getActivity();

        void goToPhotoAlbum(int max);

        void setFiles(List<UploadFileRsponseBean> uploadFileRsponseBeans);

        void setDispose();

        void returnOrderSuccess();

        void setUserInfoForCoop(List<NameAndIdBean> o);

        void setRequestSynergy(Object o);

        void setConsentHelp(Object o);

        void setHelperList(List<HelperListItemBean> o);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 受理
         *
         * @return Observable
         */
        Observable<BaseBean<Object>> accept(String id, int status);

        /**
         * 处置
         *
         * @param id       id
         * @param issues   issues
         * @param toString toString
         * @param urls     urls
         * @return Observable
         */
        Observable<BaseBean<Object>> dispose(String id, String issues, String toString, List<String> urls);

        Observable<BaseBean<Object>> returnOrder(String id, int status, String content);

        Observable<BaseBean<List<NameAndIdBean>>> getUserInfoForCoop(String toString);

        Observable<BaseBean<Object>> requestSynergy(String synergyMap, String eventRecordId);

        Observable<BaseBean<Object>> consentHelp(Map<String, Object> map);

        Observable<BaseBean<List<HelperListItemBean>>> getHelperList(String recordEventId);
    }
}
