package com.weique.overhaul.v2.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerMyInfoComponent;
import com.weique.overhaul.v2.mvp.contract.MyInfoContract;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MyInfoPresenter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 个人中心--我的资料
 *
 * @author GK
 */
@Route(path = RouterHub.APP_MY_INFO_ACTIVITY)
public class MyInfoActivity extends BaseActivity<MyInfoPresenter> implements MyInfoContract.View {


    @BindString(R.string.submit)
    String submit;
    @BindView(R.id.head_icon_image)
    ImageView headIconImage;
    @BindView(R.id.head_icon_layout)
    LinearLayout headIconLayout;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.name_layout)
    LinearLayout nameLayout;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.gender_layout)
    LinearLayout genderLayout;
    @BindView(R.id.be_born)
    TextView beBorn;
    @BindView(R.id.be_born_layout)
    LinearLayout beBornLayout;
    @BindView(R.id.unit_position)
    TextView unitPosition;
    @BindView(R.id.unit_position_layout)
    LinearLayout unitPositionLayout;
    @BindView(R.id.telephone)
    EditText telephone;
    @BindView(R.id.telephone_layout)
    LinearLayout telephoneLayout;
    @BindView(R.id.id_number)
    EditText idNumber;
    @BindView(R.id.id_number_layout)
    LinearLayout idNumberLayout;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;

    private EditText[] editTexts;

    /**
     * 用户信息
     */
    private GlobalUserInfoBean mMyInfoBean = new GlobalUserInfoBean();
    /**
     * 用户信息 改动
     */
    private GlobalUserInfoBean mMyInfoBeanChanged = new GlobalUserInfoBean();
    private OptionsPickerView pickerView;
    private List<String> list;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            KeybordUtil.hideKeyboard(this);
            setTitle(getString(R.string.user_info_));
            rightBtnText.setText(submit);
            mPresenter.getUserInfoDetail();
            editTexts = new EditText[]{name, telephone, idNumber};
            name.setSelection(name.getText().length());
            list = new ArrayList<>();
            list.add("男");
            list.add("女");
            setEditTextChangedListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEditTextChangedListener() {
        try {
            for (EditText editText : editTexts) {
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        try {
                            switch (editText.getId()) {
                                case R.id.name:
                                    mMyInfoBeanChanged.setName(s.toString());
                                    break;
                                case R.id.telephone:
                                    mMyInfoBeanChanged.setTel(s.toString());
                                    break;
                                case R.id.id_number:
                                    mMyInfoBeanChanged.setSID(s.toString());
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 更新个人信息
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MY_INFO_ACTIVITY)
    private void onEventbusCallBack(EventBusBean eventBusBean) {

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
        ArmsUtils.makeText(message);
    }


    @Override
    public void killMyself() {
        finish();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void setUserInfo(GlobalUserInfoBean myInfoBean) {
        try {
            mMyInfoBean = myInfoBean;
            mMyInfoBeanChanged = (GlobalUserInfoBean) myInfoBean.clone();
            name.setText(StringUtil.setText(mMyInfoBean.getName()));
            unitPosition.setText(StringUtil.setText(mMyInfoBean.getDepartmentName()) + "/" + StringUtil.setText(mMyInfoBean.getDepartName()));
            telephone.setText(StringUtil.setText(mMyInfoBean.getTel()));
            idNumber.setText(StringUtil.setText(mMyInfoBean.getSID()));
            gender.setText(StringUtil.setText(mMyInfoBean.getGender()));
            beBorn.setText(StringUtil.setText(mMyInfoBean.getBirthDate()));
            GlideUtil.loadImage(this, StringUtil.setText(mMyInfoBean.getHeadUrl()), headIconImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePicture(String url) {
        try {
            GlideUtil.loadImage(this, url, headIconImage);
            UserInfoUtil.updateUserInfoPhoto(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void goToPhotoAlbum() {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                1, false, true, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(MyInfoActivity.this, Uri.parse(media.getPath())));
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
    public void setSaveSuccess() {
        try {
            mMyInfoBean = (GlobalUserInfoBean) mMyInfoBeanChanged.clone();
            UserInfoUtil.saveUserInfo(mMyInfoBeanChanged);
            UserInfoUtil.updateUserInfoName(mMyInfoBeanChanged.getName());
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.right_btn, R.id.head_icon_layout, R.id.gender_layout, R.id.be_born_layout})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.right_btn:
                    KeybordUtil.hideKeyboard(this);
                    if (!mMyInfoBean.equals(mMyInfoBeanChanged)) {
                        if (StringUtil.isNullString(mMyInfoBeanChanged.getName())) {
                            ArmsUtils.makeText("请填写名称");
                            return;
                        }
                        if (StringUtil.isNullString(mMyInfoBeanChanged.getGender())) {
                            ArmsUtils.makeText("请选择性别");
                            return;
                        }
                        if (StringUtil.isNullString(mMyInfoBeanChanged.getBirthDate())) {
                            ArmsUtils.makeText("请选择出生日期");
                            return;
                        }
                        if (StringUtil.isNullString(mMyInfoBeanChanged.getTel()) || mMyInfoBeanChanged.getTel().length() < 11) {
                            ArmsUtils.makeText("请填写正确手机号码");
                            return;
                        }
                        mPresenter.saveUserInfo(mMyInfoBeanChanged);
                    } else {
                        ArmsUtils.makeText("您没有修改个人信息");
                    }
                    break;
                case R.id.gender_layout:
                    KeybordUtil.hideKeyboard(this);
                    if (pickerView == null) {
                        pickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v1) -> {
                            mMyInfoBeanChanged.setGender(list.get(options1));
                            gender.setText(list.get(options1));
                        }).build();
                        pickerView.setTitleText("性别");
                        pickerView.setPicker(list);
                    }

                    if ("女".equals(gender.getText())) {
                        pickerView.setSelectOptions(1);
                    } else {
                        pickerView.setSelectOptions(0);
                    }
                    if (!pickerView.isShowing()) {
                        pickerView.show();
                    }
                    break;
                case R.id.be_born_layout:
                    KeybordUtil.hideKeyboard(this);
                    PickerViewUtil.selectPickerTimeHasDefault(this, Constant.YMD1, beBorn.getText().toString(), (date, v12) -> {
                        String ymd = TimeUtil.dateToStringByFormit(date, Constant.YMD1);
                        mMyInfoBeanChanged.setBirthDate(ymd);
                        beBorn.setText(ymd);
                    }).show();
                    break;
                case R.id.head_icon_layout:
                    mPresenter.getPermission();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void backHandle() {
        if (!mMyInfoBeanChanged.equals(mMyInfoBean)) {
            new CommonDialog.Builder(this)
                    .setContent("个人信息有修改,是否保存")
                    .setNegativeButton("否", (view, commonDialog) -> {
                        super.onBackPressed();
                    })
                    .setPositiveButton("是", (view, commonDialog) -> {
                        mPresenter.saveUserInfo(mMyInfoBeanChanged);
                    }).create().show();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {
        backHandle();
    }
}
