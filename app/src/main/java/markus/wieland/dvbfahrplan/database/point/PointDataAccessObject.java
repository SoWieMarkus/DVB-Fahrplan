package markus.wieland.dvbfahrplan.database.point;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import markus.wieland.databases.BaseDataAccessObject;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

@Dao
public interface PointDataAccessObject extends BaseDataAccessObject<Point> {

    @Query("SELECT EXISTS(SELECT * FROM point WHERE id = :id)")
    boolean doesExist(String id);

    @Query("SELECT * FROM point ORDER BY latestUse DESC")
    LiveData<List<Point>> getRecentPoints();

}
