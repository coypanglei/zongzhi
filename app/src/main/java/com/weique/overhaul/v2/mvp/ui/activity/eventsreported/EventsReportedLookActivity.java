package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.MemoryLeakUtil;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedLookComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.model.entity.EventPriorityBean;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedLookPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupBothSidesAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAgentAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedLookAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedLookFlowAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.weique.overhaul.v2.mvp.ui.popupwindow.HeatDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.OrderHandleDetailDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.ScaleConfig;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean.IS_VIS_DELETE;


/**
 * 事件上报  查看界面
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
public class EventsReportedLookActivity extends BaseActivity<EventsReportedLookPresenter> implements EventsReportedLookContract.View, View.OnClickListener {

    TextView initiator;
    TextView eventSort;
    TextView eventSortMemo;
    ImageView eventPriorityImage;
    TextView eventPriority;
    TextView deadlineTime;
    TextView upTime;
    TextView mapAddress;
    TextView map_address_detail;
    TextView standardAddress;
    TextView selfHandle;

    @Inject
    Gson gson;
    @Inject
    EventsReportedLookFlowAdapter flowAdapter;
    RecyclerView flowRecycler;
    LinearLayout locationLayout;
    RecyclerView agentRecycler;
    @BindView(R.id.dynamic_recycler)
    RecyclerView dynamicRecycler;
    @BindView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    CardView cardView;
    @Inject
    EventsReportedLookAdapter formSelectAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;


    @Inject
    EventsReportedAgentAdapter eventsReportedAgentAdapter;

    public static final String LIST_BEAN = "listBean";
    @Autowired(name = LIST_BEAN)
    EventsReportedBean.ListBean listBean;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    /**
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment#MY 我的上报进入}
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment#HANDLE}
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment#ACCEPT}
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment#INSPECT}
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment#SYNERGY}
     * //类型对应的状态
     */
    @Autowired(name = ARouerConstant.TYPE)
    int type;


    @BindView(R.id.positive)
    TextView positive;
    @BindView(R.id.positive_layout)
    LinearLayout positiveLayout;
    @BindView(R.id.positive_arrow)
    ImageView positiveArrow;
    @BindView(R.id.negative)
    TextView negative;
    @BindView(R.id.neutral)
    TextView neutral;

    private View inflate_head;

    private EventsReportedLookBean eventsReportedLookBean;
    private MediaPlayer player;
    private boolean start = false;
    private boolean afterStart = false;
    private ImageView imageRecord;

    private RecyclerView imageRecyclerView;
    /**
     * 评论图片集合
     */
    List<InformationItemPictureBean> commentList;

    private InformationAddPhotoAdapter informationAddPhotoAdapter;
    /**
     * 多边形信息
     */
    @Autowired(name = MapActivity.POINTS_IN_JSON)
    String pointsInJSON;

    /**
     * 是否允许修改坐标
     */
    @Autowired(name = MapActivity.ADDRESS_CAN_CHANGED)
    boolean addressCanChanged;
    /**
     * 处理后布局
     */
    private LinearLayout afterLayout;
    private LinearLayout frontLayout;
    private RecyclerView frontHandleImage;
    private RecyclerView frontHandleVideo;
    private RecyclerView afterHandleImage;
    private RecyclerView afterHandleVideo;
    private ImageView afterRecordView;
    private int lastClickViewId;
    private CommonRecyclerPopupBothSidesAdapter<NameAndIdBean> adapter;
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private HeatDialog heatDialog;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedLookComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_events_reported_look;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(getString(R.string.event_detail));
        initHeadView();
        initDynamicForm();
        mPresenter.getEventDetail(listBean.getEventRecordId(), listBean.getCustId());
    }

    /**
     * 根据类型(从哪来的)  和 订单状态判断 按钮怎么显示
     */
    private void controllerButByTypeAndOrderState() {
        try {
            switch (type) {
                //我的上报 再次处理 作废
                case EventsReportedFFragment.MY:
                    //退回 和 作废操作
                    if (EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT
                            == eventsReportedLookBean.getEnumOrderStatus()) {
                        bottomLayout.setVisibility(View.VISIBLE);
                        positiveLayout.setVisibility(View.VISIBLE);
                        positiveArrow.setVisibility(View.GONE);
                        positive.setText("再次处理");

                        negative.setText("作废");
                        negative.setVisibility(View.VISIBLE);

                        neutral.setVisibility(View.GONE);
                        //评价操作
                    } else if (EventsReportedBean.ListBean.EventsReportedEnumNewBean.WAIT_EVALUATE
                            == eventsReportedLookBean.getEnumOrderStatus() ||
                            EventsReportedBean.ListBean.EventsReportedEnumNewBean.INSPECT_SUCCESS
                                    == eventsReportedLookBean.getEnumOrderStatus()) {
                        bottomLayout.setVisibility(View.VISIBLE);
                        positiveLayout.setVisibility(View.VISIBLE);
                        positiveArrow.setVisibility(View.GONE);
                        positive.setText("评价");

                        negative.setVisibility(View.GONE);

                        neutral.setVisibility(View.GONE);
                    } else {
                        bottomLayout.setVisibility(View.GONE);
                    }
                    break;
                //待处置 处置  流转  上报  下派 延期  请求协同
                case EventsReportedFFragment.HANDLE:
                    bottomLayout.setVisibility(View.VISIBLE);
                    positiveLayout.setVisibility(View.VISIBLE);
                    positiveArrow.setVisibility(View.VISIBLE);
                    positive.setText("处置方式");

                    negative.setText("请求协同");
                    negative.setVisibility(View.VISIBLE);

                    neutral.setVisibility(View.VISIBLE);
                    break;
                //待受理 受理 退回
                case EventsReportedFFragment.ACCEPT:
                    bottomLayout.setVisibility(View.VISIBLE);
                    positiveLayout.setVisibility(View.VISIBLE);
                    positiveArrow.setVisibility(View.GONE);
                    positive.setText("受理");
                    negative.setText("退回");
                    negative.setVisibility(View.VISIBLE);

                    neutral.setVisibility(View.GONE);
                    break;
                //待核查 核查通过 核查退回
                case EventsReportedFFragment.INSPECT:
                    bottomLayout.setVisibility(View.VISIBLE);
                    positiveLayout.setVisibility(View.VISIBLE);
                    positiveArrow.setVisibility(View.GONE);
                    positive.setText("核查通过");
                    negative.setText("核查退回");
                    negative.setVisibility(View.VISIBLE);

                    neutral.setVisibility(View.GONE);
                    break;
                //待协同 确定协同
                case EventsReportedFFragment.SYNERGY:
                    bottomLayout.setVisibility(View.VISIBLE);
                    positiveLayout.setVisibility(View.VISIBLE);
                    positiveArrow.setVisibility(View.GONE);
                    positive.setText("确定协同");
                    negative.setText("拒绝协同");
                    negative.setVisibility(View.VISIBLE);
                    neutral.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态表单部分
     */
    private void initDynamicForm() {
        ArmsUtils.configRecyclerView(dynamicRecycler, new LinearLayoutManager(this));
        dynamicRecycler.addItemDecoration(decoration);
        dynamicRecycler.setAdapter(formSelectAdapter);
        formSelectAdapter.addHeaderView(inflate_head);
    }

    /**
     * 顶部 流程  布局
     */
    private void initFlowRecyclerView() {
        try {
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(EventsReportedBean.ListBean.EVENTS_REPORTED_ENUM_TEXT));
            ArmsUtils.configRecyclerView(flowRecycler,
                    new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            flowRecycler.setAdapter(flowAdapter);
            flowAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化 头部布局
     */
    private void initHeadView() {
        try {
            inflate_head = View.inflate(this,
                    R.layout.activity_events_reported_look_head, null);
            locationLayout = inflate_head.findViewById(R.id.location_layout);
            locationLayout.setOnClickListener(this);
            flowRecycler = inflate_head.findViewById(R.id.flow_recycler);
            agentRecycler = inflate_head.findViewById(R.id.agent_recycler);
            initiator = inflate_head.findViewById(R.id.initiator);
            eventSort = inflate_head.findViewById(R.id.event_sort);
            eventSortMemo = inflate_head.findViewById(R.id.event_sort_memo);
            eventSortMemo.setOnClickListener(this);
            eventPriorityImage = inflate_head.findViewById(R.id.event_priority_image);
            eventPriority = inflate_head.findViewById(R.id.event_priority);
            deadlineTime = inflate_head.findViewById(R.id.deadline_time);
            upTime = inflate_head.findViewById(R.id.up_time);
            mapAddress = inflate_head.findViewById(R.id.map_address);
            standardAddress = inflate_head.findViewById(R.id.standard_address);
            selfHandle = inflate_head.findViewById(R.id.self_handle);
            cardView = inflate_head.findViewById(R.id.card_view);
            map_address_detail = inflate_head.findViewById(R.id.map_address_detail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 底部布局
     */
    private void initFootView() {
        try {
            View inflateFoot = View.inflate(this,
                    R.layout.activity_events_reported_look_foot, null);
            imageRecord = inflateFoot.findViewById(R.id.image_record);
            LinearLayout commentLayout = inflateFoot.findViewById(R.id.comment_layout);
            /*
             * 评论内容
             */
            if (StringUtil.isNotNullString(eventsReportedLookBean.getEvaluation())) {
                commentLayout.setVisibility(View.VISIBLE);
                TextView commentText = inflateFoot.findViewById(R.id.comment_text);
                commentText.setText(eventsReportedLookBean.getEvaluation());
            } else {
                commentLayout.setVisibility(View.GONE);
            }

            afterRecordView = inflateFoot.findViewById(R.id.after_record_view);
            /*
             * 录音控件
             */
            if (StringUtil.isNotNullString(eventsReportedLookBean.getRecordUrl())) {
                imageRecord.setVisibility(View.VISIBLE);
                imageRecord.setOnClickListener(this);
            } else {
                imageRecord.setVisibility(View.GONE);
            }

            if (StringUtil.isNotNullString(eventsReportedLookBean.getAfterProceedRecordUrl())) {
                afterRecordView.setVisibility(View.VISIBLE);
                afterRecordView.setOnClickListener(this);
            } else {
                afterRecordView.setVisibility(View.GONE);
            }
            /*
             * 评论图片
             */
            imageRecyclerView = inflateFoot.findViewById(R.id.rv_images);
            afterLayout = inflateFoot.findViewById(R.id.after_layout);
            frontLayout = inflateFoot.findViewById(R.id.front_handle_layout);
            frontHandleImage = inflateFoot.findViewById(R.id.front_handle_image);
            setFootRecyclerAdapter(frontHandleImage, eventsReportedLookBean.getImgsInJson());
            frontHandleVideo = inflateFoot.findViewById(R.id.front_handle_video);
            setFootRecyclerAdapter(frontHandleVideo, eventsReportedLookBean.getVideoUrl());
            if (eventsReportedLookBean.getEnumEventProcType() == 0) {
                afterLayout.setVisibility(View.VISIBLE);
                afterHandleImage = inflateFoot.findViewById(R.id.after_handle_image);
                setFootRecyclerAdapter(afterHandleImage, eventsReportedLookBean.getAfterProceedImgsInJson());
                afterHandleVideo = inflateFoot.findViewById(R.id.after_handle_video);
                setFootRecyclerAdapter(afterHandleVideo, eventsReportedLookBean.getAfterProceedVideoUrl());
            } else {
                afterLayout.setVisibility(View.GONE);
            }
            commentList = new ArrayList<>();
            informationAddPhotoAdapter = new InformationAddPhotoAdapter(commentList);
            ArmsUtils.configRecyclerView(imageRecyclerView,
                    new GridLayoutManager(AppManager.getAppManager().getmApplication(),
                            4, RecyclerView.VERTICAL, false));
            imageRecyclerView.setAdapter(informationAddPhotoAdapter);
            imageRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                    .Builder(AppManager.getAppManager().getmApplication())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            formSelectAdapter.addFooterView(inflateFoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param recycler
     * @param imgsInJson
     */
    private void setFootRecyclerAdapter(RecyclerView recycler, String imgsInJson) {
        try {
            if (StringUtil.isNotNullString(imgsInJson)) {
                List<String> list = gson.fromJson(imgsInJson, List.class);
                if (list != null && list.size() > 0) {
                    recycler.setVisibility(View.VISIBLE);
                    List<InformationItemPictureBean> beans = new ArrayList<>();
                    for (String s : list) {
                        if (StringUtil.isNotNullString(s)) {
                            beans.add(new InformationItemPictureBean(s, IS_VIS_DELETE));
                        }
                    }
                    InformationAddPhotoAdapter adapter = new InformationAddPhotoAdapter(beans);
                    ArmsUtils.configRecyclerView(recycler,
                            new GridLayoutManager(this, 4,
                                    RecyclerView.VERTICAL, false));
                    adapter.setEmptyView(R.layout.null_content_home_layout, recycler);
                    recycler.setAdapter(adapter);
                    adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            if (adapter1.getItem(position) instanceof InformationItemPictureBean) {
                                InformationItemPictureBean bean = (InformationItemPictureBean) adapter1.getItem(position);
                                if (StringUtil.isNotNullString(bean.getImageUrl())) {
                                    ARouter.getInstance()
                                            .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                            .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                            .navigation();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    recycler.setVisibility(View.VISIBLE);
                }
            }
        } catch (JsonSyntaxException e) {
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
        ArmsUtils.makeText(message);
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
     * 获取context
     *
     * @return Context
     */
    @Override
    public Context getActivity() {
        return this;
    }

    /**
     * 设置详情数据
     *
     * @param json json
     */
    @Override
    public void setData(Object json) {
        try {
            String s = gson.toJson(json);
            JSONObject jsonObject = new JSONObject(s);
            eventsReportedLookBean = gson.fromJson(s, EventsReportedLookBean.class);
            if (eventsReportedLookBean == null) {
                ArmsUtils.makeText("获取事件信息失败");
                return;
            }
            InformationDynamicFormSelectBean dynamicFormSelectBean =
                    gson.fromJson(eventsReportedLookBean.getStructureInJson(),
                            InformationDynamicFormSelectBean.class);
            if (dynamicFormSelectBean == null) {
                ArmsUtils.makeText("未查询到订单信息");
                return;
            }

            ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJson =
                    dynamicFormSelectBean.getStructureInJson();
            if (structureInJson != null && structureInJson.size() > 0) {
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> iterator = structureInJson.iterator();
                while (iterator.hasNext()) {
                    try {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = iterator.next();
                        next.setDefaultVal(jsonObject.getString(next.getPropertyName()));
                        if (StringUtil.isNullString(next.getDefaultVal()) || next.isDisabled()) {
                            iterator.remove();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                formSelectAdapter.setNewData(structureInJson);
            }
            initiator.setText(StringUtil.setText(eventsReportedLookBean.getUserName()));
            eventSort.setText(StringUtil.setText(eventsReportedLookBean.getEventTypeName()));
            if (StringUtil.isNullString(eventsReportedLookBean.getEventMemo())) {
                eventSortMemo.setVisibility(View.GONE);
            } else {
                eventSortMemo.setVisibility(View.VISIBLE);
                eventSortMemo.setText("描述：" + eventsReportedLookBean.getEventMemo());
            }
            EventPriorityBean eventPriorityBean = new EventPriorityBean(eventsReportedLookBean.getEnumEventLevel());
            eventPriorityImage.setImageResource(eventPriorityBean.getdrawableId());
            eventPriority.setTextColor(getResources().getColor(eventPriorityBean.getColorId()));
            eventPriority.setText(StringUtil.setText(eventPriorityBean.getPriority()));
            //事件处置 截止时间
            deadlineTime.setText(StringUtil.setText(eventsReportedLookBean.getDeadlineTime()));
            upTime.setText(StringUtil.setText(eventsReportedLookBean.getCreateTime()));
            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                    gson.fromJson(eventsReportedLookBean.getPointsInJson(),
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
            mapAddress.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
            map_address_detail.setText(StringUtil.setText(eventsReportedLookBean.getEventAddress()));
            standardAddress.setText(StringUtil.setText(eventsReportedLookBean.getFullPath()));
            selfHandle.setText(StringUtil.setText(eventsReportedLookBean.getEnumEventProcType() == 0 ? "是" : "否"));
            if (eventsReportedLookBean.getEnumEventProcType() == 0) {
                cardView.setVisibility(View.GONE);
            } else {
                cardView.setVisibility(View.VISIBLE);
                assert mPresenter != null;
                mPresenter.getEventProceedRecord(listBean.getCustId(), listBean.getEventRecordId());
            }
            initFootView();
            if (!ArmsUtils.isEmpty(eventsReportedLookBean.getImageUrl())) {
                /*
                 * 评论 集合
                 */
                commentList.clear();
                String[] strings = gson.fromJson(eventsReportedLookBean.getImageUrl(), String[].class);
                for (String str : strings) {
                    commentList.add(new InformationItemPictureBean(str, IS_VIS_DELETE));
                }
                if (commentList.size() > 0) {
                    imageRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    imageRecyclerView.setVisibility(View.GONE);
                }
            }

            informationAddPhotoAdapter.setNewData(commentList);
            initFlowRecyclerView();
            flowAdapter.setOrderStatus(eventsReportedLookBean.getEnumOrderStatus());
            //处理按钮怎么显示
            controllerButByTypeAndOrderState();
        } catch (JSONException e) {
            ArmsUtils.makeText("数据格式错误");
            e.printStackTrace();
        } catch (Exception e) {
            ArmsUtils.makeText("数据异常");
            e.printStackTrace();
        }
    }

    /**
     * 设置办理人列表
     *
     * @param eventProceedRecordBeans eventProceedRecordBeans
     */
    @Override
    public void setTransactList(List<EventProceedRecordBean> eventProceedRecordBeans) {
        try {
            ArmsUtils.configRecyclerView(agentRecycler, new LinearLayoutManager(this));
            agentRecycler.setAdapter(eventsReportedAgentAdapter);
            eventsReportedAgentAdapter.setNewData(eventProceedRecordBeans);
            eventsReportedAgentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (view.getId() == R.id.all_item) {
                        Object item = adapter.getItem(position);
                        if (item instanceof EventProceedRecordBean) {
                            EventProceedRecordBean bean = (EventProceedRecordBean) item;
                            OrderHandleDetailDialog orderHandleDetailDialog =
                                    new OrderHandleDetailDialog(EventsReportedLookActivity.this, bean);
                            orderHandleDetailDialog.show();
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

    @Override
    public void gotoMapTv(String pointsInJson) {
        try {
            if (StringUtil.isNullString(pointsInJson)) {
                ArmsUtils.makeText("未获取到网格信息");
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                    .withString(MapActivity.POINTS_IN_JSON, pointsInJson)
                    .withString(MapActivity.LONANDLAT, eventsReportedLookBean.getPointsInJson())
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goToPhotoAlbum(int max) {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                max, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils.getPath(EventsReportedLookActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFile("", strings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    public void setUpLoadItem(List<InformationItemPictureBean> list) {
        if (heatDialog != null && heatDialog.isShowing()) {
            heatDialog.setNewImageUrl(list);
        }
    }

    /**
     * 更新个人信息
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.COMMON_UPDATE:
                    if (eventsReportedLookBean != null) {
                        if (eventsReportedLookBean.getEnumEventProcType() == 0) {
                            cardView.setVisibility(View.GONE);
                        } else {
                            cardView.setVisibility(View.VISIBLE);
                            assert mPresenter != null;
                            mPresenter.getEventProceedRecord(listBean.getCustId(), listBean.getEventRecordId());
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.positive_layout, R.id.negative, R.id.neutral})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            heatDialog = new HeatDialog(this);
            switch (v.getId()) {
                //正向操作
                case R.id.positive_layout:
                    switch (type) {
                        //我的上报 再次处理 作废
                        case EventsReportedFFragment.MY:
                            if (EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT
                                    == eventsReportedLookBean.getEnumOrderStatus()) {
                                //我上上报 加 positive_layout 点击 = 再次上报
                                ARouter.getInstance()
                                        .build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                                        .withString(ARouerConstant.ID, listBean.getEventRecordId())
                                        .withString(EventsReportedCrudActivity.CUST_ID, listBean.getCustId())
                                        .withInt(ARouerConstant.STATUS, EventBusConstant.ADD)
                                        .withString(MapActivity.POINTS_IN_JSON, pointsInJSON)
                                        .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, addressCanChanged)
                                        .navigation();
                                //评价操作
                            } else if (EventsReportedBean.ListBean.EventsReportedEnumNewBean.WAIT_EVALUATE
                                    == eventsReportedLookBean.getEnumOrderStatus() ||
                                    EventsReportedBean.ListBean.EventsReportedEnumNewBean.INSPECT_SUCCESS
                                            == eventsReportedLookBean.getEnumOrderStatus()) {
                                heatDialog.setRecyclerViewOnItemClick(4,
                                        new HeatDialog.OnUploadImageListener() {
                                            @Override
                                            public void onnUploadImageClick(int max) {
                                                mPresenter.getPermission(max);
                                            }
                                        });
                                mPresenter.initDialog(
                                        listBean.getEventRecordId(), heatDialog,
                                        EventsReportedBean.ListBean.EventsReportedEnumNewBean.EVALUATE);
                                heatDialog.show();
                                return;
                            }
                            break;
                        //待处置 处置
                        case EventsReportedFFragment.HANDLE:
                            //弹出 处置选项
                            if (adapter == null) {
                                adapter = new CommonRecyclerPopupBothSidesAdapter<>();
                            }
                            commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(
                                    this, adapter,
                                    (adapter, view1, position) -> {
                                        switch (position) {
                                            //处置
                                            case 0:
                                                heatDialog.setRecyclerViewOnItemClick(4,
                                                        new HeatDialog.OnUploadImageListener() {
                                                            @Override
                                                            public void onnUploadImageClick(int max) {
                                                                mPresenter.getPermission(max);
                                                            }
                                                        });
                                                mPresenter.initDialog(
                                                        listBean.getId(),
                                                        heatDialog,
                                                        EventsReportedBean.ListBean.EventsReportedEnumNewBean.DISPOSE_END);
                                                heatDialog.show();
                                                break;
                                            //移交
                                            case 1:
                                                ARouter.getInstance().build(RouterHub.APP_EVENTTURNOVERACTIVITY)
                                                        .withString(ARouerConstant.ID, listBean.getId())
                                                        .withString(ARouerConstant.PORT, EventTurnOverActivity.TURN_OVER)
                                                        .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                                                        .navigation();
                                                break;
                                            default:
                                                break;
                                        }
                                        commonRecyclerPopupWindow.dismiss();
                                        commonRecyclerPopupWindow.onDestroy();
                                    });
                            //设置数据
                            List<NameAndIdBean> list = new ArrayList<>();
                            list.add(new NameAndIdBean("事件完成处理登记", "处置"));
                            list.add(new NameAndIdBean("流转、上报、下派到相关部门", "移交"));
                            commonRecyclerPopupWindow.setNewData(list);
                            commonRecyclerPopupWindow.setPopupGravityMode
                                    (BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR,
                                            BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR);
                            commonRecyclerPopupWindow.setPopupGravity(Gravity.TOP);
                            Animation showAnimation = AnimationHelper.asAnimation()
                                    .withScale(ScaleConfig.CENTER)
                                    .toShow();
                            Animation dismissAnimation = AnimationHelper.asAnimation()
                                    .withScale(ScaleConfig.CENTER)
                                    .toDismiss();
                            commonRecyclerPopupWindow.setShowAnimation(showAnimation);
                            commonRecyclerPopupWindow.setDismissAnimation(dismissAnimation);
//                                commonRecyclerPopupWindow.setBackgroundColor(ArmsUtils.getColor(this, R.color.transparent));
                            commonRecyclerPopupWindow.showPopupWindow(bottomLayout);
                            break;
                        //退回
                        case EventsReportedFFragment.ACCEPT:
                            mPresenter.receivingOrder(listBean.getId(), 2, "");
                            break;
                        //核查退回
                        case EventsReportedFFragment.INSPECT:
                            mPresenter.eventRecordCheckOrder(listBean.getId(), 6, "");
                            break;
                        //确定协同
                        case EventsReportedFFragment.SYNERGY:
//                            ArmsUtils.makeText("确定协同");
                            mPresenter.initDialog(
                                    listBean.getId(),
                                    heatDialog,
                                    EventsReportedBean.ListBean.SYNCHRONIZE);
                            heatDialog.show();
                            break;
                        default:
                            break;
                    }
                    break;
                case R.id.negative:
                    switch (type) {
                        //作废
                        case EventsReportedFFragment.MY:
//                            ArmsUtils.makeText("作废");
                            new CommonDialog.Builder(this)
                                    .setContent("作废后，将无法再处理该订单")
                                    .setNegativeButton("作废", (view, commonDialog) -> {
                                        mPresenter.invalid(listBean.getEventRecordId());
                                    })
                                    .setPositiveButton("取消", (view, commonDialog) -> {
                                        super.onBackPressed();
                                    }).create().show();

                            break;
                        //请求协同
                        case EventsReportedFFragment.HANDLE:
                            ARouter.getInstance().build(RouterHub.APP_EVENTTURNOVERACTIVITY)
                                    .withString(ARouerConstant.ID, listBean.getId())
                                    .withString(ARouerConstant.PORT, EventTurnOverActivity.SYNERGY)
                                    .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                                    .navigation();
                            break;
                        //退回
                        case EventsReportedFFragment.ACCEPT:
                            mPresenter.initDialog(

                                    listBean.getId(),
                                    heatDialog,
                                    EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT);
                            heatDialog.show();
                            break;
                        //核查退回
                        case EventsReportedFFragment.INSPECT:
                            mPresenter.initDialog(

                                    listBean.getId(),
                                    heatDialog,
                                    EventsReportedBean.ListBean.EventsReportedEnumNewBean.INSPECT_FAIL);
                            heatDialog.show();
                            break;
                        //确定协同
                        case EventsReportedFFragment.SYNERGY:
//                            ArmsUtils.makeText("拒绝协同");
                            mPresenter.initDialog(
                                    listBean.getId(),
                                    heatDialog,
                                    EventsReportedBean.ListBean.REFUSED);
                            heatDialog.show();
                            break;
                        default:
                            break;
                    }
                    break;
                case R.id.neutral:
                    switch (type) {
                        //延期
                        case EventsReportedFFragment.HANDLE:
                            ARouter.getInstance().build(RouterHub.APP_EVENTTURNOVERACTIVITY)
                                    .withString(ARouerConstant.ID, listBean.getId())
                                    .withString(ARouerConstant.PORT, EventTurnOverActivity.POSTPONE)
                                    .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                                    .navigation();
                            break;
                        default:
                            break;
                    }

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.location_layout:
                    if (StringUtil.isNotNullString(pointsInJSON)) {
                        gotoMapTv(pointsInJSON);
                    } else {
                        String pointsInJson = eventsReportedLookBean.getPointsInJson();
                        if (StringUtil.isNotNullString(pointsInJson)) {
                            assert mPresenter != null;
                            mPresenter.getDepartment(eventsReportedLookBean.getDepartmentId());
                        } else {
                            ArmsUtils.makeText("未发现定位地址");
                        }
                    }
                    break;
                case R.id.image_record:
                    lastClickViewId = R.id.image_record;
                    if (start) {
                        start = false;
                        imageRecord.setImageResource(R.drawable.record_off);
                        stopPlaying();
                    } else {
                        if (afterStart) {
                            afterStart = false;
                            afterRecordView.setImageResource(R.drawable.record_off);
                            stopPlaying();
                        }
                        start = true;
                        imageRecord.setImageResource(R.drawable.record_on);
                        startPlaying(eventsReportedLookBean.getRecordUrl());
                    }
                    break;
                case R.id.after_record_view:
                    lastClickViewId = R.id.after_record_view;
                    if (afterStart) {
                        afterStart = false;
                        afterRecordView.setImageResource(R.drawable.record_off);
                        stopPlaying();
                    } else {
                        if (start) {
                            start = false;
                            imageRecord.setImageResource(R.drawable.record_off);
                            stopPlaying();
                        }
                        afterStart = true;
                        afterRecordView.setImageResource(R.drawable.record_on);
                        startPlaying(eventsReportedLookBean.getAfterProceedRecordUrl());
                    }
                    break;
                case R.id.event_sort_memo:
                    new CommonDialog.Builder(this).setTitle("描述")
                            .setContent(eventsReportedLookBean.getEventMemo())
                            .setNegativeBtnShow(false)
                            .setPositiveButton("确定", (v1, commonDialog) -> {
                            }).create().show();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放
     *
     * @param fileName fileName
     */
    private void startPlaying(String fileName) {
        try {
            if (player != null) {
                player.release();
            }
            player = new MediaPlayer();
            player.setDataSource(GlideUtil.handleUrl(this, fileName));
            player.prepare();
            player.start();
            player.setOnCompletionListener(mp -> {
                try {
                    if (lastClickViewId == R.id.record_view) {
                        start = false;
                        imageRecord.setImageResource(R.drawable.record_off);
                    } else {
                        afterStart = false;
                        afterRecordView.setImageResource(R.drawable.record_off);
                    }
                    player.stop();
                    player.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            Timber.e("prepare() failed");
        }
    }

    /**
     * 停止
     */
    private void stopPlaying() {
        try {
            if (player != null) {
                player.release();
                player = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {

            //手动切断InputMethodManager里的View引用链
            MemoryLeakUtil.fixInputMethodMemoryLeak(this);
            if (player != null) {
                player.release();
            }
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
