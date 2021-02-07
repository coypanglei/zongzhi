package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.PictureLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationAddPhotoAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GreatKing
 */
public class HeatDialog extends Dialog {
    private Context mContext;
    private EditText content;
    private TextView title;
    private TextView confirmBtn;
    private TextView impose;
    private RecyclerView recyclerView;
    private OnConfirmClickListener OnConfirmClickListener;
    private InformationAddPhotoAdapter addPhotoAdapter;


    public interface OnConfirmClickListener {
        void onConfirmClick(HeatDialog heatDialog, String contents);
    }

    public interface OnUploadImageListener {
        void onnUploadImageClick(int max);
    }

    public HeatDialog(@NonNull Context context, OnConfirmClickListener listener) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        this.OnConfirmClickListener = listener;
        initView();
    }

    public HeatDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        initView();
    }

    public void setTitleText(String titleText) {
        this.title.setText(titleText);
    }

    public void setContentHint(String text) {
        this.content.setHint(text);
    }

    public void setConfirmBtnText(String text) {
        this.confirmBtn.setText(text);
    }

    public void setOnConfirmClickListener(HeatDialog.OnConfirmClickListener onConfirmClickListener) {
        OnConfirmClickListener = onConfirmClickListener;
    }


    public void setNewImageUrl(List<InformationItemPictureBean> list) {
        if (addPhotoAdapter != null) {
            addPhotoAdapter.addData(list);
        }
    }

    public String getImageUrls() {
        List<String> strings = new ArrayList<>();
        if (addPhotoAdapter != null) {
            List<InformationItemPictureBean> data = addPhotoAdapter.getData();
            for (InformationItemPictureBean datum : data) {
                if (StringUtil.isNotNullString(datum.getImageUrl())) {
                    strings.add(datum.getImageUrl());
                }
            }
        }
        return new Gson().toJson(strings);
    }

    public void setRecyclerViewOnItemClick(int maxItem, OnUploadImageListener onUploadImageListener) {
        try {
            recyclerView.setVisibility(View.VISIBLE);
            ArrayList<InformationItemPictureBean> list = new ArrayList<>();
            list.add(0, new InformationItemPictureBean(R.drawable.picture));
            addPhotoAdapter = new InformationAddPhotoAdapter(list);
            addPhotoAdapter.setMaxItem(maxItem);
            ArmsUtils.configRecyclerView(recyclerView, new GridLayoutManager(getContext(),
                    4, RecyclerView.VERTICAL, false));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.transparent)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            recyclerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.transparent)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            recyclerView.setAdapter(addPhotoAdapter);
            addPhotoAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    InformationItemPictureBean bean = (InformationItemPictureBean) adapter.getItem(position);
                    if (view.getId() == R.id.image_) {
                        if (bean.getType() == InformationItemPictureBean.IS_DRAWABLE) {
                            if (adapter.getData().size() >= maxItem + 1) {
                                ArmsUtils.makeText("最多上传" + maxItem + "张图片");
                                return;
                            }
                            //9  是加上了默认的加号 图标
                            int max = (maxItem + 1) - adapter.getData().size();
                            onUploadImageListener.onnUploadImageClick(max);
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

    private void initView() {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_order_handle_new, null);
            setContentView(view);
            //alertDialog是否可以点击外围消失
            setCanceledOnTouchOutside(true);
            setCancelable(true);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            confirmBtn = view.findViewById(R.id.confirm);
            impose = view.findViewById(R.id.impose);
            recyclerView = view.findViewById(R.id.recycler_view);
            content.setFocusable(true);
            content.setFocusableInTouchMode(true);
            content.requestFocus();
            content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int index = 0;
                    if(StringUtil.isNotNullString(s.toString())){
                        index = s.toString().length();
                    }
                    impose.setText(index + "/150");
                }
            });
            //
            confirmBtn.setOnClickListener(v -> {
                if (StringUtil.isNullString(content.getText().toString())) {
                    ArmsUtils.makeText("内容不能为空");
                    return;
                }
                OnConfirmClickListener.onConfirmClick(this, content.getText().toString());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
