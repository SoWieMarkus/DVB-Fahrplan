package markus.wieland.dvbfahrplan.ui.departures;

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

    public DepartureItemInteractListener getItemInteractListener(){
        return (DepartureItemInteractListener) onItemInteractListener;
    }

    public class DepartureViewHolder extends DefaultViewHolder<Departure> {

        public DepartureViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {

        }

        @Override
        public void bindItemToViewHolder(Departure departure) {
            ((TextView)findViewById(R.id.textView2)).setText(departure.getFancyRealTime());
            ((TextView)findViewById(R.id.textView3)).setText(departure.getFancyScheduledTime());
            ((TextView)findViewById(R.id.textView4)).setText(departure.getLineName());
            ((TextView)findViewById(R.id.textView5)).setText(departure.getDirection());
            ((TextView)findViewById(R.id.textView6)).setText(departure.getMinutesUntilArriving() + "");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getItemInteractListener().onClick(departure);
                }
            });
        }
    }

}
