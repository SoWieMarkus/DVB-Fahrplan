package markus.wieland.dvbfahrplan.ui.routes.route;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.ShowMapActivity;
import markus.wieland.dvbfahrplan.api.models.routes.Route;

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
        startActivity(new Intent(this, ShowMapActivity.class).putExtra(ShowMapActivity.ROUTE,getIntent().getStringExtra(ROUTE)));
    }
}