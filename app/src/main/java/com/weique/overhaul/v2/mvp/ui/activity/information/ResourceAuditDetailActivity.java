package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerResourceAuditDetailComponent;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceAuditDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.ResourceAuditDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.EditTextDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_RESOURCEAUDITDETAILACTIVITY)
public class ResourceAuditDetailActivity
        extends BaseActivity<ResourceAuditDetailPresenter>
        implements ResourceAuditDetailContract.View {

    @Autowired(name = ARouerConstant.DATA_BEAN)
    ResourceAuditBean.ListBean bean;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.see_h)
    TextView seeH;
    @BindView(R.id.reason)
    TextView reason;
    @BindView(R.id.issue_describe)
    TextView issueDescribe;
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bottom_view)
    LinearLayout bottomView;
    @BindView(R.id.check_layout)
    LinearLayout checkLayout;
    @BindView(R.id.up_tell)
    Button upTell;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;
    private ResourceAuditDetailBean auditDetailBean;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResourceAuditDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resource_audit_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("资源核查详情");
        ARouter.getInstance().inject(this);
        initPhotoAdapter();
        if (bean != null) {
            mPresenter.getDetailById(bean.getId());
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

    @OnClick({R.id.check_layout, R.id.reject, R.id.is_handle, R.id.up_tell})
    public void onClick(View view) {
        if (auditDetailBean == null) {
            return;
        }
        EditTextDialog editTextDialog;
        switch (view.getId()) {
            case R.id.check_layout:
                mPresenter.getDynamicFormByElementId(auditDetailBean.getElementTypeId());
                break;
            case R.id.reject:
                editTextDialog = new EditTextDialog(this, "提示", "请填写拒绝理由", "提交");
                editTextDialog.setOnConfirmClickListener(contents -> {
                    mPresenter.reject(auditDetailBean.getId(), contents);
                });
                editTextDialog.show();
                break;
            case R.id.is_handle:
                mPresenter.handleInfo(auditDetailBean.getId());
                break;
            case R.id.up_tell:
                editTextDialog = new EditTextDialog(this, "提示", "请填写上报理由", "上报");
                editTextDialog.setOnConfirmClickListener(contents -> {
                    mPresenter.upTell(auditDetailBean.getId(), contents);
                });
                editTextDialog.show();
                break;
            default:

        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setDetail(ResourceAuditDetailBean o) {
        try {
            auditDetailBean = o;
            name.setText(StringUtil.setText(o.getName()));
            type.setText(StringUtil.setText(o.getFullPath()));
            issueDescribe.setText(StringUtil.setText(o.getDescription()));
            state.setTextColor(ArmsUtils.getColor(this, o.getStatusColor()));
            state.setText(StringUtil.setText(o.getStatus()));
            if (o.getStatusValue() > 0) {
                checkLayout.setClickable(false);
                seeH.setVisibility(View.GONE);
            }
            if (o.getStatusValue() == 2) {
                reason.setText("拒绝理由：" + o.getReason());
                reason.setVisibility(View.VISIBLE);
            }
            if (o.getStatusValue() == 3) {
                reason.setText("上报理由：" + o.getReason());
                reason.setVisibility(View.VISIBLE);
            }
            List<InformationItemPictureBean> list;
            if (o.getPics() != null && o.getPics().size() > 0) {
                if (informationAddPhotoAdapter != null) {
                    list = new ArrayList<>();
                    for (String pic : o.getPics()) {
                        list.add(new InformationItemPictureBean(pic, InformationItemPictureBean.IS_VIS_DELETE));
                    }
                    informationAddPhotoAdapter.setNewData(list);
                }
            }
            if (o.isIsEnable() && o.getStatusValue() >= 0) {
                bottomView.setVisibility(View.VISIBLE);
                if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() >= StandardAddressStairBean.AREA) {
                    upTell.setVisibility(View.GONE);
                } else {
                    upTell.setVisibility(View.VISIBLE);
                }
            } else {
                bottomView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDynamicForm(String s) {
        if (StringUtil.isNotNullString(s)) {
            ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                    .withString(ARouerConstant.ID, auditDetailBean.getCustId())
                    .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, s)
                    .withString(ARouerConstant.TITLE, StringUtil.setText(auditDetailBean.getName()))
                    .withString(ARouerConstant.DEPARTMENT_ID, UserInfoUtil.getUserInfo().getDepartmentId())
                    .withString(InformationDynamicFormSelectActivity.TYPE_ID, auditDetailBean.getElementTypeId())
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_RESOURCEAUDITDETAILACTIVITY)
                    .navigation();
        } else {
            ArmsUtils.makeText("获取表单数据失败");
        }
    }

    @Override
    public void setHandleInfo(Object s) {
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), source);
        killMyself();
    }

    private void initPhotoAdapter() {
        try {
            informationAddPhotoAdapter = new InformationAddPhotoAdapter(null);
            ArmsUtils.configRecyclerView(rv, new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
            rv.setAdapter(informationAddPhotoAdapter);
            rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.transparent)
                    .sizeResId(R.dimen.dp_10)
                    .build());
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (view.getId() == R.id.image_) {
                        if (bean.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
                            try {
                                String item = ((InformationItemPictureBean) adapter.getItem(position)).getImageUrl();
                                ARouter.getInstance()
                                        .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                        .withString(PictureLookActivity.URL_, item)
                                        .navigation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (eventBusBean != null) {
                if (eventBusBean.getData() instanceof InformationDetailBean) {
                    InformationDetailBean informationDetailBean = (InformationDetailBean) eventBusBean.getData();
                    name.setText(StringUtil.setText(informationDetailBean.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
