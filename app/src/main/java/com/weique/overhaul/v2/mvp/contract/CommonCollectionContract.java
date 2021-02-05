package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.ProjectInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/25/2020 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CommonCollectionContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getCommonEnums(List<CommonTitleBean> bean, String type);

        Activity getContext();

        void goToPhotoAlbum();

        void goToPhotoAlbum(int max,int type);
        void updatePicture(String url);


        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBean, BasicInformationBean.RecordsBean changeBean);

        CommentCurrencyAdapter  getAdapter();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        public Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type);

        /**
         * 获取行业列表
         *
         * @return
         */
        public Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseType();

        /**
         * 获取项目信息
         */
        Observable<BaseBean<ProjectInfoBean>> getProjectInfo(String id);

        /**
         * 获取企业信息
         *
         * @param id
         * @return
         */
        Observable<BaseBean<EntInfoBean>> getEntInfo(String id);


    }
}
