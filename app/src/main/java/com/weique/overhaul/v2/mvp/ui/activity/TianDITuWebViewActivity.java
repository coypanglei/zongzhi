package com.weique.overhaul.v2.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.example.agentweb.di.module.LocationBean;
import com.example.agentweb.di.module.MiddlewareChromeClient;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.WebViewClient;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.AppLifecycleImpl;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocationService;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.api.Api;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.weique.overhaul.v2.app.common.Constant.PERMISSIONS_LIST_SPLASH_;
import static com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity.ADDRESS_CAN_CHANGED;
import static com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity.LONANDLAT;
import static com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity.POINTS_IN_JSON;

@Route(path = RouterHub.TIANDITU_APP_COMMONWEBVIEWACTIVITY)
public class TianDITuWebViewActivity extends BaseActivity {


    @Autowired(name = ARouerConstant.TITLE)
    String title;
    @Autowired(name = ARouerConstant.WEB_URL)
    String webUrl;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * 网格
     */
    @Autowired(name = POINTS_IN_JSON)
    String pointsInJSON;
    /**
     * 是否允许修改坐标
     */
    @Autowired(name = ADDRESS_CAN_CHANGED)
    boolean addressCanChanged;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;

    /**
     * 区分 查看 坐标点地图   还是 在选择 坐标点地图
     */
    private boolean isChecked = false;
    /**
     * 上次的经纬度定位
     */
    @Autowired(name = LONANDLAT)
    String lonAndLat;
    protected AgentWeb mAgentWeb;
    @BindView(R.id.ll)
    LinearLayout ll;
    /**
     * 定位点
     */
    private LatLng mLocaltion;

    private LocationService locationService;
    private MyLocationListener myLocationListener;
    private GeoCoder mSearch;
    @BindView(R.id.address_detail)
    TextView addressDetail;
    /**
     * 用户点击的 点
     */
    private StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean;
    private String addressName = "未发现地图定位地址";

    Gson gson = new Gson();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_new_common_web_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        showProgressDialog();
        setTitle(title);
        TextView textView =findViewById(R.id.right_btn_text);
        textView.setText("确定");
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                try {
                    if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                          Toast.makeText(MapActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
                        return;
                    }
                    addressName = result.getAddress();
                    addressDetail.setText("具体地址:" + addressName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pathBean = gson.fromJson(lonAndLat, StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
        /**
         *  设置定位
         */
        initMap();
        /**
         *  是否能够点击
         */
        isChecked = source.equals(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                || source.equals(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                || source.equals(RouterHub.APP_STANDARDADDRESSLOOKACTIVITY);
        if (isChecked) {
            addressCanChanged = false;
        }
        if (RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY.equals(source)
                && !addressCanChanged) {
            addressCanChanged = false;
            isChecked = false;
        }
        mBridgeWebView = new BridgeWebView(this);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(ll, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setWebViewClient(getWebViewClient())
                .setWebView(mBridgeWebView)
                .useMiddlewareWebChrome(getMiddlewareWebChrome())
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()
                .ready().get();
        //隐藏 滑动条
        String domain;
        if (BuildConfig.DEBUG) {
            domain = AppUtils.getGlobalDomain(this);
        } else {
            domain = Api.APP_DOMAIN;
        }
        mAgentWeb.getWebCreator().getWebView().setScrollBarSize(0);
        Timber.e(domain);
        mAgentWeb.getUrlLoader().loadUrl("http://47.102.147.120:9014/H5TianDiTu/index.html");
        hideProgressDialog();
        /**
         *  点击回调 html 调用 记录点位
         */
        mBridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("data", data);
                LocationBean locationBean = gson.fromJson(data, LocationBean.class);
                pathBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean(locationBean.getLongitude(), locationBean.getLatitude());
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
            }

        });
    }


    /**
     * 初始化地图
     */
    private void initMap() {
        try {
            locationService = AppLifecycleImpl.getLocationService();
            myLocationListener = new MyLocationListener();
            locationService.registerListener(myLocationListener);
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始定位
     */
    private void startLocation() {
        locationService.start();
    }


    @OnClick({R.id.toolbar_back, R.id.right_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                if (!mAgentWeb.back()) {
                    finish();
                }
                break;
            case R.id.right_btn:
                Timber.e(pathBean.toString());
                Timber.e(isChecked + "ischecked");
                if (pathBean != null && !isChecked) {
                    EventBus.getDefault().post(
                            new EventBusBean<>(EventBusConstant.UPDATE_UP_LOCATION, "", pathBean), source);
                }
                if (StringUtil.isNotNullString(addressName)) {
                    EventBus.getDefault().post(
                            new EventBusBean<>(EventBusConstant.SELF_LOCALIZATION, "", addressName), source);
                }
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 实现定位回调
     */
    public class MyLocationListener extends BDAbstractLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                Timber.e("开始定位" + location.getLatitude());
                mLocaltion = new LatLng(location.getLatitude(), location.getLongitude());
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidPositionParams", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("Info", "value:" + value);

                    }
                }, putJson(gson.toJson(mLocaltion)), "定位", " Hello! Agentweb");
//                //停止定位服务
                locationService.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private BridgeWebView mBridgeWebView;

    private WebViewClient getWebViewClient() {
        return new WebViewClient() {
            BridgeWebViewClient mBridgeWebViewClient = new BridgeWebViewClient(mBridgeWebView);

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return mBridgeWebViewClient.shouldOverrideUrlLoading(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (mBridgeWebViewClient.shouldOverrideUrlLoading(view, request.getUrl().toString())) {
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, request);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mBridgeWebViewClient.onPageFinished(view, url);
                startLocation();
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidMoreParams", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("Info", "value:" + value);
                    }
                }, getJson(), "网格", " Hello! Agentweb");
                //"{\"lat\":34.300115,\"lng\":117.211792}"
            }

        };
    }


    protected MiddlewareWebChromeBase getMiddlewareWebChrome() {
        return new MiddlewareChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideProgressDialog();
                }
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        try {
            if (locationService != null) {
                if (myLocationListener != null) {
                    locationService.unregisterListener(myLocationListener);
                }
                locationService.stop();
                locationService = null;
            }
            mAgentWeb.clearWebCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    /**
     * 网格传值
     *
     * @return
     */
    private String getJson() {

        String result = "";


        try {
            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("addressCanChanged", addressCanChanged);
            //网格
            mJSONObject.put("pointsInJSON", pointsInJSON);
            //上一次的定位点
            if (ObjectUtils.isEmpty(lonAndLat)) {
            } else {
                mJSONObject.put("lonAndLat", lonAndLat);
            }
            result = mJSONObject.toString();
            Timber.e(result);

        } catch (Exception e) {

        }

        return result;
    }


    /**
     * 定位参数 传值 给web
     *
     * @return
     */
    private String putJson(String latLng) {
        String result = "";
        try {
            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("latLng", latLng);
            result = mJSONObject.toString();

        } catch (Exception e) {

        }

        return result;
    }
}
