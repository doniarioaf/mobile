package com.daltonpro.bizzapps.model.response.download.customer;

import com.daltonpro.bizzapps.model.response.download.callplan.DownloadResponseCallplanData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadResponseCustomer {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("messagecode")
    @Expose
    private String messagecode;

    @SerializedName("httpcode")
    @Expose
    private int httpcode;

    @SerializedName("data")
    @Expose
    private DownloadResponseCustomerData data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessagecode() {
        return messagecode;
    }

    public void setMessagecode(String messagecode) {
        this.messagecode = messagecode;
    }

    public int getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(int httpcode) {
        this.httpcode = httpcode;
    }

    public DownloadResponseCustomerData getData() {
        return data;
    }

    public void setData(DownloadResponseCustomerData data) {
        this.data = data;
    }
}