package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.DynamicFormOrmBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public interface InformationDynamicFormACrudContract {

    interface View extends IView {

        /**
         * getContext
         *
         * @return Activity
         */
        Activity getContext();

        /**
         * 去 地图界面
         *
         * @param gridInformationBean gridInformationBean
         */
        void gotoMapTv(GridInformationBean gridInformationBean);

        /**
         * 新建或修改成功
         *
         * @param bean bean
         */
        void updateData(InformationTypeOneSecondBean.ElementListBean bean);

        /**
         * 图片上传成功  跟新界面
         *
         * @param uploadFileRsponseBeans
         */
        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);

        /**
         * 去相机
         *
         * @param adapterPosition adapterPosition
         * @param max             max
         * @param type            type
         */
        void goToPhotoAlbum(int adapterPosition, int max, String type);

        /**
         * 扫描的 动态映射
         *
         * @param lists lists
         */
        void setDynamicFormOrmData(List<DynamicFormOrmBean.MapListBean> lists);

        /**
         * 要合并的动态映射
         *
         * @param dynamicFormOrmBean dynamicFormOrmBean
         */
        void setMergeDynamicFormOrmData(DynamicFormOrmBean dynamicFormOrmBean);

        /**
         * 显示要同步的目标  信息列表 popup
         *
         * @param list       list
         * @param isLoadMore isLoadMore
         */
        void showMergeTargetListPopup(List<Map<String, Object>> list, boolean isLoadMore);

        /**
         * 映射来源映射到中间层
         *
         * @param dynamicFormSelectBean dynamicFormSelectBean
         */
        void sourceMapMiddle(InformationDynamicFormSelectBean dynamicFormSelectBean);
    }


    interface Model extends IModel {

        /**
         * 获取网格信息
         *
         * @return Observable
         */
        Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId);

        /**
         * 创建信息
         *
         * @param informationDetailBean informationDetailBean
         * @return Observable
         */
        Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> createElement(InformationDetailBean informationDetailBean);

        /**
         * 编辑信息
         *
         * @param informationDetailBean informationDetailBean
         * @return Observable
         */
        Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> editElement(InformationDetailBean informationDetailBean);


        /**
         * 动态表单 关系映射
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<String>> getDynamicFormOrm(Map<String, Object> map);

        /**
         * 获取详情信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<String>> getFormInfoBySearching(Map<String, Object> map);

        /**
         * 根据元素id 获取其  动态表单
         *
         * @param elementTypeId elementTypeId
         * @return Observable
         */
        Observable<BaseBean<String>> getDataStructureInJson(String elementTypeId);
    }
}
