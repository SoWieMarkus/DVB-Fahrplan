package markus.wieland.dvbfahrplan.api.models.trip;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import markus.wieland.dvbfahrplan.api.models.Platform;
import markus.wieland.dvbfahrplan.api.models.State;
import markus.wieland.dvbfahrplan.helper.TimeConverter;

public class Node {

    @SerializedName("Id")
    private String id;

    @SerializedName("Place")
    private String place;

    @SerializedName("Name")
    private String name;

    @SerializedName("Longitude")
    private long longitude;

    @SerializedName("Latitude")
    private long latitude;

    @SerializedName("Position")
    private Position position;

    @SerializedName("Platform")
    private Platform platform;

    @SerializedName("Time")
    private String time;

    @SerializedName("RealTime")
    private String realTime;

    @SerializedName("State")
    private State state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getRealTimeAsDateTime() {
        return TimeConverter.convertToLocalDateTime(realTime);
    }

    public LocalDateTime getTimeAsDateTime() {
        return TimeConverter.convertToLocalDateTime(time);
    }

    public String getFancyRealTime() {
        if (realTime == null) return getFancyTime();
        return getRealTimeAsDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFancyTime() {
        return getTimeAsDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public long delay() {
        if (getRealTime() == null) return 0;
        return TimeConverter.getMinutesBetween(getTimeAsDateTime(), getRealTimeAsDateTime());
    }

    public String getDelayAsString() {
        return "+" + delay();
    }
}
