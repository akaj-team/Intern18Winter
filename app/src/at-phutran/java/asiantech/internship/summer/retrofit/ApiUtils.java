package asiantech.internship.summer.retrofit;

public class ApiUtils {
    public static final String BASE_URL = "https://api.gyazo.com/api/";
    public static final String BASE_UPLOAD = "https://upload.gyazo.com/api/upload/";

    public static SOService getSOService() {
        return RetrofitClient.getClient().create(SOService.class);
    }
}
