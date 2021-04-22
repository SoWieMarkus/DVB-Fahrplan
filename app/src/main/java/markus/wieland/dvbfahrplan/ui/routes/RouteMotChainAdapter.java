package markus.wieland.dvbfahrplan.ui.routes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.routes.Mot;

import static android.view.View.GONE;

public class RouteMotChainAdapter extends DefaultAdapter<Mot, RouteMotChainAdapter.RouteMotChainViewHolder> {

    public RouteMotChainAdapter() {
        super(null);
    }

    @NonNull
    @Override
    public RouteMotChainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RouteMotChainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mode, parent, false));
    }

    public class RouteMotChainViewHolder extends DefaultViewHolder<Mot> {

        private ImageView itemModeIcon;
        private TextView itemModeLine;
        private ImageView itemModeNext;

        public RouteMotChainViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            itemModeIcon = findViewById(R.id.item_mode_icon);
            itemModeLine = findViewById(R.id.item_mode_line);
            itemModeNext = findViewById(R.id.item_mode_next);
        }

        @Override
        public void bindItemToViewHolder(Mot mot) {
            Mode mode = mot.getMode();
            if (mode == null) return;

            itemModeIcon.setImageDrawable(mode.getIcon(itemView.getContext()));
            itemModeLine.setBackground(mode.getBackground(itemView.getContext()));
            itemModeLine.setText(mot.getName() == null ? "" : mot.getName());

            itemModeNext.setVisibility(getAdapterPosition() == getItemCount() - 1 ? GONE : View.VISIBLE);
        }


    }

}
