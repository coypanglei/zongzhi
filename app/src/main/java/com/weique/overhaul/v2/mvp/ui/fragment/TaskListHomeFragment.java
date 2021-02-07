package com.weique.overhaul.v2.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EnumConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerTaskListHomeComponent;
import com.weique.overhaul.v2.mvp.contract.TaskListHomeContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.TaskListHomePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormCrudActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionAddActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.TaskListAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/22/2020 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author GreatKing
 */
public class TaskListHomeFragment extends BaseLazyLoadFragment<TaskListHomePresenter> implements TaskListHomeContract.View {
    @Inject
    TaskListAdapter taskListAdapter;
    @Inject
    LinearLayoutManager manager;
    @Inject
    HorizontalDividerItemDecoration itemDecoration;
    @Inject
    Gson gson;

    private GridInformationBean bean = new GridInformationBean();


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.sw)
    SwipeRefreshLayout sw;
    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;
    private CommonRecyclerPopupWindow commonRecyclerPopupWindow;

    /**
     * 选中的任务的 目标类型信息
     */
    private TaskListBean.ListBean.MissionConditionsBean mMissionConditionsBean;
    private TaskListBean.ListBean item;

    public static TaskListHomeFragment newInstance() {
        TaskListHomeFragment fragment = new TaskListHomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTaskListHomeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list_home, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            assert mPresenter != null;
            initAdapter();
            sw.setOnRefreshListener(() -> {
                mPresenter.GetDepartment();
                mPresenter.getTaskList(true, false);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            ArmsUtils.configRecyclerView(recycler, manager);
            recycler.addItemDecoration(itemDecoration);
            recycler.setAdapter(taskListAdapter);
            taskListAdapter.setEmptyView(R.layout.null_content_home_layout, recycler);
            taskListAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    item = (TaskListBean.ListBean) adapter.getItem(position);
                    if (item == null || item.getEnumMissionStatus() > TaskListAdapter.UN_COMPLETE) {
                        return;
                    }

                    List<TaskListBean.ListBean.MissionConditionsBean> missionConditions = item.getMissionConditions();
                    if (item.getEnumMissionType() == 3) {
                        mPresenter.getPermission();
                        return;
                    }
                    //当前界面选择要执行的任务的目标 直接跳转到 目标界面
                    if (missionConditions != null && missionConditions.size() > 1) {
                        if (commonRecyclerPopupAdapter == null) {
                            commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter<TaskListBean.ListBean.MissionConditionsBean>();
                        }
                        if (commonRecyclerPopupWindow == null) {
                            commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(getActivity(),
                                    commonRecyclerPopupAdapter, new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    try {
                                        mMissionConditionsBean = (TaskListBean.ListBean.MissionConditionsBean) adapter.getItem(position);
                                        skip(item);
                                        commonRecyclerPopupWindow.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }
                        commonRecyclerPopupWindow.setNewData(missionConditions);
                        if (!commonRecyclerPopupWindow.isShowing()) {
                            commonRecyclerPopupWindow.showPopupWindow();
                        }
                    } else if (missionConditions == null || missionConditions.size() <= 0) {//没有目标时跳转
                        //点击的是事件 && 没有事件类型时
                        if (item.getEnumMissionType() == 4) {
                            ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDACTIVITY)
                                    .withParcelable("MissionConditionsBean", mMissionConditionsBean)
                                    .navigation();
                        } else {
                            //点击的是采集寻访等 && 没有事件类型时
                            ARouter.getInstance().build(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                                    .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
                                    .withInt(TourVisitActivity.TYPE, item.getEnumMissionType())
                                    .navigation();
                        }
                    } else {
                        mMissionConditionsBean = missionConditions.get(0);
                        skip(item);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            taskListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getTaskList(false, true);
            }, recycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转
     *
     * @param item item
     */
    private void skip(TaskListBean.ListBean item) {
        try {
            assert mPresenter != null;
            switch (item.getEnumMissionType()) {
                //信息采集
                case 0:
                    InformationCollectionActivity.mType = InformationCollectionActivity.GATHER;
                    mPresenter.getGetNewGuidForApp(mMissionConditionsBean);
                    break;
                //走访
                case 1:
                    InformationCollectionActivity.mType = InformationCollectionActivity.INTERVIEW;
                    mPresenter.getDataStructureInJson(mMissionConditionsBean, EnumConstant.DynamicBeanByType.COLLECT);
                    break;
                //巡检
                case 2:
                    InformationCollectionActivity.mType = InformationCollectionActivity.INSPECTION;
                    mPresenter.getDataStructureInJson(mMissionConditionsBean, EnumConstant.DynamicBeanByType.COLLECT);
                    break;
                //签到
                case 3:
                    mPresenter.getPermission();
                    break;
                //事件
                case 4:
                    ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDACTIVITY)
                            .withParcelable("MissionConditionsBean", mMissionConditionsBean)
                            .navigation();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void addMoreData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        sw.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sw.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            taskListAdapter.loadMoreEnd(true);
        } else {
            taskListAdapter.loadMoreComplete();
        }
    }


    @Override
    public Activity getContext() {
        return getActivity();
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
    public void launchActivityByRouter(@NonNull String path) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setNewData(List<TaskListBean.ListBean> list, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                taskListAdapter.addData(list);
            } else {
                taskListAdapter.setNewData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        try {
            bean = gridInformationBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 guid
     *
     * @param id                    id
     * @param missionConditionsBean missionConditionsBean
     */
    @Override
    public void setGotoNewGuid(String id, TaskListBean.ListBean.MissionConditionsBean missionConditionsBean) {
        try {
            InformationDetailBean bean = new InformationDetailBean();
            bean.setDepartmentId(UserInfoUtil.getUserInfo().getDepartmentId());
            bean.setUserId(UserInfoUtil.getUserInfo().getUid());
            bean.setElementTypeId(missionConditionsBean.getId());
            bean.setName(missionConditionsBean.getName());
            bean.setId(id);
            bean.setElementId(id);
            mPresenter.getDataStructureInJson(bean, EnumConstant.DynamicBeanByType.COLLECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拿到动态表单  跳转界面
     *
     * @param s                     s
     * @param informationDetailBean informationDetailBean
     */
    @Override
    public void setDynamicForm(String s, InformationDetailBean informationDetailBean) {
        try {
            InformationDynamicFormSelectBean informationDynamicFormSelectBean = gson.fromJson(s, InformationDynamicFormSelectBean.class);
            ARouter.getInstance()
                    .build(RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                    .withParcelable(InformationDynamicFormCrudActivity.LIST, informationDynamicFormSelectBean)
                    .withInt(InformationDynamicFormCrudActivity.TYPE_KEY, EventBusConstant.ADD)
                    .withString(ARouerConstant.TITLE, StringUtil.setText(informationDetailBean.getName()))
                    .withParcelable(InformationDynamicFormCrudActivity.INFORMATIONDETAILBEAN, informationDetailBean)
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
                    .navigation();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param s                     s
     * @param missionConditionsBean missionConditionsBean
     * @param type                  这里为了区分 行为 后期可能需要处理
     */
    @Override
    public void setDynamicForm(String s, TaskListBean.ListBean.MissionConditionsBean missionConditionsBean,
                               @EnumConstant.DynamicBeanByType int type) {
        try {
            ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                    .withString(ARouerConstant.ID, missionConditionsBean.getId())
                    .withString(ARouerConstant.TITLE, missionConditionsBean.getName())
                    .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, s)
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 接收 刷新界面
     */
    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.COMMON_UPDATE:
                    mPresenter.getTaskList(true, false);
                    break;
                case EventBusConstant.COMMON_JUMP:
                    InformationTypeOneSecondBean.ElementListBean data =
                            (InformationTypeOneSecondBean.ElementListBean) eventBusBean.getData();
                    if (mMissionConditionsBean == null) {
                        mMissionConditionsBean = new TaskListBean.ListBean.MissionConditionsBean();
                        mMissionConditionsBean.setElementTypeId(data.getElementTypeId());
                        mMissionConditionsBean.setName(data.getElementTypeName());
                    }
                    if (item.getEnumMissionType() == InformationCollectionActivity.INTERVIEW) {
                        ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                                //设置来源（从哪个界面跳转过去的）  根据这个判断是 干啥的
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
                                .withString(TourVisitActivity.TYPE, "")
                                .withParcelable("MissionConditionsBean", mMissionConditionsBean)
                                .withParcelable("ElementListBean", data)
                                .withString(ARouerConstant.TITLE, "新增走访记录")
                                .navigation();
                    } else if (item.getEnumMissionType() == InformationCollectionActivity.INSPECTION) {
                        ARouter.getInstance().build(RouterHub.APP_INSPECTIONMAPACTIVITY)
                                //设置来源（从哪个界面跳转过去的）  根据这个判断是 干啥的
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)
                                .withString(TourVisitActivity.TYPE, "")
                                .withParcelable("MissionConditionsBean", mMissionConditionsBean)
                                .withParcelable("ElementListBean", data)
                                .withString(InspectionMapActivity.POINTS_IN_JSON, bean.getPointsInJSON())
                                .withString(InspectionMapActivity.IS_NEW_POINT, InspectionAddActivity.MINE_TASK)
                                .withString(ARouerConstant.TITLE, "巡检记录详情")
                                .navigation();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT)
    private void eventBusCallback(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.IS_REFRESH:
                    assert mPresenter != null;
                    mPresenter.getTaskList(true, false);
                    break;

                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void lazyLoadData() {
        try {
            mPresenter.GetDepartment();
            mPresenter.getTaskList(true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
