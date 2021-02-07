package com.weique.overhaul.v2.app.common;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import androidx.annotation.IntDef;

import com.weique.overhaul.v2.app.utils.MetaDataUtil;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 通用常量类
 *
 * @author GreatKing
 */
public class Constant {
    /**
     * 闪屏页同意请求权限 在 具体需求界面 再重新申请 防止用户拒绝产生问题
     */

    public static final String[] PERMISSIONS_LIST_SPLASH_ = {
//            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
    };
    /**
     * 读写 相机 录音
     */
    public static final String[] PERMISSIONS_LIST_READ_WRITE_CAMERA = {
//            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAPTURE_VIDEO_OUTPUT,
            Manifest.permission.CAMERA,
    };

    /**
     * 读写 相机 录音
     */
    public static final String[] LOCATION_CAMERA_LOCATION = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
    };
    /**
     * 下载所需权限
     */
    public static final String[] READ_WRITE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 下载所需权限
     */
    public static final String[] READ_WRITE_CAMERA = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    /**
     * 写  和  录制
     */
    public static final String[] READ_WRITE_RECORD = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    /**
     * 下载所需权限
     */
    public static final String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    /**
     * BUG监控appid
     */
    public static final String BUGLY_APP_ID_RELASE = "414b35cf32";
    public static final String BUGLY_APP_ID_DEBUG = "cdaa606b6f";

    /**
     * 百度扫描
     */
    public static final String BAIDU_SCAN_AK = "ZmFxer4KQKnUSzne6xwPi5wD";
    public static final String BAIDU_SCAN_SK = "iSNiMyArvP8PSWeOvXnpPwl5Nl6lNGlY";


    /**
     * 公共参数
     */
    public static final String TOKEN = "token";
    public static final String VERSION = "VERSION";
    public static final String USER_ID = "UserId";
    public static final String TIMESTAMP = "timestamp";
    public static final String NONCE = "nonce";
    public static final String SIGNATURE = "signature";
    public static final String DEPARTMENT_ID = "DepartmentId";

    /**
     * 文件后缀
     */
    public static final String MP3 = "mp3";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String MP4 = "mp4";
    public static final String _MP4 = ".mp4";
    public static final String RMVB = "rmvb";
    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String GIF = "gif";
    public static final String JPEG = "jpeg";
    public static final String BMP = "bmp";
    public static final String APK = ".apk";
    public static final String HTTP_HEAD = "<head>" +
            "<meta name=viewport content=width=device-width, initial-scale=1.0, user-scalable=no> " +
            "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
            "<style>video{max-width: 100%;max-height: 35%;}</style>" +
            "<style>p{letter-spacing: 3px !important;line-height:28px " +
            "!important;font-family:Source Han Serif SC;color:rgba(51,51,51,1);font-size:18}</style>" +
            "</head>";


    public static final String YM = "yyyy/MM";
    public static final String YM1 = "yyyy-MM";
    public static final String YMD = "yyyy/MM/dd";
    public static final String YMD_C = "yyyy年MM月dd日";
    public static final String YMD1 = "yyyy-MM-dd";
    public static final String YMDH = "yyyy/MM/dd HH";
    public static final String YMDH1 = "yyyy-MM-dd HH";
    public static final String YMDHM = "yyyy/MM/dd HH:mm";
    public static final String YMDHM1 = "yyyy-MM-dd HH:mm";
    public static final String YMDHMS = "yyyy/MM/dd HH:mm:ss";
    public static final String YMDHMS1 = "yyyy-MM-dd HH:mm:ss";


    public static final String Y = "yyyy";

    public static final String M = "MM";
    /**
     * 身份证位数
     */
    public static final int ID_NUMBER = 18;
    /**
     * 扫描响应码
     */
    public static final int REQUEST_CODE_SCAN = 100;

    /**
     * 静态对象信息
     */
    public static GlobalUserInfoBean globalUserInfoBean;


    public static final int GPS_STATE = 100;

    /**
     * 最新版本文件
     *
     * @param versionName versionName
     * @return File
     */
    public static File getNewVersionApkFile(String versionName) {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "weique");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, MetaDataUtil.getAppName() + "-" + versionName + Constant.APK);
    }

    /**
     * 最新版本文件
     *
     * @param fileName fileName
     * @return File
     */
    public static File getDownloadFile(String fileName) {
        return new File(getDir(), fileName);
    }

    /**
     * 文件目录
     *
     * @return String
     */
    public static String getDir() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "weique");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    /**
     * 隐私政策
     */
    public static final String PRIVACY_POLICY = "/APP/ProtocolForApp/PrivacyProtocolIndex";
    /**
     * 用户协议
     */
    public static final String USER_AGREEMENT = "/APP/ProtocolForApp/UserAgreementIndex";


    /**
     * 网络是否可用 requestCode
     */
    public static final int NETWORK_SETTING = 0;

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    public static String getTempFilePath(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName + _MP4);
        return file.getAbsolutePath();
    }


    /**
     * 百度地图
     */
    public final static String CoorType_GCJ02 = "gcj02";
    public final static String CoorType_BD09LL = "bd09ll";
    public final static String CoorType_BD09MC = "bd09";


    /**
     *   适配器
     */
    /**
     * 选择框
     */
    public final static String SELECT_ITEM = "select_item";

    /**
     * 视频会商 ip
     */
