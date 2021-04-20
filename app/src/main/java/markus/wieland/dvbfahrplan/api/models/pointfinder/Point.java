package markus.wieland.dvbfahrplan.api.models.pointfinder;

public class Point {

    private final String id;
    private final String place;
    private final String name;

    private final long latitude;
    private final long longitude;

    public Point(String point) {
        String[] parts = point.split("\\|");
        this.id = parts[0];
        this.place = parts[2];
        this.name = parts[3];
        this.longitude = Long.parseLong(parts[4]);
        this.latitude = Long.parseLong(parts[5]);
    }

    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }
}
