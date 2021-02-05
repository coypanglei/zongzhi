package com.weique.overhaul.v2.mvp.ui.activity.enforcelaw;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.UriUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.FileUtil;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerEnforceLawLawAddComponent;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawAddContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.MatterBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnforceLawLawAddPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.FileImageAndNameAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.AddPartiesDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonRecyclerPopupWindow;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonTreeListPopup;
import com.weique.overhaul.v2.mvp.ui.popupwindow.SearchShowToListDialog;

import org.simple.eventbus.Subscriber;

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
 * Description: 新增 案件
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ENFORCELAWLAWADDACTIVITY)
public class EnforceLawLawAddActivity extends BaseActivity<EnforceLawLawAddPresenter> implements EnforceLawLawAddContract.View {

    @BindView(R.id.select_matter)
    TextView selectMatter;
    @BindView(R.id.law_source)
    TextView lawSource;
    @BindView(R.id.law_sort)
    TextView lawSort;
    @BindView(R.id.law_affect)
    TextView lawAffect;
    @BindView(R.id.is_great)
    CheckBox isGreat;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.unit_name)
    EditText unitName;
    @BindView(R.id.contact)
    EditText contact;
    @BindView(R.id.address_a)
    EditText addressA;
    @BindView(R.id.cause_of_action)
    EditText causeOfAction;
    @BindView(R.id.accept_time)
    TextView acceptTime;
    @BindView(R.id.crime_time)
    TextView crimeTime;
    @BindView(R.id.crime_address)
    TextView crimeAddress;
    @BindView(R.id.natural_person)
    RadioButton naturalPerson;
    @BindView(R.id.legal_organization)
    RadioButton legalOrganization;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.id_number)
    TextView idNumber;
    @BindView(R.id.address_a_parties)
    TextView addressAParties;
    @BindView(R.id.contact_parties)
    TextView contactParties;
    @BindView(R.id.input_record)
    EditText inputRecord;
    @BindView(R.id.accepting_cases_sign)
    EditText acceptingCasesSign;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.attachment)
    TextView attachment;
    @BindView(R.id.transaction_link)
    TextView transactionLink;
    @BindView(R.id.department_unit)
    TextView departmentUnit;
    @BindView(R.id.transaction_p)
    TextView transactionP;
    @BindView(R.id.audit_date)
    TextView auditDate;
    @BindView(R.id.organization_legal_name)
    TextView organizationLegalName;
    @BindView(R.id.firm_code)
    TextView firmCode;
    @BindView(R.id.charge_name)
    TextView chargeName;
    @BindView(R.id.charge_duty)
    TextView chargeDuty;
    @BindView(R.id.natural_person_layout)
    LinearLayout naturalPersonLayout;
    @BindView(R.id.organization_legal_layout)
    LinearLayout organizationLegalLayout;
    @BindView(R.id.parties_info_layout)
    LinearLayout partiesInfoLayout;
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.file_list)
    RecyclerView fileList;

    @Inject
    Gson gson;

    /**
     * 案件等级
     */
    private String[][] caselevel = {{"0", "轻微"}, {"1", "中等"}, {"2", "严重"}};
    /**
     * 案件来源
     */
    private String[][] caseSource = {{"0", "投诉举报"}, {"1", "巡查发现"},
            {"2", "交办"}, {"3", "移送"}, {"4", "媒体曝光"}, {"5", "群众举报"}, {"9999", "其他"}};
    /**
     * 事件来源
     */
    private String[][] eventSource = {{"0", "由我发起"}, {"1", "网格事件"}, {"2", "上级推送"}, {"3", "社区建议"}};
    /**
     * 案件状态
     */
    private String[][] comprehensiveLawEnforcementCaseStatus = {{"0", "暂存"},
            {"1", "受理"}, {"2", "立案"}, {"3", "取证"}, {"4", "处罚"}, {"5", "听证"}, {"6", "执行"}, {"7", "归档"}};
    /**
     * 案件处理记录状态
     */
    public static final String[][] comprehensiveLawEnforcementCaseProcessStatus = {{"1", "受理"},
            {"2", "立案"}, {"3", "取证"}, {"4", "处罚"}, {"5", "听证"}, {"6", "执行"}, {"7", "归档"}, {"10", "巡查"}};

    private CommonRecyclerPopupAdapter commonRecyclerPopupAdapter;
    private CommonRecyclerPopupWindow<NameAndIdBean> commonRecyclerPopupWindow;
    private CommonTreeListPopup<MatterBean> popup;
    private FileImageAndNameAdapter<UploadFileRsponseBean> mAdapter;
    private SearchShowToListDialog<PartiesBean> showToListDialog;

    /**
     * 检索当事人 关键字  SearchShowToListDialog 使用
     */
    private String keyword;

    /**
     * 当事人信息
     */
    private PartiesBean mPartiesBean;
    private AddPartiesDialog addPartiesDialog;

    private String mKeyWord;

    @Autowired(name = ARouerConstant.DATA_BEAN)
    EventsReportedLookBean eventsReportedLookBean;
    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnforceLawLawAddComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enforce_law_law_add;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("上报案件");
            ARouter.getInstance().inject(this);
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            if (StringUtil.isNotNullString(source) && RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY.equals(source)) {
                if (eventsReportedLookBean != null) {
                    crimeAddress.setText(eventsReportedLookBean.getEventAddress());
                    crimeAddress.setTag(eventsReportedLookBean.getPointsInJson());
                    title.setText(eventsReportedLookBean.getName());
                    causeOfAction.setText(eventsReportedLookBean.getMemo());
                    crimeTime.setText(eventsReportedLookBean.getCreateTime());
                }
            } else {
                //设置当前定位
                BDLocation bdLocation =
                        LocSdkClient.getInstance(this).getLocationStart()
                                .getLastKnownLocation();
                crimeAddress.setText(StringUtil.setText(bdLocation.getAddrStr()));
                TourVistLonAndLatBean lonAndLat = new TourVistLonAndLatBean(bdLocation.getLatitude(), bdLocation.getLongitude());
                crimeAddress.setTag(gson.toJson(lonAndLat));
            }
