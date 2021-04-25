package markus.wieland.dvbfahrplan.ui.routes;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
import markus.wieland.dvbfahrplan.ui.timepicker.TimePickerBottomSheetDialog;
import markus.wieland.dvbfahrplan.ui.timepicker.TimePickerEventListener;

public class RouteMainFragment extends SearchFragment implements View.OnClickListener,TimePickerEventListener, SelectPointInteractListener, Observer<List<Point>>, View.OnFocusChangeListener, TextView.OnEditorActionListener {

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

    private final PointTextWatcher pointTextWatcherOrigin;
    private final PointTextWatcher pointTextWatcherDestination;

    public RouteMainFragment() {
        super(R.layout.activity_route);
        pointFinderFragmentOrigin = new PointFinderFragment(this);
        pointFinderFragmentDestination = new PointFinderFragment(this);
        routeFragment = new RouteFragment(this::onClick);
        pointTextWatcherOrigin = new PointTextWatcher(REQUEST_ORIGIN);
        pointTextWatcherDestination = new PointTextWatcher(REQUEST_DESTINATION);
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
            currentFragment = null;
            loadFragment(pointFinderFragmentDestination);
        }
        if (currentFragment.equals(pointFinderFragmentOrigin)) {
            focus(textInputLayoutOrigin);
            currentFragment = null;
            loadFragment(pointFinderFragmentOrigin);
        }
    }

    private void switchOriginAndDestination(View v) {
        Point tempPoint = originPoint;
        originPoint = destinationPoint;
        destinationPoint = tempPoint;

        textInputLayoutDestination.getEditText().removeTextChangedListener(pointTextWatcherDestination);
        textInputLayoutOrigin.getEditText().removeTextChangedListener(pointTextWatcherOrigin);

        String valueDestination = textInputLayoutDestination.getEditText().getText().toString();
        String valueOrigin = textInputLayoutOrigin.getEditText().getText().toString();

        textInputLayoutDestination.getEditText().setText(valueOrigin);
        textInputLayoutOrigin.getEditText().setText(valueDestination);

        textInputLayoutDestination.getEditText().addTextChangedListener(pointTextWatcherDestination);
        textInputLayoutOrigin.getEditText().addTextChangedListener(pointTextWatcherOrigin);

        searchRoute();
    }

    private void initializeViews() {
        textInputLayoutDestination.getEditText().addTextChangedListener(pointTextWatcherDestination);
        textInputLayoutOrigin.getEditText().addTextChangedListener(pointTextWatcherOrigin);

        textInputLayoutDestination.getEditText().setOnFocusChangeListener(this);
        textInputLayoutOrigin.getEditText().setOnFocusChangeListener(this);

        findViewById(R.id.activity_route_show_time_picker).setOnClickListener(this);

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
    public void onClick(Point point) {
        if (currentFragment.equals(pointFinderFragmentDestination)) {
            destinationPoint = point;
            updateTextInputLayout(textInputLayoutDestination, point, pointTextWatcherDestination);
            clearFocus(textInputLayoutDestination);
        } else {
            originPoint = point;
            updateTextInputLayout(textInputLayoutOrigin, point, pointTextWatcherOrigin);
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

    private void updateTextInputLayout(TextInputLayout textInputLayout, Point point, PointTextWatcher pointTextWatcher) {
        assert textInputLayout.getEditText() != null;

        textInputLayout.getEditText().removeTextChangedListener(pointTextWatcher);
        textInputLayout.getEditText().setText(point.toString());
        textInputLayout.getEditText().addTextChangedListener(pointTextWatcher);
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
        ((Button)findViewById(R.id.activity_route_show_time_picker)).setText(pickedTime.toString(getActivity()));
        searchRoute();
    }

    @Override
    public void onClick(View view) {
        TimePickerBottomSheetDialog timePickerBottomSheetDialog = new TimePickerBottomSheetDialog(pickedTime);
        timePickerBottomSheetDialog.show(getChildFragmentManager(),"Hello");
    }

    private class PointTextWatcher implements TextWatcher {

        private final int requestCode;

        public PointTextWatcher(int requestCode) {
            this.requestCode = requestCode;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Has to be implemented because of text watcher interface
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String query = charSequence.toString().trim();
            if (query.length() == 0) {
                if (requestCode == REQUEST_DESTINATION) destinationPoint = null;
                if (requestCode == REQUEST_ORIGIN) originPoint = null;
            }

            if (query.length() < 3) return;
            dvbApi.searchStops(RouteMainFragment.this::onLoad, query, requestCode);
            loadFragment(destinationHasFocus ? pointFinderFragmentDestination : pointFinderFragmentOrigin);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Has to be implemented because of text watcher interface
        }
    }
}
