package art.coded.pagefetch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import art.coded.pagefetch.R;
import art.coded.pagefetch.model.ElementRepository;
import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.source.ElementDataSourceFactory;

/**
 * Interacts with Repository to sync datasource with database and provide Element LiveData to the UI
 */
public class ElementListViewModel extends ViewModel {

    private static final String LOG_TAG = ElementListViewModel.class.getSimpleName();

    // Member variables
    private ElementRepository mRepository;
    private Application mApplication;

    // From application argument instantiates Repository from which LiveData is retrieved from Dao
    public ElementListViewModel(ElementRepository repository) {
        mRepository = repository;
    }

    // Provides observable, pageable LiveData to list adapter
    public LiveData<PagedList<Element>> elementList(
            Integer pageSize, String baseUrl, String appId, String appKey) {
        return (mRepository.getPagedElements(pageSize, baseUrl, appId, appKey));
    }

    public void closeDatasource(ElementDataSourceFactory.DatasourceType type) {
        mRepository.closeDatasource(type);
    }
}