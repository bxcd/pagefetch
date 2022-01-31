package art.coded.pagefetch.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 * An Element object instance with the required tags for interfacing with Dao and Room
 */
public class Element {

    private static final String LOG_TAG = Element.class.getSimpleName();

    // Member variables
    @SerializedName("ein")
    @Expose private String mId;
    @SerializedName("currentRating")
    @Expose private ListId mListId;
    @SerializedName("charityName")
    @Expose private String mName;

    // Ctor
    public Element(@NonNull String id) { mId = id; }

    // Getters and Setters
    @NonNull public String getId() { return mId; }
    public void setId(@NonNull String id) { mId = id; }
    @NonNull public ListId getListId() { return mListId; }
    public void setListId(@NonNull ListId listId) { mListId = listId; }
    @NonNull public String getName() { return mName; }
    public void setName(@NonNull String name) { mName = name; }

    @NonNull @Override public String toString() {
        return String.format(
                Locale.getDefault(),
                "Ref: %s; Id: %d; ListId: %d; Name: %s",
                super.toString(), mId, mListId.getScore(), mName);
    }

    public static class ListId {

        private final String LOG_TAG = ListId.class.getSimpleName();

        // Member variable
        @SerializedName("rating")
        @Expose private Integer mRating;

        // Ctor
        public ListId(@NonNull Integer rating) { mRating = rating; }

        // Getters and Setters
        @NonNull public Integer getScore() { return mRating; }
        public void setScore(@NonNull Integer rating) { mRating = rating; }
    }
}
