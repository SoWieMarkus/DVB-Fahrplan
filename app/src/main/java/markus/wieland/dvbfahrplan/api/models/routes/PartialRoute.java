package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartialRoute {

    @SerializedName("Duration")
    private int duration;

    @SerializedName("Infos")
    private List<String> infos;

    @SerializedName("RegularStops")
    private List<Stop> regularStops;

    @SerializedName("Mot")
    private Mot line;

    public Mot getLine() {
        return line;
    }

    public void setLine(Mot line) {
        this.line = line;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getInfos() {
        return infos;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }

    public List<Stop> getRegularStops() {
        return regularStops;
    }

    public void setRegularStops(List<Stop> regularStops) {
        this.regularStops = regularStops;
    }
}
