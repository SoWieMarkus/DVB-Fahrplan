package markus.wieland.dvbfahrplan;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Optional;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.coordinates.GKCoordinate;
import markus.wieland.dvbfahrplan.api.models.coordinates.WGSCoordinate;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DVBApi dvbApi = new DVBApi(this);
        dvbApi.searchStops(new APIResult<PointFinder>() {
            @Override
            public void onLoad(PointFinder pointFinder) {
                List<Point> points = pointFinder.getResult();
                int x = 0;



                /*dvbApi.searchRoute(new APIResult<Routes>() {
                    @Override
                    public void onLoad(Routes routes) {
                        int x = 0;
                    }
                }, points.get(0).getId(), points.get(1).getId());*/




            }
        }, "Schneeberg");

        dvbApi.searchRoute(new APIResult<Routes>() {
            @Override
            public void onLoad(Routes routes) {
                int x = 0;
            }
        }, "33000028", "33000016");



        Optional<WGSCoordinate> coordinate = new GKCoordinate(4646320.0, 5633913.0).asWGS();
        int x = 0;
        WebView webView = findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/map.html?x=" + coordinate.get().getLatitude() + "&y=" + coordinate.get().getLongitude());
    }
}