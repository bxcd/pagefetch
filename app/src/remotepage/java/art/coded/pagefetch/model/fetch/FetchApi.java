package art.coded.pagefetch.model.fetch;

import java.util.List;

import art.coded.pagefetch.model.entity.Element;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchApi {

    @GET("/v2/Organizations")
    Call<List<Element>> getPositionalElements(
            @Query("app_id") String appId,
            @Query("app_key") String appKey
    );

    @GET("/v2/Organizations")
    Call<List<Element>> getItemKeyedElements(
            @Query("app_id") String appId,
            @Query("app_key") String appKey,
            @Query("orgID") int orgId
    );

    @GET("/v2/Organizations")
    Call<List<Element>> getPageKeyedElements(
            @Query("app_id") String appId,
            @Query("app_key") String appKey,
            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize
    );
}