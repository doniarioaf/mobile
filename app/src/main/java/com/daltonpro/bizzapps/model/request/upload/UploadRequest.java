package com.daltonpro.bizzapps.model.request.upload;

import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadRequest {
    @SerializedName("monitorusermobiles")
    private List<MonitorHeaderRequest> monitorusermobiles;

    public List<MonitorHeaderRequest> getMonitorusermobiles() {
        return monitorusermobiles;
    }

    public void setMonitorusermobiles(List<MonitorHeaderRequest> monitorusermobiles) {
        this.monitorusermobiles = monitorusermobiles;
    }
}
