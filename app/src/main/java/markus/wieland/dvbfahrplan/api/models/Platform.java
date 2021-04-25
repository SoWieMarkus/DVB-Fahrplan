package markus.wieland.dvbfahrplan.api.models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import markus.wieland.dvbfahrplan.R;

public class Platform {

    @SerializedName(value = "Name")
    private String name;

    @SerializedName(value = "Type")
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

    public String toString(Context context) {
        return context.getString(R.string.platform_short) + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Platform platform = (Platform) o;
        return Objects.equals(name, platform.name) &&
                Objects.equals(type, platform.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
