package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.di.component.DaggerSearchCollectionComponent;
import com.weique.overhaul.v2.mvp.contract.SearchCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.presenter.SearchCollectionPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.TreeListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_SEARCH_COLLECTION_ACTICITY, name = "信息采集/搜索全部信息采集入口")
public class SearchCollectionActivity extends BaseActivity<SearchCollectionPresenter> implements SearchCollectionContract.View {

    @BindView(R.id.et_search)
    EditText etSearch;

    @BindString(R.string.search_info_type)
    String title;
    //搜索关键字
    String keyword = "";

    @BindString(R.string.get_info_fail)
    String getInfoFailString;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    @Inject
    TreeListAdapter treeListAdapter;

    @Inject
    LinearLayoutManager layoutManager;


    private List<InformationTypeOneSecondBean.ElementTypeListBean> alldatas;


    private List<InformationTypeOneSecondBean.ElementTypeListBean> searchDatas;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchCollectionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(title);
        alldatas = new ArrayList<>();
        searchDatas = new ArrayList<>();

        initRecycler();
        initSearch();
        //初进入页面时刷新数据
        assert mPresenter != null;
        mPresenter.getElementTypes(keyword, false);

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
        finish();
    }

    @Override
    public Context getContext() {
        return SearchCollectionActivity.this;
    }

    @Override
    public void setDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees, boolean needAdd) {
        try {
            if (needAdd) {
                treeListAdapter.addData(dataTrees);
            } else {
                treeListAdapter.setNewData(dataTrees);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setAllDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees) {
        try {
            alldatas = dataTrees;
            treeListAdapter.setNewData(dataTrees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化Recycler
     */
    private void initRecycler() {
        try {
            treeListAdapter.setSearch(true);
            swipeRefreshLayout.setOnRefreshListener(() -> {
                mPresenter.getElementTypes(keyword, false);
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    try {
                        swipeRefreshLayout.setEnabled(recyclerView.getChildCount() == 0
                                || recyclerView.getChildAt(0).getTop() >= 0);
                        super.onScrollStateChanged(recyclerView, newState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            treeListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setAdapter(treeListAdapter);
            treeListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);

            treeListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationTypeOneSecondBean.ElementTypeListBean) {
                        InformationTypeOneSecondBean.ElementTypeListBean item = (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                        if (item == null) {
                            ArmsUtils.makeText(getInfoFailString);
                            return;
                        }
                        if (view.getId() == R.id.input) {
                            if (item.isLeaf()) {
                                ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                                        .withString(ARouerConstant.ID, item.getId())
                                        .withString(ARouerConstant.TITLE, item.getName())
                                        .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, item.getDataStructureInJson())
                                        .navigation();
                                finish();
                            }
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

    /**
     * 初始化搜索
     */
    private void initSearch() {
        try {
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    keyword = charSequence.toString().trim();

                    if (keyword.length() <= 0) {
                        treeListAdapter.setNewData(alldatas);
                    } else {

                        searchDatas.clear();
                        if (alldatas.size() > 0) {
                            for (InformationTypeOneSecondBean.ElementTypeListBean listBean : alldatas) {
                                if (listBean.getName().contains(keyword)) {
                                    searchDatas.add(listBean);
                                }
                            }
                            treeListAdapter.setNewData(searchDatas);
                        }

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });
            etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    //当actionId == XX_SEND 或者 XX_DONE时都触发
                    //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                    //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                    if (actionId == EditorInfo.IME_ACTION_SEND
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {

                        searchDatas.clear();
                        if (alldatas.size() > 0) {
                            for (InformationTypeOneSecondBean.ElementTypeListBean listBean : alldatas) {
                                if (listBean.getName().contains(keyword)) {
                                    searchDatas.add(listBean);
                                }
                            }
                            treeListAdapter.setNewData(searchDatas);
                        }
                        //处理事件
                        KeybordUtil.hideKeyboard(SearchCollectionActivity.this);
                    }

                    return false;
                }
            });
            etSearch.setFocusable(true);
            etSearch.setFocusableInTouchMode(true);
            etSearch.requestFocus();
            InputMethodManager inputManager =
                    (InputMethodManager) etSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(etSearch, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.iv_clear)
    public void onViewClicked() {

        try {
            keyword = "";
            etSearch.setText("");
            assert mPresenter != null;
            mPresenter.getElementTypes(keyword, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
