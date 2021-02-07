package com.weique.overhaul.v2.mvp.ui.activity.enforcelaw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.UriUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.shuhart.stepview.StepView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.FileUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerEnforceLawLawDetailComponent;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.LawDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.presenter.EnforceLawLawDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.FileImageAndNameAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.LawDetailProcessesAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:案件详情
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ENFORCE_LAW_LAW_DETAIL_ACTIVITY)
public class EnforceLawLawDetailActivity extends BaseActivity<EnforceLawLawDetailPresenter> implements EnforceLawLawDetailContract.View {


    @BindView(R.id.step_view)
    StepView stepView;

    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.matter_involve_text)
    TextView matterInvolveText;
    @BindView(R.id.law_source_text)
    TextView lawSourceText;
    @BindView(R.id.is_important_text)
    TextView isImportantText;
    @BindView(R.id.law_sort_text)
    TextView lawSortText;
    @BindView(R.id.law_affect_text)
    TextView lawAffectText;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.unit_name_text)
    TextView unitNameText;
    @BindView(R.id.contact_text)
    TextView contactText;
    @BindView(R.id.address_a_text)
    TextView addressAText;
    @BindView(R.id.cause_of_action_text)
    TextView causeOfActionText;
    @BindView(R.id.accept_time_text)
    TextView acceptTimeText;
    @BindView(R.id.crime_time_text)
    TextView crimeTimeText;
    @BindView(R.id.crime_address_text)
    TextView crimeAddressText;
    @BindView(R.id.parties_info_text)
    TextView partiesInfoText;
    @BindView(R.id.organization_legal_name_text)
    TextView organizationLegalNameText;
    @BindView(R.id.firm_code_text)
    TextView firmCodeText;
    @BindView(R.id.charge_name_text)
    TextView chargeNameText;
    @BindView(R.id.charge_duty_text)
    TextView chargeDutyText;
    @BindView(R.id.organization_legal_layout)
    LinearLayout organizationLegalLayout;
    @BindView(R.id.my_name_text)
    TextView myNameText;
    @BindView(R.id.gender_text)
    TextView genderText;
    @BindView(R.id.age_text)
    TextView ageText;
    @BindView(R.id.id_number_text)
    TextView idNumberText;
    @BindView(R.id.natural_person_layout)
    LinearLayout naturalPersonLayout;
    @BindView(R.id.p_address_a_text)
    TextView pAddressAText;
    @BindView(R.id.p_contact_text)
    TextView pContactText;
    @BindView(R.id.record_list)
    RecyclerView recordList;
    @BindView(R.id.input_record)
    EditText inputRecord;
    @BindView(R.id.transaction_cases_sign)
    EditText transactionCasesSign;
    @BindView(R.id.fill_line)
    View fillLine;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.time_layout)
    LinearLayout timeLayout;
    @BindView(R.id.attachment)
    TextView attachment;
    @BindView(R.id.attachment_layout)
    LinearLayout attachmentLayout;
    @BindView(R.id.file_list)
    RecyclerView fileList;
    @BindView(R.id.transaction_link)
    TextView transactionLink;
    @BindView(R.id.transaction_link_layout)
    LinearLayout transactionLinkLayout;
    @BindView(R.id.department_unit)
    TextView departmentUnit;
    @BindView(R.id.department_unit_link)
    LinearLayout departmentUnitLink;
    @BindView(R.id.transaction_p)
    TextView transactionP;
    @BindView(R.id.transaction_p_layout)
    LinearLayout transactionPLayout;
    @BindView(R.id.audit_date)
    TextView auditDate;
    @BindView(R.id.audit_date_layout)
    LinearLayout auditDateLayout;
    @BindView(R.id.add_new_record)
    LinearLayout addNewRecord;
    @BindView(R.id.next_handler_layout)
    LinearLayout nextHandlerLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    /**
     * 案件处理记录状态
     */
    private String[] processStatus = {"受理",
            "立案", "取证", "处罚", "听证", "执行", "归档", "巡查"};
    private LawDetailProcessesAdapter lawDetailProcessesAdapter;
    private CommonRecyclerPopupAdapter<NameAndIdBean> commonRecyclerPopupAdapter;
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private FileImageAndNameAdapter<UploadFileRsponseBean> mAdapter;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnforceLawLawDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enforce_law_law_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initData1();
    }

    /**
     * 初始化数据
     */
    private void initData1() {
        setTitle("案件详情");
        ImmersionBar.with(this).statusBarColor(R.color.white).init();
        ARouter.getInstance().inject(this);
        mPresenter.getLawDetailById(id);
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
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setLawDetail(LawDetailBean o) {
        try {
            LawDetailBean.CaseBean aCase = o.getCase();
            stepView.setSteps(ArrayUtils.asArrayList(processStatus));
            stepView.go(aCase.getEnumComprehensiveLawEnforcementCaseStatus() - 1, true);
            matterInvolveText.setText(StringUtil.setText(aCase.getComprehensiveLawEnforcementMattersName()));
            lawSourceText.setText(StringUtil.setText(aCase.getEnumCaseSourceStr()));
            isImportantText.setText(StringUtil.setText(aCase.getIsImportantStr()));
            lawSortText.setText(StringUtil.setText(aCase.getComprehensiveLawEnforcementCaseTypeName()));
            lawAffectText.setText(StringUtil.setText(aCase.getEnumCaseLevelStr()));
            titleText.setText(StringUtil.setText(aCase.getTitle()));
            unitNameText.setText(StringUtil.setText(aCase.getSourceName()));
            contactText.setText(StringUtil.setText(aCase.getSourceTel()));
            addressAText.setText(StringUtil.setText(aCase.getSourceAddress()));
            causeOfActionText.setText(StringUtil.setText(aCase.getCause()));
            crimeTimeText.setText(StringUtil.setText(aCase.getHappeningTimeStr()));
            crimeAddressText.setText(StringUtil.setText(aCase.getHappeningPlaceAddress()));
            if (o.getTarget() != null) {
                LawDetailBean.TargetBean target = o.getTarget();
                partiesInfoText.setText(StringUtil.setText(target.getEnumPartiesTypesStr()));
                if (target.getEnumPartiesTypes() == 0) {
                    organizationLegalLayout.setVisibility(View.GONE);
                    naturalPersonLayout.setVisibility(View.VISIBLE);
                    myNameText.setText(StringUtil.setText(target.getName()));
                    genderText.setText(StringUtil.setText(target.getEnumGenderStr()));
                    ageText.setText(StringUtil.setText(target.getAge() + ""));
                    idNumberText.setText(StringUtil.setText(target.getSId()));
                } else {
                    organizationLegalLayout.setVisibility(View.VISIBLE);
                    naturalPersonLayout.setVisibility(View.GONE);
                    organizationLegalNameText.setText(StringUtil.setText(target.getName()));
                    firmCodeText.setText(StringUtil.setText(target.getSId()));
                    chargeNameText.setText(StringUtil.setText(target.getPersonInCharge()));
                    chargeDutyText.setText(StringUtil.setText(target.getDuty()));
                }
                pAddressAText.setText(StringUtil.setText(target.getAddress()));
                pContactText.setText(StringUtil.setText(target.getTel()));
            }
            if (o.getProcesses() != null && o.getProcesses().size() > 0) {
                setAcceptingRecords(o.getProcesses());
                LawDetailBean.ProcessesBean processesBean = o.getProcesses().get(0);
                if (processesBean.getNextStepHandlerId().equals(UserInfoUtil.getUserInfo().getUid())) {
                    addNewRecord.setVisibility(View.VISIBLE);
                    //
                    if (processesBean.getCurrentStepStatus() == processStatus.length) {
                        nextHandlerLayout.setVisibility(View.GONE);
                    } else {
                        nextHandlerLayout.setVisibility(View.VISIBLE);
                    }
                    btnSubmit.setVisibility(View.VISIBLE);
                    btnSubmit.setText(processesBean.getNextStepStatusStr());
                } else {
                    addNewRecord.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLegalOperation(List<NameAndIdBean> o, boolean isLoadMore) {
        transactionP.setText("");
        transactionP.setTag(null);
        listShowPopup(o, departmentUnit);
    }

    @Override
    public void setAuditorList(List<NameAndIdBean> bean, boolean isLoadMore) {
        listShowPopup(bean, transactionP);
    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            if (fileList.getVisibility() == View.GONE) {
                fileList.setVisibility(View.VISIBLE);
            }
            if (mAdapter == null) {
                mAdapter = new FileImageAndNameAdapter<>();
                mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                ArmsUtils.configRecyclerView(fileList,
                        new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
                fileList.setAdapter(mAdapter);
                mAdapter.setNewData(uploadFileRsponseBeans);
                mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        UploadFileRsponseBean bean = (UploadFileRsponseBean) adapter.getItem(position);
                        switch (view.getId()) {
                            case R.id.all_item:
                                if (StringUtil.isNullString(bean.getSourceFilePath())) {
                                    ArmsUtils.makeText("未发现本地文件");
                                    return;
                                }
                                FileUtil.openFile(EnforceLawLawDetailActivity.this, new File(bean.getSourceFilePath()));
                                break;
                            case R.id.remove_image:
                                mAdapter.remove(position);
                                if (mAdapter.getData().size() <= 0) {
                                    fileList.setVisibility(View.GONE);
                                }
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                    mAdapter.addData(0, uploadFileRsponseBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置受理记录
     */
    private void setAcceptingRecords(List<LawDetailBean.ProcessesBean> records) {
        if (lawDetailProcessesAdapter == null) {
            lawDetailProcessesAdapter = new LawDetailProcessesAdapter();
            lawDetailProcessesAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recordList, new LinearLayoutManager(this));
            recordList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            recordList.setAdapter(lawDetailProcessesAdapter);
        }
        lawDetailProcessesAdapter.setNewData(records);
    }

    @OnClick({R.id.btn_submit, R.id.time_layout, R.id.attachment_layout, R.id.audit_date_layout,
            R.id.transaction_link_layout, R.id.department_unit_link, R.id.transaction_p_layout})
    public void onViewClick(View v) {
        try {
            KeyboardUtils.hideSoftInput(this);
            assert mPresenter != null;
            switch (v.getId()) {
                case R.id.attachment_layout:
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    try {
                        startActivityForResult(Intent.createChooser(intent, "选择文件上传"), Constant.GET_ACCESSORY_CODE);
                    } catch (android.content.ActivityNotFoundException ex) {
                        ArmsUtils.makeText("请安装一个文件管理器.");
                    }
                    break;
                case R.id.time_layout:
                    selectDate(time);
                    break;
                case R.id.transaction_link_layout:
                    arrayToListShowPopup(EnforceLawLawAddActivity.comprehensiveLawEnforcementCaseProcessStatus, transactionLink);
                    break;
                case R.id.audit_date_layout:
                    selectDateWithStartEnd(auditDate);
                    break;
                case R.id.department_unit_link:
                    mPresenter.getLegalOperation(true, false);
                    break;
                case R.id.transaction_p_layout:
                    if (StringUtil.isNullString(departmentUnit.getText().toString())) {
                        ArmsUtils.makeText("请先选择部门");
                        return;
                    }
                    mPresenter.getAuditorList(departmentUnit.getTag().toString(), true, false);
                    break;
                case R.id.btn_submit:
                    Map<String, Object> map = new HashMap<>();
                    map.put("CreateSignature", transactionCasesSign.getText().toString());
                    if (mAdapter != null && mAdapter.getDataUrlString().size() > 0) {
                        map.put("Enclosure", gson.toJson(mAdapter.getDataUrlString()));
                    }
                    map.put("Memo", inputRecord.getText().toString());
                    if (nextHandlerLayout.getVisibility() == View.VISIBLE
                            && (StringUtil.isNullString(auditDate.getText().toString())
                            || StringUtil.isNullString(departmentUnit.getTag().toString())
                            || StringUtil.isNullString(transactionP.getTag().toString())
                            || StringUtil.isNullString(transactionLink.getTag().toString()))) {
                        ArmsUtils.makeText("请完善'下一步审核办理'信息");
                        return;
                    }
                    map.put("NextStepDeadlineTime", auditDate.getText().toString());
                    map.put("NextStepDepartId", departmentUnit.getTag());
                    map.put("NextStepHandlerId", transactionP.getTag());
                    map.put("NextStepStatus", transactionLink.getTag());
                    map.put("ComprehensiveLawEnforcementCaseId", id);
                    mPresenter.submitManageInfo(map);
                    break;

                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * popup 设置数据  点击 设置数据
     *
     * @param array array
     * @param tv    tv
     */
    private void arrayToListShowPopup(String[][] array, TextView tv) {
        try {
            List<NameAndIdBean> caseSourceList = new ArrayList<>();
            for (String[] s : array) {
                caseSourceList.add(new NameAndIdBean(s[0], s[1]));
            }
            listShowPopup(caseSourceList, tv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择日期
     *
     * @param tv tv
     */
    private void selectDate(TextView tv) {
        try {
            PickerViewUtil.selectPickerTimeBirth(this, Constant.YMDHM, (date, v) -> {
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMDHM1, Locale.CHINA);
                tv.setText(format.format(date));
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == Constant.GET_ACCESSORY_CODE) {
                if (data != null) {
                    if (resultCode == Activity.RESULT_OK) {
                        if (data.getData() == null) {
                            ArmsUtils.makeText("获取文件失败");
                            return;
                        }
                        Uri uri = data.getData();
                        File file = UriUtils.uri2File(uri);
                        mPresenter.upLoadFile(file);
                    }
                }
            }
        } catch (Exception e) {
            ArmsUtils.makeText("获取本地文件失败");
            e.printStackTrace();
        }
    }

    /**
     * popup 设置数据  点击 设置数据
     *
     * @param l  l
     * @param tv tv
     */
    private void listShowPopup(List<NameAndIdBean> l, TextView tv) {
        try {
            if (commonRecyclerPopupAdapter == null) {
                commonRecyclerPopupAdapter = new CommonRecyclerPopupAdapter();
            }
            commonRecyclerPopupWindow = new CommonRecyclerPopupWindow(this,
                    commonRecyclerPopupAdapter, (BaseQuickAdapter.OnItemChildClickListener) (adapter, view, position) -> {
                NameAndIdBean bean = (NameAndIdBean) adapter.getItem(position);
                if (bean != null) {
                    tv.setText(bean.getName());
                    if (tv.getId() == R.id.transaction_p) {
                        tv.setTag(bean.getId2());
                    } else {
                        tv.setTag(bean.getId());
                    }
                }
                commonRecyclerPopupWindow.dismiss();
            });
            if (!commonRecyclerPopupWindow.isShowing()) {
                commonRecyclerPopupWindow.showPopupWindow();
            }
            commonRecyclerPopupWindow.setNewData(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 选择日期 设置起始和结束 点
     *
     * @param tv tv
     */
    private void selectDateWithStartEnd(TextView tv) {
        try {
            Calendar selectedDate = Calendar.getInstance();
            Calendar startDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            //正确设置方式 原因：注意事项有说明
            int year = selectedDate.get(Calendar.YEAR);
            int month = selectedDate.get(Calendar.MONTH);
            int dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH);
            startDate.set(year, 0, 1);
            endDate.set(year + 1, month, dayOfMonth);
            PickerViewUtil.selectPickerTimeWithStartEnd(this, Constant.YMD,
                    selectedDate, startDate, endDate,
                    (date, v) -> {
                        SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                        tv.setText(format.format(date));
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
