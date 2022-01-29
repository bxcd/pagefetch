package art.coded.pagefetch.model.source;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import art.coded.pagefetch.model.fetch.FetchApi;

public class ElementDataSourceFactory extends DataSource.Factory {

    FetchApi mApi;
    DatasourceType mType;
    String mAppId;
    String mAppKey;

    public ElementDataSourceFactory(
            FetchApi api,
            DatasourceType type,
            String appId,
            String appKey) {

        mApi = api;
        mType = type;
        mAppId = appId;
        mAppKey = appKey;
    }

    @NonNull @Override public DataSource create() {
        switch(mType) {
            case PAGE: return new ElementPageKeyedDataSource(mApi, mAppId, mAppKey);
            case ITEM: return new ElementItemKeyedDataSource(mApi, mAppId, mAppKey);
            default: return new ElementPositionalDataSource(mApi, mAppId, mAppKey);
        }
    }

    public enum DatasourceType { PAGE, ITEM, POSITION }
}
