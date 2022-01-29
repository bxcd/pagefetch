package art.coded.pagefetch.model.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementItemKeyedDataSource extends ItemKeyedDataSource<Integer, Element> {

    private static final String LOG_TAG = ElementItemKeyedDataSource.class.getSimpleName();

    FetchApi mApi;
    String mAppId;
    String mAppKey;

    public ElementItemKeyedDataSource(FetchApi api, String appId, String appKey) {

        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @NonNull
    @Override
    public Integer getKey(@NonNull Element element) {
        return element.getId();
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Element> loadCallback) {

        final int current = loadParams.key;

        mApi.getItemKeyedElements(mAppId, mAppKey, current).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() == null) return;
                loadCallback.onResult(response.body());
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
    public void loadBefore(@NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Element> loadCallback) {

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Element> loadInitialCallback) {

        final int current = 1;

        mApi.getItemKeyedElements(mAppId, mAppKey, current).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() != null)
                    loadInitialCallback.onResult(response.body(), current, loadInitialParams.requestedLoadSize);
            }

            @Override
            public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                if (t.getMessage() != null) Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
