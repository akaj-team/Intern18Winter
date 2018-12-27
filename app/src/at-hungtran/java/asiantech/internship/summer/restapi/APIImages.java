package asiantech.internship.summer.restapi;

import android.media.Image;

import java.util.List;

import asiantech.internship.summer.model.QueryImage;
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

    /**
     * @param token   access token of user ,required
     * @param page    page of images ,not required,default 1
     * @param perPage number of images per page, not required,default 10
     * @return list of images like this
     * <p>
     * [
     * {
     * "image_id": "8980c52421e452ac3355ca3e5cfe7a0c",
     * "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c",
     * "thumb_url": "https://i.gyazo.com/thumb/afaiefnaf.png",
     * "url": "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png",
     * "type": "png",
     * "created_at": "2014-05-21 14:23:10+0900"
     * },
     * {
     * "image_id": "8980c52421e452ac3355ca3e5cfe7a0c",
     * "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c",
     * "thumb_url": "https://i.gyazo.com/thumb/afaiefnaf.png",
     * "url": "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png",
     * "type": "png",
     * "created_at": "2014-05-21 14:23:10+0900"
     * },
     * ...
     * ]
     */
    @GET("images")
    Call<List<QueryImage>> getImages(@Query("access_token") String token,
                                     @Query("page") int page,
                                     @Query("per_page") int perPage);

    /**
     * @param url   url for upload
     * @param token access token of user ,required
     * @param image image which is uploaded ,required
     * @return image like this
     * <p>
     * {
     * "image_id" : "8980c52421e452ac3355ca3e5cfe7a0c",
     * "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c",
     * "thumb_url" : "https://i.gyazo.com/thumb/180/afaiefnaf.png",
     * "url" : "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png",
     * "type": "png"
     * }
     */
    @Multipart
    @POST
    Call<Image> uploadImage(@Url String url, @Part("access_token") RequestBody token, @Part MultipartBody.Part image);
}
