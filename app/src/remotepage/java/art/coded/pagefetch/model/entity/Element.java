package art.coded.pagefetch.model.entity;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * An Element object instance with the required tags for interfacing with Dao and Room
 */
public class Element {

    private static final String LOG_TAG = Element.class.getSimpleName();

    // Member variables
    @SerializedName("orgID")
    @Expose private Integer mId;
    @SerializedName("minRating")
    @Expose private Integer mListId;
    @SerializedName("charityName")
    @Expose private String mName;

    // Ctor
    public Element(@NonNull Integer id) { mId = id; }

    // Getters and settings
    @NonNull public Integer getId() { return mId; }
    public void setId(@NonNull Integer id) { mId = id; }
    @NonNull public Integer getListId() { return mListId; }
    public void setListId(@NonNull Integer listId) { mListId = listId; }
    @NonNull public String getName() { return mName; }
    public void setName(@NonNull String name) { mName = name; }
}
