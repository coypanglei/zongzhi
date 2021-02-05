package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 10:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ContradictionAddContract {

    interface View extends IView {
        void setContradictionData(String s);

        void getContradictionRecord(ContradictionRecordBean contradictionRecordBean);

        void isContradictionRecordDelete(String contradictionRecordBean);

        void isInvalidContradictionRecord(String contradictionRecordBean);

        void stagingReset(String contradictionRecordBean);

        Activity getContext();

        void goToPhotoAlbum(int max);

        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);

    }


    interface Model extends IModel {
        Observable<BaseBean<String>> setContradiction(ContradictionAddBean contradictionAddBean);

        Observable<BaseBean<ContradictionRecordBean>> getContradictionRecord(String id);

        Observable<BaseBean<String>> DeleteContradictionRecord(String resetId);

        Observable<BaseBean<String>> InvalidContradictionRecord(String resetId);

        Observable<BaseBean<String>> setResetContradiction(ContradictionAddBean stagingBean);


    }
}
