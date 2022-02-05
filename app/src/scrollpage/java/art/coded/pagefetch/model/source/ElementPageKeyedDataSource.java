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
    private final FetchApi mApi;
    private final String mAppId;
    private final String mAppKey;

    public ElementPageKeyedDataSource(FetchApi api, String appId, String appKey) {
        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @Override public void loadBefore(
            @NonNull LoadParams<Integer> loadParams,
            @NonNull LoadCallback<Integer, Element> loadCallback) {}

    @Override public void loadInitial(
            @NonNull LoadInitialParams<Integer> loadInitialParams,
            @NonNull LoadInitialCallback<Integer, Element> loadInitialCallback) {

        final int pageSize = loadInitialParams.requestedLoadSize / 3;

        Call<List<Element>> call = mApi.getPageKeyedElements(mAppId, mAppKey, true, 1, pageSize);

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                loadInitialCallback.onResult(responseBody, null, pageSize);
                Log.v(LOG_TAG, String.format(
                        "Call generated callback response size of %d with contents of %s",
                        responseBody.size(),
                        responseBody.toString()
                ));
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
            @NonNull LoadParams<Integer> loadParams,
            @NonNull LoadCallback<Integer, Element> loadCallback) {

        final int current = loadParams.key;
        final int pageSize = loadParams.requestedLoadSize;

        Call<List<Element>> call = mApi.getPageKeyedElements(mAppId, mAppKey, true, current, pageSize);

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                loadCallback.onResult(responseBody, loadParams.key + 1);
                Log.v(LOG_TAG, String.format(
                        "Call generated callback response size of %d with contents of %s",
                        responseBody.size(),
                        responseBody.toString()
                ));
            }

            @Override public void onFailure(
                    @NonNull Call<List<Element>> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (message == null) return;
                Log.e(LOG_TAG, message);
            }
        });
    }
}
