package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerInformationCollectionComponent;
import com.weique.overhaul.v2.mvp.contract.InformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InformationCollectionPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationCollectionAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.SimpleViewPageFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 信息采集
 *
 * @author GK
 */
@Route(path = RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY, name = "信息采集/标准地址入口")
public class InformationCollectionActivity extends BaseActivity<InformationCollectionPresenter> implements InformationCollectionContract.View {

    @Inject
    GridLayoutManager gridLayoutManager;
    @Inject
    InformationCollectionAdapter mgAdapter;

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tl_7)
    SlidingTabLayout tl7;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.recycler_label)
    RecyclerView recyclerLabel;
    @BindView(R.id.layout)
    LinearLayout layout;
    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    //    private final String[] mTitles = {"江苏省标", "标准地址"};
    private final String[] mTitles = {"标准地址"};
    //    private final String[][] mExplain = {{mTitles[0], "省级标准地址 省 市 区/县 街道 社区 网格"}, {mTitles[1], "用于 人 地 事 物 组织 基础地址"}};
    private final String[][] mExplain = {{mTitles[0], "用于 人 地 事 物 组织 基础地址"}};


    /**
     * 信息采集
     */
    public static final int GATHER = 0;

    /**
     * 走访
     */
    public static final int INTERVIEW = 1;

    /**
     * 巡检
     */
    public static final int INSPECTION = 2;

    /**
     * 1.寻访  2. 巡检 不用时要 设置为-1 要不可能会影响业务逻辑
     */
    @Autowired(name = TourVisitActivity.TYPE)
    public static int mType = GATHER;

    /**
     * 判断从那个界面跳转过来的
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    /**
     * 判断是不是 工作清单跳转过来的
     */
    private static String mSource;


    public static String getmSource() {
        return mSource;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInformationCollectionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_information_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle(ArmsUtils.getString(this, R.string.select_info_type));
            ARouter.getInstance().inject(this);
            scrollview.scrollTo(0, 0);
            mPresenter.getList();
            //如果上个界面是  走访  或是 巡查  就 不是crud  所以加 ！  有其他界面添加进来要  添加到这里
            try {
                if (StringUtil.isNotNullString(source)) {
                    //工作清单跳转过来的
                    if ((source.equals(RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT)) ||
                            (source.equals(RouterHub.APP_TASKLISTACTIVITY))) {
                        mSource = source;
                    } else {
                        mSource = "";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mType == GATHER) {
                layout.setVisibility(View.VISIBLE);
            } else {
                layout.setVisibility(View.GONE);
            }
            initViewPage();
            initRecyclerAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerAdapter() {
        try {
            mgAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationTypeOneSecondBean.ElementTypeListBean informarionTypeBean = (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                    if (informarionTypeBean.isLeaf()) {
                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                                .withString(ARouerConstant.ID, informarionTypeBean.getId())
                                .withString(ARouerConstant.TITLE, informarionTypeBean.getName())
                                .withString(InformationDynamicFormSelectActivity.DYNAMIC_FORM_JSON, informarionTypeBean.getDataStructureInJson())
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPEFIRSTACTIVITY)
                                .withString(ARouerConstant.ID, informarionTypeBean.getId())
                                .navigation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ArmsUtils.configRecyclerView(recyclerLabel, gridLayoutManager);
            recyclerLabel.setClipToPadding(false);
            recyclerLabel.setAdapter(mgAdapter);
            mgAdapter.setEmptyView(R.layout.null_content_layout, recyclerLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPage() {
        try {
            for (String[] title : mExplain) {
                mFragments.add(SimpleViewPageFragment.newInstance(title));
            }
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
            vp.setAdapter(mAdapter);
            vp.setPageMargin(15);
            tl7.setViewPager(vp, mTitles, this, mFragments);
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

    @Override
    public Context getActivity() {
        return this;
    }

    /**
     * 传递数据给 界面显示
     *
     * @param beans beans
     */
    @Override
    public void setData(List<InformationTypeOneSecondBean.ElementTypeListBean> beans) {
        try {
            mgAdapter.setNewData(beans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_search_click)
    public void onViewClicked() {
        if (AppUtils.isFastClick()) {
            return;
        }
        //去搜索全部子节点信息界面
        ARouter.getInstance().build(RouterHub.APP_SEARCH_COLLECTION_ACTICITY)
                .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                .navigation();
    }


    /**
     * 回调
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
    public void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.DELETE:
                case EventBusConstant.ADD:
                    assert mPresenter != null;
                    mPresenter.getList();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            //用完 状态还原未默认
            InformationCollectionActivity.mType = InformationCollectionActivity.GATHER;
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
