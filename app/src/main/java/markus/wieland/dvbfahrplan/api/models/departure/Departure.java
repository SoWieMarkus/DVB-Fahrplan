package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

import markus.wieland.dvbfahrplan.api.models.Platform;
import markus.wieland.dvbfahrplan.api.models.State;

public class Departure {

    @SerializedName(value="Id")
    private String id;

    @SerializedName(value="DlId")
    private String dlId;

    @SerializedName(value="LineName")
    private String lineName;

    @SerializedName(value="Direction")
    private String direction;

    @SerializedName(value="Platform")
    private Platform platform;

    @SerializedName(value="Mot")
    private Mode mode;

    @SerializedName(value="RealTime")
    private String realTime;

    @SerializedName(value="ScheduledTime")
    private String scheduledTime;

    @SerializedName("State")
    private State state;

    //TODO Changes and CancelReasons

    public String getId() {
        return id;
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
}
