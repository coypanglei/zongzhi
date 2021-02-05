package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;


import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author GK
 * 党建答题结果
 */
@Route(path = RouterHub.APP_ANSWERRESULTACTIVITY)
public class AnswerResultActivity extends BaseActivity {

    @Autowired
    int wrongCount;
    @Autowired
    int rightCount;
    @Autowired
    String  correct;


    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn_text_end)
    TextView rightBtnTextEnd;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_answer)
    TextView rightAnswer;
    @BindView(R.id.wrong_answer)
    TextView wrongAnswer;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.correct)
    TextView tvCorrect;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("答题结果");

        try {
            rightAnswer.setText(String.format("%s", rightCount));
            wrongAnswer.setText(String.format("错题数：%s", wrongCount));
            tvCorrect.setText(String.format("正确率：%s", correct));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            finish();
            EventBus.getDefault().post("", RouterHub.APP_ANSWERRESULTACTIVITY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post("", RouterHub.APP_ANSWERRESULTACTIVITY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post("", RouterHub.APP_ANSWERRESULTACTIVITY);
    }
}
