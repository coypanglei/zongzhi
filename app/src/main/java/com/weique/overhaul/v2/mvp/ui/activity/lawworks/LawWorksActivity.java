package com.weique.overhaul.v2.mvp.ui.activity.lawworks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.di.component.DaggerLawWorksComponent;
import com.weique.overhaul.v2.mvp.contract.LawWorksContract;
import com.weique.overhaul.v2.mvp.presenter.LawWorksPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.lawworks.LawWorksInformFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.lawworks.LawWorksOrderSortFragment;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 法务订单/通知
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_LAWWORKSACTIVITY)
public class LawWorksActivity extends BaseActivity<LawWorksPresenter> implements LawWorksContract.View {
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.radio_g)
    RadioGroup radioG;
    @BindView(R.id.radio_b1)
    RadioButton radioB1;
    @BindView(R.id.radio_b2)
    RadioButton radioB2;
    public static final int ONE = 0;
    public static final int TWO = 1;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLawWorksComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_law_works;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("法院联动");
        mFragments.add(LawWorksOrderSortFragment.newInstance());
        mFragments.add(LawWorksInformFragment.newInstance());
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);
        vp.setAdapter(mAdapter);
        vp.setCurrentItem(0);
        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //系统消息
                    case R.id.radio_b1:
                        vp.setCurrentItem(ONE);
                        radioB1.setTextColor(ArmsUtils.getColor(LawWorksActivity.this, R.color.blue_3982f6));
                        radioB2.setTextColor(ArmsUtils.getColor(LawWorksActivity.this, R.color.gray_999));
                        break;
                    //通知公告
                    case R.id.radio_b2:
                        vp.setCurrentItem(TWO);
                        radioB1.setTextColor(ArmsUtils.getColor(LawWorksActivity.this, R.color.gray_999));
                        radioB2.setTextColor(ArmsUtils.getColor(LawWorksActivity.this, R.color.blue_3982f6));
                        break;
                    default:
                        break;
                }
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
                } else if (position == TWO) {
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
}
