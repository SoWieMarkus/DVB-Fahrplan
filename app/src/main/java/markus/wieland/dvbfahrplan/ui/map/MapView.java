package markus.wieland.dvbfahrplan.ui.map;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.routes.PartialRoute;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.api.models.routes.Stop;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Trip;

public class MapView extends WebView {

    private final Gson gson;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gson = new Gson();
        init();
    }

    private void init() {
        loadUrl("file:///android_asset/map.html");
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("MyApplication", consoleMessage.message() + " -- From line "
                        + consoleMessage.lineNumber() + " of "
                        + consoleMessage.sourceId());
                return super.onConsoleMessage(consoleMessage);
            }
        });
    }

    public void showTrip(Trip trip, Mode mode) {
        List<TripNode> nodes = new ArrayList<>();
        for (Node node : trip.getStops()) {
            TripNode tripNode = new TripNode(node);
            tripNode.setMode(mode);
            nodes.add(tripNode);
        }
        loadUrl("javascript:showTrip(" + gson.toJson(nodes) + ")");
    }

    public void focus(Node node) {
        loadUrl("javascript:focus(" + gson.toJson(new TripNode(node)) + ")");
    }

    public void focus(Coordinate coordinate) {
        loadUrl("javascript:focus(" + gson.toJson(coordinate) + ")");
    }

    public void show(Coordinate coordinate) {
        loadUrl("javascript:show(" + gson.toJson(coordinate) + ")");
    }

    public void showRoute(Route route) {
        List<MapRoute> nodes = new ArrayList<>();

        for (PartialRoute partialRoute : route.getRouteList()) {
            nodes.add(new MapRoute(partialRoute));
        }
        loadUrl("javascript:showRoute(" + gson.toJson(nodes) + ")");

    }


}
