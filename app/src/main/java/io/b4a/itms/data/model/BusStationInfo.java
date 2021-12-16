package io.b4a.itms.data.model;

public class BusStationInfo {
    private int busId;
    private int distance;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public BusStationInfo(int busId, int distance) {
        this.busId = busId;
        this.distance = distance;
    }
}
