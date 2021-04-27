package markus.wieland.dvbfahrplan.ui.pointfinder;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

public class PointFinderFragment extends DefaultFragment {

    private final PointAdapter pointAdapter;
    private RecyclerView recyclerView;

    public PointFinderFragment(SelectPointInteractListener selectPointInteractListener) {
        super(R.layout.fragment_point);
        pointAdapter = new PointAdapter(selectPointInteractListener);
    }

    public PointFinderFragment() {
        super(R.layout.fragment_point);
        pointAdapter = new PointAdapter(null);
    }

    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.fragment_point_recycler_view);
    }

    @Override
    public void initializeViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(pointAdapter);
    }

    @Override
    public void execute() {
        // Nothing to do here
    }

    public void update(List<Point> points) {
        if (points == null) return;
        pointAdapter.submitList(points);

        if (recyclerView == null) return;
        findViewById(R.id.fragment_point_empty).setVisibility(points.isEmpty() ? View.VISIBLE : View.GONE);

        if (!points.isEmpty())
            recyclerView.scrollToPosition(0);
    }

}
