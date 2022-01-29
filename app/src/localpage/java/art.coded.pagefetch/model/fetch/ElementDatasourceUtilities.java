package art.coded.pagefetch.model.fetch;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import art.coded.pagefetch.model.entity.Element;

/**
 * The actions required to fetch and format raw Elements
 */
public class ElementDatasourceUtilities {

    private static final String LOG_TAG = ElementDatasourceUtilities.class.getSimpleName();

    public static List<Element> fetchElement(URL url) {

        List<Element> elements = new ArrayList<>();

        // Retrieves and parse raw Element dataset from URL
        String response = requestResponseFromUrl(url);
        if (response == null) return elements;
        return parseElements(response);
    }

    public static String requestResponseFromUrl(URL url) {

        // Retrieves raw Element dataset
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader inRead = new InputStreamReader(in);
            Scanner scanner = new Scanner(inRead);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) response = scanner.next();
            scanner.close();
            Log.v(LOG_TAG, String.format("Fetched Response: %s", response));
        } catch (IOException ioEx) {
            Log.e(LOG_TAG, ioEx.getMessage());
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return response;
    }

    // Parses raw Element dataset
    public static List<Element> parseElements(@NonNull String jsonResponse) {

        List<Element> elements = null;

        try {

            JSONArray elementArray = new JSONArray(jsonResponse);
            elements = new ArrayList<>(elementArray.length());

            for (int i = 0; i < elementArray.length(); i++) {
                JSONObject elementObject = elementArray.getJSONObject(i);
                Element element = parseElement(elementObject);
                String name = element.getName();
                if (!name.isEmpty()) elements.add(element);
                Log.v(LOG_TAG,String.format("Parsed Response: %s", element.toString()));
            }
        } catch (JSONException jsonEx) {

            jsonEx.printStackTrace();
            Log.e(LOG_TAG, jsonEx.getMessage());

        } return elements;
    }

    // Parses single raw Element
    public static Element parseElement(JSONObject elementObject) throws JSONException {

        Integer id = nullToDefaultInt(elementObject.getInt(ElementDatasourceContract.KEY_ID));
        Integer listId = nullToDefaultInt(elementObject.getInt(ElementDatasourceContract.KEY_LISTID));
        String name = nullToDefaultStr(elementObject.getString(ElementDatasourceContract.KEY_NAME));

        Element element = new Element(id);
        element.setListId(listId);
        element.setName(name);
        return element;
    }

    // Translates built Uri into URL for opening an HttpURLConnection
    public static URL getUrl(Uri uri) {
        URL url = null;
        try {
            String urlStr = URLDecoder.decode(uri.toString(), "UTF-8");
            url = new URL(urlStr);
            Log.v(LOG_TAG, String.format("Fetch URL: %s", url.toString()));
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            Log.e(LOG_TAG, String.format("Unable to convert Uri of %s to URL:", e.getMessage()));
        }
        return url;
    }

    // Translates null to default String to prevent NPExceptions on accessing certain Elements
    public static String nullToDefaultStr(String str) {
        return (str.equals("null")) ? ElementDatasourceContract.DEFAULT_VALUE_STR : str;
    }

    // Translates null to default Integer to prevent NPExceptions on accessing certain Elements
    public static Integer nullToDefaultInt(Integer i) {
        return (i == null) ? ElementDatasourceContract.DEFAULT_VALUE_INT : i;
    }
}