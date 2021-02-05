package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author  GK
 */
public class PartyCenterRecommendedBean implements Parcelable {

    private List<TypesBean> types;
    private List<NewsBean> news;
    private List<SubsBean> subs;
    private List<NotificationsBean> notifications;
    private List<PartyMeetingsBean> partyMeetings;

    protected PartyCenterRecommendedBean(Parcel in) {
        types = in.createTypedArrayList(TypesBean.CREATOR);
        news = in.createTypedArrayList(NewsBean.CREATOR);
        subs = in.createTypedArrayList(SubsBean.CREATOR);
        notifications = in.createTypedArrayList(NotificationsBean.CREATOR);
        partyMeetings = in.createTypedArrayList(PartyMeetingsBean.CREATOR);
    }

    public static final Creator<PartyCenterRecommendedBean> CREATOR = new Creator<PartyCenterRecommendedBean>() {
        @Override
        public PartyCenterRecommendedBean createFromParcel(Parcel in) {
            return new PartyCenterRecommendedBean(in);
        }

        @Override
        public PartyCenterRecommendedBean[] newArray(int size) {
            return new PartyCenterRecommendedBean[size];
        }
    };

    public List<TypesBean> getTypes() {
        return types;
    }

    public void setTypes(List<TypesBean> types) {
        this.types = types;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<SubsBean> getSubs() {
        return subs;
    }

    public void setSubs(List<SubsBean> subs) {
        this.subs = subs;
    }

    public List<NotificationsBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsBean> notifications) {
        this.notifications = notifications;
    }

    public List<PartyMeetingsBean> getPartyMeetings() {
        return partyMeetings;
    }

    public void setPartyMeetings(List<PartyMeetingsBean> partyMeetings) {
        this.partyMeetings = partyMeetings;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(types);
        dest.writeTypedList(news);
        dest.writeTypedList(subs);
        dest.writeTypedList(notifications);
        dest.writeTypedList(partyMeetings);
    }

    public static class TypesBean implements Parcelable {

        public TypesBean(String id, String name) {
            Id = id;
            Name = name;
        }

        /**
         * Id : 98395ffb-eb8c-4c06-8ec5-0f6c50555bde
         * Name : 类型7
         */

        private String Id;
        private String Name;

        protected TypesBean(Parcel in) {
            Id = in.readString();
            Name = in.readString();
        }

        public static final Creator<TypesBean> CREATOR = new Creator<TypesBean>() {
            @Override
            public TypesBean createFromParcel(Parcel in) {
                return new TypesBean(in);
            }

            @Override
            public TypesBean[] newArray(int size) {
                return new TypesBean[size];
            }
        };

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Name);
        }
    }

    public static class NewsBean extends PartyBaseBean {
        protected NewsBean(Parcel in) {
            super(in);
        }

        public static final Creator<NewsBean> CREATOR = new Creator<NewsBean>() {
            @Override
            public NewsBean createFromParcel(Parcel in) {
                return new NewsBean(in);
            }

            @Override
            public NewsBean[] newArray(int size) {
                return new NewsBean[size];
            }
        };
    }

    public static class SubsBean extends PartyBaseBean {
        protected SubsBean(Parcel in) {
            super(in);
        }

        public static final Creator<SubsBean> CREATOR = new Creator<SubsBean>() {
            @Override
            public SubsBean createFromParcel(Parcel in) {
                return new SubsBean(in);
            }

            @Override
            public SubsBean[] newArray(int size) {
                return new SubsBean[size];
            }
        };
    }

    public static class NotificationsBean extends PartyBaseBean {
        protected NotificationsBean(Parcel in) {
            super(in);
        }

        public static final Creator<NotificationsBean> CREATOR = new Creator<NotificationsBean>() {
            @Override
            public NotificationsBean createFromParcel(Parcel in) {
                return new NotificationsBean(in);
            }

            @Override
            public NotificationsBean[] newArray(int size) {
                return new NotificationsBean[size];
            }
        };
    }

    public static class PartyMeetingsBean extends PartyBaseBean {
        protected PartyMeetingsBean(Parcel in) {
            super(in);
        }

        public static final Creator<PartyMeetingsBean> CREATOR = new Creator<PartyMeetingsBean>() {
            @Override
            public PartyMeetingsBean createFromParcel(Parcel in) {
                return new PartyMeetingsBean(in);
            }

            @Override
            public PartyMeetingsBean[] newArray(int size) {
                return new PartyMeetingsBean[size];
            }
        };
    }

    public static class PartyBaseBean implements Parcelable, MultiItemEntity {
        private String Id;
        private String Title;
        private String Author;
        private String Release;
        private String CoverPicturePath;
        private int itemType;
        private String titleName;
        private int jumpTargets;

        public PartyBaseBean(int itemType, String titleName, int jumpTargets) {
            this.itemType = itemType;
            this.titleName = titleName;
            this.jumpTargets = jumpTargets;
        }

        protected PartyBaseBean(Parcel in) {
            Id = in.readString();
            Title = in.readString();
            Author = in.readString();
            Release = in.readString();
            CoverPicturePath = in.readString();
        }

        public static final Creator<PartyBaseBean> CREATOR = new Creator<PartyBaseBean>() {
            @Override
            public PartyBaseBean createFromParcel(Parcel in) {
                return new PartyBaseBean(in);
            }

            @Override
            public PartyBaseBean[] newArray(int size) {
                return new PartyBaseBean[size];
            }
        };

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAuthor() {
            return Author;
        }

        public void setAuthor(String author) {
            Author = author;
        }

        public String getRelease() {
            return Release;
        }

        public void setRelease(String release) {
            Release = release;
        }

        public String getCoverPicturePath() {
            return CoverPicturePath;
        }

        public void setCoverPicturePath(String coverPicturePath) {
            CoverPicturePath = coverPicturePath;
        }

        public int getJumpTargets() {
            return jumpTargets;
        }

        public void setJumpTargets(int jumpTargets) {
            this.jumpTargets = jumpTargets;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(Title);
            dest.writeString(Author);
            dest.writeString(Release);
            dest.writeString(CoverPicturePath);
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
    }
}
