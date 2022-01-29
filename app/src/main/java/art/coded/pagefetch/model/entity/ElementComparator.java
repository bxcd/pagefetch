package art.coded.pagefetch.model.entity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Determines if two list items are equivalent or have equivalent content used in ListAdapter indexing
 */
public class ElementComparator extends DiffUtil.ItemCallback<Element> {

    private static final String LOG_TAG = ElementComparator.class.getSimpleName();

    @Override public boolean areItemsTheSame(@NonNull Element oldItem, @NonNull Element newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override public boolean areContentsTheSame(@NonNull Element oldItem, @NonNull Element newItem) {
        return oldItem.getListId().equals(newItem.getListId())
                && oldItem.getName().equals(newItem.getName());
    }
}
