package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPartySearchComponent;
import com.weique.overhaul.v2.mvp.contract.PartySearchContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;
import com.weique.overhaul.v2.mvp.presenter.PartySearchPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyContentFragmentAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PartySearchHistoryAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建搜索
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_SEARCHACTIVITY)
public class SearchActivity extends BaseActivity<PartySearchPresenter> implements PartySearchContract.View {

    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.clear_edittext)
    LinearLayout clearEdittext;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.clear_history)
    LinearLayout clearHistory;
    @BindView(R.id.history_list)
    RecyclerView historyList;
    @BindView(R.id.search_label_layout)
    LinearLayout searchLabelLayout;
    @BindView(R.id.search_state)
    TextView searchState;
    @BindView(R.id.result_list)
    RecyclerView resultList;
    @BindView(R.id.result_layout)
    LinearLayout resultLayout;


    @Inject
    PartyContentFragmentAdapter mAdapter;

    private String keyWord;
    private PartySearchHistoryAdapter itemAdapter;


    /**
     * 来源  这里只想Activity 的 路由地址 用于event 回调使用
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    /**
     * 适配器也有上个界面 带过来
     */
    public static final String ADAPTER_FROM_SOURCE = "ADAPTER_FROM_SOURCE";
    /**
     * 适配器
     */
    @Autowired(name = ADAPTER_FROM_SOURCE)
    BaseQuickAdapter adapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartySearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        initEditText();
        initSearchResultAdapter();
        initHistoryLabelAdapter();
    }

    /**
     * 初始话edit text
     */
    private void initEditText() {
        try {
            inputEt.setFocusable(true);
            inputEt.setFocusableInTouchMode(true);
            inputEt.requestFocus();
            inputEt.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        startSearch();
                    }
                    return true;
                }
                return false;
            });
            inputEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    keyWord = s.toString();
                    if (StringUtil.isNullString(keyWord)) {
                        searchLabelLayout.setVisibility(View.VISIBLE);
                        resultLayout.setVisibility(View.GONE);
                        clearEdittext.setVisibility(View.GONE);
                    } else {
                        if (clearEdittext.getVisibility() == View.GONE) {
                            clearEdittext.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 历史搜索 adapter
     */
    private void initHistoryLabelAdapter() {
        //2，增加谷歌流式布局
        try {
            MyFlexboxLayoutManager manager = new MyFlexboxLayoutManager(this);
            //设置主轴排列方式
            manager.setFlexDirection(FlexDirection.ROW);
            //设置是否换行
            manager.setFlexWrap(FlexWrap.WRAP);
            manager.setAlignItems(AlignItems.STRETCH);
            manager.setJustifyContent(JustifyContent.FLEX_START);

            historyList.setHasFixedSize(true);
            ArmsUtils.configRecyclerView(historyList, manager);
            if (itemAdapter == null) {
                itemAdapter = new PartySearchHistoryAdapter();
                historyList.setAdapter(itemAdapter);
            }
            itemAdapter.setEmptyView(R.layout.null_content_layout, resultList);
            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    keyWord = (String) adapter.getItem(position);
                    inputEt.setText(keyWord);
                    inputEt.setSelection(keyWord.length());
                    searchState.setText(String.format(getResources().getString(R.string.search_state), keyWord));
                    startSearch();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            });
            updateHistory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 搜索结果 展示adapter
     */
    private void initSearchResultAdapter() {
        try {
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mAdapter.setOnLoadMoreListener(() -> {
                if (StringUtil.isNullString(keyWord)) {
                    return;
                }
                mPresenter.getKeywordList(true, keyWord);
            }, resultList);
            ArmsUtils.configRecyclerView(resultList, new LinearLayoutManager(this));
            resultList.setClipToPadding(false);
            resultList.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.null_result_layout, resultList);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    PartyContentItemBean.ListBean favoritesBean = (PartyContentItemBean.ListBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.front_layout:
                            //只能收藏新闻  所以这里类型 固定是新闻
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PARTYCONTENTARTICLEDETAILACTIVITY)
                                    .withString("id", favoritesBean.getId())
                                    .withInt("type", PartyCenterActivity.NEWS_CENTER)
                                    .withInt("position", position)
                                    .navigation(SearchActivity.this);
                            break;
                        default:
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
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
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

    /**
     * 设置更多数据
     *
     * @param list list
     */
    @Override
    public void setMoreData(List<PartyContentItemBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            mAdapter.addData(list);
        }
    }

    /**
     * 设置新数据
     *
     * @param list list
     */
    @Override
    public void setNewData(List<PartyContentItemBean.ListBean> list) {
        searchLabelLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        mAdapter.setNewData(list);
    }

    @OnClick({R.id.search, R.id.clear_edittext, R.id.cancel, R.id.clear_history})
    public void onClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                //搜索
                case R.id.search:
                    startSearch();
                    break;
                //清理edittext
                case R.id.clear_edittext:
                    inputEt.setText("");
                    break;
                //退出
                case R.id.cancel:
                    finish();
                    break;
                //清空历史记录
                case R.id.clear_history:
                    UserInfoUtil.cleanSearchKeyword();
                    updateHistory();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始搜索
     */
    private void startSearch() {
        try {
            if (StringUtil.isNotNullString(keyWord)) {
                // 监听到回车键，会执行2次该方法。按下与松开
                mPresenter.getKeywordList(false, keyWord);
                UserInfoUtil.addSearchKeyword(keyWord);
                searchState.setText(String.format(getResources().getString(R.string.search_state), keyWord));
                updateHistory();
            } else {
                showMessage("搜索内容不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHistory() {
        try {
            List<String> searchKeywordList = UserInfoUtil.getSearchKeywordList();
            itemAdapter.setNewData(searchKeywordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
