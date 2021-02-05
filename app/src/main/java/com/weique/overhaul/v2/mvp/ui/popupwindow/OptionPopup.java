package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.ui.adapter.OptionPopupMoreRecyclerAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.OptionPopupRecyclerAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 *
 */
public class OptionPopup extends BasePopupWindow implements View.OnClickListener {

    private OptionPopupRecyclerAdapter recyclerAdapter;
    private OptionPopupMoreRecyclerAdapter morerecyclerAdapter;
    private LinearLayout linearLayout;
    private TextView name;
    private TextView sure;
    private RecyclerView optionRecycler;
    private TextView ove;
    private List<Boolean> listb;

    public interface OnSureClickListener {
        void onSureClick(String nameValue);
    }

    private OnSureClickListener onSureClickListener;

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        this.onSureClickListener = onSureClickListener;
    }

    private String titleName;
    private String type;
    private List<String> list;
    private String value;
    private List<String> values;

    /**
     * 支持延迟加载的PopupWindow
     *
     * @param context
     * @param type    类型 是什么选择框
     * @param list    数据
     * @param value   默认值  有就显示选中状态
     */
    public OptionPopup(Context context, String titleName, String type, List<String> list, String value) {
        super(context);
        this.titleName = titleName;
        this.type = type;
        this.list = list;
        this.value = value;
        listb = new ArrayList<>();
        for (String s : list) {
            if (StringUtil.isNotNullString(value) && s.equals(value)) {
                listb.add(true);
            } else {
                listb.add(false);
            }
        }
        initpopup();
    }

    /**
     * 支持延迟加载的PopupWindow
     *
     * @param context context
     * @param type    类型 是什么选择框
     * @param list    数据
     * @param values  默认值  有就显示选中状态
     */
    public OptionPopup(Context context, String titleName, String type, List<String> list, List<String> values) {
        super(context);
        try {
            this.titleName = titleName;
            this.type = type;
            this.list = list;
            this.values = values;
            listb = new ArrayList<>();
            for (String s : list) {
                listb.add(false);
            }
            if (values != null && values.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    for (String ss : values) {
                        if (ss.equals(list.get(i))) {
                            listb.set(i, true);
                            break;
                        }
                    }
                }
            }
            initpopup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initpopup() {
        try {
            setPopupGravity(Gravity.BOTTOM);
            //setBlurBackgroundEnable(true);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
            setOutSideDismiss(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            linearLayout = findViewById(R.id.title);
            ove = findViewById(R.id.ove);
            ove.setOnClickListener(this);
            name = findViewById(R.id.name);
            name.setText(titleName);
            name.setOnClickListener(this);
            sure = findViewById(R.id.sure);
            sure.setOnClickListener(this);
            optionRecycler = findViewById(R.id.option_recycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPopupWindow() {
        initadapter();
        super.showPopupWindow();
    }

    private void initadapter() {
        try {
            if (listb.size() > 6) {
                //2，增加谷歌流式布局
                MyFlexboxLayoutManager manager = new MyFlexboxLayoutManager(getContext());
                //设置主轴排列方式
                manager.setFlexDirection(FlexDirection.ROW);
                //设置是否换行
                manager.setFlexWrap(FlexWrap.WRAP);
                manager.setAlignItems(AlignItems.STRETCH);
                manager.setJustifyContent(JustifyContent.FLEX_START);

                ArmsUtils.configRecyclerView(optionRecycler, manager);
                morerecyclerAdapter = new
                        OptionPopupMoreRecyclerAdapter(R.layout.option_popup_option_more, list, listb, type);
                optionRecycler.setAdapter(morerecyclerAdapter);
                morerecyclerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        switch (type) {
                            case InformationDynamicFormSelectBean.DropdownList:
                            case InformationDynamicFormSelectBean.Option:
                                morerecyclerAdapter.itemChangedOnlyOne(position);
                                break;
                            case InformationDynamicFormSelectBean.CheckBox:
                                morerecyclerAdapter.itemChangedMore(position);
                                break;
                            default:
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                ArmsUtils.configRecyclerView(optionRecycler, new LinearLayoutManager(getContext()));
                optionRecycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_1)
                        .build());
                recyclerAdapter = new OptionPopupRecyclerAdapter(R.layout.option_popup_option, list, listb, type);
                optionRecycler.setAdapter(recyclerAdapter);
                recyclerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        if (view.getId() == R.id.all_item) {
                            switch (type) {
                                case InformationDynamicFormSelectBean.DropdownList:
                                case InformationDynamicFormSelectBean.Option:
                                    recyclerAdapter.itemChangedOnlyOne(position);
                                    break;
                                case InformationDynamicFormSelectBean.CheckBox:
                                    recyclerAdapter.itemChangedMore(position);
                                    break;
                                default:
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_option_layout);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.ove:
                    dismiss();
                    break;
                case R.id.sure:
                    List<String> data = null;
                    List<Boolean> booleans = null;
                    if (list.size() > 6) {
                        data = morerecyclerAdapter.getData();
                        booleans = morerecyclerAdapter.getmCheck();
                    } else {
                        data = recyclerAdapter.getData();
                        booleans = recyclerAdapter.getmCheck();

                    }
                    List<String> checkBoxdata = new ArrayList<>();
                    switch (type) {
                        case InformationDynamicFormSelectBean.DropdownList:
                        case InformationDynamicFormSelectBean.Option:
                            for (int i = 0; i < booleans.size(); i++) {
                                if (booleans.get(i)) {
                                    onSureClickListener.onSureClick(data.get(i));
                                    break;
                                }
                            }
                            break;
                        case InformationDynamicFormSelectBean.CheckBox:
                            for (int i = 0; i < booleans.size(); i++) {
                                if (booleans.get(i)) {
                                    checkBoxdata.add(data.get(i));
                                }
                            }
                            onSureClickListener.onSureClick(new Gson().toJson(checkBoxdata));
                            break;
                        default:
                    }
                    dismiss();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
