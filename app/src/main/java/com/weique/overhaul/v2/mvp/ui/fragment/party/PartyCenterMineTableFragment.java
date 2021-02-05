package com.weique.overhaul.v2.mvp.ui.fragment.party;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.GradualFontSpan;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterMineTableComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterMineTableContract;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterMineTablePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建个人中心
 *
 * @author GK
 */
public class PartyCenterMineTableFragment extends BaseLazyLoadFragment<PartyCenterMineTablePresenter> implements PartyCenterMineTableContract.View {

    @BindView(R.id.user_photo_text)
    TextView userPhoto;
    @BindView(R.id.user_photo_image)
    ImageView userPhotoImage;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_detail)
    TextView userDetail;
    @BindView(R.id.integral_)
    TextView integral;
    @BindView(R.id.integral_view)
    TextView integralView;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.history)
    TextView history;
    @BindView(R.id.diss)
    TextView diss;
    @BindView(R.id.answer)
    LinearLayout answer;
    @BindView(R.id.answer_layout)
    RelativeLayout answerLayout;
    @BindView(R.id.integral_layout)
    RelativeLayout integralLayout;

    public static PartyCenterMineTableFragment newInstance() {
        PartyCenterMineTableFragment fragment = new PartyCenterMineTableFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterMineTableComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party_center_mine_table, container, false);
    }

    /**
     * 接收 刷新界面  事件
     */
    @Subscriber(tag = PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_ONE_REFRESH)
    private void onEventCallBack1(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case PartyCenterActivity.PARTY_SHOW_REFRESH:
                    //                    setDataLoaded(false);
                    if (isVisible()) {
                        lazyLoadData();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收 刷新界面  事件
     */
    @Subscriber(tag = PartyCenterActivity.PARTY_UPDATE_INTEGRAL)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case PartyCenterActivity.PARTY_UPDATE_INTEGRAL_CODE:
                    integralView.setText(String.valueOf(UserInfoUtil.getUserInfo().getSum()));
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpannableStringBuilder getGradualFontSpan(String message) {
        SpannableStringBuilder builder = new SpannableStringBuilder(message);
        try {
            GradualFontSpan span =
                    new GradualFontSpan(ArmsUtils.getColor(getContext(), R.color.red_d03a28),
                            ArmsUtils.getColor(getContext(), R.color.red_eb5543), true);
            builder.setSpan(span, 0, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            GlobalUserInfoBean userInfo = UserInfoUtil.getUserInfo();
            integralView.setText(String.valueOf(UserInfoUtil.getUserInfo().getSum()));
//            integralView.setText(getGradualFontSpan(String.valueOf(UserInfoUtil.getUserInfo().getSum())));
            if (StringUtil.isNotNullString(userInfo.getHeadUrl())) {
                GlideUtil.loadCircleImage(getActivity(), userInfo.getHeadUrl(), userPhotoImage);
            } else if (StringUtil.isNotNullString(userInfo.getName())) {
                userPhoto.setText(userInfo.getName().substring(0, 1));
            }
            userName.setText(StringUtil.setText(userInfo.getName()));
            userDetail.setText(String.format(getResources().getString(R.string.party_detail_info),
                    StringUtil.setText(userInfo.getPartyDepartmentName()), StringUtil.setText(userInfo.getDepartmentName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void showLoading() {
        EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.PARTY_SHOW_REFRESH), RouterHub.APP_PARTYCENTERACTIVITY);
    }

    @Override
    public void hideLoading() {
        EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.PARTY_HIDE_REFRESH), RouterHub.APP_PARTYCENTERACTIVITY);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }


    /**
     * 挑战界面通过 路由
     *
     * @param path
     */
    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(getContext(), path);
    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        hideLoading();
    }

    @OnClick({R.id.collect, R.id.history, R.id.diss,
            R.id.card_view, R.id.answer, R.id.answer_layout, R.id.user_detail, R.id.integral_layout})
    public void onViewClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                //收藏
                case R.id.collect:
                    launchActivityByRouter(RouterHub.APP_PARTYCENTERCOLLECTSACTIVITY);
                    break;
                //历史
                case R.id.history:
                    break;
                //吐槽
                case R.id.diss:
                    launchActivityByRouter(RouterHub.APP_PARTYCENTERDISSACTIVITY);
                    break;
                //用户详情
                case R.id.user_detail:
                    break;
                //积分详情
                case R.id.integral_layout:
                    launchActivityByRouter(RouterHub.APP_PARTYCENTERINTEGRALACTIVITY);
                    break;
                //积分
                case R.id.card_view:
                    break;
                //答题
                case R.id.answer:
                case R.id.answer_layout:
                    launchActivityByRouter(RouterHub.APP_ANSWERACTIVITY);

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
