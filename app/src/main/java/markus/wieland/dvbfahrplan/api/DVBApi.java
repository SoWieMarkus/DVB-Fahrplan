package markus.wieland.dvbfahrplan.api;

import android.app.Activity;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.api.GetRequest;
import markus.wieland.defaultappelements.api.RequestResultListener;
import markus.wieland.dvbfahrplan.api.models.ApiURL;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.api.models.trip.Trip;

public class DVBApi {

    private static final String BASE_URL = "https://webapi.vvo-online.de/";

    private static final String POINT_FINDER = "tr/pointfinder";
    private static final String ROUTE = "tr/trips";
    private static final String DEPARTURE = "dm";
    private static final String LINES = "stt/lines";
    private static final String TRIPS = "dm/trip";

    private final Activity context;

    public DVBApi(Activity context) {
        this.context = context;
    }

    public void searchTrip(APIResult<Trip> apiResult, String date, String stopId, String tripId) {
        ApiURL url = new ApiURL(BASE_URL + TRIPS)
                .append("time", date)
                .append("tripId", tripId)
                .append("stopId", stopId);
        GetRequest<Trip> routesGetRequest = new GetRequest<>(Trip.class, url.toString(), new RequestResultListener<Trip>() {
            @Override
            public void onLoad(Trip response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void searchRoute(APIResult<Routes> apiResult, String origin, String destination) {
        ApiURL url = new ApiURL(BASE_URL + ROUTE)
                .append("origin", origin)
                .append("destination", destination);
        GetRequest<Routes> routesGetRequest = new GetRequest<>(Routes.class, url.toString(), new RequestResultListener<Routes>() {
            @Override
            public void onLoad(Routes response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void searchLines(APIResult<Lines> apiResult, String id) {
        ApiURL url = new ApiURL(BASE_URL + LINES)
                .append("stopid", id);
        GetRequest<Lines> routesGetRequest = new GetRequest<>(Lines.class, url.toString(), new RequestResultListener<Lines>() {
            @Override
            public void onLoad(Lines response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void searchDepartures(APIResult<DepartureMonitor> apiResult, String id) {
        ApiURL url = new ApiURL(BASE_URL + DEPARTURE)
                .append("stopid", id)
                .append("limit", 20);
        GetRequest<DepartureMonitor> routesGetRequest = new GetRequest<>(DepartureMonitor.class, url.toString(), new RequestResultListener<DepartureMonitor>() {
            @Override
            public void onLoad(DepartureMonitor response) {
                notifyClient(response, apiResult);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        routesGetRequest.execute();
    }

    public void searchStops(APIResult<PointFinder> result, String query) {
        ApiURL url = new ApiURL(BASE_URL + POINT_FINDER)
                .append("query", query)
                .append("stopsOnly", true)
                .append("regionalOnly", true);
        GetRequest<PointFinder> pointFinderGetRequest = new GetRequest<>(PointFinder.class, url.toString(), new RequestResultListener<PointFinder>() {
            @Override
            public void onLoad(PointFinder response) {
                notifyClient(response, result);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        pointFinderGetRequest.execute();
    }

    private <T> void notifyClient(T t, APIResult<T> result) {
        try {
            context.runOnUiThread(() -> result.onLoad(t));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
