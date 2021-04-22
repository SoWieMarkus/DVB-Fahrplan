package markus.wieland.dvbfahrplan.ui.routes;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import markus.wieland.defaultappelements.api.APIResult;
import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointStatus;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.ui.routes.route.RouteDetailActivity;

public class RouteActivity extends DefaultActivity implements APIResult<Routes>, SelectPointInteractListener, TextWatcher {

    private TextInputLayout textInputLayoutOrigin;
    private TextInputLayout textInputLayoutDestination;
    private DVBApi dvbApi;

    private PointFinderFragment pointFinderFragment;
    private RouteFragment routeFragment;

    private String originStopId;
    private String destinationStopId;

    private boolean originHasFocus;
    private boolean destinationHasFocus;

    private Fragment currentFragment;

    public RouteActivity() {
        super(R.layout.activity_route);
    }

    @Override
    public void bindViews() {
        textInputLayoutOrigin = findViewById(R.id.activity_route_origin);
        textInputLayoutDestination = findViewById(R.id.activity_route_destination);
    }

    @Override
    public void initializeViews() {
        pointFinderFragment = new PointFinderFragment(this);
        routeFragment = new RouteFragment(this::onClick);

        textInputLayoutDestination.getEditText().addTextChangedListener(this);
        textInputLayoutOrigin.getEditText().addTextChangedListener(this);

        textInputLayoutDestination.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            destinationHasFocus = hasFocus;
            if (hasFocus) loadFragment(pointFinderFragment);
        });
        textInputLayoutOrigin.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            originHasFocus = hasFocus;
            if (hasFocus) loadFragment(pointFinderFragment);
        });

        loadFragment(pointFinderFragment);
    }

    @Override
    public void execute() {
        dvbApi = new DVBApi(this);
    }

    private void searchForRoute() {
        textInputLayoutDestination.setError(null);
        textInputLayoutOrigin.setError(null);
        if (getOrigin().equals(getDestination())) return;

        if (getOrigin() == null) textInputLayoutOrigin.setError(getString(R.string.error_no_stop));
        if (getDestination() == null) textInputLayoutOrigin.setError(getString(R.string.error_no_stop));

        dvbApi.searchRoute(this, getOrigin(), getDestination());
        loadFragment(routeFragment);
    }

    private void searchPoints(String query) {
        dvbApi.searchStops(this::onLoad, query);
    }

    public void onLoad(PointFinder pointFinder) {
        if (pointFinder.getPointStatus() != null && pointFinder.getPointStatus().equals(PointStatus.IDENTIFIED)) {
            if (originHasFocus) originStopId = pointFinder.getResult().get(0).getId();
            if (destinationHasFocus) destinationStopId = pointFinder.getResult().get(0).getId();
        }
        pointFinderFragment.update(pointFinder.getResult());
    }

    private void switchOriginAndDestination() {
        String tempId = originStopId;
        originStopId = destinationStopId;
        destinationStopId = tempId;

        textInputLayoutDestination.getEditText().removeTextChangedListener(this);
        textInputLayoutOrigin.getEditText().removeTextChangedListener(this);

        String valueDestination = textInputLayoutDestination.getEditText().getText().toString();
        String valueOrigin = textInputLayoutOrigin.getEditText().getText().toString();
        textInputLayoutDestination.getEditText().setText(valueOrigin);
        textInputLayoutOrigin.getEditText().setText(valueDestination);

        textInputLayoutDestination.getEditText().addTextChangedListener(this);
        textInputLayoutOrigin.getEditText().addTextChangedListener(this);
    }

    private void loadFragment(Fragment fragment) {
        if (currentFragment != null && currentFragment.equals(fragment)) return;
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right_animation, R.anim.slide_out_left_animation)
                .addToBackStack(null)
                .replace(R.id.frame_layout, currentFragment).commit();
    }

    private String getOrigin() {
        return originStopId;
    }

    private String getDestination() {
        return destinationStopId;
    }

    @Override
    public void onLoad(Routes routes) {
        routeFragment.update(routes);
    }

    @Override
    public void onClick(Point point) {
        if (originHasFocus) {
            originStopId = point.getId();
            updateTextInputLayout(textInputLayoutOrigin, point);
        }
        if (destinationHasFocus) {
            destinationStopId = point.getId();
            updateTextInputLayout(textInputLayoutDestination, point);
        }
        if (getDestination() != null && getOrigin() != null) {
            searchForRoute();
        }
        if (destinationHasFocus) textInputLayoutDestination.clearFocus();
        else textInputLayoutDestination.getEditText().requestFocus();
        pointFinderFragment.update(new ArrayList<>());
    }

    private void updateTextInputLayout(TextInputLayout textInputLayout, Point point) {
        textInputLayout.getEditText().removeTextChangedListener(this);
        textInputLayout.getEditText().setText(point.getName());
        textInputLayout.getEditText().addTextChangedListener(this);
    }

    @Override
    public void onLocate(Point point) {
        // Will be implemented later
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Isn't needed
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!destinationHasFocus && !originHasFocus) return;
        String query = s.toString().trim();
        loadFragment(pointFinderFragment);
        if (query.length() > 2)
            searchPoints(query);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Isn't needed
    }

    public void onClick(Route route) {
        startActivity(new Intent(this, RouteDetailActivity.class)
                .putExtra(RouteDetailActivity.ROUTE, new Gson().toJson(route)));
    }
}