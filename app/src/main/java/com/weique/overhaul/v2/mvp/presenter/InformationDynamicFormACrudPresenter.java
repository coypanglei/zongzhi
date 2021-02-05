package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;
import com.weique.overhaul.v2.mvp.model.entity.DynamicFormOrmBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/28/2019 16:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class InformationDynamicFormACrudPresenter extends ReworkBasePresenter<InformationDynamicFormACrudContract.Model, InformationDynamicFormACrudContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RxPermissions rxPermissions;
    @Inject
    Gson gson;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    public InformationDynamicFormACrudPresenter(InformationDynamicFormACrudContract.Model model, InformationDynamicFormACrudContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getDepartment(String departmentId) {
        commonGetData(mModel.getGetDepartment(departmentId), mErrorHandler, gridInformationBean -> {
            mRootView.gotoMapTv(gridInformationBean);
        });
    }

    public void createElement(InformationDetailBean informationDetailBean) {
        commonGetData(mModel.createElement(informationDetailBean), mErrorHandler, listBean -> mRootView.updateData(listBean));
    }

    public void editElement(InformationDetailBean informationDetailBean) {
        commonGetData(mModel.editElement(informationDetailBean), mErrorHandler, listBean -> mRootView.updateData(listBean));
    }

    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        mRootView.updatePicture(uploadFileRsponseBeans);
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param adapterPosition
     * @param max
     * @param type
     */
    public void getPermission(int adapterPosition, int max, String type) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, new CommonPermissionUtil.PermissionLisenter() {
            @Override
            public void getPermissionSuccess() {
                mRootView.goToPhotoAlbum(adapterPosition, max, type);
            }
        }, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    /**
     * 动态表单关系映射 身份证扫描
     *
     * @param elementTypeId elementTypeId  类型id
     */
    public void getDynamicFormOrm(String elementTypeId, int enumAIType) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("Id", elementTypeId);
            //暂时就一种  先写死 0 是身份证扫描 1.动态映射  合并信息
            map.put("EnumAIType", enumAIType);
            commonGetData(mModel.getDynamicFormOrm(map), mErrorHandler, s -> {
                try {
                    switch (enumAIType) {
                        case DynamicFormOrmBean.SCAN_TYPE:
                            //请求合并表单 动态映射相关
                            getDynamicFormOrm(elementTypeId, DynamicFormOrmBean.MERGE_TYPE);
                            if (StringUtil.isNotNullString(s)) {
                                if (s.contains("[")) {
                                    ArrayList lists = gson.fromJson(s, ArrayList.class);
                                    if (lists != null && lists.size() > 0) {
                                        List<DynamicFormOrmBean.MapListBean> ormBeans = new ArrayList<>();
                                        for (Object o : lists) {
                                            DynamicFormOrmBean.MapListBean bean = gson.fromJson(o.toString(), DynamicFormOrmBean.MapListBean.class);
                                            ormBeans.add(bean);
                                        }
                                        mRootView.setDynamicFormOrmData(ormBeans);
                                        return;
                                    }
                                }
                            }
                            break;
                        case DynamicFormOrmBean.MERGE_TYPE:
                            if (StringUtil.isNotNullString(s)) {
                                DynamicFormOrmBean dynamicFormOrmBean = gson.fromJson(s, DynamicFormOrmBean.class);
                                mRootView.setMergeDynamicFormOrmData(dynamicFormOrmBean);
                            }
                            break;
                        default:
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据 信息类型   和检索字段  获取 具体信息
     *
     * @param sourceTypeFiredFieldName eg:singLine11（身份证标识）
     * @param sourceTypeId             eg:残疾人id 资源类型
     * @param value                    eg:320324****0
     */
    public void getFormInfoBySearching(boolean pullToRefresh, boolean isLoadMore, String sourceTypeFiredFieldName, String sourceTypeId, String value) {
        handlePaging(pullToRefresh, isLoadMore);
        Map<String, Object> map = new HashMap();
        map.put("Key", sourceTypeFiredFieldName);
        map.put("ElementTypeId", sourceTypeId);
        map.put("Value", value);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        commonGetData(mModel.getFormInfoBySearching(map), mErrorHandler, s -> {
            try {
                List<Map<String, Object>> list = gson.fromJson(s, new TypeToken<List<Map<String, Object>>>() {
                }.getType());
                if (list == null || list.size() <= 0) {
                    ArmsUtils.makeText("未查询到信息");
                } else {
                    mRootView.showMergeTargetListPopup(list, isLoadMore);
                }
                handlePaginLoadMore(list.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 根据条目信息  中的 元素id  获取元素的动态表单  并处理动态表单数据  开始  映射绘制界面
     *
     * @param item 点击的条目信息
     */
    public void getDataStructureInJson(InformationTypeOneSecondBean.ElementListBean item, Map<String, Object> o) {
        commonGetData(mModel.getDataStructureInJson(item.getElementTypeId()), mErrorHandler, s -> {
            try {
                if (StringUtil.isNotNullString(s)) {
                    InformationDynamicFormSelectBean dynamicFormSelectBean = gson.fromJson(s, InformationDynamicFormSelectBean.class);
                    JSONObject jsonObject = new JSONObject(gson.toJson(o));
                    ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJson = dynamicFormSelectBean.getStructureInJson();
                    for (InformationDynamicFormSelectBean.StructureInJsonBean bean : structureInJson) {
                        bean.setDefaultVal(jsonObject.getString(bean.getPropertyName()));
                    }
                    mRootView.sourceMapMiddle(dynamicFormSelectBean);
                } else {
                    ArmsUtils.makeText("获取表单数据失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
