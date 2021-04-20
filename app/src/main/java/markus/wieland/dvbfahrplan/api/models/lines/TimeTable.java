package markus.wieland.dvbfahrplan.api.models.lines;

import com.google.gson.annotations.SerializedName;

public class TimeTable {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    public TimeTable(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
