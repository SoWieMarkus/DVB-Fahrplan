package markus.wieland.dvbfahrplan.api.models.pointfinder;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.dvbfahrplan.api.models.Status;

public class PointFinder {

    @SerializedName("Status")
    private Status status;

    @SerializedName("Points")
    private List<String> points;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public List<Point> getResult(){
        List<Point> pointsResult = new ArrayList<>();
        for (String point : points) {
            pointsResult.add(new Point(point));
        }
        return pointsResult;
    }
}
