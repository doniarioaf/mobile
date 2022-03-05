package com.daltonpro.bizzapps.core.api;

public class APIUtils {

    public static final String BASE_URL = "http://147.139.139.25:80";

    public static APIService getApiService() {
        return APIClient.getClient(BASE_URL + "/sinarmediasakti/v1/").create(APIService.class);
    }


}
