package com.weique.overhaul.v2.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerUpLoadListComponent;
import com.weique.overhaul.v2.mvp.contract.UpLoadListContract;
import com.weique.overhaul.v2.mvp.presenter.UpLoadListPresenter;
import com.weique.overhaul.v2.app.customview.RoundnessProgressBar;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author  GK
 */
@Route(path = RouterHub.APP_UPLOADLISTACTIVITY)
public class UpLoadListActivity extends BaseActivity<UpLoadListPresenter> implements UpLoadListContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.id_progress02)
    RoundnessProgressBar idProgress02;

    private RoundnessProgressBar roundnessProgressBar;

    private static final int MSG_PROGRESS_UPDATE = 0x110;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUpLoadListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            try {
                int roundProgress = roundnessProgressBar.getProgress();

                roundnessProgressBar.setProgress(roundProgress);
                roundnessProgressBar.setProgress(++roundProgress);
                if (roundProgress >= 100) {
                    mHandler.removeMessages(MSG_PROGRESS_UPDATE);
                }
                mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    };

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_up_load_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("上传列表");
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
