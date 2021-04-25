package markus.wieland.dvbfahrplan.ui.map;

import java.util.Optional;

import markus.wieland.dvbfahrplan.api.Mode;
import markus.wieland.dvbfahrplan.api.models.coordinates.GKCoordinate;
import markus.wieland.dvbfahrplan.api.models.coordinates.WGSCoordinate;
import markus.wieland.dvbfahrplan.api.models.routes.Stop;
import markus.wieland.dvbfahrplan.api.models.trip.Node;
import markus.wieland.dvbfahrplan.api.models.trip.Position;

public class TripNode {

    private String name;
    private Position position;
    private double x;
    private double y;
    private String mode;

    public TripNode(Node node) {
        this.name = node.getName();
        this.position = node.getPosition();

        Optional<WGSCoordinate> wgsCoordinate = new GKCoordinate((double) node.getLongitude(), (double) node.getLatitude()).asWGS();
        if (!wgsCoordinate.isPresent()) return;
        WGSCoordinate coordinate = wgsCoordinate.get();
        this.x = coordinate.getLatitude();
        this.y = coordinate.getLongitude();
    }

    public TripNode(Stop stop) {
        this.name = stop.getName();
        this.position = Position.NEXT;

        Optional<WGSCoordinate> wgsCoordinate = new GKCoordinate((double) stop.getLongitude(), (double) stop.getLatitude()).asWGS();
        if (!wgsCoordinate.isPresent()) return;
        WGSCoordinate coordinate = wgsCoordinate.get();
        this.x = coordinate.getLatitude();
        this.y = coordinate.getLongitude();
    }

    public String getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode.getMapId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
