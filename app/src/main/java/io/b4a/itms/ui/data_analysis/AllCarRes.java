package io.b4a.itms.ui.data_analysis;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AllCarRes implements Parcelable {

    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("ROWS_DETAIL")
    private List<ROWSDETAILDTO> rowsDetail;

    protected AllCarRes(Parcel in) {
        result = in.readString();
        errmsg = in.readString();
    }

    public static final Creator<AllCarRes> CREATOR = new Creator<AllCarRes>() {
        @Override
        public AllCarRes createFromParcel(Parcel in) {
            return new AllCarRes(in);
        }

        @Override
        public AllCarRes[] newArray(int size) {
            return new AllCarRes[size];
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

    public List<ROWSDETAILDTO> getRowsDetail() {
        return rowsDetail;
    }

    public void setRowsDetail(List<ROWSDETAILDTO> rowsDetail) {
        this.rowsDetail = rowsDetail;
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

    public static class ROWSDETAILDTO {
        @SerializedName("carnumber")
        private String carnumber;
        @SerializedName("number")
        private int number;
        @SerializedName("pcardid")
        private String pcardid;
        @SerializedName("buydata")
        private String buydata;
        @SerializedName("carbrand")
        private String carbrand;

        public String getCarnumber() {
            return carnumber;
        }

        public void setCarnumber(String carnumber) {
            this.carnumber = carnumber;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPcardid() {
            return pcardid;
        }

        public void setPcardid(String pcardid) {
            this.pcardid = pcardid;
        }

        public String getBuydata() {
            return buydata;
        }

        public void setBuydata(String buydata) {
            this.buydata = buydata;
        }

        public String getCarbrand() {
            return carbrand;
        }

        public void setCarbrand(String carbrand) {
            this.carbrand = carbrand;
        }
    }
}
