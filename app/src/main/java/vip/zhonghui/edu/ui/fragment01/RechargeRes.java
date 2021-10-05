package vip.zhonghui.edu.ui.fragment01;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RechargeRes implements Parcelable {

    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;

    protected RechargeRes(Parcel in) {
        result = in.readString();
        errmsg = in.readString();
    }

    @Override
    public String toString() {
        return "RechargeRes{" +
                "result='" + result + '\'' +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }

    public static final Creator<RechargeRes> CREATOR = new Creator<RechargeRes>() {
        @Override
        public RechargeRes createFromParcel(Parcel in) {
            return new RechargeRes(in);
        }

        @Override
        public RechargeRes[] newArray(int size) {
            return new RechargeRes[size];
        }
    };

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(errmsg);
    }
}
