package markus.wieland.dvbfahrplan.api.models.pointfinder;

import com.google.gson.annotations.SerializedName;

public enum PointStatus {
    @SerializedName("Identified") IDENTIFIED,
    @SerializedName("List") LIST
}
