package com.daltonpro.bizzapps.modul.login.presenter;

import com.daltonpro.bizzapps.core.api.APIService;
import com.daltonpro.bizzapps.core.api.APIUtils;
import com.daltonpro.bizzapps.model.request.LoginRequest;
import com.daltonpro.bizzapps.model.response.ErrorResponse;
import com.daltonpro.bizzapps.model.response.login.LoginResponse;
import com.daltonpro.bizzapps.modul.login.view.LoginViewContract;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginPresenterContract {
    private final LoginViewContract loginViewContract;
    private final APIService apiService;

    public LoginPresenter(LoginViewContract loginViewContract) {
        this.loginViewContract = loginViewContract;
        apiService = APIUtils.getApiService();
    }


    @Override
    public void doLogin(LoginRequest loginRequest) {

        Gson gson = new Gson();

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code()==500){
                    try {
                        ErrorResponse errorResponse  = gson.fromJson(response.errorBody().string(),ErrorResponse.class);
                        loginViewContract.onFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        loginViewContract.onFailed(response.code(), e.getMessage());
                    }

                }else{
                    if (response.code() == 401 || response.code() == 400) {
                        try {
                            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            loginViewContract.onFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                        } catch (IOException e) {
                            e.printStackTrace();
                            loginViewContract.onFailed(response.code(), e.getMessage());
                        }
                    }else {
                        if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                            LoginResponse loginResponse = response.body();
                            loginResponse.setUser(loginRequest.getUser());
                            loginResponse.setPasssword(loginRequest.getPassword());
                            loginViewContract.onLoginResult(loginResponse);
                        } else {
                            loginViewContract.onFailed(response.body().getHttpcode(), response.body().getMessage());
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginViewContract.onFailed(500, t.getMessage());
            }
        });
    }
}
