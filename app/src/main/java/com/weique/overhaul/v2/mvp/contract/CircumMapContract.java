package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;
import com.weique.overhaul.v2.mvp.ui.activity.map.CircumMapActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/27/2020 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CircumMapContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setPoitons(List<PointsListBean.ElementPointsBean> points);

        void setDepartmentsPoints(List<PointsBean> points);

        Context getActivity();

        void setTypeId(List<PointsListBean.ElementPointsBean> points);

        void setClickPoint(String dataStructureInJson, CircumMapActivity.MyItem myItem);


        void setClickPoint(String dataStructureInJson, PointsDetailBean pointsDetailBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        //获取地点
        Observable<BaseBean<PointsListBean>> getAllAddressPoints(Map<String, Object> map);

        //获取所有网格信息
        Observable<BaseBean<List<PointsBean>>> getDepartmentsPoints(Map<String, Object> map);

        // 根据id获取动态表单
        Observable<BaseBean<String>> getDataStructureInJson(@QueryMap Map<String, Object> paramSign);


    }
}
