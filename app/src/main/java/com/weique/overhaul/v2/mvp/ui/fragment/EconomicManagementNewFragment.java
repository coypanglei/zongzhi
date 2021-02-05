package com.weique.overhaul.v2.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerEconomicManagementNewComponent;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementNewContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EconomicManagementNewPresenter;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2020 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EconomicManagementNewFragment extends BaseFragment<EconomicManagementNewPresenter> implements EconomicManagementNewContract.View {

    @BindView(R.id.ry_content)
    RecyclerView ryContent;

    @BindColor(R.color.color_656565)
    int color65656;

    @Inject
    Gson gson;
    @BindView(R.id.ll_package_leader)
    LinearLayout llPackageLeader;
    @BindView(R.id.package_leader_name)
    EditText editName;
    @BindView(R.id.add)
    TextView add;

    private int type = 0;

    private String id = "";

    private CommonCollectBean commonCollectBean;

    List<BasicInformationBean.RecordsBean> list;

    private String path;

    public static EconomicManagementNewFragment newInstance() {
        EconomicManagementNewFragment fragment = new EconomicManagementNewFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEconomicManagementNewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_economic_management_new, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void setData(@Nullable Object data) {

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

    }

    /**
     * 更新  tab 数据
     */
    @Subscriber(tag = RouterHub.APP_COMMON_COLLECTION)
    private void onEventCallBack(EventBusBean eventBusBean) {
        Timber.e("%s", eventBusBean.getData());
        if (commonCollectBean.getPath().equals(eventBusBean.getData())) {
            assert mPresenter != null;
            mPresenter.getTabledata(id, type);
        }
    }

    @OnClick({R.id.tv_submit, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                String name = editName.getText().toString();
                assert mPresenter != null;
                mPresenter.createCharger(id, name);
                break;
            case R.id.add:
                commonCollectBean.setList(list);
                if (commonCollectBean.getList().size() > 0) {
                    ARouter.getInstance().build(RouterHub.APP_COMMON_COLLECTION).
                            withParcelable(ARouerConstant.COMMON_COLLECTION, commonCollectBean).
                            navigation();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setTable(EntInfoItemListBean bean) {

    }

    @Override
    public void setProjectTable(List<EntInfoItemBean> bean) {

    }
}
