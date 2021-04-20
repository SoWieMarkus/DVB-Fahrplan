package markus.wieland.dvbfahrplan.api.models.trip;

import com.google.gson.annotations.SerializedName;

public enum Position {
    @SerializedName("Next") NEXT,
    @SerializedName("Previous") PREVIOUS,
    @SerializedName("Current") CURRENT;
}
