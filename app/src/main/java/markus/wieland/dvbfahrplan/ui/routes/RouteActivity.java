package markus.wieland.dvbfahrplan.ui.routes;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import markus.wieland.dvbfahrplan.database.point.PointViewModel;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.ui.routes.route.RouteDetailActivity;

public class RouteActivity extends DefaultActivity implements TextView.OnEditorActionListener, APIResult<Routes>, SelectPointInteractListener, TextWatcher, View.OnFocusChangeListener {

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

    private PointViewModel pointViewModel;

    public RouteActivity() {
        super(R.layout.activity_route);
    }

    @Override
    public void bindViews() {
        setSupportActionBar(findViewById(R.id.toolbar));

        textInputLayoutOrigin = findViewById(R.id.activity_route_origin);
        textInputLayoutDestination = findViewById(R.id.activity_route_destination);
    }

    @Override
    public void initializeViews() {
        pointFinderFragment = new PointFinderFragment(this);
        routeFragment = new RouteFragment(this::onClick);

        assert textInputLayoutOrigin.getEditText() != null;
        assert textInputLayoutDestination.getEditText() != null;

        textInputLayoutDestination.getEditText().addTextChangedListener(this);
        textInputLayoutOrigin.getEditText().addTextChangedListener(this);

        textInputLayoutDestination.getEditText().setOnFocusChangeListener(this);
        textInputLayoutOrigin.getEditText().setOnFocusChangeListener(this);

        pointViewModel = ViewModelProviders.of(this).get(PointViewModel.class);

        loadFragment(pointFinderFragment);
    }

    @Override
    public void execute() {
        dvbApi = new DVBApi(this);
    }

    private void searchForRoute() {
        textInputLayoutDestination.setError(null);
        textInputLayoutOrigin.setError(null);

        if (getOrigin() == null){
            textInputLayoutOrigin.setError(getString(R.string.error_no_stop));
            return;
        }
        if (getDestination() == null){
            textInputLayoutDestination.setError(getString(R.string.error_no_stop));
            return;
        }
        if (getOrigin().equals(getDestination())){
            textInputLayoutDestination.setError(getString(R.string.error_duplicated_stop));
            return;
        }

        dvbApi.searchRoute(this, getOrigin(), getDestination());
        routeFragment.update(null);
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

        textInputLayoutDestination.getEditText().setOnEditorActionListener(this);

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
        pointFinderFragment.update(new ArrayList<>());

        if (originHasFocus) {
            originStopId = point.getId();
            updateTextInputLayout(textInputLayoutOrigin, point);
            textInputLayoutDestination.requestFocus();
        }
        else if (destinationHasFocus) {
            destinationStopId = point.getId();
            updateTextInputLayout(textInputLayoutDestination, point);
            textInputLayoutDestination.clearFocus();
        }

        if (getDestination() != null && getOrigin() != null) {
            searchForRoute();
        }
    }

    private void updateTextInputLayout(TextInputLayout textInputLayout, Point point) {
        assert textInputLayout.getEditText() != null;

        textInputLayout.getEditText().removeTextChangedListener(this);
        textInputLayout.getEditText().setText(point.toString());
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


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        originHasFocus = v.equals(textInputLayoutOrigin.getEditText());
        destinationHasFocus = v.equals(textInputLayoutDestination.getEditText());

        if (originHasFocus) {
            String content = textInputLayoutOrigin.getEditText().getText().toString();
            if (content.length() > 0)
                textInputLayoutOrigin.getEditText().setSelection(content.length()-1);
        }
        if (destinationHasFocus) {
            String content = textInputLayoutDestination.getEditText().getText().toString();
            if (content.length() > 0)
                textInputLayoutDestination.getEditText().setSelection(content.length()-1);}

        if (originHasFocus || destinationHasFocus){
            loadFragment(pointFinderFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment.equals(routeFragment)) {
            textInputLayoutDestination.getEditText().requestFocus();
            loadFragment(pointFinderFragment);
            return;
        }
        if (destinationHasFocus) {
            textInputLayoutOrigin.getEditText().requestFocus();
            return;
        }
        if (originHasFocus) {
            finish();
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            searchForRoute();
            return true;
        }
        return false;
    }
}