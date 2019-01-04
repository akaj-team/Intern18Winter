package asiantech.internship.summer.restapi;

import java.util.List;

import asiantech.internship.summer.restapi.model.ImageItem;
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
    String TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476";
    String LOAD_URL = "https://api.gyazo.com/api/";
    String UPLOAD_URL = "https://upload.gyazo.com/api/upload";

    @GET("images")
    Call<List<ImageItem>> getImages(@Query("access_token") String token,
                                    @Query("page") int page);
    @Multipart
    @POST
    Call<ImageItem> uploadImage(@Url String url, @Part("access_token") RequestBody token, @Part MultipartBody.Part image);
}
