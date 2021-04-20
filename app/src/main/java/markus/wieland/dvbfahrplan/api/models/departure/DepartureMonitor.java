package markus.wieland.dvbfahrplan.api.models.departure;

import com.google.gson.annotations.SerializedName;

public class DepartureMonitor {

    @SerializedName(value="Name")
    private String name;

    @SerializedName(value="Place")
    private String place;

    @SerializedName("ExpirationTime")
    private String expirationTime;







}
