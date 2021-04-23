package markus.wieland.dvbfahrplan.ui.pointfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.QueryableAdapter;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

public class PointAdapter extends QueryableAdapter<String, Point, PointAdapter.PointViewHolder> {

    public PointAdapter(SelectPointInteractListener selectPointInteractListener) {
        super(selectPointInteractListener);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false));
    }

    @Override
    public SelectPointInteractListener getOnItemInteractListener() {
        return (SelectPointInteractListener) super.getOnItemInteractListener();
    }

    public class PointViewHolder extends DefaultViewHolder<Point> {

        private TextView itemPointName;
        private TextView itemPointPlace;

        public PointViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemPointName = findViewById(R.id.item_point_name);
            itemPointPlace = findViewById(R.id.item_point_place);
        }

        @Override
        public void bindItemToViewHolder(Point point) {
            itemPointName.setText(point.getName());
            itemPointPlace.setText(point.getPlace());
            itemView.setOnClickListener(v -> getOnItemInteractListener().onClick(point));
        }
    }

}
