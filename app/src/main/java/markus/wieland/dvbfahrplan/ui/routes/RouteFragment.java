package markus.wieland.dvbfahrplan.ui.routes;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;

public class RouteFragment extends DefaultFragment {

    private final RoutesAdapter routesAdapter;
    private RecyclerView recyclerView;

    private ShimmerFrameLayout shimmerFrameLayout;

    public RouteFragment(RoutesInteractListener routesInteractListener) {
        super(R.layout.fragment_routes);
        routesAdapter = new RoutesAdapter(routesInteractListener);
    }

    public RouteFragment() {
        super(R.layout.fragment_routes);
        this.routesAdapter = new RoutesAdapter(null);
    }

    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.fragment_routes_recycler_view);
        shimmerFrameLayout = findViewById(R.id.fragment_route_loading);
    }

    @Override
    public void initializeViews() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(routesAdapter);
        if (!routesAdapter.getList().isEmpty())
            shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void execute() {
        // Nothing to do here
    }

    public void update(Routes routes) {

        if (routes == null || routes.getRouteList() == null) {
            routesAdapter.submitList(new ArrayList<>());
        } else routesAdapter.submitList(routes.getRouteList());

        if (recyclerView != null && routes != null) {
            findViewById(R.id.fragment_routes_empty).setVisibility(routes.getRouteList() == null || routes.getRouteList().isEmpty() ? View.VISIBLE : View.GONE);
        }

        if (shimmerFrameLayout != null) {
            shimmerFrameLayout.setVisibility(routes == null ? View.VISIBLE : View.GONE);
        }
    }

}
