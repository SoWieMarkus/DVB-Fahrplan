package markus.wieland.dvbfahrplan;

import android.content.Intent;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.ui.departures.DepartureActivity;

public class MainActivity extends DefaultActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void execute() {

        startActivity(new Intent(this, DepartureActivity.class).putExtra(DepartureActivity.DEPARTURE_STOP_ID, "33000084"));

        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchStops(new APIResult<PointFinder>() {
            @Override
            public void onLoad(PointFinder pointFinder) {
                int x = 0;
            }
        }, "Centrum");
    }
}