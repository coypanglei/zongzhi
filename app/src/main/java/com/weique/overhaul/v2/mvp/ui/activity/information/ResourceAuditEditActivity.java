package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerResourceAuditEditComponent;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditEditContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.presenter.ResourceAuditEditPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Route(path = RouterHub.APP_RESOURCEAUDITEDITACTIVITY)
public class ResourceAuditEditActivity extends BaseActivity<ResourceAuditEditPresenter> implements ResourceAuditEditContract.View {

    @BindView(R.id.issue_describe)
    EditText describe;
    @BindView(R.id.number_text)
    TextView numberText;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * 照片模块
     */
    List<InformationItemPictureBean> list;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResourceAuditEditComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resource_audit_edit;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("资源核查上报");
        ARouter.getInstance().inject(this);
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        initPhotoaAdapter();
        describe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numberText.setText(s.length() + "/100");
            }
        });
    }

    private void initPhotoaAdapter() {
        list = new ArrayList<>();
        list.add(0, new InformationItemPictureBean(R.drawable.picture));
        informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
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
                    if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                        int maxPhoto = 5;
                        //加1 是因为有个默认的 添加图片
                        if (adapter.getData().size() >= maxPhoto + 1) {
                            ArmsUtils.makeText("最多上传" + maxPhoto + "张图片");
                            return;
                        }
                        //9  是加上了默认的加号 图标
                        int max = maxPhoto + 1 - adapter.getData().size();
                        mPresenter.getPermissionAndUpLoad(max);
                    } else if (bean.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
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

    @OnClick(R.id.bottom_view)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_view:
                Map<String, Object> map = new HashMap<>();
                map.put("CustId", id);
                if (StringUtil.isNullString(describe.getText().toString())) {
                    ArmsUtils.makeText(describe.getHint().toString());
                    return;
                }
                map.put("Description", describe.getText().toString());
                if (informationAddPhotoAdapter != null) {
                    List<InformationItemPictureBean> data = informationAddPhotoAdapter.getData();
                    if (data.size() > 0) {
                        String picPathsJson = informationAddPhotoAdapter.getPicPathsJson();
                        map.put("MultPics", picPathsJson);
                    }
                }
                mPresenter.resourceAudit(map);
                break;
            default:
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void updatePictureResponse(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
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
