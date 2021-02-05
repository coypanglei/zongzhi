package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressAreaBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 10:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SearchEconoicManageMentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Context getContext();

        /**
         * 设置界面列表数据
         *
         * @param newData newData
         */
        void setNewData(List<NameAndIdBean> newData, boolean isLoadMore);

        void showAreaListPopup(List<StandardAddressAreaBean.DepartmentBean> department, boolean isChild);

        void deleteInfo(NameAndIdBean nameAndIdBean,int position);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取项目列表
         *
         * @param name         name
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<List<NameAndIdBean>>> getProjectList(String dId, String name, int pageSize, int ignoreNumber);

        /**
         * 获取企业列表
         *
         * @param name         name
         * @param pageSize     pageSize
         * @param ignoreNumber ignoreNumber
         * @return Observable
         */
        Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseList(String dId, String name, int pageSize, int ignoreNumber);

        /**
         * @return Observable
         */
        Observable<BaseBean<StandardAddressAreaBean>> getAreaList(String departmentId);


        /**
         * 删除企业或者项目信息
         *
         * @param  id
         * @return
         */
        Observable<BaseBean<Object>> deleteInfo(String id,int type);
    }
}
