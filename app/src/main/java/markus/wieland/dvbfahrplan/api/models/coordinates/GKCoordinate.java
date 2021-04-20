package markus.wieland.dvbfahrplan.api.models.coordinates;


import java.util.Optional;

public class GKCoordinate extends Coordinate {

    private Double x;
    private Double y;

    public GKCoordinate(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public Optional<GKCoordinate> asGK() {
        return Optional.of(this);
    }

    @Override
    public Optional<WGSCoordinate> asWGS() {
        return GaussKrueger.gk2wgs(this);
    }

}
