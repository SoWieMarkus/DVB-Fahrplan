package markus.wieland.dvbfahrplan.ui.routes.route;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.ShowMapActivity;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.ui.map.MapView;

import static android.view.View.GONE;

public class RouteDetailActivity extends DefaultActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    public static final String ROUTE = "markus.wieland.dvbfahrplan.ui.routes.route.ROUTE";
    private final Gson gson;

    private RecyclerView recyclerViewRoute;
    private RouteAdapter routeAdapter;
    private MapView mapViewRoute;

    private TextView textViewPrice;
    private TextView textViewDuration;

    public RouteDetailActivity() {
        super(R.layout.activity_route_detail);
        gson = new Gson();
    }

    @Override
    public void bindViews() {
        recyclerViewRoute = findViewById(R.id.activity_route_detail_recycler_view);
        mapViewRoute = findViewById(R.id.activity_route_detail_map_view);
        textViewDuration = findViewById(R.id.activity_route_detail_duration);
        textViewPrice = findViewById(R.id.activity_route_detail_price);
    }

    @Override
    public void initializeViews() {

        findViewById(R.id.coordinator_layout).getViewTreeObserver().addOnGlobalLayoutListener(this);

        routeAdapter = new RouteAdapter();
        recyclerViewRoute.setHasFixedSize(true);
        recyclerViewRoute.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRoute.setAdapter(routeAdapter);

        Route route = getRoute();
        textViewDuration.setText(route.getDurationAsString());
        textViewPrice.setVisibility(route.getPrice() == null ? GONE : View.VISIBLE);
        textViewPrice.setText(route.getPrice() == null ? "" : route.getPrice());
    }

    private Route getRoute() {
        return gson.fromJson(getIntent().getStringExtra(ROUTE), Route.class);
    }

    @Override
    public void execute() {
        routeAdapter.submitList(getRoute().getRouteList());
    }

    @Override
    public void onGlobalLayout() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)mapViewRoute.getLayoutParams();
        params.height = findViewById(R.id.coordinator_layout).getHeight() - BottomSheetBehavior.from(findViewById(R.id.bottom_sheet)).getPeekHeight();
        mapViewRoute.setLayoutParams(params);
        mapViewRoute.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mapViewRoute.showRoute(getRoute());
            }
        });
        findViewById(R.id.coordinator_layout).getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onBackPressed() {
        if (BottomSheetBehavior.from(findViewById(R.id.bottom_sheet)).getState() == BottomSheetBehavior.STATE_EXPANDED) {
            BottomSheetBehavior.from(findViewById(R.id.bottom_sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}