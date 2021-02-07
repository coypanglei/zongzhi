package com.weique.overhaul.v2.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerMyComponent;
import com.weique.overhaul.v2.mvp.contract.MyContract;
import com.weique.overhaul.v2.mvp.model.entity.IntergralBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.WorkLineLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MyPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.chat.voip.ChatSelectActivity;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.sign.SignInActivity;
import com.weique.overhaul.v2.mvp.ui.activity.task.TaskListActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionListActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.ResourceSearchActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 我的
 * <p>
 * ================================================
 *
 * @author GK
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tag_one)
    TextView tagOne;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.layout_event)
    TextView layoutEvent;
    @BindView(R.id.layout_attendance)
    TextView layoutAttendance;
    @BindView(R.id.layout_interview)
    TextView layoutInterview;
    @BindView(R.id.layout_patrol)
    TextView layoutPatrol;
    @BindView(R.id.layout_settings)
    TextView layoutSettings;
    @BindView(R.id.chat)
    TextView chat;
    @BindView(R.id.task_list)
    TextView taskList;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_the_inspection_points)
    TextView tvTheInspectionPoints;
    private ArrayList<WorkLineLatBean> list;


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    /**
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                mPresenter.setIntegral();
                EventBus.getDefault().post(new EventBusBean(EventBusConstant.REQUEST_AGAIN), RouterHub.APP_MAINACTIVITY);
                initViewVisible();
                hideLoading();
            });
            initViewVisible();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewVisible() {
        //此界面需要添加 新功能 必须加到map中  可以通过 首页的菜单栏控制 布局显示
        Map<String, View> map = new HashMap<>();
        //签到
        map.put(SignInActivity.class.getSimpleName(), layoutAttendance);
        //走访
        map.put(ResourceSearchActivity.class.getSimpleName(), layoutInterview);
        //巡检
        map.put(InspectionListActivity.class.getSimpleName(), layoutPatrol);
        //视频会商
        map.put(ChatSelectActivity.class.getSimpleName(), chat);
        //工作清单
        map.put(TaskListActivity.class.getSimpleName(), taskList);

        //控制view是否显示
        AccessControlUtil.fromActivityNameComparisonMenuControlViewGone(map);

        initUserInfo();
        hideLoading();
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initUserInfo() {
        try {
            GlideUtil.loadRoundImage(mContext, UserInfoUtil.getUserInfo().getHeadUrl(), headImg, 5);
            String roleName = "";
            if (StringUtil.isNotNullString(UserInfoUtil.getUserInfo().getRoleName())) {
                roleName = "(" + StringUtil.setText(UserInfoUtil.getUserInfo().getRoleName()) + ")";
            }
            name.setText(UserInfoUtil.getUserInfo().getName() + roleName);
            tagOne.setText(UserInfoUtil.getUserInfo().getFullPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
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
    public void onResume() {
        super.onResume();
        mPresenter.setIntegral();
    }

    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(getContext(), path);
    }


    @OnClick({R.id.top_layout, R.id.layout_event,
            R.id.layout_attendance, R.id.layout_interview, R.id.layout_the_inspection_points,
            R.id.layout_patrol, R.id.layout_settings,
            R.id.head_img, R.id.task_list, R.id.chat})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.top_layout:
                    launchActivityByRouter(RouterHub.APP_MY_INFO_ACTIVITY);
                    break;
                case R.id.layout_event:
                    launchActivityByRouter(RouterHub.APP_PARTICIPATEEVENTACTIVITY);
                    break;
                /**
                 *  积分
                 */
                case R.id.layout_the_inspection_points:
                    ARouter.getInstance()
                            .build(RouterHub.APP_INTEGRAL_ACTIVITY)
                            .withString(ARouerConstant.ID, tvTheInspectionPoints.getText().toString())
                            .navigation();
                    break;
                case R.id.layout_attendance:
                    mPresenter.getPermission();
                    break;
                case R.id.layout_interview:
                    launchActivityByRouter(RouterHub.APP_RESOURCESEARCHACTIVITY);
                    break;
                case R.id.layout_patrol:
                    launchActivityByRouter(RouterHub.APP_INSPECTIONLISTACTIVITY);
                    break;
                case R.id.layout_settings:
                    launchActivityByRouter(RouterHub.APP_SETTINGSACTIVITY);
                    break;
                case R.id.head_img:
                    ARouter.getInstance()
                            .build(RouterHub.APP_PICTURELOOKACTIVITY)
                            .withString(PictureLookActivity.URL_, UserInfoUtil.getUserInfo().getHeadUrl())
                            .withString(ARouerConstant.TITLE, "头像")
                            .navigation();
                    break;
                case R.id.task_list:
                    ARouter.getInstance()
                            .build(RouterHub.APP_TASKLISTACTIVITY)
                            .navigation();
                    break;
                case R.id.chat:
                    if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                        ARouter.getInstance()
                                .build(RouterHub.APP_CHATSELECTACTIVITY)
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_ADDRESSBOOKACTIVITY)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_CHATSELECTACTIVITY)
                                .withString(ARouerConstant.TITLE, getString(R.string.video_hs))
                                .navigation();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回调
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_MYFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.COMMON_UPDATE:
                    initUserInfo();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getIntegral(IntergralBean Integral) {

        try {
            tvTheInspectionPoints.setText(Integral.getSumScore());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
