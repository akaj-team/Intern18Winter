package asiantech.internship.summer.restapi;

class ApiUtils {

    private static final String BASE_URL = "https://api.gyazo.com/api/";

    static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}
