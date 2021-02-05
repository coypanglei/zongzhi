package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.lxj.xpopup.XPopup;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInformationTypeSecondComponent;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationTypeSecondPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeSecondAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.SearchPopup;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * 人地事物 信息采集 查看  第二级  界面
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
public class InformationTypeSecondActivity extends BaseActivity<InformationTypeSecondPresenter> implements InformationTypeSecondContract.View {

    /**
     * title
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title;
    /**
     * 具体 的  某个 类型id -ElementTypeId  主键查询 elementTypeId  eg 元素 人 -  类型 残疾人(@ID)
     */
    @Autowired(name = ARouerConstant.ID)
    String elementTypeId;

    /**
     * 上个界面标识
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * 日期类型
     */
    @Autowired(name = ARouerConstant.DATE_TYPE)
    int dateType = -1;
    /**
     * 动态布局json  下个界面要用
     */
    @Autowired(name = InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON)
    String dynamicFormJson;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.select_grid)
    TextView selectGrid;
    @BindView(R.id.select_standard_address)
    TextView selectStandardAddress;
    @BindView(R.id.number)
    TextView number;

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    HorizontalDividerItemDecoration itemDecoration;
    @Inject
    InformationTypeSecondAdapter mAdapter;
    @Inject
    Gson gson;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    /**
     * 记录点击进入详情的position
     */
    private int checkedPosition = -1;
    private CountDownTimer timer;
    @Inject
    AppManager mAppManager;

    private String mKeyWord = "";
    private SearchPopup searchPopup;
    /**
     * 选择网格的弹框
     */
    private static final int GRID = 0;
    /**
     * 选择标准地址
     */
    private static final int STANDARD_ADDRESS = 1;

    /**
     * 行为确定  是 请求的网格GRID   还是标准地址 STANDARD_ADDRESS
     */
    private int clickItem = -1;
    /**
     * 网格信息
     */
    private String departmentId;
    private String departmentName;

    /**
     * 标准地址信息
     */
    private String standardAddressId;
    /**
     * 标准地址名称
     */
    private String standardAddressName;
    /**
     * 是否是 第一次 获取网格信息
     */
    private boolean isFirstGetGrid = true;

    List<BasicInformationBean.RecordsBean> list = new ArrayList<>();

    private CommonCollectBean bean = new CommonCollectBean();

    private Map<String, String> addMap = new HashMap<>();


    private int firstOne = 1;
    private int mCount;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInformationTypeSecondComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_information_type_second;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle(title);
            rightBtnText.setText("高级查询");
            initRecycler();
            AccessControlUtil.controlByLevelCommunity(add);
            //从任务跳过来  时   隐藏add
            if (RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT.equals(source)) {
                add.setVisibility(View.GONE);
            }
            if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() <= StandardAddressStairBean.GRIDDING) {
                departmentId = UserInfoUtil.getUserInfo().getDepartmentId();
                departmentId = UserInfoUtil.getUserInfo().getDepartmentId();
                departmentName = UserInfoUtil.getUserInfo().getDepartmentName();
                selectGrid.setText(StringUtil.setText(departmentName));
                mPresenter.getGridResource(true, false, departmentId,
                        standardAddressId, elementTypeId, mKeyWord, addMap, source);
            } else {
                isFirstGetGrid = true;
                clickItem = GRID;
                mPresenter.getGridList(true, false, "");
            }
            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable newText) {
                    try {
                        if (!newText.equals(mKeyWord)) {
                            if (timer != null) {
                                timer.cancel();
                            }
                            timer = new CountDownTimer(1000, 1000) {
                                @SuppressLint("StringFormatMatches")
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    mKeyWord = newText.toString();
                                    mPresenter.getGridResource(true, false,
                                            departmentId, standardAddressId, elementTypeId, mKeyWord, addMap, source);
                                }
                            }.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 回调
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.ADD:
                case EventBusConstant.ALERT:
                    assert mPresenter != null;
                    mPresenter.getGridResource(true, false,
                            departmentId, standardAddressId, elementTypeId, mKeyWord, addMap, source);
                    break;
                case EventBusConstant.DELETE:
                    if (mCount > 0) {
                        number.setText(String.format(getResources().getString(R.string.data_count), mCount -= 1));
                    }
                    mAdapter.remove(checkedPosition);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 recycler
     */
    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                try {
                    addMap = new HashMap<>();
                    mPresenter.getGridResource(true, false,
                            departmentId, standardAddressId, elementTypeId, mKeyWord, addMap, source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    try {
                        swipeRefreshLayout.setEnabled(recyclerView.getChildCount() == 0 || recyclerView.getChildAt(0).getTop() >= 0);
                        super.onScrollStateChanged(recyclerView, newState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(itemDecoration);
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationTypeOneSecondBean.ElementListBean) {
                        InformationTypeOneSecondBean.ElementListBean listBean = (InformationTypeOneSecondBean.ElementListBean) adapter.getItem(position);
                        if (view.getId() == R.id.input) {
                            //从工作清单 跳转过来的  执行这些操作
                            if (InformationCollectionActivity.mType == InformationCollectionActivity.GATHER) {
                                ARouter.getInstance().build(RouterHub.APP_INFORMATIONDYNAMICFORMSELECTACTIVITY)
                                        .withString(ARouerConstant.ID, listBean.getId())
                                        .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, dynamicFormJson)
                                        .withString(ARouerConstant.TITLE, StringUtil.setText(listBean.getName()))
                                        .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                                        .withString(InformationDynamicFormSelectActivity.TYPE_ID, listBean.getElementTypeId())
                                        .navigation();
                                checkedPosition = position;
                            } else if (RouterHub.APP_TASKLISTACTIVITY.equals(InformationCollectionActivity.getmSource())
                                    || RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT.
                                    equals(InformationCollectionActivity.getmSource())) {
                                mAppManager.killActivity(InformationTypeFirstActivity.class);
                                mAppManager.killActivity(InformationCollectionActivity.class);
                                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_JUMP, listBean), InformationCollectionActivity.getmSource());
                                killMyself();
                            } else if (RouterHub.APP_TASKLISTACTIVITY.equals(source)
                                    || RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT.equals(source)) {
                                EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_JUMP, listBean), source);
                                killMyself();
                            } else {
                                mAppManager.killActivity(InformationCollectionActivity.class);
                                mAppManager.killActivity(InformationTypeFirstActivity.class);
                                EventBus.getDefault().post(new EventBusBean(0, "", listBean), RouterHub.APP_TOURVISITACTIVITY);
                                killMyself();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                try {
                    mPresenter.getGridResource(false, true, departmentId,
                            standardAddressId, elementTypeId, mKeyWord, addMap, source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, recyclerView);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (searchPopup != null && searchPopup.isShowing()) {
            searchPopup.setLoadMoreViewHide(b);
        } else {
            if (b) {
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.loadMoreComplete();
            }
        }
    }


    @OnClick({R.id.select_grid_layout, R.id.select_tier_layout, R.id.add, R.id.right_btn})
    public void onClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.add:
                    mPresenter.getGetNewGuidForApp();
                    break;
                case R.id.select_grid_layout:
                    isFirstGetGrid = false;
                    clickItem = GRID;
                    mPresenter.getGridList(true, false, "");
                    break;
                case R.id.select_tier_layout:
                    isFirstGetGrid = false;
                    clickItem = STANDARD_ADDRESS;
                    mPresenter.getStandardAddressByGridId(true,
                            false, false, departmentId, "");
                    break;
                //高级搜索
                case R.id.right_btn:

                    try {
                        SearchDialogPopup dialogPopup = new SearchDialogPopup(this, bean);
                        dialogPopup.setConfirmBtnClick((map, list) -> {
                            addMap = map;
                            bean.setList(list);
                            mPresenter.getGridResource(true, false,
                                    departmentId, standardAddressId,
                                    elementTypeId, mKeyWord, addMap, source);
                        });
                        new XPopup.Builder(getContext())
                                .moveUpToKeyboard(false)
                                .enableDrag(false)
                                .isDestroyOnDismiss(true)
//                        .isThreeDrag(true)
                                .asCustom(dialogPopup)
                                .show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    KeybordUtil.hideKeyboard(this);
                    break;
            }
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
    public void killMyself() {
        finish();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setData(List<InformationTypeOneSecondBean.ElementListBean> informationTypeSecondBeans,
                        boolean isLoadMore, int count, int type) {
        try {
            mAdapter.setType(type);
            this.mCount = count;
            if (isLoadMore) {
                mAdapter.addData(informationTypeSecondBeans);
            } else {
                mAdapter.setNewData(informationTypeSecondBeans);
                number.setText(String.format(getResources().getString(R.string.data_count), count));
            }
            /**
             *  第一次生成界面
             */
            if (firstOne == 1) {
                //排序构造数据
                List<NameAndIdBean> sortList = new ArrayList<>();
                switch (InformationCollectionActivity.mType) {
                    // 走访
                    case 1:
                        sortList.add(new NameAndIdBean("0", "更新时间"));
                        sortList.add(new NameAndIdBean("1", "走访时间"));
//                        list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "开始时间", "DTT", "请选择开始时间"));
//                        list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "结束时间", "DTF", "请选择结束时间"));
                        BasicInformationBean.RecordsBean selectBean = new BasicInformationBean.RecordsBean(SELECT_ITEM, "默认排序", "SortBy", "请选择排序方式");
                        selectBean.setValue(sortList.get(0).getName());
                        selectBean.setSelectValue(sortList.get(0).getId());
                        selectBean.setOptionlist(sortList);
                        list.add(selectBean);
                        break;
                    //巡检
                    case 2:
                        sortList.add(new NameAndIdBean("0", "更新时间"));
                        sortList.add(new NameAndIdBean("2", "巡检时间"));
//                        list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "开始时间", "DTT", "请选择开始时间"));
//                        list.add(new BasicInformationBean.RecordsBean(DATA_ITEM, "结束时间", "DTF", "请选择结束时间"));
                        BasicInformationBean.RecordsBean selectBeanTwo = new BasicInformationBean.RecordsBean(SELECT_ITEM, "默认排序", "SortBy", "请选择排序方式");
                        selectBeanTwo.setValue(sortList.get(0).getName());
                        selectBeanTwo.setSelectValue(sortList.get(0).getId());
                        selectBeanTwo.setOptionlist(sortList);
                        list.add(selectBeanTwo);
                        break;
                    default:
                        break;

                }
                // 搜索内容集合
                InformationDynamicFormSelectBean informationDynamicFormSelectBean = gson.fromJson(dynamicFormJson, InformationDynamicFormSelectBean.class);


                if (ObjectUtils.isNotEmpty(informationDynamicFormSelectBean.getStructureInJson()) && informationDynamicFormSelectBean.getStructureInJson().size() > 0) {
                    for (InformationDynamicFormSelectBean.StructureInJsonBean bean : informationDynamicFormSelectBean.getStructureInJson()) {
                        // 字段为可搜索
                        if (ObjectUtils.isNotEmpty(bean) && bean.isIsQueryField()) {
                            BasicInformationBean.RecordsBean recordsBean;
                            String required = "";
//                            if (bean.isIsRequired()) {
//                                required = required + "(必填)";
//                            } else {
//                                required = "";
//                            }
                            switch (bean.getItemType()) {
                                case InformationDynamicFormSelectBean.SingleLineN:
                                case InformationDynamicFormSelectBean.MultiLineN:
                                    recordsBean = new BasicInformationBean.RecordsBean(EDIT_ITEM, bean.getName(), bean.getPropertyName(), "请填写" + bean.getName() + required, bean.isIsRequired());
                                    break;
                                case InformationDynamicFormSelectBean.NumberN:
                                    recordsBean = new BasicInformationBean.RecordsBean(NUMBER_EDIT_ITEM, bean.getName(), bean.getPropertyName(), "请填写" + bean.getName() + required, bean.isIsRequired());
                                    break;
                                case InformationDynamicFormSelectBean.DateTimeN:
                                    recordsBean = new BasicInformationBean.RecordsBean(DATA_ITEM, bean.getName(), bean.getPropertyName(), "请填写" + bean.getName() + required, bean.isIsRequired());
                                    break;
                                case InformationDynamicFormSelectBean.OptionN:
                                case InformationDynamicFormSelectBean.DropdownListN:
                                    //搜索内容构造数据
                                    List<NameAndIdBean> searchContent = new ArrayList<>();
                                    for (String option : bean.getOption()) {
                                        searchContent.add(new NameAndIdBean(option, option));
                                    }
                                    recordsBean = new BasicInformationBean.RecordsBean(SELECT_ITEM, bean.getName(), bean.getPropertyName(), "请填写" + bean.getName() + required, bean.isIsRequired());
                                    recordsBean.setOptionlist(searchContent);
                                    break;
                                default:
                                    recordsBean = new BasicInformationBean.RecordsBean(EDIT_ITEM, bean.getName(), bean.getPropertyName(), "请填写" + bean.getName() + required, bean.isIsRequired());
                                    break;
                            }
                            list.add(recordsBean);
                        }
                    }
                }


                bean.setList(list);
                firstOne++;
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建 具体人地事物信息
     *
     * @param id 新建时后台给的id
     */
    @Override
    public void setGotoNewGuid(String id) {
        try {
            InformationDetailBean bean = new InformationDetailBean();
            bean.setDepartmentId(departmentId);
            bean.setUserId(UserInfoUtil.getUserInfo().getUid());
            bean.setElementTypeId(elementTypeId);
            bean.setId(id);
            bean.setElementId(id);
            InformationDynamicFormSelectBean informationDynamicFormSelectBean = gson.fromJson(dynamicFormJson, InformationDynamicFormSelectBean.class);
            ARouter.getInstance()
                    .build(RouterHub.APP_INFORMATIONDYNAMICFORMAAACTIVITY)
                    .withParcelable(InformationDynamicFormCrudActivity.LIST, informationDynamicFormSelectBean)
                    .withInt(InformationDynamicFormCrudActivity.TYPE_KEY, EventBusConstant.ADD)
                    .withString(ARouerConstant.TITLE, "新增")
                    .withString(ARouerConstant.DEPARTMENT_ID, departmentId)
                    .withString(InformationDynamicFormCrudActivity.STANDARD_ADDRESS_NAME, standardAddressName)
                    .withString(InformationDynamicFormCrudActivity.STANDARD_ADDRESS_ID, standardAddressId)
                    .withParcelable(InformationDynamicFormCrudActivity.INFORMATIONDETAILBEAN, bean)
                    .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPopup(List<? extends BaseSearchPopupBean> list, boolean isLoadMore) {
        try {
            if (list != null && list.size() > 0) {
                switch (clickItem) {
                    case GRID:
                        if (isFirstGetGrid) {
                            isFirstGetGrid = false;
                            if (list == null || list.size() <= 0) {
                                ArmsUtils.makeText("获取信息失败");
                                return;
                            }
                            departmentId = list.get(0).getId();
                            departmentName = list.get(0).getName();
                            selectGrid.setText(StringUtil.setText(departmentName));
                            mPresenter.getGridResource(true, false,
                                    departmentId, standardAddressId, elementTypeId, "", addMap, source);
                            return;
                        }
                        break;
                    case STANDARD_ADDRESS:
                        break;
                    default:
                }
                if (searchPopup == null) {
                    searchPopup = new SearchPopup(this);
                    searchPopup.setPopupClickListener(new SearchPopup.PopupClickListener() {
                        @Override
                        public void onSearchBtnClick(String keyword) {
                            try {
                                switch (clickItem) {
                                    case GRID:
                                        mPresenter.getGridList(true, false, keyword);
                                        break;
                                    case STANDARD_ADDRESS:
                                        mPresenter.getStandardAddressByGridId(true, false,
                                                true, departmentId, keyword);
                                        break;
                                    default:
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLoadMore(String keyword) {
                            try {
                                switch (clickItem) {
                                    case GRID:
                                        mPresenter.getGridList(false, true, keyword);
                                        break;
                                    case STANDARD_ADDRESS:
                                        mPresenter.getStandardAddressByGridId(false,
                                                true, false, departmentId, keyword);
                                        break;
                                    default:
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onItemClick(BaseSearchPopupBean bean) {
                            try {
                                if (bean == null) {
                                    ArmsUtils.makeText("选中信息为空");
                                    return;
                                }
                                switch (clickItem) {
                                    case GRID:
                                        if (!bean.getId().equals(departmentId)) {
                                            departmentId = bean.getId();
                                            departmentName = bean.getName();
                                            selectGrid.setText(StringUtil.setText(departmentName));
                                            standardAddressId = "";
                                            standardAddressName = "";
                                            selectStandardAddress.setText("全部");
                                        }
                                        break;
                                    case STANDARD_ADDRESS:
                                        if (!bean.getId().equals(standardAddressId)) {
                                            standardAddressId = bean.getId();
                                            standardAddressName = bean.getName();
                                            if (bean instanceof StandardAddressStairBean.StandardAddressBean) {
                                                StandardAddressStairBean.StandardAddressBean standardAddressBean =
                                                        (StandardAddressStairBean.StandardAddressBean) bean;
                                                selectStandardAddress.setText(standardAddressBean.getFullPath());
                                                standardAddressName = standardAddressBean.getFullPath();
                                            } else {
                                                selectStandardAddress.setText(StringUtil.setText(standardAddressName));
                                            }
                                        }
                                        break;
                                    default:
                                }
                                mPresenter.getGridResource(true, false,
                                        departmentId, standardAddressId, elementTypeId, mKeyWord, addMap, source);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    });
                }
                if (!searchPopup.isShowing()) {
                    searchPopup.showPopupWindow();
                }
                searchPopup.setRecyclerViewData(list, isLoadMore, true);
            } else {
                if (!isLoadMore) {
                    ArmsUtils.makeText("未获取到您需要的信息");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (!mAppManager.activityClassIsLive(InformationCollectionActivity.class)
                    && !mAppManager.activityClassIsLive(InformationTypeFirstActivity.class)) {
                //用完 状态还原未默认
                InformationCollectionActivity.mType = InformationCollectionActivity.GATHER;
            }

            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
