package com.daltonpro.bizzapps.modul.monitoring.checkin;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.request.upload.MonitorDetailPhotoUpload;
import com.daltonpro.bizzapps.model.request.upload.MonitorHeaderRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;
import com.daltonpro.bizzapps.modul.login.LoginActivity;
import com.daltonpro.bizzapps.modul.monitoring.checkin.presenter.CheckInOutPresenter;
import com.daltonpro.bizzapps.modul.monitoring.checkin.presenter.CheckInOutPresenterContract;
import com.daltonpro.bizzapps.modul.monitoring.checkin.view.CheckInOutViewContract;
import com.daltonpro.bizzapps.modul.monitoring.photo.PhotoActivity;
import com.daltonpro.bizzapps.modul.monitoring.survey.SurveyActivity;
import com.daltonpro.bizzapps.util.GPSTracker;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.daltonpro.bizzapps.util.localstorage.Session;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class CheckInOutSurveyActivity extends BaseActivity implements CheckInOutViewContract {
    private Activity activity;
    private DaltonDatabase daltonDatabase;
    private Customer customer;
    private int customerID, idCallplan;
    private TextView tvID, tvLat, tvLong, tvCity, tvArea, tvSubArea, tvCustomer, tvAddress;
    private EditText etCheckinTime, etCheckoutTime;
    private LinearLayout llSurvey;
    private RelativeLayout rlLocation;
    private CheckInOutPresenterContract checkInOutPresenterContract;
    private GPSTracker gpsTracker;
    private AppCompatButton btnCheckin, btnCheckOut;

    private MonitorHeaderRequest monitorHeader = new MonitorHeaderRequest();
    private ProgressBar progressBar;
    private int MY_ACCESS_COARSE_LOCATION_PERMISSION_CODE = 1022;
    private int MY_ACCESS_FINE_LOCATION_PERMISSION_CODE = 1021;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        activity = CheckInOutSurveyActivity.this;
        Session.beginInitialization(activity);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                gpsTracker = new GPSTracker(activity);
            }
        };
        setUPGPS(runnable);

        checkInOutPresenterContract = new CheckInOutPresenter(CheckInOutSurveyActivity.this, CheckInOutSurveyActivity.this);
        tvCustomer = findViewById(R.id.tv_customer);
        rlLocation = findViewById(R.id.rl_location);
        tvID = findViewById(R.id.tv_id);
        tvLat = findViewById(R.id.tv_lat);
        tvLong = findViewById(R.id.tv_long);
        tvCity = findViewById(R.id.tv_city);
        tvArea = findViewById(R.id.tv_area);
        tvSubArea = findViewById(R.id.tv_sub_area);
        tvAddress = findViewById(R.id.tv_address);
        etCheckinTime = findViewById(R.id.checkin_time);
        etCheckoutTime = findViewById(R.id.checkout_time);
        llSurvey = findViewById(R.id.ll_survey);
        progressBar = findViewById(R.id.progressbar);

        btnCheckin = findViewById(R.id.btn_checkin);
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCheckinTime.getText().length() > 0) {
                    Toast.makeText(activity, "Anda sudah melakukan checkin", Toast.LENGTH_LONG).show();
                } else {
                    Runnable runnable1 = new Runnable() {
                        @Override
                        public void run() {
                            doCheckin();
                        }
                    };
                    setUPGPS(runnable1);
                }

            }
        });

        btnCheckOut = findViewById(R.id.btn_checkout);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCheckinTime.getText().length() < 1) {
                    Toast.makeText(activity, "Anda belum melakukan checkin", Toast.LENGTH_LONG).show();
                } else {
                    if (etCheckoutTime.getText().length() > 0) {
                        Toast.makeText(activity, "Anda sudah melakukan checkout", Toast.LENGTH_LONG).show();
                    } else {
                        Runnable runnable1 = new Runnable() {
                            @Override
                            public void run() {
                                doCheckout();
                            }
                        };
                        setUPGPS(runnable1);
                    }
                }
            }
        });

        rlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + customer.getLatitude() + "," + customer.getLongitude() + "&daddr=" + customer.getLatitude() + "," + customer.getLongitude()));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(activity, "Tidak menemukan aplikasi google maps", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button btnSurvey = findViewById(R.id.btn_survey);
        btnSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCheckoutTime.length() > 0) {
                    Intent intent = new Intent(activity, SurveyActivity.class);
                    intent.putExtra("customerID", customerID);
                    intent.putExtra("idcallplan", customer.getIdcallplan());
                    intent.putExtra("isCheckout", true);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    Intent intent = new Intent(activity, SurveyActivity.class);
                    intent.putExtra("customerID", customerID);
                    intent.putExtra("idcallplan", customer.getIdcallplan());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });
        Button btnPhoto = findViewById(R.id.btn_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCheckoutTime.length() > 0) {
                    Intent intent = new Intent(activity, PhotoActivity.class);
                    intent.putExtra("customerID", customerID);
                    intent.putExtra("idcallplan", customer.getIdcallplan());
                    intent.putExtra("isCheckout", true);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                } else {
                    if (daltonDatabase.monitorDetailDao().getTotalSurveyByMonitorHeaderID(monitorHeader.getId()) > 0) {
                        Intent intent = new Intent(activity, PhotoActivity.class);
                        intent.putExtra("customerID", customerID);
                        intent.putExtra("idcallplan", customer.getIdcallplan());
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                    } else {
                        Toast.makeText(activity, "Belum melakukan survey, belum bisa foto", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        daltonDatabase = DaltonDatabase.getInstance(activity);
        try {
            customerID = (int) getIntent().getExtras().get("customerID");
            idCallplan = (int) getIntent().getExtras().get("idcallplan");
        } catch (Exception e) {
            e.printStackTrace();
        }

        customer = daltonDatabase.customerDao().getCustomerByCode(customerID, idCallplan);
        updateUI();

    }


    private void updateUI() {
        tvCustomer.setText(customer.getNama());
        tvID.setText("[" + String.valueOf(customer.getId()) + "]");
        tvLat.setText(String.valueOf(customer.getLatitude()));
        tvLong.setText(String.valueOf(customer.getLongitude()));
        tvCity.setText(customer.getCity());
        tvArea.setText(customer.getAreaname());
        tvSubArea.setText(customer.getSubarename());
        tvAddress.setText(customer.getAddress());

        checkInOutPresenterContract.doGetData(customerID, customer.getIdcallplan());
    }

    private void doCheckin() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        if (etCheckinTime.getText().length() == 0) {
                            SimpleDateFormat hourFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                            String waktu = hourFormatGmt.format(new Date()) + "";

                            Timestamp time = new Timestamp(System.currentTimeMillis());
                            String ts = String.valueOf(time.getTime());

                            MonitorHeader monitorHeader = new MonitorHeader();
                            monitorHeader.setIdproject(daltonDatabase.callplanDAO().getIdProject(customer.getIdcallplan()));
                            monitorHeader.setIdcustomer(customerID);
                            monitorHeader.setIdcallplan(customer.getIdcallplan());
                            monitorHeader.setTanggal(ts);
                            monitorHeader.setCheckintime(ts);

                            gpsTracker = new GPSTracker(activity);

                            if (gpsTracker.getLatitude() == 0.0 || gpsTracker.getLongitude() == 0.0) {
                                gpsTracker.showSettingGps();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                etCheckinTime.setText(waktu);

                                monitorHeader.setLatitudein(gpsTracker.getLatitude());
                                monitorHeader.setLongitudein(gpsTracker.getLongitude());
                                checkInOutPresenterContract.doCheckin(monitorHeader);
                            }

                        }


                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("").setMessage("Anda yakin untuk melakukan checkin ?").setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener).show();
    }

    private void doCheckout() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if (daltonDatabase.monitorDetailDao().getTotalSurveyByMonitorHeaderID(monitorHeader.getId()) == 0 ||
                                daltonDatabase.monitorDetailPhotoDao().getTotalPhotoByMonitorHeaderID(monitorHeader.getId()) < 5) {
                            Toast.makeText(activity, "Tidak bisa checkout, Belum melakukan survey atau Foto harus 5", Toast.LENGTH_SHORT).show();
                        } else {
                            if (etCheckoutTime.getText().length() == 0) {
                                progressBar.setVisibility(View.VISIBLE);
                                btnCheckOut.setEnabled(false);

                                monitorHeader = daltonDatabase.monitorHeaderDao().getAllListByMonitorHeaderID(monitorHeader.getId());

                                SimpleDateFormat hourFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String waktu = hourFormatGmt.format(new Date()) + "";

                                Timestamp time = new Timestamp(System.currentTimeMillis());
                                String ts = String.valueOf(time.getTime());

                                monitorHeader.setCheckouttime(ts);

                                UploadRequest uploadRequest = new UploadRequest();
                                List<MonitorHeaderRequest> monitorHeaderList = new ArrayList<>();

                                monitorHeader.setMonitorDetailList(daltonDatabase.monitorDetailDao().getAllListByMonitorHeaderID(monitorHeader.getId()));

                                monitorHeaderList.add(monitorHeader);
                                uploadRequest.setMonitorusermobiles(monitorHeaderList);

                                gpsTracker = new GPSTracker(activity);

                                if (gpsTracker.getLatitude() == 0.0 || gpsTracker.getLongitude() == 0.0) {
                                    gpsTracker.showSettingGps();
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    monitorHeader.setLatitudeout(gpsTracker.getLatitude());
                                    monitorHeader.setLongitudeout(gpsTracker.getLongitude());

                                    etCheckoutTime.setText(waktu);
                                    checkInOutPresenterContract.doCheckOut(Session.getSessionGlobal(Session.SESSION_USER_TOKEN), monitorHeader, uploadRequest);
                                }

                            }
                        }


                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("").setMessage("Anda yakin untuk melakukan checkout ?").setPositiveButton("Ya", dialogClickListener)
                .setNegativeButton("Tidak", dialogClickListener).show();
    }

    @Override
    public void getData(MonitorHeader monitorHeader) {

        this.monitorHeader.setId(monitorHeader.getId());
        this.monitorHeader.setCheckintime(monitorHeader.getCheckintime());
        if (NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader.getCheckouttime())) {
            this.monitorHeader.setCheckouttime(monitorHeader.getCheckouttime());
        }
        this.monitorHeader.setIdcallplan(monitorHeader.getIdcallplan());
        this.monitorHeader.setIdcustomer(monitorHeader.getIdcustomer());
        this.monitorHeader.setIdproject(monitorHeader.getIdproject());
        this.monitorHeader.setLatitudein(monitorHeader.getLatitudein());
        this.monitorHeader.setTanggal(monitorHeader.getTanggal());
        this.monitorHeader.setLatitudeout(monitorHeader.getLatitudeout());
        this.monitorHeader.setLongitudein(monitorHeader.getLongitudein());
        this.monitorHeader.setLongitudeout(monitorHeader.getLongitudeout());
        if (NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader.getMonitorDetailList())) {
            this.monitorHeader.setMonitorDetailList(monitorHeader.getMonitorDetailList());
        }

        SimpleDateFormat hourFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader) && NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader.getCheckintime())) {

            Date checkinTime = new Date(Long.parseLong(monitorHeader.getCheckintime()));
            etCheckinTime.setText(hourFormatGmt.format(checkinTime));

            btnCheckin.setBackgroundColor(getResources().getColor(R.color.gray_hint));
            llSurvey.setVisibility(View.VISIBLE);
        }

        if (NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader) && NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader.getCheckouttime())) {
            Date checkOutTime = new Date(Long.parseLong(monitorHeader.getCheckouttime()));
            etCheckoutTime.setText(hourFormatGmt.format(checkOutTime));
            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.red_hint));
        }


    }

    @Override
    public void oncheckin(MonitorHeader monitorHeader) {
        llSurvey.setVisibility(View.VISIBLE);
        if (etCheckinTime.getText().length() > 0) {
            btnCheckin.setBackgroundColor(getResources().getColor(R.color.gray_hint));
        }

    }

    @Override
    public void onCheckoutSendSuccess(UploadResponse uploadResponse) {
        UploadPhotoRequest uploadPhotoRequest = new UploadPhotoRequest();
        List<MonitorDetailPhotoUpload> monitorDetailPhotoList = new ArrayList<>();
        List<MonitorHeader> monitorHeaderList = daltonDatabase.monitorHeaderDao().getAllList();
        for (int i = 0; i < monitorHeaderList.size(); i++) {
            MonitorDetailPhoto monitorDetailPhoto = daltonDatabase.monitorDetailPhotoDao().getAllListUploadByMonitorHeaderID(monitorHeaderList.get(i).getId());
            MonitorDetailPhotoUpload monitorDetailPhotoUpload = new MonitorDetailPhotoUpload();
            monitorDetailPhotoUpload.setIdusermonitor(monitorHeaderList.get(i).getIdmonitoruser());
            long[] a = new long[0];

            if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto)) {
                if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto1())) {
                    a = new long[1];
                    a[0] = 1;

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
                    String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto1());

                    monitorDetailPhotoUpload.setPhoto1(encodedString);
                } else {
                    monitorDetailPhotoUpload.setPhoto1("");
                }
                if (NullEmptyChecker.isNotNullOrNotEmpty(monitorDetailPhoto.getPhoto2())) {
                    a = new long[2];
                    a[0] = 1;
                    a[1] = 2;

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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
                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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
                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
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

                    Base64.Encoder simpleEncoder = Base64.getEncoder();
                    String encodedString = simpleEncoder.encodeToString(monitorDetailPhoto.getPhoto8());

                    monitorDetailPhotoUpload.setPhoto8(encodedString);
                } else {
                    monitorDetailPhotoUpload.setPhoto8("");
                }
            }
            monitorDetailPhotoUpload.setTophoto(a);

            monitorDetailPhotoList.add(monitorDetailPhotoUpload);
        }
        uploadPhotoRequest.setMonitorDetailPhotoList(monitorDetailPhotoList);
        if (monitorDetailPhotoList.size() > 0) {
            checkInOutPresenterContract.doSendPhoto(Session.getSessionGlobal(Session.SESSION_USER_TOKEN), uploadPhotoRequest);
        } else {
            Toast.makeText(activity, "Tidak ada data untuk diupload", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckoutSendFailed(int httpCode, String message) {
        progressBar.setVisibility(View.GONE);
        btnCheckOut.setEnabled(true);

        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            if (etCheckoutTime.getText().length() > 0) {
                btnCheckOut.setBackgroundColor(getResources().getColor(R.color.red_hint));
            }

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    public void onCheckoutSendPhotoSuccess(PhotoUploadResponse photoUploadResponse) {
        progressBar.setVisibility(View.GONE);
        btnCheckOut.setEnabled(true);

        if (etCheckoutTime.getText().length() > 0) {
            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.red_hint));
        }

        if (NullEmptyChecker.isNotNullOrNotEmpty(photoUploadResponse) && photoUploadResponse.isSuccess()) {
            Toast.makeText(activity, "Berhasil Upload Data", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void onCheckoutSendPhotoFailed(int httpCode, String message) {
        progressBar.setVisibility(View.GONE);
        if (httpCode == 401 || httpCode == 400) {
            Session.clearSessionGlobal(Session.SESSION_USER_TOKEN);

            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finishAffinity();
            Intent intent = new Intent(activity, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void setUPGPS(Runnable runnable) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_ACCESS_COARSE_LOCATION_PERMISSION_CODE);
            } else {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_ACCESS_FINE_LOCATION_PERMISSION_CODE);
                } else {
                    runnable.run();
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_ACCESS_COARSE_LOCATION_PERMISSION_CODE);
            } else {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_ACCESS_FINE_LOCATION_PERMISSION_CODE);
                } else {
                    runnable.run();
                }
            }
        }
    }
}