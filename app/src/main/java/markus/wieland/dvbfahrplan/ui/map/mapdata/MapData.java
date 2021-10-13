package markus.wieland.dvbfahrplan.ui.map.mapdata;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.api.models.routes.Route;

public class MapData {

    private final List<MapDataPart> mapDataParts;

    public MapData(Route route) {
        this.mapDataParts = new ArrayList<>();
        for (String part : route.getMapData()) {
            mapDataParts.add(new MapDataPart(part));
        }
    }

    public List<MapDataPart> getMapDataParts() {
        return mapDataParts;
    }
}
