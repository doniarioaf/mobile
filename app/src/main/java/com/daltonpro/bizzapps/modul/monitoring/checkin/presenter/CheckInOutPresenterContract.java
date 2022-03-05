package com.daltonpro.bizzapps.modul.monitoring.checkin.presenter;

import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.request.upload.MonitorHeaderRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadPhotoRequest;
import com.daltonpro.bizzapps.model.request.upload.UploadRequest;

public interface CheckInOutPresenterContract {
    void doGetData(int customerID,int callPlanId);

    void doCheckin(MonitorHeader monitorHeader);

    void doCheckOut(String token, MonitorHeaderRequest monitorHeader, UploadRequest uploadRequest);

    void doSendPhoto(String token, UploadPhotoRequest uploadPhotoRequest);
}
