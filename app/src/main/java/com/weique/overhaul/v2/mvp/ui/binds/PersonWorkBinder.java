package com.weique.overhaul.v2.mvp.ui.binds;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.newlibrary.adapter.base.binder.QuickItemBinder;
import com.chad.newlibrary.adapter.base.viewholder.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.PersonWorkBean;

import org.jetbrains.annotations.NotNull;

/**
 * 个人工作日志
 *
 * @author Administrator
 */
public class PersonWorkBinder extends QuickItemBinder<PersonWorkBean> implements Parcelable {


    @Override
    public int getLayoutId() {
        return R.layout.item_inspection;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, PersonWorkBean inspectionRecordBean) {
        try {

            if (0 == baseViewHolder.getAdapterPosition()) {
                baseViewHolder.setVisible(R.id.iv_top_line, false);
            } else {
                baseViewHolder.setVisible(R.id.iv_top_line, true);
            }
            baseViewHolder.setText(R.id.tv_status, inspectionRecordBean.getTitle());
            baseViewHolder.setText(R.id.tv_content, inspectionRecordBean.getSubtitle() + "    " + inspectionRecordBean.getTypePathName() + "    " + inspectionRecordBean.getResourceName());
            baseViewHolder.setText(R.id.tv_time, inspectionRecordBean.getCreateTimeStr());
            if (getData().size() == (baseViewHolder.getAdapterPosition() + 1)) {
                baseViewHolder.setVisible(R.id.iv_bottom_line, false);
            } else {
                baseViewHolder.setVisible(R.id.iv_bottom_line, true);
            }

            if (getData().size() == 1) {
                baseViewHolder.setVisible(R.id.rl_left, false);
            }else {
                baseViewHolder.setVisible(R.id.rl_left, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PersonWorkBinder() {
    }

    protected PersonWorkBinder(Parcel in) {
    }

    public static final Parcelable.Creator<PersonWorkBinder> CREATOR = new Parcelable.Creator<PersonWorkBinder>() {
        @Override
        public PersonWorkBinder createFromParcel(Parcel source) {
            return new PersonWorkBinder(source);
        }

        @Override
        public PersonWorkBinder[] newArray(int size) {
            return new PersonWorkBinder[size];
        }
    };
}
