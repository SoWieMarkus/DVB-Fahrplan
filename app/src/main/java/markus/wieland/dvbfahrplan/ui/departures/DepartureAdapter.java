package markus.wieland.dvbfahrplan.ui.departures;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.QueryableAdapter;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.departure.Departure;

public class DepartureAdapter extends QueryableAdapter<String, Departure, DepartureAdapter.DepartureViewHolder> {

    public DepartureAdapter(DepartureItemInteractListener departureItemInteractListener) {
        super(departureItemInteractListener);
    }

    @NonNull
    @Override
    public DepartureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DepartureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_departure, parent, false));
    }

    public DepartureItemInteractListener getItemInteractListener() {
        return (DepartureItemInteractListener) onItemInteractListener;
    }

    public class DepartureViewHolder extends DefaultViewHolder<Departure> {

        private TextView itemDepartureLine;
        private TextView itemDepartureName;
        private TextView itemDepartureTime;
        private TextView itemDeparturePlatform;
        private TextView itemDepartureInMinutes;
        private TextView itemDepartureDelay;
        private TextView itemDepartureMinutes;

        public DepartureViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemDepartureDelay = findViewById(R.id.item_departure_delay);
            itemDepartureLine = findViewById(R.id.item_departure_line);
            itemDepartureName = findViewById(R.id.item_departure_name);
            itemDeparturePlatform = findViewById(R.id.item_departure_platform);
            itemDepartureTime = findViewById(R.id.item_departure_time);
            itemDepartureInMinutes = findViewById(R.id.item_departure_arrival);
            itemDepartureMinutes = findViewById(R.id.item_departure_minutes);
        }

        @Override
        public void bindItemToViewHolder(Departure departure) {

            itemView.setAlpha(departure.getMinutesUntilArriving() < 0 ? 0.3f : 1f);

            itemDepartureDelay.setVisibility(departure.getDelay() == 0 ? View.GONE : View.VISIBLE);
            itemDepartureMinutes.setVisibility(departure.getMinutesUntilArriving() > 60
                    || departure.getMinutesUntilArriving() == 0
                    ? View.GONE
                    : View.VISIBLE);

            itemDepartureLine.setText(departure.getLineName());
            itemDepartureLine.setBackground(departure.getMode().getBackground(itemView.getContext()));
            itemDepartureName.setText(departure.getDirection());

            itemDepartureInMinutes.setText(departure.getArrivalTimeAsString(itemView.getContext()));
            itemDeparturePlatform.setText(departure.getPlatform() == null ? "" : departure.getPlatform().toString(itemView.getContext()));
            itemDepartureDelay.setText(departure.getDelayAsString());

            itemDepartureTime.setText(departure.getFancyScheduledTime());

            itemView.setOnClickListener(view -> getItemInteractListener().onClick(departure));
        }


    }

}
