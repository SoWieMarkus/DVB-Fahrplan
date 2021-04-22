package markus.wieland.dvbfahrplan.api.models.routes;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;

public class PartialRoute {

    @SerializedName("Duration")
    private int duration;

    @SerializedName("Infos")
    private List<String> infos;

    @SerializedName("RegularStops")
    private List<Stop> regularStops;

    @SerializedName("Mot")
    private Mot line;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

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

    public Stop getOrigin() {
        return regularStops.isEmpty() ? null : regularStops.get(0);
    }

    public Stop getDestination() {
        return regularStops.isEmpty() ? null : regularStops.get(regularStops.size() - 1);
    }

    public List<Stop> getStopsBetween() {
        List<Stop> stops = new ArrayList<>();
        if (regularStops.size() <= 2) return stops;
        for (int i = 1; i < regularStops.size() - 1; i++) {
            stops.add(regularStops.get(i));
        }
        return stops;
    }

    public String getDurationAsString(Context context) {
        int hours = duration / 60;
        int minutes = duration % 60;

        String minutesPart = minutes + " " + context.getString(R.string.minute_short);
        String hoursPart = hours > 0 ? hours + " " + context.getString(R.string.hour_short_term) + " " : "";

        String backPart = "";
        if (line.getMode() == Mode.WAITING)
            backPart = context.getString(R.string.route_waiting_time);
        if (line.getMode() == Mode.CHANGE_PLATFORM)
            backPart = context.getString(R.string.route_transfer_time);

        return hoursPart + minutesPart + (backPart.length() == 0 ? "" : " ") + backPart;
    }

    public String getAmountOfStops(Context context) {
        int stops = getStopsBetween().size();
        if (stops == 1) return "- " + stops + " " + context.getString(R.string.stops_singular);
        if (stops > 1) return "- " +stops + " " + context.getString(R.string.stops);
        return "";
    }

}
