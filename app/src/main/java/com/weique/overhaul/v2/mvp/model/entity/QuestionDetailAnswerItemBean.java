package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author GreatKing
 */
public class QuestionDetailAnswerItemBean implements Parcelable {


    /**
     * Id : de58defc-371c-404f-831c-1dc65731f01c
     * Content : vdfvdfvd
     * AnswersInJson : [{"answer":"1","result":false},{"answer":"2","result":false},{"answer":"3","result":true},{"answer":"4","result":false}]
     * Point : 2
     * AnswerAnalysis : null
     * AnswerInJson : 2
     * IsCorrect : false
     * ExamDetailCount : 6
     * isFinish : true
     */

    private String Id;
    private String Content;
    private int Point;
    private String AnswerAnalysis;
    private String AnswerInJson;
    private boolean IsCorrect;
    private int ExamDetailCount;
    private boolean isFinish;
    private List<AnswersInJsonBean> AnswersInJson;

    protected QuestionDetailAnswerItemBean(Parcel in) {
        Id = in.readString();
        Content = in.readString();
        Point = in.readInt();
        AnswerAnalysis = in.readString();
        AnswerInJson = in.readString();
        IsCorrect = in.readByte() != 0;
        ExamDetailCount = in.readInt();
        isFinish = in.readByte() != 0;
    }

    public static final Creator<QuestionDetailAnswerItemBean> CREATOR = new Creator<QuestionDetailAnswerItemBean>() {
        @Override
        public QuestionDetailAnswerItemBean createFromParcel(Parcel in) {
            return new QuestionDetailAnswerItemBean(in);
        }

        @Override
        public QuestionDetailAnswerItemBean[] newArray(int size) {
            return new QuestionDetailAnswerItemBean[size];
        }
    };

    public boolean isCorrect() {
        return IsCorrect;
    }

    public void setCorrect(boolean correct) {
        IsCorrect = correct;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public String getAnswerAnalysis() {
        return AnswerAnalysis;
    }

    public void setAnswerAnalysis(String AnswerAnalysis) {
        this.AnswerAnalysis = AnswerAnalysis;
    }

    public String getAnswerInJson() {
        return AnswerInJson;
    }

    public void setAnswerInJson(String AnswerInJson) {
        this.AnswerInJson = AnswerInJson;
    }


    public int getExamDetailCount() {
        return ExamDetailCount;
    }

    public void setExamDetailCount(int ExamDetailCount) {
        this.ExamDetailCount = ExamDetailCount;
    }

    public List<AnswersInJsonBean> getAnswersInJson() {
        return AnswersInJson;
    }

    public void setAnswersInJson(List<AnswersInJsonBean> AnswersInJson) {
        this.AnswersInJson = AnswersInJson;
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
        dest.writeString(Content);
        dest.writeInt(Point);
        dest.writeString(AnswerAnalysis);
        dest.writeString(AnswerInJson);
        dest.writeByte((byte) (IsCorrect ? 1 : 0));
        dest.writeInt(ExamDetailCount);
        dest.writeByte((byte) (isFinish ? 1 : 0));
    }

    public static class AnswersInJsonBean implements Parcelable {
        /**
         * answer : 1
         * result : false
         */

        private String answer;
        private boolean result;

        private boolean isClick;
        private boolean isAnswer;

        protected AnswersInJsonBean(Parcel in) {
            answer = in.readString();
            result = in.readByte() != 0;
            isClick = in.readByte() != 0;
            isAnswer = in.readByte() != 0;
        }

        public static final Creator<AnswersInJsonBean> CREATOR = new Creator<AnswersInJsonBean>() {
            @Override
            public AnswersInJsonBean createFromParcel(Parcel in) {
                return new AnswersInJsonBean(in);
            }

            @Override
            public AnswersInJsonBean[] newArray(int size) {
                return new AnswersInJsonBean[size];
            }
        };

        public boolean isClick() {
            return isClick;
        }

        public void setClick(boolean click) {
            isClick = click;
        }

        public boolean isAnswer() {
            return isAnswer;
        }

        public void setAnswer(boolean answer) {
            isAnswer = answer;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
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
            dest.writeString(answer);
            dest.writeByte((byte) (result ? 1 : 0));
            dest.writeByte((byte) (isClick ? 1 : 0));
            dest.writeByte((byte) (isAnswer ? 1 : 0));
        }
    }
}
