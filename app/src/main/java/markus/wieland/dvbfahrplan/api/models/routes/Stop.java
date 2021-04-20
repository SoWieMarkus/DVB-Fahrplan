package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import markus.wieland.dvbfahrplan.api.models.Platform;
import markus.wieland.dvbfahrplan.api.models.State;

public class Stop {

    @SerializedName("ArrivalTime")
    private String arrivalTime;

    @SerializedName("DepartureTime")
    private String departureTime;

    @SerializedName("ArrivalRealTime")
    private String arrivalRealTime;

    @SerializedName("DepartureRealTime")
    private String departureRealTime;

    @SerializedName("Name")
    private String name;

    @SerializedName("Place")
    private String place;

    @SerializedName("DepartureState")
    private State departureState;

    @SerializedName("ArrivalState")
    private State arrivalState;

    @SerializedName("Latitude")
    private long latitude;

    @SerializedName("Longitude")
    private long longitude;

    @SerializedName("Platform")
    private Platform platform;

    public Stop(String arrivalTime, String departureTime, String arrivalRealTime, String departureRealTime, String name, String place, State departureState, State arrivalState, long latitude, long longitude, Platform platform) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.arrivalRealTime = arrivalRealTime;
        this.departureRealTime = departureRealTime;
        this.name = name;
        this.place = place;
        this.departureState = departureState;
        this.arrivalState = arrivalState;
        this.latitude = latitude;
        this.longitude = longitude;
        this.platform = platform;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalRealTime() {
        return arrivalRealTime;
    }

    public void setArrivalRealTime(String arrivalRealTime) {
        this.arrivalRealTime = arrivalRealTime;
    }

    public String getDepartureRealTime() {
        return departureRealTime;
    }

    public void setDepartureRealTime(String departureRealTime) {
        this.departureRealTime = departureRealTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public State getDepartureState() {
        return departureState;
    }

    public void setDepartureState(State departureState) {
        this.departureState = departureState;
    }

    public State getArrivalState() {
        return arrivalState;
    }

    public void setArrivalState(State arrivalState) {
        this.arrivalState = arrivalState;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
