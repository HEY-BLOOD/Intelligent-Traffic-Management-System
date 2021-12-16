package io.b4a.itms.data.model;

import com.google.gson.annotations.SerializedName;

public class Park {

    @SerializedName("Id")
    private String id;
    @SerializedName("parking_name")
    private String parkingName;
    @SerializedName("coordinate_longitude")
    private String coordinateLongitude;
    @SerializedName("coordinate_latitude")
    private String coordinateLatitude;
    @SerializedName("distance")
    private String distance;
    @SerializedName("total_parking_number")
    private String totalParkingNumber;
    @SerializedName("free_parking_number")
    private String freeParkingNumber;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("parking_address")
    private String parkingAddress;
    @SerializedName("open_type")
    private String openType;
    @SerializedName("charging_reference")
    private String chargingReference;

    private int imageId;

    @Deprecated
    public Park(String id, String parkingName, String coordinateLongitude, String coordinateLatitude, String distance, String totalParkingNumber, String freeParkingNumber, String paymentMethod, String parkingAddress, String openType, String chargingReference, int imageId) {
        this.id = id;
        this.parkingName = parkingName;
        this.coordinateLongitude = coordinateLongitude;
        this.coordinateLatitude = coordinateLatitude;
        this.distance = distance;
        this.totalParkingNumber = totalParkingNumber;
        this.freeParkingNumber = freeParkingNumber;
        this.paymentMethod = paymentMethod;
        this.parkingAddress = parkingAddress;
        this.openType = openType;
        this.chargingReference = chargingReference;
        this.imageId = imageId;
    }

    public Park(String parkingName, String coordinateLongitude, String coordinateLatitude, String distance, String totalParkingNumber, String freeParkingNumber, String paymentMethod, String parkingAddress, String openType, String chargingReference, int imageId) {
        this.parkingName = parkingName;
        this.coordinateLongitude = coordinateLongitude;
        this.coordinateLatitude = coordinateLatitude;
        this.distance = distance;
        this.totalParkingNumber = totalParkingNumber;
        this.freeParkingNumber = freeParkingNumber;
        this.paymentMethod = paymentMethod;
        this.parkingAddress = parkingAddress;
        this.openType = openType;
        this.chargingReference = chargingReference;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getCoordinateLongitude() {
        return coordinateLongitude;
    }

    public void setCoordinateLongitude(String coordinateLongitude) {
        this.coordinateLongitude = coordinateLongitude;
    }

    public String getCoordinateLatitude() {
        return coordinateLatitude;
    }

    public void setCoordinateLatitude(String coordinateLatitude) {
        this.coordinateLatitude = coordinateLatitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTotalParkingNumber() {
        return totalParkingNumber;
    }

    public void setTotalParkingNumber(String totalParkingNumber) {
        this.totalParkingNumber = totalParkingNumber;
    }

    public String getFreeParkingNumber() {
        return freeParkingNumber;
    }

    public void setFreeParkingNumber(String freeParkingNumber) {
        this.freeParkingNumber = freeParkingNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public String getChargingReference() {
        return chargingReference;
    }

    public void setChargingReference(String chargingReference) {
        this.chargingReference = chargingReference;
    }
}
