package markus.wieland.dvbfahrplan.database.point;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseRepository;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;
import markus.wieland.dvbfahrplan.database.Database;

public class PointDataRepository extends BaseRepository<Point, PointDataAccessObject> {

    public PointDataRepository(@NonNull Application application) {
        super(application);
    }

    @Override
    public PointDataAccessObject initDataAccessObject(@NonNull Application application) {
        return Database.getInstance(application).getPointDataAccessObject();
    }

    public boolean doesExist(String id) {
        return dataAccessObject.doesExist(id);
    }

    public LiveData<List<Point>> getRecentPoints() {
        return dataAccessObject.getRecentPoints();
    }

}
