package asiantech.internship.summer.retrofit;

import java.util.List;
import asiantech.internship.summer.model.ImageItem;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SOService {
    @GET("images")
    Call<List<ImageItem>> getImages(@Query("access_token") String accessToken, @Query("page") int page, @Query("per_page") int perPage);

    @Multipart
    @POST
    Call<ImageItem> uploadImage(@Url String url, @Part("access_token") RequestBody token, @Part MultipartBody.Part image);
}
