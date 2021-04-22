package markus.wieland.dvbfahrplan.ui.trip;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.TimeConverter;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Trip;
import markus.wieland.dvbfahrplan.ui.map.MapView;

public class TripActivity extends DefaultActivity implements APIResult<Trip>, TripItemInteractListener {

    public static final String TRIP_STOP_ID = "markus.wieland.dvbfahrplan.ui.trip.TRIP_STOP_ID";
    public static final String TRIP_ID = "markus.wieland.dvbfahrplan.ui.trip.TRIP_ID";
    public static final String TRIP_MODE = "markus.wieland.dvbfahrplan.ui.trip.MODE";

    private RecyclerView recyclerViewTrip;
    private MapView mapViewTrip;
    private TripAdapter tripAdapter;

    public TripActivity() {
        super(R.layout.activity_trip);
    }

    @Override
    public void bindViews() {
        mapViewTrip = findViewById(R.id.activity_trip_map_view);
        recyclerViewTrip = findViewById(R.id.activity_trip_recycler_view);
    }

    @Override
    public void initializeViews() {
        tripAdapter = new TripAdapter(this, getMode());

        recyclerViewTrip.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrip.setAdapter(tripAdapter);
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

    @Override
    public void execute() {
        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchTrip(this, getTime(), getStopId(), getTripId());
    }

    @Override
    public void onLoad(Trip trip) {
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
    }
}