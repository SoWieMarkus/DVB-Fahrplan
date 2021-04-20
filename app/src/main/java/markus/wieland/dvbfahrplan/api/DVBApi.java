package markus.wieland.dvbfahrplan.api;

import android.app.Activity;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.api.GetRequest;
import markus.wieland.defaultappelements.api.RequestResultListener;
import markus.wieland.dvbfahrplan.api.models.ApiURL;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;

public class DVBApi {

    private static final String BASE_URL = "https://webapi.vvo-online.de/";

    private static final String POINT_FINDER = "tr/pointfinder";
    private static final String ROUTE = "tr/trips";

    private final Activity context;

    public DVBApi(Activity context) {
        this.context = context;
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
