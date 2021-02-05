package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.textfield.TextInputEditText;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerRestPasswordComponent;
import com.weique.overhaul.v2.mvp.contract.RestPasswordContract;
import com.weique.overhaul.v2.mvp.presenter.RestPasswordPresenter;
import com.jess.arms.utils.LoadingDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_RESTPASSWORDACTIVITY)
public class RestPasswordActivity extends BaseActivity<RestPasswordPresenter> implements RestPasswordContract.View {

    @BindView(R.id.old_pas)
    TextInputEditText oldPas;
    @BindView(R.id.new_pas)
    TextInputEditText newPas;
    @BindView(R.id.again_pas)
    TextInputEditText againPas;
    @BindView(R.id.submit)
    Button submit;

    private String PASSWORD_REGULAR = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,24}$";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRestPasswordComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rest_password;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        setTitle(getString(R.string.reset_password));
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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick(R.id.submit)
    public void onClick() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            KeybordUtil.hideKeyboard(this);
            String old = oldPas.getText().toString();
            if (StringUtil.isNullString(old)) {
                ArmsUtils.makeText("旧密码不能为空");
                return;
            }
            String newp = newPas.getText().toString();
            if (StringUtil.isNullString(newp)) {
                ArmsUtils.makeText("新密码不能为空");
                return;
            }
            // 创建 Pattern 对象
            if (newp.length() < 6 || newp.length() > 24) {
                ArmsUtils.makeText("新密码为6-24位字母加数字");
                return;
            }

            Pattern compile = Pattern.compile(PASSWORD_REGULAR);
            Matcher m = compile.matcher(newp);
            // 创建 Pattern 对象
            if (!m.matches()) {
                ArmsUtils.makeText("新密码为6-24位字母加数字");
                return;
            }
            String again = againPas.getText().toString();
            if (StringUtil.isNullString(again)) {
                ArmsUtils.makeText("再次输入新密码不能为空");
                return;
            }
            if (newp.equals(old)) {
                ArmsUtils.makeText("新密码不能和旧密码一样");
                return;
            }
            if (!again.equals(newp)) {
                ArmsUtils.makeText("再次输入密码不一致");
                return;
            }
            String oldSHA = ArmsUtils.encodeSHA(old).toUpperCase();
            String newpSHA = ArmsUtils.encodeSHA(newp).toUpperCase();
            mPresenter.resetPassword(oldSHA, newpSHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
