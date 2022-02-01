package art.coded.pagefetch.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import art.coded.pagefetch.model.entity.Element;

/**
 * A database for storing fetched Elements and retrieving stored Elements
 */
@Database(entities={Element.class}, version=1, exportSchema=false)
public abstract class ElementRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = ElementRoomDatabase.class.getSimpleName();

    public abstract ElementDao elementDao();
    private static ElementRoomDatabase INSTANCE;

    public static ElementRoomDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            synchronized (ElementRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ElementRoomDatabase.class, "element_database")
                            .build();
                }
            }
        } return INSTANCE;
    }
}