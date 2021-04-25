package markus.wieland.dvbfahrplan.ui.routes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.routes.Mot;

import static android.view.View.GONE;

public class RouteMotChainAdapter extends DefaultAdapter<Mot, RouteMotChainAdapter.RouteMotChainViewHolder> {

    private static final int LAYOUT_FOOT_PATH = 1;
    private static final int LAYOUT_VEHICLE = 2;

    public RouteMotChainAdapter() {
        super(null);
    }

    @NonNull
    @Override
    public RouteMotChainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @LayoutRes int layoutId = R.layout.item_mode;
        if (viewType == LAYOUT_FOOT_PATH)
            layoutId = R.layout.item_mode_footpath;
        return new RouteMotChainViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getMode().equals(Mode.WALKING))
            return LAYOUT_FOOT_PATH;
        return LAYOUT_VEHICLE;
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

            if (mode.equals(Mode.WALKING)) {
                loadWalking(mot);
            } else {
                loadVehicle(mot);
            }
        }

        private void loadWalking(Mot mot) {
            Mode mode = mot.getMode();
            itemModeIcon.setImageDrawable(mode.getIcon(itemView.getContext()));
            itemModeNext.setVisibility(getAdapterPosition() == getItemCount() - 1 ? GONE : View.VISIBLE);
        }

        private void loadVehicle(Mot mot){
            Mode mode = mot.getMode();
            itemModeIcon.setImageDrawable(mode.getIcon(itemView.getContext()));
            itemModeLine.setBackground(mode.getBackground(itemView.getContext()));
            itemModeLine.setText(mot.getName() == null ? "" : mot.getName());
            itemModeNext.setVisibility(getAdapterPosition() == getItemCount() - 1 ? GONE : View.VISIBLE);
        }
    }
}
