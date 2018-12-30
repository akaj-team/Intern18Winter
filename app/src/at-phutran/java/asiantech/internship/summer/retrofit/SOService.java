package asiantech.internship.summer.retrofit;

import java.util.List;

import asiantech.internship.summer.model.ImageItem;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface SOService {
    @GET("images")
    Call<List<ImageItem>> getImages(@Query("access_token") String accessToken, @Query("page") int page, @Query("per_page") int perPage);

    @Multipart
    @POST("upload")
    Call<ImageItem> uploadImage(@Query("access_token") String accessToken, @Part MultipartBody.Part image);
}
