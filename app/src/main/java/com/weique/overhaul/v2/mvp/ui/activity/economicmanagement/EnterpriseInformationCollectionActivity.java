package com.weique.overhaul.v2.mvp.ui.activity.economicmanagement;

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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.ObjectUtils;
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
import com.weique.overhaul.v2.di.component.DaggerEnterpriseInformationCollectionComponent;
import com.weique.overhaul.v2.mvp.contract.EnterpriseInformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.collectBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnterpriseInformationCollectionPresenter;
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

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;
import static com.weique.overhaul.v2.app.utils.CollectionAdapterUtils.getMap;
import static com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean.RecordsBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 09:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author Administrator
 */

@Route(path = RouterHub.APP_ENTERPRISE_INFORMATION_COLLECTION, name = "企业信息采集页")
public class EnterpriseInformationCollectionActivity extends
        BaseActivity<EnterpriseInformationCollectionPresenter>
        implements EnterpriseInformationCollectionContract.View {


    /**
     * 通用adapter
     */
    @Inject
    CommentCurrencyAdapter mAdapter;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    /**
     * 初始化  选择框
     */
    private List<NameAndIdBean> typeList = new ArrayList<>();

    private List<NameAndIdBean> enterpriseTypeBeans = new ArrayList<>();

    private CommonRecyclerPopupWindow commonRecyclerPopupWindow;
    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;

    private List<collectBean> collectBean = new ArrayList<>();

    private RecordsBean imgBean;


    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnterpriseInformationCollectionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enterprise_information_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.enterprise_information_collection);
        initRecyclerAdapter();


    }


    /**
     * 初始化 信息类型
     */
    private void initRecyclerAdapter() {
        try {
            mPresenter.getList();
            mPresenter.getEnterpriseTypes();
            ArmsUtils.configRecyclerView(rvContent, new LinearLayoutManager(this));
            rvContent.setClipToPadding(false);
            rvContent.setAdapter(mAdapter);

            List<RecordsBean> list = new ArrayList<>();
            list.add(new RecordsBean(EDIT_ITEM, "企业名称", "Name", "请填写企业名称(必填)", true));
            list.add(new RecordsBean(EDIT_ITEM, "统一社会信用代码", "Code", "请填写信用代码(必填)", true));
            list.add(new RecordsBean(IMG_ITEM, "公司图片", "PhotoUrls", ""));
            list.add(new RecordsBean(EDIT_ITEM, "法定代表人", "LegalMan", "请填写法定代表人(必填)", true));
            list.add(new RecordsBean(NUMBER_EDIT_ITEM, "电话", "Tel", "请填写电话(必填)", true));
            list.add(new RecordsBean(DATA_ITEM, "成立日期", "FoundDate", "请填写成立日期"));
            list.add(new RecordsBean(EDIT_ITEM, "注册地址", "RegAddress", "请填写注册地址(必填)", true));
            list.add(new RecordsBean(EDIT_ITEM, "经营地址", "OperAddress", "请选择经营地址(必填)", true));
            list.add(new RecordsBean(ADDRESS_ITEM, "经营位置", "PointInJSON", "请选择经营位置(必填)", true));
            list.add(new RecordsBean(SELECT_ITEM, "所属行业", "EntBusinessTypeId", "请选择所属行业"));
            list.add(new RecordsBean(SELECT_ITEM, "企业登记注册类型", "EnumEntRegType", "请选择企业登记注册类型"));
            list.add(new RecordsBean(SELECT_ITEM, "五上企业", "EnumEntFUpType", "请选择五上企业"));
            list.add(new RecordsBean(SELECT_ITEM, "生产经营方式", "EnumEntManageType", "请选择生产经营方式"));
            list.add(new RecordsBean(SELECT_ITEM, "行政隶属关系", "EnumEntAdminType", "请选择行政隶属关系"));
            list.add(new RecordsBean(SELECT_ITEM, "企业规模", "EnumEntScaleType", "请选择企业规模"));
            list.add(new RecordsBean(NUMBER_EDIT_ITEM, "从业人员（人数）", "EmployeeCount", "请填写从业人员"));
            list.add(new RecordsBean(EDIT_ITEM, "包挂领导", "Charger", "请填写包挂领导"));
            list.add(new RecordsBean(EDIT_ITEM, "信用评价", "CreditEvaluates", "请填写信用评价"));


            mAdapter.setNewData(list);

            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

                Timber.e(adapter.getData().get(position).toString());
                RecordsBean bean = (RecordsBean) adapter.getData().get(position);

                switch (view.getId()) {

                        /*
                         图片添加
                         */
                    case R.id.rl_img_add:
                        if (ObjectUtils.isEmpty(bean.getValue())) {
                            mPresenter.getPermission();
                            imgBean = bean;
                        }
                        break;
                        /*
                         地址添加
                         */
                    /*
                     *  点击编辑
                     */
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
                                    initSelectAll(bean);
                                    break;
                                case ADDRESS_ITEM:
                                    imgBean = bean;
                                    ARouter.getInstance().build(RouterHub.APP_NEWMAPACTIVITY)
                                            .withString(ARouerConstant.DEPARTMENT_ID, UserInfoUtil.getUserInfo().getDepartmentId())
                                            .withString(ARouerConstant.SOURCE, RouterHub.APP_ENTERPRISE_INFORMATION_COLLECTION)
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
     * 初始化时间选择器
     */
    private void initTimePicker(final RecordsBean bean) {

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initSelectAll(final RecordsBean bean) {

        typeList.clear();
        if ("EntBusinessTypeId".equals(bean.getName())) {
            typeList.addAll(enterpriseTypeBeans);
        } else {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < collectBean.size(); i++) {
                if (collectBean.get(i).get字段名称().equals(bean.getName())) {
                    list.addAll(collectBean.get(i).get枚举列表());
                    break;
                }
            }

            for (int j = 0; j < list.size(); j++) {
                typeList.add(new NameAndIdBean(j + "", list.get(j)));
            }
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
                    bean.setValue(nameAndIdBean.getName());
                    bean.setSelectValue(nameAndIdBean.getId());
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

        commonRecyclerPopupWindow.setNewData(typeList);
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


    @Override
    public Activity getContext() {
        return this;
    }

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
                                        strings.add(PictureFileUtils.getPath(EnterpriseInformationCollectionActivity.this, Uri.parse(media.getPath())));
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
     * 更新图片地址
     *
     * @param url
     */
    @Override
    public void updatePicture(String url) {
        if (ObjectUtils.isNotEmpty(imgBean)) {
            imgBean.setValue(url);
            mAdapter.setNewData(mAdapter.getData());
        }

    }

    @Override
    public void submit() {
        finish();
    }

    @Override
    public void setListData(List<collectBean> bean) {
        collectBean = bean;
    }

    @Override
    public void getEnterpriseType(List<NameAndIdBean> enterpriseTypes) {
        enterpriseTypeBeans = enterpriseTypes;
    }


    @Subscriber(tag = RouterHub.APP_ENTERPRISE_INFORMATION_COLLECTION)
    private void eventBusCallback(EventBusBean<LocationAndName> eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_LOCATION:
                    LocationAndName data = eventBusBean.getData();
                    if (ObjectUtils.isNotEmpty(imgBean)) {
                        imgBean.setSelectValue(data.getLatLng().getPointJson());
                        imgBean.setValue(data.getLatLng().getLngAndLatName());
                        mAdapter.setNewData(mAdapter.getData());
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.sumbit)
    public void onViewClicked() {
        Map map = getMap(mAdapter.getData());
        if (ObjectUtils.isNotEmpty(map)) {
            mPresenter.createData(map);
        }

    }



}
