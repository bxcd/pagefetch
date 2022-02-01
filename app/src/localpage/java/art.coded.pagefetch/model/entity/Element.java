package art.coded.pagefetch.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Locale;

/**
 * An Element object instance with the required tags for interfacing with Dao and Room
 */
@Entity(tableName="element_table")
public class Element {

    private static final String LOG_TAG = Element.class.getSimpleName();

    // Member variables
    @PrimaryKey @NonNull @ColumnInfo(name="orgID") private Integer mId;
    @ColumnInfo(name="minRating") private Integer mListId;
    @ColumnInfo(name="charityName") private String mName;

    // Ctor
    public Element(@NonNull Integer id) { mId = id; }

    // Getters and Setters
    @NonNull public Integer getId() { return mId; }
    public void setId(@NonNull Integer id) { mId = id; }
    @NonNull public Integer getListId() { return mListId; }
    public void setListId(@NonNull Integer listId) { mListId = listId; }
    @NonNull public String getName() { return mName; }
    public void setName(@NonNull String name) { mName = name; }

    @NonNull @Override public String toString() {
        return String.format(
                Locale.getDefault(),
                "Ref: %s; Id: %d; ListId: %d; Name: %s",
                super.toString(), mId, mListId, mName);
    }
}
