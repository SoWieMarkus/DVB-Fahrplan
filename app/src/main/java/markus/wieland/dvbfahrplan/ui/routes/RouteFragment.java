package markus.wieland.dvbfahrplan.ui.routes;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.util.ArrayList;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Routes;
import markus.wieland.dvbfahrplan.ui.timepicker.PickedTime;
import markus.wieland.dvbfahrplan.ui.timepicker.TimePickerBottomSheetDialog;
import markus.wieland.dvbfahrplan.ui.timepicker.TimePickerEventListener;

public class RouteFragment extends DefaultFragment implements TimePickerEventListener {

    private final RoutesAdapter routesAdapter;
    private RecyclerView recyclerView;

    private PickedTime pickedTime;

    public RouteFragment(RoutesInteractListener routesInteractListener) {
        super(R.layout.fragment_routes);
        routesAdapter = new RoutesAdapter(routesInteractListener);
    }

    public RouteFragment() {
        super(R.layout.fragment_routes);
        this.routesAdapter = new RoutesAdapter(null);
    }

    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.fragment_routes_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(routesAdapter);

        pickedTime = new PickedTime();
    }

    public void update(Routes routes) {

        if (routes == null || routes.getRouteList() == null) {
            routesAdapter.submitList(new ArrayList<>());
        } else routesAdapter.submitList(routes.getRouteList());

        if (recyclerView != null && routes != null) {
            findViewById(R.id.fragment_routes_empty).setVisibility(routes.getRouteList() == null || routes.getRouteList().isEmpty() ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onSetDate(PickedTime pickedTime) {
        this.pickedTime = pickedTime;
    }
}
