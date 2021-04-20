package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

public enum Mode {

    @SerializedName("tram") TRAM("tram"),
    @SerializedName("citybus") CITY_BUS("citybus"),
    @SerializedName("intercitybus") INTER_CITY_BUS("intercitybus"),
    @SerializedName("train") TRAIN("train"),
    @SerializedName("lift") LIFT("lift"),
    @SerializedName("ferry") FERRY("ferry"),
    @SerializedName("alita") ALITA_TAXI("alita"),
    @SerializedName("footpath") WALKING("footpath"),
    @SerializedName("rapidtransit") RAPID_TRANSIT("rapidtransit"),
    UNKNOWN("unkown");

    private final String value;

    Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
