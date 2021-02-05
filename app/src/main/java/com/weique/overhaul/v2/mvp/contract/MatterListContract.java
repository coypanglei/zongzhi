package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MatterListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setList(List<MatterListBean.ListBean> list, boolean isLoadMore);

        void setEnum(List<CommonEnumBean> o);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取列表
         *
         * @param flag flag
         * @return Observable
         */
        Observable<BaseBean<MatterListBean>> getList(int pageSize, int ignoreNumber, int flag,
                                                     int orderStatus, int dataSource, String keyword);

        /**
         * getEnum
         *
         * @param enumType enumType
         * @return Observable
         */
        Observable<BaseBean<List<CommonEnumBean>>> getEnum(String enumType);

        Observable<BaseBean<MatterListBean>> getMyCooperListByReceiveUserId(int pageSize, int ignoreNumber, int flag, int orderStatus, int dataSource, String keyword);

        Observable<BaseBean<MatterListBean>> getMyProceedListByUserId(int pageSize, int ignoreNumber, int flag, int orderStatus, int dataSource, String keyword);
    }
}
