package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.app.customview.SelectDialog;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionAddBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionOneAddBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/10/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface InspectionAddContract {

    interface View extends IView {
        void setInspectionData(String itemBean);

        void setInspectionOneData(String itemBean);

        /**
         * 图片上传成功  跟新界面
         *
         * @param uploadFileRsponseBeans
         */
        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);

        Activity getActivity();

        void showDialog(SelectDialog.SelectDialogListener selectDialogListener, List<String> names);
    }


    interface Model extends IModel {
        Observable<BaseBean<String>> setInspectionData(InspectionAddBean s);

        Observable<BaseBean<String>> setInspectionOneData(InspectionOneAddBean s);


    }
}
