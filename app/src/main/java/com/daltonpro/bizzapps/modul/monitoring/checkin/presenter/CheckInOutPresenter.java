package com.daltonpro.bizzapps.modul.monitoring.checkin.presenter;

import android.content.Context;

import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.core.api.APIService;
import com.daltonpro.bizzapps.core.api.APIUtils;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.request.upload.MonitorHeaderRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;
import com.daltonpro.bizzapps.model.response.ErrorResponse;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;
import com.daltonpro.bizzapps.modul.monitoring.checkin.view.CheckInOutViewContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInOutPresenter implements CheckInOutPresenterContract {
    private final CheckInOutViewContract checkInOutViewContract;
    private Context context;
    private final APIService apiService;
    private DaltonDatabase daltonDatabase;

    public CheckInOutPresenter(CheckInOutViewContract checkInOutViewContract, Context context) {
        this.checkInOutViewContract = checkInOutViewContract;
        apiService = APIUtils.getApiService();
        daltonDatabase = DaltonDatabase.getInstance(context);
    }

    @Override
    public void doGetData(int customerID, int callplanID) {
        MonitorHeader monitorHeader = daltonDatabase.monitorHeaderDao().getAllListByCustomerID(customerID,callplanID);
        if (NullEmptyChecker.isNotNullOrNotEmpty(monitorHeader)) {
            checkInOutViewContract.getData(monitorHeader);
        }

    }

    @Override
    public void doCheckin(MonitorHeader monitorHeader) {
        daltonDatabase.monitorHeaderDao().insertMonitorHeader(monitorHeader);
        checkInOutViewContract.oncheckin(monitorHeader);
    }

    @Override
    public void doCheckOut(String token, MonitorHeaderRequest monitorHeader, UploadRequest uploadRequest) {
        MonitorHeader monitorHeaderDb = new MonitorHeader();
        monitorHeaderDb.setId(monitorHeader.getId());
        monitorHeaderDb.setCheckintime(monitorHeader.getCheckintime());
        monitorHeaderDb.setCheckouttime(monitorHeader.getCheckouttime());
        monitorHeaderDb.setIdcallplan(monitorHeader.getIdcallplan());
        monitorHeaderDb.setIdcustomer(monitorHeader.getIdcustomer());
        monitorHeaderDb.setIdproject(monitorHeader.getIdproject());
        monitorHeaderDb.setLatitudein(monitorHeader.getLatitudein());
        monitorHeaderDb.setTanggal(monitorHeader.getTanggal());
        monitorHeaderDb.setLatitudeout(monitorHeader.getLatitudeout());
        monitorHeaderDb.setLongitudein(monitorHeader.getLongitudein());
        monitorHeaderDb.setLongitudeout(monitorHeader.getLongitudeout());
        monitorHeaderDb.setStatusUploaded("N");
        monitorHeaderDb.setMonitorDetailList(monitorHeader.getMonitorDetailList());

        daltonDatabase.monitorHeaderDao().updateMonitorHeader(monitorHeaderDb);
        Gson gson = new Gson();

        apiService.upload(token, uploadRequest).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.code() == 401 || response.code() == 400) {
                    try {
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        checkInOutViewContract.onCheckoutSendFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        checkInOutViewContract.onCheckoutSendFailed(response.code(), e.getMessage());
                    }
                } else {
                    if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        UploadResponse uploadResponse = response.body();
                        if (NullEmptyChecker.isNotNullOrNotEmpty(uploadResponse.getData())) {
                            monitorHeaderDb.setIdmonitoruser(uploadResponse.getData().get(0).getIdmonitoruser());
                            monitorHeaderDb.setStatusUploaded("Y");
                            daltonDatabase.monitorHeaderDao().updateMonitorHeader(monitorHeaderDb);
                        }
                        checkInOutViewContract.onCheckoutSendSuccess(uploadResponse);
                    } else {
                        checkInOutViewContract.onCheckoutSendFailed(response.body().getHttpcode(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                checkInOutViewContract.onCheckoutSendFailed(500, t.getMessage());
            }
        });


    }

    @Override
    public void doSendPhoto(String token, UploadPhotoRequest uploadPhotoRequest) {
        Gson gson = new Gson();
        apiService.uploadPhoto(token, uploadPhotoRequest).enqueue(new Callback<PhotoUploadResponse>() {
            @Override
            public void onResponse(Call<PhotoUploadResponse> call, Response<PhotoUploadResponse> response) {
                if (response.code() == 401 || response.code() == 400) {
                    try {
                        daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(0).getIdusermonitor(),"N");
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        checkInOutViewContract.onCheckoutSendPhotoFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        checkInOutViewContract.onCheckoutSendPhotoFailed(response.code(), e.getMessage());

                    }
                } else {
                    if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        PhotoUploadResponse uploadResponse = response.body();
                        daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(0).getIdusermonitor(),"Y");
                        checkInOutViewContract.onCheckoutSendPhotoSuccess(uploadResponse);
                    } else {
                        daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(0).getIdusermonitor(),"N");
                        checkInOutViewContract.onCheckoutSendPhotoFailed(response.body().getHttpcode(), response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<PhotoUploadResponse> call, Throwable t) {
                daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(0).getIdusermonitor(),"N");
                checkInOutViewContract.onCheckoutSendPhotoFailed(500, t.getMessage());
            }
        });
    }

}
