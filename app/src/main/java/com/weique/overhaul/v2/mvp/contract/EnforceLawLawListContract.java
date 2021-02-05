package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CaseReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EnforceLawLawListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        Context getContext();

        /**
         * 设置界面列表数据
         *
         * @param newData newData
         */
        void setNewData(List<CaseReportedBean> newData, boolean isLoadMore);

        void getCommonEnums(List<CommonTitleBean> bean, String type);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取案件集合
         *
         * @param keyword
         * @param type
         * @param sortType
         * @param pageSize
         * @param ignoreNumber
         * @return
         */
        Observable<BaseBean<List<CaseReportedBean>>> getCaseList(String keyword, String type,
                                                                 String sortType, int pageSize, int ignoreNumber);

        Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type);
    }
}
