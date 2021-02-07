package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerCommonCollectionComponent;
import com.weique.overhaul.v2.mvp.contract.CommonCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.TimeSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.CommonCollectionPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/25/2020 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author 434604925@qq.com
 */

@Route(path = RouterHub.APP_COMMON_COLLECTION, name = "通用新增，编辑界面")
public class CommonCollectionActivity extends BaseActivity<CommonCollectionPresenter> implements CommonCollectionContract.View {


    /**
     * 通用adapter
     */
    @Inject
    CommentCurrencyAdapter mAdapter;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    /**
     * 改变的bean
     */
    private BasicInformationBean.RecordsBean changeBean;


    /**
     * 上个界面跳转的参数
     */
    @Autowired(name = ARouerConstant.COMMON_COLLECTION)
    CommonCollectBean commonCollectBean;

    @BindString(R.string.add_new)
    String add_new;

    @BindString(R.string.edit_content)
    String edit_content;

    @Inject
    Gson gson;


    /**
     * 通用 选择弹框
     */
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;

    /**
     * 通用 选择adapter
     */
    private CommonRecyclerPopupAdapter<NameAndIdBean> commonRecyclerPopupAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_common_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        if (Constant.CommonCollectionEnum.COMMON_COLLECTION_ADD == commonCollectBean.getType()) {
            setTitle(add_new + commonCollectBean.getTitle());
        } else if (Constant.CommonCollectionEnum.COMMON_COLLECTION_EDIT == commonCollectBean.getType()) {
            setTitle(edit_content + commonCollectBean.getTitle());
            mPresenter.getInfoById(commonCollectBean);
        }

