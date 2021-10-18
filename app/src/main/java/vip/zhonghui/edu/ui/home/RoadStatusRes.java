package vip.zhonghui.edu.ui.home;

import com.google.gson.annotations.SerializedName;

public class RoadStatusRes {


    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("Status")
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
