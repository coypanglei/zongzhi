package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.StringUtils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsDetailBean;
import com.weique.overhaul.v2.mvp.ui.adapter.PointDetailsListAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenSecondAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenStarterAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PrefectureScreenThreeAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SimpleTextAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
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
public class PointSetPopup extends BasePopupWindow {
    private RxErrorHandler mErrorHandler;
    private IView mRootView;
    private IRepositoryManager mRepositoryManager;
    private OnPrefectureScreenListener listener;

    public OnPrefectureScreenListener getListener() {
        return listener;
    }

    /**
     * 当前 第几页
     */
    private int size = 0;


    public PointSetPopup(Context context, IRepositoryManager iRepositoryManager, RxErrorHandler errorHandler,
                         IView rootView, int width) {
        super(context);
        this.mRepositoryManager = iRepositoryManager;
        this.mErrorHandler = errorHandler;
        this.mRootView = rootView;
        try {
            setPopupGravity(Gravity.BOTTOM);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
            setOutSideDismiss(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            recyclerView = findViewById(R.id.recycler_view);
            searchBtn = findViewById(R.id.search_btn);
            searchText = findViewById(R.id.search_text);
            searchLayout = findViewById(R.id.search_layout);
//            searchBtn.setOnClickListener(v -> {
//                String st = searchText.getText().toString();
//                DeviceUtils.hideSoftKeyboard(getContext(), searchText);
//                popupClickListener.onSearchBtnClick(st);
//            });
            if (mAdapter == null) {
                mAdapter = new PointDetailsListAdapter();
                ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(mAdapter);
                recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_1)
                        .build());
                mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    DeviceUtils.hideSoftKeyboard(getContext(), searchText);
                    PointsDetailBean item = (PointsDetailBean) adapter.getItem(position);
                    listener.onSureClick(item);
                    dismiss();
                });
                mAdapter.setOnLoadMoreListener(() -> {
                    Timber.e("szie" + size);
//                    popupClickListener.onLoadMore(searchText.getText().toString());
                    List<String> idsTest = new ArrayList<>();


                    if (size > ALL_PAGE_NUMBER) {
                        setLoadMoreViewHide(true);
                    } else {
                        if (PAGE_NUMBER * (size + 1) > ids.size()) {
                            idsTest = ids.subList(size * PAGE_NUMBER, ids.size());
                        } else {
                            idsTest = ids.subList(size * PAGE_NUMBER, PAGE_NUMBER * (size + 1));
                        }
                        size++;

                        Map<String, Object> map = new HashMap<>();
                        Gson gson = new Gson();
                        map.put("ids", gson.toJson(idsTest));

                        Observable<BaseBean<List<PointsDetailBean>>> observable =
                                mRepositoryManager.obtainRetrofitService(InformationService.class).getPointsInfo(SignUtil.paramSign(map));
                        NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, elementTypeListBeans -> {
                            Timber.e(size + "");
                            setData(elementTypeListBeans, true);
                            setLoadMoreViewHide(false);
                        });

                    }
                }, recyclerView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 点击
     * onPrefectureScreenListener
     */
    public interface OnPrefectureScreenListener {
        /**
         * onSureClick
         */
        void onSureClick(PointsDetailBean PointsDetailBean);
    }

    public void setListener(OnPrefectureScreenListener listener) {
        this.listener = listener;
    }


    private List<String> ids = new ArrayList<>();

    /**
     * 获取一级列表数据
     */
    public void getPointsList(List<String> ids) {
        try {
            Timber.e(ids.toString());
            this.ids.clear();
            /*
              大于20数据
             */
            this.ids = ids;
            ALL_PAGE_NUMBER = ids.size() / PAGE_NUMBER;
            Timber.e("ALL_PAGE_NUMBER" + ALL_PAGE_NUMBER);
            List<String> idsTest = new ArrayList<>();
            if (ids.size() < PAGE_NUMBER) {
                idsTest = ids;

                Map<String, Object> map = new HashMap<>();
                Gson gson = new Gson();
                map.put("ids", gson.toJson(idsTest));

                Observable<BaseBean<List<PointsDetailBean>>> observable =
                        mRepositoryManager.obtainRetrofitService(InformationService.class).getPointsInfo(SignUtil.paramSign(map));
                NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, elementTypeListBeans -> {
                    setData(elementTypeListBeans, false);
                    setLoadMoreViewHide(true);
                });
            } else {
                idsTest = ids.subList(size, PAGE_NUMBER );
                size++;
                Map<String, Object> map = new HashMap<>();
                Gson gson = new Gson();
                map.put("ids", gson.toJson(idsTest));

                Observable<BaseBean<List<PointsDetailBean>>> observable =
                        mRepositoryManager.obtainRetrofitService(InformationService.class).getPointsInfo(SignUtil.paramSign(map));
                NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, elementTypeListBeans -> {
                    setData(elementTypeListBeans, true);
                    setLoadMoreViewHide(false);
                });

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PointDetailsListAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView searchBtn;
    private EditText searchText;
    private LinearLayout searchLayout;

    /**
     * 数据大于多少时  显示搜索框
     */
    public static final int SHOW_SEARCH_VIEW_NUMBER = 10;
    /**
     * 每页几条
     */
    public static final int PAGE_NUMBER = 20;


    /**
     * 总共多少页
     *
     * @param
     */
    public static int ALL_PAGE_NUMBER = 1;

    public void setLoadMoreViewHide(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * @param data   data
     * @param isMore isMore  是否是加载更多
     */
    public void setData(List<PointsDetailBean> data, boolean isMore) {
        try {

            if (isMore) {
                mAdapter.addData(data);
            } else {
//                if (data.size() > SHOW_SEARCH_VIEW_NUMBER) {
//                    searchLayout.setVisibility(View.VISIBLE);
//                } else {
//                    searchLayout.setVisibility(View.GONE);
//                }
                searchText.setText("");
                mAdapter.setNewData(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_search_layout);
    }
}
