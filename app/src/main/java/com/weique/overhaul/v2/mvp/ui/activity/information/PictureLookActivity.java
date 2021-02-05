package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.MyJzvdStd;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.videoplayer.JZMediaExo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import timber.log.Timber;

@Route(path = RouterHub.APP_PICTURELOOKACTIVITY)
public class PictureLookActivity extends AppCompatActivity {

    public static final String URL_ = "URL_";
    @Autowired(name = URL_)
    String url;

    @Autowired(name = ARouerConstant.TITLE)
    String title;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.image)
    PhotoView image;
    @BindView(R.id.remove_image)
    RelativeLayout removeImage;
    @BindView(R.id.image_layout)
    RelativeLayout imageLayout;
    @BindView(R.id.video_layout)
    RelativeLayout videoLayout;
    @BindView(R.id.video_view)
    MyJzvdStd videoView;

    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_picture_look);
            ButterKnife.bind(this);
            ARouter.getInstance().inject(this);
            setTitle("媒体资源");

            if (!ArmsUtils.isEmpty(title)) {
                setTitle(title);
            }

            if (StringUtil.isNullString(url)) {
                ArmsUtils.makeText("资源信息错误");
                return;
            }
            if (url.endsWith(Constant.MP4)) {
                imageLayout.setVisibility(View.GONE);
                videoLayout.setVisibility(View.VISIBLE);
                mMediaController = new MediaController(this);
                GlideUtil.getLoadVideoBitmap(this, url, videoView.posterImageView);
                videoView.setUp(GlideUtil.handleUrl(this, url), "", JzvdStd.SCREEN_NORMAL);
            } else if (url.endsWith(Constant.JPG) || url.endsWith(Constant.PNG) || url.endsWith(Constant.JPEG)) {
                imageLayout.setVisibility(View.VISIBLE);
                image.setVisibility(View.VISIBLE);
                videoLayout.setVisibility(View.GONE);
                GlideUtil.loadImage(this, url, image);
            } else if (url.contains("m3u8")) {
                Timber.e(url);
                imageLayout.setVisibility(View.GONE);
                videoLayout.setVisibility(View.VISIBLE);
                mMediaController = new MediaController(this);
                videoView.setUp(url, "", JzvdStd.SCREEN_NORMAL, JZMediaExo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.toolbar_back})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (videoView != null) {
            videoView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
