package markus.wieland.dvbfahrplan.database.point;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseViewModel;
import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

public class PointViewModel extends BaseViewModel<Point, PointDataAccessObject, PointDataRepository> {

    public PointViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected PointDataRepository initRepository() {
        return new PointDataRepository(getApplication());
    }

    public void updatePoint(Point point) {
        point.setLatestUse(System.currentTimeMillis());
        UpdatePointTask task = new UpdatePointTask(point);
        task.execute();
    }

    public LiveData<List<Point>> getRecentPoints() {
        return repository.getRecentPoints();
    }

    private class UpdatePointTask extends AsyncTask<Void, Void, Void> {

        private final Point point;

        public UpdatePointTask(Point point) {
            this.point = point;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (repository.doesExist(point.getId())) update(point);
            else insert(point);
            return null;
        }
    }
}
