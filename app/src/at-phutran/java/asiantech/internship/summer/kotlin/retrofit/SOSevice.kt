package asiantech.internship.summer.kotlin.retrofit

import asiantech.internship.summer.model.ImageItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface SOSevice {
    @GET("images")
    fun getImages(@Query("access_token") accessToken: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Call<List<Image>>

    @Multipart
    @POST
    fun uploadImage(@Url url: String, @Part("access_token") token: RequestBody, @Part image: MultipartBody.Part): Call<Image>
}
