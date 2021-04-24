package markus.wieland.dvbfahrplan.ui.departures;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;

public class DepartureFragment extends DefaultFragment {

    private final DepartureAdapter departureAdapter;
    private final LinesAdapter linesAdapter;

    private ShimmerFrameLayout shimmerFrameLayout;

    public DepartureFragment(DepartureItemInteractListener departureItemInteractListener) {
        super(R.layout.fragment_departure);
        departureAdapter = new DepartureAdapter(departureItemInteractListener);
        linesAdapter = new LinesAdapter();
    }

    @Override
    public void bindViews() {
        RecyclerView recyclerViewDepartures = findViewById(R.id.fragment_departure_departures_recycler_view);
        recyclerViewDepartures.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewDepartures.setHasFixedSize(true);
        recyclerViewDepartures.setAdapter(departureAdapter);

        shimmerFrameLayout = findViewById(R.id.fragment_departure_loading);

        RecyclerView recyclerViewLines = findViewById(R.id.fragment_departure_lines_recycler_view);
        recyclerViewLines.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLines.setHasFixedSize(true);
        recyclerViewLines.setAdapter(linesAdapter);
    }

    public void update(DepartureMonitor departureMonitor) {
        if (shimmerFrameLayout == null) return;
        if (departureMonitor == null) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            departureAdapter.submitList(new ArrayList<>());
            linesAdapter.submitList(new ArrayList<>());
            findViewById(R.id.fragment_departure_empty).setVisibility(View.GONE);
        } else {
            shimmerFrameLayout.setVisibility(View.GONE);
            findViewById(R.id.fragment_departure_empty).setVisibility(departureMonitor.getDepartures() == null
                    || departureMonitor.getDepartures().isEmpty()
                    ? View.VISIBLE
                    : View.GONE);
            departureAdapter.submitList(departureMonitor.getDepartures() == null
                    ? new ArrayList<>()
                    : departureMonitor.getDepartures());
        }

    }

    public void update(Lines lines) {
        linesAdapter.submitList(lines.getLinesList());
    }
}
