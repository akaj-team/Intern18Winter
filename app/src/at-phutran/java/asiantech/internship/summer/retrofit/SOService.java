package asiantech.internship.summer.retrofit;

import java.util.List;

import asiantech.internship.summer.model.ImageItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {
    @GET("images")
    Call<List<ImageItem>> getImages(@Query("access_token") String accessToken, @Query("page") int page, @Query("per_page") int perPage);
}
