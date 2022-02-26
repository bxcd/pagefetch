package art.coded.pagefetch.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import java.util.List;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.ElementRepository;
import art.coded.pagefetch.model.entity.Element;

/**
 * Interacts with Repository to sync datasource with database and provide Element LiveData to the UI
 */
public class ElementListViewModel extends ViewModel {

    private static final String LOG_TAG = ElementListViewModel.class.getSimpleName();

    // Member variables
    private Application mApplication;

    // From application argument instantiates Repository from which LiveData is retrieved from Dao
    public void loadData(Application application) {
        mApplication = application;
    }

    // Provides observable, pageable LiveData to list adapter
    public LiveData<List<Element>> elementList(Integer pageSize) {
        String baseUrl = mApplication.getString(R.string.base_url);
        String appId = mApplication.getString(R.string.app_id);
        String appKey  = mApplication.getString(R.string.app_key);
        ElementRepository repository = new ElementRepository(mApplication);
        repository.deleteAll();
        return (repository.getPagedElements(baseUrl, appId, appKey, pageSize));
    }
}