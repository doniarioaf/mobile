package com.daltonpro.bizzapps.model.response.download.customer;

import com.daltonpro.bizzapps.model.database.Callplan;
import com.daltonpro.bizzapps.model.database.Customer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponseCustomerData {


    @SerializedName("size")
    @Expose
    private int size;

    @SerializedName("customercallplan")
    @Expose
    private List<Customer> customercallplan;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Customer> getCustomercallplan() {
        return customercallplan;
    }

    public void setCustomercallplan(List<Customer> customercallplan) {
        this.customercallplan = customercallplan;
    }
}