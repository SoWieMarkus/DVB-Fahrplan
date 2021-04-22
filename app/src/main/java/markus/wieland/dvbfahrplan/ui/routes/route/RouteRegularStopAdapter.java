package markus.wieland.dvbfahrplan.ui.routes.route;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.routes.Stop;

public class RouteRegularStopAdapter extends DefaultAdapter<Stop, RouteRegularStopAdapter.RouteRegularStopViewHolder> {

    private final Mode mode;

    public RouteRegularStopAdapter(Mode mode) {
        super(null);
        this.mode = mode;
    }

    @NonNull
    @Override
    public RouteRegularStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteRegularStopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route_stop, parent, false));
    }

    public class RouteRegularStopViewHolder extends DefaultViewHolder<Stop> {

        private ImageView itemStopMarker;
        private LinearLayout itemStopLine;
        private TextView itemStopName;
        private TextView itemStopTime;

        public RouteRegularStopViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemStopMarker = findViewById(R.id.item_stop_marker);
            itemStopLine = findViewById(R.id.item_stop_line);
            itemStopName = findViewById(R.id.item_stop_name);
            itemStopTime = findViewById(R.id.item_stop_arriving);
        }

        @Override
        public void bindItemToViewHolder(Stop stop) {
            itemStopLine.setBackgroundColor(mode.getColor());
            itemStopName.setText(stop.getName());
            itemStopTime.setText(stop.getFancyArrivalTime());
            itemStopMarker.setImageDrawable(mode.getMarker(itemView.getContext()));
        }
    }
}
