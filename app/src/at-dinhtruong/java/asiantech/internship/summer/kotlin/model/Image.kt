package asiantech.internship.summer.kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Image {

    @SerializedName("image_id")
    @Expose
    var imageId: String? = ""

    @SerializedName("permalink_url")
    @Expose
    var permalinkUrl: String? = ""

    @SerializedName("url")
    @Expose
    var url: String? = ""

    @SerializedName("type")
    @Expose
    var type: String? = ""

    @SerializedName("thumb_url")
    @Expose
    var thumbUrl: String? = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = ""
}
