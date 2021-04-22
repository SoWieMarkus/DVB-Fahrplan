package markus.wieland.dvbfahrplan.ui.map;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.api.models.routes.PartialRoute;
import markus.wieland.dvbfahrplan.api.models.routes.Stop;

public class MapRoute {

    private final List<TripNode> nodes;
    private final String mode;

    public MapRoute(PartialRoute partialRoute) {
        nodes = new ArrayList<>();
        mode = partialRoute.getLine().getMode().getMapId();
        if (partialRoute.getRegularStops() == null) return;
        for (Stop stop : partialRoute.getRegularStops()) {
            nodes.add(new TripNode(stop));
        }

    }

    public List<TripNode> getNodes() {
        return nodes;
    }

    public String getMode() {
        return mode;
    }
}
