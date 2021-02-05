package com.weique.overhaul.v2.mvp.ui.adapter;


import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;

import static android.text.Html.FROM_HTML_MODE_COMPACT;
import static com.weique.overhaul.v2.app.common.Constant.ADDRESS_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.DATA_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.IMG_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.LARGE_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.NUMBER_EDIT_ITEM;
import static com.weique.overhaul.v2.app.common.Constant.SELECT_ITEM;


/**
 * 作者：PangLei on 2019/3/21 0021 11:03
 * <p>
 * 邮箱：434604925qq.com
 *
 * @author Administrator
 */
public class SearchCurrencyAdapter extends BaseQuickAdapter<BasicInformationBean.RecordsBean, BaseViewHolder> {


    public SearchCurrencyAdapter() {
        super(null);
        setMultiTypeDelegate(new MultiTypeDelegate<BasicInformationBean.RecordsBean>() {
            @Override
            protected int getItemType(BasicInformationBean.RecordsBean entity) {
                //根据你的实体类来判断布局类型

                switch (entity.getParamtype()) {
                    case IMG_ITEM:
                        return BasicInformationBean.IMG;
                    case SELECT_ITEM:
                    case ADDRESS_ITEM:
                    case DATA_ITEM:
                        return BasicInformationBean.ADDRESS;
                    case LARGE_EDIT_ITEM:
                        return BasicInformationBean.LARGE_EDIT;
                    default:
                        return BasicInformationBean.EDIT;
                }

//                }
            }
        });
        //Step.2
        getMultiTypeDelegate()
                .registerItemType(BasicInformationBean.EDIT, R.layout.item_edit_kuang)
                .registerItemType(BasicInformationBean.IMG, R.layout.item_img_add)
                .registerItemType(BasicInformationBean.ADDRESS, R.layout.item_address)
                .registerItemType(BasicInformationBean.LARGE_EDIT, R.layout.item_edit_large);


    }

    @Override
    protected void convert(final BaseViewHolder helper, final BasicInformationBean.RecordsBean item) {
        try {
            helper.setIsRecyclable(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                helper.setText(R.id.tv_name_title, Html.fromHtml(item.getTitile(),FROM_HTML_MODE_COMPACT));
            }else {
                helper.setText(R.id.tv_name_title, Html.fromHtml(item.getTitile()));
            }
            final EditText mDdvXB = helper.getView(R.id.et_name);

            switch (item.getParamtype()) {
                case SELECT_ITEM:
                case DATA_ITEM:
                    helper.addOnClickListener(R.id.iv_address);
                    /*
                  设置不可编辑，但是可以点击
                 */
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
                    helper.addOnClickListener(R.id.iv_address);
                    setEditItem(mDdvXB, item);
                    break;
                case ADDRESS_ITEM:
                    helper.addOnClickListener(R.id.iv_address);
                    setEditItem(mDdvXB, item);
                    break;
                default:

                    break;

            }
            if (item.isEdit()) {
                mDdvXB.setEnabled(true);
            } else {
                mDdvXB.setEnabled(false);

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
        if (ObjectUtils.isNotEmpty(item.getValue())) {
            mDdvXB.setText(Html.fromHtml(item.getValue()));
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
    }
}
