package markus.wieland.dvbfahrplan.api.models.routes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import markus.wieland.dvbfahrplan.api.Mode;

public class Mot {

    @SerializedName("Type")
    private Mode mode;

    @SerializedName("Name")
    private String name;

    @SerializedName("Direction")
    private String direction;

    @SerializedName("Changes")
    private List<String> changes;

    public Mode getMode() {
        if (mode == null) return Mode.UNKNOWN;
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<String> getChanges() {
        return changes;
    }

    public void setChanges(List<String> changes) {
        this.changes = changes;
    }
}
