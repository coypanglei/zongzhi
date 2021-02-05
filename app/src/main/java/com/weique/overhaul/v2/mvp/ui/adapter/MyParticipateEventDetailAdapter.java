package com.weique.overhaul.v2.mvp.ui.adapter;

import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.model.entity.MyParticipateEventDetailItemBean;

import java.util.List;

/**
 * @author  GK
 */
public class MyParticipateEventDetailAdapter extends BaseMultiItemQuickAdapter<MyParticipateEventDetailItemBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */


    public MyParticipateEventDetailAdapter(@Nullable List<MyParticipateEventDetailItemBean> data) {
        super(data);
        addItemType(0, R.layout.my_participate_event_item_attachment_image);
        addItemType(1, R.layout.my_participate_event_item_attachment_vedio);

    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyParticipateEventDetailItemBean item) {


        try {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            int width = dm.widthPixels;


            helper.addOnClickListener(R.id.pic);
            switch (helper.getItemViewType()) {
                case 0:
                    ImageView imageView = helper.itemView.findViewById(R.id.pic);

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    //获取当前控件的布局对象
                    params.width = width / 3;
                    params.height = width / 3;
                    imageView.setLayoutParams(params);

                    RoundedCorners roundedCorners = new RoundedCorners(7);
                    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                    GlideUtil.loadImageWithOptions(mContext, (UserInfoUtil.getUserInfo().getHeadUrl()), imageView, options);
                    break;
                case 1:

    //                ImageView imageView1 = helper.itemView.findViewById(R.id.pic);
    //
    //                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) imageView1.getLayoutParams();
    //                //获取当前控件的布局对象
    //                params1.width = width / 3;//设置当前控件布局的高度
    //                params1.height = width / 3;//设置当前控件布局的高度
    //                imageView1.setLayoutParams(params1);//将设置好的布局参数应用到控件中
    //
    //                RoundedCorners roundedCorners1 = new RoundedCorners(7);
    //                RequestOptions options1 = RequestOptions.bitmapTransform(roundedCorners1).override(300, 300);
    //
    //                Glide.with(mContext)
    //                        .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572428780754&di=96f22c621130af192b7de36be2616a65&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fb%2F56909a8c95ebc.jpg")
    //                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
    //                        .apply(options1).into(imageView1);

                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
