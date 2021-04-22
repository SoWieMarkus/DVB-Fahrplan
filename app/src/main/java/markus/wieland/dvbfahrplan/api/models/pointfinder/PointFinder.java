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

    @SerializedName("PointStatus")
    private PointStatus pointStatus;

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
        if (points == null) return pointsResult;
        for (String point : points) {
            pointsResult.add(new Point(point));
        }
        return pointsResult;
    }

    public PointStatus getPointStatus() {
        return pointStatus;
    }

    public void setPointStatus(PointStatus pointStatus) {
        this.pointStatus = pointStatus;
    }
}
