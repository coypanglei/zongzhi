package com.weique.overhaul.v2.mvp.ui.activity.lawworks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.FileUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerLawWorksOrderDetailComponent;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.FileBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderStatusBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.model.entity.event.ProgressChangeBean;
import com.weique.overhaul.v2.mvp.presenter.LawWorksOrderDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.MessageDetailAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SimplePhotoAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.jess.arms.utils.LoadingDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/23/2020 13:57
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_LAWWORKSORDERDETAILACTIVITY)
public class LawWorksOrderDetailActivity extends BaseActivity<LawWorksOrderDetailPresenter> implements LawWorksOrderDetailContract.View {

    @Inject
    Gson gson;
    /**
     * 获取订单详情的id
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    /**
     * 来源 - 上个界面  一般用于判断当前行为  和  回调位置
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    /**
     * 订单列表的标识  event 回调时 用这个标识
     */
    @Autowired(name = ARouerConstant.STATUS)
    int status;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    List<InformationItemPictureBean> list;

    @BindView(R.id.event_subclass)
    TextView eventSubclass;
    @BindView(R.id.event_name)
    TextView eventName;
    @BindView(R.id.append_address)
    TextView appendAddress;
    @BindView(R.id.judge)
    TextView judge;
    @BindView(R.id.judge_tel)
    TextView judgeTel;
    @BindView(R.id.apply_people)
    TextView applyPeople;
    @BindView(R.id.apply_people_tel)
    TextView applyPeopleTel;
    @BindView(R.id.subjected_people)
    TextView subjectedPeople;
    @BindView(R.id.subjected_people_tel)
    TextView subjectedPeopleTel;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.staging)
    Button staging;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.order_num)
    TextView orderNum;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_number)
    TextView idNumber;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.minzu)
    TextView minzu;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.limit)
    TextView limit;
    @BindView(R.id.requirements)
    TextView requirements;
    @BindView(R.id.jianshu)
    TextView jianshu;
    @BindView(R.id.recycler_photo)
    RecyclerView recyclerPhoto;
    @BindView(R.id.fujian)
    TextView fujian;
    @BindView(R.id.recycler_file)
    RecyclerView recyclerFile;
    private InformationAddPhotoAdapter informationAddPhotoAdapter;
    private SimplePhotoAdapter simplePhotoAdapter;
    private MessageDetailAdapter messageDetailAdapter;
    /**
     * 当前订单状态
     */
    private int orderStatus1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLawWorksOrderDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_law_works_order_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            ARouter.getInstance().inject(this);
            setTitle("法务订单详情");
            mPresenter.getLawDetailListData(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initadapter() {
        list = new ArrayList<>();
        informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(informationAddPhotoAdapter);
        informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                if (view.getId() == R.id.image_) {
                    if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                        if (adapter.getData().size() >= 8) {
                            ArmsUtils.makeText("最多上传8张图片");
                            return;
                        }
                        //9  是加上了默认的加号 图标
                        int max = 9 - adapter.getData().size();
                        mPresenter.getPermission(max);
                    } else if (bean.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
                        try {
                            String item = ((InformationItemPictureBean) adapter.getItem(position)).getImageUrl();
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                    .withString(PictureLookActivity.URL_, item)
                                    .navigation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (view.getId() == R.id.remove_image) {
                    adapter.remove(position);
                    adapter.notifyItemChanged(position);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
    public Activity getContext() {
        return this;
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
                                        strings.add(PictureFileUtils.getPath(LawWorksOrderDetailActivity.this, Uri.parse(media.getPath())));
                                    } else {
                                        if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
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
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                String path = uploadFileRsponseBean.getUrl();
                list.add(new InformationItemPictureBean((path)));
            }
            informationAddPhotoAdapter.setNewData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLawDetailListData(LawWorksOrderDetailBean itemBean) {
        try {
            initadapter();
            orderStatus1 = itemBean.getCODetail().getOrderStatus();
            if (orderStatus1 == LawWorksOrderStatusBean.AUDIT) {
                layout.setVisibility(View.GONE);
                staging.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
            } else if (orderStatus1 == LawWorksOrderStatusBean.ORDER) {
                layout.setVisibility(View.VISIBLE);
                staging.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                list.add(0, new InformationItemPictureBean(R.drawable.picture));
            } else if (orderStatus1 == LawWorksOrderStatusBean.FEEDBACK) {
                search.setEnabled(false);
                layout.setVisibility(View.VISIBLE);
                staging.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
            } else if (orderStatus1 == LawWorksOrderStatusBean.EVALUATE) {
                search.setEnabled(false);
                layout.setVisibility(View.VISIBLE);
                staging.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
            } else if (orderStatus1 == LawWorksOrderStatusBean.BALANCE) {
                search.setEnabled(false);
                layout.setVisibility(View.VISIBLE);
                staging.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
            }
            orderNum.setText(String.format("单号：%s", itemBean.getCODetail().getCode()));
            if (itemBean.getCODetail().getOrderStatus() == 3) {
                this.orderStatus.setText("待接单");
            }
            eventSubclass.setText(itemBean.getCODetail().getCourtOrderTypeName());
            eventName.setText(itemBean.getCODetail().getCourtOrderSourcenName());
            appendAddress.setText(itemBean.getCODetail().getDepartmentFullPath());

            judge.setText(itemBean.getCODetail().getLawerName());
            judgeTel.setText(itemBean.getCODetail().getLawerTel());

            applyPeople.setText(itemBean.getCODetail().getApplicant());
            applyPeopleTel.setText(itemBean.getCODetail().getApplicantTel());

            subjectedPeople.setText(itemBean.getCODetail().getExecutedUser());
            subjectedPeopleTel.setText(itemBean.getCODetail().getExecutedUseTel());

            idNumber.setText(itemBean.getCODetail().getExecutedUserIDCard());

            job.setText(itemBean.getCODetail().getEnumExecutedUserJobStr());
            minzu.setText(itemBean.getCODetail().getExecutedUserNation());
            sex.setText(itemBean.getCODetail().getExecutedUserGenderStr());
            birth.setText(itemBean.getCODetail().getExecutedUserDateStr());
            limit.setText(itemBean.getCODetail().getEndDateStr());
            requirements.setText(itemBean.getCODetail().getRequirement());
            jianshu.setText(itemBean.getCODetail().getDescription());

            //图片
            try {
                if (StringUtil.isNotNullString(itemBean.getCODetail().getImgsInJson())) {
                    String photo = itemBean.getCODetail().getImgsInJson();
                    List<String> tbList = gson.fromJson(photo,  new TypeToken<List<String>>() {
                    }.getType());
                    if (simplePhotoAdapter == null) {
                        simplePhotoAdapter = new SimplePhotoAdapter(tbList);
                        ArmsUtils.configRecyclerView(recyclerPhoto, new GridLayoutManager(this, 4));
                        recyclerPhoto.setAdapter(simplePhotoAdapter);
                        simplePhotoAdapter.setOnItemClickListener((adapter, view, position) -> {
                            try {
                                if (AppUtils.isFastClick()) {
                                    return;
                                }
                                String item = (String) adapter.getItem(position);
                                ARouter.getInstance()
                                        .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                        .withString(PictureLookActivity.URL_, item)
                                        .navigation();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        simplePhotoAdapter.setNewData(tbList);
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                ArmsUtils.makeText("图片加载失败");
            }

            //附件
            try {
                if (StringUtil.isNotNullString(itemBean.getCODetail().getRecordUrl())) {
                    String word = itemBean.getCODetail().getRecordUrl();
                    List<String> tbList = gson.fromJson(word,  new TypeToken<List<String>>() {
                    }.getType());
                    List<FileBean> fileBeans = new ArrayList<>();
                    FileBean fileBean;
                    for (String s : tbList) {
                        fileBean = new FileBean();
                        fileBean.setUrl(s);
                        fileBeans.add(fileBean);
                    }
                    if (messageDetailAdapter == null) {
                        messageDetailAdapter = new MessageDetailAdapter();
                        messageDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        ArmsUtils.configRecyclerView(recyclerFile, new LinearLayoutManager(this));
                        recyclerFile.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                                .colorResId(R.color.gray_eee)
                                .sizeResId(R.dimen.dp_1)
                                .build());
                        recyclerFile.setAdapter(messageDetailAdapter);
                        messageDetailAdapter.setOnItemClickListener((adapter, view, position) -> {
                            try {
                                if (AppUtils.isFastClick()) {
                                    return;
                                }
                                FileBean item = (FileBean) adapter.getItem(position);
                                String url = item.getUrl();
                                String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
                                File file = Constant.getDownloadFile(fileName);
                                if (file.exists()) {
                                    new CommonDialog.Builder(this)
                                            .setContent("文件已经下载完成，确认查看文件")
                                            .setPositiveButton("确定", (v, commonDialog) -> {
                                                //todo 打开文件
                                                FileUtil.openFile(this, file);
                                            }).create().show();
                                    return;
                                }
                                new CommonDialog.Builder(this)
                                        .setContent("确定下载<" + fileName + ">")
                                        .setPositiveButton((view1, commonDialog) -> {
                                            mPresenter.getFilePermission(url, fileName, position);
                                        }).create().show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    messageDetailAdapter.setNewData(fileBeans);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                ArmsUtils.makeText("附件加载失败");
            }


            /**
             * 如果type==3 && 4 ，照片模块只显示，不做删减（反馈的照片）
             */
            if (orderStatus1 != LawWorksOrderStatusBean.AUDIT && orderStatus1 != LawWorksOrderStatusBean.ORDER) {
                search.setText(itemBean.getCORecord().getContent());

                if (StringUtil.isNotNullString(itemBean.getCORecord().getImgsInJson())) {
                    String photo = itemBean.getCORecord().getImgsInJson();
                    List<String> tbList1 = gson.fromJson(photo,  new TypeToken<List<String>>() {
                    }.getType());
                    List<InformationItemPictureBean> list1 = new ArrayList<>();

                    for (String s : tbList1) {
                        list1.add(new InformationItemPictureBean((s), 2));
                    }
                    informationAddPhotoAdapter.setNewData(list1);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void setOrderData(String itemBean) {
        killMyself();
        eventLastUpdate();
    }

    /**
     * 提醒 上个界面更新数据
     */
    private void eventLastUpdate() {
        //不管咋样刷下 总订单  --  反馈 接单 都有可能再总订单中 所以刷新
        EventBus.getDefault().post(new EventBusBean<>(EventBusConstant.COMMON_UPDATE, LawWorksOrderStatusBean.ALL), source);
        EventBus.getDefault().post(new EventBusBean<>(EventBusConstant.COMMON_UPDATE, orderStatus1), source);
        EventBus.getDefault().post(new EventBusBean<>(EventBusConstant.COMMON_UPDATE, orderStatus1 + 1), source);
    }

    @Override
    public void setOrderIntoData(String itemBean) {
        killMyself();
        eventLastUpdate();
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.staging, R.id.submit, R.id.fujian})
    public void onViewClicked(View view) {
        try {
            switch (view.getId()) {
                case R.id.staging:
                    new CommonDialog.Builder(LawWorksOrderDetailActivity.this)
                            .setContent("确定接单？")
                            .setPositiveButton((view1, commonDialog) -> {
                                mPresenter.getOrderData(id);
                            }).create().show();

                    break;
                case R.id.submit:

                    if (search.getText().toString().isEmpty()) {
                        ArmsUtils.makeText("请填写事件简述！");
                    } else {


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
                        String ImageJson = gson.toJson(urls);


                        new CommonDialog.Builder(LawWorksOrderDetailActivity.this)
                                .setContent("确定提交反馈？")
                                .setPositiveButton((view1, commonDialog) -> {
                                    mPresenter.getOrderIntoData(id, search.getText().toString(), ImageJson);
                                }).create().show();


                    }

                    break;
                case R.id.fujian:

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收服务上传签到点位
     *
     * @param eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_LAWWORKSORDERDETAILACTIVITY)
    private void eventBusCallback(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.DOWNLOAD_PREGRESS:
                case EventBusConstant.DOWNLOAD_PREGRESS_OK:
                    ProgressChangeBean data = (ProgressChangeBean) eventBusBean.getData();
                    messageDetailAdapter.setUpdateProgress(data);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
