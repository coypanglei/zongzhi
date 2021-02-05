package com.weique.overhaul.v2.mvp.model;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AddressSelectBean implements Parcelable {

    public AddressSelectBean(boolean isLeaf, String title, List<AddressSelectBean> children) {
        this.isLeaf = isLeaf;
        this.title = title;
        this.children = children;
    }

    /**
     * key : A01
     * value : A01
     * title : 农业
     * isLeaf : false
     * id : A01
     * hybh : A01
     * hymc : 农业
     * px : 2
     * hyzgzcfzl : null
     * hyxs : null
     * sqsxzlbh : null
     * sqsxzljb : null
     * sqsxzlfjbh : null
     * sfqy : 1
     * fhybh : A
     * children : [{"key":"A011","value":"A011","title":"谷物种植","isLeaf":false,"id":"A011","hybh":"A011","hymc":"谷物种植","px":3,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0111","value":"A0111","title":"稻谷种植","isLeaf":true,"id":"A0111","hybh":"A0111","hymc":"稻谷种植","px":4,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A011","children":null},{"key":"A0112","value":"A0112","title":"小麦种植","isLeaf":true,"id":"A0112","hybh":"A0112","hymc":"小麦种植","px":5,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A011","children":null},{"key":"A0113","value":"A0113","title":"玉米种植","isLeaf":true,"id":"A0113","hybh":"A0113","hymc":"玉米种植","px":6,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A011","children":null},{"key":"A0119","value":"A0119","title":"其他谷物种植","isLeaf":true,"id":"A0119","hybh":"A0119","hymc":"其他谷物种植","px":7,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A011","children":null}]},{"key":"A012","value":"A012","title":"豆类、油料和薯类种植","isLeaf":false,"id":"A012","hybh":"A012","hymc":"豆类、油料和薯类种植","px":8,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0121","value":"A0121","title":"豆类种植","isLeaf":true,"id":"A0121","hybh":"A0121","hymc":"豆类种植","px":9,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A012","children":null},{"key":"A0122","value":"A0122","title":"油料种植","isLeaf":true,"id":"A0122","hybh":"A0122","hymc":"油料种植","px":10,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A012","children":null},{"key":"A0123","value":"A0123","title":"薯类种植","isLeaf":true,"id":"A0123","hybh":"A0123","hymc":"薯类种植","px":11,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A012","children":null}]},{"key":"A013","value":"A013","title":"棉、麻、糖、烟草种植","isLeaf":false,"id":"A013","hybh":"A013","hymc":"棉、麻、糖、烟草种植","px":12,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0131","value":"A0131","title":"棉花种植","isLeaf":true,"id":"A0131","hybh":"A0131","hymc":"棉花种植","px":13,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A013","children":null},{"key":"A0132","value":"A0132","title":"麻类种植","isLeaf":true,"id":"A0132","hybh":"A0132","hymc":"麻类种植","px":14,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A013","children":null},{"key":"A0133","value":"A0133","title":"糖料种植","isLeaf":true,"id":"A0133","hybh":"A0133","hymc":"糖料种植","px":15,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A013","children":null},{"key":"A0134","value":"A0134","title":"烟草种植","isLeaf":true,"id":"A0134","hybh":"A0134","hymc":"烟草种植","px":16,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A013","children":null}]},{"key":"A014","value":"A014","title":"蔬菜、食用菌及园艺作物种植","isLeaf":false,"id":"A014","hybh":"A014","hymc":"蔬菜、食用菌及园艺作物种植","px":17,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0141","value":"A0141","title":"蔬菜种植","isLeaf":true,"id":"A0141","hybh":"A0141","hymc":"蔬菜种植","px":18,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A014","children":null},{"key":"A0142","value":"A0142","title":"食用菌种植","isLeaf":true,"id":"A0142","hybh":"A0142","hymc":"食用菌种植","px":19,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A014","children":null},{"key":"A0143","value":"A0143","title":"花卉种植","isLeaf":true,"id":"A0143","hybh":"A0143","hymc":"花卉种植","px":20,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A014","children":null},{"key":"A0149","value":"A0149","title":"其他园艺作物种植","isLeaf":true,"id":"A0149","hybh":"A0149","hymc":"其他园艺作物种植","px":21,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A014","children":null}]},{"key":"A015","value":"A015","title":"水果种植","isLeaf":false,"id":"A015","hybh":"A015","hymc":"水果种植","px":22,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0151","value":"A0151","title":"仁果类和核果类水果种植","isLeaf":true,"id":"A0151","hybh":"A0151","hymc":"仁果类和核果类水果种植","px":23,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A015","children":null},{"key":"A0152","value":"A0152","title":"葡萄种植","isLeaf":true,"id":"A0152","hybh":"A0152","hymc":"葡萄种植","px":24,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A015","children":null},{"key":"A0153","value":"A0153","title":"柑橘类种植","isLeaf":true,"id":"A0153","hybh":"A0153","hymc":"柑橘类种植","px":25,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A015","children":null},{"key":"A0154","value":"A0154","title":"香蕉等亚热带水果种植","isLeaf":true,"id":"A0154","hybh":"A0154","hymc":"香蕉等亚热带水果种植","px":26,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A015","children":null},{"key":"A0159","value":"A0159","title":"其他水果种植","isLeaf":true,"id":"A0159","hybh":"A0159","hymc":"其他水果种植","px":27,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A015","children":null}]},{"key":"A016","value":"A016","title":"坚果、含油果、香料和饮料作物种植","isLeaf":false,"id":"A016","hybh":"A016","hymc":"坚果、含油果、香料和饮料作物种植","px":28,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0161","value":"A0161","title":"坚果种植","isLeaf":true,"id":"A0161","hybh":"A0161","hymc":"坚果种植","px":29,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A016","children":null},{"key":"A0162","value":"A0162","title":"含油果种植","isLeaf":true,"id":"A0162","hybh":"A0162","hymc":"含油果种植","px":30,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A016","children":null},{"key":"A0169","value":"A0169","title":"茶及其他饮料作物种植","isLeaf":true,"id":"A0169","hybh":"A0169","hymc":"茶及其他饮料作物种植","px":31,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A016","children":null},{"key":"A0163","value":"A0163","title":"香料作物种植","isLeaf":true,"id":"A0163","hybh":"A0163","hymc":"香料作物种植","px":1624,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A016","children":null}]},{"key":"A017","value":"A017","title":"中药材种植","isLeaf":false,"id":"A017","hybh":"A017","hymc":"中药材种植","px":32,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0170","value":"A0170","title":"中药材种植","isLeaf":true,"id":"A0170","hybh":"A0170","hymc":"中药材种植","px":33,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A017","children":null}]},{"key":"A019","value":"A019","title":"其他农业","isLeaf":false,"id":"A019","hybh":"A019","hymc":"其他农业","px":35,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A01","children":[{"key":"A0190","value":"A0190","title":"其他农业","isLeaf":true,"id":"A0190","hybh":"A0190","hymc":"其他农业","px":34,"hyzgzcfzl":null,"hyxs":null,"sqsxzlbh":null,"sqsxzljb":null,"sqsxzlfjbh":null,"sfqy":"1","fhybh":"A019","children":null}]}]
     */

    private String key;
    private String value;
    private String title;
    private boolean isLeaf;
    private String id;
    private String hybh;
    private String hymc;
    private String px;
    private String hyzgzcfzl;
    private String hyxs;
    private String sqsxzlbh;
    private String sqsxzljb;
    private String sqsxzlfjbh;
    private String sfqy;
    private String fhybh;
    private List<AddressSelectBean> children;

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getPx() {
        return px;
    }

    public void setPx(String px) {
        this.px = px;
    }

    public String getHyzgzcfzl() {
        return hyzgzcfzl;
    }

    public void setHyzgzcfzl(String hyzgzcfzl) {
        this.hyzgzcfzl = hyzgzcfzl;
    }

    public String getHyxs() {
        return hyxs;
    }

    public void setHyxs(String hyxs) {
        this.hyxs = hyxs;
    }

    public String getSqsxzlbh() {
        return sqsxzlbh;
    }

    public void setSqsxzlbh(String sqsxzlbh) {
        this.sqsxzlbh = sqsxzlbh;
    }

    public String getSqsxzljb() {
        return sqsxzljb;
    }

    public void setSqsxzljb(String sqsxzljb) {
        this.sqsxzljb = sqsxzljb;
    }

    public String getSqsxzlfjbh() {
        return sqsxzlfjbh;
    }

    public void setSqsxzlfjbh(String sqsxzlfjbh) {
        this.sqsxzlfjbh = sqsxzlfjbh;
    }

    public String getSfqy() {
        return sfqy;
    }

    public void setSfqy(String sfqy) {
        this.sfqy = sfqy;
    }

    public String getFhybh() {
        return fhybh;
    }

    public void setFhybh(String fhybh) {
        this.fhybh = fhybh;
    }

    public List<AddressSelectBean> getChildren() {
        return children;
    }

    public void setChildren(List<AddressSelectBean> children) {
        this.children = children;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHybh() {
        return hybh;
    }

    public void setHybh(String hybh) {
        this.hybh = hybh;
    }

    public String getHymc() {
        return hymc;
    }

    public void setHymc(String hymc) {
        this.hymc = hymc;
    }

    @Override
    public String toString() {
        return "AddressSelectBean{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", title='" + title + '\'' +
                ", isLeaf=" + isLeaf +
                ", id='" + id + '\'' +
                ", hybh='" + hybh + '\'' +
                ", hymc='" + hymc + '\'' +
                ", px='" + px + '\'' +
                ", hyzgzcfzl='" + hyzgzcfzl + '\'' +
                ", hyxs='" + hyxs + '\'' +
                ", sqsxzlbh='" + sqsxzlbh + '\'' +
                ", sqsxzljb='" + sqsxzljb + '\'' +
                ", sqsxzlfjbh='" + sqsxzlfjbh + '\'' +
                ", sfqy='" + sfqy + '\'' +
                ", fhybh='" + fhybh + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
        dest.writeString(this.title);
        dest.writeByte(this.isLeaf ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeString(this.hybh);
        dest.writeString(this.hymc);
        dest.writeString(this.px);
        dest.writeString(this.hyzgzcfzl);
        dest.writeString(this.hyxs);
        dest.writeString(this.sqsxzlbh);
        dest.writeString(this.sqsxzljb);
        dest.writeString(this.sqsxzlfjbh);
        dest.writeString(this.sfqy);
        dest.writeString(this.fhybh);
        dest.writeList(this.children);
    }

    public AddressSelectBean() {
    }

    protected AddressSelectBean(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
        this.title = in.readString();
        this.isLeaf = in.readByte() != 0;
        this.id = in.readString();
        this.hybh = in.readString();
        this.hymc = in.readString();
        this.px = in.readString();
        this.hyzgzcfzl = in.readString();
        this.hyxs = in.readString();
        this.sqsxzlbh = in.readString();
        this.sqsxzljb = in.readString();
        this.sqsxzlfjbh = in.readString();
        this.sfqy = in.readString();
        this.fhybh = in.readString();
        this.children = new ArrayList<AddressSelectBean>();
        in.readList(this.children, AddressSelectBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddressSelectBean> CREATOR = new Parcelable.Creator<AddressSelectBean>() {
        @Override
        public AddressSelectBean createFromParcel(Parcel source) {
            return new AddressSelectBean(source);
        }

        @Override
        public AddressSelectBean[] newArray(int size) {
            return new AddressSelectBean[size];
        }
    };
}
