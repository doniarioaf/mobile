package com.daltonpro.bizzapps.model.request.upload;

import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadPhotoRequest {
    @SerializedName("listphoto")
    private List<MonitorDetailPhotoUpload> monitorDetailPhotoList;

    public List<MonitorDetailPhotoUpload> getMonitorDetailPhotoList() {
        return monitorDetailPhotoList;
    }

    public void setMonitorDetailPhotoList(List<MonitorDetailPhotoUpload> monitorDetailPhotoList) {
        this.monitorDetailPhotoList = monitorDetailPhotoList;
    }
}
