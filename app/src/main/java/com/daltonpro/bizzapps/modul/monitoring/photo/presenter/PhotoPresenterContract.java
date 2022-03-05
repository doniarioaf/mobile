package com.daltonpro.bizzapps.modul.monitoring.photo.presenter;

import com.daltonpro.bizzapps.model.database.MonitorDetailPhotoShow;

import java.util.List;

public interface PhotoPresenterContract {
    void doGetData(int customerID,int callPlanID);

    void doGetDataDetailPhoto(int monitorHeaderID);

    void doAddPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position);

    void doUpdatePhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position);

    void doNext();

    void doReset(int monitorHeaderID);


}
