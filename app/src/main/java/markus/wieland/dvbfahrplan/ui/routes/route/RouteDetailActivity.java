package markus.wieland.dvbfahrplan.ui.routes.route;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.helper.ShareRoute;
import markus.wieland.dvbfahrplan.helper.ViewToBitmap;
import markus.wieland.dvbfahrplan.ui.map.MapView;

import static android.view.View.GONE;

public class RouteDetailActivity extends DefaultActivity implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

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
        textViewDuration.setText(route.getDurationAsString(this));
        textViewPrice.setVisibility(route.getPrice() == null ? GONE : View.VISIBLE);
        textViewPrice.setText(route.getPrice() == null ? "" : route.getPrice());

        findViewById(R.id.activity_route_detail_share).setOnClickListener(v -> shareImageUri(getImageUri(ViewToBitmap.getBitmapFromMultipleViews(getViewsToShare(), recyclerViewRoute.getWidth()))));
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
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mapViewRoute.getLayoutParams();
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

    @Override
    public void onClick(View v) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, getRoute().toString(this));
        startActivity(Intent.createChooser(intentShare, "Shared the text ..."));
    }

    private List<View> getViewsToShare() {
        List<View> views = new ArrayList<>();
        views.add(mapViewRoute);
        views.addAll(new ShareRoute(getRoute().getRouteList(), findViewById(R.id.coordinator_layout)).getViews());
        return views;
    }

    private void shareImageUri(Uri uri) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getRoute().toString(this));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}