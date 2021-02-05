package com.weique.overhaul.v2.mvp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerSimpleViewPageComponent;
import com.weique.overhaul.v2.mvp.contract.SimpleViewPageContract;
import com.weique.overhaul.v2.mvp.presenter.SimpleViewPagePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GK
 */
public class SimpleViewPageFragment extends BaseLazyLoadFragment<SimpleViewPagePresenter> implements SimpleViewPageContract.View {
    @BindView(R.id.card_title_tv)
    TextView cardTitleTv;
    @BindView(R.id.card_explain_tv)
    TextView cardExplainTv;
    @BindView(R.id.input_layout)
    LinearLayout layout;
    @BindView(R.id.image)
    ImageView image;
    private static final String CONTENTS = "CONTENTS";

    public static SimpleViewPageFragment newInstance(String[] content) {
        SimpleViewPageFragment fragment = new SimpleViewPageFragment();
        Bundle args = new Bundle();
        args.putStringArray(CONTENTS, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSimpleViewPageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_view_page, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            if ("标准地址".equals(getArguments().getStringArray(CONTENTS))) {
                //            layout.setBackground(ArmsUtils.getDrawablebyResource(mContext, R.drawable.dizhi_img));
                //            image.setImageDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.infomation_icon_dizhi));
                layout.setBackground(ArmsUtils.getDrawablebyResource(mContext, R.drawable.shegnbiao_img));
                image.setImageDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.infomation_icon_house));
            } else {
                layout.setBackground(ArmsUtils.getDrawablebyResource(mContext, R.drawable.shegnbiao_img));
                image.setImageDrawable(ArmsUtils.getDrawablebyResource(mContext, R.drawable.infomation_icon_house));

            }
            cardTitleTv.setText(getArguments().getStringArray(CONTENTS)[0]);
            cardExplainTv.setText(getArguments().getStringArray(CONTENTS)[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(@Nullable Object data) {

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


    @OnClick(R.id.input_layout)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if ("标准地址".equals(getArguments().getStringArray(CONTENTS)[0])) {
                ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
                        .withString(ARouerConstant.SOURCE, RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                        .withString(ARouerConstant.TITLE, "标准地址")
                        .navigation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void lazyLoadData() {

    }
}
