package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.IntergralBean;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface MyContract {
    interface View extends IView {
        Activity getActivity();

        void getIntegral(IntergralBean Integral);
    }

    interface Model extends IModel {
        /**
         * 获取积分总和
         *
         * @param map
         * @return
         */
        Observable<BaseBean<IntergralBean>> getIntegral(@QueryMap Map<String, Object> map);


    }

}