        initRecyclerAdapter(commonCollectBean.getList());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 初始化 Adapter
     */
    private void initRecyclerAdapter(List<BasicInformationBean.RecordsBean> list) {
        try {
            ArmsUtils.configRecyclerView(rvContent, new LinearLayoutManager(this));
            rvContent.setClipToPadding(false);
            rvContent.setAdapter(mAdapter);


            mAdapter.setNewData(list);
            mAdapter.setReworkBasePresenter(mPresenter);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

                Timber.e(adapter.getData().get(position).toString());
                BasicInformationBean.RecordsBean bean = (BasicInformationBean.RecordsBean) adapter.getData().get(position);

                switch (view.getId()) {
                    case R.id.rl_img_add:
                        if (ObjectUtils.isEmpty(bean.getValue()) || bean.isEdit()) {
                            mPresenter.getPermission();
                            changeBean = bean;
                        }
                        break;
                    case R.id.et_name:
                    case R.id.iv_address:

                        try {
                            Timber.e(bean.getParamtype());
                            switch (bean.getParamtype()) {
                                case DATA_ITEM:
                                    KeybordUtil.hideKeyboard(this);
                                    initTimePicker(bean);
                                    break;
                                case SELECT_ITEM:
                                    KeybordUtil.hideKeyboard(this);
                                    changeBean = bean;
                                    initSelectAll(bean.getName());
                                    break;
                                case ADDRESS_ITEM:
                                    changeBean = bean;
                                    ARouter.getInstance().build(RouterHub.APP_NEWMAPACTIVITY)
                                            .withString(ARouerConstant.DEPARTMENT_ID, UserInfoUtil.getUserInfo().getDepartmentId())
                                            .withString(ARouerConstant.SOURCE, RouterHub.APP_COMMON_COLLECTION)
                                            .navigation();
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据填写的枚举值 去后台获取
     *
     * @param type
     */
    private void initSelectAll(String type) {
        mPresenter.getCommonEnums(type);

    }


    /**
     * 接受地址信息回调
     *
     * @param eventBusBean
     */

    @Subscriber(tag = RouterHub.APP_COMMON_COLLECTION)
    private void eventBusCallback(EventBusBean<LocationAndName> eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_LOCATION:
                    LocationAndName data = eventBusBean.getData();
                    if (ObjectUtils.isNotEmpty(changeBean)) {
                        List<BasicInformationBean.RecordsBean> dd = mAdapter.getData();
                        for (BasicInformationBean.RecordsBean recordsBean : dd) {
                            if ("EventAddress".equals(recordsBean.getName())) {
                                if (StringUtil.isNotNullString(data.getName())) {
                                    recordsBean.setValue(data.getName());
                                    mAdapter.setNewData(mAdapter.getData());
                                    continue;
                                }
                            }
                            if ("PointInJSON".equals(recordsBean.getName())
                                    || "LocationInJson".equals(recordsBean.getName())) {
                                recordsBean.setSelectValue(data.getLatLng().getPointJson());
                                recordsBean.setValue(data.getLatLng().getLngAndLatName());
                                mAdapter.setNewData(mAdapter.getData());
                            }

                        }
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker(final BasicInformationBean.RecordsBean bean) {
        try {
            TimeSelectBean timeSelectBean;
            /**
             *  获取不同的类型
             */
            if (ObjectUtils.isNotEmpty(bean.getChangeType())) {
                timeSelectBean = gson.fromJson(bean.getChangeType(), TimeSelectBean.class);

                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
                startDate.set(year - timeSelectBean.getYear(), 0, 1);
                endDate.set(year + timeSelectBean.getYear(), month, dayOfMonth);
                PickerViewUtil.selectPickerTimeWithStartEnd(this, timeSelectBean.getPrecision(),
                        selectedDate, startDate, endDate,
                        (date, v) -> {
                            SimpleDateFormat format = new SimpleDateFormat(timeSelectBean.getPrecision(), Locale.CHINA);
                            /**
                             * 更新数据 ，不会破坏原有的布局
                             */
                            bean.setValue(format.format(date));
                            mAdapter.setNewData(mAdapter.getData());
                        }).show();
            } else {
                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
                startDate.set(year - 100, 0, 1);
                endDate.set(year + 10, month, dayOfMonth);
                PickerViewUtil.selectPickerTimeWithStartEnd(this, Constant.YMD,
                        selectedDate, startDate, endDate,
                        (date, v) -> {
                            SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                            /**
                             * 更新数据 ，不会破坏原有的布局
                             */
                            bean.setValue(format.format(date));
                            mAdapter.setNewData(mAdapter.getData());
                        }).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.sumbit)
    public void onViewClicked() {
        if (Constant.CommonCollectionEnum.COMMON_COLLECTION_ADD == commonCollectBean.getType()) {

        }
        Map<String, Object> map = new HashMap<>();
        for (BasicInformationBean.RecordsBean bean : mAdapter.getData()) {
            if (ObjectUtils.isNotEmpty(bean.getValue())) {
                if (ObjectUtils.isNotEmpty(bean.getSelectValue())) {
                    map.put(bean.getName(), bean.getSelectValue());
                } else {
                    map.put(bean.getName(), bean.getValue());
                }
            } else {
                if (bean.getRequire()) {
                    ToastUtils.showLong(bean.getTitile() + "为必填项");
                    return;
                }
            }
        }
        /**
         *  如果当前没有定位 把当前坐标传给后台
         */
        if (ObjectUtils.isEmpty(map.get("PointInJSON")) && ObjectUtils.isEmpty(map.get("LocationInJson"))) {
            BDLocation location =
                    LocSdkClient.getInstance(this).getLocationStart()
                            .getLastKnownLocation();
            TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean(location.getLatitude(), location.getLongitude());

            map.put("PointInJSON", gson.toJson(lonAndLat));
            map.put("LocationInJson", gson.toJson(lonAndLat));
        }
        map.putAll(commonCollectBean.getMap());
        mPresenter.createData(map, commonCollectBean.getPath());

    }


    /**
     * 获取枚举值
     */
    @Override
    public void getCommonEnums(List<CommonTitleBean> beans, String type) {
        List<NameAndIdBean> list = new ArrayList<>();
        for (CommonTitleBean bean : beans) {
            list.add(new NameAndIdBean(bean.getKey(), bean.getValue()));
        }
        if (commonRecyclerPopupAdapter == null) {
            commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter();
        }

        commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(this,
                commonRecyclerPopupAdapter, new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NameAndIdBean nameAndIdBean = (NameAndIdBean) adapter.getItem(position);
                /*
                  更新數據
                 */
                if (ObjectUtils.isNotEmpty(nameAndIdBean)) {
                    changeBean.setValue(nameAndIdBean.getName());
                    changeBean.setSelectValue(nameAndIdBean.getId());
                    mAdapter.setNewData(mAdapter.getData());
                }
                /*
                  关闭选择框
                 */
                commonRecyclerPopupWindow.dismiss();
            }
        });
        if (!commonRecyclerPopupWindow.isShowing()) {
            commonRecyclerPopupWindow.showPopupWindow();
        }
        commonRecyclerPopupWindow.setNewData(list);
    }

    @Override
    public Activity getContext() {
        return this;
    }


    /**
     * 更新图片地址
     *
     * @param url
     */
    @Override
    public void updatePicture(String url) {
        if (ObjectUtils.isNotEmpty(changeBean)) {
            changeBean.setValue(url);
            mAdapter.setNewData(mAdapter.getData());
        }

    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans, BasicInformationBean.RecordsBean mbean) {
        List<String> strings = new ArrayList<>();
        for (UploadFileRsponseBean bean : uploadFileRsponseBeans) {
            strings.add(bean.getUrl());
        }
        changeBean = mPresenter.getChangeBean();
        Timber.e(changeBean.toString());
        if (ObjectUtils.isNotEmpty(changeBean)) {
            List<String> imgeList = gson.fromJson(changeBean.getValue(), ArrayList.class);
            if (ObjectUtils.isNotEmpty(imgeList) && imgeList.size() > 0) {
                imgeList.addAll(strings);
                changeBean.setValue(gson.toJson(imgeList));
            } else {
                changeBean.setValue(gson.toJson(strings));
            }
            mAdapter.setNewData(mAdapter.getData());

        }
    }

    @Override
    public CommentCurrencyAdapter getAdapter() {
        return mAdapter;
    }


    /**
     * 相册操作
     */
    @Override
    public void goToPhotoAlbum() {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                1, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(CommonCollectionActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFile("", strings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    /**
     * 上传多张视频 或者图片
     *
     * @param max
     */
    @Override
    public void goToPhotoAlbum(int max, int type) {
        PictureSelectorUtils.gotoPhoto(this, type,
                max, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(CommonCollectionActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFileImgOrVideo("", strings, changeBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }


}
