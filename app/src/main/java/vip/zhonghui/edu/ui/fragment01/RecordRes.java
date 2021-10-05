package vip.zhonghui.edu.ui.fragment01;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordRes implements Parcelable {

    @SerializedName("CarId")
    private int carId;
    @SerializedName("Time")
    private String time;
    @SerializedName("Cost")
    private int cost;

    protected RecordRes(Parcel in) {
        carId = in.readInt();
        time = in.readString();
        cost = in.readInt();
    }

    public static final Creator<RecordRes> CREATOR = new Creator<RecordRes>() {
        @Override
        public RecordRes createFromParcel(Parcel in) {
            return new RecordRes(in);
        }

        @Override
        public RecordRes[] newArray(int size) {
            return new RecordRes[size];
        }
    };

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(carId);
        dest.writeString(time);
        dest.writeInt(cost);
    }

    public String getFormattedTime() {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String formattedTime = "";
        try {
            Date date = parser.parse(time);
            formattedTime = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }

    public String getFormattedDate() {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String formattedDate = "";
        try {
            Date date = parser.parse(time);
            formattedDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


}
