package io.b4a.itms.ui.road_status;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SenseRes implements Parcelable {


    @SerializedName("RESULT")
    private String result;
    @SerializedName("ERRMSG")
    private String errmsg;
    @SerializedName("pm2.5")
    private int pm25;
    @SerializedName("co2")
    private int co2;
    @SerializedName("LightIntensity")
    private int lightIntensity;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("temperature")
    private int temperature;

    protected SenseRes(Parcel in) {
        result = in.readString();
        errmsg = in.readString();
        pm25 = in.readInt();
        co2 = in.readInt();
        lightIntensity = in.readInt();
        humidity = in.readInt();
        temperature = in.readInt();
    }

    @Override
    public String toString() {
        return "SenseRes{" +
                "result='" + result + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", pm25=" + pm25 +
                ", co2=" + co2 +
                ", lightIntensity=" + lightIntensity +
                ", humidity=" + humidity +
                ", temperature=" + temperature +
                '}';
    }

    public static final Creator<SenseRes> CREATOR = new Creator<SenseRes>() {
        @Override
        public SenseRes createFromParcel(Parcel in) {
            return new SenseRes(in);
        }

        @Override
        public SenseRes[] newArray(int size) {
            return new SenseRes[size];
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

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(int lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeString(errmsg);
        dest.writeInt(pm25);
        dest.writeInt(co2);
        dest.writeInt(lightIntensity);
        dest.writeInt(humidity);
        dest.writeInt(temperature);
    }
}
