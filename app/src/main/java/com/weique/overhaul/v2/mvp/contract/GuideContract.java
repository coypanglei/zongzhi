package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/11/2019 14:18
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface GuideContract {

    interface View extends IView {
        Activity getActivity();
    }


    interface Model extends IModel {

    }
}
