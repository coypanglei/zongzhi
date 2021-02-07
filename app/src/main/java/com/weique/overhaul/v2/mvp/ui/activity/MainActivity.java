package com.weique.overhaul.v2.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.NetworkUtil;
import com.blankj.utilcode.util.MetaDataUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.broadcastreceiver.GpsStateReceiver;
import com.weique.overhaul.v2.app.broadcastreceiver.NetworkStateReceiver;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppProgressListenerUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.DownLoadUtil;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.RegexUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerMainComponent;
import com.weique.overhaul.v2.mvp.contract.MainContract;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserDepartmentBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.PushMessageBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MainPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.AsyncPlayer;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.SingleCallActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.AppFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.HomeFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.MyFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.weique.overhaul.v2.mvp.ui.popupwindow.VersionDialog;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_NO;
import static com.weique.overhaul.v2.app.common.EventBusConstant.ISRUNNINGFOREGROUND_YES;


/**
 * 主界面
 * ================================================
 * Description:首页
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Inject
    Gson gson;
    @Inject
    ACache aCache;
    @Inject
    OkHttpClient okHttpClient;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @BindView(R.id.message_img)
    ImageView messageImg;
    @BindView(R.id.scan_img)
    ImageView scanImg;
    @BindView(R.id.info_number)
    TextView infoNumber;
    @BindView(R.id.scan_text)
    TextView scanText;
    @BindView(R.id.message_text)
    TextView messageText;
    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.arrow_title)
    ImageView arrowTitle;
    @BindView(R.id.icon_news_layout)
    RelativeLayout iconNewsLayout;
    @BindView(R.id.icon_scan)
    LinearLayout iconScan;
    public static final int HOME = 0;
    public static final int MY = 1;
    public static final int APP = 2;
    private int index;
    private int currentIndex = HOME;
    private List<BaseFragment> fragments;
    private VersionDialog dialog;
    private String mUrl;
    private NewVersionInfoBean newVersionInfoBean;

    public static String isChat = "close";
    public static String isRunningForeground = "NO";//是否是在后台，如果在后台则当有视频推送时为YES，在前台则为NO
    private AsyncPlayer ringPlayer;

    /**
     * 是否是第一次加载
     */
    private boolean isFirst = true;
    private PushMessageBean listBean;
    private HomeFragment homeFragment;
    private AppFragment appFragment;
    private MyFragment myFragment;
    private CommonDialog commonDialog;

    /**
     * APP一直 后台运行 不提示版本更新问题
     */
    private boolean isFirstGetVersionInfo = true;

    private NetworkStateReceiver netWorkStateReceiver;
    private GpsStateReceiver gpsStateReceiver;


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
    private CommonRecyclerPopupAdapter<GlobalUserDepartmentBean> commonRecyclerPopupAdapter;
    private CommonRecyclerPopupWindow<GlobalUserDepartmentBean> commonRecyclerPopupWindow;

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            mUrl = null;
            if (netWorkStateReceiver != null) {
                unregisterReceiver(netWorkStateReceiver);
            }
            if (gpsStateReceiver != null) {
                unregisterReceiver(gpsStateReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
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
            //三方跳转
            if (RouterHub.APP_THIRDPARTYSPLASHPRESENTER.equals(source)) {
                if (!isIsExist) {
                    mPresenter.sgtToZzBinding(id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //第一次打开
        try {
            if (UserInfoUtil.getUserInfo() != null) {
                setTitle(UserInfoUtil.getUserInfo().getDepartmentName());
                arrowTitle.setColorFilter(ArmsUtils.getColor(this, R.color.white));
            }
            initIntent();
            String alias = UserInfoUtil.getUserInfo().getUid().replace("-", "");
            JPushInterface.setAlias(this, -1, alias);//设置别名或标签
            if (StringUtil.isNullString(aCache.getAsString(ACacheConstant.IS_FIRST_START_APP))) {
                aCache.put(ACacheConstant.IS_FIRST_START_APP, ACacheConstant.IS_FIRST_START_APP);
            }
            initViewData();
            if (StringUtil.isNotNullString(aCache.getAsString(ACacheConstant.HAS_NEW_MESSAGE_CODE))) {
                infoNumber.setVisibility(View.VISIBLE);
            } else {
                infoNumber.setVisibility(View.GONE);
            }
            title.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.white));
            mPresenter.getPermissionGps();
            mPresenter.getAppVersionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initViewData() {
        try {
            if (!NetworkUtil.isNetworkAvailable(this)) {
                new CommonDialog.Builder(this)
                        .setContent("您需要链接网络,才能继续使用")
                        .setNegativeButton("取消", (v, commonDialog) -> {
                            ArmsUtils.exitApp();
                        }).setPositiveButton("去设置", (v, commonDialog) -> {
                    Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    startActivityForResult(intent, Constant.NETWORK_SETTING);
                }).setCancelable(false).create().show();
            } else {
                if (homeFragment == null) {
                    homeFragment = HomeFragment.newInstance();
                }

                if (appFragment == null) {
                    appFragment = AppFragment.newInstance();
                }

                if (myFragment == null) {
                    myFragment = MyFragment.newInstance();
                }
                if (fragments == null || fragments.size() == 0) {
                    fragments = new ArrayList<>();
                    fragments.add(homeFragment);
                    fragments.add(myFragment);
                    fragments.add(appFragment);
                }
                showFragment();
                bottomNavigation.setItemIconTintList(null);
                bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
                    try {
                        isFirst = false;
                        switch (menuItem.getItemId()) {
                            case R.id.agenda:
                                index = HOME;
                                showFragment();
                                messageImg.setImageResource(R.drawable.icon_news_);
                                scanImg.setImageResource(R.drawable.icon_scan);
                                scanText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.white));
                                messageText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.white));
                                title.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.white));
                                arrowTitle.setColorFilter(ArmsUtils.getColor(this, R.color.white));
                                break;
                            case R.id.app_p:
                                index = APP;
                                showFragment();
                                messageImg.setImageResource(R.drawable.icon_news_b);
                                scanImg.setImageResource(R.drawable.icon_scan_bb);
                                scanText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                messageText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                title.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                arrowTitle.setColorFilter(ArmsUtils.getColor(this, R.color.black_333));
                                break;
                            case R.id.my:
                                index = MY;
                                showFragment();
                                messageImg.setImageResource(R.drawable.icon_news_b);
                                scanImg.setImageResource(R.drawable.icon_scan_bb);
                                scanText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                messageText.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                title.setTextColor(ArmsUtils.getColor(MainActivity.this, R.color.black_333));
                                arrowTitle.setColorFilter(ArmsUtils.getColor(this, R.color.black_333));
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.icon_news_layout, R.id.icon_scan, R.id.toolbar_title_layout})
    public void onClickView(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.icon_news_layout:
                    ARouter.getInstance().build(RouterHub.APP_MESSAGELISTACTIVITY)
                            .navigation();
                    break;
                //扫描
                case R.id.icon_scan:
                    mPresenter.getPermissionCamera();
                    break;
                case R.id.toolbar_title_layout:
                    GlobalUserInfoBean userInfo = UserInfoUtil.getUserInfo();
                    if (userInfo == null) {
                        ArmsUtils.makeText("获取用户信息失败");
                        return;
                    }
                    List<GlobalUserDepartmentBean> departments = userInfo.getDepartments();
                    if (departments == null || departments.size() <= 1) {
                        ArmsUtils.makeText("您只有当前一个负责区域，无法切换");
                        return;
                    }
                    List<GlobalUserDepartmentBean> temp = new ArrayList<>();
                    Iterator<GlobalUserDepartmentBean> iterator = departments.iterator();
                    while (iterator.hasNext()) {
                        GlobalUserDepartmentBean next = iterator.next();
                        if (!next.getDepartmentId().equals(userInfo.getDepartmentId())) {
                            temp.add(next);
                        }
                    }
                    if (commonRecyclerPopupAdapter == null) {
                        commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter<>();
                    }
                    if (commonRecyclerPopupWindow == null) {
                        commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(this,
                                commonRecyclerPopupAdapter, new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                GlobalUserDepartmentBean bean = (GlobalUserDepartmentBean) adapter.getItem(position);
                                if (ObjectUtils.isNotEmpty(bean)) {
                                    userInfo.setDepartmentId(bean.getDepartmentId());
                                    userInfo.setDepartmentName(bean.getDepartmentName());
                                    userInfo.setFullPath(bean.getDepartmentFullPath());
                                    userInfo.setEnumCommunityLevel(bean.getEnumCommunityLevel());
                                    title.setText(bean.getDepartmentName());
                                }
                                commonRecyclerPopupWindow.dismiss();
                                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
//                                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
                            }
                        });
                        commonRecyclerPopupWindow.setOutSideDismiss(true);
                    }
                    if (!commonRecyclerPopupWindow.isShowing()) {
                        commonRecyclerPopupWindow.showPopupWindow();
                    }
                    commonRecyclerPopupWindow.setNewData(temp);

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(this, path);
    }

    @Override
    public void registerReceiver() {
        try {
            if (gpsStateReceiver == null) {
                gpsStateReceiver = new GpsStateReceiver();
                IntentFilter gpsFilter = new IntentFilter();
                gpsFilter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
                gpsFilter.addAction("android.intent.action.DOWNLOAD_COMPLETED");
                registerReceiver(gpsStateReceiver, gpsFilter);
            }
            if (netWorkStateReceiver == null) {
                netWorkStateReceiver = new NetworkStateReceiver();
                IntentFilter filter = new IntentFilter();
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                filter.addAction("android.intent.action.DOWNLOAD_COMPLETED");
                registerReceiver(netWorkStateReceiver, filter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFragment() {
        try {
            if (!isFirst) {
                if (index == currentIndex) {
                    return;
                }
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.hide(fragments.get(currentIndex));
            if (!fragments.get(index).isAdded()) {
                ft.add(R.id.content, fragments.get(index));
            }
            ft.show(fragments.get(index)).commitAllowingStateLoss();
            currentIndex = index;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragment instanceof HomeFragment) {
            homeFragment = (HomeFragment) fragment;
        }
        if (fragment instanceof AppFragment) {
            appFragment = (AppFragment) fragment;
        }
        if (fragment instanceof MyFragment) {
            myFragment = (MyFragment) fragment;
        }
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
        ArmsUtils.makeText(message);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setSignData(String data) {
    }

    /**
     * @param o 设置是否有新消息
     */
    @Override
    public void setHasNewMessage(Boolean o) {
        try {
            if (o) {
                if (commonDialog == null) {
                    commonDialog = new CommonDialog.Builder(this).setTitle("提示").setContent("您有新消息")
                            .setPositiveButton("去看看", (v, commonDialog) -> {
                                ARouter.getInstance().build(RouterHub.APP_MESSAGELISTACTIVITY).navigation();
                            }).create();
                }
                if (!commonDialog.isShowing()) {
                    commonDialog.show();
                }
                aCache.put(ACacheConstant.HAS_NEW_MESSAGE_CODE, ACacheConstant.HAS_NEW_MESSAGE_CODE);
            } else {
                if (commonDialog != null && commonDialog.isShowing()) {
                    commonDialog.dismiss();
                }
                aCache.put(ACacheConstant.HAS_NEW_MESSAGE_CODE, "");
            }
            if (o) {
                infoNumber.setVisibility(View.VISIBLE);
            } else {
                infoNumber.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 版本相关
     *
     * @param newVersionInfoBean newVersionInfoBean
     */
    @SuppressLint("CheckResult")
    @Override
    public void showVersionDialog(NewVersionInfoBean newVersionInfoBean) {
        try {
            if (!newVersionInfoBean.isIsForceUpdate() && !isFirstGetVersionInfo) {
                return;
            }
            //先获取版本信息再获取 消息 状态
            this.newVersionInfoBean = newVersionInfoBean;
            mUrl = new String(newVersionInfoBean.getApkUrl());
            dialog = new VersionDialog(this, newVersionInfoBean);
            if (StringUtil.isNullString(mUrl) ||
                    !mUrl.endsWith(Constant.APK)) {
                ArmsUtils.makeText("安装包异常");
                dialog.dismiss();
                return;
            }
            dialog.setOnVersionDialogClickLisenter(() -> {
                mPresenter.getPermission(newVersionInfoBean);
            });
            dialog.setOnDismissListener(dialog -> {
                isFirstGetVersionInfo = false;
                getMessageStatus();
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.GET_NOTICE), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT);
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void showStartDownLoad(NewVersionInfoBean newVersionInfoBean) {
        try {
            ProgressManager.getInstance().addResponseListener(mUrl, AppProgressListenerUtil.getApkDownloadListener());
            DownLoadUtil.downloadStart(this, mUrl, okHttpClient, newVersionInfoBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessageStatus() {
        mPresenter.getMessageStatus();
    }

    @Override
    public void showOpenGpsDialog() {
        try {
            new CommonDialog.Builder(this).setTitle("提示")
                    .setContent("GPS未开启，会导致您的定位不准确")
                    .setNegativeBtnShow(false)
                    .setCancelable(false)
                    .setPositiveButton("去设置", (v, commonDialog) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, Constant.GPS_STATE);
                    }).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收服务上传签到点位
     *
     * @param eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MAINACTIVITY)
    private void eventBusCallback(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_LOCATION:
                    if (eventBusBean.getData() instanceof BDLocation) {
                        BDLocation bdLocation = (BDLocation) eventBusBean.getData();
                        TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean();
                        lonAndLat.setLat(bdLocation.getLatitude());
                        lonAndLat.setLng(bdLocation.getLongitude());
                        String longAndLatJson = gson.toJson(lonAndLat);
                        assert mPresenter != null;
                        mPresenter.automaticCheckInOrder(longAndLatJson, bdLocation.getAddrStr());
                    }
                    break;
                case EventBusConstant.REQUEST_AGAIN:
                    mPresenter.getAppVersionInfo();
                    if (dialog == null || !dialog.isShowing()) {
                        mPresenter.getAppVersionInfo();
                    }
                    break;
                case EventBusConstant.SET_SCAN_ICON_STATUS:
                    AccessControlUtil.fromActivityNameComparisonMenuControlViewInvisible(CaptureActivity.class, iconScan);
                    break;
                case EventBusConstant.DOWNLOAD_PREGRESS:
                    int progress = (int) eventBusBean.getData();
                    if (dialog != null) {
                        dialog.setUpdateProgress(progress);
                    }
                    break;
                case EventBusConstant.DOWNLOAD_PREGRESS_OK:
                    if (newVersionInfoBean != null) {
                        if (!dialog.isShowing()) {
                            new CommonDialog.Builder(this)
                                    .setContent("安装包下载完成去安装")
                                    .setPositiveButton((v, commonDialog) -> {
                                        File file = Constant.
                                                getNewVersionApkFile(newVersionInfoBean.getVersionName());
                                        if (file.exists()) {
                                            com.blankj.utilcode.util.AppUtils.installApp(file);
                                        } else {
                                            ArmsUtils.makeText("安装包不存在");
                                        }
                                    }).create().show();
                        } else {
                            dialog.setUpdateProgress((int) eventBusBean.getData());
                        }

                    }
                    break;
                case ISRUNNINGFOREGROUND_NO:
                    listBean = new PushMessageBean();
                    listBean = (PushMessageBean) eventBusBean.getData();
                    isRunningForeground = "YES";
                    ringPlayer = new AsyncPlayer(null);
                    int bell;
                    if (Boolean.parseBoolean(MetaDataUtils.getMetaDataInApp("isGuLouZF"))) {
                        bell = R.raw.gulou;
                    } else {
                        bell = R.raw.naoling;
                    }
                    Uri uri = Uri.parse("android.resource://" +
                            AppManager.getAppManager().getmApplication().getPackageName() + "/" + bell);
                    ringPlayer.play(AppManager.getAppManager().
                            getmApplication(), uri, true, AudioManager.STREAM_RING);
                    timer.start();
                    break;
                case ISRUNNINGFOREGROUND_YES:
                    if (ringPlayer != null) {
                        ringPlayer.stop();
                        if (timer != null) {
                            timer.cancel();
                        }
                        JPushInterface.clearAllNotifications(AppManager.getAppManager().getmApplication());
                        SingleCallActivity.openActivity(AppManager.getAppManager().getmApplication(),
                                listBean.getRoomId(), listBean.getWho(),
                                GlideUtil.handleUrl(this, listBean.getHeadUrl()),
                                listBean.getVideoEnable());
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CountDownTimer timer = new CountDownTimer(15 * 1000, 1000) {
        /**
         * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
        }

        /**
         * 倒计时完成时被调用
         */
        @Override
        public void onFinish() {
            ringPlayer.stop();
            timer.cancel();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constant.NETWORK_SETTING) {
                initViewData();
            }
            if (requestCode == Constant.GPS_STATE) {
                mPresenter.getPermissionGps();
            }
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case Constant.REQUEST_CODE_SCAN:
                        if (null != data) {
                            Bundle bundle = data.getExtras();
                            if (bundle == null) {
                                return;
                            }
                            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                                String result = bundle.getString(CodeUtils.RESULT_STRING);
                                if (TextUtils.isEmpty(result)) {
                                    ArmsUtils.makeText("二维码内容无效!");
                                } else {
                                    if (result.contains("http")) {
                                             /*
                                      标准地址
                                     */
                                        String code = RegexUtils.getMatches("code=(.*)&", result).get(0).replace("code=", "").replace("&", "");
                                        Timber.e(code);
                                        String type = RegexUtils.getMatches("type=(.*)", result).get(0).replace("type=", "");
                                        Timber.e(type);
                                        if ("1".equals(type)) {

                                        } else {
                                            ARouter.getInstance().build(RouterHub.APP_SCANRESULTACTIVITY)
                                                    .withString(ARouerConstant.SOURCE, result)
                                                    .navigation();
                                        }
                                    } else {
                                        ARouter.getInstance().build(RouterHub.APP_SCANRESULTACTIVITY)
                                                .withString(ARouerConstant.SOURCE, result)
                                                .navigation();
                                    }
                                }
                            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                                ArmsUtils.makeText("解析二维码失败!");
                            }
                        }
                        break;
                    // 得到通过UpdateDialogFragment默认dialog方式安装，用户取消安装的回调通知，以便用户自己去判断，比如这个更新如果是强制的，但是用户下载之后取消了，在这里发起相应的操作
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = "close")
    private void close(String s) {
        isChat = s;
    }


//    @SuppressLint("HandlerLeak")
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 100) {
//                JPushInterface.setAlias(getApplicationContext(), -1, "123456");
//            }
//        }
//    };
//    private JPushMessageReceiver jPushMessageReceiver = new JPushMessageReceiver() {
//        @Override
//        public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
//            super.onAliasOperatorResult(context, jPushMessage);
//            if (jPushMessage.getErrorCode() == 6002) {//超时处理
//                Message obtain = Message.obtain();
//                obtain.what = 100;
//                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
//            }else {
//                Log.e("onAliasOperatorResult: ", jPushMessage.getErrorCode()+"");
//            }
//        }
//    };
}
