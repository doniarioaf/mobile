package com.daltonpro.bizzapps.modul.login.presenter;

import com.daltonpro.bizzapps.model.request.LoginRequest;

public interface LoginPresenterContract {
    void doLogin(LoginRequest loginRequest);
}
