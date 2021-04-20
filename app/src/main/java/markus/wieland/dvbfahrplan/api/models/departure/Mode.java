package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

public enum Mode {

    @SerializedName("Tram") TRAM("tram"),
    @SerializedName("CityBus") CITY_BUS("citybus"),
    @SerializedName("IntercityBus") INTER_CITY_BUS("intercitybus"),
    @SerializedName("Train") TRAIN("train"),
    @SerializedName("Lift") LIFT("lift"),
    @SerializedName("Ferry") FERRY("ferry"),
    @SerializedName("Alita") ALITA_TAXI("alita"),
    @SerializedName("footpath") WALKING("footpath"),
    @SerializedName("RapidTransit") RAPID_TRANSIT("rapidtransit"),
    UNKNOWN("unkown");

    private final String value;

    Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
