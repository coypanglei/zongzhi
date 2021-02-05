package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.io.File;

/**
 * @author GreatKing
 */
public class VersionDialog extends Dialog implements View.OnClickListener {
    private Context mContext;
    private NewVersionInfoBean newVersionInfoBean;

    private TextView versionCode;
    private ProgressBar downloadProgress;
    private TextView content;
    private TextView downloadProgressText;
    private Button gotoUpdate;
    private Button next;
    private Button backgroundRun;
    private RelativeLayout layoutBtn;
    private RelativeLayout layoutProgress;
    private OnVersionDialogClickLisenter clickLisenter;
    private boolean downloadOk = false;

    public void setUpdateProgress(int progress) {
        try {
            if (layoutProgress.getVisibility() == View.GONE) {
                layoutProgress.setVisibility(View.VISIBLE);
                layoutBtn.setVisibility(View.GONE);
                if (newVersionInfoBean.isIsForceUpdate()) {
                    backgroundRun.setText(ArmsUtils.getString(mContext, R.string.loading));
                } else {
                    backgroundRun.setText(ArmsUtils.getString(mContext, R.string.background_run));
                }
            }
            downloadProgress.setProgress(progress);
            downloadProgressText.setText(progress + "%");
            if (progress == 100) {
                downloadOk = true;
                backgroundRun.setText(ArmsUtils.getString(mContext, R.string.install));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnVersionDialogClickLisenter {
        void onGotoUpdateClick();
    }

    public void setOnVersionDialogClickLisenter(OnVersionDialogClickLisenter clickLisenter) {
        this.clickLisenter = clickLisenter;
    }

    public VersionDialog(Context context, NewVersionInfoBean newVersionInfoBean) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        this.newVersionInfoBean = newVersionInfoBean;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
            View view = LayoutInflater.from(mContext).inflate(R.layout.version_dialog, null);
            setContentView(view);
            initView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView(View view) throws Exception {
        layoutBtn = view.findViewById(R.id.layout_btn);
        layoutProgress = view.findViewById(R.id.layout_progress);
        downloadProgress = view.findViewById(R.id.download_progress);
        downloadProgressText = view.findViewById(R.id.download_progress_text);
        backgroundRun = view.findViewById(R.id.background_run);
        backgroundRun.setOnClickListener(this);
        versionCode = view.findViewById(R.id.version_code);
        versionCode.setText("V" + StringUtil.setText(newVersionInfoBean.getVersionName()));
        content = view.findViewById(R.id.content);
        content.setText(StringUtil.setText(newVersionInfoBean.getMemo()));
        gotoUpdate = view.findViewById(R.id.goto_update);
        gotoUpdate.setOnClickListener(this);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        if (newVersionInfoBean.isIsForceUpdate()) {
            next.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
        layoutProgress.setVisibility(View.GONE);
        layoutBtn.setVisibility(View.VISIBLE);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.goto_update:
                    clickLisenter.onGotoUpdateClick();
                    break;
                case R.id.next:
                    dismiss();
                    break;
                case R.id.background_run:

                    if (downloadOk) {
                        File file = Constant.getNewVersionApkFile(newVersionInfoBean.getVersionName());
                        if (file.exists()) {
                            AppUtils.installApk(mContext, file);
                        } else {
                            ArmsUtils.makeText("安装包不存在");
                        }
                        if(!newVersionInfoBean.isIsForceUpdate()){
                            dismiss();
                        }
                    } else if (newVersionInfoBean.isIsForceUpdate()) {
                        break;
                    } else {
                        dismiss();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
