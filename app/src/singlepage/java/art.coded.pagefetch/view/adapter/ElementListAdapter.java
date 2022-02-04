package art.coded.pagefetch.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.entity.Element;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Manages and formats ViewHolders from the corresponding Elements data
 */
public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {

    private static final String LOG_TAG = ElementListAdapter.class.getSimpleName();

    // Member variables
    LayoutInflater mLayoutInflater;
    List<Element> mAllElements;

    // Instantiates a LayoutInflater provided from the calling Activity
    public ElementListAdapter(Activity activity) {

        mLayoutInflater = LayoutInflater.from(activity);
    }

    // Inflates and instantiates a ViewHolder from the LayoutInflater provided from the calling Activity
    @NonNull @Override  public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return new ElementViewHolder(itemView);
    }

    // Formats VieHolder on binding at the specified position
    @Override  public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {

        Element element = mAllElements.get(position);
        if (element != null) holder.bind(element);
    }

    // Identifies itemcount
    @Override  public int getItemCount() {
        return mAllElements == null ? 0 : mAllElements.size();
    }

    // Defines the Elements data that will populate ViewHolders
    public void setElements(List<Element> elements) {
        mAllElements = elements;
        notifyDataSetChanged();
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

            String id = element.getId();
            String ratingStr = String.valueOf(element.getListId().getRating());
            String name = element.getName();
            if (name.length() > 70)
                name = name
                        .substring(0, 70)
                        .substring(0, name.lastIndexOf(" "))
                        .concat("...");

            mIdView.setText(id);
            mListIdView.setText(ratingStr);
            mNameView.setText(name);
        }
    }
}