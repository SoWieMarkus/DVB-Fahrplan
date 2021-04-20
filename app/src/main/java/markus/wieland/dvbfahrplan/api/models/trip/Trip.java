package markus.wieland.dvbfahrplan.api.models.trip;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trip {

    @SerializedName("Stops")
    private List<Node> stops;

    public List<Node> getStops() {
        return stops;
    }

    public void setStops(List<Node> stops) {
        this.stops = stops;
    }
}
