package markus.wieland.dvbfahrplan.ui.trip;

import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.TimeConverter;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Trip;
import markus.wieland.dvbfahrplan.ui.map.MapView;

public class TripActivity extends DefaultActivity implements APIResult<Trip>, TripItemInteractListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final String TRIP_STOP_ID = "markus.wieland.dvbfahrplan.ui.trip.TRIP_STOP_ID";
    public static final String TRIP_ID = "markus.wieland.dvbfahrplan.ui.trip.TRIP_ID";
    public static final String TRIP_MODE = "markus.wieland.dvbfahrplan.ui.trip.MODE";
    public static final String TRIP_LINE = "markus.wieland.dvbfahrplan.ui.trip.LINE";
    public static final String TRIP_DIRECTION = "markus.wieland.dvbfahrplan.ui.trip.DIRECTION";

    private RecyclerView recyclerViewTrip;
    private MapView mapViewTrip;
    private TripAdapter tripAdapter;

    private TextView textViewLine;
    private TextView textViewDirection;

    public TripActivity() {
        super(R.layout.activity_trip);
    }

    @Override
    public void bindViews() {
        mapViewTrip = findViewById(R.id.activity_trip_map_view);
        recyclerViewTrip = findViewById(R.id.activity_trip_recycler_view);

        textViewLine = findViewById(R.id.activity_trip_line);
        textViewDirection = findViewById(R.id.activity_trip_direction);

    }

    @Override
    public void initializeViews() {
        tripAdapter = new TripAdapter(this, getMode());

        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrip.setAdapter(tripAdapter);

        textViewLine.setText(getTripLine());
        textViewLine.setBackground(getMode().getBackground(this));
        textViewDirection.setText(getTripDirection());

        findViewById(R.id.coordinator_layout).getViewTreeObserver().addOnGlobalLayoutListener(this);


    }

    @Override
    public void onGlobalLayout() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mapViewTrip.getLayoutParams();
        params.height = findViewById(R.id.coordinator_layout).getHeight() - BottomSheetBehavior.from(findViewById(R.id.bottom_sheet)).getPeekHeight();
        mapViewTrip.setLayoutParams(params);
        findViewById(R.id.coordinator_layout).getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private String getTime() {
        return TimeConverter.getTimeAsSAP();
    }

    private Mode getMode() {
        return (Mode) getIntent().getSerializableExtra(TRIP_MODE);
    }

    private String getStopId() {
        return getIntent().getStringExtra(TRIP_STOP_ID);
    }

    private String getTripId() {
        return getIntent().getStringExtra(TRIP_ID);
    }

    private String getTripLine() {
        return getIntent().getStringExtra(TRIP_LINE);
    }

    private String getTripDirection() {
        return getIntent().getStringExtra(TRIP_DIRECTION);
    }

    @Override
    public void execute() {
        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchTrip(this, getTime(), getStopId(), getTripId());
    }

    @Override
    public void onLoad(Trip trip) {
        findViewById(R.id.activity_trip_loading).setVisibility(View.GONE);
        mapViewTrip.showTrip(trip, getMode());
        mapViewTrip.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mapViewTrip.showTrip(trip, getMode());
            }
        });
        tripAdapter.submitList(trip.getStops());
        recyclerViewTrip.scrollToPosition(tripAdapter.getCurrentStopPosition());

    }

    @Override
    public void onClick(Node node) {
        mapViewTrip.focus(node);
        BottomSheetBehavior.from(findViewById(R.id.bottom_sheet)).setState(BottomSheetBehavior.STATE_COLLAPSED);
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