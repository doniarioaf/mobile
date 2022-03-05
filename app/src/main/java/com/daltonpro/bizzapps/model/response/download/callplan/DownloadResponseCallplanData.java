package com.daltonpro.bizzapps.model.response.download.callplan;

import com.daltonpro.bizzapps.model.database.Callplan;
import com.daltonpro.bizzapps.model.database.Customer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponseCallplanData {


    @SerializedName("size")
    @Expose
    private int size;

    @SerializedName("callplans")
    @Expose
    private List<Callplan> customercallplan;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Callplan> getCustomercallplan() {
        return customercallplan;
    }

    public void setCustomercallplan(List<Callplan> customercallplan) {
        this.customercallplan = customercallplan;
    }
}