package com.daltonpro.bizzapps.modul.monitoring.checkin.view;

import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.response.upload.PhotoUploadResponse;
import com.daltonpro.bizzapps.model.response.upload.UploadResponse;

public interface CheckInOutViewContract {
    void getData(MonitorHeader monitorHeader);

    void oncheckin(MonitorHeader monitorHeader);

    void onCheckoutSendSuccess(UploadResponse uploadResponse);

    void onCheckoutSendFailed(int httpCode, String message);

    void onCheckoutSendPhotoSuccess(PhotoUploadResponse photoUploadResponse);

    void onCheckoutSendPhotoFailed(int httpCode, String message);


}
