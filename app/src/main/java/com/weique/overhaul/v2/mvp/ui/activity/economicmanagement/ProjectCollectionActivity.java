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
import com.weique.overhaul.v2.di.component.DaggerProjectCollectionComponent;
import com.weique.overhaul.v2.mvp.contract.ProjectCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.collectBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.ProjectCollectionPresenter;
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
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;
import static com.weique.overhaul.v2.app.utils.CollectionAdapterUtils.getMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2020 15:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_PROJECT_COLLECTION, name = "项目采集页面")
public class ProjectCollectionActivity extends BaseActivity<ProjectCollectionPresenter> implements ProjectCollectionContract.View {

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
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;

    private BasicInformationBean.RecordsBean imgBean;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProjectCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_project_collection; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.project_information_collection);
        initRecyclerAdapter();
    }

    /**
     * 初始化 信息类型
     */
    private void initRecyclerAdapter() {
        try {

            ArmsUtils.configRecyclerView(rvContent, new LinearLayoutManager(this));
            rvContent.setClipToPadding(false);
            rvContent.setAdapter(mAdapter);

            List<BasicInformationBean.RecordsBean> list = new ArrayList<>();
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目名称", "Name", "请填写项目名称(必填)", true));
            list.add(new BasicInformationBean.RecordsBean(IMG_ITEM, "项目图片", "PhotoUrls", ""));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM, "项目地址", "Address", "请填写项目地址", true));
            list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM, "项目位置", "PointInJSON", "请选择项目位置(必填)", true));
            list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "开工日期", "StartDate", "请填写开工日期"));
            list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "完工日期", "FinishDate", "请填写完工日期"));
            list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM, "项目介绍", "Desc", "请填写项目介绍"));
            list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "项目总投资(万元)", "TotalInvestment", "请填写项目总投资"));
            list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年计划投资额(万元)", "PlotInvestment", "请填写计划投资"));
            list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, "当年已完成投资(万元)", "Invested", "请填写已完成投资"));


            mAdapter.setNewData(list);

            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {

                Timber.e(adapter.getData().get(position).toString());
                BasicInformationBean.RecordsBean bean = (BasicInformationBean.RecordsBean) adapter.getData().get(position);

                switch (view.getId()) {
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
                        /*
                         图片添加
                         */
                    case R.id.rl_img_add:
                        if (ObjectUtils.isEmpty(bean.getValue())) {
                            assert mPresenter != null;
                            mPresenter.getPermission();
                            imgBean = bean;
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
    private void initTimePicker(final BasicInformationBean.RecordsBean bean) {

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


    private void initSelectAll(final BasicInformationBean.RecordsBean bean) {
        typeList.clear();
        typeList.add(new NameAndIdBean("", "教育"));
        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));
        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));

        typeList.add(new NameAndIdBean("", "教育"));


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
        }
    );

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
                                        strings.add(PictureFileUtils.getPath(ProjectCollectionActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            assert mPresenter != null;
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
     * @param url 图片地址
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
        killMyself();
    }

    @Override
    public void setListData(List<collectBean> bean) {

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
