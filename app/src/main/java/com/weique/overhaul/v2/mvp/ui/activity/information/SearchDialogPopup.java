package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.TimeSelectBean;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SearchCurrencyAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonCustomAdapterPopupWindow;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import timber.log.Timber;

import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;

/**
 * 高级搜索弹窗
 */
public class SearchDialogPopup extends BottomPopupView {

    /**
     * 通用 选择弹框
     */
    private SearchCurrencyAdapter searchCurrencyAdapter;

    private CommonCollectBean commonCollectBean;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView reset;
    private TextView sumbit;

    public SearchDialogPopup(@NonNull Context context, CommonCollectBean commonCollectBean) {
        super(context);
        this.commonCollectBean = commonCollectBean;
    }

    public OnDataClickListener getConfirmBtnClick() {
        return confirmBtnClick;
    }

    public void setConfirmBtnClick(OnDataClickListener confirmBtnClick) {
        this.confirmBtnClick = confirmBtnClick;
    }

    /**
     * Interface definition for a callback to be invoked when a view is clicked.
     */
    public interface OnDataClickListener {
        /**
         * Called when a view has been clicked.
         */
        void onClick(Map map, List<BasicInformationBean.RecordsBean> list);
    }

    private OnDataClickListener confirmBtnClick;
    /**
     * 通用 选择adapter
     */
    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;
    private Gson gson;

    @Override
    protected int getImplLayoutId() {
        return R.layout.search_dialog;
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        try {
            gson = new Gson();
            searchCurrencyAdapter = new SearchCurrencyAdapter();
            searchCurrencyAdapter.setNewData(commonCollectBean.getList());
            recyclerView = findViewById(R.id.rv_content);
            relativeLayout = findViewById(R.id.rl_all);
            reset = findViewById(R.id.reset);
            sumbit = findViewById(R.id.sumbit);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(searchCurrencyAdapter);
            reset.setOnClickListener(v -> {
                for (BasicInformationBean.RecordsBean bean : searchCurrencyAdapter.getData()) {
                    /*
                       清空
                     */
                    if (ObjectUtils.isNotEmpty(bean.getValue())) {
                        bean.setValue("");

                    }

                }
                searchCurrencyAdapter.setNewData(searchCurrencyAdapter.getData());
                confirmBtnClick.onClick(null, searchCurrencyAdapter.getData());

            });
            sumbit.setOnClickListener(v -> {
                if (ObjectUtils.isNotEmpty(confirmBtnClick)) {
                    KeyboardUtils.hideSoftInput(this.getRootView());
                    /*
                     * 动态传给后台json
                     */
                    List<NameAndIdBean> nameAndIdBeanList = new ArrayList<>();
                    Map<String, Object> map = new HashMap<>();
                    for (BasicInformationBean.RecordsBean bean : searchCurrencyAdapter.getData()) {
                        if (ObjectUtils.isNotEmpty(bean.getValue())) {
                            if (!bean.getName().equals("DTF") && !bean.getName().equals("DTT") && !bean.getName().equals("SortBy")) {
                                if (ObjectUtils.isNotEmpty(bean.getSelectValue())) {
                                    nameAndIdBeanList.add(new NameAndIdBean(bean.getName(), bean.getSelectValue()));
                                } else {
                                    nameAndIdBeanList.add(new NameAndIdBean(bean.getName(), bean.getValue()));
                                }
                            } else {
                                if (ObjectUtils.isNotEmpty(bean.getSelectValue())) {

                                    map.put(bean.getName(), bean.getSelectValue());
                                } else {
                                    map.put(bean.getName(), bean.getValue());
                                }
                            }
                        }
                    }
                    Gson gson = new Gson();
                    map.put("QueryInJson", gson.toJson(nameAndIdBeanList));
                    confirmBtnClick.onClick(map, searchCurrencyAdapter.getData());
                    dismiss();
                }
            });
            searchCurrencyAdapter.setOnItemChildClickListener((adapter, view, position) -> {

                Timber.e(adapter.getData().get(position).toString());
                BasicInformationBean.RecordsBean bean = (BasicInformationBean.RecordsBean) adapter.getData().get(position);

                switch (view.getId()) {
                    /*
                     *  点击编辑
                     */
                    case R.id.et_name:
                    case R.id.iv_address:
                        KeyboardUtils.hideSoftInput(this.getRootView());
                        try {

                            switch (bean.getParamtype()) {
                                case DATA_ITEM:
                                    initTimePicker(bean);
                                    break;
                                case SELECT_ITEM:
                                    initSelectAll(bean);
                                    break;
                                default:

                                    break;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        break;
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSelectAll(final BasicInformationBean.RecordsBean bean) {
        try {
            CommonCustomAdapterPopupWindow<NameAndIdBean> window =
                    new CommonCustomAdapterPopupWindow<>(getContext(),
                            new CommonRecyclerPopupAdapter<>());
            window.setOnCustomAdapterPopupListener((popup, elementListBean, position) -> {
                try {
                    if (ObjectUtils.isNotEmpty(elementListBean)) {
                        bean.setValue(elementListBean.getName());
                        bean.setSelectValue(elementListBean.getId());
                        searchCurrencyAdapter.setNewData(searchCurrencyAdapter.getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            window.setNewData(bean.getOptionlist());
            new XPopup.Builder(getContext())
                    .moveUpToKeyboard(false)
                    .enableDrag(false)
                    .isDestroyOnDismiss(true)
//                        .isThreeDrag(true)
                    .asCustom(window)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化时间选择器
     */
    private void initTimePicker(final BasicInformationBean.RecordsBean bean) {
        try {
            TimeSelectBean timeSelectBean;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    getScreenWidth(getContext()),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            params.leftMargin = 0;
            params.rightMargin = 0;
            /**
             *  获取不同的类型
             */
            if (ObjectUtils.isNotEmpty(bean.getChangeType())) {
                timeSelectBean = gson.fromJson(bean.getChangeType(), TimeSelectBean.class);

                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
                startDate.set(year - timeSelectBean.getYear(), 0, 1);
                endDate.set(year + timeSelectBean.getYear(), month, dayOfMonth);

                TimePickerView timePickerdialog =
                        PickerViewUtil.selectPickerTimeWithStartEndDialogTrue(getContext(), timeSelectBean.getPrecision(),
                                selectedDate, startDate, endDate,
                                (date, v) -> {
                                    SimpleDateFormat format = new SimpleDateFormat(Constant.YM, Locale.CHINA);
                                    /**
                                     * 更新数据 ，不会破坏原有的布局
                                     */
                                    bean.setValue(format.format(date));
                                    searchCurrencyAdapter.setNewData(searchCurrencyAdapter.getData());
                                });
                timePickerdialog.getDialogContainerLayout().setLayoutParams(params);
                timePickerdialog.show();
            } else {
                Calendar selectedDate = Calendar.getInstance();
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                //正确设置方式 原因：注意事项有说明
                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
                startDate.set(year - 100, 0, 1);
                endDate.set(year + 10, month, dayOfMonth);
                TimePickerView timePickerdialogTwo = PickerViewUtil.
                        selectPickerTimeWithStartEndDialogTrue(
                                getContext(), Constant.YMD,
                                selectedDate, startDate, endDate,
                                (date, v) -> {
                                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                                    /**
                                     * 更新数据 ，不会破坏原有的布局
                                     */
                                    bean.setValue(format.format(date));
                                    searchCurrencyAdapter.setNewData(searchCurrencyAdapter.getData());
                                });
                timePickerdialogTwo.getDialogContainerLayout().setLayoutParams(params);
                timePickerdialogTwo.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }
}
