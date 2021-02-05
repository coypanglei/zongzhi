package com.weique.overhaul.v2.mvp.ui.activity.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.necer.calendar.Miui10Calendar;
import com.necer.enumeration.CheckModel;
import com.necer.enumeration.DateChangeBehavior;
import com.necer.painter.InnerPainter;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerSignSearchComponent;
import com.weique.overhaul.v2.mvp.contract.SignSearchContract;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;
import com.weique.overhaul.v2.mvp.presenter.SignSearchPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MySignRecordAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_MYSIGNRECORDACTIVITY)
public class SignSearchActivity extends BaseActivity<SignSearchPresenter> implements SignSearchContract.View {

    @BindView(R.id.miui10Calendar)
    Miui10Calendar miui10Calendar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.statistics)
    TextView statistics;


    /**
     * 要查询的用户id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * title
     */
    @Autowired(name = ARouerConstant.COMMON_NAME)
    String name;
    private MySignRecordAdapter mySignRecordAdapter;

    /**
     * 所有签到记录
     */
    private LinkedHashSet<MySignListBean.ListBean> allList;
    /**
     * 网格内签到记录
     */
    private LinkedHashSet<MySignListBean.ListBean> withinList;
    /**
     * 网格内签退
     */
    private LinkedHashSet<MySignListBean.ListBean> withinOutList;
    /**
     * 网格内签到记录
     */
    private LinkedHashSet<MySignListBean.ListBean> outerList;
    /**
     * 网格外签退
     */
    private LinkedHashSet<MySignListBean.ListBean> outerOutList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            if (StringUtil.isNullString(id)) {
                id = UserInfoUtil.getUserInfo().getUid();
                name = UserInfoUtil.getUserInfo().getName();
            }
            setTitle(StringUtil.setText(name) + "-打卡记录");
            assert mPresenter != null;
            miui10Calendar.setCheckMode(CheckModel.SINGLE_DEFAULT_CHECKED);
            miui10Calendar.setStretchCalendarEnable(true);
            String mouth = TimeUtil.getCurrentTimeByFormit(Constant.YM1);
            mPresenter.getSignRecordListData(id, mouth, null);
            mPresenter.getSignRecordListData(id, null, TimeUtil.getCurrentTimeByFormit(Constant.YMD1));
            miui10Calendar.setOnCalendarChangedListener((baseCalendar, year, month, localDate, dateChangeBehavior) -> {
                try {
                    if (localDate != null) {
                        if (dateChangeBehavior == DateChangeBehavior.PAGE) {
                            mPresenter.getSignRecordListData(id, TimeUtil.changedFormat(localDate.toString(), Constant.YM1), null);
                        } else if (dateChangeBehavior == DateChangeBehavior.CLICK) {
                            mPresenter.getSignRecordListData(id, null, localDate.toString());
                        } else if (dateChangeBehavior == DateChangeBehavior.CLICK_PAGE) {
                            mPresenter.getSignRecordListData(id, TimeUtil.changedFormat(localDate.toString(), Constant.YM1), null);
                            mPresenter.getSignRecordListData(id, null, localDate.toString());
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
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
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
    public void setSignRecordListData(List<MySignListBean.ListBean> list) {
        try {
            allList = new LinkedHashSet<>(list);
            withinList = new LinkedHashSet<>();
            withinOutList = new LinkedHashSet<>();
            outerList = new LinkedHashSet<>();
            outerOutList = new LinkedHashSet<>();
            InnerPainter innerPainter = (InnerPainter) miui10Calendar.getCalendarPainter();
            LinkedHashSet<String> hashSet = new LinkedHashSet<>();
            int sign_within = 0;
            int sign_within_out = 0;
            int sign_outer = 0;
            int sign_outer_out = 0;
            for (MySignListBean.ListBean listBean : list) {
                String changedFormat = TimeUtil.changedFormat(StringUtil.setText(listBean.getCreateTime()), Constant.YMD1);
                hashSet.add(changedFormat);
                if (listBean.isInGrid() && listBean.getEnumCheckInStatus() == 0) {
                    sign_within += 1;
                    withinList.add(listBean);
                } else if (listBean.isInGrid() && listBean.getEnumCheckInStatus() == 1) {
                    sign_within_out += 1;
                    withinOutList.add(listBean);
                } else if (!listBean.isInGrid() && listBean.getEnumCheckInStatus() == 0) {
                    sign_outer += 1;
                    outerList.add(listBean);
                } else if (!listBean.isInGrid() && listBean.getEnumCheckInStatus() == 1) {
                    sign_outer_out += 1;
                    outerOutList.add(listBean);
                }
            }
            List<String> strings = new ArrayList<>(hashSet);
            innerPainter.setPointList(strings);
            initStatistics(strings.size(), sign_within, sign_within_out, sign_outer, sign_outer_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化统计信息
     */
    private void initStatistics(int all, int sign_within, int sign_within_out, int sign_outer, int sign_outer_out) {
        try {
            SpannableString spanStrStart = new SpannableString("本月共打卡");
            SpannableString spanStrClick = new SpannableString(String.valueOf(all));
            spanStrClick.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setSignRecordListByDay(new ArrayList<>(allList));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(SignSearchActivity.this, R.color.blue_4D8FF7));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrStart2 = new SpannableString("天，网格内签到");
            SpannableString spanStrClick2 = new SpannableString(String.valueOf(sign_within));
            spanStrClick2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setSignRecordListByDay(new ArrayList<>(withinList));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(SignSearchActivity.this, R.color.green));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrStart3 = new SpannableString("次，网格外签到");
            SpannableString spanStrClick3 = new SpannableString(String.valueOf(sign_outer));
            spanStrClick3.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setSignRecordListByDay(new ArrayList<>(outerList));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(SignSearchActivity.this, R.color.red));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrClick3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrStart4 = new SpannableString("次");
            //签退
            SpannableString spanStrOutStart2 = new SpannableString("\n网格内签退");
            SpannableString spanStrOutClick2 = new SpannableString(String.valueOf(sign_within_out));
            spanStrOutClick2.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setSignRecordListByDay(new ArrayList<>(withinOutList));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(SignSearchActivity.this, R.color.green));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrOutClick2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrOutStart3 = new SpannableString("次，网格外签退");
            SpannableString spanStrOutClick3 = new SpannableString(String.valueOf(sign_outer_out));
            spanStrOutClick3.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setSignRecordListByDay(new ArrayList<>(outerOutList));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    //设置颜色
                    ds.setColor(ArmsUtils.getColor(SignSearchActivity.this, R.color.red));
                    //去掉下划线，默认是带下划线的
                    ds.setUnderlineText(false);
                    //设置字体背景
                    //				ds.bgColor = Color.parseColor("#FF0000");
                }
            }, 0, spanStrOutClick3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            SpannableString spanStrOutStart4 = new SpannableString("次");
            statistics.setText("");
            statistics.append(spanStrStart);
            statistics.append(spanStrClick);
            statistics.append(spanStrStart2);
            statistics.append(spanStrClick2);
            statistics.append(spanStrStart3);
            statistics.append(spanStrClick3);
            statistics.append(spanStrStart4);
            statistics.append(spanStrOutStart2);
            statistics.append(spanStrOutClick2);
            statistics.append(spanStrOutStart3);
            statistics.append(spanStrOutClick3);
            statistics.append(spanStrOutStart4);
            statistics.setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按天设置 签到记录
     *
     * @param mySignListBean mySignListBean
     */
    @Override
    public void setSignRecordListByDay(List<MySignListBean.ListBean> mySignListBean) {
        if (mySignRecordAdapter == null) {
            mySignRecordAdapter = new MySignRecordAdapter();
            mySignRecordAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(mySignRecordAdapter);
            mySignRecordAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                }
            });
        }
        mySignRecordAdapter.setNewData(mySignListBean);
    }
}
