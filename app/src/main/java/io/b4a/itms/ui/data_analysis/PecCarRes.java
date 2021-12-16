package io.b4a.itms.ui.data_analysis;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PecCarRes implements Parcelable {

    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("ROWS_DETAIL")
    private List<ROWSDETAILDTO> rowsDetail;

    protected PecCarRes(Parcel in) {
        result = in.readString();
        errmsg = in.readString();
    }

    public static final Creator<PecCarRes> CREATOR = new Creator<PecCarRes>() {
        @Override
        public PecCarRes createFromParcel(Parcel in) {
            return new PecCarRes(in);
        }

        @Override
        public PecCarRes[] newArray(int size) {
            return new PecCarRes[size];
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
        @SerializedName("pcode")
        private String pcode;
        @SerializedName("paddr")
        private String paddr;
        @SerializedName("datetime")
        private String datetime;

        public String getCarnumber() {
            return carnumber;
        }

        public void setCarnumber(String carnumber) {
            this.carnumber = carnumber;
        }

        public String getPcode() {
            return pcode;
        }

        public void setPcode(String pcode) {
            this.pcode = pcode;
        }

        public String getPaddr() {
            return paddr;
        }

        public void setPaddr(String paddr) {
            this.paddr = paddr;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }
}
