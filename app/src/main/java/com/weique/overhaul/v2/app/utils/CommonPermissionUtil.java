package com.weique.overhaul.v2.app.utils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.List;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * 权限申请  尽量写到这里来
 *
 * @author GreatKing
 */
public class CommonPermissionUtil {

    public interface PermissionLisenter {
        void getPermissionSuccess();
    }

    /**
     * 获取权限
     *
     * @param activity      context
     * @param mErrorHandler mErrorHandler
     * @param lisenter      lisenter
     * @param permissions   权限集合
     */
    public static void getPermission(Activity activity, RxErrorHandler mErrorHandler, PermissionLisenter lisenter, String... permissions) {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {

            @Override
            public void onRequestPermissionSuccess() {
                lisenter.getPermissionSuccess();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                new CommonDialog.Builder(activity).setTitle("注意")
                        .setContent("您拒绝了部分授权,部分功能无法正常使用")
                        .setPositiveButton("知道了", (v, commonDialog) -> {
                        })
                        .setNegativeBtnShow(false).create().show();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                try {
                    new CommonDialog.Builder(activity).setTitle("注意")
                            .setContent("您拒绝了部分授权,并选择不在询问,部分功能无法正常使用")
                            .setPositiveButton("去设置", (v, commonDialog) -> {
                                String brand = Build.BRAND;//手机厂商
                                if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                                    PhoneInfoUtil.gotoMiuiPermission(activity);
                                } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                                    PhoneInfoUtil.gotoMeizuPermission(activity);
                                } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                                    PhoneInfoUtil.gotoHuaweiPermission(activity);
                                } else {
                                    ArmsUtils.startActivity(PhoneInfoUtil.getAppDetailSettingIntent(activity));
                                }
                            }).setNegativeButton("知道了", (v, commonDialog) -> {
                    }).create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new RxPermissions((FragmentActivity) activity), mErrorHandler, permissions);
    }

    /**
     * 获取权限
     *
     * @param activity      context
     * @param mErrorHandler mErrorHandler
     * @param lisenter      lisenter
     * @param isBreak       是否因为 拒绝权限 终端 逻辑
     * @param permissions   权限集合
     */
    public static void getPermission(Activity activity, RxErrorHandler mErrorHandler, PermissionLisenter lisenter, boolean isBreak, String... permissions) {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                lisenter.getPermissionSuccess();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                new CommonDialog.Builder(activity).setTitle("注意")
                        .setContent("您拒绝了部分授权,部分功能无法正常使用")
                        .setPositiveButton("知道了", (v, commonDialog) -> {
                            if (!isBreak) {
                                lisenter.getPermissionSuccess();
                            }
                        })
                        .setNegativeBtnShow(false).create().show();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                try {
                    new CommonDialog.Builder(activity).setTitle("注意")
                            .setContent("您拒绝了部分授权,并选择不在询问,部分功能无法正常使用")
                            .setPositiveButton("去设置", (v, commonDialog) -> {
                                String brand = Build.BRAND;//手机厂商
                                if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
                                    PhoneInfoUtil.gotoMiuiPermission(activity);
                                } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                                    PhoneInfoUtil.gotoMeizuPermission(activity);
                                } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                                    PhoneInfoUtil.gotoHuaweiPermission(activity);
                                } else {
                                    ArmsUtils.startActivity(PhoneInfoUtil.getAppDetailSettingIntent(activity));
                                }
                            }).setNegativeButton("知道了", (v, commonDialog) -> {
                    }).create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new RxPermissions((FragmentActivity) activity), mErrorHandler, permissions);
    }

}
