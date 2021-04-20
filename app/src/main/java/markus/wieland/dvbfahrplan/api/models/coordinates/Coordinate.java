package markus.wieland.dvbfahrplan.api.models.coordinates;

import androidx.annotation.NonNull;

import java.util.Optional;

public abstract class Coordinate {

    abstract public Optional<GKCoordinate> asGK();

    abstract public Optional<WGSCoordinate> asWGS();

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
