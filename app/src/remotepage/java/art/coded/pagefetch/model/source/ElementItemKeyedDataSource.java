package art.coded.pagefetch.model.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementItemKeyedDataSource extends ItemKeyedDataSource<Integer, Element> {

    private static final String LOG_TAG = ElementItemKeyedDataSource.class.getSimpleName();

    private final FetchApi mApi;
    private final String mAppId;
    private final String mAppKey;

    public ElementItemKeyedDataSource(FetchApi api, String appId, String appKey) {
        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @NonNull @Override public Integer getKey(@NonNull Element element) { return element.getId(); }
    @Override public void loadBefore(
            @NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Element> loadCallback) {}

    @Override public void loadInitial(
            @NonNull LoadInitialParams<Integer> loadInitialParams,
            @NonNull LoadInitialCallback<Element> loadInitialCallback) {

        final int current = 1;

        Call<List<Element>> call = mApi.getItemKeyedElements(mAppId, mAppKey, current);

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                loadInitialCallback.onResult(responseBody, current, loadInitialParams.requestedLoadSize);
                Log.v(LOG_TAG, responseBody.toString());
            }

            @Override public void onFailure(
                    @NonNull Call<List<Element>> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (message == null) return;
                Log.e(LOG_TAG, message);
            }
        });
    }

    @Override public void loadAfter(
            @NonNull LoadParams<Integer> loadParams, @NonNull LoadCallback<Element> loadCallback) {

        final int current = loadParams.key;

        Call<List<Element>> call = mApi.getItemKeyedElements(mAppId, mAppKey, current);
        RequestBody requestBody = call.request().body();
        if (requestBody == null) return;
        Log.v(LOG_TAG, requestBody.toString());

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                loadCallback.onResult(responseBody);
                Log.v(LOG_TAG, responseBody.toString());
            }

            @Override public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (message == null) return;
                Log.e(LOG_TAG, message);
            }
        });
    }
}
