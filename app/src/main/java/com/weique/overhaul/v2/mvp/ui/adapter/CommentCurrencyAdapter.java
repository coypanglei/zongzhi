package com.weique.overhaul.v2.mvp.ui.adapter;


import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean;
import com.weique.overhaul.v2.mvp.model.entity.TourVistLonAndLatBean;
import com.weique.overhaul.v2.mvp.presenter.CommonCollectionPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MATTER_FLOW;
import static com.weique.overhaul.v2.app.common.Constant.MORE_IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.MORE_URL_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * 作者：PangLei on 2019/3/21 0021 11:03
 * <p>
 * 邮箱：434604925qq.com
 *
 * @author Administrator
 */
public class CommentCurrencyAdapter extends BaseQuickAdapter<BasicInformationBean.RecordsBean, BaseViewHolder> {


    ReworkBasePresenter reworkBasePresenter;

    public ReworkBasePresenter getReworkBasePresenter() {
        return reworkBasePresenter;
    }

    public void setReworkBasePresenter(ReworkBasePresenter reworkBasePresenter) {
        this.reworkBasePresenter = reworkBasePresenter;
    }


    public CommentCurrencyAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<BasicInformationBean.RecordsBean>() {
            @Override
            protected int getItemType(BasicInformationBean.RecordsBean entity) {
                //根据你的实体类来判断布局类型

                switch (entity.getParamtype()) {
                    case MATTER_FLOW:
                        return BasicInformationBean.MATTER_FLOW;
                    case IMG_ITEM:
                        return BasicInformationBean.IMG;
                    case SELECT_ITEM:
                    case ADDRESS_ITEM:
                    case DATA_ITEM:
                        return BasicInformationBean.ADDRESS;
                    case LARGE_EDIT_ITEM:
                        return BasicInformationBean.LARGE_EDIT;
                    case MORE_IMG_ITEM:
                    case MORE_URL_ITEM:
                        return BasicInformationBean.MORE_IMG;

                    default:
                        return BasicInformationBean.EDIT;
                }
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(BasicInformationBean.EDIT, R.layout.item_edit_kuang)
                .registerItemType(BasicInformationBean.IMG, R.layout.item_img_add)
                .registerItemType(BasicInformationBean.ADDRESS, R.layout.item_address)
                .registerItemType(BasicInformationBean.LARGE_EDIT, R.layout.item_edit_large)
                .registerItemType(BasicInformationBean.MORE_IMG, R.layout.item_more_img)
                .registerItemType(BasicInformationBean.MATTER_FLOW, R.layout.item_matter_flow)
        ;


    }

    @Override
    protected void convert(final BaseViewHolder helper, final BasicInformationBean.RecordsBean item) {
        try {
            helper.setIsRecyclable(false);
            if (ObjectUtils.isNotEmpty((TextView) helper.getView(R.id.tv_name_title))) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    try {
                        helper.setText(R.id.tv_name_title, Html.fromHtml(item.getTitile(), FROM_HTML_MODE_COMPACT));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        helper.setText(R.id.tv_name_title, Html.fromHtml(item.getTitile()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            final EditText mDdvXB = helper.getView(R.id.et_name);

            switch (item.getParamtype()) {
                case MATTER_FLOW:
                    try {
                        MatterDetailsFlowBean matterDetailsFlowBean = new Gson().fromJson(item.getValue(), MatterDetailsFlowBean.class);
                        helper.setText(R.id.send_orders_u, StringUtil.setText(matterDetailsFlowBean.getEventSubmitUserDepartmentName()))
                                .setText(R.id.send_orders_p, StringUtil.setText(matterDetailsFlowBean.getEventSubmitUserEmployeeName()))
                                .setText(R.id.processing_unit, StringUtil.setText(matterDetailsFlowBean.getDepartmentName()))
                                .setText(R.id.processor, StringUtil.setText(matterDetailsFlowBean.getEmployeeName()))
                                .setText(R.id.way, StringUtil.setText(matterDetailsFlowBean.getEnumEventRecordTransitTypeStr()))
                                .setText(R.id.state, StringUtil.setText(matterDetailsFlowBean.getEnumOrderStatusStr()))
                                .setText(R.id.time_astrict, StringUtil.setText("无"))
                        ;
//                        if(StringUtil.isNotNullString(EnumEventProceedStatusStr)){
//
//                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
                case SELECT_ITEM:
                case DATA_ITEM:
                    /*
                  设置不可编辑，但是可以点击
                 */
                    helper.addOnClickListener(R.id.iv_address);
                    helper.addOnClickListener(R.id.et_name);
                    mDdvXB.setCursorVisible(false);
                    mDdvXB.setFocusable(false);
                    mDdvXB.setFocusableInTouchMode(false);
                /*
                 设置默认值
                 */
                    if (ObjectUtils.isNotEmpty(item.getValue())) {
                        mDdvXB.setText(Html.fromHtml(item.getValue()));
                    } else {
                        if (item.isEdit()) {
                            mDdvXB.setHint(Html.fromHtml(item.getDefaultvalue()));
                        }
                    }
                    final ImageView iv = helper.getView(R.id.iv_address);
                    setImgResource(iv, item.getParamtype());
                    break;
                case IMG_ITEM:
                    helper.addOnClickListener(R.id.rl_img_add);
                    if (ObjectUtils.isNotEmpty(item.getValue())) {
                        ImageView imageView = helper.getView(R.id.iv_add);
                        GlideUtil.loadBlurImage(mContext, item.getValue(), imageView, 10);
                    }
                    break;

                case EDIT_ITEM:
                case NUMBER_EDIT_ITEM:
                case LARGE_EDIT_ITEM:
                    setEditItem(mDdvXB, item);
                    break;
                case ADDRESS_ITEM:
                    if (item.isEdit()) {
                        helper.addOnClickListener(R.id.iv_address);
                    }
                    setEditItem(mDdvXB, item);
                    break;
                case MORE_IMG_ITEM:
                case MORE_URL_ITEM:
                    RecyclerView recyclerView = helper.getView(R.id.rv_images);
                    InformationAddPhotoAdapter informationAddPhotoAdapter = new InformationAddPhotoAdapter();
                    ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false));
                    informationAddPhotoAdapter.setEmptyView(R.layout.null_content_home_layout, recyclerView);
                    recyclerView.setAdapter(informationAddPhotoAdapter);
                    if (ObjectUtils.isNotEmpty(item.getValue())) {
                        Gson gson = new Gson();
                        List<String> imgeList = new ArrayList<>();
                        if (item.getValue().contains("[")) {
                            imgeList = gson.fromJson(item.getValue(), ArrayList.class);
                        } else {
                            imgeList.add(item.getValue());
                        }
                        List<InformationItemPictureBean> list = new ArrayList<>();
                        for (String imageList : imgeList) {
                            if (item.isEdit()) {
                                list.add(new InformationItemPictureBean(imageList));
                            } else {
                                list.add(new InformationItemPictureBean(imageList
                                        , InformationItemPictureBean.IS_VIS_DELETE));
                            }
                        }
                        if (item.isEdit()) {
                            if (MORE_IMG_ITEM.equals(item.getParamtype())) {
                                list.add(0, new InformationItemPictureBean(R.drawable.picture));
                            } else {
                                list.add(0, new InformationItemPictureBean(R.drawable.video));
                            }
                        }
                        informationAddPhotoAdapter.setNewData(list);
                        Timber.e(imgeList.toString());
                    } else {
                        List<InformationItemPictureBean> list = new ArrayList<>();
                        if (item.isEdit()) {
                            if (MORE_IMG_ITEM.equals(item.getParamtype())) {
                                list.add(0, new InformationItemPictureBean(R.drawable.picture));
                            } else {
                                list.add(0, new InformationItemPictureBean(R.drawable.video));
                            }
                        }
                        informationAddPhotoAdapter.setNewData(list);
                    }
                    int finalType = PictureMimeType.ofImage();
                    int num = 8;
                    if (MORE_URL_ITEM.equals(item.getParamtype())) {
                        finalType = PictureMimeType.ofVideo();
                        num = 1;
                    }
                    int finalType1 = finalType;
                    int finalNum = num;
                    informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                        try {
                            if (AppUtils.isFastClick()) {
                                return;
                            }
                            InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                            Timber.e(bean.toString());
                            if (view.getId() == R.id.image_) {
                                if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                                    if (adapter.getData().size() >= finalNum + 1) {
                                        if (finalNum == 8) {
                                            ArmsUtils.makeText("最多上传8张图片");
                                        } else {
                                            ArmsUtils.makeText("最多上传一个视频");
                                        }
                                        return;
                                    }
                                    //9  是加上了默认的加号 图标
                                    int max = finalNum + 1 - adapter.getData().size();
                                    if (ObjectUtils.isNotEmpty(reworkBasePresenter)) {
                                        CommonCollectionPresenter mPresenter = (CommonCollectionPresenter) reworkBasePresenter;

                                        mPresenter.setChangeBean(mData.get(helper.getAdapterPosition()));
                                        Timber.e(finalType1 + "");
                                        mPresenter.getPermission(max, finalType1);
                                    }
                                } else if (bean.getType() == InformationItemPictureBean.IS_URL
                                        || bean.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
                                    try {
                                        String url = ((InformationItemPictureBean) adapter.getItem(position)).getImageUrl();
                                        ARouter.getInstance()
                                                .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                                .withString(PictureLookActivity.URL_, url)
                                                .navigation();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else if (view.getId() == R.id.remove_image) {
                                adapter.remove(position);
                                adapter.notifyItemChanged(position);
                                List<String> strings = new ArrayList<>();
                                List<InformationItemPictureBean> data = informationAddPhotoAdapter.getData();
                                for (InformationItemPictureBean datum : data) {
                                    if (StringUtil.isNotNullString(datum.getImageUrl())) {
                                        strings.add(datum.getImageUrl());
                                    }
                                }
                                item.setValue(new Gson().toJson(strings));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    break;

                default:

                    break;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setImgResource(ImageView iv, String paramType) {
        switch (paramType) {
            case SELECT_ITEM:
                iv.setImageResource(R.drawable.circle_down_arrow);
                break;
            case DATA_ITEM:
                iv.setImageResource(R.drawable.date_);
                break;
            default:

                break;

        }

    }

    /**
     * 编辑框设置
     *
     * @param mDdvXB editview
     * @param item   当前数据
     */
    private void setEditItem(EditText mDdvXB, BasicInformationBean.RecordsBean item) {
        try {
            if (StringUtil.isNotNullString(item.getValue())) {
                if (ADDRESS_ITEM.equals(item.getParamtype())) {
                    if (item.isEdit()) {
                        mDdvXB.setText(Html.fromHtml(item.getValue()));
                    } else {
                        TourVistLonAndLatBean latBean = new Gson().fromJson(item.getValue(), TourVistLonAndLatBean.class);
                        mDdvXB.setText(String.format(mContext.getResources().getString(R.string.lon_lat), latBean.getLat(), latBean.getLng()));
                    }
                } else {
                    mDdvXB.setText(Html.fromHtml(item.getValue()));
                }
            } else {
                if (item.isEdit()) {
                    mDdvXB.setHint(Html.fromHtml(item.getDefaultvalue()));
                }
            }
            /**
             *  设置编剧框类型
             */
            if (NUMBER_EDIT_ITEM.equals(item.getParamtype())) {
                mDdvXB.setInputType(InputType.TYPE_CLASS_NUMBER);

            } else if ("idcard".equals(item.getParamtype())) {
                mDdvXB.setKeyListener(DigitsKeyListener.getInstance("0123456789xyzXYZ"));
                InputFilter[] filters = {new InputFilter.LengthFilter(18)};
                mDdvXB.setFilters(filters);
            } else if ("noedit".equals(item.getType()) || "noedit".equals(item.getParamtype())) {
                mDdvXB.setEnabled(false);
            }

            final TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (mDdvXB.hasFocus()) {
                        item.setValue(editable.toString().trim());
                    }
                }
            };
            mDdvXB.setOnFocusChangeListener((view, b) -> {
                if (b) {
                    mDdvXB.addTextChangedListener(textWatcher);
                } else {

                    mDdvXB.removeTextChangedListener(textWatcher);

                }
            });
            if (item.isEdit()) {
                mDdvXB.setEnabled(true);
            } else {

                mDdvXB.setCursorVisible(false);
                mDdvXB.setFocusable(true);
                mDdvXB.setFocusableInTouchMode(true);
                mDdvXB.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //canScrollVertically()方法为判断指定方向上是否可以滚动,参数为正数或负数,负数检查向上是否可以滚动,正数为检查向下是否可以滚动
                        if (mDdvXB.canScrollVertically(1) || mDdvXB.canScrollVertically(-1)) {
                            v.getParent().requestDisallowInterceptTouchEvent(true);//requestDisallowInterceptTouchEvent();要求父类布局不在拦截触摸事件
                            if (event.getAction() == MotionEvent.ACTION_UP) { //判断是否松开
                                v.getParent().requestDisallowInterceptTouchEvent(false); //requestDisallowInterceptTouchEvent();让父类布局继续拦截触摸事件
                            }
                        }
                        return false;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
