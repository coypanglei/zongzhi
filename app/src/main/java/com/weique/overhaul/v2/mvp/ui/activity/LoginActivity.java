package com.weique.overhaul.v2.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.NetworkUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.material.textfield.TextInputEditText;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.CodeUtils;
import com.weique.overhaul.v2.app.customview.DrawableTextView;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.LoginNamePasswordUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerLoginComponent;
import com.weique.overhaul.v2.mvp.contract.LoginContract;
import com.weique.overhaul.v2.mvp.model.entity.ExpandItem;
import com.weique.overhaul.v2.mvp.model.entity.ExpandItemOne;
import com.weique.overhaul.v2.mvp.model.entity.LoginInfoBean;
import com.weique.overhaul.v2.mvp.presenter.LoginPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.QuickExpandableAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.AgreementDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 登录
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_LOGINACTIVITY)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Inject
    ImageLoader imageLoader;
    @BindView(R.id.logo)
    DrawableTextView logo;
    @BindView(R.id.et_mobile)
    TextInputEditText etMobile;
    @BindView(R.id.et_authcode)
    TextInputEditText etAuthcode;
    @BindView(R.id.auth_code_img)
    ImageView authCodeImg;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.get_auth_code)
    TextView getAuthCode;
    @BindView(R.id.agreement)
    TextView agreement;
    @BindView(R.id.and_login)
    TextView andLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.environment_list)
    RecyclerView environmentList;
    @BindView(R.id.et_authcode_layout)
    LinearLayout etAuthcodeLayout;
    @BindView(R.id.ip_info)
    TextInputEditText ipInfo;
    @Inject
    AppManager mAppManager;


    private CodeUtils codeUtils;

    private boolean isValidate = true;

    private LoginNamePasswordUtil loginNamePasswordUtil;


    private static final int VERIFY_INTERVAL = 30 * 1000;


    /**
     * 是否已绑定
     */
    @Autowired(name = ARouerConstant.IS_BINDING)
    boolean isIsExist;
    /**
     * 上个界面名称
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * 苏格通id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * 登录名 - > 其实事 ID 综治用户id
     */
    @Autowired(name = ARouerConstant.LOGIN_NAME)
    String zongzhiId;

    /**
     * 登录信息缓存key
     */
    @Override
    protected void onResume() {
        super.onResume();
        judgeNetwork();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initIntent();
    }

    private void initIntent() {
        try {
            ARouter.getInstance().inject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            if (UserInfoUtil.getUserInfo() != null) {
                UserInfoUtil.loginOut(this);
                AppManager.getAppManager().killAll(LoginActivity.class);
            }
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            loginNamePasswordUtil = new LoginNamePasswordUtil();
            initAgreement();
            initLogin();
            initAuthCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化登录
     */
    private void initLogin() {
        try {
            if (BuildConfig.DEBUG) {
                initEnvironmentList();
            } else {
                environmentList.setVisibility(View.GONE);
                ipInfo.setVisibility(View.GONE);
            }
            //判断是否是第一次打开  是否展示弹窗
            if (StringUtil.isNullString(ACache.get(this).getAsString(ACacheConstant.AGREE_WITH_THE_PROTOCOL))) {
                AgreementDialog agreementDialog = new AgreementDialog(this);
                agreementDialog.show();
            }
            //处理缓存中用户信息
            List<LoginInfoBean> allLoginName = loginNamePasswordUtil.getAllLoginName(this);
            if (allLoginName != null && allLoginName.size() > 0) {
                LoginInfoBean bean = allLoginName.get(0);
                etMobile.setText((bean.getName()));
                etPassword.setText((bean.getPassword()));
            }
            //判断是否验证用户
            if (StringUtil.isNotNullString(loginNamePasswordUtil.getLastLoginTime(this))) {
                String lastLoginTime = loginNamePasswordUtil.getLastLoginTime(this);
                long last = Long.parseLong(lastLoginTime);
                long newTime = System.currentTimeMillis();
                if (newTime - last < VERIFY_INTERVAL) {
                    etAuthcodeLayout.setVisibility(View.VISIBLE);
                    isValidate = true;
                } else {
                    etAuthcodeLayout.setVisibility(View.GONE);
                    isValidate = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEnvironmentList() {
        try {

            environmentList.setVisibility(View.VISIBLE);
            TreeMap<String, String> environmentMap = new TreeMap<>();
            environmentMap.put("0.本地夏仲翰", "http://192.168.20.25");
            environmentMap.put("测.测试专属-合肥瑶海-2020-9-18", "http://47.102.147.120:9006");
//            environmentMap.put("0.本地王琰", "http://192.168.20.114:9090");
//            environmentMap.put("0.黄老师", "http://192.168.20.177:805");
//            environmentMap.put("0.马玉巧", "http://192.168.20.123:80");
            environmentMap.put("测.测试1+4", "http://47.102.147.120:9013");
            environmentMap.put("测.测试静海", "http://47.102.147.120:9014");

            environmentMap.put("测.阿里测试", "http://47.102.147.120:2020");
            environmentMap.put("测.天津测试", "http://192.168.20.211:9011");
            environmentMap.put("生产.鼓楼政法委/公安", "http://xzgl.jsweique.com");
//            environmentMap.put("2.鼓楼森林防火", "http://47.102.147.120:8090");
//            environmentMap.put("3.鼓楼疫情", "http://47.102.147.120:8088");
            environmentMap.put("生产.鼓楼1+4", "http://1p4.xzgl.jsweique.com");
            environmentMap.put("生产.铜山", "http://157.0.243.110:8280");
            environmentMap.put("生产.丰县", "http://58.218.242.84:8280");
            environmentMap.put("生产.经开区", "http://101.133.211.243:8088");
            environmentMap.put("生产.贾汪政法委(新)", "http://36.154.82.22:2021");
//            environmentMap.put("生产.合肥企业复工", "http://58.243.183.70:2021");
            environmentMap.put("生产.合肥综治", "http://58.243.183.70:8088");
            environmentMap.put("生产.合肥1加4", "http://58.243.183.70:8084");
            environmentMap.put("生产.贾汪公安新地址", "http://47.102.147.120:8081");
            environmentMap.put("生产.沛县", "http://120.195.6.67:2021");
            environmentMap.put("生产.沛县1+4", "http://36.133.29.114:9002");
            environmentMap.put("生产.云龙", "http://222.187.92.83:2021");
            environmentMap.put("生产.云龙1+4", "http://xzyl.jsweique.com:9005");
            environmentMap.put("生产.邯郸", "http://157.0.243.104:8013");
            environmentMap.put("生产.天津西营门街道", "http://47.102.147.120:9011");
            environmentMap.put("生产.天津静海区", "http://211.94.218.54:2001");
            List<MultiItemEntity> data = new ArrayList<>();
            ExpandItem item0 = new ExpandItem();
            int subperPos = 0;
            boolean isOne = false;
            Iterator<Map.Entry<String, String>> iterator = environmentMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                if (!isOne) {
                    isOne = true;
                    item0.setTitle("默认（" + next.getKey() + ")");
                    item0.setValue(next.getValue());
                    AppUtils.setGlobalDomain(this, next.getValue());
                    if (AppUtils.getGlobalDomain(this).contains(next.getValue())) {
                        ArmsUtils.makeText("服务器切换至" + next.getKey());
                    }
                }
                ExpandItemOne item1 = new ExpandItemOne(next.getKey(), next.getValue(), subperPos);
                item0.addSubItem(item1);
            }
            data.add(item0);
            QuickExpandableAdapter quickExpandableAdapter = new QuickExpandableAdapter(data);
            quickExpandableAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            ArmsUtils.configRecyclerView(environmentList, new LinearLayoutManager(this));
            environmentList.setAdapter(quickExpandableAdapter);
            quickExpandableAdapter.expandAll(0, true);
            quickExpandableAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    Object item = adapter.getItem(position);
                    if (item instanceof ExpandItemOne) {
                        ExpandItemOne eItem1 = (ExpandItemOne) item;
                        int superPos = eItem1.getSuperPos();
                        Object item1 = adapter.getItem(superPos);
                        if (item1 instanceof ExpandItem) {
                            ExpandItem expandItem = (ExpandItem) item1;
                            expandItem.setValue(eItem1.getValue());
                            expandItem.setTitle(eItem1.getTitle());
                            adapter.notifyDataSetChanged();
                        }
                        AppUtils.setGlobalDomain(this, eItem1.getValue());
                        if (AppUtils.getGlobalDomain(this).contains(eItem1.getValue())) {
                            ArmsUtils.makeText("服务器切换至" + eItem1.getTitle());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void judgeNetwork() {
        try {
            if (!NetworkUtil.isNetworkAvailable(this)) {
                new CommonDialog.Builder(this)
                        .setContent(getResources().getString(R.string.connect_network_hint))
                        .setNegativeButton(getResources().getString(R.string.ove), (v, commonDialog) -> {
                            moveTaskToBack(true);
                        }).setPositiveButton(getResources().getString(R.string.goto_set), (v, commonDialog) -> {
                    Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    startActivityForResult(intent, Constant.NETWORK_SETTING);
                }).setCancelable(false).create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAgreement() {
        try {
            SpannableString spanStrStart = new SpannableString(getString(R.string.agreement));
            SpannableString spanStrClick = new SpannableString(getString(R.string.agreement_dialog_content2));
            spanStrClick.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                            .withString(ARouerConstant.TITLE, getString(R.string.user_agreement))
                            .withString(ARouerConstant.WEB_URL, BuildConfig.SERVER_URL + Constant.USER_AGREEMENT)
                            .navigation();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(LoginActivity.this, R.color.blue_4D8FF7));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrClick2 = new SpannableString(LoginActivity.this.getString(R.string.agreement_dialog_content3));
            spanStrClick2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                            .withString(ARouerConstant.TITLE, LoginActivity.this.getString(R.string.privacy_policy))
                            .withString(ARouerConstant.WEB_URL, BuildConfig.SERVER_URL + Constant.PRIVACY_POLICY)
                            .navigation();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(LoginActivity.this, R.color.blue_4D8FF7));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            agreement.append(spanStrStart);
            agreement.append(spanStrClick);
            agreement.append(spanStrClick2);
            agreement.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void initAuthCode() {
        try {
            codeUtils = CodeUtils.getInstance();
            Bitmap bitmap = codeUtils.createBitmap();
            authCodeImg.setImageBitmap(bitmap);
            if (BuildConfig.DEBUG) {
                etAuthcode.setText(codeUtils.getCode());
            }
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
        ArmsUtils.makeText(message);
    }


    /**
     * 路由跳转
     *
     * @param path
     */
    @Override
    public void launchActivityByRouter(@NonNull String path) {
        try {
            ARouterUtils.navigation(this, path);
            killMyself();
            if (StringUtil.isNotNullString(etMobile.getText().toString())) {
                String loginName = etMobile.getText().toString();
                String ps = etPassword.getText().toString();
                loginNamePasswordUtil.addLoginName(this, new LoginInfoBean(loginName, ps));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * 倒计时
     *
     * @param second 秒
     */
    @Override
    public void setAuthCode(String second) {
        getAuthCode.setText(second);
    }

    /**
     * 设置验证码 view 样式
     *
     * @param drawableId drawableId
     * @param color      color
     * @param b          是否可点击
     */
    @Override
    public void setAuthCodeViewBackground(int drawableId, int color, boolean b) {
        try {
            getAuthCode.setEnabled(b);
            getAuthCode.setTextColor(color);
            getAuthCode.setBackgroundResource(drawableId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_login, R.id.get_auth_code, R.id.auth_code_img, R.id.clear_name})
    public void onClickView(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.btn_login:
                    if (StringUtil.isNotNullString(ipInfo.getText().toString())) {
                        AppUtils.setGlobalDomain(this, ipInfo.getText().toString());
                    }
                    KeybordUtil.hideKeyboard(this);
                    String loginName = etMobile.getText().toString();
                    String ps = etPassword.getText().toString();
                    String code = etAuthcode.getText().toString();
                    if (StringUtil.isNullString(loginName)) {
                        ArmsUtils.makeText(ArmsUtils.getString(this, R.string.input_phone));
                        return;
                    }
                    if (isValidate && StringUtil.isNullString(ps)) {
                        ArmsUtils.makeText(ArmsUtils.getString(this, R.string.auth_code_not_null));
                        return;
                    }
                    if (isValidate && !codeUtils.getCode().equalsIgnoreCase(code)) {
                        ArmsUtils.makeText(ArmsUtils.getString(this, R.string.please_input_success_code));
                        return;
                    }
                    mPresenter.getValidateCodeLogin(loginName, ArmsUtils.encodeSHA(ps).toUpperCase(), zongzhiId, source, isIsExist, id);
                    break;
                case R.id.get_auth_code:
                    mPresenter.startGetAuthCode(etMobile.getText().toString());
                    break;
                case R.id.auth_code_img:
                    initAuthCode();
                    break;
                case R.id.clear_name:
                    etMobile.setText("");
                    etPassword.setText("");
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constant.NETWORK_SETTING) {
                initLogin();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
