package markus.wieland.dvbfahrplan.ui.departures;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.SearchFragment;
import markus.wieland.dvbfahrplan.ui.trip.TripActivity;

public class DepartureMainFragment extends SearchFragment implements Observer<List<Point>>,View.OnFocusChangeListener, SelectPointInteractListener, SwipeRefreshLayout.OnRefreshListener, TextView.OnEditorActionListener {

    private TextInputLayout textInputLayoutStation;

    private boolean stationInputLayoutHasFocus;

    private DepartureFragment departureFragment;
    private PointFinderFragment pointFinderFragment;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Point currentPoint;

    public DepartureMainFragment() {
        super(R.layout.activity_departure);
    }

    @Override
    public void bindViews() {
        super.bindViews();
        textInputLayoutStation = findViewById(R.id.activity_departure_station);
        swipeRefreshLayout = findViewById(R.id.activity_departures_swipe_refresh);
        initializeViews();
    }

    private void initializeViews() {
        departureFragment = new DepartureFragment(this::onClick);
        pointFinderFragment = new PointFinderFragment(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        textInputLayoutStation.getEditText().setOnFocusChangeListener(this);
        textInputLayoutStation.getEditText().addTextChangedListener(this);
        textInputLayoutStation.getEditText().setOnEditorActionListener(this);

        pointViewModel.getRecentPoints().observe(getActivity(),this);

        loadFragment(pointFinderFragment);
        execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (currentFragment.equals(pointFinderFragment)) focus(textInputLayoutStation);
    }

    @Override
    public void onStop() {
        super.onStop();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textInputLayoutStation.getEditText().getWindowToken(), 0);
    }

    private void execute() {
        pointFinderFragment.update(pointViewModel.getRecentPoints().getValue());
    }

    private void onClick(Departure departure) {
        Intent intent = new Intent(getActivity(), TripActivity.class);
        intent.putExtra(TripActivity.TRIP_ID, departure.getIdForQuery())
                .putExtra(TripActivity.TRIP_STOP_ID, currentPoint.getId())
                .putExtra(TripActivity.TRIP_LINE, departure.getLineName())
                .putExtra(TripActivity.TRIP_DIRECTION, departure.getDirection())
                .putExtra(TripActivity.TRIP_MODE, departure.getMode());
        startActivity(intent);
    }

    public void onClick(Point point) {
        if (point == null) return;
        textInputLayoutStation.getEditText().removeTextChangedListener(this);
        textInputLayoutStation.getEditText().setText(point.toString());
        textInputLayoutStation.getEditText().addTextChangedListener(this);
        this.currentPoint = point;
        loadFragment(departureFragment);
        search();
        clearFocus(textInputLayoutStation);
    }

    @Override
    public void onLocate(Point point) {

    }

    public void onLoad(DepartureMonitor departureMonitor) {
        swipeRefreshLayout.setRefreshing(false);
        departureFragment.update(departureMonitor);
        findViewById(R.id.frame_layout).requestFocus();
    }

    public void onLoad(Lines lines) {
        departureFragment.update(lines);
    }

    public void onLoad(PointFinder pointFinder) {
        pointFinderFragment.update(pointFinder.getResult());
        if (!pointFinder.getResult().isEmpty()) {
            currentPoint = pointFinder.getResult().get(0);
        }
    }

    private void search() {
        if (currentPoint == null) return;
        dvbApi.searchDepartures(this::onLoad, currentPoint.getId());
        dvbApi.searchLines(this::onLoad, currentPoint.getId());
        pointViewModel.updatePoint(currentPoint);
    }

    @Override
    public boolean handleBackPress() {
        if (currentFragment.equals(departureFragment)) {
            focus(textInputLayoutStation);
            return false;
        }
        return stationInputLayoutHasFocus;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        stationInputLayoutHasFocus = view.equals(textInputLayoutStation.getEditText()) && b;
        if (stationInputLayoutHasFocus) {
            loadFragment(pointFinderFragment);
            departureFragment.update((DepartureMonitor) null);
        }
    }

    @Override
    public void onRefresh() {
        if (currentFragment.equals(pointFinderFragment)) swipeRefreshLayout.setRefreshing(false);
        else search();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String query = charSequence.toString().trim();
        if (query.length() < 3) return;
        dvbApi.searchStops(this::onLoad, query);
        loadFragment(pointFinderFragment);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onClick(currentPoint);
            return true;
        }
        return false;
    }

    @Override
    public void onChanged(List<Point> points) {
        pointFinderFragment.update(points);
    }
}
