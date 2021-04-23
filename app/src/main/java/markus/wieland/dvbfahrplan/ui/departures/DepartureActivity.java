package markus.wieland.dvbfahrplan.ui.departures;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.DVBApi;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointStatus;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.ui.trip.TripActivity;

public class DepartureActivity extends DefaultActivity implements TextWatcher, SelectPointInteractListener, SwipeRefreshLayout.OnRefreshListener, View.OnFocusChangeListener {

    private SwipeRefreshLayout swipeRefreshLayout;

    private DepartureFragment departureFragment;
    private PointFinderFragment pointFinderFragment;

    private Fragment currentFragment;

    private TextInputLayout textInputLayoutStop;

    private Point currentPoint;

    private DVBApi dvbApi;

    public DepartureActivity() {
        super(R.layout.activity_departure);
    }

    @Override
    public void bindViews() {
        swipeRefreshLayout = findViewById(R.id.activity_departures_swipe_refresh);
        textInputLayoutStop = findViewById(R.id.activity_departure_station);
    }

    @Override
    public void initializeViews() {
        swipeRefreshLayout.setOnRefreshListener(this);
        textInputLayoutStop.getEditText().addTextChangedListener(this);
        textInputLayoutStop.getEditText().setOnFocusChangeListener(this);
    }

    private void loadFragment(Fragment fragment) {
        if (currentFragment != null && currentFragment.equals(fragment)) return;
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right_animation, R.anim.slide_out_left_animation)
                .addToBackStack(null)
                .replace(R.id.frame_layout, currentFragment).commit();
    }

    @Override
    public void execute() {
        setTitle("...");

        dvbApi = new DVBApi(this);

        departureFragment = new DepartureFragment(this::onClick);
        pointFinderFragment = new PointFinderFragment(this);

        loadFragment(pointFinderFragment);
    }

    public void onLoad(DepartureMonitor departureMonitor) {
        swipeRefreshLayout.setRefreshing(false);
        departureFragment.update(departureMonitor);
    }

    public void onLoad(Lines lines) {
        swipeRefreshLayout.setRefreshing(false);
        departureFragment.update(lines);
    }

    public void onLoad(PointFinder pointFinder) {
        pointFinderFragment.update(pointFinder.getResult());
        if (pointFinder.getPointStatus().equals(PointStatus.IDENTIFIED)) {
            this.currentPoint = pointFinder.getResult().get(0);
        }
    }

    public void onClick(Departure departure) {
        Intent intent = new Intent(this, TripActivity.class);
        intent.putExtra(TripActivity.TRIP_ID, departure.getIdForQuery())
                .putExtra(TripActivity.TRIP_STOP_ID, currentPoint.getId())
                .putExtra(TripActivity.TRIP_MODE, departure.getMode());
        startActivity(intent);
    }

    @Override
    public void onClick(Point point) {
        this.currentPoint = point;
        loadFragment(departureFragment);
        search();
    }

    @Override
    public void onLocate(Point point) {
        // Will be implemented later
    }

    private void search() {
        dvbApi.searchDepartures(this::onLoad, currentPoint.getId());
        dvbApi.searchLines(this::onLoad, currentPoint.getId());
    }

    @Override
    public void onRefresh() {
        if (!currentFragment.equals(departureFragment)) {
            swipeRefreshLayout.setRefreshing(false);
            return;
        }
        search();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) loadFragment(pointFinderFragment);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Just because I have to implement the method with TextWatcher
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String query = s.toString().trim();
        if (query.length() < 3) return;
        dvbApi.searchStops(this::onLoad, query);
        loadFragment(pointFinderFragment);
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Just because I have to implement the method with TextWatcher
    }
}