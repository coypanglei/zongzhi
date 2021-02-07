package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenSecondAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenStarterAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenThreeAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import razerdp.basepopup.BasePopupWindow;
import timber.log.Timber;

/**
 * @author GK
 * @description:
 * @date :2020/10/9 13:44
 */
public class PrefectureScreenPopup extends BasePopupWindow {
    private RecyclerView starters;
    private RecyclerView second;
    private RecyclerView three;
    private RxErrorHandler mErrorHandler;
    private IView mRootView;
    private IRepositoryManager mRepositoryManager;
    private PrefectureScreenStarterAdapter startersAdapter;
    private PrefectureScreenSecondAdapter secondAdapter;
    private PrefectureScreenThreeAdapter threeAdapter;
    private TextView reset;
    private TextView goSee;
    private OnPrefectureScreenListener listener;

    public PrefectureScreenPopup(Context context, IRepositoryManager iRepositoryManager, RxErrorHandler errorHandler,
                                 IView rootView, int width) {
        super(context);
        this.mRepositoryManager = iRepositoryManager;
        this.mErrorHandler = errorHandler;
        this.mRootView = rootView;
        initCView();
        getStarters();
        setWidth(width);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent));
    }

    /**
     * 点击
     * onPrefectureScreenListener
     */
    public interface OnPrefectureScreenListener {
        /**
         * onSureClick
         */
        void onSureClick(String eventualId);
    }

    public void setListener(OnPrefectureScreenListener listener) {
        this.listener = listener;
    }

    /**
     * 获取一级列表数据
     */
    private void getStarters() {
        try {
            Observable<BaseBean<List<InformationTypeOneSecondBean.ElementTypeListBean>>> observable =
                    mRepositoryManager.obtainRetrofitService(InformationService.class).getElementType(SignUtil.paramSign(null));
            NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, elementTypeListBeans -> {
                setStartersAdapter(elementTypeListBeans);
                if (elementTypeListBeans != null && elementTypeListBeans.size() > 0) {
                    getSecond(elementTypeListBeans.get(0));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一级列表 adapter
     *
     * @param elementTypeListBeans elementTypeListBeans
     */
    private void setStartersAdapter(List<InformationTypeOneSecondBean.ElementTypeListBean> elementTypeListBeans) {
        try {
            if (startersAdapter == null) {
                startersAdapter = new PrefectureScreenStarterAdapter();
                ArmsUtils.configRecyclerView(starters, new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                starters.setAdapter(startersAdapter);
                startersAdapter.setOnItemClickListener((adapter, view, position) -> {
                    startersAdapter.setCheckedPos(position);
                    InformationTypeOneSecondBean.ElementTypeListBean item =
                            (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                    getSecond(item);
                });
            }
            startersAdapter.setNewData(elementTypeListBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取二级 列表
     *
     * @param item item
     */
    private void getSecond(InformationTypeOneSecondBean.ElementTypeListBean item) {
        try {
            if (item.isLeaf()) {
                if (secondAdapter != null) {
                    secondAdapter.setNewData(null);
                }
                if (threeAdapter != null) {
                    threeAdapter.setNewData(null);
                }
                return;
            }

            if (item.getSubItems() != null && item.getSubItems().size() > 0) {
                setSecondAdapter(item.getSubItems());
                getThree(item.getSubItems().get(0));
                return;
            }
            Map<String, Object> map = new HashMap<>(8);
            map.put("ElementId", item.getId());
            Observable<BaseBean<InformationTypeOneSecondBean>> observable = mRepositoryManager.
                    obtainRetrofitService(InformationService.class).
                    getInfoCollectResource(SignUtil.paramSign(map));
            NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, beans -> {
                if (beans != null && beans.getElementTypeList() != null && beans.getElementTypeList().size() > 0) {
                    item.setList(beans.getElementTypeList());
                    setSecondAdapter(beans.getElementTypeList());
                    getThree(beans.getElementTypeList().get(0));
                } else {
                    if (secondAdapter != null) {
                        secondAdapter.setNewData(null);
                    }
                    if (threeAdapter != null) {
                        threeAdapter.setNewData(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置二级数据
     *
     * @param elementTypeListBeans beans
     */
    private void setSecondAdapter(List<InformationTypeOneSecondBean.ElementTypeListBean> elementTypeListBeans) {
        if (secondAdapter == null) {
            secondAdapter = new PrefectureScreenSecondAdapter();
            ArmsUtils.configRecyclerView(second, new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            second.setAdapter(secondAdapter);
            secondAdapter.setOnItemClickListener((adapter, view, position) -> {
                InformationTypeOneSecondBean.ElementTypeListBean item =
                        (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                secondAdapter.setCheckedPos(position);
                getThree(item);
            });
        }
        if (elementTypeListBeans != null && elementTypeListBeans.size() > 0) {
            secondAdapter.setCheckedPos(0);
        }
        secondAdapter.setNewData(elementTypeListBeans);
    }

    /**
     * 获取三级
     *
     * @param item item
     */
    private void getThree(InformationTypeOneSecondBean.ElementTypeListBean item) {
        try {
            if (item.isLeaf()) {
                if (threeAdapter != null) {
                    threeAdapter.setNewData(null);
                }
                return;
            }
            if (item.getSubItems() != null && item.getSubItems().size() > 0) {
                setThreeAdapter(item.getSubItems());
                return;
            }
            Map<String, Object> map = new HashMap<>(8);
            map.put("ElementId", item.getId());
            Observable<BaseBean<InformationTypeOneSecondBean>> observable = mRepositoryManager.
                    obtainRetrofitService(InformationService.class).
                    getInfoCollectResource(SignUtil.paramSign(map));
            NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, beans -> {
                if (beans != null && beans.getElementTypeList() != null && beans.getElementTypeList().size() > 0) {
                    item.setList(beans.getElementTypeList());
                    setThreeAdapter(beans.getElementTypeList());
                } else {
                    if (threeAdapter != null) {
                        threeAdapter.setNewData(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setThreeAdapter(List<InformationTypeOneSecondBean.ElementTypeListBean> beans) {
        if (threeAdapter == null) {
            threeAdapter = new PrefectureScreenThreeAdapter();
            //2，增加谷歌流式布局
            MyFlexboxLayoutManager manager = new MyFlexboxLayoutManager(getContext());
            //设置主轴排列方式
            manager.setFlexDirection(FlexDirection.ROW);
            //设置是否换行
            manager.setFlexWrap(FlexWrap.WRAP);
            manager.setAlignItems(AlignItems.STRETCH);
            manager.setJustifyContent(JustifyContent.FLEX_START);

            ArmsUtils.configRecyclerView(three, manager);
            threeAdapter.setEmptyView(R.layout.null_content_home_layout, three);
            three.setAdapter(threeAdapter);
            three.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.transparent)
                    .sizeResId(R.dimen.dp_10)
                    .build());
            three.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.transparent)
                    .sizeResId(R.dimen.dp_10)
                    .build());
            threeAdapter.setOnItemClickListener((adapter, view, position) -> {
                threeAdapter.setCheckedPos(position);
            });
        }
        if (beans != null && beans.size() > 0) {
            threeAdapter.setCheckedPos(0);
        }
        threeAdapter.setNewData(beans);
    }

    /**
     * 初始化view
     */
    private void initCView() {
        starters = findViewById(R.id.starters);
        second = findViewById(R.id.second);
        three = findViewById(R.id.three);
        reset = findViewById(R.id.reset);
        goSee = findViewById(R.id.go_see);
        reset.setOnClickListener(v -> {
            if (listener != null) {
                String eventualId = "";
                listener.onSureClick(eventualId);
                dismiss();
            }
        });
        goSee.setOnClickListener(v -> {
            try {
                String eventualId = "";
                Timber.e("sadasdsadasdsa");
                if (listener != null) {
                    if (startersAdapter != null) {
                        String starterItem = startersAdapter.getCheckedItem();
                        if (StringUtil.isNotNullString(starterItem)) {
                            eventualId = starterItem;
                        }
                    }
                    if (secondAdapter != null) {
                        String secondItem = secondAdapter.getCheckedItem();
                        if (StringUtil.isNotNullString(secondItem)) {
                            eventualId = secondItem;
                        }
                    }
                    if (threeAdapter != null) {
                        String threeItem = threeAdapter.getCheckedItem();
                        if (StringUtil.isNotNullString(threeItem)) {
                            eventualId = threeItem;
                        }
                    }
                    Timber.e("listener" + eventualId);
                    listener.onSureClick(eventualId);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dismiss();
            }
        });
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_prefecture_layout);
    }
}
