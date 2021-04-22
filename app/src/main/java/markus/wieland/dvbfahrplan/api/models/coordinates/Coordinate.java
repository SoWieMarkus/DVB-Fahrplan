package markus.wieland.dvbfahrplan.api.models.coordinates;

import androidx.annotation.NonNull;

import java.util.Optional;

public abstract class Coordinate {

    public abstract Optional<GKCoordinate> asGK();

    public abstract Optional<WGSCoordinate> asWGS();

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
