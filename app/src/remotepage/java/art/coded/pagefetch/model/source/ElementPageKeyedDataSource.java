package art.coded.pagefetch.model.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementPageKeyedDataSource extends PageKeyedDataSource<Integer, Element> {

    private static final String LOG_TAG = ElementPageKeyedDataSource.class.getSimpleName();
    FetchApi mApi;
    String mAppId;
    String mAppKey;

    public ElementPageKeyedDataSource(FetchApi api, String appId, String appKey) {
        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Element> loadCallback) {

        int current = loadParams.key;
        int pageSize = loadParams.requestedLoadSize;

        mApi.getPageKeyedElements(mAppId, mAppKey, pageSize, current).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() == null) return;
                loadCallback.onResult(response.body(), current);
                Log.v(LOG_TAG, response.body().toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (message == null) return;
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Integer, Element> loadCallback) {

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer, Element> loadInitialCallback) {

        int current = 1;
        int pageSize = loadInitialParams.requestedLoadSize;

        mApi.getPageKeyedElements(mAppId, mAppKey, pageSize, current).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() != null) loadInitialCallback.onResult(response.body(), null, current);
            }

            @Override
            public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                if (t.getMessage() != null) Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

}
