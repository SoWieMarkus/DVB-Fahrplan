package markus.wieland.dvbfahrplan.ui.routes;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.QueryableAdapter;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Route;

public class RoutesAdapter extends QueryableAdapter<Long, Route, RoutesAdapter.RoutesViewHolder> {

    public RoutesAdapter(RoutesInteractListener routesInteractListener) {
        super(routesInteractListener);
    }

    @Override
    public RoutesInteractListener getOnItemInteractListener() {
        return (RoutesInteractListener) super.getOnItemInteractListener();
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoutesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false));
    }

    public class RoutesViewHolder extends DefaultViewHolder<Route> {

        private RecyclerView itemRouteMotChain;
        private TextView itemRouteDuration;
        private TextView itemRoutePrice;
        private TextView itemRouteChanges;
        private TextView itemRouteArrival;
        private TextView itemRouteDeparture;

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemRouteMotChain = findViewById(R.id.item_route_mot_chain);
            itemRouteDuration = findViewById(R.id.item_route_duration);
            itemRoutePrice = findViewById(R.id.item_route_price);
            itemRouteChanges = findViewById(R.id.item_route_changes);
            itemRouteArrival = findViewById(R.id.item_route_arrival);
            itemRouteDeparture = findViewById(R.id.item_route_departure);
        }

        @Override
        public void bindItemToViewHolder(Route route) {
            RouteMotChainAdapter motChainAdapter = new RouteMotChainAdapter();

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            itemRouteMotChain.setLayoutManager(layoutManager);
            itemRouteMotChain.setHasFixedSize(true);
            itemRouteMotChain.setAdapter(motChainAdapter);
            motChainAdapter.submitList(route.getMotChain());

            itemRouteDuration.setText(route.getDurationAsString(itemView.getContext()));
            itemRoutePrice.setText(route.getPrice());
            itemRouteChanges.setText(route.getInterchangesAsString(itemView.getContext()));
            itemRouteArrival.setText(route.getArrivalTime(itemView.getContext()));
            itemRouteDeparture.setText(route.getDepartureTime(itemView.getContext()));

            findViewById(R.id.item_route_foreground).setOnClickListener(v -> getOnItemInteractListener().onClick(route));
            findViewById(R.id.item_route_foreground).setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
                    findViewById(R.id.item_route_foreground).performClick();
                    return false;
                }
                itemRouteMotChain.onTouchEvent(event);
                return false;
            });
        }
    }
}
