package markus.wieland.dvbfahrplan.ui.departures;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;
import markus.wieland.dvbfahrplan.ui.trip.TripActivity;

public class DepartureActivity extends DefaultActivity implements DepartureItemInteractListener {

    public static final String DEPARTURE_STOP_ID = "markus.wieland.dvbfahrplan.ui.departures.DEPARTURE_STOP_ID";

    private RecyclerView recyclerViewDepartures;
    private RecyclerView recyclerViewLines;
    private DepartureAdapter departureAdapter;
    private LinesAdapter linesAdapter;

    public DepartureActivity() {
        super(R.layout.activity_departure);
    }

    @Override
    public void bindViews() {
        recyclerViewDepartures = findViewById(R.id.activity_departures_recycler_view_departures);
        recyclerViewLines = findViewById(R.id.activity_departures_recycler_view_lines);
    }

    @Override
    public void initializeViews() {

        linesAdapter = new LinesAdapter();
        departureAdapter = new DepartureAdapter(this);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLines.setLayoutManager(layoutManager);
        recyclerViewLines.setHasFixedSize(true);
        recyclerViewLines.setAdapter(linesAdapter);

        recyclerViewDepartures.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDepartures.setHasFixedSize(true);
        recyclerViewDepartures.setAdapter(departureAdapter);
    }

    @Override
    public void execute() {
        setTitle("...");

        String departureStopId = getDepartureStopId();
        if (departureStopId == null) {
            finish();
            return;
        }

        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchDepartures(this::onLoad, departureStopId);
        dvbApi.searchLines(this::onLoad, departureStopId);
    }

    public String getDepartureStopId() {
        return getIntent().getStringExtra(DEPARTURE_STOP_ID);
    }

    public void onLoad(DepartureMonitor departureMonitor) {
        setTitle(departureMonitor.getPlace() + " " + departureMonitor.getName());
        departureAdapter.submitList(departureMonitor.getDepartures());
    }

    public void onLoad(Lines lines) {
        linesAdapter.submitList(lines.getLinesList());
    }

    @Override
    public void onClick(Departure departure) {
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra(TripActivity.TRIP_ID, departure.getIdForQuery())
                .putExtra(TripActivity.TRIP_STOP_ID, getDepartureStopId())
                .putExtra(TripActivity.TRIP_TIME, departure.getRealTime());
        startActivity(intent);
    }
}