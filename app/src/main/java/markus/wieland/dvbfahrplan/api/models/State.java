package markus.wieland.dvbfahrplan.api.models;

import com.google.gson.annotations.SerializedName;

public enum State {

    @SerializedName("InTime") IN_TIME,
    @SerializedName("Delayed") DELAYED,
    @SerializedName("Unkown") UNKOWN;

}
