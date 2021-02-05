package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 * @author  GK
 */
public interface UpEventContract {
    interface View extends IView {
        /**
         * Activity
         *
         * @return Activity
         */
        Activity getActivity();
    }

    interface Model extends IModel {

    }
}
