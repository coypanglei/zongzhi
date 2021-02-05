package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

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
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterStudyComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterStudyContract;
import com.weique.overhaul.v2.mvp.model.api.Api;
import com.weique.overhaul.v2.mvp.model.entity.QuestionStudyItemBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterStudyPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static java.util.regex.Pattern.compile;


/**
 * 党建文章学习并答题
 *
 * @author GK
 */
@Route(path = RouterHub.APP_PARTYCENTERSTUDYACTIVITY)
public class PartyCenterStudyActivity extends BaseActivity<PartyCenterStudyPresenter> implements PartyCenterStudyContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn_text_end)
    TextView rightBtnTextEnd;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.jz_video)
    JzvdStd jzVideo;
    @BindView(R.id.video_layout)
    LinearLayout videoLayout;

    @BindView(R.id.start_answer)
    TextView startAnswer;

    @Autowired
    String questionId;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.webview_vessel)
    FrameLayout webviewVessel;
    private WebView webView;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterStudyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_center_study;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        ARouter.getInstance().inject(this);
        initWebView();
        assert mPresenter != null;
        mPresenter.getPartyAnswerStudyData(questionId);


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


    @Override
    public void setAnswerStudyData(QuestionStudyItemBean questionStudyItemBean) {
        try {
            title.setText(questionStudyItemBean.getTitle());
            content.setText(getHTMLStr(questionStudyItemBean.getContent()));
            if (questionStudyItemBean.getVideoLink() != null &&
                    (questionStudyItemBean.getVideoLink().endsWith(Constant.MP4) ||
                            (questionStudyItemBean.getVideoLink().endsWith(Constant.RMVB)))) {
                videoLayout.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(this, questionStudyItemBean.getSubjectCoverImgUrl(), jzVideo.posterImageView);
                jzVideo.setUp(GlideUtil.handleUrl(this, questionStudyItemBean.getVideoLink()), "");
                pic.setVisibility(View.GONE);
            } else {
                videoLayout.setVisibility(View.GONE);
                pic.setVisibility(View.VISIBLE);
                GlideUtil.loadRoundImage(this, questionStudyItemBean.getSubjectCoverImgUrl(), pic, 5);

            }
            setWebviewContent(questionStudyItemBean.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getHTMLStr(String htmlStr) {

        //先将换行符保留，然后过滤标签
        Pattern p_enter = compile("<br/>", Pattern.CASE_INSENSITIVE);
        Matcher m_enter = p_enter.matcher(htmlStr);
        htmlStr = m_enter.replaceAll("\n");

        //过滤html标签
        Pattern p_html = compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        return m_html.replaceAll("");
    }


    @OnClick(R.id.start_answer)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            killMyself();
            ARouter.getInstance()
                    .build(RouterHub.APP_ANSWERDETAILACTIVITY)
                    .withString("questionId", questionId)
                    .navigation(PartyCenterStudyActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (webView != null) {
                webView.clearView();
                webView = null;
            }
            if (webviewVessel != null) {
                webviewVessel.removeAllViews();
            }
            if (jzVideo != null) {
                jzVideo = null;
            }
            Jzvd.releaseAllVideos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initWebView() {
        try {
            webView = new WebView(this);
            webviewVessel.addView(webView);
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
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                }
            });
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    if (progress == 100) {
                        hideProgressDialog();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setWebviewContent(String webContent) {
        if (StringUtil.isNotNullString(webContent)) {
//                String content = "<html>" + Constant.HTTP_HEAD + "<body>" + webContent + "</body></html>";
            String domain = "";
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
            webView.loadDataWithBaseURL(null,
                    content, "text/html", "utf-8", null);
        }
    }

}
