package com.daltonpro.bizzapps.model.response.download.callplan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadResponseCallplan {

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
    private DownloadResponseCallplanData data;

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

    public DownloadResponseCallplanData getData() {
        return data;
    }

    public void setData(DownloadResponseCallplanData data) {
        this.data = data;
    }
}