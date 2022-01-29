package art.coded.pagefetch.model.source;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementPositionalDataSource extends PositionalDataSource<Element> {

    private static final String LOG_TAG = ElementPositionalDataSource.class.getSimpleName();

    FetchApi mApi;
    String mAppId;
    String mAppKey;

    public ElementPositionalDataSource(FetchApi api, String appId, String appKey) {

        mApi = api;
        mAppId = appId;
        mAppKey = appKey;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Element> callback) {

        final int current = 1;

        mApi.getPositionalElements(mAppId, mAppKey).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() == null) return;
                callback.onResult(response.body(), current);
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
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Element> callback) {

        mApi.getPositionalElements(mAppId, mAppKey).enqueue(new Callback<List<Element>>() {
            @Override
            public void onResponse(@NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                if (response.body() != null) callback.onResult(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Element>> call, @NonNull Throwable t) {
                if (t.getMessage() != null) Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}
