package markus.wieland.dvbfahrplan.api.models.lines;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import markus.wieland.dvbfahrplan.api.Mode;

public class Line {

    @SerializedName("Name")
    private String name;

    @SerializedName("Mot")
    private Mode mode;

    @SerializedName("Directions")
    private List<Direction> directions;

    public Line(String name, Mode mode, List<Direction> directions) {
        this.name = name;
        this.mode = mode;
        this.directions = directions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mode getMode() {
        if (mode == null) return Mode.UNKNOWN;
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
