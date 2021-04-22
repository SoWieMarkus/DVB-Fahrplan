package markus.wieland.dvbfahrplan.ui.pointfinder;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

public class PointFinderFragment extends DefaultFragment {

    private PointAdapter pointAdapter;

    private final SelectPointInteractListener selectPointInteractListener;

    public PointFinderFragment(SelectPointInteractListener selectPointInteractListener) {
        super(R.layout.fragment_point);
        this.selectPointInteractListener = selectPointInteractListener;
    }

    @Override
    public void bindViews() {
        RecyclerView recyclerView = findViewById(R.id.fragment_point_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        pointAdapter = new PointAdapter(selectPointInteractListener);

        recyclerView.setAdapter(pointAdapter);
    }

    public void update(List<Point> points) {
        pointAdapter.submitList(points);
    }

}
