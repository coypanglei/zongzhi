package com.weique.overhaul.v2.mvp.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;

public class StaffTemperatureAdapter extends BaseQuickAdapter<StaffListBean.ListBean, BaseViewHolder> {

    public StaffTemperatureAdapter() {
        super(R.layout.activity_staff_temperature_item, null);
    }

    public static final double WARNING = 37.3;
    private static final double MAX = 43;
    private static final double MIN = 36;



    @Override
    protected void convert(@NonNull BaseViewHolder helper, StaffListBean.ListBean item) {
        try {
            helper.setText(R.id.name, "姓名: " + (item.getName()));
            EditText temperature_up = helper.getView(R.id.temperature_up);
            EditText temperature_d = helper.getView(R.id.temperature_d);
            if (temperature_up.getTag() instanceof TextWatcher) {
                temperature_up.removeTextChangedListener((TextWatcher) temperature_up.getTag());
            }
            if (temperature_d.getTag() instanceof TextWatcher) {
                temperature_d.removeTextChangedListener((TextWatcher) temperature_d.getTag());
            }
            if (item.getAMTemperature() > 0) {
                temperature_up.setText(String.valueOf(item.getAMTemperature()));
            } else {
                temperature_up.setText("");
            }
            if (item.getPMTemperature() > 0) {
                temperature_d.setText(String.valueOf(item.getPMTemperature()));
            } else {
                temperature_d.setText("");
            }
            TextWatcher upTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (StringUtil.isNullString(s.toString())) {
                            item.setAMTemperature(0);
                            return;
                        }
                        if (s.toString().length() == 1 && s.toString().equals(".")
                                || s.toString().length() == 1 && s.toString().equals("0")) {
                            temperature_up.setText("");
                            item.setAMTemperature(0.0);
                            return;
                        }
                        double v = Double.parseDouble(s.toString());
                        if (v > MAX) {
                            temperature_up.setText("");
                            item.setAMTemperature(0.0);
                            ArmsUtils.makeText("输入体温过，高请审查");
                            return;
                        }
                        if (s.toString().length() > 1 && v < MIN) {
                            temperature_up.setText("");
                            item.setAMTemperature(0.0);
                            ArmsUtils.makeText("输入体温过低,请重新输入");
                            return;
                        }
                        if (v >= WARNING) {
                            helper.setTextColor(R.id.temperature_up, ArmsUtils.getColor(mContext, R.color.red));
                        } else {
                            helper.setTextColor(R.id.temperature_up, ArmsUtils.getColor(mContext, R.color.green));
                        }
                        item.setAMTemperature(v);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            };

            TextWatcher dTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (StringUtil.isNullString(s.toString())) {
                            item.setPMTemperature(0);
                            return;
                        }
                        if (s.toString().length() == 1 && s.toString().equals(".")
                                || s.toString().length() == 1 && s.toString().equals("0")) {
                            temperature_d.setText("");
                            item.setPMTemperature(0.0);
                            return;
                        }
                        double v = Double.parseDouble(s.toString());
                        if (v > MAX) {
                            temperature_d.setText("");
                            ArmsUtils.makeText("输入体温过高,请重新输入");
                            item.setPMTemperature(0.0);
                            return;
                        }
                        if (s.toString().length() > 1 && v < MIN) {
                            temperature_d.setText("");
                            ArmsUtils.makeText("输入体温过低,请重新输入");
                            item.setPMTemperature(0.0);
                            return;
                        }
                        if (v >= WARNING) {
                            helper.setTextColor(R.id.temperature_d, ArmsUtils.getColor(mContext, R.color.red));
                        } else {
                            helper.setTextColor(R.id.temperature_d, ArmsUtils.getColor(mContext, R.color.green));
                        }
                        item.setPMTemperature(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            temperature_up.addTextChangedListener(upTextWatcher);
            temperature_d.addTextChangedListener(dTextWatcher);
            temperature_up.setTag(upTextWatcher);
            temperature_d.setTag(dTextWatcher);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
