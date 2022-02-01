package art.coded.pagefetch.model.entity;

import androidx.room.TypeConverter;

public class ElementConverters {

    @TypeConverter public Integer listIdToRating(Element.ListId listId) { return listId.getRating(); }
    @TypeConverter public Element.ListId ratingToListId(Integer rating) { return new Element.ListId(rating); }
}
