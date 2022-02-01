package art.coded.pagefetch.model.fetch;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchClient {

    private static Retrofit client;

    public static Retrofit getInstance(String baseUrl) {

        if (client == null) {
            client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        } return client;
    }
}