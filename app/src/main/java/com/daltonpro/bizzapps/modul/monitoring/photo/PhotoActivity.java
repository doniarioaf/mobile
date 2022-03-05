package com.daltonpro.bizzapps.modul.monitoring.photo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daltonpro.bizzapps.R;
import com.daltonpro.bizzapps.core.BaseActivity;
import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhotoShow;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.modul.monitoring.photo.adapter.PhotoDetailTypeAdapter;
import com.daltonpro.bizzapps.modul.monitoring.photo.presenter.PhotoPresenter;
import com.daltonpro.bizzapps.modul.monitoring.photo.presenter.PhotoPresenterContract;
import com.daltonpro.bizzapps.modul.monitoring.photo.view.PhotoViewContract;
import com.daltonpro.bizzapps.util.BitmapHelper;
import com.daltonpro.bizzapps.util.localstorage.Session;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhotoActivity extends BaseActivity implements PhotoViewContract {
    private Activity activity;
    private DaltonDatabase daltonDatabase;
    private Customer customer;
    private int customerID,idCallplan;
    private boolean isCheckout;
    private PhotoPresenterContract photoPresenterContract;

    private ProgressBar progressBar;

    private MonitorHeader monitorHeader;

    private RecyclerView recyclerView;
    private PhotoDetailTypeAdapter photoDetailTypeAdapter;
    private static final int CAMERA_REQUEST_ADD = 1988;
    private static final int CAMERA_REQUEST_UPDATE = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private List<MonitorDetailPhotoShow> monitorDetailPhotoShowList;
    private int position;

    private TextView tvCustomer, tvAddress;

    private AppCompatButton btnSave, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        activity = PhotoActivity.this;
        Session.beginInitialization(activity);

        tvCustomer = findViewById(R.id.tv_customer);
        tvAddress = findViewById(R.id.tv_address);

        progressBar = findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.rv_photo);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPresenterContract.doNext();
            }
        });
        btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                photoPresenterContract.doReset(monitorHeader.getId());
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("").setMessage("Anda yakin untuk reset semua foto ?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();
            }
        });

        recyclerView.setHasFixedSize(true);


        photoPresenterContract = new PhotoPresenter(PhotoActivity.this, PhotoActivity.this);

    }


    @Override
    protected void onResume() {
        super.onResume();

        daltonDatabase = DaltonDatabase.getInstance(activity);
        try {
            customerID = (int) getIntent().getExtras().get("customerID");
            idCallplan = (int) getIntent().getExtras().get("idcallplan");
            isCheckout = getIntent().getExtras().getBoolean("isCheckout", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        customer = daltonDatabase.customerDao().getCustomerByCode(customerID,idCallplan);
        updateUI();
    }


    private void updateUI() {
        tvCustomer.setText(customer.getNama());
        tvAddress.setText(customer.getAddress());

        photoPresenterContract.doGetData(customerID,customer.getIdcallplan());
        photoPresenterContract.doGetDataDetailPhoto(monitorHeader.getId());
    }

    @Override
    public void getData(MonitorHeader monitorHeader) {
        this.monitorHeader = monitorHeader;
    }

    @Override
    public void getDataDetailPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList) {
        this.monitorDetailPhotoShowList = monitorDetailPhotoShowList;

        photoDetailTypeAdapter = new PhotoDetailTypeAdapter(activity, photoPresenterContract, monitorDetailPhotoShowList, monitorHeader.getId(), isCheckout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(photoDetailTypeAdapter);

        if (isCheckout) {
            recyclerView.setEnabled(false);
            btnReset.setVisibility(View.GONE);
            btnSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAddPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position) {
        this.position = position;
        if (monitorDetailPhotoShowList.size() < 7) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_ADD);
                }
            } else {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST_ADD);
                }
            }

        } else {
            Toast.makeText(activity, "Maksimal untuk foto 5", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpdatePhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position) {
        this.position = position;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_UPDATE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_UPDATE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_ADD && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");

            String watermarkRow1 = customer.getId() + " " + customer.getNama();
            String watermarkRow2 = customer.getAddress();
            String watermarkRow3 = customer.getCity();
            bmp = BitmapHelper.addWatermark(bmp, watermarkRow1, watermarkRow2, watermarkRow3);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            monitorDetailPhotoShowList.get(monitorDetailPhotoShowList.size() - 1).setDataPhoto(byteArray);
            monitorDetailPhotoShowList.get(monitorDetailPhotoShowList.size() - 1).setDataPhotoBitmap(bmp);
            monitorDetailPhotoShowList.get(monitorDetailPhotoShowList.size() - 1).setIdinfophoto(daltonDatabase.monitorDetailPhotoDao().getIdMax());

            MonitorDetailPhoto monitorDetailPhoto = getPhotoPosition(monitorDetailPhotoShowList.get(monitorDetailPhotoShowList.size() - 1), monitorDetailPhotoShowList.size() - 1);
            daltonDatabase.monitorDetailPhotoDao().insertMonitordetailPhoto(monitorDetailPhoto);
            photoDetailTypeAdapter.updateItems(monitorDetailPhotoShowList);


        } else if (requestCode == CAMERA_REQUEST_UPDATE && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");

            String watermarkRow1 = customer.getId() + " " + customer.getNama();
            String watermarkRow2 = customer.getAddress();
            String watermarkRow3 = customer.getCity();
            bmp = BitmapHelper.addWatermark(bmp, watermarkRow1, watermarkRow2, watermarkRow3);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();


            monitorDetailPhotoShowList.get(position).setDataPhoto(byteArray);
            monitorDetailPhotoShowList.get(position).setDataPhotoBitmap(bmp);
            monitorDetailPhotoShowList.get(position).setIdinfophoto(monitorDetailPhotoShowList.get(position).getIdinfophoto());

            MonitorDetailPhoto monitorDetailPhoto = getPhotoPosition(monitorDetailPhotoShowList.get(position), position);
            daltonDatabase.monitorDetailPhotoDao().insertMonitordetailPhoto(monitorDetailPhoto);
            photoDetailTypeAdapter.updateItems(monitorDetailPhotoShowList);

        }
    }

    private MonitorDetailPhoto getPhotoPosition(MonitorDetailPhotoShow monitorDetailPhotoShow, int position) {
        MonitorDetailPhoto monitorDetailPhoto = new MonitorDetailPhoto();

        switch (position) {
            case 1:
                monitorDetailPhoto.setPhoto1(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 2:
                monitorDetailPhoto.setPhoto2(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 3:
                monitorDetailPhoto.setPhoto3(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 4:
                monitorDetailPhoto.setPhoto4(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 5:
                monitorDetailPhoto.setPhoto5(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 6:
                monitorDetailPhoto.setPhoto6(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 7:
                monitorDetailPhoto.setPhoto7(monitorDetailPhotoShow.getDataPhoto());
                break;
            case 8:
                monitorDetailPhoto.setPhoto8(monitorDetailPhotoShow.getDataPhoto());
                break;
            default:
                monitorDetailPhoto.setPhoto1(monitorDetailPhotoShow.getDataPhoto());
        }

        monitorDetailPhoto.setIdinfophoto(monitorDetailPhotoShow.getIdinfophoto());
        monitorDetailPhoto.setMonitor_header_id(monitorHeader.getId());
        monitorDetailPhoto.setTypePhoto("SHOW");

        return monitorDetailPhoto;
    }

    @Override
    public void onNext() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setEnabled(true);
        finish();
    }

    @Override
    public void onReset() {
        updateUI();
    }
}