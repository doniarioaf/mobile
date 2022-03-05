package com.daltonpro.bizzapps.modul.monitoring.photo.view;

import com.daltonpro.bizzapps.model.database.MonitorDetailPhotoShow;
import com.daltonpro.bizzapps.model.database.MonitorHeader;

import java.util.List;

public interface PhotoViewContract {
    void getData(MonitorHeader monitorHeader);

    void getDataDetailPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList);

    void onAddPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position);

    void onUpdatePhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position);

    void onNext();

    void onReset();

}
