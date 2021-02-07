package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerBusinessQuestionDetailComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessQuestionDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.jess.arms.utils.LoadingDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 企业问题详情
 * <p>
 * ================================================
 */
@Route(path = RouterHub.APP_BUSINESSQUESTIONDETAILACTIVITY)
public class BusinessQuestionDetailActivity extends BaseActivity<BusinessQuestionDetailPresenter> implements BusinessQuestionDetailContract.View {

    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.issue_type)
    TextView issueType;
    @BindView(R.id.issue_time)
    TextView issueTime;
    @BindView(R.id.issue_title)
    TextView issueTitle;
    @BindView(R.id.issue_content)
    TextView issueContent;
    @BindView(R.id.issue_photo_list)
    RecyclerView issuePhotoList;
    @BindView(R.id.replier_name)
    TextView replierName;
    @BindView(R.id.feedback)
    EditText feedback;
    private SimplePhotoAdapter simplePhotoAdapter;

    @Inject
    Gson gson;
    /**
     * 问题id
     */
    private String issueId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessQuestionDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_question_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mPresenter.getQuestionDetail(id);
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

    @OnClick(R.id.submit)
    public void onClickLisenter(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (StringUtil.isNullString(issueId)) {
                ArmsUtils.makeText("回复问题错误");
                return;
            }
            if (StringUtil.isNullString(feedback.getText().toString())) {
                ArmsUtils.makeText("回复内容不能为空");
                return;
            }
            mPresenter.submitFeedback(issueId, feedback.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setIssueData(EnterpriseIssueDetailBean bean) {
        try {
            issueId = bean.getId();
            issueType.setText((bean.getEnumTroubleTypeStr()));
            issueTime.setText((bean.getCreateTimeStr()));
            issueTitle.setText((bean.getEnterpriseInfName()));
            issueContent.setText((bean.getDesc()));
            if (StringUtil.isNotNullString(bean.getPhoto())) {
                String photo = bean.getPhoto();
                List<String> tbList = gson.fromJson(photo, List.class);
                if (simplePhotoAdapter == null) {
                    simplePhotoAdapter = new SimplePhotoAdapter(tbList);
                    ArmsUtils.configRecyclerView(issuePhotoList, new GridLayoutManager(this, 5));
                    issuePhotoList.setAdapter(simplePhotoAdapter);
                    simplePhotoAdapter.setOnItemClickListener((adapter, view, position) -> {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            String item = (String) adapter.getItem(position);
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, item)
                                    .navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    simplePhotoAdapter.setNewData(tbList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
