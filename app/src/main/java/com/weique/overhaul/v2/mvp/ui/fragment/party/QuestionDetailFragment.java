package com.weique.overhaul.v2.mvp.ui.fragment.party;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerQuestionDetailComponent;
import com.weique.overhaul.v2.mvp.contract.QuestionDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.IntegralBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.QuestionDetailAnswerItemBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.QuestionDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.QuestionDetailAnswerAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.AnswerPopWin;
import com.jess.arms.utils.LoadingDialog;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GK
 */
public class QuestionDetailFragment extends BaseFragment<QuestionDetailPresenter> implements QuestionDetailContract.View {


    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_sure)
    TextView btnSure;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.AnswerAnalysis)
    TextView answerAnalysis;
    @BindView(R.id.right_answer)
    TextView rightAnswer;
    @BindView(R.id.card_layout2)
    CardView cardLayout2;

    private AnswerPopWin answerPopWin;
    private int isClick = 0;
    private static final String SIZE = "SIZE";
    private static final String LIST = "LIST";
    private QuestionDetailAnswerAdapter questionDetailAnswerAdapter;
    private String answer;

    private int isRightPostion;
    private QuestionDetailAnswerItemBean bean;

    private static final String SUBJECTID = "SUBJECTID";
    private String subjectid;
    private int size;
    private ArrayList<QuestionDetailAnswerItemBean> list;


    public static QuestionDetailFragment newInstance(List<QuestionDetailAnswerItemBean> list, int size, String Subjectid) {
        QuestionDetailFragment fragment = new QuestionDetailFragment();
        Bundle args = new Bundle();
        args.putInt(SIZE, size);
        args.putString(SUBJECTID, Subjectid);
        args.putParcelableArrayList(LIST, (ArrayList<? extends Parcelable>) list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerQuestionDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            size = getArguments().getInt(SIZE);
            subjectid = getArguments().getString(SUBJECTID);
            list = getArguments().getParcelableArrayList(LIST);
            if (size + 1 == list.size()) {
                btnSure.setText(getResources().getString(R.string.submit));
            }
            bean = list.get(size);
            answer = bean.getAnswerAnalysis();
            text.setText(bean.getContent());
            if (TextUtils.isEmpty(bean.getAnswerAnalysis())) {
                rightAnswer.setText("暂无解析");
            } else {
                rightAnswer.setText(bean.getAnswerAnalysis());
            }

            if (bean.isFinish()) {
                if (bean.isCorrect()) {
                    answerAnalysis.setVisibility(View.GONE);
                } else {
                    cardLayout2.setVisibility(View.VISIBLE);
                    answerAnalysis.setVisibility(View.GONE);
                }
                layout.setVisibility(View.GONE);
            } else {
                layout.setVisibility(View.VISIBLE);
            }
            questionDetailAnswerAdapter = new QuestionDetailAnswerAdapter(bean.getAnswerInJson());
            questionDetailAnswerAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(questionDetailAnswerAdapter);
            recyclerView.setClipToPadding(false);
            questionDetailAnswerAdapter.setNewData(bean.getAnswersInJson());
            recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
                @Override
                public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        switch (view.getId()) {
                            case R.id.answer_layout:
                                if (isClick == 0) {
                                    if (!bean.isFinish()) {
                                        for (Object datum : adapter.getData()) {
                                            ((QuestionDetailAnswerItemBean.AnswersInJsonBean) datum).setClick(false);
                                        }
                                        ((QuestionDetailAnswerItemBean.AnswersInJsonBean) adapter.getData().get(position)).setClick(true);
                                        adapter.notifyDataSetChanged();
                                        isRightPostion = position;
                                    }
                                }
                                break;
                            default:
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(@Nullable Object data) {
        try {
            if (data instanceof IntegralBean) {
                IntegralBean integralBean = (IntegralBean) data;
                GlobalUserInfoBean userInfo = UserInfoUtil.getUserInfo();
                if (integralBean.getIntegral() > userInfo.getSum()) {
                    userInfo.setSum(integralBean.getIntegral());
                    UserInfoUtil.saveUserInfo(userInfo);
                    EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.PARTY_UPDATE_INTEGRAL_CODE, String.valueOf(integralBean.getIntegral())), PartyCenterActivity.PARTY_UPDATE_INTEGRAL);
                }
            }
            EventBus.getDefault().post(size + 1, RouterHub.APP_ANSWERDETAILACTIVITY);
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


    @OnClick({R.id.AnswerAnalysis, R.id.btn_sure})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.AnswerAnalysis:
                    if (answerPopWin == null) {
                        answerPopWin = new AnswerPopWin(mContext, answer);
                    }
                    answerPopWin.showPopupWindow();
                    break;
                case R.id.btn_sure:
                    if (bean.getAnswersInJson().get(isRightPostion).isClick()) {
                        isClick = 1;
                        if (btnSure.getText().equals("下一题")) {
                            assert mPresenter != null;
                            mPresenter.getPartyAnswer(subjectid, bean.getAnswersInJson().get(isRightPostion).getAnswer(), bean.getId());
                        }

                        if (size + 1 == bean.getExamDetailCount() && btnSure.getText().equals(getString(R.string.submit))) {
                            assert mPresenter != null;
                            mPresenter.getPartyAnswer(subjectid, bean.getAnswersInJson().get(isRightPostion).getAnswer(), bean.getId());
                            break;
                        }
                        if (!bean.getAnswersInJson().get(isRightPostion).isResult()) {
                            bean.getAnswersInJson().get(isRightPostion).setAnswer(true);
                            cardLayout2.setVisibility(View.VISIBLE);
                            questionDetailAnswerAdapter.notifyDataSetChanged();
                            btnSure.setText("下一题");
                            if (size + 1 == bean.getExamDetailCount()) {
                                btnSure.setText(getString(R.string.submit));
                            } else {
                                btnSure.setText("下一题");
                            }
                        } else {
                            bean.getAnswersInJson().get(isRightPostion).setAnswer(true);
                            questionDetailAnswerAdapter.notifyDataSetChanged();
                            assert mPresenter != null;
                            mPresenter.getPartyAnswer(subjectid, bean.getAnswersInJson().get(isRightPostion).getAnswer(), bean.getId());
                        }
                    } else {
                        ArmsUtils.makeText("请选择答案");
                    }


                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public BaseFragment getFragment() {
        return this;
    }

}
