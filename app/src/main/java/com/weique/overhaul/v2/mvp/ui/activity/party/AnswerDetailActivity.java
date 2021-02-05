package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerAnswerDetailComponent;
import com.weique.overhaul.v2.mvp.contract.AnswerDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.QuestionDetailAnswerItemBean;
import com.weique.overhaul.v2.mvp.presenter.AnswerDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.fragment.party.QuestionDetailFragment;

import org.jetbrains.annotations.NotNull;
import org.simple.eventbus.Subscriber;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建--答题详情
 *
 * @author GK
 */
@Route(path = RouterHub.APP_ANSWERDETAILACTIVITY)
public class AnswerDetailActivity extends BaseActivity<AnswerDetailPresenter> implements AnswerDetailContract.View {

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
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.right_btn_text_end)
    TextView rightBtnTextEnd;

    @Autowired
    String questionId;


    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int mSelecteSize = 0;
    private MyPagerAdapter mAdapter;
    private int AnswerCount;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnswerDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer_detail;
    }

    @Subscriber(tag = RouterHub.APP_ANSWERDETAILACTIVITY)
    private void onEventCallBack(int size) {
        mSelecteSize = size;
        assert mPresenter != null;
        mPresenter.getPartyAnswerDetailData(false, false, questionId);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        assert mPresenter != null;
        mPresenter.getPartyAnswerDetailData(false, false, questionId);
        rightBtn.setVisibility(View.VISIBLE);
        rightBtnText.setText("1");


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void setAnswerDetailListData(List<QuestionDetailAnswerItemBean> data) {

        try {
            if (data.size() > 0) {
                AnswerCount = data.get(0).getExamDetailCount();
                if (mSelecteSize == AnswerCount) {
                    int rightCount = 0;
                    int wrongCount = 0;
                    for (QuestionDetailAnswerItemBean datum : data) {
                        if (datum.isCorrect()) {
                            rightCount++;
                        } else {
                            wrongCount++;
                        }
                    }
                    String accuracy = accuracy(rightCount, data.size(), 1);
                    ARouter.getInstance()
                            .build(RouterHub.APP_ANSWERRESULTACTIVITY)
                            .withInt("rightCount", rightCount)
                            .withInt("wrongCount", wrongCount)
                            .withString("correct", accuracy)
                            .navigation(this);
                    finish();

                } else {
                    AnswerCount = data.get(0).getExamDetailCount();
                    rightBtnTextEnd.setText(String.format("/%s", data.get(0).getExamDetailCount()));

                    ArrayList<QuestionDetailAnswerItemBean> arrayList = new ArrayList<>(data);
                    mFragments.clear();
                    for (int i = 0; i < data.size(); i++) {
                        mFragments.add(QuestionDetailFragment.newInstance(data, i, questionId));
                    }


                    mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, arrayList);
                    vp.setAdapter(mAdapter);
                    vp.setOffscreenPageLimit(data.size());
                    mAdapter.notifyDataSetChanged();
                    if (mSelecteSize != 0) {
                        vp.setCurrentItem(mSelecteSize);
                    } else {
                        for (int i = 0; i < data.size(); i++) {
                            if (!data.get(i).isFinish()) {
                                vp.setCurrentItem(i);
                                rightBtnText.setText(String.format("%s", i + 1));
                                break;
                            }
                        }
                    }
                    vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int i, float v, int i1) {
                        }

                        @Override
                        public void onPageSelected(int i) {
                            rightBtnText.setText(String.format("%s", i + 1));
                            //                rightBtnTextEnd.setText(String.format("/%s", data.get(0).getExamDetailCount()));
                        }

                        @Override
                        public void onPageScrollStateChanged(int i) {

                        }
                    });
                }
            } else {
                ArmsUtils.makeText("当前题库没有题目！");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<QuestionDetailAnswerItemBean> mTitles;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments, ArrayList<QuestionDetailAnswerItemBean> titles) {
            super(fm);
            fragments = mFragments;
            mTitles = titles;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position).getId();
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return QuestionDetailFragment.newInstance(mTitles, position, questionId);
//            return fragments.get(position);
        }

        @Override
        public int getItemPosition(@NotNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }


    public static String accuracy(double num, double total, int scale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracyNum = num / total * 100;
        return df.format(accuracyNum) + "%";


    }
}
