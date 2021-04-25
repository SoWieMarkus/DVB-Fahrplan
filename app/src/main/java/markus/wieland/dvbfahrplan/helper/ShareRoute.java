package markus.wieland.dvbfahrplan.helper;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.api.models.routes.PartialRoute;
import markus.wieland.dvbfahrplan.ui.routes.route.RouteAdapter;

public class ShareRoute {

    private final List<PartialRoute> partialRoutes;
    private final RouteAdapter routeAdapter;
    private final ViewGroup parent;

    public ShareRoute(List<PartialRoute> partialRoutes, ViewGroup parent) {
        this.partialRoutes = partialRoutes;
        this.parent = parent;
        this.routeAdapter = new RouteAdapter();
        this.routeAdapter.submitList(partialRoutes);
    }

    public List<View> getViews() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < partialRoutes.size(); i++) {
            int viewType = routeAdapter.getItemViewType(i);
            PartialRoute partialRoute = partialRoutes.get(i);

            RouteAdapter.RouteViewHolder childView = routeAdapter.onCreateViewHolder(parent, viewType);
            childView.bindItemToViewHolder(partialRoute);
            views.add(childView.getItemView());
        }
        return views;
    }
}
