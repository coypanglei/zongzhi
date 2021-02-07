package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.weique.overhaul.v2.di.component.DaggerEnterpriseIssueSeeComponent;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueSeeContract;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;
import com.weique.overhaul.v2.mvp.presenter.EnterpriseIssueSeePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.jess.arms.utils.LoadingDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:  企业上报问题 查看详情
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ENTERPRISEISSUESEEACTIVITY)
public class EnterpriseIssueSeeActivity extends BaseActivity<EnterpriseIssueSeePresenter> implements EnterpriseIssueSeeContract.View {

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
    @BindView(R.id.replier_photo)
    ImageView replierPhoto;
    @BindView(R.id.replier_name)
    TextView replierName;
    @BindView(R.id.reply_time)
    TextView replyTime;
    @BindView(R.id.reply_content)
    TextView replyContent;

    @Inject
    Gson gson;

    @Autowired(name = ARouerConstant.ID)
    String id;
    private SimplePhotoAdapter simplePhotoAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnterpriseIssueSeeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enterprise_issue_see;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            mPresenter.getIssueDetail(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * 设置问题类型 列表数据
     *
     * @param bean objects
     */
    @Override
    public void setIssueData(EnterpriseIssueDetailBean bean) {
        try {
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
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        try {
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
            //回复人信息
            replierName.setText((bean.getLeaderName()));
            //大于1是一回复  暂时写死
            if (bean.getEnumEntTroubleOrderSaveType() > 1) {
                replyTime.setText((bean.getUpdateTimeStr()));
                mPresenter.getIssueReplyDetail(id);
            } else {
                replyContent.setText("您的意见反馈我们已收到，正在处理中。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置问题回复详情
     *
     * @param o objects
     */
    @Override
    public void setIssueReplyData(String o) {
        replyContent.setText((o));
    }

}
