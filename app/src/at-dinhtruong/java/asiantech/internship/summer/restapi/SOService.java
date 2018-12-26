package asiantech.internship.summer.restapi;

import java.util.List;

import asiantech.internship.summer.models.Image;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SOService {

    @GET("/images")
    Call<List<Image>> getAnswers(@Query("tagged") String tags);

//    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
//    Call<List<SOAnswersResponse>> getAnswers(@Query("tagged") String tags);
//
//    @GET("/answers/{id}/{id2}")
//    Call<List<SOAnswersResponse>> getAnswers(@Query("tagged") String tags,
//                                             @Query("order") String order,
//                                             @Path("id") String id,
//                                             @Path("id") String id2);
//    // query không cần truyền "id " trên đường dẫn, chỉ truyền khi dùng Path
}
