package com.weique.overhaul.v2.mvp.presenter;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.MetaDataUtil;
import com.weique.overhaul.v2.mvp.contract.SettingsContract;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.progressmanager.body.ProgressInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class SettingsPresenter extends ReworkBasePresenter<SettingsContract.Model, SettingsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    private Handler mHandler;
    private ProgressInfo mLastDownloadingInfo;

    @Inject
    public SettingsPresenter(SettingsContract.Model model, SettingsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 获取App版本信息
     */
    public void getAppVersionInfo() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("APPId", "");
            map.put("Channel", MetaDataUtil.getChannelName());
            commonGetData(mModel.getAppVersionInfo(map), mErrorHandler, newVersionInfoBean -> {
                try {
                    if (newVersionInfoBean.getVersionNumber() > AppUtils.getVersionCode(mRootView.getActivity())) {
                        //处理url  添加 域名  前缀
                        newVersionInfoBean.setApkUrl(GlideUtil.handleUrl(mRootView.getActivity(), newVersionInfoBean.getApkUrl()));
                        mHandler = new Handler();
                        getPermission(newVersionInfoBean);
                    } else {
                        ArmsUtils.makeText("已经是最新版本");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取二维码信息
     */
    public void getAppVersionInfoCode() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("APPId", "");
            map.put("Channel", MetaDataUtil.getChannelName());
            commonGetData(mModel.getAppVersionInfo(map), mErrorHandler, newVersionInfoBean -> {
                try {
                    String url = GlideUtil.handleUrl(mRootView.getActivity(), newVersionInfoBean.getApkUrl());
                    Timber.e(url);
                    mRootView.setCodeImg(createQRcodeImage(url),newVersionInfoBean.getVersionName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建二维码
     * @param url 网址
     * @return 图片
     */
    private Bitmap createQRcodeImage(String url) {

        int w = 250;
        int h = 250;
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                ArmsUtils.makeText("网址不合法");
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    } else {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void getPermission(NewVersionInfoBean newVersionInfoBean) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = mRootView.getActivity().getPackageManager().canRequestPackageInstalls();
                if (!hasInstallPermission) {
                    getInstallPermission();
                    return;
                }
            }
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                mRootView.showVersionDialog(newVersionInfoBean);
            }, Constant.READ_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getInstallPermission() {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                startInstallPermissionSettingActivity();
            }
        }, new RxPermissions((FragmentActivity) mRootView.getActivity()), mErrorHandler, Manifest.permission.REQUEST_INSTALL_PACKAGES);
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + mRootView.getActivity().getPackageName()));
        mRootView.getActivity().startActivityForResult(intent, 1);
    }
}
