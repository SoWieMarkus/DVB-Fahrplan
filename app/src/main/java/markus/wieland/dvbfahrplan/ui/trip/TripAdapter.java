package markus.wieland.dvbfahrplan.ui.trip;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Position;

public class TripAdapter extends DefaultAdapter<Node, TripAdapter.TripViewHolder> {

    public TripAdapter(TripItemInteractListener onItemInteractListener) {
        super(onItemInteractListener);
    }

    public TripItemInteractListener getOnItemInteractListener() {
        return (TripItemInteractListener) super.getOnItemInteractListener();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TripViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false));
    }

    public int getCurrentStopPosition() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPosition().equals(Position.CURRENT)) {
                if (i > 0) return i - 1;
                return i;
            }
        }
        return 0;
    }

    public class TripViewHolder extends DefaultViewHolder<Node> implements View.OnClickListener {

        private LinearLayout itemTripBottomLine;
        private LinearLayout itemTripTopLine;

        private TextView itemTripTime;
        private TextView itemTripStopName;
        private TextView itemTripPlatform;

        private Node node;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemTripTopLine = findViewById(R.id.item_trip_top);
            itemTripBottomLine = findViewById(R.id.item_trip_bottom);
            itemTripStopName = findViewById(R.id.item_trip_name);
            itemTripPlatform = findViewById(R.id.item_trip_platform);
            itemTripTime = findViewById(R.id.item_trip_time);
        }

        @Override
        public void bindItemToViewHolder(Node node) {
            this.node = node;

            itemTripTime.setText(node.getFancyRealTime());
            itemTripTime.setTextColor(node.delay() != 0 ? Color.RED : Color.WHITE);

            itemTripStopName.setText(node.getName());
            itemTripStopName.setTypeface(null, node.getPosition().equals(Position.CURRENT) ? Typeface.BOLD : Typeface.NORMAL);

            itemTripPlatform.setText(node.getPlatform().getName());

            itemView.setAlpha(node.getPosition().equals(Position.PREVIOUS) ? 0.3f : 1f);

            itemTripBottomLine.setVisibility(getAdapterPosition() == list.size() - 1 ? View.INVISIBLE : View.VISIBLE);
            itemTripTopLine.setVisibility(getAdapterPosition() == 0 ? View.INVISIBLE : View.VISIBLE);

            itemView.setOnClickListener(this);


            Node previousNode = getPreviousNode();
            if (previousNode == null) return;

            itemTripTopLine.setAlpha(previousNode.getPosition().equals(Position.PREVIOUS) && node.getPosition().equals(Position.CURRENT) ? 0.3f : 1f);
        }

        private Node getPreviousNode() {
            int index = getAdapterPosition() - 1;
            return index < 0 ? null : getItem(index);
        }

        @Override
        public void onClick(View v) {
            getOnItemInteractListener().onClick(node);
        }
    }
}
