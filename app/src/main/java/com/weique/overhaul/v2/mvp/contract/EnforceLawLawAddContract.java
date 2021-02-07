package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * ================================================
 */
public interface EnforceLawLawAddContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        /**
         * 权力事项
         *
         * @param o
         * @param isLoadMore
         */
        void setMatterList(List<MatterSecondBean> o, boolean isLoadMore);

        /**
         * 权力事项二级分类
         *
         * @param o
         */
        void setMatterSecondList(List<MatterSecondBean> o);

        /**
         * 部门泪飙
         *
         * @param o
         * @param isLoadMore
         */
        void setLegalOperation(List<NameAndIdBean> o, boolean isLoadMore);

        /**
         * 案件分类
         *
         * @param o o
         */
        void setLawSort(List<NameAndIdBean> o);

        /**
         * 更新图
         *
         * @param uploadFileRsponseBeans uploadFileRsponseBeans
         */
        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);

        /**
         * 弹框搜索 当事人
         *
         * @param bean       bean
         * @param isLoadMore isLoadMore
         */
        void setPartiesList(List<PartiesBean> bean, boolean isLoadMore);

        /**
         * 设置当事人信息
         *
         * @param partiesBean partiesBean
         */
        void setPartiesInfo(PartiesBean partiesBean);

        /**
         * 审核人列表
         *
         * @param bean
         */
        void setAuditorList(List<NameAndIdBean> bean, boolean isLoadMore);

        Activity getActivity();

        void showOpenGpsDialog();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取事项列表
         *
         * @return Observable
         */
        Observable<BaseBean<List<MatterSecondBean>>> getMatterList(int ignoreNumber, int pageSize);

        /**
         * 获取子集
         *
         * @param elementId elementId
         * @return Observable
         */
        Observable<BaseBean<List<MatterSecondBean>>> getMatterSecondList(String elementId);

        /**
         * 获取案件分类
         *
         * @return o
         */
        Observable<BaseBean<List<NameAndIdBean>>> getLawSort();

        /**
         * 获取执法部门
         *
         * @param ignoreNumber
         * @param pageSize
         * @return o
         */
        Observable<BaseBean<List<NameAndIdBean>>> getLegalOperation(int ignoreNumber, int pageSize);


        Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type);

        Observable<BaseBean<List<PartiesBean>>> getPartiesList(int ignoreNumber, int pageSize, String text);

        Observable<BaseBean<String>> submitPartiesInfo(PartiesBean partiesBean);

        Observable<BaseBean<List<NameAndIdBean>>> getAuditorList(String departmentId, int ignoreNumber, int pageSize);

        Observable<BaseBean<Object>> submitLaw(Map<String, Object> allMap);

        Observable<BaseBean<GridInformationBean>> getAreaMapByDepartmentId(String departmentId);
    }
}
