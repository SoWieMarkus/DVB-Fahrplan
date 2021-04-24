package markus.wieland.dvbfahrplan.ui.routes;

import android.content.Intent;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;

import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.SearchFragment;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.api.models.pointfinder.PointFinder;
import markus.wieland.dvbfahrplan.api.models.routes.Route;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.ui.pointfinder.PointFinderFragment;
import markus.wieland.dvbfahrplan.ui.pointfinder.SelectPointInteractListener;
import markus.wieland.dvbfahrplan.ui.routes.route.RouteDetailActivity;
import markus.wieland.dvbfahrplan.ui.timepicker.PickedTime;
import markus.wieland.dvbfahrplan.ui.timepicker.TimePickerEventListener;

public class RouteMainFragment extends SearchFragment implements TimePickerEventListener, SelectPointInteractListener, Observer<List<Point>>, View.OnFocusChangeListener, TextView.OnEditorActionListener {

    private static final int REQUEST_ORIGIN = 1;
    private static final int REQUEST_DESTINATION = 2;

    private final PointFinderFragment pointFinderFragmentOrigin;
    private final PointFinderFragment pointFinderFragmentDestination;
    private final RouteFragment routeFragment;

    private TextInputLayout textInputLayoutOrigin;
    private TextInputLayout textInputLayoutDestination;

    private boolean destinationHasFocus;
    private boolean originHasFocus;

    private Point originPoint;
    private Point destinationPoint;

    private PickedTime pickedTime;

    public RouteMainFragment() {
        super(R.layout.activity_route);
        pointFinderFragmentOrigin = new PointFinderFragment(this);
        pointFinderFragmentDestination = new PointFinderFragment(this);
        routeFragment = new RouteFragment(this::onClick);
    }

    @Override
    public void bindViews() {
        super.bindViews();

        textInputLayoutOrigin = findViewById(R.id.activity_route_origin);
        textInputLayoutDestination = findViewById(R.id.activity_route_destination);

        findViewById(R.id.activity_route_switch).setOnClickListener(this::switchOriginAndDestination);

        initializeViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentFragment.equals(pointFinderFragmentDestination)) {
            focus(textInputLayoutDestination);
            loadFragment(pointFinderFragmentDestination);
        }

        if (currentFragment.equals(pointFinderFragmentOrigin)) {
            focus(textInputLayoutOrigin);
            loadFragment(pointFinderFragmentOrigin);
        }

    }

    private void switchOriginAndDestination(View v) {
        Point tempPoint = originPoint;
        originPoint = destinationPoint;
        destinationPoint = tempPoint;

        textInputLayoutDestination.getEditText().removeTextChangedListener(this);
        textInputLayoutOrigin.getEditText().removeTextChangedListener(this);

        String valueDestination = textInputLayoutDestination.getEditText().getText().toString();
        String valueOrigin = textInputLayoutOrigin.getEditText().getText().toString();

        textInputLayoutDestination.getEditText().setText(valueOrigin);
        textInputLayoutOrigin.getEditText().setText(valueDestination);

        textInputLayoutDestination.getEditText().addTextChangedListener(this);
        textInputLayoutOrigin.getEditText().addTextChangedListener(this);

        searchRoute();
    }

    private void initializeViews() {

        textInputLayoutDestination.getEditText().addTextChangedListener(this);
        textInputLayoutOrigin.getEditText().addTextChangedListener(this);

        textInputLayoutDestination.getEditText().setOnFocusChangeListener(this);
        textInputLayoutOrigin.getEditText().setOnFocusChangeListener(this);

        textInputLayoutDestination.getEditText().setOnEditorActionListener(this);

        execute();
    }

    private void execute() {
        pointViewModel.getRecentPoints().observe(getActivity(), this);
        pickedTime = new PickedTime();
        loadFragment(pointFinderFragmentOrigin);
    }

    @Override
    public boolean handleBackPress() {
        if (originHasFocus) return true;
        else if (destinationHasFocus) {
            textInputLayoutOrigin.getEditText().requestFocus();
            loadFragment(pointFinderFragmentOrigin);
        } else if (currentFragment.equals(routeFragment)) {
            textInputLayoutDestination.getEditText().requestFocus();
            loadFragment(pointFinderFragmentDestination);
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String query = charSequence.toString().trim();
        if (query.length() < 3) return;
        dvbApi.searchStops(this::onLoad, query, destinationHasFocus ? REQUEST_DESTINATION : REQUEST_ORIGIN);
        loadFragment(destinationHasFocus ? pointFinderFragmentDestination : pointFinderFragmentOrigin);
    }

    public void onLoad(int requestId, PointFinder pointFinder) {
        if (pointFinder.getResult() == null) return;
        if (requestId == REQUEST_DESTINATION) {
            pointFinderFragmentDestination.update(pointFinder.getResult());
            destinationPoint = pointFinder.getResult().isEmpty() ? null : pointFinder.getResult().get(0);
        }
        if (requestId == REQUEST_ORIGIN) {
            pointFinderFragmentOrigin.update(pointFinder.getResult());
            originPoint = pointFinder.getResult().isEmpty() ? null : pointFinder.getResult().get(0);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(Point point) {
        if (currentFragment.equals(pointFinderFragmentDestination)) {
            destinationPoint = point;
            updateTextInputLayout(textInputLayoutDestination, point);
            clearFocus(textInputLayoutDestination);
        } else {
            originPoint = point;
            updateTextInputLayout(textInputLayoutOrigin, point);
            clearFocus(textInputLayoutOrigin);
            focus(textInputLayoutDestination);
        }
        searchRoute();
    }

    public void searchRoute() {
        if (originPoint == null || destinationPoint == null) return;
        if (originPoint.equals(destinationPoint)) return;
        routeFragment.update(null);
        loadFragment(routeFragment);
        dvbApi.searchRoute(this::onLoad, originPoint.getId(), destinationPoint.getId(), pickedTime);

        pointViewModel.updatePoint(originPoint);
        pointViewModel.updatePoint(destinationPoint);
    }

    private void onLoad(Routes routes) {
        routeFragment.update(null);
        routeFragment.update(routes);
        loadFragment(routeFragment);
    }


    public void onClick(Route route) {
        startActivity(new Intent(getActivity(), RouteDetailActivity.class)
                .putExtra(RouteDetailActivity.ROUTE, new Gson().toJson(route)));
    }

    @Override
    public void onLocate(Point point) {
        // Will be implemented later
    }

    @Override
    public void onChanged(List<Point> points) {
        pointFinderFragmentDestination.update(points);
        pointFinderFragmentOrigin.update(points);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        destinationHasFocus = view.equals(textInputLayoutDestination.getEditText()) && b;
        originHasFocus = view.equals(textInputLayoutOrigin.getEditText()) && b;

        if (destinationHasFocus) {
            loadFragment(pointFinderFragmentDestination);
        }
        if (originHasFocus) {
            loadFragment(pointFinderFragmentOrigin);
        }
    }

    private void updateTextInputLayout(TextInputLayout textInputLayout, Point point) {
        assert textInputLayout.getEditText() != null;

        textInputLayout.getEditText().removeTextChangedListener(this);
        textInputLayout.getEditText().setText(point.toString());
        textInputLayout.getEditText().addTextChangedListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            searchRoute();
            clearFocus(textInputLayoutDestination);
            return true;
        }
        return false;

    }

    @Override
    public void onSetDate(PickedTime pickedTime) {
        this.pickedTime = pickedTime;
        searchRoute();
    }
}
