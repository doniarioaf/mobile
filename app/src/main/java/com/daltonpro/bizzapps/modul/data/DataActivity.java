package com.daltonpro.bizzapps.modul.data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.core.ExceptionHandler;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.request.DownloadRequest;
import com.daltonpro.bizzapps.model.request.upload.MonitorDetailPhotoUpload;
import com.daltonpro.bizzapps.model.request.upload.MonitorHeaderRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;
import com.daltonpro.bizzapps.model.response.download.callplan.DownloadResponseCallplan;
import com.daltonpro.bizzapps.model.response.download.customer.DownloadResponseCustomer;
import com.daltonpro.bizzapps.model.response.download.info.DownloadResponse;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;
import com.daltonpro.bizzapps.modul.data.presenter.DataPresenter;
import com.daltonpro.bizzapps.modul.data.presenter.DataPresenterContract;
import com.daltonpro.bizzapps.modul.data.view.DataViewContract;
import com.daltonpro.bizzapps.modul.login.LoginActivity;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.daltonpro.bizzapps.util.localstorage.Session;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DataActivity extends BaseActivity implements DataViewContract {
    private DataPresenterContract dataPresenterContract;
    private ConstraintLayout clProgress;
    private TextView tvTitleProgressBar;
    private Activity activity;
    private RelativeLayout rlBtnDownload, rlBtnUpload, rlBtnReset, rlBtnReport;
    private AlertDialog.Builder dialog;
    private DaltonDatabase daltonDatabase;
    private ImageView imgAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        activity = DataActivity.this;

        Session.beginInitialization(activity);
        daltonDatabase = DaltonDatabase.getInstance(activity);

        dataPresenterContract = new DataPresenter(DataActivity.this, activity);
        clProgress = findViewById(R.id.clProgress);

        imgAnim = (ImageView) findViewById(R.id.img_anim);

        Glide.with(this)
                .load(R.drawable.animation_loading)
                .placeholder(R.drawable.animation_loading)
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(imgAnim);

        tvTitleProgressBar = findViewById(R.id.tv_title_progressbar);

        rlBtnUpload = findViewById(R.id.uploadBTN);
        rlBtnReset = findViewById(R.id.resetBTN);
        rlBtnReport = findViewById(R.id.reportBTN);
        rlBtnDownload = findViewById(R.id.downloadBTN);

        rlBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlBtnDownload.setEnabled(false);
                rlBtnUpload.setEnabled(false);
                rlBtnReset.setEnabled(false);
                rlBtnReport.setEnabled(false);


                clProgress.setVisibility(View.VISIBLE);

                tvTitleProgressBar.setText("Downloading Info Header & Detail...");


                DownloadRequest downloadRequest = new DownloadRequest();
                downloadRequest.setToken(Session.getSessionGlobal(Session.SESSION_USER_TOKEN));
                dataPresenterContract.doDownload(downloadRequest);
            }
        });


        rlBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlBtnDownload.setEnabled(false);
                rlBtnUpload.setEnabled(false);
                rlBtnReset.setEnabled(false);
                rlBtnReport.setEnabled(false);

                clProgress.setVisibility(View.VISIBLE);
                tvTitleProgressBar.setText("Uploading Data...");


                UploadRequest uploadRequest = new UploadRequest();
                List<MonitorHeaderRequest> monitorHeaderList = daltonDatabase.monitorHeaderDao().getAllListReq();
                for (int i = 0; i < monitorHeaderList.size(); i++) {
                    monitorHeaderList.get(i).setMonitorDetailList(daltonDatabase.monitorDetailDao().getAllListByMonitorHeaderID(monitorHeaderList.get(i).getId()));
                }
                uploadRequest.setMonitorusermobiles(monitorHeaderList);
                if (monitorHeaderList.size() > 0) {
                    dataPresenterContract.doUpload(Session.getSessionGlobal(Session.SESSION_USER_TOKEN), uploadRequest);
                } else {
                    clProgress.setVisibility(View.GONE);
                    rlBtnDownload.setEnabled(true);
                    rlBtnUpload.setEnabled(true);
                    rlBtnReset.setEnabled(true);
                    rlBtnReport.setEnabled(true);
                    Toast.makeText(activity, "Tidak ada data untuk diupload", Toast.LENGTH_SHORT).show();
                }


            }
        });

        rlBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(activity);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.popup_reset_data, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);


                final EditText et_confirm = (EditText) dialogView.findViewById(R.id.et_confirm);
                dialog.setNegativeButton("KEMBALI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("LANJUT", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (et_confirm.getText().toString().equals("KONFIRMASI")) {
                            rlBtnDownload.setEnabled(false);
                            rlBtnUpload.setEnabled(false);
                            rlBtnReset.setEnabled(false);
                            rlBtnReport.setEnabled(false);

                            dataPresenterContract.doResetData();
                        } else {
                            clProgress.setVisibility(View.GONE);
                            rlBtnDownload.setEnabled(true);
                            rlBtnUpload.setEnabled(true);
                            rlBtnReset.setEnabled(true);
                            rlBtnReport.setEnabled(true);
                            Toast.makeText(activity, "Inputan KONFIRMASI tidak sesuai", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();

            }
        });

        rlBtnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity).setTitle("Report Lewat Email")
                        .setMessage("Apakah anda ingin melaporkan masalah lewat email ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ExceptionHandler exceptionHandler = new ExceptionHandler(activity);
                                exceptionHandler.emailReport();
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });


    }


    @Override
    public void onDownloadInfoSuccess(DownloadResponse downloadResponse) {
        tvTitleProgressBar.setText("Downloading Callplan...");
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setToken(Session.getSessionGlobal(Session.SESSION_USER_TOKEN));
        dataPresenterContract.doDownloadCallplan(downloadRequest);
    }

    @Override
    public void onDownloadInfoFailed(int httpCode, String message) {
        clProgress.setVisibility(View.GONE);
        rlBtnDownload.setEnabled(true);
        rlBtnUpload.setEnabled(true);
        rlBtnReset.setEnabled(true);
        rlBtnReport.setEnabled(true);

        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            rlBtnDownload.setEnabled(true);
            rlBtnUpload.setEnabled(true);
            rlBtnReset.setEnabled(true);
            rlBtnReport.setEnabled(true);
        }
    }

    @Override
    public void onDownloadCallplanSuccess(DownloadResponseCallplan downloadResponseCallplan) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tvTitleProgressBar.setText("Downloading Customer...");
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.setToken(Session.getSessionGlobal(Session.SESSION_USER_TOKEN));
        dataPresenterContract.doDownloadCustomer(downloadRequest);
    }

    @Override
    public void onDownloadCallplanFailed(int httpCode, String message) {
        clProgress.setVisibility(View.GONE);
        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            rlBtnDownload.setEnabled(true);
            rlBtnUpload.setEnabled(true);
            rlBtnReset.setEnabled(true);
            rlBtnReport.setEnabled(true);
        }
    }


    @Override
    public void onDownloadCustomerSuccess(DownloadResponseCustomer downloadResponse) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clProgress.setVisibility(View.GONE);
        Toast.makeText(activity, "Berhasil Download Data", Toast.LENGTH_LONG).show();

        rlBtnDownload.setEnabled(true);
        rlBtnUpload.setEnabled(true);
        rlBtnReset.setEnabled(true);
        rlBtnReport.setEnabled(true);


    }

    @Override
    public void onDownloadCustomerFailed(int httpCode, String message) {
        clProgress.setVisibility(View.GONE);
        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            rlBtnDownload.setEnabled(true);
            rlBtnUpload.setEnabled(true);
            rlBtnReset.setEnabled(true);
            rlBtnReport.setEnabled(true);
        }
    }


    @Override
    public void onUploadSuccess(UploadResponse uploadResponse) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tvTitleProgressBar.setText("Uploading Data Photo...");

        UploadPhotoRequest uploadPhotoRequest = new UploadPhotoRequest();
        List<MonitorDetailPhotoUpload> monitorDetailPhotoList = new ArrayList<>();
        List<MonitorHeader> monitorHeaderList = daltonDatabase.monitorHeaderDao().getAllList();
        for (int i = 0; i < monitorHeaderList.size(); i++) {
            MonitorDetailPhoto monitorDetailPhoto = daltonDatabase.monitorDetailPhotoDao().getAllListUploadByMonitorHeaderID(monitorHeaderList.get(i).getId());
            MonitorDetailPhotoUpload monitorDetailPhotoUpload = new MonitorDetailPhotoUpload();
            monitorDetailPhotoUpload.setIdusermonitor(monitorHeaderList.get(i).getIdmonitoruser());
            long[] a = new long[0];

            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto1())) {
                a = new long[1];
                a[0] = 1;

                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto1());

                monitorDetailPhotoUpload.setPhoto1(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto1("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto2())) {
                a = new long[2];
                a[0] = 1;
                a[1] = 2;

                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto2());

                monitorDetailPhotoUpload.setPhoto2(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto2("");
            }

            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto3())) {
                a = new long[3];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto3());

                monitorDetailPhotoUpload.setPhoto3(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto3("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto4())) {
                a = new long[4];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                a[3] = 4;
                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto4());

                monitorDetailPhotoUpload.setPhoto4(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto4("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto5())) {
                a = new long[5];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                a[3] = 4;
                a[4] = 5;

                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto5());

                monitorDetailPhotoUpload.setPhoto5(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto5("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto6())) {
                a = new long[6];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                a[3] = 4;
                a[4] = 5;
                a[5] = 6;

                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto6());

                monitorDetailPhotoUpload.setPhoto6(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto6("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto7())) {
                a = new long[7];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                a[3] = 4;
                a[4] = 5;
                a[5] = 6;
                a[6] = 7;

                java.util.Base64.Encoder simpleEncoder = java.util.Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto7());

                monitorDetailPhotoUpload.setPhoto7(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto7("");
            }
            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto8())) {
                a = new long[8];
                a[0] = 1;
                a[1] = 2;
                a[2] = 3;
                a[3] = 4;
                a[4] = 5;
                a[5] = 6;
                a[6] = 7;
                a[7] = 8;

                java.util.Base64.Encoder simpleEncoder = Base64.getEncoder();
                String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto8());

                monitorDetailPhotoUpload.setPhoto8(encodedString);
            } else {
                monitorDetailPhotoUpload.setPhoto8("");
            }
            monitorDetailPhotoUpload.setTophoto(a);

            monitorDetailPhotoList.add(monitorDetailPhotoUpload);
        }
        uploadPhotoRequest.setMonitorDetailPhotoList(monitorDetailPhotoList);
        if (monitorDetailPhotoList.size() > 0) {
            dataPresenterContract.doUploadPhoto(Session.getSessionGlobal(Session.SESSION_USER_TOKEN), uploadPhotoRequest);
        } else {
            Toast.makeText(activity, "Tidak ada data untuk diupload", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onUploadFailed(int httpCode, String message) {
        clProgress.setVisibility(View.GONE);
        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            rlBtnDownload.setEnabled(true);
            rlBtnUpload.setEnabled(true);
            rlBtnReset.setEnabled(true);
            rlBtnReport.setEnabled(true);
        }
    }

    @Override
    public void onUploadPhotoSuccess(PhotoUploadResponse photoUploadResponse) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clProgress.setVisibility(View.GONE);

        Toast.makeText(activity, "Berhasil Upload Data", Toast.LENGTH_LONG).show();

        rlBtnDownload.setEnabled(true);
        rlBtnUpload.setEnabled(true);
        rlBtnReset.setEnabled(true);
        rlBtnReport.setEnabled(true);

    }

    @Override
    public void onUploadPhotoFailed(int httpCode, String message) {
        clProgress.setVisibility(View.GONE);
        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            rlBtnDownload.setEnabled(true);
            rlBtnUpload.setEnabled(true);
            rlBtnReset.setEnabled(true);
            rlBtnReport.setEnabled(true);
        }
    }

    @Override
    public void onResetDataSuccess() {
        rlBtnDownload.setEnabled(true);
        rlBtnUpload.setEnabled(true);
        rlBtnReset.setEnabled(true);
        rlBtnReport.setEnabled(true);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Data berhasil dihapus.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}