package markus.wieland.dvbfahrplan.ui.departures;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Optional;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.ShowMapActivity;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.coordinates.GKCoordinate;
import markus.wieland.dvbfahrplan.api.models.coordinates.WGSCoordinate;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Trip;

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

        String departureStopId = getIntent().getStringExtra(DEPARTURE_STOP_ID);
        if (departureStopId == null) {
            finish();
            return;
        }

        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchDepartures(this::onLoad, departureStopId);
        dvbApi.searchLines(this::onLoad, departureStopId);
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
        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchTrip(new APIResult<Trip>() {
            @Override
            public void onLoad(Trip s) {
                int x = 0;

                String test = "";

                int i = 0;
                for (Node node : s.getStops()) {
                    i++;
                    if(i!= 1)test+=",";
                    Optional<WGSCoordinate> coordinate = new GKCoordinate((double)node.getLongitude(),(double)node.getLatitude()).asWGS();
                    test+=""+coordinate.get().getLatitude() +","+coordinate.get().getLongitude()+"";
                }





                startActivity(new Intent(DepartureActivity.this, ShowMapActivity.class).putExtra("test",test));

            }
        },departure.getRealTime(),getIntent().getStringExtra(DEPARTURE_STOP_ID),departure.getId().split(":")[1]);
    }
}