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
    @SerializedName("orgID")
    @Expose private Integer mId;
    @SerializedName("currentRating")
    @Expose private ListId mListId;
    @SerializedName("charityName")
    @Expose private String mName;

    // Ctor
    public Element(@NonNull Integer id) { mId = id; }

    // Getters and Setters
    @NonNull public Integer getId() { return mId; }
    public void setId(@NonNull Integer id) { mId = id; }
    @NonNull public ListId getListId() { return mListId; }
    public void setListId(@NonNull ListId listId) { mListId = listId; }
    @NonNull public String getName() { return mName; }
    public void setName(@NonNull String name) { mName = name; }

    @NonNull @Override public String toString() {
        return String.format(
                Locale.getDefault(),
                "Ref: %s; Id: %d; ListId: %f; Name: %s",
                super.toString(), mId, mListId.getScore(), mName);
    }

    public static class ListId {

        private final String LOG_TAG = ListId.class.getSimpleName();

        // Member variable
        @SerializedName("score")
        @Expose private Float mScore;

        // Ctor
        public ListId(@NonNull Float score) { mScore = score; }

        // Getters and Setters
        @NonNull public Float getScore() { return mScore; }
        public void setScore(@NonNull Float score) { mScore = score; }
    }
}
