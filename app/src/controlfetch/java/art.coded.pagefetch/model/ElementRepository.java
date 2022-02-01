package art.coded.pagefetch.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.FetchApi;
import art.coded.pagefetch.model.fetch.FetchClient;
import art.coded.pagefetch.model.local.ElementDao;
import art.coded.pagefetch.model.local.ElementRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Asynchronous interactions for the ViewModel to interact with Element data
 */
public class ElementRepository {

    private static final String LOG_TAG = ElementRepository.class.getSimpleName();

    // Member variables
    private final ElementDao mElementDao;

    // Ctor
    public ElementRepository(Application application) {
        ElementRoomDatabase db = ElementRoomDatabase.getInstance(application);
        mElementDao = db.elementDao();
    }

    public LiveData<PagedList<Element>> getPagedElements(
            String baseUrl, String appId, String appKey, Integer pageSize) {

        List<Element> elements = new ArrayList<>();

        FetchApi api = FetchClient.getInstance(baseUrl).create(FetchApi.class);
        Call<List<Element>> call = api.getPageKeyedElements(appId, appKey, true, 1, pageSize);

        call.enqueue(new Callback<List<Element>>() {
            @Override public void onResponse(
                    @NonNull Call<List<Element>> call, @NonNull Response<List<Element>> response) {
                List<Element> responseBody = response.body();
                if (responseBody == null) return;
                elements.addAll(responseBody);
                new PersistAsyncTask(mElementDao).execute(elements);
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

        return new LivePagedListBuilder<>(mElementDao.getPaged(), pageSize).build();
    }

    // Asynchronous handling of datasource interaction
    private static class PersistAsyncTask extends AsyncTask<List<Element>, Void, Void> {

        ElementDao mAsyncTaskDao;
        public PersistAsyncTask(ElementDao dao) { mAsyncTaskDao = dao; }
        @Override protected Void doInBackground(List<Element>... elements) {
            for (Element e : elements[0]) { mAsyncTaskDao.insert(e); }
            return null;
        }
    }
}