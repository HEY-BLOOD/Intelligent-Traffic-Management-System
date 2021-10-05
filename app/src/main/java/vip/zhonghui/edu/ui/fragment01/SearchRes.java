package vip.zhonghui.edu.ui.fragment01;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchRes implements Parcelable {

    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("Balance")
    private int balance;

    protected SearchRes(Parcel in) {
        result = in.readString();
        errmsg = in.readString();
        balance = in.readInt();
    }

    public static final Creator<SearchRes> CREATOR = new Creator<SearchRes>() {
        @Override
        public SearchRes createFromParcel(Parcel in) {
            return new SearchRes(in);
        }

        @Override
        public SearchRes[] newArray(int size) {
            return new SearchRes[size];
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(errmsg);
        dest.writeInt(balance);
    }

    @Override
    public String toString() {
        return "SearchRes{" +
                "result='" + result + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", balance=" + balance +
                '}';
    }
}
