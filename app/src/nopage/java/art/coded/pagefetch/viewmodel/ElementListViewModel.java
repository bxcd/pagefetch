package art.coded.pagefetch.viewmodel;

import static art.coded.pagefetch.model.fetch.ElementDatasourceUtilities.getUrl;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.ElementRepository;

import java.net.URL;
import java.util.List;

/**
 * Interacts with Repository to sync datasource with database and provide Element LiveData to the UI
 */
public class ElementListViewModel extends ViewModel {

    private static final String LOG_TAG = ElementListViewModel.class.getSimpleName();

    // Member variables
    private LiveData<List<Element>> mData;

    // From application argument instantiates Repository from which LiveData is retrieved from Dao
    public void loadData(Application application) {
        ElementRepository repository = new ElementRepository(application);
        mData = repository.getAllElements();

        String baseUrl = application.getString(R.string.base_url);
        String apiPath = application.getString(R.string.api_path);

        // Defines datasource URL
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        builder.appendPath(apiPath);
        URL url = getUrl(builder.build());

        repository.fetchAllElements(url); // updates LiveData when fetched Elements are added to Dao
    }

    // Provides observable LiveData to list adapter
    public LiveData<List<Element>> getData() {
        return mData;
    }
}