package markus.wieland.dvbfahrplan.ui.departures;

import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;

public interface DepartureItemInteractListener extends OnItemInteractListener<Departure> {
    void onClick(Departure departure);
}
