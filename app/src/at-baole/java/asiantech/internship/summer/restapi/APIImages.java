package asiantech.internship.summer.restapi;

import android.media.Image;

import java.util.List;

import asiantech.internship.summer.restapi.model.QueryImage;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIImages {
    String TOKEN = "6f5a48ac0e8aca77e0e8ef42e88962852b6ffaba01c16c5ba37ea13760c0317e";
    String BASE_URL = "https://api.gyazo.com/api/";
    String UPLOAD_URL = "https://upload.gyazo.com/api/upload";
    int PER_PAGE = 10;

    @GET("images")
    Call<List<QueryImage>> getImages(@Query("access_token") String token,
                                     @Query("page") int page,
                                     @Query("per_page") int perPage);
    @Multipart
    @POST
    Call<Image> uploadImage(@Url String url, @Part("access_token") RequestBody token, @Part MultipartBody.Part image);
}
