package com.weique.overhaul.v2.mvp.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.speechrecognition.utils.ControlViewUtils;
import com.example.speechrecognition.view.RecordClickPopupWindow;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.DynamicFormOrmBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息采集动态 添加  修改界面
 *
 * @author GreatKing
 */
public class DynamicFormAdapter extends BaseMultiItemQuickAdapter<InformationDynamicFormSelectBean.StructureInJsonBean, BaseViewHolder> {

    private Gson gson = new Gson();
    private MergeOrmFormClickListener mergeOrmFormClickListener;
    private RecordClickPopupWindow recordPopup;
    /**
     * 同步映射表单 文本长度变化 监听
     */
    public interface MergeOrmFormClickListener {
        /**
         * 文本长度变化
         *
         * @param text text
         */
        void onTextChanged(String text);
    }

    /**
     * 根据动态表单映射关系设置值
     *
     * @param bean bean
     */
    public void setDataByDynamicFormOrmBean(DynamicFormOrmBean.MapListBean bean) {
        try {
            List<InformationDynamicFormSelectBean.StructureInJsonBean> data = getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getPropertyName().equals(bean.getSourceid())) {
                    data.get(i).setDefaultVal(bean.getValue());
                    notifyItemChanged(i + 1);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加 合并 采集到的信息监听
     *
     * @param dynamicFormOrmBean        映射表单
     * @param mergeOrmFormClickListener 同步映射监听
     */
    public void addMergeInfoSearchClick(DynamicFormOrmBean dynamicFormOrmBean, MergeOrmFormClickListener mergeOrmFormClickListener) {
        try {
            this.mergeOrmFormClickListener = mergeOrmFormClickListener;
            List<InformationDynamicFormSelectBean.StructureInJsonBean> data = getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getPropertyName().equals(dynamicFormOrmBean.getSourceTypeFiredFieldName())) {
                    data.get(i).setHasMergeInfoListener(true);
                    data.get(i).setPlaceholder("输入信息点击合并,可选择合并信息");
                    notifyItemChanged(i + 1);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnPictureClickListener {
        void onPictureClick(int adapterPosition, int max, String type);
    }

    private OnPictureClickListener onPictureClickLisenter;

    public void setOnPictureClickLisenter(OnPictureClickListener onPictureClickLisenter) {
        this.onPictureClickLisenter = onPictureClickLisenter;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DynamicFormAdapter(List<InformationDynamicFormSelectBean.StructureInJsonBean> data) {
        super(data);
        //edittext 输入框
        try {
            addItemType(InformationDynamicFormSelectBean.SingleLineN, R.layout.dynamic_item_single_line);
            addItemType(InformationDynamicFormSelectBean.MultiLineN, R.layout.dynamic_item_multi_line);
            addItemType(InformationDynamicFormSelectBean.NumberN, R.layout.dynamic_item_number);
            addItemType(InformationDynamicFormSelectBean.IdCardN, R.layout.dynamic_item_id_card);
            //图片用相同布局 设置最大数量进行控制
            addItemType(InformationDynamicFormSelectBean.SinglePicN, R.layout.dynamic_item_multi_pics);
            addItemType(InformationDynamicFormSelectBean.MultiPicsN, R.layout.dynamic_item_multi_pics);
            addItemType(InformationDynamicFormSelectBean.SingleVideoN, R.layout.dynamic_item_multi_pics);
            // 选择项全部用 popup 控制
            addItemType(InformationDynamicFormSelectBean.DateTimeN, R.layout.dynamic_item_popup);
            addItemType(InformationDynamicFormSelectBean.DropdownListN, R.layout.dynamic_item_popup);
            addItemType(InformationDynamicFormSelectBean.OptionN, R.layout.dynamic_item_popup);
            addItemType(InformationDynamicFormSelectBean.CheckBoxN, R.layout.dynamic_item_popup);
            addItemType(InformationDynamicFormSelectBean.AutographN, R.layout.dynamic_item_single_line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationDynamicFormSelectBean.StructureInJsonBean item) {
        try {
            int adapterPosition = helper.getAdapterPosition();
            ArrayList<String> arrayList = new ArrayList<>();
            EditText editText;
            ArrayList<InformationItemPictureBean> list = new ArrayList<>();
            switch (helper.getItemViewType()) {
                case InformationDynamicFormSelectBean.AutographN:
                case InformationDynamicFormSelectBean.SingleLineN:
                case InformationDynamicFormSelectBean.MultiLineN:
                case InformationDynamicFormSelectBean.NumberN:
                case InformationDynamicFormSelectBean.IdCardN:
                    initEditText(helper, item);
                    break;
                case InformationDynamicFormSelectBean.DateTimeN:
                case InformationDynamicFormSelectBean.DropdownListN:
                case InformationDynamicFormSelectBean.OptionN:
                    helper.setVisible(R.id.must, item.isIsRequired());
                    helper.setText(R.id.key, StringUtil.setText(item.getName()));
                    helper.setText(R.id.value, StringUtil.setText(item.getDefaultVal()));
                    helper.addOnClickListener(R.id.all_item);
                    break;
                case InformationDynamicFormSelectBean.CheckBoxN:
                    helper.setVisible(R.id.must, item.isIsRequired());
                    helper.setText(R.id.key, StringUtil.setText(item.getName()));
                    if (StringUtil.isNotNullString(item.getDefaultVal())) {
                        String replaceString = item.getDefaultVal();
                        if (item.getDefaultVal().contains("\\")) {
                            replaceString = item.getDefaultVal().replace("\\", "");
                        }
                        if (item.getDefaultVal().contains("[")) {
                            arrayList = gson.fromJson(replaceString, ArrayList.class);
                        } else {
                            if (replaceString.contains("\"")) {
                                arrayList.add(gson.fromJson(replaceString, String.class));
                            } else {
                                arrayList.add(replaceString);
                            }
                        }
                        if (arrayList.size() > 0) {
                            StringBuffer buffer = new StringBuffer();
                            for (String s : arrayList) {
                                buffer.append(s).append(" ");
                            }
                            helper.setText(R.id.value, buffer.toString());
                        } else {
                            helper.setText(R.id.value, "");
                        }
                    }
                    helper.addOnClickListener(R.id.all_item);
                    break;
                case InformationDynamicFormSelectBean.SingleVideoN:
                    helper.setVisible(R.id.must, item.isIsRequired());
                    helper.setText(R.id.key, StringUtil.setText(item.getName()));
                    RecyclerView multiVideoRecyclerView = helper.getView(R.id.images_recycler_view);
                    if (StringUtil.isNotNullString(item.getDefaultVal())) {
                        String replaceString = item.getDefaultVal();
                        if (item.getDefaultVal().contains("\\")) {
                            replaceString = item.getDefaultVal().replace("\\", "");
                        }
                        if (item.getDefaultVal().contains("[")) {
                            arrayList = gson.fromJson(replaceString, ArrayList.class);
                        } else {
                            if (replaceString.contains("\"")) {
                                arrayList.add(gson.fromJson(replaceString, String.class));
                            } else {
                                arrayList.add(replaceString);
                            }
                        }
                        for (String s : arrayList) {
                            list.add(new InformationItemPictureBean(s));
                        }
                    }
                    setPictureAdapter(multiVideoRecyclerView, item.getMaxLength(), list, adapterPosition, item, helper.getItemViewType());
                    break;
                case InformationDynamicFormSelectBean.SinglePicN:
                    helper.setVisible(R.id.must, item.isIsRequired());
                    helper.setText(R.id.key, StringUtil.setText(item.getName()));
                    RecyclerView singleRecyclerView = helper.getView(R.id.images_recycler_view);
                    if (StringUtil.isNotNullString(item.getDefaultVal())) {
                        list.add(new InformationItemPictureBean(item.getDefaultVal()));
                    }
                    setPictureAdapter(singleRecyclerView, item.getMaxLength(), list, adapterPosition, item, helper.getItemViewType());
                    break;
                case InformationDynamicFormSelectBean.MultiPicsN:
                    helper.setVisible(R.id.must, item.isIsRequired());
                    helper.setText(R.id.key, StringUtil.setText(item.getName()));
                    RecyclerView multiRecyclerView = helper.getView(R.id.images_recycler_view);
                    if (StringUtil.isNotNullString(item.getDefaultVal())) {
                        String replaceString = item.getDefaultVal();
                        if (item.getDefaultVal().contains("\\")) {
                            replaceString = item.getDefaultVal().replace("\\", "");
                        }
                        if (item.getDefaultVal().contains("[")) {
                            arrayList = gson.fromJson(replaceString, ArrayList.class);
                        } else {
                            if (replaceString.contains("\"")) {
                                arrayList.add(gson.fromJson(replaceString, String.class));
                            } else {
                                arrayList.add(replaceString);
                            }
                        }
                        for (String s : arrayList) {
                            list.add(new InformationItemPictureBean(s));
                        }
                    }
                    setPictureAdapter(multiRecyclerView, item.getMaxLength(), list, adapterPosition, item, helper.getItemViewType());
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 文编编辑
     *
     * @param helper
     * @param item
     */
    private void initEditText(@NonNull BaseViewHolder helper,
                              InformationDynamicFormSelectBean.StructureInJsonBean item) {
        try {
            helper.setVisible(R.id.must, item.isIsRequired());
            helper.setText(R.id.key, StringUtil.setText(item.getName()));
            EditText editText = helper.getView(R.id.value_et);
            //1.根据Tag移除掉监听
            if (editText.getTag() instanceof TextWatcher) {
                editText.removeTextChangedListener((TextWatcher) editText.getTag());
            }
            if (StringUtil.isNotNullString(item.getDefaultVal())) {
                editText.setText(StringUtil.setText(item.getDefaultVal()));
                editText.setSelection(item.getDefaultVal().length());
            } else {
                editText.setText("");
                if (StringUtil.isNotNullString(item.getPlaceholder())) {
                    editText.setHint((item.getPlaceholder()));
                } else {
                    editText.setHint(ArmsUtils.getString(mContext, R.string.please_input));
                }
            }
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    item.setDefaultVal(s.toString());
                }
            };
            editText.addTextChangedListener(textWatcher);
            editText.setTag(textWatcher);
            TextView searchBtn = helper.getView(R.id.search_btn);
            if (item.isHasMergeInfoListener()) {
                searchBtn.setVisibility(View.VISIBLE);
                searchBtn.setOnClickListener(v -> {
                    mergeOrmFormClickListener.onTextChanged(item.getDefaultVal());
                });
            } else {
                searchBtn.setVisibility(View.GONE);
            }
            ControlViewUtils.setClick(editText, new ControlViewUtils.RecorePopupClickInterface() {
                @Override
                public void onRecordCreate() {
                    if (recordPopup == null) {
                        recordPopup = new RecordClickPopupWindow(mContext);
                    }
                    recordPopup.setEditText(editText, new RecordClickPopupWindow.RecorePopupListener() {
                        @Override
                        public void onRecordCreate(String str) {
                            ControlViewUtils.setEditText(editText, str);
                            recordPopup.clearContent(str);
                        }

                    });
                    if (!recordPopup.isShowing()) {
                        recordPopup.showPopupWindow();
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 图片相关控件
     *
     * @param recyclerView    recyclerView
     * @param max             max
     * @param list            list
     * @param adapterPosition adapterPosition
     * @param item            item
     * @param itemViewType    itemViewType
     */
    private void setPictureAdapter(RecyclerView recyclerView,
                                   int max,
                                   ArrayList<InformationItemPictureBean> list,
                                   int adapterPosition,
                                   InformationDynamicFormSelectBean.StructureInJsonBean item,
                                   int itemViewType) {
        try {
            if (list.size() > 0 && list.get(0).getType() != InformationItemPictureBean.IS_DRAWABLE
                    || list.size() <= 0) {
                if (itemViewType == InformationDynamicFormSelectBean.SingleVideoN) {
                    list.add(0, new InformationItemPictureBean(R.drawable.video));
                } else {
                    list.add(0, new InformationItemPictureBean(R.drawable.picture));
                }
            }
            InformationAddPhotoAdapter informationAddPhotoAdapter = new InformationAddPhotoAdapter(list);
            informationAddPhotoAdapter.setMaxItem(max);
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(informationAddPhotoAdapter);
            informationAddPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof InformationItemPictureBean) {
                        InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                        if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                            if (view.getId() == R.id.image_) {
                                if (adapter.getData().size() - 1 >= informationAddPhotoAdapter.getMaxItem()) {
                                    if (bean.getDrawableIntRes() == R.drawable.video) {
                                        ArmsUtils.makeText(
                                                String.format(ArmsUtils.getString(mContext, R.string.video_up_load_max_file_number), informationAddPhotoAdapter.getMaxItem()));
                                    } else {
                                        ArmsUtils.makeText(
                                                String.format(ArmsUtils.getString(mContext, R.string.up_load_max_file_number), informationAddPhotoAdapter.getMaxItem()));
                                    }
                                    return;
                                }
                                //max - list.size() + 1  可选的最大数量   +1 是上面 添加了  一个 默认选项
                                onPictureClickLisenter.onPictureClick(adapterPosition, max - list.size() + 1, item.getIdentification().getType());
                            }
                        } else {
                            if (view.getId() == R.id.remove_image) {
                                informationAddPhotoAdapter.getData().remove(position);
                                //                            notifyItemChanged(position);
                                if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SinglePic)) {
                                    item.setDefaultVal("");
                                    DynamicFormAdapter.this.notifyItemChanged(adapterPosition);
                                }
                                if (item.getIdentification().getType().equals(InformationDynamicFormSelectBean.MultiPics)
                                        || item.getIdentification().getType().equals(InformationDynamicFormSelectBean.SingleVideo)) {
                                    List<InformationItemPictureBean> data = adapter.getData();
                                    List<String> ts = new ArrayList<>();
                                    for (InformationItemPictureBean pictureBean : data) {
                                        if (pictureBean.getType() == InformationItemPictureBean.IS_URL) {
                                            ts.add(pictureBean.getImageUrl());
                                        }
                                    }
                                    item.setDefaultVal(gson.toJson(ts));
                                    DynamicFormAdapter.this.notifyItemChanged(adapterPosition);
                                }
                            } else if (view.getId() == R.id.image_) {
                                if (StringUtil.isNotNullString(bean.getImageUrl())) {
                                    ARouter.getInstance()
                                            .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                            .withString(PictureLookActivity.URL_, bean.getImageUrl())
                                            .navigation();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
