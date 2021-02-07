package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerEventTurnOverComponent;
import com.weique.overhaul.v2.mvp.contract.EventTurnOverContract;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.presenter.EventTurnOverPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 */
@Route(path = RouterHub.APP_EVENTTURNOVERACTIVITY)
public class EventTurnOverActivity extends BaseActivity<EventTurnOverPresenter> implements EventTurnOverContract.View {

    @BindView(R.id.turn_over_name)
    TextView turnOverName;
    @BindView(R.id.name1)
    TextView name1;
    @BindView(R.id.name2)
    TextView name2;
    @BindView(R.id.turn_over_content)
    EditText turnOverContent;
    @BindView(R.id.sure)
    Button sure;
    @BindView(R.id.cancel)
    Button cancel;
    @Inject
    Gson gson;

    /**
     * 协同
     */
    public static String SYNERGY = "APP/EventFormType/GetCoopRequestDeparts";
    /**
     * 移交
     */
    public static String TURN_OVER = "APP/EventFormType/GetDepartsForEventTransit";
    /**
     * 延期
     */
    public static String POSTPONE = "APP/EventFormType/EventDelayApply";

    @Autowired(name = ARouerConstant.ID)
    String id;
    @Autowired(name = ARouerConstant.PORT)
    String port;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private CommonRecyclerPopupAdapter<NameAndIdBean> commonRecyclerPopupAdapter;
    private String toJson;
    private TimePickerView timePickerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventTurnOverComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_event_turn_over;
    }

    @OnClick({R.id.turn_over_name, R.id.sure, R.id.cancel})
    public void onClick(View view) {
        try {
            assert mPresenter != null;
            switch (view.getId()) {
                case R.id.turn_over_name:
                    if (POSTPONE.equals(port)) {
                        if (timePickerView == null) {
                            timePickerView = PickerViewUtil.selectPickerTime(this, Constant.YMD, (date, v) -> {
                                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD, Locale.CHINA);
                                turnOverName.setText(format.format(date));
                            });
                        }
                        if (!timePickerView.isShowing()) {
                            timePickerView.show();
                        }
                    } else {
                        mPresenter.getDeparts(port, id);
                    }
                    break;
                case R.id.sure:
                    if (StringUtil.isNullString(turnOverContent.getText().toString())) {
                        ArmsUtils.makeText(turnOverContent.getHint().toString());
                        return;
                    }
                    if (TURN_OVER.equals(port)) {
                        if (ObjectUtils.isEmpty(turnOverName.getTag())) {
                            ArmsUtils.makeText("请选择部门");
                            return;
                        }
                        mPresenter.transitOrder(id, turnOverName.getTag(), turnOverContent.getText().toString());
                    } else if (SYNERGY.equals(port)) {
                        if (StringUtil.isNullString(toJson)) {
                            ArmsUtils.makeText("请选择部门");
                            return;
                        }
                        mPresenter.coopRequestCreate(id, toJson, turnOverContent.getText().toString(), source);
                    } else {
                        if (ObjectUtils.isEmpty(turnOverName.getText())) {
                            ArmsUtils.makeText(turnOverName.getHint().toString());
                            return;
                        }
                        mPresenter.eventDelayApply(port, id, turnOverName.getText().toString(),
                                turnOverContent.getText().toString(), source);
                    }
                    break;
                case R.id.cancel:
                    killMyself();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);

            if (SYNERGY.equals(port)) {
                setTitle("请求协同");
                name1.setText("协同部门");
                name2.setText("协同意见");
                turnOverContent.setHint("请填写协同意见");
                turnOverName.setHint("请选择协同部门(多选)");
                if (commonRecyclerPopupAdapter == null) {
                    commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter<>();
                }
                commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(this,
                        commonRecyclerPopupAdapter, (adapter, view, position) -> {
                    commonRecyclerPopupAdapter.setCheckPos(position);
                }, (CommonRecyclerPopupWindow.CommonRecyclerPopupListener) () -> {
                    List<NameAndIdBean> listByPosList = commonRecyclerPopupAdapter.getListByPosList();
                    List<String> ids = new ArrayList<>();
                    StringBuffer nameBuffer = new StringBuffer();
                    for (NameAndIdBean nameAndIdBean : listByPosList) {
                        ids.add(nameAndIdBean.getId());
                        nameBuffer.append(nameAndIdBean.getName() + "\n");
                    }
                    String names = nameBuffer.substring(0, nameBuffer.length() - 2);
                    turnOverName.setText(names);
                    toJson = gson.toJson(ids);
                });
            } else if (TURN_OVER.equals(port)) {
                setTitle("事件移交");
                name1.setText("移交部门");
                name2.setText("移交意见");
                turnOverContent.setHint("请填写移交意见");
                turnOverName.setHint("请选择移交部门");
                if (commonRecyclerPopupAdapter == null) {
                    commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter<>();
                }
                commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(this,
                        commonRecyclerPopupAdapter, new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        NameAndIdBean nameAndIdBean = (NameAndIdBean) adapter.getItem(position);
                        turnOverName.setText(nameAndIdBean.getName());
                        turnOverName.setTag(nameAndIdBean.getId());
                        commonRecyclerPopupWindow.dismiss();
                    }
                });
            } else {
                setTitle("事件延期");
                name1.setText("延期期限");
                name2.setText("延期说明");
                turnOverContent.setHint("请填写延期说明");
                turnOverName.setHint("请选择延期期限");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setDeparts(List<NameAndIdBean> items) {
        try {
            Iterator<NameAndIdBean> iterator = items.iterator();
            while (iterator.hasNext()) {
                NameAndIdBean next = iterator.next();
                if (next.getId().equals(UserInfoUtil.getUserInfo().getDepartId())) {
                    iterator.remove();
                    break;
                }
            }
            commonRecyclerPopupWindow.setNewData(items);
            if (!commonRecyclerPopupWindow.isShowing()) {
                commonRecyclerPopupWindow.showPopupWindow();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void killMyself() {
        finish();
    }
}