//            time.setText(TimeUtils.getNowString(new SimpleDateFormat(Constant.YMDHM1)));
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
    public void LoadingMore(boolean b) {
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


    @OnClick({R.id.law_source_layout, R.id.select_matter_layout, R.id.law_sort_layout,
            R.id.transaction_p_layout, R.id.audit_date_layout,
            R.id.law_affect_layout, R.id.accept_time_layout, R.id.btn_submit, R.id.search_parties,
            R.id.crime_time_layout, R.id.crime_address_layout, R.id.add_parties,
            R.id.attachment_layout, R.id.transaction_link_layout, R.id.department_unit_link})
    public void onViewClick(View v) {
        try {
            KeyboardUtils.hideSoftInput(this);
            assert mPresenter != null;
            if (commonRecyclerPopupWindow != null) {
                commonRecyclerPopupWindow.dismiss();
            }
            switch (v.getId()) {
                case R.id.select_matter_layout:
                    mPresenter.getMatterList(true, false);
                    break;
                case R.id.law_source_layout:
                    arrayToListShowPopup(caseSource, lawSource);
                    break;
                case R.id.law_sort_layout:
                    mPresenter.getLawSort();
                    break;
                case R.id.law_affect_layout:
                    arrayToListShowPopup(caselevel, lawAffect);
                    break;
                case R.id.transaction_link_layout:
                    arrayToListShowPopup(comprehensiveLawEnforcementCaseProcessStatus, transactionLink);
                    break;
                case R.id.accept_time_layout:
                    selectDate(acceptTime);
                    break;
                case R.id.audit_date_layout:
                    selectDateWithStartEnd(auditDate);
                    break;
                case R.id.crime_time_layout:
                    selectDate(crimeTime);
                    break;
//                case R.id.time_layout:
//                    selectDate(time);
//                    break;
                case R.id.transaction_p_layout:
                    if (StringUtil.isNullString(departmentUnit.getText().toString())) {
                        ArmsUtils.makeText("请先选择部门");
                        return;
                    }
                    mPresenter.getAuditorList(departmentUnit.getTag().toString(), true, false);
                    break;
                case R.id.department_unit_link:
                    mPresenter.getLegalOperation(true, false);
                    break;
                case R.id.crime_address_layout:
                    ARouter.getInstance().build(RouterHub.APP_NEWMAPACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_ENFORCELAWLAWADDACTIVITY)
                            .withString(ARouerConstant.DEPARTMENT_ID, UserInfoUtil.getUserInfo().getDepartmentId())
                            .withParcelable(ARouerConstant.LAST_POINT, gson.fromJson(crimeAddress.getTag().toString(), LatLng.class))
                            .navigation();
                    break;
                case R.id.attachment_layout:
                    mPresenter.getPermission();
                    break;
                case R.id.search_parties:
                    if (showToListDialog == null) {
                        showToListDialog = new SearchShowToListDialog<>(this);
                        showToListDialog.setListener(new SearchShowToListDialog.OnSearchShowToListListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                try {
                                    PartiesBean partiesBean = (PartiesBean) adapter.getItem(position);
                                    partiesSetToView(partiesBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onSearch(String text) {
                                keyword = text;
                                mPresenter.getPartiesList(true, false, text);
                            }

                            @Override
                            public void onLoadMore() {
                                mPresenter.getPartiesList(false, true, keyword);
                            }
                        });
                    }
                    if (!showToListDialog.isShowing()) {
                        showToListDialog.show();
                    }

                    mPresenter.getPartiesList(true, false, "");
                    break;
                case R.id.add_parties:
                    addPartiesDialog = new AddPartiesDialog(this);
                    addPartiesDialog.setListener(partiesBean -> {
                        try {
                            mPresenter.submitPartiesInfo(partiesBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    addPartiesDialog.show();
                    break;
                case R.id.btn_submit:
                    Map<String, Object> allMap = new HashMap<>();
                    //涉及权力事项
                    if (selectMatter.getTag() == null || StringUtil.isNullString(selectMatter.getTag().toString())) {
                        ArmsUtils.makeText(getString(R.string.select_matter_hint));
                        return;
                    }
                    allMap.put("Case.ComprehensiveLawEnforcementMattersId", selectMatter.getTag());
                    //案件来源
                    if (lawSource.getTag() == null || StringUtil.isNullString(lawSource.getTag().toString())) {
                        ArmsUtils.makeText(getString(R.string.law_source_hint));
                        return;
                    }
                    allMap.put("Case.EnumCaseSource", lawSource.getTag());
                    allMap.put("Case.SourceId", "");
                    allMap.put("Case.DepartmentId", UserInfoUtil.getUserInfo().getDepartmentId());


                    //案件分类
                    if (lawSort.getTag() == null || StringUtil.isNullString(lawSort.getTag().toString())) {
                        ArmsUtils.makeText(getString(R.string.select_sort_hint));
                        return;
                    }
                    allMap.put("Case.ComprehensiveLawEnforcementCaseTypeId", lawSort.getTag());

                    //案件影响
                    if (lawAffect.getTag() == null || StringUtil.isNullString(lawAffect.getTag().toString())) {
                        ArmsUtils.makeText(getString(R.string.select_affect_hint));
                        return;
                    }
                    allMap.put("Case.EnumCaseLevel", lawAffect.getTag());
                    allMap.put("Case.EnumComprehensiveLawEnforcementCaseStatus", 1);
                    allMap.put("Case.Memo", "");
                    //案件标题
                    if (title.getText() == null || StringUtil.isNullString(title.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.title_hint));
                        return;
                    }
                    allMap.put("Case.Title", title.getText().toString());
                    allMap.put("Case.isImportant", isGreat.isChecked());
                    //单位名称
                    if (unitName.getText() == null || StringUtil.isNullString(unitName.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.unit_name_hint));
                        return;
                    }
                    //联系方式
                    allMap.put("Case.SourceName", unitName.getText().toString());
                    if (contact.getText() == null || StringUtil.isNullString(contact.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.contact_hint));
                        return;
                    }
                    allMap.put("Case.SourceTel", contact.getText().toString());
                    //地址
                    if (addressA.getText() == null || StringUtil.isNullString(addressA.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.address_a_hint));
                        return;
                    }
                    allMap.put("Case.SourceAddress", addressA.getText().toString());

                    //案由
                    if (StringUtil.isNullString(causeOfAction.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.cause_of_action_hint));
                        return;
                    }
                    allMap.put("Case.Cause", causeOfAction.getText().toString());
                    //案发时间
                    if (crimeTime.getText() == null || StringUtil.isNullString(crimeTime.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.crime_time_hint));
                        return;
                    }
                    allMap.put("Case.HappeningTime", crimeTime.getText().toString());
                    //案发地点
                    if (crimeAddress.getText() == null || StringUtil.isNullString(crimeAddress.getTag().toString())) {
                        ArmsUtils.makeText(getString(R.string.crime_address_hint));
                        return;
                    }
                    allMap.put("Case.HappeningPlaceAddress", crimeAddress.getText().toString());
                    allMap.put("Case.PointInJson", crimeAddress.getTag());
                    allMap.put("Case.EnumEventSource", 0);

                    //当事人信息
                    if (mPartiesBean == null) {
                        ArmsUtils.makeText("请添加当事人信息");
                        return;
                    }
                    allMap.put("Case.ComprehensiveLawEnforcementTargetId", mPartiesBean.getId());
                    //受理记录
                    if (inputRecord.getText() == null
                            || StringUtil.isNullString(inputRecord.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.input_record));
                        return;
                    }
                    allMap.put("Process.Memo", inputRecord.getText().toString());
                    //受理人签名
                    if (acceptingCasesSign.getText() == null
                            || StringUtil.isNullString(acceptingCasesSign.getText().toString())) {
                        ArmsUtils.makeText(getString(R.string.accepting_cases_sign_hit));
                        return;
                    }
                    allMap.put("Process.CreateSignature", acceptingCasesSign.getText().toString());
                    //附件
                    if (mAdapter != null && mAdapter.getDataUrlString().size() > 0) {
                        allMap.put("Process.Enclosure", gson.toJson(mAdapter.getDataUrlString()));
                    }
                    //下一环节 办理相关信息
                    if (
                            auditDate.getText() == null
                                    || departmentUnit.getTag() == null
                                    || transactionP.getTag() == null
                                    || transactionLink.getTag() == null
                                    || StringUtil.isNullString(auditDate.getText().toString())
                                    || StringUtil.isNullString(departmentUnit.getTag().toString())
                                    || StringUtil.isNullString(transactionP.getTag().toString())
                                    || StringUtil.isNullString(transactionLink.getTag().toString())) {
                        ArmsUtils.makeText("请完善'下一步审核办理'信息");
                        return;
                    }
                    allMap.put("Process.NextStepDeadlineTime", auditDate.getText().toString());
                    allMap.put("Process.NextStepDepartId", departmentUnit.getTag());
                    allMap.put("Process.NextStepHandlerId", transactionP.getTag());
                    allMap.put("Process.NextStepStatus", transactionLink.getTag());
                    mPresenter.submitLaw(allMap);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void partiesSetToView(PartiesBean partiesBean) {
        try {
            assert partiesBean != null;
            mPartiesBean = partiesBean;
            if (partiesInfoLayout.getVisibility() == View.GONE) {
                partiesInfoLayout.setVisibility(View.VISIBLE);
            }
            switch (partiesBean.getEnumPartiesTypes()) {
                //自然人
                case 0:
                    naturalPersonLayout.setVisibility(View.VISIBLE);
                    organizationLegalLayout.setVisibility(View.GONE);
                    radioGroup.check(R.id.natural_person);
                    myName.setText(StringUtil.setText(partiesBean.getName()));
                    gender.setText(StringUtil.setText(partiesBean.getEnumGenderStr()));
                    age.setText(StringUtil.setText(partiesBean.getBirthDateStr()));
                    idNumber.setText(StringUtil.setText(partiesBean.getSId()));
                    break;
                //法人或组织
                case 1:
                    naturalPersonLayout.setVisibility(View.GONE);
                    organizationLegalLayout.setVisibility(View.VISIBLE);
                    radioGroup.check(R.id.legal_organization);
                    organizationLegalName.setText(StringUtil.setText(partiesBean.getName()));
                    firmCode.setText(StringUtil.setText(partiesBean.getSId()));
                    chargeName.setText(StringUtil.setText(partiesBean.getPersonInCharge()));
                    chargeDuty.setText(StringUtil.setText(partiesBean.getDuty()));
                    break;
                default:
                    break;
            }
            addressAParties.setText(StringUtil.setText(partiesBean.getAddress()));
            contactParties.setText(StringUtil.setText(partiesBean.getTel()));
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

    @Subscriber(tag = RouterHub.APP_ENFORCELAWLAWADDACTIVITY)
    private void eventBusCallback(EventBusBean<LocationAndName> eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_LOCATION:
                    LocationAndName data = eventBusBean.getData();
                    crimeAddress.setText(data.getName());
                    crimeAddress.setTag(gson.toJson(data.getLatLng()));
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMatterList(List<MatterBean> o, boolean isLoadMore) {
        try {
            for (MatterBean matterBean : o) {
                matterBean.setLeaf(matterBean.getChildrenCount() == 0);
            }
            if (popup == null) {
                popup = new CommonTreeListPopup<MatterBean>(this);
                popup.setListener(new CommonTreeListPopup.CommonTreeListPopupListener<MatterSecondBean>() {
                    @Override
                    public void onItemClickGetSubset(String elementId) {
                        mPresenter.getMatterSecondList(elementId);
                    }

                    @Override
                    public void onItemClickInput(MatterSecondBean da) {
                        selectMatter.setText(da.getName());
                        selectMatter.setTag(da.getId());
                        popup.dismiss();
                    }

                    @Override
                    public void onSearchByKeyword(String keyWord) {
                        mKeyWord = keyWord;
                    }

                    @Override
                    public void onLoadMore() {

                    }
                });
            }
            if (!popup.isShowing()) {
                popup.showPopupWindow();
            }
            popup.setData(o, isLoadMore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMatterSecondList(List<MatterSecondBean> o) {
        try {
            for (MatterSecondBean matterBean : o) {
                matterBean.setLeaf(true);
                matterBean.setLevel(1);
            }
            if (popup != null && popup.isShowing()) {
                popup.setDataTree(o);
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
    public void setLawSort(List<NameAndIdBean> o) {
        listShowPopup(o, lawSort);
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
            commonRecyclerPopupWindow = new CommonRecyclerPopupWindow<>(this,
                    commonRecyclerPopupAdapter, (adapter, view, position) -> {
                        NameAndIdBean bean = (NameAndIdBean) adapter.getItem(position);
                        if (bean != null) {
                            tv.setText(bean.getName());
    //                        if (tv.getId() == R.id.transaction_p) {
    //                            tv.setTag(bean.getId2());
    //                        } else {
                                tv.setTag(bean.getId());
    //                        }
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
                                FileUtil.openFile(EnforceLawLawAddActivity.this, new File(bean.getSourceFilePath()));
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

    @Override
    public void setPartiesList(List<PartiesBean> bean, boolean isLoadMore) {
        if (showToListDialog != null && showToListDialog.isShowing()) {
            showToListDialog.setNewData(bean, isLoadMore);
        }
    }

    @Override
    public void setPartiesInfo(PartiesBean partiesBean) {
        try {
            if (StringUtil.isNotNullString(partiesBean.getBirthDate())) {
                int age = TimeUtil.getAge(TimeUtil.stringToDateForFotmat(partiesBean.getBirthDate(), Constant.YMD1));
                partiesBean.setBirthDateStr(String.valueOf(age));
            }
            partiesSetToView(partiesBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showOpenGpsDialog() {
        try {
            new CommonDialog.Builder(this).setTitle("提示")
                    .setContent("GPS未开启，会导致您的定位不准确")
                    .setNegativeBtnShow(false)
                    .setCancelable(false)
                    .setPositiveButton("去设置", (v, commonDialog) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, Constant.GPS_STATE);
                    }).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAuditorList(List<NameAndIdBean> bean, boolean isLoadMore) {
        listShowPopup(bean, transactionP);
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
}
