package markus.wieland.dvbfahrplan.ui.departures;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.departure.DepartureMonitor;
import markus.wieland.dvbfahrplan.api.models.lines.Lines;

public class DepartureFragment extends DefaultFragment {

    private final DepartureAdapter departureAdapter;
    private final LinesAdapter linesAdapter;

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

        RecyclerView recyclerViewLines = findViewById(R.id.fragment_departure_lines_recycler_view);
        recyclerViewLines.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewLines.setHasFixedSize(true);
        recyclerViewLines.setAdapter(linesAdapter);
    }

    public void update(DepartureMonitor departureMonitor) {
        departureAdapter.submitList(departureMonitor.getDepartures());
    }

    public void update(Lines lines) {
        linesAdapter.submitList(lines.getLinesList());
    }
}
