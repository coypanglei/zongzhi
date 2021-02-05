package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.SelectDialog;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideImageLoaderPic;
import com.weique.overhaul.v2.app.utils.ImagePathUtill;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInspectionAddComponent;
import com.weique.overhaul.v2.mvp.contract.InspectionAddContract;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionAddBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;
import com.weique.overhaul.v2.mvp.model.entity.InspectionOneAddBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.InspectionAddPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ImagePickerAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity.IS_NEW_POINT;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity.POINTS_IN_JSON;


/**
 * ================================================
 * 巡检添加记录（备注，图片）
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INSPECTIONADDACTIVITY)
public class InspectionAddActivity extends BaseActivity<InspectionAddPresenter> implements InspectionAddContract.View, ImagePickerAdapter.OnRecyclerViewItemClickListener {

    private int HASH_CODE = 0;

    public static final String TARGET_NEW_POINT = "TARGET_NEW_POINT";//巡检第二级列表进入新增传值
    public static final String NEW = "NEW";//巡检第一级列表新增按钮传值
    public static final String RESET = "RESET";//巡检第二级列表进入修改传值
    public static final String TAGETNEW = "TAGETNEW";//巡检第二级列表进入,增加已有巡检点内的新的一条巡检信息
    public static final String NEW_TASK = "NEW_TASK";//首页代办任务进去传值
    public static final String MINE_TASK = "MINE_TASK";//个人中心任务列表

    @Inject
    AppManager mAppManager;

    @Inject
    Gson gson;

    /**
     * 巡检个人当前位置
     */
    @Autowired(name = POINTS_IN_JSON)
    String latLngs;
    /**
     * 修改某条巡检的经纬度
     */
    @Autowired(name = "RESET_LAT")
    String reset_latLngs;

    /**
     * 图片路径
     */
    @Autowired(name = "imageUrl")
    String imageUrl;

    /**
     * 图片路径
     */
    @Autowired(name = ARouerConstant.TITLE)
    String title;


    @Autowired(name = "listModel")
    InspectionLatLng.ListBean model;

    @Autowired(name = "xunjianModel")
    ResourceSearchDetailItemBean.ListBean listBean;


    /**
     * 是否新建巡检点
     */
    @Autowired(name = IS_NEW_POINT)
    String is_new_point;

    /**
     * 資源名
     */
    public static final String RESOURCE = "resource";


    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "addInspId")
    String addInspId;


    /**
     * 待办任务穿过来的model
     */
    @Autowired(name = "TASK_MODEL")
    PatrolsDetailItemBean.ElementsBean elementsBean;

    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "isComplete")
    boolean complete;

    /**
     * 待办任务穿过来的
     */
    @Autowired(name = "NEW_TASK_ITEM")
    int new_task_item;


    /**
     * 个人中心任务列表
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    @Autowired(name = "MissionConditionsBean")
    TaskListBean.ListBean.MissionConditionsBean missionConditionsBean;

    @Autowired(name = "MINE_TASK_MODEL")
    InformationTypeOneSecondBean.ElementListBean ElementListBean;//选择资源返回的数据


    @Autowired(name = RESOURCE)
    String resource;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_submit)
    RelativeLayout toolbarSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_resource)
    LinearLayout selectResource;
    @BindView(R.id.remind)
    EditText remind;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_select_resource)
    TextView tvSelectResource;

    private String ElementId;//上传图片需要的ID

    private InformationTypeOneSecondBean.ElementListBean elementListBean = null;
    /**
     * 相机
     */
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;


    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 4;               //允许选择图片最大数


    private String inspId = "";


    @Subscriber(tag = RouterHub.APP_TOURVISITACTIVITY)
    private void updateList(EventBusBean eventBusBean) {
        try {
            elementListBean = (InformationTypeOneSecondBean.ElementListBean) eventBusBean.getData();
            tvSelectResource.setText(elementListBean.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInspectionAddComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inspection_add;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        HASH_CODE = this.hashCode();
        AccessControlUtil.controlByLevelCommunity(toolbarSubmit);
        setTitle(title);
        initImagePicker();
        initWidget();
        if (complete) {
            toolbarSubmit.setVisibility(View.GONE);
        }
        try {
            if (is_new_point.equals(InspectionAddActivity.RESET)) {//修改巡檢信息
                selectResource.setClickable(false);
                remind.setText(model.getMemo());
                tvSelectResource.setText(resource);

                List<String> jsonList = new ArrayList<>();
                jsonList = gson.fromJson(imageUrl, new TypeToken<List<String>>() {
                }.getType());

                for (int i = 0; i < jsonList.size(); i++) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = jsonList.get(i);
                    selImageList.add(imageItem);
                }
                adapter.setImages(selImageList);

            } else if (is_new_point.equals(InspectionAddActivity.TAGETNEW)) {//增加已有巡检点内的新的一条巡检信息
                selectResource.setClickable(false);
                tvSelectResource.setText(resource);
            } else if (is_new_point.equals(InspectionAddActivity.TARGET_NEW_POINT)) {//增加已有巡检点的新的巡检信息
                selectResource.setClickable(false);
                tvSelectResource.setText(resource);
            } else if (is_new_point.equals(InspectionAddActivity.NEW_TASK)) {


                selectResource.setClickable(false);
                tvSelectResource.setText(resource);
                if (new_task_item == 0) {
                    remind.setText(model.getMemo());

                    List<String> jsonList = new ArrayList<>();
                    jsonList = gson.fromJson(imageUrl, new TypeToken<List<String>>() {
                    }.getType());

                    for (int i = 0; i < jsonList.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = jsonList.get(i);
                        selImageList.add(imageItem);
                    }
                    adapter.setImages(selImageList);
                }


            } else if (is_new_point.equals(InspectionAddActivity.MINE_TASK)) {
                selectResource.setClickable(false);
                tvSelectResource.setText(resource);


            } else {
                selectResource.setClickable(true);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


        remind.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().trim().length() >= 150) {
                    ArmsUtils.makeText("您最多能输入150个字！");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!isFinishing()) {
            dialog.show();
        }
    }

    private void initWidget() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    ArrayList<ImageItem> images = null;

    /**
     * 拍照
     */
    private void initImagePicker() {
        try {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoaderPic());   //设置图片加载器
            imagePicker.setShowCamera(true);                      //显示拍照按钮
            imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
            imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
            imagePicker.setSelectLimit(4);              //选中数量限制
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
            imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
            imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
            imagePicker.setOutPutX(400);                         //保存文件的宽度。单位像素
            imagePicker.setOutPutY(340);                         //保存文件的高度。单位像素
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        try {
            switch (position) {
                case IMAGE_ITEM_ADD:
                    if (UserInfoUtil.getUserInfo().getEnumCommunityLevel() > StandardAddressStairBean.COMMUNITY) {
                        ArmsUtils.makeText("您没有权限修改！");
                    } else if (complete) {

                    } else {
                        if (elementListBean == null && listBean == null && model == null && ElementListBean == null && elementsBean == null) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_select_resource));
                        } else {
                            if (InspectionAddActivity.RESET.equals(is_new_point)) {
                                ElementId = model.getId();
                            } else if (InspectionAddActivity.TAGETNEW.equals(is_new_point)) {
                                ElementId = listBean.getId();
                            } else if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {
                                ElementId = listBean.getId();
                            } else if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                                ElementId = elementsBean.getId();
                            } else if (InspectionAddActivity.MINE_TASK.equals(is_new_point)) {
                                ElementId = ElementListBean.getId();
                            } else {
                                ElementId = elementListBean.getId();
                            }

                            List<String> names = new ArrayList<>();
                            names.add("拍照");
                            mPresenter.getPermission(maxImgCount, selImageList, names);
                        }
                    }
                    break;
                default:
                    //打开预览
                    Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    intentPreview.putExtra("isCompete", complete);
                    startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                //添加图片返回
                if (data != null && requestCode == REQUEST_CODE_SELECT) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    List<String> list = new ArrayList<>();
                    String s = ImagePathUtill.compressImage(images.get(0).path);
                    list.add(s);
                    assert mPresenter != null;
                    mPresenter.upLoadFile(ElementId, list);
                }
            } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                //预览图片返回
                if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                    if (images != null) {
                        selImageList.clear();
                        selImageList.addAll(images);
                        adapter.setImages(selImageList);
                    }
                }
            }
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
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
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
    public void setInspectionData(String itemBean) {
        try {
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.HAS_NEW_MESSAGE, is_new_point, inspId), RouterHub.APP_INSPECTIONADDACTIVITY);
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.IS_REFRESH), RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT);

            killMyself();
            if (is_new_point.equals(InspectionAddActivity.MINE_TASK)) {
                mAppManager.killActivity(InspectionMapActivity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setInspectionOneData(String itemBean) {
        try {
            inspId = itemBean;
            InspectionAddBean inspectionAddBean = new InspectionAddBean();
            inspectionAddBean.setInspectionRecordId(itemBean);
            inspectionAddBean.setPointInJson(latLngs);


            List<String> imgPath = new ArrayList<>();
            for (ImageItem imageItem : selImageList) {
                imgPath.add(imageItem.path);
            }
            String ImageJson = gson.toJson(imgPath);
            inspectionAddBean.setIRImageUrlsInJson(ImageJson);
            inspectionAddBean.setMemo(remind.getText().toString());
            inspectionAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
            inspectionAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

            if (!ArmsUtils.isEmpty(elementsBean)) {
                inspectionAddBean.setTaskId(elementsBean.getTaskId());
            }

            assert mPresenter != null;
            mPresenter.setInspectionData(false, false, inspectionAddBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans) {
        try {
            for (UploadFileRsponseBean uploadFileRsponseBean : uploadFileRsponseBeans) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = uploadFileRsponseBean.getUrl();
                selImageList.add(imageItem);
            }
            adapter.setImages(selImageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.toolbar_submit, R.id.select_resource})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.toolbar_back:
                    break;
                case R.id.toolbar_submit:
                    InspectionAddBean inspectionAddBean = new InspectionAddBean();
                    if (InspectionAddActivity.RESET.equals(is_new_point)) {
                        inspectionAddBean.setId(model.getId());
                        inspectionAddBean.setInspectionRecordId(listBean.getId());
                        inspectionAddBean.setPointInJson(reset_latLngs);

                        List<String> imgPath = new ArrayList<>();
                        for (ImageItem imageItem : selImageList) {
                            imgPath.add(imageItem.path);
                        }
                        String ImageJson = gson.toJson(imgPath);
                        inspectionAddBean.setIRImageUrlsInJson(ImageJson);
                        inspectionAddBean.setMemo(remind.getText().toString());
                        inspectionAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                        inspectionAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                        assert mPresenter != null;
                        mPresenter.setInspectionData(false, false, inspectionAddBean);

                    } else if (InspectionAddActivity.TAGETNEW.equals(is_new_point)) {

                        if (TextUtils.isEmpty(remind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_inspect_record));
                        } else {
                            inspectionAddBean.setInspectionRecordId(listBean.getId());
                            inspectionAddBean.setPointInJson(latLngs);

                            List<String> imgPath = new ArrayList<>();
                            for (ImageItem imageItem : selImageList) {
                                imgPath.add(imageItem.path);
                            }
                            String ImageJson = gson.toJson(imgPath);
                            inspectionAddBean.setIRImageUrlsInJson(ImageJson);
                            inspectionAddBean.setMemo(remind.getText().toString());
                            inspectionAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            inspectionAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

                            assert mPresenter != null;
                            mPresenter.setInspectionData(false, false, inspectionAddBean);
                        }


                    } else if (InspectionAddActivity.TARGET_NEW_POINT.equals(is_new_point)) {

                        if (TextUtils.isEmpty(remind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_inspect_record));
                        } else {
                            InspectionOneAddBean inspectionOneAddBean = new InspectionOneAddBean();
                            inspectionOneAddBean.setResourceId(listBean.getcId());
                            inspectionOneAddBean.setResourceName(resource);
                            inspectionOneAddBean.setElementTypeId(listBean.getElementTypeId());
                            inspectionOneAddBean.setDepartmentId(listBean.getDepartmentId());
                            inspectionOneAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            inspectionOneAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

                            assert mPresenter != null;
                            mPresenter.setInspectionOneData(false, false, inspectionOneAddBean);
                        }

                    } else if (InspectionAddActivity.NEW_TASK.equals(is_new_point)) {
                        if (TextUtils.isEmpty(remind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_inspect_record));
                        } else {
                            if (TextUtils.isEmpty(addInspId)) {
                                if (TextUtils.isEmpty(elementsBean.getInspectionRecordId())) {
                                    InspectionOneAddBean inspectionOneAddBean = new InspectionOneAddBean();
                                    inspectionOneAddBean.setResourceId(elementsBean.getId());
                                    inspectionOneAddBean.setResourceName(elementsBean.getName());
                                    inspectionOneAddBean.setElementTypeId(elementsBean.getElementTypeId());
                                    inspectionOneAddBean.setDepartmentId(elementsBean.getDepartmentId());
                                    inspectionOneAddBean.setTaskId(elementsBean.getTaskId());
                                    inspectionOneAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                                    inspectionOneAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

                                    assert mPresenter != null;
                                    mPresenter.setInspectionOneData(false, false, inspectionOneAddBean);
                                } else {
                                    InspectionAddBean inspectionAddBean1 = new InspectionAddBean();
                                    inspectionAddBean1.setInspectionRecordId(elementsBean.getInspectionRecordId());
                                    inspectionAddBean1.setPointInJson(latLngs);
                                    inspId = elementsBean.getInspectionRecordId();
                                    List<String> imgPath = new ArrayList<>();
                                    for (ImageItem imageItem : selImageList) {
                                        imgPath.add(imageItem.path);
                                    }
                                    String ImageJson = gson.toJson(imgPath);
                                    inspectionAddBean1.setIRImageUrlsInJson(ImageJson);
                                    inspectionAddBean1.setMemo(remind.getText().toString());
                                    inspectionAddBean1.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                                    inspectionAddBean1.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

                                    assert mPresenter != null;
                                    mPresenter.setInspectionData(false, false, inspectionAddBean1);
                                }
                            } else {
                                InspectionAddBean inspectionAddBean1 = new InspectionAddBean();
                                inspectionAddBean1.setInspectionRecordId(addInspId);
                                inspectionAddBean1.setPointInJson(latLngs);
                                inspId = addInspId;
                                List<String> imgPath = new ArrayList<>();
                                for (ImageItem imageItem : selImageList) {
                                    imgPath.add(imageItem.path);
                                }
                                String ImageJson = gson.toJson(imgPath);
                                inspectionAddBean1.setIRImageUrlsInJson(ImageJson);
                                inspectionAddBean1.setMemo(remind.getText().toString());
                                inspectionAddBean1.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                                inspectionAddBean1.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());

                                assert mPresenter != null;
                                mPresenter.setInspectionData(false, false, inspectionAddBean1);
                            }
                        }
                    } else if (InspectionAddActivity.MINE_TASK.equals(is_new_point)) {

                        if (TextUtils.isEmpty(remind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_inspect_record));
                        } else {
                            InspectionOneAddBean inspectionOneAddBean = new InspectionOneAddBean();
                            inspectionOneAddBean.setResourceId(ElementListBean.getId());
                            inspectionOneAddBean.setResourceName(ElementListBean.getName());
                            inspectionOneAddBean.setElementTypeId(ElementListBean.getElementTypeId());
                            inspectionOneAddBean.setDepartmentId(ElementListBean.getDepartmentId());
                            inspectionOneAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            inspectionOneAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                            assert mPresenter != null;
                            mPresenter.setInspectionOneData(false, false, inspectionOneAddBean);
                        }

                    } else {
                        if (ArmsUtils.isEmpty(elementListBean)) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_select_resource));
                        } else if (TextUtils.isEmpty(remind.getText().toString())) {
                            ArmsUtils.makeText(getResources().getString(R.string.input_inspect_record));
                        } else {
                            InspectionOneAddBean inspectionOneAddBean = new InspectionOneAddBean();
                            inspectionOneAddBean.setResourceId(elementListBean.getId());
                            inspectionOneAddBean.setResourceName(elementListBean.getName());
                            inspectionOneAddBean.setElementTypeId(elementListBean.getElementTypeId());
                            inspectionOneAddBean.setDepartmentId(elementListBean.getDepartmentId());
                            inspectionOneAddBean.setCreateUserId(UserInfoUtil.getUserInfo().getUid());
                            inspectionOneAddBean.setUpdateUserId(UserInfoUtil.getUserInfo().getUid());
                            assert mPresenter != null;
                            mPresenter.setInspectionOneData(false, false, inspectionOneAddBean);
                        }
                    }
                    break;
                case R.id.select_resource:
                    if (InspectionAddActivity.MINE_TASK.equals(is_new_point)) {
                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONTYPESECONDACTIVITY)
                                .withString(ARouerConstant.ID, missionConditionsBean.getElementTypeId())
                                .withString(ARouerConstant.TITLE, missionConditionsBean.getName())
                                .withString(InspectionMapActivity.IS_NEW_POINT, is_new_point)
                                .navigation();
                    } else {

                        ARouter.getInstance().build(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_INSPECTIONMAPACTIVITY)
                                .withInt(TourVisitActivity.TYPE, TourVisitActivity.TYPE_IPQC)
                                .navigation();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
