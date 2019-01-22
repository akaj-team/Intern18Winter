package asiantech.internship.summer.kotlin.restapi

import asiantech.internship.summer.kotlin.model.ImageItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIImages {

    @GET("images")
    fun getImages(@Query("access_token") token: String,
                           @Query("page") page: Int): Call<List<ImageItem>>

    @Multipart
    @POST
    fun uploadImage(@Url url: String, @Part("access_token") token: RequestBody, @Part image: MultipartBody.Part): Call<ImageItem>

    companion object {
        const val TOKEN = "604d1f2a63e1620f8e496970f675f0322671a3de0ba9f44c850e9ddc193f4476"
        const val BASE_URL = "https://api.gyazo.com/api/"
        const val UPLOAD_URL = "https://upload.gyazo.com/api/upload"
    }

}