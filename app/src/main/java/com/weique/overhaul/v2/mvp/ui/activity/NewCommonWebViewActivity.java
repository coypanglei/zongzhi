package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.agentweb.di.module.MiddlewareChromeClient;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.WebViewClient;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.api.Api;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

@Route(path = RouterHub.APP_COMMONWEBVIEWACTIVITY)
public class NewCommonWebViewActivity extends BaseActivity {


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.collect_image)
    ImageView collectImage;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Autowired(name = ARouerConstant.TITLE)
    String title;
    @Autowired(name = ARouerConstant.WEB_CONTENT)
    String webContent;
    @Autowired(name = ARouerConstant.WEB_URL)
    String webUrl;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    protected AgentWeb mAgentWeb;
    @BindView(R.id.ll)
    LinearLayout ll;


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

            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(ll, new LinearLayout.LayoutParams(-1, -1))
                    .closeIndicator()
                    .setWebViewClient(mWebViewClient)
                    .useMiddlewareWebChrome(getMiddlewareWebChrome())
                    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                    .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                    .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                    .createAgentWeb()
                    .ready().get();
            //隐藏 滑动条
            mAgentWeb.getWebCreator().getWebView().setScrollBarSize(0);

            if (StringUtil.isNotNullString(webContent)) {
//                String content = "<html>" + Constant.HTTP_HEAD + "<body>" + webContent + "</body></html>";
                String domain;
                if (BuildConfig.DEBUG) {
                    domain = AppUtils.getGlobalDomain(this);
                } else {
                    domain = Api.APP_DOMAIN;
                }
                Document parse = Jsoup.parseBodyFragment(webContent);
                Elements videos = parse.getElementsByTag("video");
                for (Element video : videos) {
                    String src = video.attr("src");
                    if (StringUtil.isNotNullString(src)) {
                        if (!src.startsWith(Constant.HTTP)) {
                            video.attr("src", domain + "/" + src);
                        }
                    }
                }
                Elements imgss = parse.getElementsByTag("img");
                for (Element imgs : imgss) {
                    String src = imgs.attr("src");
                    if (StringUtil.isNotNullString(src)) {
                        if (!src.startsWith(Constant.HTTP)) {
                            imgs.attr("src", domain + "/" + src);
                        }
                    }
                }

                Elements as = parse.getElementsByTag("a");

                for (Element a : as) {
                    String src = a.attr("href");
                    if (StringUtil.isNotNullString(src)) {
                        if (!src.startsWith(Constant.HTTP)) {
                            a.attr("href", domain + "/" + src);
                        }
                    }
                }
                String newStr = parse.body().toString();
                String content = "<html>" + Constant.HTTP_HEAD + newStr + "</html>";
                Timber.i("html  ------  " + content);
                mAgentWeb.getUrlLoader().loadDataWithBaseURL(null,
                        content, "text/html", "utf-8", null);
            } else if (StringUtil.isNotNullString(webUrl)) {
                mAgentWeb.getUrlLoader().loadUrl(webUrl);
            } else {
                ArmsUtils.makeText("通知详情为空");
                hideProgressDialog();
            }
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null && url.startsWith("http://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
                return true;
            } else {
                return false;
            }
        }
    };

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
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        //上个界面是  法务 相关通知列表
        if (StringUtil.isNotNullString(source)) {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), source);
        }
    }


    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        if (!mAgentWeb.back()) {
            finish();
        }
    }
}
