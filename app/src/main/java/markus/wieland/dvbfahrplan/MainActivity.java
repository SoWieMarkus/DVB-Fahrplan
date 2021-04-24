package markus.wieland.dvbfahrplan;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.dvbfahrplan.ui.departures.DepartureMainFragment;
import markus.wieland.dvbfahrplan.ui.routes.RouteMainFragment;

public class MainActivity extends DefaultActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private RouteMainFragment routeFragment;
    private DepartureMainFragment departureFragment;

    private BottomNavigationView bottomNavigationView;

    private SearchFragment currentFragment;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
    }

    @Override
    public void initializeViews() {
        routeFragment = new RouteMainFragment();
        departureFragment = new DepartureMainFragment();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        showFragment(routeFragment);
    }

    private void showFragment(SearchFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frame_layout, fragment)
                .commit();
        currentFragment = fragment;

    }

    @Override
    public void execute() {


        // TODO shimmer
        // TODO Abfahrtszeiten
        // TODO fußweg am Anfang oder am ende
        // TODO Empty Messages
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_activity_main_bottom_departure) {
            showFragment(departureFragment);
        } else {
            showFragment(routeFragment);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (currentFragment.handleBackPress()) {
            finish();
        }
    }
}