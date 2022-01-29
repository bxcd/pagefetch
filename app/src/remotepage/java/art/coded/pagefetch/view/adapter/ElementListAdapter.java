package art.coded.pagefetch.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.entity.Element;

/**
 * Manages and formats ViewHolders from the corresponding Elements data
 */
public class ElementListAdapter
        extends PagedListAdapter<Element, ElementListAdapter.ElementViewHolder> {

    // Member variables
    private final LayoutInflater mLayoutInflater;

    // Instantiates a LayoutInflater provided from the calling Activity and sets indexing policy
    public ElementListAdapter(
            @NonNull DiffUtil.ItemCallback<Element> diffCallback, Activity activity) {
        super(diffCallback);
        mLayoutInflater = LayoutInflater.from(activity);
    }

    // Inflates view holder
    @NonNull @Override public ElementViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ElementListAdapter.ElementViewHolder(itemView);
    }

    // Populates holder child views from Element at given adapter position
    @Override public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
            Element element = getItem(position);
            if (element != null) holder.bind(element);
    }

    /**
     * Formats an individual ViewHolder
     */
    public static class ElementViewHolder extends RecyclerView.ViewHolder {

        // Member variables
        private final TextView mIdView;
        private final TextView mListIdView;
        private final TextView mNameView;

        // Ctor inflates child views
        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            mIdView = itemView.findViewById(R.id.idView);
            mListIdView = itemView.findViewById(R.id.listIdView);
            mNameView = itemView.findViewById(R.id.nameView);
        }

        // Helper for populating child views from Element
        public void bind(Element element) {
            final String name = element.getName();
            final String idStr = String.format(Locale.getDefault(), "%d", element.getId());
            final String listIdStr = String.format(Locale.getDefault(), "%d", element.getListId());
            mIdView.setText(String.format("ID : %s", idStr));
            mListIdView.setText(String.format("List ID : %s", listIdStr));
            mNameView.setText(name);
        }
    }
}
