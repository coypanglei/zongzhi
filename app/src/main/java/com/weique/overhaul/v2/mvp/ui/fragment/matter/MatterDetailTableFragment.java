package com.weique.overhaul.v2.mvp.ui.fragment.matter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerMatterDetailTableComponent;
import com.weique.overhaul.v2.mvp.contract.MatterDetailTableContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean;
import com.weique.overhaul.v2.mvp.presenter.MatterDetailTablePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommentCurrencyAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MATTER_FLOW;
import static com.weique.overhaul.v2.app.common.Constant.MORE_IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_URL_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/01/2020 13:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MatterDetailTableFragment extends BaseLazyLoadFragment<MatterDetailTablePresenter>
        implements MatterDetailTableContract.View {

    private static final int NULL_NUM = -2;
    private static final String ID = "ID";
    private static final String ID2 = "ID2";
    private static final String FLAG = "FLAG";
    @BindView(R.id.rv)
    RecyclerView rv;
    private String id;
    private String eventRecordId;
    private int flag = NULL_NUM;

    private static final int BASE_INFO = 0;
    private static final int PROCESS_INFO = 1;
    private static final int CUI_BAN_INFO = 2;
    private static final int DU_BAN_INFO = 3;
    /**
     * INFOS
     */
    public static final int[] INFOS = {BASE_INFO, PROCESS_INFO, CUI_BAN_INFO, DU_BAN_INFO};
    @Inject
    Gson gson;

    CommentCurrencyAdapter mAdapter;

    public static MatterDetailTableFragment newInstance(String eventRecordId, String orderId, int flag) {
        Bundle bundle = new Bundle();
        bundle.putString(ID2, eventRecordId);
        bundle.putString(ID, orderId);
        bundle.putInt(FLAG, flag);
        MatterDetailTableFragment fragment = new MatterDetailTableFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMatterDetailTableComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matter_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            eventRecordId = arguments.getString(ID2);
            id = arguments.getString(ID);
            flag = arguments.getInt(FLAG);
        }
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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

    @Override
    protected void lazyLoadData() {
        if (StringUtil.isNotNullString(id) && flag != NULL_NUM) {
            switch (flag) {
                case BASE_INFO:
                    mPresenter.getDetailById(eventRecordId, id, flag);
                    break;
                case PROCESS_INFO:
                    mPresenter.getProgress(eventRecordId, id, flag);
                    break;
                case CUI_BAN_INFO:
                    mPresenter.getCuiBan(eventRecordId, id, flag);
                    break;
                case DU_BAN_INFO:
                    mPresenter.getDuBan(eventRecordId, id, flag);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setDetail(MatterDetailsBean o) {
        try {
            if (mAdapter == null) {
                mAdapter = new CommentCurrencyAdapter();
                ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(getActivity()));
                rv.setClipToPadding(false);
                rv.setAdapter(mAdapter);
            }
            List<BasicInformationBean.RecordsBean> list = new ArrayList<>();
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项编号", "Code", "请填写项目名称", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项标题", "Title", "请填写事项标题", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项等级", "EnumEventLevelStr", "请选择事项等级", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项处置状态", "EnumOrderStatusStr", "请填写事项等级", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项信息来源", "EnumSourceStr", "请选择事项信息来源", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "创建日期", "CreateTimeStr", "请填写创建日期", true));
            list.add(new BasicInformationBean.RecordsBean(LARGE_EDIT_ITEM,
                    "事项详情", "Content", "请填写事项详情", true));
            list.add(new BasicInformationBean.RecordsBean(ADDRESS_ITEM,
                    "发生位置", "LocationInJson", "请选择发生位置", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "发生地址", "EventAddress", "请填写发生地址", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "事项信息来源", "EnumSourceStr", "请填写事项信息来源", true));
            list.add(new BasicInformationBean.RecordsBean(EDIT_ITEM,
                    "上报人姓名", "CreateEmName", "请填写上报人姓名", true));
            list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM,
                    "上报人电话", "CreateEmTel", "请填写上报人电话", true));
            list.add(new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM,
                    "上报人身份证", "CreateEmSID", "请填写上报人身份证", true));
            list.add(new BasicInformationBean.RecordsBean(MORE_IMG_ITEM,
                    "现场照片", "ImgsInJson", "增加图片"));
            list.add(new BasicInformationBean.RecordsBean(MORE_URL_ITEM,
                    "现场视频", "VideoUrl", "增加视频"));
            Map<String, Object> stringObjectMap = MapUtils.entityToMapHasNull(o);
            for (BasicInformationBean.RecordsBean bean : list) {
                bean.setEdit(false);
                bean.setValue(StringUtil.setText(stringObjectMap.get(bean.getName()) + ""));
            }
            mAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProgress(List<MatterDetailsFlowBean> o) {
        try {
            if (mAdapter == null) {
                mAdapter = new CommentCurrencyAdapter();
                ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(getActivity()));
                rv.setClipToPadding(false);
                rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_5)
                        .build());
                rv.setAdapter(mAdapter);
            }
            List<BasicInformationBean.RecordsBean> list = new ArrayList<>();
            for (MatterDetailsFlowBean matterDetailsFlowBean : o) {
                BasicInformationBean.RecordsBean recordsBean =
                        new BasicInformationBean.RecordsBean(MATTER_FLOW, "", "", "");
                recordsBean.setValue(gson.toJson(matterDetailsFlowBean));
                list.add(recordsBean);
            }
            mAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCuiBan(Object o) {
        if (mAdapter == null) {
            mAdapter = new CommentCurrencyAdapter();
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(getActivity()));
            mAdapter.setEmptyView(R.layout.null_content_layout, rv);
            rv.setClipToPadding(false);
            rv.setAdapter(mAdapter);
        }
    }

    @Override
    public void setDuBan(Object o) {
        if (mAdapter == null) {
            mAdapter = new CommentCurrencyAdapter();
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(getActivity()));
            mAdapter.setEmptyView(R.layout.null_content_layout, rv);
            rv.setClipToPadding(false);
            rv.setAdapter(mAdapter);
        }
    }
}