//    public final static String CHAT_IP = "wss://121.40.168.83:4443/wss";
        public final static String CHAT_IP = "wss://157.0.243.115:4443/wss";
    /**
     * 视频会商 ip
     */
//    public final static String CHAT_HOST = "121.40.168.83";
    public final static String CHAT_HOST = "157.0.243.115";

    /**
     * 编辑框
     */
    public final static String EDIT_ITEM = "edit_item";


    /**
     * 数字编辑框
     */
    public final static String NUMBER_EDIT_ITEM = "number_edit_item";


    /**
     * 时间选择框
     */
    public final static String DATA_ITEM = "data_item";


    /**
     * 图片选择框
     */
    public final static String IMG_ITEM = "img_edit_item";


    /**
     * 地址选择框
     */
    public final static String ADDRESS_ITEM = "address__item";


    /**
     * 图片选择框
     */
    public final static String MORE_IMG_ITEM = "more_img_item";


    /**
     * 视频选择框
     */
    public final static String MORE_URL_ITEM = "more_video_item";

    /**
     * 大的填写框
     * 布局{@link com.weique.overhaul.v2 / item_edit_large}
     * 对象 {@link com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean.RecordsBean}
     */
    public final static String LARGE_EDIT_ITEM = "large_edit_item";

    /**
     * 事项 流程 对象 样式
     * <p>
     * 布局{@link com.weique.overhaul.v2 / item_matter_flow.xml}
     * 对象 {@link com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean}
     */
    public final static String MATTER_FLOW = "matter_flow";


    public static final int GET_ACCESSORY_CODE = 8888;


    /**
     * 成功响应码 200 时  message  不给用户展示
     */
    public static final int HTTP200 = 200;
    /**
     * 成功响应码 201 时  message  给用户展示
     */
    public static final int HTTP201 = 201;
    /**
     * 重定向相关
     */
    public static final int HTTP300 = 300;
    /**
     * 客户端错误永固   不给用户展示
     */
    public static final int HTTP400 = 400;
    /**
     * 给用户展示
     */
    public static final int HTTP401 = 401;
    public static final int HTTP404 = 404;
    /**
     * token 验证失效 需要重新登录
     */
    public static final int HTTP403 = 403;
    /**
     * 签名验证失败
     */
    public static final int HTTP405 = 405;
    /**
     * 链接 已超时 失效
     */
    public static final int HTTP406 = 406;
    /**
     * 初登录外  其它接口 服务端没有token时  生成新的token  返回到 407
     */
    public static final int HTTP407 = 407;
    /**
     * 服务器错误
     */
    public static final int HTTP500 = 500;


    /**
     * commonCollectionBean key
     */

    public static final String COMMON_COLLECTION_KEY = "commonCollection_key";


    /**
     * 通用采集object
     */
    public static final String COMMON_COLLECTION = "common_collection";

    /**
     * 編輯 選項
     */
    @IntDef({CommonCollectionEnum.COMMON_COLLECTION_DETAIL,
            CommonCollectionEnum.COMMON_COLLECTION_ADD,
            CommonCollectionEnum.COMMON_COLLECTION_EDIT
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CommonCollectionEnum {
        /**
         * 详情
         */
        int COMMON_COLLECTION_DETAIL = 0;
        /**
         * 新增
         */
        int COMMON_COLLECTION_ADD = 1;
        /**
         * 编辑
         */
        int COMMON_COLLECTION_EDIT = 2;

    }
}
