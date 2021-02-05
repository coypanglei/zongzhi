package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EnforceLawEventContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 获取用户网格信息
         *
         * @param pointsInJSON pointsInJSON
         */
        void getGridInfoSuccess(String pointsInJSON);

        /**
         * 获取 是否 已用户 定位 为 上报坐标点
         *
         * @param o o
         */
        void setAddressCanChanged(Boolean o);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<String>> getGetNewGuidForApp();

        Observable<BaseBean<GridInformationBean>> gridOperatorInformation();

        /**
         * @return 是否允许修改坐标信息
         */
        Observable<BaseBean<Boolean>> getIsInGrid();
    }
}
