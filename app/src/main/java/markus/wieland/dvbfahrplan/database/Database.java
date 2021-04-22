package markus.wieland.dvbfahrplan.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import markus.wieland.dvbfahrplan.api.models.pointfinder.Point;

@androidx.room.Database(entities = Point.class, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "school_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
