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
        UpdatePointTask task = new UpdatePointTask(point, repository, this);
        task.execute();
    }

    public LiveData<List<Point>> getRecentPoints() {
        return repository.getRecentPoints();
    }

    private static class UpdatePointTask extends AsyncTask<Void, Void, Void> {

        private final Point point;
        private final PointDataRepository repository;
        private final PointViewModel pointViewModel;

        public UpdatePointTask(Point point, PointDataRepository repository, PointViewModel pointViewModel) {
            this.point = point;
            this.repository = repository;
            this.pointViewModel = pointViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (repository.doesExist(point.getId())) pointViewModel.update(point);
            else pointViewModel.insert(point);
            return null;
        }
    }
}
