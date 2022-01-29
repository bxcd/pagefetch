package art.coded.pagefetch.viewmodel;

import static art.coded.pagefetch.model.fetch.ElementDatasourceUtilities.getUrl;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import java.net.URL;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.ElementRepository;
import art.coded.pagefetch.model.entity.Element;

/**
 * Interacts with Repository to sync datasource with database and provide Element LiveData to the UI
 */
public class ElementListViewModel extends ViewModel {

    private static final String LOG_TAG = ElementListViewModel.class.getSimpleName();

    // Member variables
    private ElementRepository mRepository;

    // From application argument instantiates Repository from which LiveData is retrieved from Dao
    public void loadData(Application application) {
        mRepository = new ElementRepository(application);

        String baseUrl = application.getString(R.string.base_url);
        String apiPath = application.getString(R.string.api_path);

        // Defines datasource URL
        Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
        builder.appendPath(apiPath);
        URL url = getUrl(builder.build());

        mRepository.fetchAllElements(url); // updates LiveData when fetched Elements are added to Dao
    }

    // Provides observable, pagable LiveData to list adapter
    public LiveData<PagedList<Element>> elementList(Integer pageSize) {
        return (mRepository.getPagedElements(pageSize));
    }
}