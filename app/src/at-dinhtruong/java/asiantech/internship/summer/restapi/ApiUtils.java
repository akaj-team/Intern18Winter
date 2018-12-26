package asiantech.internship.summer.restapi;

public class ApiUtils {

    public static final String BASE_URL = "https://api.gyazo.com/api/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
