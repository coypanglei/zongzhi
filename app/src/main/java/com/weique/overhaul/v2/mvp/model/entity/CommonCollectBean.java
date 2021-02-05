package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.weique.overhaul.v2.dynamic.BaseBinderAdapterBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用 新增编辑
 *
 * @author Administrator
 */
public class CommonCollectBean implements Parcelable {



    /**
     * 接口返回值 className;
     */
    private Class className;

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }


    public CommonCollectBean() {
    }

    /**
     * 数据集合
     */
    private List<BasicInformationBean.RecordsBean> list;


    /**
     * 编辑 或 新增 或 详情接口
     */

    private String path;

    /**
     * 类型  Constant  CommonCollectionEnum
     */
    private int type;


    /**
     *  map 接口需要的参数
     */
   private Map<String,Object> map;


    private List<BaseBinderAdapterBean> bindBeanList;

    public List<BaseBinderAdapterBean> getBindBeanList() {
        return bindBeanList;
    }

    public void setBindBeanList(List<BaseBinderAdapterBean> bindBeanList) {
        this.bindBeanList = bindBeanList;
    }

    /**
     * 通用title activity 使用的标题
     *
     */
    private String title;

    public List<BasicInformationBean.RecordsBean> getList() {
        return list;
    }

    public void setList(List<BasicInformationBean.RecordsBean> list) {
        this.list = list;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
        dest.writeSerializable(this.className);
        dest.writeString(this.path);
        dest.writeInt(this.type);
        dest.writeMap(this.map);
        dest.writeTypedList(this.bindBeanList);
        dest.writeString(this.title);
    }

    protected CommonCollectBean(Parcel in) {
        this.list = in.createTypedArrayList(BasicInformationBean.RecordsBean.CREATOR);
        this.className = (Class) in.readSerializable();
        this.path = in.readString();
        this.type = in.readInt();
        map = new HashMap<>();
        in.readMap(map, getClass().getClassLoader());
        this.bindBeanList = in.createTypedArrayList(BaseBinderAdapterBean.CREATOR);
        this.title = in.readString();
    }

    public static final Parcelable.Creator<CommonCollectBean> CREATOR = new Parcelable.Creator<CommonCollectBean>() {
        @Override
        public CommonCollectBean createFromParcel(Parcel source) {
            return new CommonCollectBean(source);
        }

        @Override
        public CommonCollectBean[] newArray(int size) {
            return new CommonCollectBean[size];
        }
    };
}
