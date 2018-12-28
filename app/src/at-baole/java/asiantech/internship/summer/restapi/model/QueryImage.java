package asiantech.internship.summer.restapi.model;

import com.google.gson.annotations.SerializedName;

public class QueryImage extends ImageItem {
    @SerializedName("created_at")
    public String createdAt;
}
