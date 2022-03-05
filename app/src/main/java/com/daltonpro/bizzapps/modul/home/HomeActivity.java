package com.daltonpro.bizzapps.modul.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.modul.data.DataActivity;
import com.daltonpro.bizzapps.modul.login.LoginActivity;
import com.daltonpro.bizzapps.modul.monitoring.callplan.MonitoringActivity;
import com.daltonpro.bizzapps.util.localstorage.Session;

public class HomeActivity extends BaseActivity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = HomeActivity.this;

        ImageButton ibMonitoring = findViewById(R.id.ibtnMonitoring);
        ibMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MonitoringActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        LinearLayout llLogout = findViewById(R.id.ll_logout);
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

                                Intent intent = new Intent(activity, LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finishAffinity();

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("").setMessage("Apakah anda ingin keluar dari aplikasi ?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();

            }
        });

        ImageButton ibData = findViewById(R.id.ibtnData);
        ibData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DataActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}