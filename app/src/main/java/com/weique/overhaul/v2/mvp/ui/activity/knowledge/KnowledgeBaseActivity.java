package com.weique.overhaul.v2.mvp.ui.activity.knowledge;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideImageLoader;
import com.weique.overhaul.v2.di.component.DaggerKnowledgeBaseComponent;
import com.weique.overhaul.v2.mvp.contract.KnowledgeBaseContract;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;
import com.weique.overhaul.v2.mvp.presenter.KnowledgeBasePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.KnowledgeBaseListAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.KnowledgePopup;
import com.weique.overhaul.v2.mvp.ui.popupwindow.ListPopup;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static razerdp.util.SimpleAnimationUtils.getTranslateVerticalAnimation;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_KNOWLEDGEBASEACTIVITY)
public class KnowledgeBaseActivity extends BaseActivity<KnowledgeBasePresenter> implements KnowledgeBaseContract.View {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.fenlei)
    TextView fenlei;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;
    private KnowledgePopup knowledgePopup;
    private ListPopup listPopup;
    List<KnowledgeBean> TypeList;
    List<KnowledgeBean> LabelList;

    private KnowledgeBaseListAdapter knowledgeBaseListAdapter;
    private String Tid;
    private String Lid;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerKnowledgeBaseComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_knowledge_base;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("知识库");
        initBanner();
        ARouter.getInstance().inject(this);
        assert mPresenter != null;
        mPresenter.getTypeListData();
        mPresenter.getLabelsListData();
        initApapter();
        assert mPresenter != null;
        mPresenter.getKnowledgeBaseListData(true, false, Tid, Lid);
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
        try {
            List<String> images = new ArrayList();
            images.add(ArmsUtils.getStringFromDrawableRes(this, R.drawable.repository_banner));
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setImageLoader(new GlideImageLoader());
            banner.setImages(images);
            banner.setBannerAnimation(Transformer.DepthPage);
            banner.isAutoPlay(true);
            banner.setDelayTime(3000);
            banner.setIndicatorGravity(BannerConfig.CENTER);
            banner.start();
            banner.setOnBannerListener(position -> {
                //            ArmsUtils.makeText(getActivity(), "position" + position)
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getKnowledgeBaseListData(true, false, Tid, Lid);
            });


            knowledgeBaseListAdapter = new KnowledgeBaseListAdapter();
            knowledgeBaseListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(knowledgeBaseListAdapter);
            knowledgeBaseListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                                .withString(ARouerConstant.TITLE, ((KnowledgeBaseBean.ListBean) adapter.getData().get(position)).getTitle())
                                .withString(ARouerConstant.WEB_CONTENT, ((KnowledgeBaseBean.ListBean) adapter.getData().get(position)).getContent())
                                .navigation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            knowledgeBaseListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getKnowledgeBaseListData(false, true, Tid, Lid);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        vSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        vSwipeRefresh.setRefreshing(false);
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            knowledgeBaseListAdapter.loadMoreEnd(true);
        } else {
            knowledgeBaseListAdapter.loadMoreComplete();
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
        finish();
    }


    @OnClick({R.id.fenlei, R.id.tag})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.fenlei:
                    if (TypeList == null || TypeList.size() <= 0) {
                        ArmsUtils.makeText("暂无分类");
                    } else {
                        if (listPopup == null) {
                            listPopup = new ListPopup(this, TypeList);
                        }
                        listPopup.setListItemClickListener((id, name) -> {
                            try {
                                Tid = id;
                                fenlei.setText(name);

                                if (id == null) {
                                    fenlei.setTextColor(getResources().getColor(R.color.black_333));
                                } else {
                                    fenlei.setTextColor(getResources().getColor(R.color.colorPrimary));
                                }

                                assert mPresenter != null;
                                mPresenter.getKnowledgeBaseListData(true, false, Tid, Lid);

                                listPopup.dismiss();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                        });


                        fenlei.setTextColor(getResources().getColor(R.color.colorPrimary));
                        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.shangla_icon);
                        fenlei.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);

                        listPopup.setShowAnimation(getTranslateVerticalAnimation(-1f, 0f, 500))
                                .setDismissAnimation(getTranslateVerticalAnimation(0f, -1f, 500)).showPopupWindow(layout);

                        listPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                fenlei.setTextColor(getResources().getColor(R.color.black_333));
                                Drawable drawable = ContextCompat.getDrawable(KnowledgeBaseActivity.this, R.drawable.xiala_icon);
                                fenlei.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                            }
                        });
                    }
                    break;
            case R.id.tag:
                try {
                    if (LabelList == null || LabelList.size() <= 0) {
                        ArmsUtils.makeText("暂无标签");

                    } else {
                        if (knowledgePopup == null) {
                            knowledgePopup = new KnowledgePopup(this, LabelList);
                        }

                        knowledgePopup.setShowAnimation(getTranslateVerticalAnimation(-1f, 0f, 500))
                                .setDismissAnimation(getTranslateVerticalAnimation(0f, -1f, 500)).showPopupWindow(layout);

                        knowledgePopup.setListItemClickListener(new KnowledgePopup.ListItemClickListener() {
                            @Override
                            public void onItemClick(String pos, String name) {
                                try {
                                    Lid = pos;


                                    if (Lid == null) {
                                        tag.setTextColor(getResources().getColor(R.color.black_333));
                                        tag.setText("标签");
                                    } else {
                                        tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                                        tag.setText(name);
                                    }

                                    assert mPresenter != null;
                                    mPresenter.getKnowledgeBaseListData(true, false, Tid, Lid);
                                    knowledgePopup.dismiss();
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                        tag.setTextColor(getResources().getColor(R.color.colorPrimary));
                        Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.shangla_icon);
                        tag.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable1, null);


                        knowledgePopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                try {
                                    tag.setTextColor(getResources().getColor(R.color.black_333));
                                    Drawable drawable = ContextCompat.getDrawable(KnowledgeBaseActivity.this, R.drawable.xiala_icon);
                                    tag.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
            default:
        }
    } catch(
    Exception e)

    {
        e.printStackTrace();
    }

}


    @Override
    public void setKnowledgeBaseListData(KnowledgeBaseBean data, boolean isLoadMore) {
        if (isLoadMore) {
            knowledgeBaseListAdapter.addData(data.getList());
        } else {
            knowledgeBaseListAdapter.setNewData(data.getList());
        }
    }

    @Override
    public void setTypeList(List<KnowledgeBean> knowledgeBean) {
        try {
            TypeList = new ArrayList<>();
            TypeList = knowledgeBean;
            TypeList.add(0, new KnowledgeBean(null, getString(R.string.all)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setLabelList(List<KnowledgeBean> knowledgeBean) {
        try {
            LabelList = new ArrayList<>();
            LabelList = knowledgeBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
