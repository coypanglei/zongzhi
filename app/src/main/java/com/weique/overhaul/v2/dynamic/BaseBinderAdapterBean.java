package com.weique.overhaul.v2.dynamic;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.DiffUtil;

import com.chad.newlibrary.adapter.base.binder.BaseItemBinder;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author Administrator
 */
public class BaseBinderAdapterBean<T> implements Serializable, Parcelable {

    private Class<T> aClass;

    private BaseItemBinder<T, ?> baseItemBinder;
    private DiffUtil.ItemCallback<T> diffItem;

    public Class<T> getaClass() {
        return aClass;
    }

    public void setaClass(Class<T> aClass) {
        this.aClass = aClass;
    }

    public BaseItemBinder<T, ?> getBaseItemBinder() {
        return baseItemBinder;
    }

    public void setBaseItemBinder(BaseItemBinder<T, ?> baseItemBinder) {
        this.baseItemBinder = baseItemBinder;
    }

    public DiffUtil.ItemCallback<T> getDiffItem() {
        return diffItem;
    }

    public void setDiffItem(DiffUtil.ItemCallback<T> diffItem) {
        this.diffItem = diffItem;
    }

    public BaseBinderAdapterBean(Class<T> aClass, BaseItemBinder<T, ?> baseItemBinder, DiffUtil.ItemCallback<T> diffItem) {
        this.aClass = aClass;
        this.baseItemBinder = baseItemBinder;
        this.diffItem = diffItem;
    }

    public BaseBinderAdapterBean(Class<T> aClass, BaseItemBinder<T, ?> baseItemBinder) {
        this.aClass = aClass;
        this.baseItemBinder = baseItemBinder;
        this.diffItem = diffItem;
    }


    @NotNull
    @Override
    public String toString() {
        return "BaseBinderAdapterBean{" +
                "aClass=" + aClass +
                ", baseItemBinder=" + baseItemBinder +
                ", diffItem=" + diffItem +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.aClass);
        dest.writeParcelable((Parcelable) this.baseItemBinder, flags);
        dest.writeParcelable((Parcelable) this.diffItem, flags);
    }

    private BaseBinderAdapterBean(Parcel in) {
        this.aClass = (Class<T>) in.readSerializable();
        this.baseItemBinder = in.readParcelable(BaseItemBinder.class.getClassLoader());
        this.diffItem = in.readParcelable(DiffUtil.ItemCallback.class.getClassLoader());
    }

    public static final Creator<BaseBinderAdapterBean> CREATOR = new Creator<BaseBinderAdapterBean>() {
        @Override
        public BaseBinderAdapterBean createFromParcel(Parcel source) {
            return new BaseBinderAdapterBean(source);
        }

        @Override
        public BaseBinderAdapterBean[] newArray(int size) {
            return new BaseBinderAdapterBean[size];
        }
    };
}
