package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/01/2020 13:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MatterDetailTableContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        /**
         * 设置数据
         *
         * @param o o
         */
        void setDetail(MatterDetailsBean o);

        void setProgress(List<MatterDetailsFlowBean> o);

        void setCuiBan(Object o);

        void setDuBan(Object o);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<MatterDetailsBean>> getDetailById(String eventRecordId, String id, int flag);

        Observable<BaseBean<List<MatterDetailsFlowBean>>> getProgress(String eventRecordId, String id, int flag);

        Observable<BaseBean<Object>> getDuBan(String eventRecordId, String id, int flag);

        Observable<BaseBean<Object>> getCuiBan(String eventRecordId, String id, int flag);
    }
}
