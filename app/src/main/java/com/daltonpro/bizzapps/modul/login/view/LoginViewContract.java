package com.daltonpro.bizzapps.modul.login.view;

import com.daltonpro.bizzapps.model.response.login.LoginResponse;

public interface LoginViewContract {
    void onLoginResult(LoginResponse loginResponse);

    void onFailed(int httpCode,String message);
}
