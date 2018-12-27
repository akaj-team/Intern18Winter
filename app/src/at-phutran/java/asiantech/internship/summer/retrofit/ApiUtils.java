package asiantech.internship.summer.retrofit;

public class ApiUtils {
    public static final String BASE_URL = "https://api.gyazo.com/api/";

    public static SOService getSOService() {
        return RetrofitClient.getClient().create(SOService.class);
    }
}
