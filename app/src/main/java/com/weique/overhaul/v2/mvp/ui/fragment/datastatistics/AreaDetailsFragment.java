package com.weique.overhaul.v2.mvp.ui.fragment.datastatistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerAreaDetailsComponent;
import com.weique.overhaul.v2.mvp.contract.AreaDetailsContract;
import com.weique.overhaul.v2.mvp.model.entity.AreaDetailsListBean;
import com.weique.overhaul.v2.mvp.presenter.AreaDetailsPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.AreaDetailsListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AreaDetailsFragment extends BaseFragment<AreaDetailsPresenter> implements AreaDetailsContract.View {
    @Autowired(name = "Level")
    String level;
    @Autowired(name = "id")
    String id;


    @BindView(R.id.start_data)
    TextView startData;
    @BindView(R.id.end_data)
    TextView endData;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.address_name)
    TextView addressName;


    private AreaDetailsListAdapter areaDetailsListAdapter;

    private List<AreaDetailsListBean> listBeans;

    private int Pos;

    private TimePickerView startTimePickerView;


    private TimePickerView endTimePickerView;

    public static AreaDetailsFragment newInstance() {
        AreaDetailsFragment fragment = new AreaDetailsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAreaDetailsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_area_details, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);

        try {

            initApapter();
            initTime();
            initTimePicker();
            assert mPresenter != null;
            mPresenter.getAreaDetailsListData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {

        startTimePickerView = PickerViewUtil.selectPickerTimeByToday(getActivity(), Constant.YMD, (date, v) -> {
            try {
                //开始时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                startData.setText(format.format(date));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        endTimePickerView = PickerViewUtil.selectPickerTimeToday(getActivity(), Constant.YMD, (date, v) -> {
            try {
                //结束时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                endData.setText(format.format(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initApapter() {
        try {
            areaDetailsListAdapter = new AreaDetailsListAdapter();
            areaDetailsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(areaDetailsListAdapter);
            areaDetailsListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);

            recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
                @Override
                public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                    switch (view.getId()) {
                        case R.id.layout_click:
                            try {
                               AreaDetailsListBean listBean = (AreaDetailsListBean) adapter.getData().get(position);
                                if (listBean.isClick()) {
                                    listBean.setClick(false);
                                    adapter.notifyItemChanged(position);
                                } else {
                                    listBean.setClick(true);

                                    String start = startData.getText().toString().trim();
                                    String end = endData.getText().toString().trim();

                                    assert mPresenter != null;
                                    //type 类型为1 时 传送参数
                                    Timber.e("type"+listBean.getType());
                                    mPresenter.getAreaDetailsSecondListData(listBean.getId(), start,end,listBean.getType());
                                    Pos = position;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        case R.id.down_data:
                            try {
                                if (AppUtils.isFastClick()) {
                                    return;
                                }


                                ARouter.getInstance().build(RouterHub.APP_AREADETAILSECONDACTIVITY)
                                        .withString("Level", ((AreaDetailsListBean) adapter.getData().get(position)).getLevel())
                                        .withString("id", ((AreaDetailsListBean) adapter.getData().get(position)).getId())
                                        .withString("path", ((AreaDetailsListBean) adapter.getData().get(position)).getFullPath())
                                        .navigation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                    }


                }
            });

//
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @OnClick({R.id.start_data, R.id.end_data, R.id.search})
    public void onViewClicked(View view) {

        try {

            if (AppUtils.isFastClick()) {
                return;
            }
            String start = startData.getText().toString().trim();
            String end = endData.getText().toString().trim();
            switch (view.getId()) {
                //起始时间
                case R.id.start_data:
                    if (!startTimePickerView.isShowing()) {
                        startTimePickerView.show();
                    }
                    break;
                //结束时间
                case R.id.end_data:
                    if (!endTimePickerView.isShowing()) {
                        endTimePickerView.show();
                    }
                    break;
                case R.id.search:

//                    int compareTo = start.compareTo(end);
//                    if (compareTo == 0) {
//                        ArmsUtils.makeText("至少选择时间间隔要大于一天");
//                        return;
//                    }
                    assert mPresenter != null;
                    mPresenter.getAreaDetailsListData();

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initTime() {
        try {
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
            Date date = calendar.getTime();
            endData.setText(format.format(date));
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - 30);
            startData.setText(format.format(calendar.getTime()));




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAreaDetailsListData(List<AreaDetailsListBean> data) {
        try {
            addressName.setText(UserInfoUtil.getUserInfo().getFullPath());
            listBeans = new ArrayList<>();
            for (AreaDetailsListBean datum : data) {
                datum.setClick(false);
                listBeans.add(datum);
            }
            areaDetailsListAdapter.setNewData(listBeans);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAreaDetailsSecondListData(List<AreaDetailsListBean> itemBean) {
        try {

            listBeans.get(Pos).setCollectionCount(itemBean.get(0).getCollectionCount());
            listBeans.get(Pos).setVisitCount(itemBean.get(0).getVisitCount());
            listBeans.get(Pos).setInspectionCount(itemBean.get(0).getInspectionCount());
            listBeans.get(Pos).setEventCount(itemBean.get(0).getEventCount());
            listBeans.get(Pos).setMediateCount(itemBean.get(0).getMediateCount());
            listBeans.get(Pos).setLevel(itemBean.get(0).getLevel());
            areaDetailsListAdapter.notifyItemChanged(Pos);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
