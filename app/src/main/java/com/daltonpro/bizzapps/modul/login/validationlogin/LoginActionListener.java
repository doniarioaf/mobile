package com.daltonpro.bizzapps.modul.login.validationlogin;

public interface LoginActionListener {
    void showUsernameCannotBeEmpty();
    void showPasswordCannotBeEmpty();
    void doCheckCredential();
}
