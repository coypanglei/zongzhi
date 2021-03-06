package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationDynamicFormSelectBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GK
 */
public class DynamicFormSelectAdapter extends BaseMultiItemQuickAdapter<InformationDynamicFormSelectBean.StructureInJsonBean, BaseViewHolder> {

    private final Gson gson;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public DynamicFormSelectAdapter(List<InformationDynamicFormSelectBean.StructureInJsonBean> data) {
        super(data);
        addItemType(InformationDynamicFormSelectBean.SingleLineN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.AutographN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.MultiLineN, R.layout.dynamic_form_select_multi_text);
        addItemType(InformationDynamicFormSelectBean.NumberN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.IdCardN, R.layout.dynamic_form_select_id_card);
        addItemType(InformationDynamicFormSelectBean.SinglePicN, R.layout.dynamic_form_select_images);
        addItemType(InformationDynamicFormSelectBean.SingleVideoN, R.layout.dynamic_form_select_images);
        addItemType(InformationDynamicFormSelectBean.MultiPicsN, R.layout.dynamic_form_select_images);
        addItemType(InformationDynamicFormSelectBean.DateTimeN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.DropdownListN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.OptionN, R.layout.dynamic_form_select_text);
        addItemType(InformationDynamicFormSelectBean.CheckBoxN, R.layout.dynamic_form_select_text);
        gson = new Gson();
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
            ArrayList<String> arrayList = new ArrayList<>();
            switch (helper.getItemViewType()) {
                case InformationDynamicFormSelectBean.SingleLineN:
                case InformationDynamicFormSelectBean.AutographN:
                case InformationDynamicFormSelectBean.MultiLineN:
                case InformationDynamicFormSelectBean.NumberN:
                case InformationDynamicFormSelectBean.DateTimeN:
                case InformationDynamicFormSelectBean.DropdownListN:
                case InformationDynamicFormSelectBean.OptionN:
                case InformationDynamicFormSelectBean.IdCardN:
                    helper.setText(R.id.key, item.getName());
                    if (item.isEncryption()) {
                        helper.setText(R.id.value, StringUtil.encryption(item.getDefaultVal()));
                    } else {
                        helper.setText(R.id.value, item.getDefaultVal());
                    }
                    break;
                case InformationDynamicFormSelectBean.CheckBoxN:
                    helper.setText(R.id.key, item.getName());
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
                            }else{
                                arrayList.add(replaceString);
                            }
                        }
                        StringBuffer buffer = new StringBuffer();
                        int i = 0;
                        for (String s : arrayList) {
                            buffer.append(s);
                            i++;
                            if (i < arrayList.size()) {
                                buffer.append("\n");
                            }
                        }
                        helper.setText(R.id.value, buffer.toString());
                    }
                    break;
                case InformationDynamicFormSelectBean.SinglePicN:
                    helper.setText(R.id.key, item.getName());
                    ArrayList<String> ss = new ArrayList<>();
                    ss.add(item.getDefaultVal());
                    vrayImage(helper, ss);
                    break;
                case InformationDynamicFormSelectBean.SingleVideoN:
                    helper.setText(R.id.key, item.getName());
                    String replaceString = item.getDefaultVal();
                    if (item.getDefaultVal().contains("\\")) {
                        replaceString = item.getDefaultVal().replace("\\", "");
                    }
                    if (item.getDefaultVal().contains("[")) {
                        arrayList = gson.fromJson(replaceString, ArrayList.class);
                    } else {
                        if (replaceString.contains("\"")) {
                            arrayList.add(gson.fromJson(replaceString, String.class));
                        }else{
                            arrayList.add(replaceString);
                        }
                    }
                    vrayImage(helper, arrayList);
                    break;
                case InformationDynamicFormSelectBean.MultiPicsN:
                    helper.setText(R.id.key, item.getName());
                    replaceString = item.getDefaultVal();
                    if (item.getDefaultVal().contains("\\")) {
                        replaceString = item.getDefaultVal().replace("\\", "");
                    }
                    if (item.getDefaultVal().contains("[")) {
                        arrayList = gson.fromJson(replaceString, ArrayList.class);
                    } else {
                        if (replaceString.contains("\"")) {
                            arrayList.add(gson.fromJson(replaceString, String.class));
                        }else{
                            arrayList.add(replaceString);
                        }
                    }
                    vrayImage(helper, arrayList);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void vrayImage(@NonNull BaseViewHolder helper, ArrayList<String> strings) {
        try {
            RecyclerView recyclerView = helper.getView(R.id.image_recycler);
            InformationDynamicFormSelectItemAdapter informationDynamicFormSelectItemAdapter =
                    new InformationDynamicFormSelectItemAdapter(R.layout.item_image, strings);
            ArmsUtils.configRecyclerView(recyclerView,new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(informationDynamicFormSelectItemAdapter);
            informationDynamicFormSelectItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (adapter.getItem(position) instanceof String) {
                            String s = (String) adapter.getItem(position);
                            if (StringUtil.isNotNullString(s) && view.getId() == R.id.image_) {
        //                        if (s.endsWith(Constant.MP4)) {
        //                            String extension = MimeTypeMap.getFileExtensionFromUrl(s);
        //                            String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        //                            Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
        //                            mediaIntent.setDataAndType(Uri.parse(s), mimeType);
        //                            mContext.startActivity(mediaIntent);
        //                        } else {
                                ARouter.getInstance()
                                        .build(RouterHub.APP_PICTURELOOKACTIVITY)
                                        .withString(PictureLookActivity.URL_, s)
                                        .navigation();
        //                        }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
