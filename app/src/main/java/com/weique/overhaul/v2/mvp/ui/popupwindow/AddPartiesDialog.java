package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author GK
 * @description: 添加当事人dialog
 * @date :2020/8/12 11:00
 */
public class AddPartiesDialog extends Dialog {
    private Activity mContext;
    private RadioGroup radioGroup;
    //自然人
    private LinearLayout naturalPersonLayout;
    private EditText myName;
    private TextView age;
    private EditText idNumber;
    private EditText addressParties;
    private EditText contactParties;
    //法人
    private LinearLayout organizationLegalLayout;
    private EditText organizationLegalName;
    private EditText firmCode;
    private EditText chargeName;
    private EditText chargeDuty;
    private Button submitUse;
    private LinearLayout selectDate;
    private RadioGroup genderG;

    private AddPartiesDialogListener mListener;

    public interface AddPartiesDialogListener {
        void onSubmitClick(PartiesBean partiesBean);
    }

    public void setListener(AddPartiesDialogListener mListener) {
        this.mListener = mListener;
    }

    public AddPartiesDialog(@NonNull Activity context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        initView();
    }

    private void initView() {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add_parties, null);
            setContentView(view);
            //alertDialog是否可以点击外围消失
            setCanceledOnTouchOutside(false);
            setCancelable(true);
            radioGroup = view.findViewById(R.id.rg);
            naturalPersonLayout = view.findViewById(R.id.natural_person_layout);
            myName = view.findViewById(R.id.my_name);
            genderG = view.findViewById(R.id.gender_g);
            selectDate = view.findViewById(R.id.select_date);
            age = view.findViewById(R.id.age);
            idNumber = view.findViewById(R.id.id_number);

            addressParties = view.findViewById(R.id.address_a_parties);
            contactParties = view.findViewById(R.id.contact_parties);

            organizationLegalLayout = view.findViewById(R.id.organization_legal_layout);
            organizationLegalName = view.findViewById(R.id.organization_legal_name);
            firmCode = view.findViewById(R.id.firm_code);
            chargeName = view.findViewById(R.id.charge_name);
            chargeDuty = view.findViewById(R.id.charge_duty);

            submitUse = view.findViewById(R.id.submit_use);

            ImageView close = view.findViewById(R.id.close);
            close.setOnClickListener(v -> dismiss());

            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                try {
                    if (checkedId == R.id.natural_person) {
                        naturalPersonLayout.setVisibility(View.VISIBLE);
                        organizationLegalLayout.setVisibility(View.GONE);
                    } else {
                        naturalPersonLayout.setVisibility(View.GONE);
                        organizationLegalLayout.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            selectDate.setOnClickListener(v -> {
                PickerViewUtil.selectPickerTimeBirthIsDialog(mContext, Constant.YMD, (date, v1) -> {
                    try {
                        SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                        age.setText(format.format(date));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).show(view);
            });
            submitUse.setOnClickListener(v -> {
                try {
                    PartiesBean partiesBean = new PartiesBean();
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.natural_person:
                            partiesBean.setEnumPartiesTypes(0);
                            if(StringUtil.isNullString(myName.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.my_name_hint));
                                return;
                            }
                            partiesBean.setName(myName.getText().toString());

                            if(StringUtil.isNullString(age.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.be_born_hint));
                                return;
                            }
                            partiesBean.setBirthDate(age.getText().toString());

                            if(StringUtil.isNullString(idNumber.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.id_number_hint));
                                return;
                            }
                            partiesBean.setSId(idNumber.getText().toString());

                            RadioButton button = findViewById(genderG.getCheckedRadioButtonId());
                            partiesBean.setEnumGender(button.getId() == R.id.man ? 1 : 2);
                            partiesBean.setEnumGenderStr(button.getText().toString());
                            break;
                        case R.id.legal_organization:
                            partiesBean.setEnumPartiesTypes(1);
                            if(StringUtil.isNullString(organizationLegalName.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.organization_legal_name_hint));
                                return;
                            }
                            partiesBean.setName(organizationLegalName.getText().toString());

                            if(StringUtil.isNullString(chargeName.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.charge_name_hint));
                                return;
                            }
                            partiesBean.setPersonInCharge(chargeName.getText().toString());
                            if(StringUtil.isNullString(firmCode.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.firm_code_hint));
                                return;
                            }
                            partiesBean.setSId(firmCode.getText().toString());

                            if(StringUtil.isNullString(chargeDuty.getText().toString())){
                                ArmsUtils.makeText(getContext().getString(R.string.charge_duty_hint));
                                return;
                            }
                            partiesBean.setDuty(chargeDuty.getText().toString());
                            break;
                        default:
                    }

                    if(StringUtil.isNullString(addressParties.getText().toString())){
                        ArmsUtils.makeText(getContext().getString(R.string.address_a_hint));
                        return;
                    }
                    partiesBean.setAddress(addressParties.getText().toString());

                    if(StringUtil.isNullString(contactParties.getText().toString())){
                        ArmsUtils.makeText(getContext().getString(R.string.contact_hint));
                        return;
                    }
                    partiesBean.setTel(contactParties.getText().toString());
                    mListener.onSubmitClick(partiesBean);
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
