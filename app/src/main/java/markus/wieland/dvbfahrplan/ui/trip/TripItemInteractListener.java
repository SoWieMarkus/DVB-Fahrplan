package markus.wieland.dvbfahrplan.ui.trip;

import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.dvbfahrplan.api.models.trip.Node;

public interface TripItemInteractListener extends OnItemInteractListener<Node> {

    void onClick(Node node);
}
