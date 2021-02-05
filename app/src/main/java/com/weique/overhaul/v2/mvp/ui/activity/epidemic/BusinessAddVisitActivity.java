package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerBusinessVisitComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessVisitContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessVisitPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.jess.arms.utils.LoadingDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 领导人新增寻访记录
 * 企业新增走访
 */

@Route(path = RouterHub.APP_BUSINESSVISITACTIVITY)
public class BusinessAddVisitActivity extends BaseActivity<BusinessVisitPresenter> implements BusinessVisitContract.View {

    @Autowired(name = ARouerConstant.ID)
    String id;
    @Autowired(name = ARouerConstant.COMMON_NAME)
    String name;

    @BindView(R.id.select_business)
    TextView selectBusiness;
    @BindView(R.id.select_time)
    TextView selectTime;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.feedback)
    EditText feedback;
    @BindView(R.id.issue_describe)
    EditText issueDescribe;


    /**
     * 走访时间
     */
    private TimePickerView pvTime;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;
    private ArrayList<InformationItemPictureBean> list;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessVisitComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_visit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("新增走访");
            ARouter.getInstance().inject(this);
            selectBusiness.setText((name));
            initAdapter();
            initTimePicker();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            list = new ArrayList<>();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));
            informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
            informationAddPhotoAdapter.setMaxItem(8);
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(informationAddPhotoAdapter);
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (view.getId() == R.id.image_) {
                        if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                            if (adapter.getData().size() >= 8) {
                                ArmsUtils.makeText("最多上传8张图片");
                                return;
                            }
                            //9  是加上了默认的加号 图标
                            int max = 9 - adapter.getData().size();
                            mPresenter.getPermission(max);
                        } else {
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                    .navigation();
                        }
                    } else if (view.getId() == R.id.remove_image) {
                        adapter.remove(position);
                        adapter.notifyItemChanged(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goToPhotoAlbum(int max) {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
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
                                        strings.add(PictureFileUtils.getPath(BusinessAddVisitActivity.this, Uri.parse(media.getPath())));
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

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                String path = uploadFileRsponseBean.getUrl();
                list.add(new InformationItemPictureBean((path)));
            }
            informationAddPhotoAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTimePicker() {

        /*
         * 隐患录入日期
         */
        try {
            pvTime = PickerViewUtil.selectPickerTime(this, Constant.YMD, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat(Constant.YMD, Locale.CHINA);
                        selectTime.setText(format.format(date));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
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

    @OnClick({R.id.select_time, R.id.btn_submit})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.select_time:
                    KeybordUtil.hideKeyboard(BusinessAddVisitActivity.this);
                    pvTime.show();
                    break;
                case R.id.btn_submit:
                    if (StringUtil.isNullString(id)) {
                        ArmsUtils.makeText("未获取到企业信息");
                        return;
                    }
                    if (StringUtil.isNullString(selectTime.getText().toString())) {
                        ArmsUtils.makeText("未选择时间");
                        return;
                    }
                    if (StringUtil.isNullString(issueDescribe.getText().toString())) {
                        ArmsUtils.makeText("企业困难及问题不能为空");
                        return;
                    }
                    if (StringUtil.isNullString(feedback.getText().toString())) {
                        ArmsUtils.makeText("企业意见建议不能为空");
                        return;
                    }
                    List<String> urls = null;
                    List<InformationItemPictureBean> data = informationAddPhotoAdapter.getData();
                    if (data.size() > 0) {
                        urls = new ArrayList<>();
                        for (InformationItemPictureBean dd : data) {
                            if (dd.getType() == InformationItemPictureBean.IS_URL) {
                                urls.add(dd.getImageUrl());
                            }
                        }
                    }
                    mPresenter.submit(id, selectTime.getText().toString(), issueDescribe.getText().toString(), feedback.getText().toString(), urls);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
