package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
public interface IntegratedWithContract {
    interface View extends IView {
        /**
         * 获取context
         *
         * @return Activity
         */
        Activity getActivity();

    }

    interface Model extends IModel {

    }
}
