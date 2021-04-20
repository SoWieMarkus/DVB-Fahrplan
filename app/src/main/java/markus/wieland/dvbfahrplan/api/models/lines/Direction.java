package markus.wieland.dvbfahrplan.api.models.lines;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Direction {

    @SerializedName("Name")
    private String name;

    @SerializedName("TimeTables")
    private List<TimeTable> timeTables;

    public Direction(String name, List<TimeTable> timeTables) {
        this.name = name;
        this.timeTables = timeTables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
