package markus.wieland.dvbfahrplan.ui.routes;

import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.dvbfahrplan.api.models.routes.Route;

public interface RoutesInteractListener extends OnItemInteractListener<Route> {

    void onClick(Route route);
}
