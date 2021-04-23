package markus.wieland.dvbfahrplan.api.models.pointfinder;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import markus.wieland.databases.DatabaseEntity;
import markus.wieland.defaultappelements.uielements.adapter.QueryableEntity;

@Entity
public class Point implements QueryableEntity<String>, DatabaseEntity {

    @PrimaryKey @NonNull
    private String id;
    private String place;
    private String name;

    private long latitude;
    private long longitude;

    private long latestUse;

    public Point(){}

    public Point(String point) {
        String[] parts = point.split("\\|");
        this.id = parts[0];
        this.place = parts[2];
        this.name = parts[3];
        this.longitude = Long.parseLong(parts[4]);
        this.latitude = Long.parseLong(parts[5]);
    }

    public long getLatestUse() {
        return latestUse;
    }

    public void setLatestUse(long latestUse) {
        this.latestUse = latestUse;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getStringToApplyQuery() {
        return name + place;
    }

    public String getPlace() {
        if (place == null ||place.isEmpty()) return "Dresden";
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

    @Override
    public long getUniqueId() {
        return 0;
    }

    @Override
    public String toString() {
        return (name + " " +place).trim();
    }
}
