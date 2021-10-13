package markus.wieland.dvbfahrplan.ui.map.mapdata;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.coordinates.GKCoordinate;
import markus.wieland.dvbfahrplan.api.models.coordinates.WGSCoordinate;

public class MapDataPart {

    private final String mode;
    private final List<WGSCoordinate> wgsCoordinates;

    public MapDataPart(@NotNull String part) {

        this.wgsCoordinates = new ArrayList<>();

        //Part in Form "<Mode>|x-coordinate|y-coordinate|x-coordinate ...

        List<String> coordinates = new ArrayList<>(Arrays.asList(part.split("\\|")));

        this.mode = MapDataHelper.build(coordinates.remove(0)).getMapId();

        for (int i = 0; i < coordinates.size(); i += 2) {
            double x = Double.parseDouble(coordinates.get(i + 1));
            double y = Double.parseDouble(coordinates.get(i));

            Optional<WGSCoordinate> wgsCoordinate = new GKCoordinate(x, y).asWGS();
            if (!wgsCoordinate.isPresent()) continue;
            this.wgsCoordinates.add(wgsCoordinate.get());
        }
    }

    public String getMode() {
        return mode;
    }

    public List<WGSCoordinate> getWgsCoordinates() {
        return wgsCoordinates;
    }
}
