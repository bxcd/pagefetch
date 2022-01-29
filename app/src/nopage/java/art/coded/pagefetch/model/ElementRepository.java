package art.coded.pagefetch.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.net.URL;
import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import art.coded.pagefetch.model.local.ElementDao;
import art.coded.pagefetch.model.local.ElementRoomDatabase;
import art.coded.pagefetch.model.fetch.ElementDatasourceUtilities;

/**
 * Asynchronous interactions for the ViewModel to interact with Element data
 */
public class ElementRepository {

    private static final String LOG_TAG = ElementRepository.class.getSimpleName();

    // Member variables
    private ElementDao mElementDao;
    private LiveData<List<Element>> mAllElements;

    // Ctor
    public ElementRepository(Application application) {
        ElementRoomDatabase db = ElementRoomDatabase.getInstance(application);
        mElementDao = db.elementDao();
        mAllElements = mElementDao.getAll();
    }

    // Database interactions
    public LiveData<List<Element>> getAllElements() { return mAllElements; }
    public void fetchAllElements(URL url) { new FetchAsyncTask(mElementDao).execute(url); }

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