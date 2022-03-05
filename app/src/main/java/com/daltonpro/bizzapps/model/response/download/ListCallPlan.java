package com.daltonpro.bizzapps.model.response.download;

import com.daltonpro.bizzapps.model.database.Customer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListCallPlan {
    @SerializedName("customercallplans")
    @Expose
    private Customer customercallplans;

    public ListCallPlan() {
    }

    public Customer getCustomercallplans() {
        return customercallplans;
    }

    public void setCustomercallplans(Customer customercallplans) {
        this.customercallplans = customercallplans;
    }
}
