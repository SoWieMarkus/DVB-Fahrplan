package markus.wieland.dvbfahrplan.ui.routes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.routes.Route;

public class RoutesAdapter extends DefaultAdapter<Route, RoutesAdapter.RoutesViewHolder> {

    public RoutesAdapter(OnItemInteractListener<Route> onItemInteractListener) {
        super(onItemInteractListener);
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RoutesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false));
    }

    public class RoutesViewHolder extends DefaultViewHolder<Route> {

        public RoutesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {

        }

        @Override
        public void bindItemToViewHolder(Route route) {

        }
    }
}
