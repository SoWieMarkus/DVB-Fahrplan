package markus.wieland.dvbfahrplan.ui.map;

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

    public TripNode(Node node) {
        this.name = node.getName();
        this.position = node.getPosition();
        WGSCoordinate coordinate = new GKCoordinate((double) node.getLongitude(), (double) node.getLatitude()).asWGS().get();
        this.x = coordinate.getLatitude();
        this.y = coordinate.getLongitude();
    }

    public TripNode(Stop stop) {
        this.name = stop.getName();
        this.position = Position.NEXT;
        WGSCoordinate coordinate = new GKCoordinate((double) stop.getLongitude(), (double) stop.getLatitude()).asWGS().get();
        this.x = coordinate.getLatitude();
        this.y = coordinate.getLongitude();
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
