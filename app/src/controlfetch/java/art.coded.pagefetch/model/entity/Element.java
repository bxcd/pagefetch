package art.coded.pagefetch.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * An Element object instance with the required tags for interfacing with Retrofit, Dao and Room
 */
@Entity(tableName="element_table")
public class Element {

    private static final String LOG_TAG = Element.class.getSimpleName();

    // Member variables
    @ColumnInfo(name = "id") @SerializedName("ein")
    @PrimaryKey @Expose private String mId;
    @ColumnInfo(name = "listId") @SerializedName("currentRating")
    @Expose private ListId mListId;
    @ColumnInfo(name="name") @SerializedName("charityName")
    @Expose private String mName;
//    @ColumnInfo(name="rating")
//    @Expose private Integer mRating;

    // Ctor
    public Element(@NonNull String id) { mId = id; }

    // Getters and Setters
    @NonNull public String getId() { return mId; }
    public void setId(@NonNull String id) { mId = id; }
    @NonNull public ListId getListId() { return mListId; }
    public void setListId(@NonNull ListId listId) { mListId = listId; /* mRating = listId.getRating(); */ }
    @NonNull public String getName() { return mName; }
    public void setName(@NonNull String name) { mName = name; }
//    @NonNull public Integer getRating() { return mRating; }
//    public void setRating(Integer mRating) { this.mRating = mRating; }

    @NonNull @Override public String toString() {
        return String.format(
                Locale.getDefault(),
                "Ref: %s; Id: %s; ListId: %d; Name: %s",
                super.toString(), mId, mListId.getRating(), mName);
    }

    public static class ListId {

        private final String LOG_TAG = ListId.class.getSimpleName();

        // Member variable
        @SerializedName("rating")
        @Expose private Integer mRating;

        // Ctor
        public ListId(@NonNull Integer rating) { mRating = rating; }

        // Getters and Setters
        @NonNull public Integer getRating() { return mRating; }
        public void setRating(@NonNull Integer rating) { mRating = rating; }
    }
}
