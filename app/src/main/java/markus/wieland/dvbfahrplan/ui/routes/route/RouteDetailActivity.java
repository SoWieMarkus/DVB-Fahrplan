package markus.wieland.dvbfahrplan.ui.routes.route;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.TimeConverter;
import markus.wieland.dvbfahrplan.api.models.routes.Mot;
import markus.wieland.dvbfahrplan.api.models.routes.PartialRoute;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.api.models.routes.Stop;

public class RouteDetailActivity extends DefaultActivity {

    public static final String ROUTE = "markus.wieland.dvbfahrplan.ui.routes.route.ROUTE";
    private final Gson gson;

    private RecyclerView recyclerViewRoute;
    private RouteAdapter routeAdapter;

    public RouteDetailActivity() {
        super(R.layout.activity_route_detail);
        gson = new Gson();
    }

    @Override
    public void bindViews() {
        recyclerViewRoute = findViewById(R.id.activity_route_detail_recycler_view);

    }

    @Override
    public void initializeViews() {
        routeAdapter = new RouteAdapter();
        recyclerViewRoute.setHasFixedSize(true);
        recyclerViewRoute.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRoute.setAdapter(routeAdapter);
    }

    private Route getRoute() {
        return gson.fromJson(getIntent().getStringExtra(ROUTE), Route.class);
    }



    @Override
    public void execute() {
        routeAdapter.submitList(getRoute().getRouteList());
    }
}