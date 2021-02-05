package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2019 15:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SignInContract {

    interface View extends IView {
        void setSignData(String  data);

        void setSignBtnStatus(SigninStatusBean status);

        void setReseau(GridInformationBean gridInformationBean);
    }


    interface Model extends IModel {
        Observable<BaseBean<String>> setSign(String PointsInJson, String Address,boolean isInGrid,int signActive);

        Observable<BaseBean<SigninStatusBean>> getSignStatus();

        Observable<BaseBean<GridInformationBean>> getGetDepartment();
    }
}
