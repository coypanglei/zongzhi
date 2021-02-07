package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kyleduo.switchbutton.SwitchButton;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerSpeechSettingComponent;
import com.weique.overhaul.v2.dynamic.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.contract.SpeechSettingContract;
import com.weique.overhaul.v2.mvp.presenter.SpeechSettingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.speechrecognition.utils.ControlViewUtils.OPEN_SPEECH;
import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_LANGUAGE;
import static com.example.speechrecognition.utils.ControlViewUtils.SPEECH_MODE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author panglei
 */

@Route(path = RouterHub.APP_SPEECH_SETTING_ACTIVITY, name = "语音设置")
public class SpeechSettingActivity extends BaseActivity<SpeechSettingPresenter> implements SpeechSettingContract.View {

    @BindView(R.id.sb_text)
    SwitchButton sbText;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_usage)
    TextView tvUsage;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private OptionsPickerView pickerView;


    private OptionsPickerView pickerViewTwo;

    private List<CommonTitleBean> list = new ArrayList<>();


    private List<CommonTitleBean> listTwo = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSpeechSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_speech_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("语音设置");
        list.add(new CommonTitleBean("mandarin", "普通话"));
        list.add(new CommonTitleBean("tianjinese", "天津话"));

        listTwo.add(new CommonTitleBean("click_mode", "点击弹窗自动识别"));
        listTwo.add(new CommonTitleBean("long_click_mode", "长按识别"));
        String language = SPUtils.getInstance().getString(SPEECH_LANGUAGE, "mandarin");
        if (language.equals("mandarin")) {
            tvLanguage.setText("普通话");
        } else {
            tvLanguage.setText("天津话");
        }


        String mode = SPUtils.getInstance().getString(SPEECH_MODE, "click_mode");
        if (mode.equals("long_click_mode")) {
            tvUsage.setText("长按识别");
        } else {
            tvUsage.setText("点击弹窗自动识别");
        }
        boolean isOpen = SPUtils.getInstance().getBoolean(OPEN_SPEECH, true);
        if (isOpen) {
            sbText.setChecked(true);
        } else {
            sbText.setChecked(false);
        }
        sbText.setOnCheckedChangeListener((buttonView, isChecked) -> SPUtils.getInstance().put(OPEN_SPEECH, isChecked));
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


    @OnClick({R.id.rl_language, R.id.rl_usage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_language:
                if (pickerView == null) {
                    pickerView = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                        SPUtils.getInstance().put(SPEECH_LANGUAGE, list.get(options1).getKey());
                        tvLanguage.setText(list.get(options1).getValue());
                    }).setTitleBgColor(0xFFFFFFFF).build();
                }
                pickerView.setTitleText("选择语言");

                pickerView.setPicker(list);
                     /*
                  如果已经有值就默认选择
                 */
                for (int i = 0; i < list.size(); i++) {
                    if (tvLanguage.getText().toString().equals(list.get(i).getValue())) {
                        pickerView.setSelectOptions(i);
                    }
                }
                if (!pickerView.isShowing()) {
                    pickerView.show();
                }
                break;
            case R.id.rl_usage:
                if (pickerViewTwo == null) {
                    pickerViewTwo = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
                        SPUtils.getInstance().put(SPEECH_MODE, listTwo.get(options1).getKey());
                        tvUsage.setText(listTwo.get(options1).getValue());
                    }).setTitleBgColor(0xFFFFFFFF).build();
                }
                pickerViewTwo.setTitleText("选择使用方式");

                pickerViewTwo.setPicker(listTwo);
                     /*
                  如果已经有值就默认选择
                 */
                for (int i = 0; i < list.size(); i++) {
                    if (tvUsage.getText().toString().equals(listTwo.get(i).getValue())) {
                        pickerViewTwo.setSelectOptions(i);
                    }
                }
                if (!pickerViewTwo.isShowing()) {
                    pickerViewTwo.show();
                }
                break;


            default:
                break;
        }
    }
}
