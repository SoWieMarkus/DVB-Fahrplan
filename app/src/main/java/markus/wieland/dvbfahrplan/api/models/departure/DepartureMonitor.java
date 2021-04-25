package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

import markus.wieland.dvbfahrplan.api.models.Status;
import markus.wieland.dvbfahrplan.helper.TimeConverter;

public class DepartureMonitor {

    @SerializedName(value = "Name")
    private String name;

    @SerializedName(value = "Place")
    private String place;

    @SerializedName("Departures")
    private List<Departure> departures;

    @SerializedName("ExpirationTime")
    private String expirationTime;

    @SerializedName("Status")
    private Status status;

    public List<Departure> getDepartures() {
        return departures;
    }

    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
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

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getExpirationTimeAsLocalDateTime() {
        return TimeConverter.convertToLocalDateTime(expirationTime);
    }
}
