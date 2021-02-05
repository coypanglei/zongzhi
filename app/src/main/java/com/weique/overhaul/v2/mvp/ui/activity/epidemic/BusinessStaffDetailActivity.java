package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
import com.weique.overhaul.v2.di.component.DaggerBusinessStaffDetailComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessStaffDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.StaffDetailBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessStaffDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.jess.arms.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Route(path = RouterHub.APP_BUSINESSSTAFFDETAILACTIVITY)
public class BusinessStaffDetailActivity extends BaseActivity<BusinessStaffDetailPresenter> implements BusinessStaffDetailContract.View {

    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.id_code)
    TextView idCode;
    @BindView(R.id.real_address)
    TextView realAddress;
    @BindView(R.id.new_address)
    TextView newAddress;
    @BindView(R.id.leave)
    TextView leave;
    @BindView(R.id.home_type)
    TextView homeType;
    @BindView(R.id.reply_time)
    TextView replyTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private SimplePhotoAdapter simplePhotoAdapter;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessStaffDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_staff_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("员工详情");
            ARouter.getInstance().inject(this);
            mPresenter.getStaffData((id));
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

    @Override
    public void setStaffData(StaffDetailBean o) {
        try {
            name.setText(StringUtil.setText(o.getName()));
            gender.setText(StringUtil.setText(o.getEnumGenderValue()));
            age.setText(StringUtil.setText(String.valueOf(o.getAge())));
            phoneNumber.setText(StringUtil.setText(o.getTel()));
            idCode.setText(StringUtil.setText(o.getSID()));
            realAddress.setText(StringUtil.setText(o.getDomicile()));
            newAddress.setText(StringUtil.setText(o.getCurrentAddress()));
            leave.setText(StringUtil.setText(o.getEnumIsOutXuzhou()));
            homeType.setText(StringUtil.setText(o.getEnumPropertyType()));
            replyTime.setText(StringUtil.setText(o.getReturnToWorkTime()));
            List<String> strings = new ArrayList<>();
            if (StringUtil.isNotNullString(o.getPhotoUrl())) {
                strings.add(o.getPhotoUrl());
            }
            if (StringUtil.isNotNullString(o.getPersonalTrajectorySrc())) {
                strings.add(o.getPersonalTrajectorySrc());
            }
            if (StringUtil.isNotNullString(o.getContactPersonSrc())) {
                strings.add(o.getContactPersonSrc());
            }
            if (strings.size() > 0) {
                if (simplePhotoAdapter == null) {
                    simplePhotoAdapter = new SimplePhotoAdapter(strings);
                    ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 5));
                    recyclerView.setAdapter(simplePhotoAdapter);
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
