package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;
import android.graphics.Bitmap;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 16:51
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SettingsContract {

    interface View extends IView {

        Activity getActivity();

        /**
         * 开始下载
         *
         * @param newVersionInfoBean newVersionInfoBean
         */
        void showVersionDialog(NewVersionInfoBean newVersionInfoBean);


        void setDialogGone();


        void setCodeImg(Bitmap bitmap,String versionName);
    }


    interface Model extends IModel {

        Observable<BaseBean<NewVersionInfoBean>> getAppVersionInfo(Map<String, Object> map);
    }
}
