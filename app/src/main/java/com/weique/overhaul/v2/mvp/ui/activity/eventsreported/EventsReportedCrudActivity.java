package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.agentweb.di.module.MapControlUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedCuedComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedCrudContract;
import com.weique.overhaul.v2.mvp.model.entity.EventPriorityBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedCrudPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.NarrowDynamicFormAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.EventPriorityPopup;
import com.weique.overhaul.v2.mvp.ui.popupwindow.OptionPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 事件上报 曾删改
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
public class EventsReportedCrudActivity extends BaseActivity<EventsReportedCrudPresenter>
        implements EventsReportedCrudContract.View, View.OnClickListener {

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    NarrowDynamicFormAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    @Inject
    Gson gson;

    @BindView(R.id.dynamic_form_recycler)
    RecyclerView dynamicFormRecycler;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.alert_layout)
    LinearLayout alertLayout;
    @BindView(R.id.delete_layout)
    LinearLayout deleteLayout;

    /**
     * 是否自处理  默认自处理
     */
    ImageView selfHandle;


    /**
     * 0是自处理 1 是  正常提交
     */
    private int isSelfHandle = 1;
    /**
     * 事件分类选项
     */
    LinearLayout selectEventSort;
    TextView eventSortValue;
    TextView eventSortMemo;
    EditText addressName;
    /**
     * 事件优先级选项
     */
    RelativeLayout selectEventPriority;
    ImageView priorityImage;
    TextView priorityValue;
    /**
     * 事件发生地点地图定位
     */
    LinearLayout mapLocation;
    TextView mapLocationValue;
    /**
     * 事件发生地点 标准地址
     */
    RelativeLayout standardAddressLayout;
    TextView standardAddressText;


    private int adapterPosition = -1;


    /**
     * 接口需要字段
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * 接口需要字段
     */
    @Autowired(name = MapActivity.POINTS_IN_JSON)
    String pointsInJson;

    public static final String CUST_ID = "CUST_ID";
    @Autowired(name = CUST_ID)
    String custId;
    /**
     * 判断是修改还是新增
     */
    @Autowired(name = ARouerConstant.STATUS)
    int status;

    public static final String EVENTSREPORTEDLOOKBEAN = "EVENTSREPORTEDLOOKBEAN";

    @Autowired(name = EVENTSREPORTEDLOOKBEAN)
    EventsReportedLookBean eventsReportedLookBean;

    /**
     * 获取枚举值使用
     */
    private EventsReportedBean.ListBean listBean;
    private TimePickerView timePickerView;

    /**
     * 处置前 录音文件文件地址
     */
    private String frontRecordFilUrl;
    /**
     * 处置后 录音文件文件地址
     */
    private String afterRecordFilUrl;
    /**
     * 播放录音
     */
    private MediaPlayer player;

    /**
     * 处置前是否再播放
     */
    private boolean start = false;
    /**
     * 处置后是否再播放
     */
    private boolean afterStart = false;
    /**
     * 是否允许修改定位坐标
     */
    @Autowired(name = MapActivity.ADDRESS_CAN_CHANGED)
    boolean addressCanChanged;

    @Autowired(name = ARouerConstant.SOURCE)
    String source;


    /**
     * 最后一次点击的 view id
     */
    private int lastClickViewId = -1;
    /**
     * 处理前 添加 录音文件  布局
     */
    private LinearLayout addRecordLayout;
    private ImageView deleteX;
    private ImageView image;
    private RecyclerView frontHandleImage;
    private RecyclerView frontHandleVideo;


    /**
     * 处理后 添加 录音文件  布局
     */
    private LinearLayout afterAddRecordLayout;
    private ImageView afterDeleteX;
    private ImageView afterImage;
    private RecyclerView afterHandleImage;
    private RecyclerView afterHandleVideo;

    private LinearLayout afterLayout;
    /**
     * 处理前adapter
     */
    private InformationAddPhotoAdapter frontHandleImageAdapter;
    private InformationAddPhotoAdapter afterHandleImageAdapter;
    private InformationAddPhotoAdapter frontHandleVideoAdapter;
    private InformationAddPhotoAdapter afterHandleVideoAdapter;
    /**
     * 当前 adapter
     */
    private InformationAddPhotoAdapter currentAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedCuedComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_events_reported_crud;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            setTitle(getString(R.string.up_tell_intent));
            ARouter.getInstance().inject(this);
            listBean = new EventsReportedBean.ListBean();
            alertLayout.setVisibility(View.GONE);
            if (status == EventBusConstant.ALERT) {
                deleteLayout.setVisibility(View.VISIBLE);
            } else {
                deleteLayout.setVisibility(View.GONE);
            }
            initRecyclerView();
            initHeadAndFootView();
            if (StringUtil.isNotNullString(id)) {
                mPresenter.getEventDetail(id, custId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化recycler
     */
    private void initRecyclerView() {
        try {
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(dynamicFormRecycler, linearLayoutManager);
            dynamicFormRecycler.addItemDecoration(horizontalDividerItemDecoration);
            dynamicFormRecycler.setAdapter(mAdapter);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationDynamicFormSelectBean.StructureInJsonBean) {
                        InformationDynamicFormSelectBean.StructureInJsonBean bean =
                                (InformationDynamicFormSelectBean.StructureInJsonBean) adapter.getItem(position);
                        switch (bean.getIdentification().getType()) {
                            case InformationDynamicFormSelectBean.SingleLine:
                                break;
                            case InformationDynamicFormSelectBean.MultiLine:
                                break;
                            case InformationDynamicFormSelectBean.Number:
                                break;
                            case InformationDynamicFormSelectBean.SinglePic:
                                break;
                            case InformationDynamicFormSelectBean.MultiPics:
                                break;
                            case InformationDynamicFormSelectBean.DateTime:
                                showDateTimeSelector(bean, position);
                                break;
                            case InformationDynamicFormSelectBean.DropdownList:
                            case InformationDynamicFormSelectBean.Option:
                                if (view.getId() == R.id.all_item) {
                                    OptionPopup optionPopup = new OptionPopup(this, bean.getName(),
                                            bean.getIdentification().getType(),
                                            bean.getOption(), bean.getDefaultVal());
                                    optionPopup.setOnSureClickListener(nameValue -> {
                                        bean.setDefaultVal(nameValue);
                                        mAdapter.setData(position, bean);
                                    });
                                    optionPopup.showPopupWindow();
                                    KeybordUtil.hideKeyboard(EventsReportedCrudActivity.this);
                                }
                                break;
                            case InformationDynamicFormSelectBean.CheckBox:
                                if (view.getId() == R.id.all_item) {
                                    List<String> arrayList = new ArrayList<>();
                                    String defaultValString = bean.getDefaultVal();
                                    if (StringUtil.isNotNullString(defaultValString)) {
                                        if (defaultValString.contains("\\")) {
                                            defaultValString = defaultValString.replace("\\", "");
                                        }
                                        if (defaultValString.contains("[")) {
                                            arrayList = gson.fromJson(defaultValString, ArrayList.class);
                                        } else {
                                            if (defaultValString.contains("\"")) {
                                                arrayList.add(gson.fromJson(defaultValString, String.class));
                                            } else {
                                                arrayList.add(defaultValString);
                                            }
                                        }
                                    }
                                    OptionPopup optionPopup = new OptionPopup(this, bean.getName(),
                                            bean.getIdentification().getType(),
                                            bean.getOption(), arrayList);
                                    optionPopup.setOnSureClickListener(nameValue -> {
                                        bean.setDefaultVal(nameValue);
                                        mAdapter.setData(position, bean);
                                    });
                                    optionPopup.showPopupWindow();
                                    KeybordUtil.hideKeyboard(EventsReportedCrudActivity.this);
                                }
                            default:
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnPictureClickLisenter((adapterPosition, max, type) -> {
                mPresenter.getPermission(adapterPosition, max, type, true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 头部和尾部布局
     */
    private void initHeadAndFootView() {
        try {
            View headInflate = View.inflate(this, R.layout.activity_events_reported_crud_head, null);
            selectEventSort = headInflate.findViewById(R.id.select_event_sort);
            selectEventSort.setOnClickListener(this);
            eventSortValue = headInflate.findViewById(R.id.event_sort_value);
            addressName = headInflate.findViewById(R.id.address_name);
            eventSortMemo = headInflate.findViewById(R.id.event_sort_memo);
            eventSortMemo.setOnClickListener(this);
            selectEventPriority = headInflate.findViewById(R.id.select_event_priority);
            selectEventPriority.setOnClickListener(this);
            priorityImage = headInflate.findViewById(R.id.priority_image);
            priorityValue = headInflate.findViewById(R.id.priority_value);
            mapLocation = headInflate.findViewById(R.id.map_location);
            mapLocation.setOnClickListener(this);
            mapLocationValue = headInflate.findViewById(R.id.map_location_value);
            standardAddressLayout = headInflate.findViewById(R.id.standard_address_layout);
            standardAddressLayout.setOnClickListener(this);
            standardAddressText = headInflate.findViewById(R.id.standard_address_text);
            mAdapter.addHeaderView(headInflate);
            View footInflate = View.inflate(this, R.layout.activity_events_reported_crud_foot, null);
            selfHandle = footInflate.findViewById(R.id.self_handle);
            afterLayout = footInflate.findViewById(R.id.after_layout);
            selfHandle.setOnClickListener(v -> {
                try {
                    if (isSelfHandle == 0) {
                        isSelfHandle = 1;
                        selfHandle.setImageResource(R.drawable.s_close);
                        afterLayout.setVisibility(View.GONE);
                    } else {
                        isSelfHandle = 0;
                        selfHandle.setImageResource(R.drawable.s_open);
                        afterLayout.setVisibility(View.VISIBLE);
                        dynamicFormRecycler.scrollToPosition(mAdapter.getItemCount() - 1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //处理前
            frontHandleImage = footInflate.findViewById(R.id.front_handle_image);
            frontHandleVideo = footInflate.findViewById(R.id.front_handle_video);
            addRecordLayout = footInflate.findViewById(R.id.record_view);
            addRecordLayout.setOnClickListener(this);
            image = footInflate.findViewById(R.id.image_);
            image.setOnClickListener(this);
            deleteX = footInflate.findViewById(R.id.delete_x);
            deleteX.setOnClickListener(this);
            //处理后
            afterHandleImage = footInflate.findViewById(R.id.after_handle_image);
            afterHandleVideo = footInflate.findViewById(R.id.after_handle_video);
            afterAddRecordLayout = footInflate.findViewById(R.id.after_record_view);
            afterAddRecordLayout.setOnClickListener(this);
            afterImage = footInflate.findViewById(R.id.after_image_);
            afterImage.setOnClickListener(this);
            afterDeleteX = footInflate.findViewById(R.id.after_delete_x);
            afterDeleteX.setOnClickListener(this);
            mAdapter.addFooterView(footInflate);
            initFootRecyclerAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 脚布局 adapter 处置前上报  处置后 上报
     */
    private void initFootRecyclerAdapter() {
        try {
            //自处理前图片
            List<InformationItemPictureBean> list = new ArrayList<>();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));
            frontHandleImageAdapter = new InformationAddPhotoAdapter(list);
            frontHandleImageAdapter.setMaxItem(8);
            ArmsUtils.configRecyclerView(frontHandleImage,
                    new GridLayoutManager(this, 4));
            frontHandleImage.setAdapter(frontHandleImageAdapter);
            setAdapterItemClick(frontHandleImageAdapter, InformationDynamicFormSelectBean.MultiPics);
            //自处理后图片
            List<InformationItemPictureBean> list1 = new ArrayList<>();
            list1.add(0, new InformationItemPictureBean(R.drawable.picture));
            afterHandleImageAdapter = new InformationAddPhotoAdapter(list1);
            afterHandleImageAdapter.setMaxItem(8);
            ArmsUtils.configRecyclerView(afterHandleImage,
                    new GridLayoutManager(this, 4));
            afterHandleImage.setAdapter(afterHandleImageAdapter);
            setAdapterItemClick(afterHandleImageAdapter, InformationDynamicFormSelectBean.MultiPics);
            //自处理前视频
            List<InformationItemPictureBean> list2 = new ArrayList<>();
            list2.add(0, new InformationItemPictureBean(R.drawable.video));
            frontHandleVideoAdapter = new InformationAddPhotoAdapter(list2);
            frontHandleVideoAdapter.setMaxItem(1);
            ArmsUtils.configRecyclerView(frontHandleVideo,
                    new GridLayoutManager(this, 4));
            frontHandleVideo.setAdapter(frontHandleVideoAdapter);
            setAdapterItemClick(frontHandleVideoAdapter, InformationDynamicFormSelectBean.SingleVideo);
            //自处理后视频
            List<InformationItemPictureBean> list3 = new ArrayList<>();
            list3.add(0, new InformationItemPictureBean(R.drawable.video));
            afterHandleVideoAdapter = new InformationAddPhotoAdapter(list3);
            afterHandleVideoAdapter.setMaxItem(1);
            ArmsUtils.configRecyclerView(afterHandleVideo,
                    new GridLayoutManager(this, 4));
            afterHandleVideo.setAdapter(afterHandleVideoAdapter);
            setAdapterItemClick(afterHandleVideoAdapter, InformationDynamicFormSelectBean.SingleVideo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapterItemClick(InformationAddPhotoAdapter informationAddPhotoAdapter, String type) {
        informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                currentAdapter = informationAddPhotoAdapter;
                if (adapter.getItem(position) instanceof InformationItemPictureBean) {
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (InformationItemPictureBean.IS_DRAWABLE == bean.getType()) {
                        if (view.getId() == R.id.image_) {
                            if (adapter.getData().size() - 1 >= informationAddPhotoAdapter.getMaxItem()) {
                                //如果是视频提示不同
                                if (InformationDynamicFormSelectBean.SingleVideo.equals(type)) {
                                    ArmsUtils.makeText(
                                            String.format(ArmsUtils.getString(
                                                    EventsReportedCrudActivity.this,
                                                    R.string.video_up_load_max_file_number),
                                                    informationAddPhotoAdapter.getMaxItem()));
                                } else {
                                    ArmsUtils.makeText(
                                            String.format(ArmsUtils.getString(EventsReportedCrudActivity.this,
                                                    R.string.up_load_max_file_number),
                                                    informationAddPhotoAdapter.getMaxItem()));
                                }
                                return;
                            }
                            //max - list.size() + 1  可选的最大数量   +1 是上面 添加了  一个 默认选项
                            int max = informationAddPhotoAdapter.getMaxItem() - (informationAddPhotoAdapter.getData().size() - 1);
                            mPresenter.getPermission(position, max, type, false);
                        }
                    } else {
                        if (view.getId() == R.id.remove_image) {
                            informationAddPhotoAdapter.getData().remove(position);
                            informationAddPhotoAdapter.notifyDataSetChanged();
                        } else if (view.getId() == R.id.image_) {
                            if (StringUtil.isNotNullString(bean.getImageUrl())) {
                                ARouter.getInstance()
                                        .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                        .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                        .navigation();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void showLoading() {
        showProgressDialogContCanCancel();
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


    @OnClick({R.id.save, R.id.submit, R.id.delete_layout})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.save:
                    //暂存
                    if (this.eventsReportedLookBean.getEnumOrderStatus() != EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT) {
                        eventsReportedLookBean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS);
                    }
                    submitForm();
                    break;
                case R.id.submit:
                    //提交
                    if (this.eventsReportedLookBean.getEnumOrderStatus() != EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT) {
                        if (isSelfHandle == 0) {
                            eventsReportedLookBean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.EVALUATE);
                        } else {
                            eventsReportedLookBean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.PUT_ON_RECORD);
                        }
                    }
                    submitForm();
                    break;
                case R.id.delete_layout:
                    //删除
                    new CommonDialog.Builder(this).setTitle("提示")
                            .setContent("删除后,无法恢复")
                            .setPositiveButton("删除", (view, commonDialog) -> {
                                mPresenter.deleteEvent(custId, eventsReportedLookBean.getEventFormTypeId());
                            }).create().show();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitForm() {
        try {
            if (StringUtil.isNullString(eventsReportedLookBean.getEventFormTypeId())) {
                ArmsUtils.makeText("请选择事件分类");
                return;
            }
            if (eventsReportedLookBean.getEnumEventLevel() < 0) {
                ArmsUtils.makeText("请选择事件等级");
                return;
            }
            if (StringUtil.isNullString(eventsReportedLookBean.getPointsInJson())) {
                ArmsUtils.makeText("请选择坐标点");
                return;
            }
            if (StringUtil.isNullString(addressName.getText().toString())) {
                ArmsUtils.makeText("请输入事件地址");
                return;
            }
            eventsReportedLookBean.setEnumEventProcType(isSelfHandle);
            eventsReportedLookBean.setRecordUrl(frontRecordFilUrl);
            //处置前图片
            if (frontHandleImageAdapter != null && frontHandleImageAdapter.getData().size() > 0) {
                List<InformationItemPictureBean> data = frontHandleImageAdapter.getData();
                List<String> dd = new ArrayList<>();
                for (InformationItemPictureBean datum : data) {
                    if (StringUtil.isNotNullString(datum.getImageUrl())) {
                        dd.add(datum.getImageUrl());
                    }
                }
                eventsReportedLookBean.setImgsInJson(gson.toJson(dd));
            }
            //处置前video
            if (frontHandleVideoAdapter != null && frontHandleVideoAdapter.getData().size() > 0) {
                List<InformationItemPictureBean> data = frontHandleVideoAdapter.getData();
                List<String> dd = new ArrayList<>();
                for (InformationItemPictureBean datum : data) {
                    if (StringUtil.isNotNullString(datum.getImageUrl())) {
                        dd.add(datum.getImageUrl());
                    }
                }
                eventsReportedLookBean.setVideoUrl(gson.toJson(dd));
            }
            if (0 == isSelfHandle) {
                //处置后image
                if (afterHandleImageAdapter != null && afterHandleImageAdapter.getData().size() > 0) {
                    List<InformationItemPictureBean> data = afterHandleImageAdapter.getData();
                    List<String> dd = new ArrayList<>();
                    for (InformationItemPictureBean datum : data) {
                        if (StringUtil.isNotNullString(datum.getImageUrl())) {
                            dd.add(datum.getImageUrl());
                        }
                    }
                    eventsReportedLookBean.setAfterProceedImgsInJson(gson.toJson(dd));
                }
                //处置后video
                if (afterHandleVideoAdapter != null && afterHandleVideoAdapter.getData().size() > 0) {
                    List<InformationItemPictureBean> data = afterHandleVideoAdapter.getData();
                    List<String> dd = new ArrayList<>();
                    for (InformationItemPictureBean datum : data) {
                        if (StringUtil.isNotNullString(datum.getImageUrl())) {
                            dd.add(datum.getImageUrl());
                        }
                    }
                    eventsReportedLookBean.setAfterProceedVideoUrl(gson.toJson(dd));
                }
                eventsReportedLookBean.setAfterProceedRecordUrl(afterRecordFilUrl);
            }
            eventsReportedLookBean.setEventAddress(addressName.getText().toString().trim());
            List<InformationDynamicFormSelectBean.StructureInJsonBean> data = mAdapter.getData();
            for (InformationDynamicFormSelectBean.StructureInJsonBean bean : data) {
                if (StringUtil.isNotNullString(bean.getDefaultVal()) && bean.getDefaultVal().contains("\\")) {
                    bean.setDefaultVal(bean.getDefaultVal().replace("\\", ""));
                }
                if (bean.isIsRequired() && StringUtil.isNullString(bean.getDefaultVal())) {
                    ArmsUtils.makeText("请填写或选择 " + bean.getName() + " 信息");
                    return;
                } else {
                    //配合夏仲翰 反人类写法
                    if (bean.getPropertyName().trim().equals("name")) {
                        eventsReportedLookBean.setName(bean.getDefaultVal());
                    } else if (bean.getPropertyName().trim().equals("memo")) {
                        eventsReportedLookBean.setMemo(bean.getDefaultVal());
                    } else if (bean.getPropertyName().trim().equals("addr")) {
                        eventsReportedLookBean.setAddr(bean.getDefaultVal());
                    } else if (bean.getPropertyName().trim().equals("cover")) {
                        eventsReportedLookBean.setCover(bean.getDefaultVal());
                    }
                }
            }
            InformationDynamicFormSelectBean dynamicFormSelectBean = new InformationDynamicFormSelectBean();
            dynamicFormSelectBean.setStructureInJson((ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean>) data);
            eventsReportedLookBean.setStructureInJson(gson.toJson(dynamicFormSelectBean));
            eventsReportedLookBean.setUserId(UserInfoUtil.getUserInfo().getUid());
            if (eventsReportedLookBean.getEnumOrderStatus() == EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS) {
                if (status == EventBusConstant.ALERT) {
                    // 暂存的id 和 recordid传值
                    eventsReportedLookBean.setId(eventsReportedLookBean.getId());
                    mPresenter.alterSubmit(eventsReportedLookBean);
                } else {
                    mPresenter.submit(eventsReportedLookBean);
                }
            } else if (eventsReportedLookBean.getEnumOrderStatus() == EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT) {
                eventsReportedLookBean.setOldId(eventsReportedLookBean.getId());
                eventsReportedLookBean.setId("");
                eventsReportedLookBean.setSaveSubmit(false);
                eventsReportedLookBean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.PUT_ON_RECORD);
                mPresenter.submit(eventsReportedLookBean);
            } else if (eventsReportedLookBean.getEnumOrderStatus() ==
                    EventsReportedBean.ListBean.EventsReportedEnumNewBean.PUT_ON_RECORD
                    || eventsReportedLookBean.getEnumOrderStatus() ==
                    EventsReportedBean.ListBean.EventsReportedEnumNewBean.EVALUATE) {
                new CommonDialog.Builder(EventsReportedCrudActivity.this).setTitle("提示")
                        .setContent("确定上报事件(" + eventsReportedLookBean.getName() + ")")
                        .setPositiveButton(getString(R.string.up_tell), (v, commonDialog) -> {
                            eventsReportedLookBean.setSaveSubmit(false);
                            if (status == EventBusConstant.ALERT) {
                                eventsReportedLookBean.setId(eventsReportedLookBean.getRecordId());
                                mPresenter.alterSubmit(eventsReportedLookBean);
                            } else {
                                mPresenter.submit(eventsReportedLookBean);
                            }
                        }).create().show();
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
                case R.id.select_event_sort:
                    int limit = EventsReportedSortActivity.ALL;
                    if (RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT.equals(source)) {
                        limit = EventsReportedSortActivity.URGENCY;
                    }
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDSORTACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .withInt(EventsReportedSortActivity.LIMIT, limit)
                            .navigation();
                    break;
                case R.id.select_event_priority:
                    EventPriorityPopup eventPriorityPopup = new EventPriorityPopup(this);
                    eventPriorityPopup.setOnLevelItemClick(eventPriorityBean -> {
                        try {
                            priorityImage.setImageResource(eventPriorityBean.getdrawableId());
                            priorityValue.setTextColor(ArmsUtils.getColor(this, eventPriorityBean.getColorId()));
                            priorityValue.setText(eventPriorityBean.getPriority());
                            eventsReportedLookBean.setEnumEventLevel(eventPriorityBean.getmPriorityIndex());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    eventPriorityPopup.showPopupWindow();
                    break;
                case R.id.map_location:
                    if (MapControlUtils.isTiandiTu) {
                        ARouter.getInstance().build(RouterHub.TIANDITU_APP_COMMONWEBVIEWACTIVITY)
                                .withString(ARouerConstant.TITLE, " 地图")
                                .withString(MapActivity.POINTS_IN_JSON, pointsInJson)
                                .withString(MapActivity.LONANDLAT, eventsReportedLookBean.getPointsInJson())
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                                .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, addressCanChanged)
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_MAPACTIVITY)
                                .withString(MapActivity.POINTS_IN_JSON, pointsInJson)
                                .withString(MapActivity.LONANDLAT, eventsReportedLookBean.getPointsInJson())
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                                .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, addressCanChanged)
                                .navigation();
                    }
                    break;
                case R.id.record_view:
                    lastClickViewId = R.id.record_view;
                    mPresenter.getPermissionRecord();
                    break;
                case R.id.after_record_view:
                    lastClickViewId = R.id.after_record_view;
                    mPresenter.getPermissionRecord();
                    break;
                case R.id.image_:
                    lastClickViewId = R.id.image_;
                    if (StringUtil.isNotNullString(frontRecordFilUrl)) {
                        if (start) {
                            start = false;
                            image.setImageResource(R.drawable.record_off);
                            stopPlaying();
                        } else {
                            if (afterStart) {
                                afterStart = false;
                                afterImage.setImageResource(R.drawable.record_off);
                                stopPlaying();
                            }
                            start = true;
                            image.setImageResource(R.drawable.record_on);
                            startPlaying(frontRecordFilUrl);
                        }
                    } else {
                        ArmsUtils.makeText("未发现录音文件，请重新上传");
                        image.setVisibility(View.GONE);
                        deleteX.setVisibility(View.GONE);
                    }
                    break;
                case R.id.after_image_:
                    lastClickViewId = R.id.after_image_;
                    if (StringUtil.isNotNullString(afterRecordFilUrl)) {
                        if (afterStart) {
                            afterStart = false;
                            afterImage.setImageResource(R.drawable.record_off);
                            stopPlaying();
                        } else {
                            if (start) {
                                start = false;
                                image.setImageResource(R.drawable.record_off);
                                stopPlaying();
                            }
                            afterStart = true;
                            afterImage.setImageResource(R.drawable.record_on);
                            startPlaying(afterRecordFilUrl);
                        }
                    } else {
                        ArmsUtils.makeText("未发现录音文件，请重新上传");
                        afterImage.setVisibility(View.GONE);
                        afterDeleteX.setVisibility(View.GONE);
                    }
                    break;
                case R.id.delete_x:
                    frontRecordFilUrl = "";
                    image.setVisibility(View.GONE);
                    deleteX.setVisibility(View.GONE);
                    break;
                case R.id.after_delete_x:
                    afterRecordFilUrl = "";
                    afterImage.setVisibility(View.GONE);
                    afterDeleteX.setVisibility(View.GONE);
                    break;
                case R.id.standard_address_layout:
                    ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .navigation();
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
                if (lastClickViewId == R.id.image_) {
                    start = false;
                    image.setImageResource(R.drawable.record_off);
                } else if (lastClickViewId == R.id.after_image_) {
                    afterStart = false;
                    afterImage.setImageResource(R.drawable.record_off);
                }
                player.stop();
                player.release();
            });
        } catch (IOException e) {
            Timber.e("prepare() failed");
        }
    }

    /**
     * 停止
     */
    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Subscriber(tag = RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                //事件分类
                case EventBusConstant.UPDATE_UP_EVENT_SORT:
                    if (eventBusBean.getData() instanceof EventsReportedSortBean.ListBean) {
                        EventsReportedSortBean.ListBean bean =
                                (EventsReportedSortBean.ListBean) eventBusBean.getData();
                        if (bean == null) {
                            ArmsUtils.makeText("获取事件分类信息失败");
                            return;
                        }
                        String dataStructureInJson = bean.getStructureInJson();
                        if (StringUtil.isNotNullString(dataStructureInJson)) {
                            InformationDynamicFormSelectBean dynamicFormSelectBean = gson.fromJson(dataStructureInJson, InformationDynamicFormSelectBean.class);
                            ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJson = dynamicFormSelectBean.getStructureInJson();
                            if (structureInJson != null && structureInJson.size() > 0) {
                                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> iterator = structureInJson.iterator();
                                while (iterator.hasNext()) {
                                    try {
                                        InformationDynamicFormSelectBean.StructureInJsonBean next = iterator.next();
                                        if (next.isDisabled()) {
                                            iterator.remove();
                                        }
                                        if ("name".equals(next.getPropertyName())) {
                                            next.setDefaultVal(bean.getName());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                mAdapter.setNewData(structureInJson);
                            }
                        }
                        eventsReportedLookBean.setElementTypeName(bean.getName());
                        eventsReportedLookBean.setEventFormTypeId(bean.getId());
                        eventsReportedLookBean.setEventMemo(bean.getMemo());
                        eventSortValue.setText(bean.getName());
                        if (StringUtil.isNullString(bean.getMemo())) {
                            eventSortMemo.setVisibility(View.GONE);
                        } else {
                            eventSortMemo.setVisibility(View.VISIBLE);
                            eventSortMemo.setText("描述：" + bean.getMemo());
                        }
                        //设置事件等级默认值
                        priorityImage.setImageResource(EventPriorityBean.drawableId[EventPriorityBean.COMMON]);
                        priorityValue.setTextColor(ArmsUtils.getColor(this, EventPriorityBean.colorId[EventPriorityBean.COMMON]));
                        priorityValue.setText(EventPriorityBean.priority[EventPriorityBean.COMMON]);
                        eventsReportedLookBean.setEnumEventLevel(EventPriorityBean.COMMON);
                    }
                    break;
                //上级地址
                case EventBusConstant.UPDATE_UP_ADDRESS:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                        StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                        eventsReportedLookBean.setStandardAddressId(bean.getId());
                        eventsReportedLookBean.setStandardAddressName(bean.getFullPath());
                        standardAddressText.setText(bean.getFullPath());
                    }
                    break;
                //定位
                case EventBusConstant.UPDATE_UP_LOCATION:
                    if (eventBusBean.getData() instanceof StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) {
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean) eventBusBean.getData();
                        if (pathBean.getLng() > 0 && pathBean.getLat() > 0) {
                            eventsReportedLookBean.setPointsInJson(gson.toJson(pathBean));
                            mapLocationValue.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
                        }
                    }
                    break;

                //定位
                case EventBusConstant.SELF_LOCALIZATION:
                    String data = (String) eventBusBean.getData();
                    addressName.setText(data);

                    break;
                default:
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void setData(Object eventsReportedLookBean) {
        try {
            String s = gson.toJson(eventsReportedLookBean);
            this.eventsReportedLookBean = gson.fromJson(s, EventsReportedLookBean.class);
            JSONObject jsonObject = new JSONObject(s);
            eventSortValue.setText(this.eventsReportedLookBean.getEventTypeName());
            if (StringUtil.isNullString(this.eventsReportedLookBean.getEventMemo())) {
                eventSortMemo.setVisibility(View.GONE);
            } else {
                eventSortMemo.setVisibility(View.VISIBLE);
                eventSortMemo.setText("描述：" + this.eventsReportedLookBean.getEventMemo());
            }
            addressName.setText(this.eventsReportedLookBean.getEventAddress());
            EventPriorityBean eventPriorityBean = new EventPriorityBean(this.eventsReportedLookBean.getEnumEventLevel());
            priorityImage.setImageResource(eventPriorityBean.getdrawableId());
            priorityValue.setTextColor(ArmsUtils.getColor(this, eventPriorityBean.getColorId()));
            priorityValue.setText(eventPriorityBean.getPriority());
            try {
                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                        gson.fromJson(this.eventsReportedLookBean.getPointsInJson(),
                                StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                mapLocationValue.setText(String.format(getResources().getString(R.string.lon_lat), pathBean.getLng(), pathBean.getLat()));
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
            standardAddressText.setText(this.eventsReportedLookBean.getFullPath());
            String dataStructureInJson = this.eventsReportedLookBean.getStructureInJson();
            if (StringUtil.isNotNullString(dataStructureInJson)) {
                InformationDynamicFormSelectBean dynamicFormSelectBean = gson.fromJson(dataStructureInJson, InformationDynamicFormSelectBean.class);
                ArrayList<InformationDynamicFormSelectBean.StructureInJsonBean> structureInJson = dynamicFormSelectBean.getStructureInJson();
                for (InformationDynamicFormSelectBean.StructureInJsonBean structure : structureInJson) {
                    if (StringUtil.isNotNullString(structure.getPropertyName())) {
                        structure.setDefaultVal(jsonObject.getString(structure.getPropertyName()));
                    }
                }
                Iterator<InformationDynamicFormSelectBean.StructureInJsonBean> iterator = structureInJson.iterator();

                while (iterator.hasNext()) {
                    try {
                        InformationDynamicFormSelectBean.StructureInJsonBean next = iterator.next();
                        if (next.isDisabled()) {
                            iterator.remove();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (structureInJson.size() > 0) {
                    mAdapter.setNewData(structureInJson);
                }
            }
            if (this.eventsReportedLookBean.getEnumEventProcType() == 0) {
                isSelfHandle = 0;
                selfHandle.setImageResource(R.drawable.s_open);
                afterLayout.setVisibility(View.VISIBLE);
            } else {
                isSelfHandle = 1;
                selfHandle.setImageResource(R.drawable.s_close);
                afterLayout.setVisibility(View.GONE);
            }
            setFrontAdapterData();
            if (this.eventsReportedLookBean.getEnumOrderStatus()
                    == EventsReportedBean.ListBean.EventsReportedEnumNewBean.EXIT) {
                save.setVisibility(View.GONE);
            } else {
                save.setText("暂存");
            }

            if (this.eventsReportedLookBean.getEnumOrderStatus()
                    == EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS) {
                selectEventSort.setEnabled(false);
            } else {

                selectEventSort.setEnabled(true);
            }


            if (StringUtil.isNotNullString(this.eventsReportedLookBean.getRecordUrl())) {
                frontRecordFilUrl = this.eventsReportedLookBean.getRecordUrl();
                image.setImageResource(R.drawable.record_off);
                image.setVisibility(View.VISIBLE);
                deleteX.setVisibility(View.VISIBLE);
            }
            if (StringUtil.isNotNullString(this.eventsReportedLookBean.getAfterProceedRecordUrl())) {
                afterRecordFilUrl = this.eventsReportedLookBean.getAfterProceedRecordUrl();
                afterImage.setImageResource(R.drawable.record_off);
                afterImage.setVisibility(View.VISIBLE);
                afterDeleteX.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置 处置前 数据
     */
    private void setFrontAdapterData() {
        //处置前的 图片 数据
        try {
            String fImgInJson = eventsReportedLookBean.getImgsInJson();
            List<String> fImgList = gson.fromJson(fImgInJson, List.class);
            List<InformationItemPictureBean> fImgll = frontHandleImageAdapter.getData();
            for (String s : fImgList) {
                fImgll.add(new InformationItemPictureBean(s));
            }
            frontHandleImageAdapter.setNewData(fImgll);
            //处置前的 video 数据
            String fVideoInJson = eventsReportedLookBean.getVideoUrl();
            List<String> fVideolist = gson.fromJson(fVideoInJson, List.class);
            List<InformationItemPictureBean> fVideoll = frontHandleVideoAdapter.getData();
            for (String s : fVideolist) {
                fVideoll.add(new InformationItemPictureBean(s));
            }
            frontHandleVideoAdapter.setNewData(fVideoll);
            //自处理处置后数据
            if (this.eventsReportedLookBean.getEnumEventProcType() == 0) {
                //处置后的 image 数据
                String aImgInJson = eventsReportedLookBean.getAfterProceedImgsInJson();
                List<String> aImgList = gson.fromJson(aImgInJson, List.class);
                List<InformationItemPictureBean> aImgll = afterHandleImageAdapter.getData();
                for (String s : aImgList) {
                    aImgll.add(new InformationItemPictureBean(s));
                }
                afterHandleImageAdapter.setNewData(aImgll);
                //处置后的 video 数据
                String aVideoInJson = eventsReportedLookBean.getAfterProceedVideoUrl();
                List<String> aVideolist = gson.fromJson(aVideoInJson, List.class);
                List<InformationItemPictureBean> aVideoll = afterHandleVideoAdapter.getData();
                for (String s : aVideolist) {
                    aVideoll.add(new InformationItemPictureBean(s));
                }
                afterHandleVideoAdapter.setNewData(aVideoll);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置 时间选择器 时间
     *
     * @param bean
     * @param pos
     */
    private void showDateTimeSelector(InformationDynamicFormSelectBean.StructureInJsonBean bean, int pos) {
        /**
         * 显示时间选择器
         */
        try {
            timePickerView = PickerViewUtil.selectPickerTime(this, Constant.YMDHM, (date, v) -> {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMDHM, Locale.CHINA);
                    bean.setDefaultVal(format.format(date));
                    mAdapter.setData(pos, bean);
                    mAdapter.notifyItemChanged(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            timePickerView.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图片上传成功  跟新界面
     *
     * @param uploadFileRsponseBeans
     */
    @Override
    public void setUpdatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans, boolean isInDynamic) {
        try {
            //动态表单的 图片更新

            if (isInDynamic) {
                ArrayList<String> strings = new ArrayList<>();
                for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                    strings.add(uploadFileRsponseBean.getUrl());
                }
                if (adapterPosition >= 0 && strings.size() > 0) {
                    InformationDynamicFormSelectBean.StructureInJsonBean item = mAdapter.getItem(adapterPosition - 1);
                    if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SinglePic)) {
                        item.setDefaultVal(strings.get(0));
                    } else if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.MultiPics)
                            || item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SingleVideo)) {
                        if (StringUtil.isNotNullString(item.getDefaultVal())) {
                            String defaultVal = item.getDefaultVal();
                            if (defaultVal.contains("[")) {
                                ArrayList<String> arrayList = gson.fromJson(defaultVal, ArrayList.class);
                                strings.addAll(arrayList);
                            }
                        }
                        item.setDefaultVal(gson.toJson(strings));
                    }
                    mAdapter.setData(adapterPosition - 1, item);
                }
                //列表的更新
            } else {
                if (currentAdapter != null) {
                    List<InformationItemPictureBean> list = currentAdapter.getData();
                    for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                        list.add(new InformationItemPictureBean(uploadFileRsponseBean.getUrl()));
                    }
                    currentAdapter.setNewData(list);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 去相册
     */
    @Override
    public void goToPhotoAlbum(int adapterPosition, int max, String type, boolean isInDynamic) {
        try {
            int pictureMimeType;
            boolean previewVideo;
            if (InformationDynamicFormSelectBean.SingleVideo.equals(type)) {
                pictureMimeType = PictureMimeType.ofVideo();
                previewVideo = true;
            } else {
                pictureMimeType = PictureMimeType.ofImage();
                previewVideo = false;
            }
            EventsReportedCrudActivity.this.adapterPosition = adapterPosition;
            PictureSelectorUtils.gotoPhoto(this, pictureMimeType,
                    max, previewVideo, false, new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            try {
                                List<String> strings = new ArrayList<>();
                                for (LocalMedia media : result) {
                                    if (StringUtil.isNotNullString(media.getCompressPath())) {
                                        strings.add(media.getCompressPath());
                                    } else {
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                            strings.add(PictureFileUtils.getPath(EventsReportedCrudActivity.this, Uri.parse(media.getPath())));
                                        } else {
                                            if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                                strings.add(media.getPath());
                                            }
                                        }
                                    }
                                }
                                if (strings.size() > 0) {
                                    mPresenter.upLoadFile(eventsReportedLookBean.getId(), strings, isInDynamic);
                                } else {
                                    ArmsUtils.makeText("未发现文件");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void next() {
        EventBus.getDefault().post(new EventBusBean(EventBusConstant.SELECT), RouterHub.APP_EVENTSREPORTEDACTIVITY);
        finish();
    }

    /**
     * 上传 语音文件成功
     *
     * @param uploadFileRsponseBeans uploadFileRsponseBeans
     */
    @Override
    public void setRecordInfo(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            UploadFileRsponseBean uploadFileRsponseBean = uploadFileRsponseBeans.get(0);
            String url = uploadFileRsponseBean.getUrl();
            if (StringUtil.isNotNullString(url)) {
                if (lastClickViewId == R.id.record_view) {
                    frontRecordFilUrl = url;
                    image.setImageResource(R.drawable.record_off);
                    image.setVisibility(View.VISIBLE);
                    deleteX.setVisibility(View.VISIBLE);
                } else if (lastClickViewId == R.id.after_record_view) {
                    afterRecordFilUrl = url;
                    afterImage.setImageResource(R.drawable.record_off);
                    afterImage.setVisibility(View.VISIBLE);
                    afterDeleteX.setVisibility(View.VISIBLE);
                }
            } else {
                if (lastClickViewId == R.id.record_view) {
                    image.setVisibility(View.GONE);
                    deleteX.setVisibility(View.GONE);
                } else if (lastClickViewId == R.id.after_record_view) {
                    afterImage.setVisibility(View.GONE);
                    afterDeleteX.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (player != null) {
                player.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}