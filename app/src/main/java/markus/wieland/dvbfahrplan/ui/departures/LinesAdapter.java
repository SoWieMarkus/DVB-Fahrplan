package markus.wieland.dvbfahrplan.ui.departures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.dvbfahrplan.R;
import markus.wieland.dvbfahrplan.api.models.lines.Line;

public class LinesAdapter extends DefaultAdapter<Line, LinesAdapter.LinesViewHolder> {

    public LinesAdapter() {
        super(null);
    }

    @NonNull
    @Override
    public LinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line, parent, false));
    }

    public static class LinesViewHolder extends DefaultViewHolder<Line> {

        private TextView lineName;

        public LinesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            this.lineName = findViewById(R.id.item_line_name);
        }

        @Override
        public void bindItemToViewHolder(Line line) {
            this.lineName.setText(line.getName());
        }
    }
}
