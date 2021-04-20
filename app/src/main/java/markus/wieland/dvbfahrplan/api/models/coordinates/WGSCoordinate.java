package markus.wieland.dvbfahrplan.api.models.coordinates;

import java.util.Optional;

public class WGSCoordinate extends Coordinate {

    private Double latitude;
    private Double longitude;

    public WGSCoordinate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public Optional<GKCoordinate> asGK() {
        return GaussKrueger.wgs2gk(this);
    }

    @Override
    public Optional<WGSCoordinate> asWGS() {
        return Optional.of(this);
    }

}

