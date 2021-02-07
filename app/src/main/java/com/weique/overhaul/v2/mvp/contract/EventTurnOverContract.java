package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2021/1/26 15:51
 */
public interface EventTurnOverContract {
    interface View extends IView {

        void setDeparts(List<NameAndIdBean> items);
    }

    interface Model extends IModel {

    }
}
