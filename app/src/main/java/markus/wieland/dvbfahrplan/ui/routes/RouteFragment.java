package markus.wieland.dvbfahrplan.ui.routes;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;

public class RouteFragment extends DefaultFragment {

    private final RoutesAdapter routesAdapter;

    public RouteFragment(RoutesInteractListener routesInteractListener) {
        super(R.layout.fragment_routes);
        routesAdapter = new RoutesAdapter(routesInteractListener);
    }

    @Override
    public void bindViews() {
        RecyclerView recyclerView = findViewById(R.id.fragment_routes_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(routesAdapter);
    }

    public void update(Routes routes) {
        if (routes == null) routesAdapter.submitList(new ArrayList<>());
        else routesAdapter.submitList(routes.getRouteList());
    }
}
