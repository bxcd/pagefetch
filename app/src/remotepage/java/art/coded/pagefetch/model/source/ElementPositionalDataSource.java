package art.coded.pagefetch.model.source;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementPositionalDataSource extends PositionalDataSource<Element> {

    private static final String LOG_TAG = ElementPositionalDataSource.class.getSimpleName();

    private final FetchApi mApi;
    private final String mAppId;
    private final String mAppKey;

    public ElementPositionalDataSource(FetchApi api, String appId, String appKey) {
        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params,
                            @NonNull LoadInitialCallback<Element> callback) {

        final int current = 1;

        Call<List<Element>> call = mApi.getPositionalElements(mAppId, mAppKey);
        RequestBody requestBody = call.request().body();
        if (requestBody == null) return;
        Log.v(LOG_TAG, requestBody.toString());

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                callback.onResult(responseBody, current);
                Log.v(LOG_TAG, responseBody.toString());
            }

            @Override public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                String message = t.getMessage();
                if (message == null) return;
                Log.e(LOG_TAG, message);
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Element> callback) {

        Call<List<Element>> call = mApi.getPositionalElements(mAppId, mAppKey);
        RequestBody requestBody = call.request().body();
        if (requestBody == null) return;
        Log.v(LOG_TAG, requestBody.toString());

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                callback.onResult(responseBody);
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
