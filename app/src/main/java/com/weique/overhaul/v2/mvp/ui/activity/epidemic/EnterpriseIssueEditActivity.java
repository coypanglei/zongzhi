package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerEnterpriseIssueEditComponent;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueEditContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.presenter.EnterpriseIssueEditPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.IssueTypeListPopup;
import com.jess.arms.utils.LoadingDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 问题上报按钮
 * <p>
 * ================================================
 */
@Route(path = RouterHub.APP_ENTERPRISEISSUEEDITACTIVITY)
public class EnterpriseIssueEditActivity extends BaseActivity<EnterpriseIssueEditPresenter> implements EnterpriseIssueEditContract.View {

    @BindView(R.id.select_issue_type)
    TextView selectIssueType;
    @BindView(R.id.select_issue_content)
    EditText selectIssueContent;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    IssueTypeListPopup issueTypeListPopup;


    /**
     * 问题类型  文本list
     */
    private List<String> mStrings;

    private int issueType = -1;
    List<InformationItemPictureBean> list;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnterpriseIssueEditComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enterprise_issue_edit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        list = new ArrayList<>();
        list.add(0, new InformationItemPictureBean(R.drawable.picture));
        informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
        informationAddPhotoAdapter.setMaxItem(8);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(informationAddPhotoAdapter);
        informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                if (view.getId() == R.id.image_) {
                    if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                        if (adapter.getData().size() >= 8) {
                            ArmsUtils.makeText("最多上传8张图片");
                            return;
                        }
                        //9  是加上了默认的加号 图标
                        int max = 9 - adapter.getData().size();
                        mPresenter.getPermission(max);
                    } else {
                        ARouter.getInstance()
                                .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                .navigation();
                    }
                } else if (view.getId() == R.id.remove_image) {
                    adapter.remove(position);
                    adapter.notifyItemChanged(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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

    @OnClick({R.id.select_issue_type, R.id.submit,})
    public void onClickLisenter(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.submit:
                    if (issueType < 0) {
                        ArmsUtils.makeText("请选择提问类型");
                        return;
                    }
                    if (StringUtil.isNullString(selectIssueContent.getText().toString())) {
                        ArmsUtils.makeText("请填写问题描述(尽量详细)");
                        return;
                    }
                    List<String> urls = null;
                    List<InformationItemPictureBean> data = informationAddPhotoAdapter.getData();
                    if (data.size() > 0) {
                        urls = new ArrayList<>();
                        for (InformationItemPictureBean dd : data) {
                            if (dd.getType() == InformationItemPictureBean.IS_URL) {
                                urls.add(dd.getImageUrl());
                            }
                        }
                    }
                    mPresenter.submitInfo(issueType, selectIssueContent.getText().toString(), urls);
                    break;
                case R.id.select_issue_type:
                    if (mStrings != null && mStrings.size() > 0) {
                        issueTypeListPopup.showPopupWindow(selectIssueType);
                    } else {
                        mPresenter.getIssueTypeList();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置问题类型 列表数据
     *
     * @param strings strings
     */
    @Override
    public void setIssueTypeData(List<String> strings) {
        try {
            mStrings = strings;
            if (issueTypeListPopup != null) {
                issueTypeListPopup.setNewData(strings);
                issueTypeListPopup.setListItemClickListener((pos, text) -> {
                    issueType = pos;
                    selectIssueType.setText((text));
                });
                issueTypeListPopup.showPopupWindow(selectIssueType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void goToPhotoAlbum(int max) {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                max, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(EnterpriseIssueEditActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFile("", strings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    /**
     * 更新  图片
     *
     * @param uploadFileRsponseBeans
     */
    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                String path = uploadFileRsponseBean.getUrl();
                list.add(new InformationItemPictureBean((path)));
            }
            informationAddPhotoAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
