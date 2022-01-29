package art.coded.pagefetch.model;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.source.ElementDataSourceFactory;
import art.coded.pagefetch.model.fetch.FetchApi;
import art.coded.pagefetch.model.fetch.FetchClient;

/**
 * Asynchronous interactions for the ViewModel to interact with Element data
 */
public class ElementRepository {

    private final ElementDataSourceFactory.DatasourceType mType;

    public ElementRepository(ElementDataSourceFactory.DatasourceType type) {
        mType = type;
    }

    // Database interactions
    public LiveData<PagedList<Element>> getPagedElements(
            Integer pageSize, String baseUrl, String appId, String appKey) {
        FetchApi api = FetchClient.getInstance(baseUrl).create(FetchApi.class);
        return new LivePagedListBuilder<Integer, Element>(
                new ElementDataSourceFactory(api, mType, appId, appKey), pageSize).build();
    }

    public void closeDatasource(ElementDataSourceFactory.DatasourceType type) {

    }
}