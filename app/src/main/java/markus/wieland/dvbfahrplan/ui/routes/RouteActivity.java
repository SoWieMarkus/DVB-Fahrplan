package markus.wieland.dvbfahrplan.ui.routes;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;

public class RouteActivity extends DefaultActivity implements APIResult<Routes> {

    public static final String ORIGIN_STOP_ID = "markus.wieland.dvbfahrplan.ui.routes.ORIGIN_STOP_ID";
    public static final String DESTINATION_STOP_ID = "markus.wieland.dvbfahrplan.ui.routes.DESTINATION_STOP_ID ";

    public RouteActivity() {
        super(R.layout.activity_route);
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void execute() {
        DVBApi dvbApi = new DVBApi(this);


        dvbApi.searchRoute(new APIResult<Routes>() {
            @Override
            public void onLoad(Routes routes) {

            }
        }, getOrigin(), getDestination());
    }

    private String getOrigin() {
        return getIntent().getStringExtra(ORIGIN_STOP_ID);
    }

    private String getDestination() {
        return getIntent().getStringExtra(DESTINATION_STOP_ID);
    }

    @Override
    public void onLoad(Routes routes) {

    }
}