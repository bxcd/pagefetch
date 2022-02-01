package art.coded.pagefetch.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.net.URL;
import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.fetch.ElementDatasourceUtilities;
import art.coded.pagefetch.model.local.ElementDao;
import art.coded.pagefetch.model.local.ElementRoomDatabase;

/**
 * Asynchronous interactions for the ViewModel to interact with Element data
 */
public class ElementRepository {

    // Member variables
    private final ElementDao mElementDao;

    // Ctor
    public ElementRepository(Application application) {
        ElementRoomDatabase db = ElementRoomDatabase.getInstance(application);
        mElementDao = db.elementDao();
    }

    // Database interactions
    public void fetchAllElements(URL url) { new FetchAsyncTask(mElementDao).execute(url); }
    public LiveData<PagedList<Element>> getPagedElements(Integer pageSize) {
        return new LivePagedListBuilder<>(mElementDao.getPaged(), pageSize).build();
    }

    // Asynchronous handling of datasource interaction
    private static class FetchAsyncTask extends AsyncTask<URL, Void, Void> {

        ElementDao mAsyncTaskDao;
        public FetchAsyncTask(ElementDao dao) { mAsyncTaskDao = dao; }
        @Override protected Void doInBackground(URL... urls) {
            List<Element> elements = ElementDatasourceUtilities.fetchElement(urls[0]);
            for (Element e : elements) {
                mAsyncTaskDao.insert(e);
            }
            return null;
        }
    }
}