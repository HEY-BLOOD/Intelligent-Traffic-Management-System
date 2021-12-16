package io.b4a.itms.ui.traffic_light;

import com.google.gson.annotations.SerializedName;

public class LightInfoRes {

    private int roadId;
    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("RedTime")
    private String redTime;
    @SerializedName("GreenTime")
    private String greenTime;
    @SerializedName("YellowTime")
    private String yellowTime;

//    Integer.parseInt(str)

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

    public String getRedTime() {
        return redTime;
    }

    public void setRedTime(String redTime) {
        this.redTime = redTime;
    }

    public String getGreenTime() {
        return greenTime;
    }

    public void setGreenTime(String greenTime) {
        this.greenTime = greenTime;
    }

    public String getYellowTime() {
        return yellowTime;
    }

    public void setYellowTime(String yellowTime) {
        this.yellowTime = yellowTime;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    @Override
    public String toString() {
        return "LightInfoRes{" +
                "result='" + result + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", redTime='" + redTime + '\'' +
                ", greenTime='" + greenTime + '\'' +
                ", yellowTime='" + yellowTime + '\'' +
                ", roadId='" + roadId + '\'' +
                '}';
    }
}
