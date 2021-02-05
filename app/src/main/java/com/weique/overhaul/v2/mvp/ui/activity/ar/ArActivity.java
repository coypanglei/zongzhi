package com.weique.overhaul.v2.mvp.ui.activity.ar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerArComponent;
import com.weique.overhaul.v2.mvp.contract.ArContract;
import com.weique.overhaul.v2.mvp.presenter.ArPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import map.baidu.ar.ArPageListener;
import map.baidu.ar.camera.SimpleSensor;
import map.baidu.ar.camera.find.FindArCamGLView;
import map.baidu.ar.model.ArLatLng;
import map.baidu.ar.model.ArPoiInfo;
import map.baidu.ar.model.PoiInfoImpl;
import map.baidu.ar.utils.TypeUtils;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_ARACTIVITY)
public class ArActivity extends BaseActivity<ArPresenter> implements ArContract.View, ArPageListener {
    /**
     * 前一个界面
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    /**
     * 状态码 是事件上报还是  信息采集
     */
    @Autowired(name = ARouerConstant.STATUS)
    @EnumConstant.DynamicBeanByType
    int behaviour;

    @BindView(R.id.cam_rl)
    RelativeLayout camRl;
    @BindView(R.id.ar_poi_item_rl)
    RelativeLayout mArPoiItemRl;
    /* @BindView(R.id.screen_btn)
     ImageView screenBtn;*/
    private FindArCamGLView mCamGLView;
    private SimpleSensor mSensor;
    private ArLatLng[] latLngs = {new ArLatLng(40.082545, 116.376188), new ArLatLng(40.04326, 116.376781),
            new ArLatLng(40.043204, 116.300784), new ArLatLng(39.892352, 116.433015),
            new ArLatLng(39.970696, 116.267439), new ArLatLng(40.040553, 116.315732),
            new ArLatLng(40.032156, 116.316307), new ArLatLng(40.012707, 116.265714),
            new ArLatLng(40.010497, 116.335279), new ArLatLng(40.124643, 116.701359),
            new ArLatLng(40.042321, 116.15648), new ArLatLng(41.092678, 116.343903),
            new ArLatLng(40.083846, 116.234669), new ArLatLng(40.094444, 116.29216)};
    //    @Inject
//    Gson gson;
//    private ArrayList<PoiInfoImpl> poiInfos;
    public static ArrayList<PoiInfoImpl> poiInfos;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerArComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("AR视角");
            ARouter.getInstance().inject(this);
        } catch (Exception e) {
            Timber.e("ssssss" + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getPermissionCamera();
    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        if (mSensor == null) {
            mSensor = new SimpleSensor(this, new HoldPositionListenerImp());
        }
        mSensor.startSensor();
    }

    @Override
    public void noPoiInScreen(boolean b) {

    }

    @Override
    public void selectItem(Object iMapPoiItem) {
        if (iMapPoiItem instanceof PoiInfoImpl) {
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void permissionSuccess() {
        try {
            poiInfos = new ArrayList<>();
            int i = 0;
            for (ArLatLng all : latLngs) {
                ArPoiInfo pTest = new ArPoiInfo();
                pTest.name = "testPoint" + i++;
                pTest.location = all;
                PoiInfoImpl poiImplT = new PoiInfoImpl();
                poiImplT.setShowInAr(true);
                poiImplT.setShowInScreen(true);
                poiImplT.setPoiInfo(pTest);
                poiInfos.add(poiImplT);
            }

            camRl = (RelativeLayout) findViewById(R.id.cam_rl);
            mCamGLView = (FindArCamGLView) LayoutInflater.from(this).inflate(R.layout.layout_find_cam_view, null);
            mCamGLView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                           int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (bottom == 0 || oldBottom != 0 || mCamGLView == null) {
                        return;
                    }
                    RelativeLayout.LayoutParams params = TypeUtils.safeCast(
                            mCamGLView.getLayoutParams(), RelativeLayout.LayoutParams.class);
                    if (params == null) {
                        return;
                    }
                    params.height = bottom - top;
                    mCamGLView.requestLayout();
                }
            });
            camRl.addView(mCamGLView);
            initSensor();
            // 保持屏幕不锁屏
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            Timber.e("ssssss" + e.getMessage());
        }
    }


    private class HoldPositionListenerImp implements SimpleSensor.OnHoldPositionListener {

        @Override
        public void onOrientationWithRemap(float[] remapValue) {
            try {
                if (mCamGLView != null && mArPoiItemRl != null) {
                    if (poiInfos.size() <= 0) {
                        mArPoiItemRl.setVisibility(View.GONE);
                        ArmsUtils.makeText("附近没有可识别的类别");
                    } else {
                        mCamGLView.setFindArSensorState(remapValue, getLayoutInflater(),
                                mArPoiItemRl, ArActivity.this, poiInfos, ArActivity.this);
                        mArPoiItemRl.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                Timber.e("sssss飒飒s" + e.getMessage());
            }
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
        ArmsUtils.snackbarText(message);
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
    public void onDestroy() {
        super.onDestroy();
        finishCamInternal();
        if (mCamGLView != null) {
            if (mCamGLView.getmDialog() != null) {
                mCamGLView.getmDialog().dismiss();
            }
        }
    }

    private void finishCamInternal() {
        try {
            if (mCamGLView != null) {
                mCamGLView.stopCam();
                mCamGLView = null;
            }
            if (camRl != null && camRl.getChildCount() > 0) {
                camRl.removeAllViews();
            }
            if (mArPoiItemRl != null) {
                mArPoiItemRl.removeAllViews();
            }
            if (mSensor != null) {
                mSensor.stopSensor();
            }
            // 恢复屏幕自动锁屏
            ArActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
