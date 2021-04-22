package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;
import markus.wieland.dvbfahrplan.api.TimeConverter;
import markus.wieland.dvbfahrplan.api.models.Platform;
import markus.wieland.dvbfahrplan.api.models.State;

public class Departure implements QueryableEntity<String> {

    @SerializedName(value = "Id")
    private String id;

    @SerializedName(value = "DlId")
    private String dlId;

    @SerializedName(value = "LineName")
    private String lineName;

    @SerializedName(value = "Direction")
    private String direction;

    @SerializedName(value = "Platform")
    private Platform platform;

    @SerializedName(value = "Mot")
    private Mode mode;

    @SerializedName(value = "RealTime")
    private String realTime;

    @SerializedName(value = "ScheduledTime")
    private String scheduledTime;

    @SerializedName("State")
    private State state;

    @SerializedName("RouteChanges")
    private List<String> routeChanges;

    public List<String> getRouteChanges() {
        return routeChanges;
    }

    public String getIdForQuery() {
        String[] contents = getId().split(":");
        return contents[0] + ":" + contents[1];
    }

    public void setRouteChanges(List<String> routeChanges) {
        this.routeChanges = routeChanges;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getStringToApplyQuery() {
        return lineName + direction;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDlId() {
        return dlId;
    }

    public void setDlId(String dlId) {
        this.dlId = dlId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public LocalDateTime getRealTimeAsLocalDate() {
        if (realTime == null) return getScheduledTimeAsLocalDate();
        return TimeConverter.convertToLocalDateTime(realTime);
    }

    public LocalDateTime getScheduledTimeAsLocalDate() {
        return TimeConverter.convertToLocalDateTime(scheduledTime);
    }

    public long getDelay() {
        return TimeConverter.getMinutesBetween(getScheduledTimeAsLocalDate(), getRealTimeAsLocalDate());
    }

    public long getMinutesUntilArriving() {
        return TimeConverter.getMinutesBetween(LocalDateTime.now(), getRealTimeAsLocalDate());
    }

    public String getFancyRealTime() {
        return getRealTimeAsLocalDate().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getFancyScheduledTime() {
        return getScheduledTimeAsLocalDate().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getArrivalTimeAsString() {
        long minutes = getMinutesUntilArriving();
        if (minutes <= 60) return String.valueOf(minutes);
        return getFancyRealTime();
    }

    public String getDelayAsString() {
        return "+" + getDelay();
    }


}
