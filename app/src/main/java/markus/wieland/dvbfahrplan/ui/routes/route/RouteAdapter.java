package markus.wieland.dvbfahrplan.ui.routes.route;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.routes.Mot;
import markus.wieland.dvbfahrplan.api.models.routes.PartialRoute;

public class RouteAdapter extends DefaultAdapter<PartialRoute, RouteAdapter.RouteViewHolder> {
    private static final int BETWEEN_ROUTE = 1;
    private static final int ONLY_ONE_PART = 3;
    private static final int ROUTE = 2;

    public RouteAdapter() {
        super(null);
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId = R.layout.item_route_partial_route;
        if (viewType == BETWEEN_ROUTE) layoutId = R.layout.item_route_between;
        if (viewType == ONLY_ONE_PART) layoutId = R.layout.item_route_top;
        return new RouteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        PartialRoute partialRoute = list.get(position);
        Mot mot = partialRoute.getLine();

        if (mot == null || mot.getMode() == null) return ROUTE;
        if (mot.getMode().equals(Mode.WALKING) || mot.getMode().equals(Mode.CHANGE_PLATFORM) || mot.getMode().equals(Mode.STAY_FOR_CONNECTION) || mot.getMode().equals(Mode.WAITING))
            return BETWEEN_ROUTE;
        if (mot.getMode().equals(Mode.ONLY_ONE_PART))
            return ONLY_ONE_PART;

        return ROUTE;
    }



    public class RouteViewHolder extends DefaultViewHolder<PartialRoute> {

        private TextView itemPartialRouteOriginStop;
        private TextView itemPartialRouteOriginTime;
        private TextView itemPartialRouteOriginTimeDelay;
        private TextView itemPartialRouteOriginPlatform;
        private TextView itemPartialRouteDestinationPlatform;
        private TextView itemPartialRouteDestinationStop;
        private TextView itemPartialRouteDestinationTime;
        private TextView itemPartialRouteDestinationTimeDelay;

        private TextView itemPartialRouteModeName;
        private TextView itemPartialRouteModeDirection;

        private ImageView itemPartialRouteModeIcon;
        private ImageView itemPartialRouteStartMarker;
        private ImageView itemPartialRouteEndMarker;

        private TextView itemPartialRouteDuration;
        private TextView itemPartialRouteAmountStops;
        private ImageView itemPartialRouteExpand;

        private LinearLayout itemPartialRouteMarkerLine;
        private LinearLayout itemPartialRouteExpandStops;

        private RecyclerView itemPartialRouteRecyclerView;


        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemPartialRouteModeIcon = findViewById(R.id.item_partial_route_mode_icon);
            itemPartialRouteMarkerLine = findViewById(R.id.item_partial_route_marker_line);
            itemPartialRouteStartMarker = findViewById(R.id.item_partial_route_start_marker);
            itemPartialRouteEndMarker = findViewById(R.id.item_partial_route_end_marker);

            itemPartialRouteRecyclerView = findViewById(R.id.item_partial_route_recycler_view);
            itemPartialRouteRecyclerView.setHasFixedSize(true);
            itemPartialRouteRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            itemPartialRouteDuration = findViewById(R.id.item_partial_route_duration);
            itemPartialRouteAmountStops = findViewById(R.id.item_partial_route_amount_stops);
            itemPartialRouteExpand = findViewById(R.id.item_partial_route_expand);

            itemPartialRouteOriginTime = findViewById(R.id.item_partial_route_arrival);
            itemPartialRouteOriginStop = findViewById(R.id.item_partial_route_origin_name);
            itemPartialRouteOriginPlatform = findViewById(R.id.item_partial_route_origin_platform);

            itemPartialRouteDestinationTime = findViewById(R.id.item_partial_route_destination_time);
            itemPartialRouteDestinationStop = findViewById(R.id.item_partial_route_destination_name);
            itemPartialRouteDestinationPlatform = findViewById(R.id.item_partial_route_destination_platform);

            itemPartialRouteExpandStops = findViewById(R.id.item_partial_route_expand_stops);

            itemPartialRouteModeName = findViewById(R.id.item_partial_route_mode_name);
            itemPartialRouteModeDirection = findViewById(R.id.item_partial_route_mode_direction);
            itemPartialRouteOriginTimeDelay = findViewById(R.id.item_partial_route_delay_departure);
            itemPartialRouteDestinationTimeDelay = findViewById(R.id.item_partial_route_delay_arrival);
        }

