package markus.wieland.dvbfahrplan.api.models;

import com.google.gson.annotations.SerializedName;

public class Platform {

    @SerializedName(value="Name")
    private String name;

    @SerializedName(value="Type")
    private String type;

    public Platform(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
