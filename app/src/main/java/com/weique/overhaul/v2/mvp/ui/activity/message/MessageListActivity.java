package com.weique.overhaul.v2.mvp.ui.activity.message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerMessageListComponent;
import com.weique.overhaul.v2.mvp.contract.MessageListContract;
import com.weique.overhaul.v2.mvp.presenter.MessageListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.message.AnnouncementFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.message.MessageListsFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 消息列表
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_MESSAGELISTACTIVITY)
public class MessageListActivity extends BaseActivity<MessageListPresenter> implements MessageListContract.View {


    @BindView(R.id.vp)
    ViewPager vp;

    /**
     * 显示的界面
     */
    public static final String CHECK_POS = "checkPos";
    public static final int ONE = 0;
    public static final int TWO = 1;
    @Autowired(name = CHECK_POS)
    int checkPos = 0;

    @BindView(R.id.radio_g)
    RadioGroup radioG;
    @BindView(R.id.radio_b1)
    RadioButton radioB1;
    @BindView(R.id.radio_b2)
    RadioButton radioB2;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"系统消息", "通知公告"};
    private MyPagerAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle(getString(R.string.mes));
        mFragments.add(MessageListsFragment.newInstance());
        mFragments.add(AnnouncementFragment.newInstance());
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vp.setAdapter(mAdapter);
//        tabLayout.setViewPager(vp, mTitles, this, mFragments);
        vp.setCurrentItem(checkPos);
        if(ONE == checkPos){
            radioB1.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.blue_3982f6));
            radioB2.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.gray_999));
            radioG.check(R.id.radio_b1);
        }else{
            radioB1.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.gray_999));
            radioB2.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.blue_3982f6));
            radioG.check(R.id.radio_b2);
        }
        radioG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                //系统消息
                case R.id.radio_b1:
                    vp.setCurrentItem(ONE);
                    radioB1.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.blue_3982f6));
                    radioB2.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.gray_999));
                    break;
                //通知公告
                case R.id.radio_b2:
                    vp.setCurrentItem(TWO);
                    radioB1.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.gray_999));
                    radioB2.setTextColor(ArmsUtils.getColor(MessageListActivity.this, R.color.blue_3982f6));
                    break;
                default:
                    break;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == ONE) {
                    radioG.check(radioB1.getId());
                }else
                if (position == TWO) {
                    radioG.check(radioB2.getId());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        return this;
    }

}
