package com.weique.overhaul.v2.mvp.ui.activity.information;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.diff.BaseQuickDiffCallback;
import com.google.gson.Gson;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.Utils;
import com.shehuan.nicedialog.ViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.KeybordUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.TimeSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SearchCurrencyAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;

import org.simple.eventbus.EventBus;

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
public class SearchDialogFragment extends NiceDialog {

    /**
     * 通用 选择弹框
     */
    private SearchCurrencyAdapter searchCurrencyAdapter;

    private CommonCollectBean commonCollectBean;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;

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

    public static SearchDialogFragment newInstance(CommonCollectBean commonCollectBean) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.COMMON_COLLECTION_KEY, commonCollectBean);
        SearchDialogFragment fragment = new SearchDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            commonCollectBean = bundle.getParcelable(Constant.COMMON_COLLECTION_KEY);
        }
    }


    @Override
    public int intLayoutId() {
        return R.layout.search_dialog;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        try {
            gson = new Gson();
            searchCurrencyAdapter = new SearchCurrencyAdapter();
            searchCurrencyAdapter.setNewData(commonCollectBean.getList());
            recyclerView = holder.getView(R.id.rv_content);
            relativeLayout = holder.getView(R.id.rl_all);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(searchCurrencyAdapter);
            holder.setOnClickListener(R.id.reset, v -> {
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
            holder.setOnClickListener(R.id.sumbit, v -> {
                if (ObjectUtils.isNotEmpty(confirmBtnClick)) {
                    KeyboardUtils.hideSoftInput(this.getView());
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
//                        } else {
//                            if (bean.getRequire()) {
//                                ToastUtils.showLong(bean.getTitile() + "此项为必填");
//                                return;
//                            }
//                        }
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
                        KeyboardUtils.hideSoftInput(this.getView());
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
    /**
     * 选择框
     */
    CommonRecyclerPopupWindow<NameAndIdBean>  commonRecyclerPopupWindow;
    private void initSelectAll(final BasicInformationBean.RecordsBean bean) {
        try {
            if (commonRecyclerPopupAdapter == null) {
                commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter<NameAndIdBean>();
            }

            commonRecyclerPopupWindow= new CommonRecyclerPopupWindow<>(getContext(),
                        commonRecyclerPopupAdapter,this);
            commonRecyclerPopupAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    Timber.e(view.getId() + "");
                    NameAndIdBean nameAndIdBean = (NameAndIdBean) adapter.getItem(position);
                    /*
                      更新數據
                     */
                    if (ObjectUtils.isNotEmpty(nameAndIdBean)) {
                        bean.setValue(nameAndIdBean.getName());
                        bean.setSelectValue(nameAndIdBean.getId());
                        searchCurrencyAdapter.setNewData(searchCurrencyAdapter.getData());
                    }
                    /*
                      关闭选择框
                     */
                    try {
                        commonRecyclerPopupWindow.dismissWithOutAnimate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
                if (!commonRecyclerPopupWindow.isShowing()) {
                    commonRecyclerPopupWindow.showPopupWindow();
                }

                commonRecyclerPopupWindow.setNewData(bean.getOptionlist());
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
                    getScreenWidth(getActivity()),//这块写成获取到的屏幕的宽就ok
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

                TimePickerView  timePickerdialog = PickerViewUtil.selectPickerTimeWithStartEndDialogTrue((ViewGroup)getDialog().getWindow().getDecorView(),getActivity(), timeSelectBean.getPrecision(),
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
                TimePickerView  timePickerdialogTwo =  PickerViewUtil.selectPickerTimeWithStartEndDialogTrue((ViewGroup)getDialog().getWindow().getDecorView(),getActivity(), Constant.YMD,
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

    public static int getScreenWidth(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }
}
