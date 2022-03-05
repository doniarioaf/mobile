package com.daltonpro.bizzapps.modul.data.presenter;

import android.content.Context;

import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.core.api.APIService;
import com.daltonpro.bizzapps.core.api.APIUtils;
import com.daltonpro.bizzapps.model.database.InfoDetail;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.request.DownloadRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;
import com.daltonpro.bizzapps.model.response.ErrorResponse;
import com.daltonpro.bizzapps.model.response.download.callplan.DownloadResponseCallplan;
import com.daltonpro.bizzapps.model.response.download.customer.DownloadResponseCustomer;
import com.daltonpro.bizzapps.model.response.download.info.DownloadResponse;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;
import com.daltonpro.bizzapps.modul.data.view.DataViewContract;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPresenter implements DataPresenterContract {
    private final DataViewContract dataViewContract;
    private final APIService apiService;
    private DaltonDatabase daltonDatabase;
    private HashMap hashMap = new HashMap();

    public DataPresenter(DataViewContract dataViewContract, Context context) {
        this.dataViewContract = dataViewContract;
        apiService = APIUtils.getApiService();
        daltonDatabase = DaltonDatabase.getInstance(context);
    }


    @Override
    public void doDownload(DownloadRequest downloadRequest) {
        Gson gson = new Gson();

        apiService.download(downloadRequest.getToken()).enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        DownloadResponse downloadResponse = response.body();
                        for (int i = 0; i < downloadResponse.getData().getListinfo().size(); i++) {
                            InfoHeader infoHeader = downloadResponse.getData().getListinfo().get(i).getInfoheader();
                            daltonDatabase.infoHeaderDao().insertInfoHeader(infoHeader);
                            for (int j = 0; j < downloadResponse.getData().getListinfo().get(i).getDetail().size(); j++) {
                                InfoDetail infoDetail = downloadResponse.getData().getListinfo().get(i).getDetail().get(j);
                                infoDetail.setIdInfoHeader(infoHeader.getId());
                                daltonDatabase.infoDetailDao().insertInfoDetail(infoDetail);
                            }
                        }

                        dataViewContract.onDownloadInfoSuccess(downloadResponse);
                    } else {
                        dataViewContract.onDownloadInfoFailed(response.body().getHttpcode(), response.body().getMessage());
                    }
                } else {
                    if (response.body() != null && response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        DownloadResponse downloadResponse = response.body();
                        for (int i = 0; i < downloadResponse.getData().getListinfo().size(); i++) {
                            daltonDatabase.infoHeaderDao().insertInfoHeader(downloadResponse.getData().getListinfo().get(i).getInfoheader());
                            for (int j = 0; j < downloadResponse.getData().getListinfo().get(i).getDetail().size(); j++) {
                                daltonDatabase.infoDetailDao().insertInfoDetail(downloadResponse.getData().getListinfo().get(i).getDetail().get(j));
                            }
                        }

                        dataViewContract.onDownloadInfoSuccess(downloadResponse);
                    } else {
                        try {
                            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                            dataViewContract.onDownloadInfoFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                        } catch (IOException e) {
                            e.printStackTrace();
                            dataViewContract.onDownloadInfoFailed(response.code(), e.getMessage());

                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                dataViewContract.onDownloadInfoFailed(500, t.getMessage());
            }
        });
    }

    @Override
    public void doDownloadCallplan(DownloadRequest downloadRequest) {

        int limit = 10;
        int offset = 0;

        downloadPaginationCallplan(downloadRequest, limit, offset);


    }

    private HashMap downloadPaginationCallplan(DownloadRequest downloadRequest, int limit, int offset) {

        apiService.downloadCallplan(downloadRequest.getToken(), limit, offset).enqueue(new Callback<DownloadResponseCallplan>() {
            @Override
            public void onResponse(Call<DownloadResponseCallplan> call, Response<DownloadResponseCallplan> response) {

                if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                    DownloadResponseCallplan downloadResponse = response.body();

                    for (int i = 0; i < downloadResponse.getData().getCustomercallplan().size(); i++) {
                        daltonDatabase.callplanDAO().insertCallplan(downloadResponse.getData().getCustomercallplan().get(i));
                    }

                    hashMap.put("size", downloadResponse.getData().getSize());
                    hashMap.put("limit", limit);
                    hashMap.put("offset", offset + 10);
                    hashMap.put("downloadResponseCallplan", downloadResponse);

                    if (offset < (int) hashMap.get("size")) {
                        hashMap = downloadPaginationCallplan(downloadRequest, (int) hashMap.get("limit"), (int) hashMap.get("offset"));
                    } else {
                        dataViewContract.onDownloadCallplanSuccess(downloadResponse);
                    }
                } else {
                    dataViewContract.onDownloadCallplanFailed(response.body().getHttpcode(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<DownloadResponseCallplan> call, Throwable t) {
                dataViewContract.onDownloadCallplanFailed(500, t.getMessage());
            }
        });
        return hashMap;
    }

    @Override
    public void doDownloadCustomer(DownloadRequest downloadRequest) {
        int limit = 10;
        int offset = 0;

        downloadPaginationCustomer(downloadRequest, limit, offset);
    }

    private HashMap downloadPaginationCustomer(DownloadRequest downloadRequest, int limit, int offset) {

        apiService.downloadCustomer(downloadRequest.getToken(), limit, offset).enqueue(new Callback<DownloadResponseCustomer>() {
            @Override
            public void onResponse(Call<DownloadResponseCustomer> call, Response<DownloadResponseCustomer> response) {

                if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                    DownloadResponseCustomer downloadResponse = response.body();

                    for (int i = 0; i < downloadResponse.getData().getCustomercallplan().size(); i++) {
                        daltonDatabase.customerDao().insertCustomer(downloadResponse.getData().getCustomercallplan().get(i));
                    }

                    hashMap.put("size", downloadResponse.getData().getSize());
                    hashMap.put("limit", limit);
                    hashMap.put("offset", offset + 10);
                    hashMap.put("downloadResponseCallplan", downloadResponse);

                    if (offset < (int) hashMap.get("size")) {
                        hashMap = downloadPaginationCustomer(downloadRequest, (int) hashMap.get("limit"), (int) hashMap.get("offset"));
                    } else {
                        dataViewContract.onDownloadCustomerSuccess(downloadResponse);
                    }
                } else {
                    dataViewContract.onDownloadCallplanFailed(response.body().getHttpcode(), response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<DownloadResponseCustomer> call, Throwable t) {
                dataViewContract.onDownloadCallplanFailed(500, t.getMessage());
            }
        });
        return hashMap;
    }

    @Override
    public void doUpload(String token, UploadRequest uploadRequest) {
        Gson gson = new Gson();

        apiService.upload(token, uploadRequest).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.code() == 401 || response.code() == 400) {
                    try {
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        for (int i=0;i<uploadRequest.getMonitorusermobiles().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncData(
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcustomer(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcallplan(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdproject(),
                                    "N");
                        }

                        dataViewContract.onUploadFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        dataViewContract.onUploadFailed(response.code(), e.getMessage());

                    }
                } else {
                    if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        UploadResponse uploadResponse = response.body();
                        for (int i=0;i<uploadRequest.getMonitorusermobiles().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncData(
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcustomer(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcallplan(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdproject(),
                                    "Y");
                        }
                        dataViewContract.onUploadSuccess(uploadResponse);
                    } else {
                        for (int i=0;i<uploadRequest.getMonitorusermobiles().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncData(
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcustomer(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdcallplan(),
                                    uploadRequest.getMonitorusermobiles().get(i).getIdproject(),
                                    "N");
                        }
                        dataViewContract.onUploadFailed(response.body().getHttpcode(), response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                for (int i=0;i<uploadRequest.getMonitorusermobiles().size();i++){
                    daltonDatabase.monitorHeaderDao().updateStatusSyncData(
                            uploadRequest.getMonitorusermobiles().get(i).getIdcustomer(),
                            uploadRequest.getMonitorusermobiles().get(i).getIdcallplan(),
                            uploadRequest.getMonitorusermobiles().get(i).getIdproject(),
                            "N");
                }
                dataViewContract.onUploadFailed(500, t.getMessage());
            }
        });


    }

    @Override
    public void doUploadPhoto(String token, UploadPhotoRequest uploadPhotoRequest) {
        Gson gson = new Gson();
        apiService.uploadPhoto(token, uploadPhotoRequest).enqueue(new Callback<PhotoUploadResponse>() {
            @Override
            public void onResponse(Call<PhotoUploadResponse> call, Response<PhotoUploadResponse> response) {
                if (response.code() == 401 || response.code() == 400) {
                    try {
                        for (int i=0;i<uploadPhotoRequest.getMonitorDetailPhotoList().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(i).getIdusermonitor(),"N");
                        }

                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        dataViewContract.onUploadPhotoFailed(response.code(), String.valueOf(errorResponse.getMessage()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        dataViewContract.onUploadPhotoFailed(response.code(), e.getMessage());

                    }
                } else {
                    if (response.body().getHttpcode() >= 200 && response.body().getHttpcode() <= 300) {
                        PhotoUploadResponse uploadResponse = response.body();
                        for (int i=0;i<uploadPhotoRequest.getMonitorDetailPhotoList().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(i).getIdusermonitor(),"Y");
                        }
                        dataViewContract.onUploadPhotoSuccess(uploadResponse);
                    } else {
                        for (int i=0;i<uploadPhotoRequest.getMonitorDetailPhotoList().size();i++){
                            daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(i).getIdusermonitor(),"N");
                        }
                        dataViewContract.onUploadPhotoFailed(response.body().getHttpcode(), response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<PhotoUploadResponse> call, Throwable t) {
                for (int i=0;i<uploadPhotoRequest.getMonitorDetailPhotoList().size();i++){
                    daltonDatabase.monitorHeaderDao().updateStatusSyncByIdMonitorUser(uploadPhotoRequest.getMonitorDetailPhotoList().get(i).getIdusermonitor(),"N");
                }
                dataViewContract.onUploadPhotoFailed(500, t.getMessage());
            }
        });

    }

    @Override
    public void doResetData() {
        daltonDatabase.infoHeaderDao().deleteAll();
        daltonDatabase.infoDetailDao().deleteAll();
        daltonDatabase.monitorHeaderDao().deleteAll();
        daltonDatabase.monitorDetailDao().deleteAll();
        daltonDatabase.monitorDetailPhotoDao().deleteAll();
        daltonDatabase.customerDao().deleteAll();
        daltonDatabase.callplanDAO().deleteAll();

        dataViewContract.onResetDataSuccess();
    }
}