        public View getItemView(){
            return itemView;
        }

        @Override
        public void bindItemToViewHolder(PartialRoute route) {
            Mode mode = route.getLine().getMode();
            if (mode == null) return;

            Context context = itemView.getContext();

            if (mode.equals(Mode.ONLY_ONE_PART)) {
                itemPartialRouteEndMarker.setImageDrawable(mode.getMarker(context));
                if (getAdapterPosition() == 0) {
                    itemPartialRouteOriginStop.setText(route.getOrigin().toString());
                    itemPartialRouteOriginTime.setText(route.getOrigin().getFancyDepartureTime());
                } else {
                    itemPartialRouteOriginStop.setText(route.getDestination().toString());
                    itemPartialRouteOriginTime.setText(route.getDestination().getFancyDepartureTime());
                }
                return;
            }

            itemPartialRouteModeIcon.setImageDrawable(mode.getIcon(context));
            itemPartialRouteDuration.setText(route.getDurationAsString(context));

            if (mode.equals(Mode.WALKING) || mode.equals(Mode.CHANGE_PLATFORM) || mode.equals(Mode.WAITING) || mode.equals(Mode.STAY_FOR_CONNECTION))
                return;

            itemPartialRouteAmountStops.setText(route.getAmountOfStops(context));

            itemPartialRouteDestinationTimeDelay.setVisibility(route.getDestination().getDelayArrival() == 0 ? View.GONE : View.VISIBLE);
            itemPartialRouteOriginTimeDelay.setVisibility(route.getOrigin().getDelayDeparture() == 0 ? View.GONE : View.VISIBLE);
            itemPartialRouteDestinationTimeDelay.setText("+" + route.getDestination().getDelayArrival());
            itemPartialRouteOriginTimeDelay.setText("+" + route.getOrigin().getDelayDeparture());

            itemPartialRouteEndMarker.setImageDrawable(mode.getMarker(context));
            itemPartialRouteStartMarker.setImageDrawable(mode.getMarker(context));
            itemPartialRouteModeName.setBackground(mode.getBackground(context));
            itemPartialRouteMarkerLine.setBackgroundColor(mode.getColor());

            itemPartialRouteModeName.setText(route.getLine().getName());
            itemPartialRouteModeDirection.setText(route.getLine().getDirection());

            RouteRegularStopAdapter routeRegularStopAdapter = new RouteRegularStopAdapter(mode);
            routeRegularStopAdapter.submitList(route.getStopsBetween());

            itemPartialRouteRecyclerView.setAdapter(routeRegularStopAdapter);

            itemPartialRouteOriginTime.setText(route.getOrigin().getFancyDepartureTime());
            itemPartialRouteOriginPlatform.setText(route.getOrigin().getPlatform() == null
                    ? ""
                    : route.getOrigin().getPlatform().toString(itemView.getContext()));
            itemPartialRouteOriginStop.setText(route.getOrigin().toString());

            itemPartialRouteDestinationTime.setText(route.getDestination().getFancyArrivalTime());
            itemPartialRouteDestinationPlatform.setText(route.getDestination().getPlatform() == null
                    ? ""
                    : route.getDestination().getPlatform().toString(itemView.getContext()));
            itemPartialRouteDestinationStop.setText(route.getDestination().toString());

            toggleStopsBetween(route, context);

            itemPartialRouteExpand.setVisibility(route.getStopsBetween().isEmpty() ? View.GONE : View.VISIBLE);

            itemPartialRouteExpandStops.setOnClickListener(v -> {
                route.setExpanded(!route.isExpanded());
                toggleStopsBetween(route, context);
            });
        }

        private void toggleStopsBetween(PartialRoute route, Context context) {
            itemPartialRouteRecyclerView.setVisibility(route.isExpanded() ? View.VISIBLE : View.GONE);
            itemPartialRouteExpand.setImageDrawable(route.isExpanded()
                    ? ContextCompat.getDrawable(context, R.drawable.ic_collapse)
                    : ContextCompat.getDrawable(context, R.drawable.ic_expand));
        }
    }
}
