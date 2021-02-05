package com.weique.overhaul.v2.mvp.ui.activity.matter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerMatterDetailComponent;
import com.weique.overhaul.v2.mvp.contract.MatterDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.HelperListItemBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MatterDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.DialogAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.HelperItemAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.dialogFragment.CommonDialogFragment;
import com.weique.overhaul.v2.mvp.ui.dialogFragment.DialogFragmentHelper;
import com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterDetailTableFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_MATTERDETAILACTIVITY)
public class MatterDetailActivity extends BaseActivity<MatterDetailPresenter> implements MatterDetailContract.View {

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.staging)
    Button staging;
    @BindView(R.id.submit)
    Button submit;

    @Inject
    Gson gson;

    private MyPagerAdapter mAdapter;
    private ArrayList<Fragment> mFragments;

    /**
     * 上个界面跳转的id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * 上个界面跳转的id
     */
    @Autowired(name = ARouerConstant.ID2)
    String eventRecordId;

    /**
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment#MY} 我的 - 查看
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment#SPONSOR}
     * {@link com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment#SYNERGY}
     * 这个字段区分 是 来干嘛的
     */
    @Autowired(name = ARouerConstant.TYPE)
    String type;
    /**
     * 订单状态
     */
    @Autowired(name = ARouerConstant.STATUS)
    int status;
    String[] orderTab = {"基本信息", "流程信息", "催办信息", "督办信息"};
    private ArrayList<InformationItemPictureBean> list;
    private DialogAddPhotoAdapter informationAddPhotoAdapter;
    private CommonDialogFragment dialogFragment;
    private CommonDialogFragment synergyFragment;
    private RecyclerView synergyRecyclerView;
    private CommonRecyclerPopupAdapter<NameAndIdBean> synergyAdapter;
    private HelperItemAdapter helperItemAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMatterDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_matter_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("事项详情");
        setOrderEnum();
        initOperation();
    }

    /**
     * 初始化 操作 按钮  根据 type 我的 主办 协办  和 订单状态 status 控制
     * "key": 0,"value": "已核查"
     * "key": 1,"value": "等待受理"
     * "key": 2,"value": "已受理"
     * "key": 3,"value": "正在处置"
     * "key": 4,"value": "处置完毕"
     * "key": 5,"value": "等待评价"
     * "key": 6,"value": "评价完毕"
     * "key": 7,"value": "等待归档"
     * "key": 8,"value": "归档"
     * "key": 9,"value": "退回"
     * "key": 10,"value": "作废"
     * "key": 11,"value": "撤回"
     * "key": -1,"value": "暂存"
     */
    public void initOperation() {
        switch (type) {
            //我的
            case MatterListFragment.MY:
                layout.setVisibility(View.GONE);
                break;
            //主办
            case MatterListFragment.SPONSOR:
                layout.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                staging.setVisibility(View.VISIBLE);
                setSponsorBtn();
                break;
            //协同
            case MatterListFragment.SYNERGY:
                layout.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                staging.setVisibility(View.VISIBLE);
                submit.setText("同意");
                staging.setText("拒绝");
//                submit.setVisibility(View.VISIBLE);
//                submit.setText("协同办理");
                break;
            default:
                break;
        }

    }


    /**
     * 主办时 显示的操作
     */
    private void setSponsorBtn() {
        switch (status) {
            case 1:
                submit.setText("受理");
                staging.setText("退回");
                break;
            case 3:
                submit.setText("办理");
                staging.setText("请求协同");
                break;
            default:
                layout.setVisibility(View.GONE);
                break;
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

    public void setOrderEnum() {
        try {
            mFragments = new ArrayList<>();
            for (int j : MatterDetailTableFragment.INFOS) {
                mFragments.add(MatterDetailTableFragment.newInstance(eventRecordId, id, j));
            }
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, orderTab);
            vp.setAdapter(mAdapter);
            tl2.setViewPager(vp, orderTab);
            vp.setOffscreenPageLimit(orderTab.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.submit, R.id.staging})
    public void onViewClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.submit:
                    //受理
                    if (type.equals(MatterListFragment.SPONSOR)) {
                        if (status == 1) {
                            new CommonDialog.Builder(this).
                                    setContent("确定受理").
                                    setPositiveButton((view, commonDialog) -> {
                                        mPresenter.accept(id, 2);
                                    }).create().show();
                            //处置
                        } else if (status == 3) {
                            mPresenter.getHelperList(eventRecordId);
                            break;
                        }
                        //协同操作
                    } else if (type.equals(MatterListFragment.SYNERGY)) {
                        // 协同同意
                        new CommonDialog.Builder(this).
                                setContent("同意协办此事项").
                                setPositiveButton((view, commonDialog) -> {
                                    mPresenter.consentHelp(2, id, null);
                                }).create().show();
                    }
                    break;

                case R.id.staging:
                    //主办操作
                    if (type.equals(MatterListFragment.SPONSOR)) {
                        //退回
                        if (status == 1) {
                            View view = getLayoutInflater().inflate(R.layout.dialog_order_handle, null, false);
                            dialogFragment = DialogFragmentHelper.showEvalutateDialog(view, true, null);
                            TextView title = view.findViewById(R.id.title);
                            title.setText("退回原因");
                            EditText editText = view.findViewById(R.id.content);
                            editText.setFocusable(true);
                            editText.setFocusableInTouchMode(true);
                            editText.requestFocus();
                            TextView impose = view.findViewById(R.id.impose);
                            Button confirm = view.findViewById(R.id.confirm);
                            confirm.setText("退回");
                            confirm.setOnClickListener(v1 -> {
                                if (StringUtil.isNullString(editText.getText().toString()) ||
                                        editText.getText().toString().length() < 6) {
                                    ArmsUtils.makeText("请填写退回信息(最少6个文字)");
                                    return;
                                }
                                mPresenter.returnOrder(id, 1, editText.getText().toString());
                            });
                            dialogFragment.show(getSupportFragmentManager(), "");
                            //请求协同
                        } else if (status == 3) {
                            View view = getLayoutInflater()
                                    .inflate(R.layout.dialog_order_request_synergy, null, false);
                            synergyFragment = DialogFragmentHelper
                                    .showEvalutateDialog(view, true, null);
                            EditText searchText = view.findViewById(R.id.search_text);
                            searchText.setFocusable(true);
                            searchText.setFocusableInTouchMode(true);
                            searchText.requestFocus();
                            EditText editText = view.findViewById(R.id.content);
                            editText.setFocusable(true);
                            editText.setFocusableInTouchMode(true);
                            editText.requestFocus();
                            ImageView search = view.findViewById(R.id.search);
                            search.setOnClickListener(v2 -> {
                                if (StringUtil.isNullString(searchText.getText().toString())) {
                                    ArmsUtils.makeText("请输入协同人姓名");
                                    return;
                                }
                                mPresenter.getUserInfoForCoop(searchText.getText().toString());
                            });
                            synergyRecyclerView = view.findViewById(R.id.recycler_view);
                            Button confirm = view.findViewById(R.id.confirm);
                            confirm.setOnClickListener(v1 -> {
                                if (synergyAdapter == null || synergyAdapter.getListByPosList().size() <= 0) {
                                    ArmsUtils.makeText("请搜索并选择协同人");
                                    return;
                                }
                                if (StringUtil.isNullString(editText.getText().toString())) {
                                    ArmsUtils.makeText("请填写协同说明");
                                    return;
                                }
                                List<NameAndIdBean> listByPosList = synergyAdapter.getListByPosList();
                                List<String> ids = new ArrayList<>();
                                Map<String, Object> CoopInfoMap = new HashMap<>();
                                Map<String, Object> paramMap = new HashMap<>();
                                for (NameAndIdBean andIdBean : listByPosList) {
                                    ids.add(andIdBean.getId());
                                }
                                paramMap.put("SelectedUserCoopList", ids);
                                paramMap.put("coopDesc", editText.getText().toString());
                                CoopInfoMap.put("CoopInfo", paramMap);
                                mPresenter.requestSynergy(gson.toJson(CoopInfoMap), eventRecordId);
                            });
                            synergyFragment.show(getSupportFragmentManager(), "");
                        }
                        //协同操作
                    } else if (type.equals(MatterListFragment.SYNERGY)) {
                        //协同 拒绝
                        View view = getLayoutInflater().inflate(R.layout.dialog_order_handle, null, false);
                        dialogFragment = DialogFragmentHelper.showEvalutateDialog(view, true, null);
                        TextView title = view.findViewById(R.id.title);
                        title.setText("拒绝协同");
                        EditText editText = view.findViewById(R.id.content);
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        editText.setHint("请输入拒绝理由(最少6个文字)");
                        TextView impose = view.findViewById(R.id.impose);
                        Button confirm = view.findViewById(R.id.confirm);
                        confirm.setText("拒绝协同");
                        dialogFragment.setmCancelListener(new CommonDialogFragment.OnDialogCancelListener() {
                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onDismiss() {
                                if (!ArmsUtils.isEmpty(list)) {
                                    list = null;
                                }
                            }
                        });
                        confirm.setOnClickListener(v1 -> {
                            if (StringUtil.isNullString(editText.getText().toString()) ||
                                    editText.getText().toString().length() < 6) {
                                ArmsUtils.makeText("请填写拒绝协同理由(最少六个文字)");
                                return;
                            }
                            mPresenter.consentHelp(3, id, editText.getText().toString());
                        });
                        dialogFragment.show(getSupportFragmentManager(), "");
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAccept() {
        //受理成功 订单改为正在办理状态
        status = 3;
        initOperation();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    private void initAdapter(RecyclerView recyclerView) {
        try {
            list = new ArrayList<>();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));
            informationAddPhotoAdapter = new DialogAddPhotoAdapter(list);
            informationAddPhotoAdapter.setMaxItem(10);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                    .Builder(AppManager.getAppManager().getmApplication())
                    .colorResId(R.color.white)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(AppManager
                    .getAppManager().getmApplication(),
                    4, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(informationAddPhotoAdapter);
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (view.getId() == R.id.image_) {
                        assert bean != null;
                        if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                            if (adapter.getData().size() >= 8) {
                                ArmsUtils.makeText("最多上传7张图片");
                                return;
                            }
                            //9  是加上了默认的加号 图标
                            int max = 8 - adapter.getData().size();
                            mPresenter.getPermission(max);
                        } else {
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                    .navigation();
                        }
                    } else if (view.getId() == R.id.remove_image) {
                        adapter.remove(position);
                        adapter.notifyItemChanged(position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goToPhotoAlbum(int max) {
        PictureSelectorUtils.gotoPhoto(this, PictureMimeType.ofImage(),
                max, false, false, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        try {
                            List<String> strings = new ArrayList<>();
                            for (LocalMedia media : result) {
                                if (StringUtil.isNotNullString(media.getCompressPath())) {
                                    strings.add(media.getCompressPath());
                                } else {
                                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                        strings.add(PictureFileUtils
                                                .getPath(MatterDetailActivity.this,
                                                        Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath())
                                                && new File(media.getPath()).exists()) {
                                            strings.add(media.getPath());
                                        }
                                    }
                                }
                            }
                            mPresenter.upLoadFile("", strings);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    @Override
    public void setFiles(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            if (informationAddPhotoAdapter != null) {
                for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                    String path = uploadFileRsponseBean.getUrl();
                    list.add(new InformationItemPictureBean((path)));
                }
                informationAddPhotoAdapter.setNewData(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDispose() {
        try {
            EventBus.getDefault().
                    post(new EventBusBean(EventBusConstant.COMMON_UPDATE),
                            RouterHub.APP_MATTERLISTACTIVITY);
            if (dialogFragment != null) {
                dialogFragment.dismiss();
                dialogFragment = null;
            }
            killMyself();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnOrderSuccess() {
        EventBus.getDefault().
                post(new EventBusBean(EventBusConstant.COMMON_UPDATE),
                        RouterHub.APP_MATTERLISTACTIVITY);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
            dialogFragment = null;
        }
        killMyself();
    }

    @Override
    public void setUserInfoForCoop(List<NameAndIdBean> o) {
        try {
            if (synergyFragment != null) {
                if (synergyRecyclerView != null) {
                    synergyAdapter = new CommonRecyclerPopupAdapter<>();
                    ArmsUtils.configRecyclerView(synergyRecyclerView, new LinearLayoutManager(this));
                    synergyAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
                        try {
                            synergyAdapter.setCheckPos(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    synergyAdapter.setEmptyView(R.layout.null_content_home_layout, synergyRecyclerView);
                    synergyRecyclerView.setAdapter(synergyAdapter);
                    synergyAdapter.setNewData(o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRequestSynergy(Object o) {
        if (synergyFragment != null) {
            synergyFragment.dismiss();
            synergyFragment = null;
            synergyAdapter = null;
        }
    }

    @Override
    public void setConsentHelp(Object o) {
        EventBus.getDefault().
                post(new EventBusBean(EventBusConstant.COMMON_UPDATE),
                        RouterHub.APP_MATTERLISTACTIVITY);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
            dialogFragment = null;
        }
        killMyself();
    }

    @Override
    public void setHelperList(List<HelperListItemBean> o) {
        try {
            View view = getLayoutInflater().inflate(R.layout.dialog_matter_order_handle, null, false);
            dialogFragment = DialogFragmentHelper.showEvalutateDialog(view, true, null);
            TextView title = view.findViewById(R.id.title);
            title.setText("处置意见");
            EditText editText = view.findViewById(R.id.content);
            RecyclerView helperList = view.findViewById(R.id.helper_list);
            TextView ttTitle = view.findViewById(R.id.tt_title);
            if (o == null || o.size() <= 0) {
                helperList.setVisibility(View.GONE);
                ttTitle.setVisibility(View.GONE);
            } else {
                helperList.setVisibility(View.VISIBLE);
                ttTitle.setVisibility(View.VISIBLE);
                helperItemAdapter = new HelperItemAdapter();
                ArmsUtils.configRecyclerView(helperList, new LinearLayoutManager(this));
                helperList.setAdapter(helperItemAdapter);
                helperItemAdapter.setNewData(o);
            }
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            TextView impose = view.findViewById(R.id.impose);
            Button confirm = view.findViewById(R.id.confirm);
            confirm.setText("处置");
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setVisibility(View.VISIBLE);
            initAdapter(recyclerView);
            dialogFragment.setmCancelListener(new CommonDialogFragment.OnDialogCancelListener() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onDismiss() {
                    if (!ArmsUtils.isEmpty(informationAddPhotoAdapter)) {
                        informationAddPhotoAdapter = null;
                    }
                    if (!ArmsUtils.isEmpty(list)) {
                        list = null;
                    }
                }
            });
            confirm.setOnClickListener(v1 -> {
                if (StringUtil.isNullString(editText.getText().toString())) {
                    ArmsUtils.makeText("请填写处置信息");
                    return;
                }
                List<String> urls = null;
                List<InformationItemPictureBean> data = informationAddPhotoAdapter.getData();
                if (data.size() > 0) {
                    urls = new ArrayList<>();
                    for (InformationItemPictureBean dd : data) {
                        if (dd.getType() == InformationItemPictureBean.IS_URL) {
                            urls.add(dd.getImageUrl());
                        }
                    }
                }
                List<Map<String, Object>> coopDescList = new ArrayList<>();
                if (helperItemAdapter != null) {
                    List<HelperListItemBean> helperData = helperItemAdapter.getData();
                    Map<String, Object> map;
                    for (HelperListItemBean helperDatum : helperData) {
                        map = new HashMap<>();
                        if (StringUtil.isNullString(helperDatum.getIssue())) {
                            ArmsUtils.makeText("请完善协办用户相关意见信息");
                            return;
                        } else {
                            map.put("Id", helperDatum.getId());
                            map.put("CoopDesc", helperDatum.getIssue());
                            coopDescList.add(map);
                        }
                    }
                }
                mPresenter.dispose(id, gson.toJson(coopDescList), editText.getText().toString(), urls);
            });
            dialogFragment.show(getSupportFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
