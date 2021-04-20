package markus.wieland.dvbfahrplan;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Optional;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.coordinates.GKCoordinate;
import markus.wieland.dvbfahrplan.api.models.coordinates.WGSCoordinate;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.ui.departures.DepartureActivity;
import markus.wieland.dvbfahrplan.ui.departures.DepartureAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(this, DepartureActivity.class).putExtra(DepartureActivity.DEPARTURE_STOP_ID, "33000016"));


        Optional<WGSCoordinate> coordinate = new GKCoordinate(4646320.0, 5633913.0).asWGS();

        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/map.html?x=" + coordinate.get().getLatitude() + "&y=" + coordinate.get().getLongitude());
    }
}