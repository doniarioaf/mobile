package com.daltonpro.bizzapps.modul.login.validationlogin;

public class CheckOnLoginInput implements CheckLogin{

    private LoginActionListener loginActionListener;
    public CheckOnLoginInput(LoginActionListener loginActionListener){
        this.loginActionListener = loginActionListener;
    }
    @Override
    public void doCheckLogin(CredentialsLoginInput credentialsLoginInput) {

        if (credentialsLoginInput.getUsername().equals("")) {
            this.loginActionListener.showUsernameCannotBeEmpty();

        } else if (credentialsLoginInput.getPassword().equals("")) {
            this.loginActionListener.showPasswordCannotBeEmpty();

        } else {
            //kirim ke api
            this.loginActionListener.doCheckCredential();
        }
    }
}
