package markus.wieland.dvbfahrplan.ui.routes.newdesing;

import android.text.Editable;

import com.google.android.material.textfield.TextInputLayout;

import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.ui.routes.RouteFragment;

public class RouteMainFragment extends SearchFragment implements SelectPointInteractListener {

    private final PointFinderFragment pointFinderFragmentOrigin;
    private final PointFinderFragment pointFinderFragmentDestination;
    private final RouteFragment routeFragment;

    private TextInputLayout textInputLayoutOrigin;
    private TextInputLayout textInputLayoutDestination;
    private DVBApi dvbApi;

    public RouteMainFragment() {
        super(R.layout.activity_route);
        pointFinderFragmentOrigin = new PointFinderFragment(this);
        pointFinderFragmentDestination = new PointFinderFragment(this);
        routeFragment = new RouteFragment(this::onClick);
    }

    @Override
    public void bindViews() {
        super.bindViews();
    }

    @Override
    public boolean handleBackPress() {
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(Point point) {

    }


    public void onClick(Route route) {

    }

    @Override
    public void onLocate(Point point) {

    }
}
