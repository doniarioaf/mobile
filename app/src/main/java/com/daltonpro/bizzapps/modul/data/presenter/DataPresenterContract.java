package com.daltonpro.bizzapps.modul.data.presenter;

import com.daltonpro.bizzapps.model.request.DownloadRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;

public interface DataPresenterContract {
    void doDownload(DownloadRequest downloadRequest);

    void doDownloadCallplan(DownloadRequest downloadRequest);

    void doDownloadCustomer(DownloadRequest downloadRequest);

    void doUpload(String token,UploadRequest uploadRequest);

    void doUploadPhoto(String token, UploadPhotoRequest uploadPhotoRequest);

    void doResetData();
}
