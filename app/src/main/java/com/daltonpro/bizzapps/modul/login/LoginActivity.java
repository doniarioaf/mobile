package com.daltonpro.bizzapps.modul.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.model.request.LoginRequest;
import com.daltonpro.bizzapps.model.response.login.LoginResponse;
import com.daltonpro.bizzapps.modul.home.HomeActivity;
import com.daltonpro.bizzapps.modul.login.presenter.LoginPresenter;
import com.daltonpro.bizzapps.modul.login.presenter.LoginPresenterContract;
import com.daltonpro.bizzapps.modul.login.validationlogin.CheckOnLoginInput;
import com.daltonpro.bizzapps.modul.login.validationlogin.CredentialsLoginInput;
import com.daltonpro.bizzapps.modul.login.validationlogin.LoginActionListener;
import com.daltonpro.bizzapps.modul.login.view.LoginViewContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.daltonpro.bizzapps.util.localstorage.Session;

public class LoginActivity extends BaseActivity implements LoginActionListener, LoginViewContract {

    private CheckBox rememeberMe;
    private EditText etUserName, etPassword;
    private Activity activity;
    private LoginPresenterContract loginPresenterContract;
    private ProgressBar progressBar;
    private RelativeLayout btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = LoginActivity.this;

        loginPresenterContract = new LoginPresenter(LoginActivity.this);

        rememeberMe = findViewById(R.id.cb_remember);
        etUserName = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressbar);


        btnLogin = findViewById(R.id.loginBTN);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CheckOnLoginInput checkOnLoginInput = new CheckOnLoginInput(LoginActivity.this);
                    checkOnLoginInput.doCheckLogin(new CredentialsLoginInput() {
                        @Override
                        public String getUsername() {
                            return etUserName.getText().toString().trim();
                        }

                        @Override
                        public String getPassword() {
                            return etPassword.getText().toString();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Session.beginInitialization(activity);
        checkRememberMe();

    }

    private void checkRememberMe() {
        if (NullEmptyChecker.isNotNullOrNotEmpty(Session.getSessionGlobal(Session.SESSION_USER_TOKEN))) {
            Intent intent = new Intent(activity, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }

        if (NullEmptyChecker.isNotNullOrNotEmpty(Session.getSessionGlobal(Session.SESSION_USER_REMEMBER_ME)) &&
                Session.getSessionGlobal(Session.SESSION_USER_REMEMBER_ME).equals("Y")) {

            rememeberMe.setChecked(true);
        } else {
            rememeberMe.setChecked(false);
        }

        if (rememeberMe.isChecked()) {
            Session.setSessionGlobal(Session.SESSION_USER_REMEMBER_ME, "Y");

            if (NullEmptyChecker.isNotNullOrNotEmpty(Session.getSessionGlobal(Session.SESSION_USERNAME))) {
                etUserName.setText(Session.getSessionGlobal(Session.SESSION_USERNAME));
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(Session.getSessionGlobal(Session.SESSION_USER_PASSWORD))) {
                etPassword.setText(Session.getSessionGlobal(Session.SESSION_USER_PASSWORD));
            }
        } else {
            Session.setSessionGlobal(Session.SESSION_USER_REMEMBER_ME, "N");

            Session.clearSessionGlobal(Session.SESSION_USERNAME);
            Session.clearSessionGlobal(Session.SESSION_USER_PASSWORD);
        }
    }

    @Override
    public void showUsernameCannotBeEmpty() {
        Toast.makeText(activity, "Username tidak boleh kosong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPasswordCannotBeEmpty() {
        Toast.makeText(activity, "Password tidak boleh kosong", Toast.LENGTH_LONG).show();

    }

    @Override
    public void doCheckCredential() {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser(etUserName.getText().toString().trim());
        loginRequest.setPassword(etPassword.getText().toString());

        loginPresenterContract.doLogin(loginRequest);
    }

    @Override
    public void onLoginResult(LoginResponse loginResponse) {
        progressBar.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
        Session.setSessionGlobal(Session.SESSION_USERNAME, loginResponse.getUser());
        Session.setSessionGlobal(Session.SESSION_USER_PASSWORD, loginResponse.getPasssword());
        Session.setSessionGlobal(Session.SESSION_USER_TOKEN, loginResponse.getData().getToken());

        if (rememeberMe.isChecked()) {
            Session.setSessionGlobal(Session.SESSION_USER_REMEMBER_ME, "Y");
        } else {
            Session.setSessionGlobal(Session.SESSION_USER_REMEMBER_ME, "N");
        }

        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onFailed(int httpCode, String message) {
        progressBar.setVisibility(View.GONE);
        btnLogin.setEnabled(true);
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

    }
}