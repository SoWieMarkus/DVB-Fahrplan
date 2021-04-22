package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import markus.wieland.dvbfahrplan.api.TimeConverter;
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

    public LocalDateTime getArrivalTimeAsLocalDate() {
        return TimeConverter.convertToLocalDateTime(arrivalTime);
    }

    public LocalDateTime getRealArrivalTimeAsLocalDateTime() {
        if (arrivalRealTime == null) return getArrivalTimeAsLocalDate();
        return TimeConverter.convertToLocalDateTime(arrivalRealTime);
    }

    public LocalDateTime getDepartureTimeAsLocalDate() {
        return TimeConverter.convertToLocalDateTime(departureTime);
    }

    public LocalDateTime getRealDepartureTimeAsLocalDate() {
        if (departureRealTime == null) return getDepartureTimeAsLocalDate();
        return TimeConverter.convertToLocalDateTime(departureRealTime);
    }

    public long getDelayArrival() {
        return TimeConverter.getMinutesBetween(getArrivalTimeAsLocalDate(), getRealArrivalTimeAsLocalDateTime());
    }

    public long getDelayDeparture() {
        return TimeConverter.getMinutesBetween(getDepartureTimeAsLocalDate(), getRealDepartureTimeAsLocalDate());
    }

    public String getFancyArrivalTime() {
        return getArrivalTimeAsLocalDate().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFancyDepartureTime() {
        return getDepartureTimeAsLocalDate().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString(){
        if (getPlace().equalsIgnoreCase("Dresden")) return getName();
        return getPlace() + " " + getName();
    }
}
