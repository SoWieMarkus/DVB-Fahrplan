package markus.wieland.dvbfahrplan.ui.pointfinder;

import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

public interface SelectPointInteractListener extends OnItemInteractListener<Point> {

    void onClick(Point point);
    void onLocate(Point point);
}
