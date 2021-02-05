package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPartyContentArticleDetailComponent;
import com.weique.overhaul.v2.mvp.contract.PartyContentArticleDetailContract;
import com.weique.overhaul.v2.mvp.model.api.Api;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyContentArticleDetailPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建中心 文章详情
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_PARTYCONTENTARTICLEDETAILACTIVITY)
public class PartyContentArticleDetailActivity extends BaseActivity<PartyContentArticleDetailPresenter> implements PartyContentArticleDetailContract.View {

    /**
     * 收藏 成功
     */
    public static final int IS_COLLECT = 0;
    /**
     * 未收藏
     */
    public static final int NOT_COLLECT = 1;

    @BindView(R.id.web_view_vessel)
    FrameLayout webViewVessel;
    @BindView(R.id.collect_image)
    ImageView collectImage;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    /**
     * 获取文章详情的id
     */
    @Autowired
    String id;
    /**
     * 区分调用接口的  字段
     */
    @Autowired
    int type;
    /**
     * 从收藏中进入传过来的position
     */
    @Autowired
    int position = -1;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.section)
    TextView section;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.time)
    TextView time;


    private WebView webView;
    private int mIsCollect = -1;

    private PartyDetailBean bean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyContentArticleDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_content_article_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        showProgressDialog();
        initWebView();
        assert mPresenter != null;
        mPresenter.getGetArticleDetail(type, id);
        //只有新闻能收藏 其余的不显示收藏逻辑
        if (type == PartyCenterActivity.NEWS_CENTER) {
            mPresenter.addOrSelectCollectStatus(true, id);
        } else {
            rightBtn.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initWebView() {
        try {
            webView = new WebView(getApplicationContext());
            webView.setVerticalScrollBarEnabled(false);
            WebSettings settings = webView.getSettings();
            //是否支持缩放，配合方法setBuiltInZoomControls使用，默认true
            settings.setSupportZoom(false);
            //是否需要用户手势来播放Media，默认true
            settings.setMediaPlaybackRequiresUserGesture(true);
            //是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
            settings.setBuiltInZoomControls(false);
            //是否显示窗口悬浮的缩放控制，默认true
            settings.setDisplayZoomControls(false);
            //设置是否允许执行JS。
            settings.setJavaScriptEnabled(true);
            //将图片调整到适合webview的大小
            settings.setUseWideViewPort(true);
            // 缩放至屏幕的大小
            settings.setLoadWithOverviewMode(true);
            settings.setTextZoom(90);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    try {
                        if (progress == 100) {
                            hideProgressDialog();
                            title.setText((bean.getTitle()));
                            TextPaint tp = title.getPaint();
                            tp.setFakeBoldText(bean.isTitleBold());
                            author.setText(String.format("责任编辑 : %s", (bean.getAuthor())));
                            section.setText(String.format("编辑部门 : %s", (bean.getDepartmentName())));
                            if (StringUtil.isNotNullString((bean.getStartTime()))
                                    && StringUtil.isNotNullString((bean.getEndTime()))) {
                                String sss = "开始时间: " + (bean.getStartTime())
                                        + "\n" + "结束时间: " + (bean.getEndTime());
                                time.setText(sss);
                            } else {
                                time.setText(String.format("发布时间 : %s", (bean.getCreateTime())));
                            }
                        } else {
                            showProgressDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            webView.setWebViewClient(new WebViewClient() {
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

                @Override
                public void onPageFinished(WebView view, String url) {
                    //                webView.loadUrl("javascript:document.body.style.paddingLeft=\"2%\";javascript:document.body.style.paddingRight=\"2%\"; void 0");
                }
            });
            webViewVessel.addView(webView);
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

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 获取context
     *
     * @return Activity
     */
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void show(PartyDetailBean bean) {
        try {
            this.bean = bean;
            GlobalUserInfoBean userInfo = UserInfoUtil.getUserInfo();
            if (bean.getIntegral() > userInfo.getSum()) {
                userInfo.setSum(bean.getIntegral());
                UserInfoUtil.saveUserInfo(userInfo);
                //更新积分
                EventBus.getDefault().post(
                        new EventBusBean(PartyCenterActivity.PARTY_UPDATE_INTEGRAL_CODE, String.valueOf(bean.getIntegral())),
                        PartyCenterActivity.PARTY_UPDATE_INTEGRAL);
            }

            String domain = "";
            if (BuildConfig.DEBUG) {
                domain = AppUtils.getGlobalDomain(this);
            } else {
                domain = Api.APP_DOMAIN;
            }
            Document parse = Jsoup.parseBodyFragment(bean.getContent());
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
            webView.loadDataWithBaseURL(null,
                    content, "text/html", "utf-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置收藏
     *
     * @param isCollect 判断是否是收藏  0
     */
    @Override
    public void setCollect(int isCollect) {
        this.mIsCollect = isCollect;
        if (rightBtn.getVisibility() == View.GONE) {
            rightBtn.setVisibility(View.VISIBLE);
        }
        if (mIsCollect == IS_COLLECT) {
            collectImage.setImageResource(R.drawable.collect_true);
        } else {
            collectImage.setImageResource(R.drawable.collect_false);
        }
    }

    @OnClick({R.id.right_btn})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (v.getId() == R.id.right_btn) {
                //已收藏 - 去 取消
                assert mPresenter != null;
                if (mIsCollect == IS_COLLECT) {
                    mPresenter.removeCollectStatus(id);
                    //未收藏 - 去 添加
                } else {
                    mPresenter.addOrSelectCollectStatus(false, id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (webView != null) {
                webView.clearHistory();
                webView.clearView();
                webView.destroy();
                webView = null;
            }
            if (webViewVessel.getChildCount() > 0) {
                webViewVessel.removeAllViews();
            }
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
