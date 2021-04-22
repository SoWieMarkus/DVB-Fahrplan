package markus.wieland.dvbfahrplan;

import android.content.Intent;
import android.view.View;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.ui.departures.DepartureActivity;
import markus.wieland.dvbfahrplan.ui.routes.RouteActivity;

public class MainActivity extends DefaultActivity {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void initializeViews() {

        findViewById(R.id.departure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DepartureActivity.class));

            }
        });

        findViewById(R.id.route).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouteActivity.class));
            }
        });

    }

    @Override
    public void execute() {

        //startActivity(new Intent(this, RouteActivity.class).putExtra(DepartureActivity.DEPARTURE_STOP_ID, "33000084"));


        // TODO shimmer
        // TODO Farben
        // TODO Abfahrtszeiten
    }
}