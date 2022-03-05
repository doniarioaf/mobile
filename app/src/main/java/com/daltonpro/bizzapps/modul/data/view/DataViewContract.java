package com.daltonpro.bizzapps.modul.data.view;

import com.daltonpro.bizzapps.model.response.download.callplan.DownloadResponseCallplan;
import com.daltonpro.bizzapps.model.response.download.customer.DownloadResponseCustomer;
import com.daltonpro.bizzapps.model.response.download.info.DownloadResponse;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;

public interface DataViewContract {
    void onDownloadInfoSuccess(DownloadResponse downloadResponse);

    void onDownloadInfoFailed(int httpCode, String message);

    void onDownloadCallplanSuccess(DownloadResponseCallplan downloadResponseCallplan);

    void onDownloadCallplanFailed(int httpCode, String message);

    void onDownloadCustomerSuccess(DownloadResponseCustomer downloadResponse);

    void onDownloadCustomerFailed(int httpCode, String message);

    void onUploadSuccess(UploadResponse uploadResponse);

    void onUploadFailed(int httpCode, String message);

    void onUploadPhotoSuccess(PhotoUploadResponse photoUploadResponse);

    void onUploadPhotoFailed(int httpCode, String message);

    void onResetDataSuccess();
}
