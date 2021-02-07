package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
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
import com.weique.overhaul.v2.di.component.DaggerBusinessInterviewDetailComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessInterviewDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.InterviewDetailBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessInterviewDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.jess.arms.utils.LoadingDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_BUSINESSINTERVIEWDETAILACTIVITY)
public class BusinessInterviewDetailActivity extends BaseActivity<BusinessInterviewDetailPresenter> implements BusinessInterviewDetailContract.View {
    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.issue_title)
    TextView issueTitle;
    @BindView(R.id.issue_time)
    TextView issueTime;
    @BindView(R.id.issue_content)
    TextView issueContent;
    @BindView(R.id.issue_feedback)
    TextView issueFeedback;
    @BindView(R.id.issue_photo_list)
    RecyclerView issuePhotoList;

    @Inject
    Gson gson;
    private SimplePhotoAdapter simplePhotoAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessInterviewDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_interview_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("问题回复");
        ARouter.getInstance().inject(this);
        mPresenter.getInterviewDetail(id);
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

    @Override
    public void setInterviewDetail(InterviewDetailBean o) {
        try {
            issueTitle.setText(StringUtil.setText(o.getEnterpriseInfName()));
            issueTime.setText(StringUtil.setText(o.getCreateTimeStr()));
            issueContent.setText(StringUtil.setText(o.getTroubleOrProblem()));
            issueFeedback.setText(StringUtil.setText(o.getSuggestion()));
            if (StringUtil.isNotNullString(o.getPhotos())) {
                List<String> strings = gson.fromJson(o.getPhotos(), List.class);
                if (simplePhotoAdapter == null) {
                    simplePhotoAdapter = new SimplePhotoAdapter(strings);
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
                    simplePhotoAdapter.setNewData(strings);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
