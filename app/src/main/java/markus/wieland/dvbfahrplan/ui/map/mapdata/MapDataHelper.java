package markus.wieland.dvbfahrplan.ui.map.mapdata;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import markus.wieland.dvbfahrplan.api.Mode;

/**
 * This class exists to convert the mode declared in the first part of the map data string into a mode enum.
 * This class will be build by Gson. There are multiple possible values to parse a mode enum.
 * Thats why this class exists
 */
public class MapDataHelper {

    private static final Gson gson = new Gson();

    @SerializedName("mode")
    private final Mode mode;

    public MapDataHelper(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }

    public static Mode build(String mode) {
        return gson.fromJson("{\"mode\":\"" + mode + "\"}", MapDataHelper.class).getMode();
    }

}
