package asiantech.internship.summer.restapi;

import java.util.List;

import asiantech.internship.summer.models.Image;
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

    String TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    String BASE_URL = "https://api.gyazo.com/api/";
    String UPLOAD_URL = "https://upload.gyazo.com/api/upload";

    @GET("images")
    Call<List<Image>> getImages(@Query("access_token") String accessToken, @Query("page") int page, @Query("per_page") int perPage);

    @Multipart
    @POST
    Call<Image> uploadImage(@Url String url, @Part("access_token") RequestBody token, @Part MultipartBody.Part image);
}
