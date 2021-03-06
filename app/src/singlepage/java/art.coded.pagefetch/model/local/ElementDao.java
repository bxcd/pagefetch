package art.coded.pagefetch.model.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;

/**
 * Database interactions required to store fetched Elements and retrieve stored Elements
 */
@Dao public interface ElementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(Element element);

    @Query("SELECT * from element_table ORDER BY listId ASC, name")
    LiveData<List<Element>> getPaged();

    @Query("DELETE from element_table") void deleteAll();
}