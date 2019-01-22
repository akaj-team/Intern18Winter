package asiantech.internship.summer.kotlin.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImageItem {
    @SerializedName("image_id")
    @Expose
    private var imageId: String? = null
    @SerializedName("permalink_url")
    @Expose
    private var permalinkUrl: String? = null
    @SerializedName("thumb_url")
    @Expose
    private var thumbUrl: String? = null
    @SerializedName("url")
    @Expose
    private var url: String? = null
    @SerializedName("type")
    @Expose
    private var type: String? = null

    fun getImageId(): String? {
        return imageId
    }

    fun setImageId(imageId: String) {
        this.imageId = imageId
    }

    fun getPermalinkUrl(): String? {
        return permalinkUrl
    }

    fun setPermalinkUrl(permalinkUrl: String) {
        this.permalinkUrl = permalinkUrl
    }

    fun getThumbUrl(): String? {
        return thumbUrl
    }

    fun setThumbUrl(thumbUrl: String) {
        this.thumbUrl = thumbUrl
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String) {
        this.url = url
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }
}