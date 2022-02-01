package art.coded.pagefetch.model.source;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;

public class ElementDataSourceFactory extends DataSource.Factory<Integer, Element> {

    private final FetchApi mApi;
    private final DatasourceType mType;
    private final String mAppId;
    private final String mAppKey;

    public ElementDataSourceFactory(
            FetchApi api,
            DatasourceType type,
            final String appId,
            final String appKey) {

        mApi = api;
        mType = type;
        mAppId = appId;
        mAppKey = appKey;
    }

    @NonNull @Override public DataSource<Integer, Element> create() {
        switch(mType) {
            case PAGE: return new ElementPageKeyedDataSource(mApi, mAppId, mAppKey);
            case ITEM: return new ElementItemKeyedDataSource(mApi, mAppId, mAppKey);
            default: return new ElementPositionalDataSource(mApi, mAppId, mAppKey);
        }
    }

    public enum DatasourceType { PAGE, ITEM, POSITION }
}
