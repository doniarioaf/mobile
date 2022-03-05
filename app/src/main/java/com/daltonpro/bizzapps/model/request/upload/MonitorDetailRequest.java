package com.daltonpro.bizzapps.model.request.upload;

import com.google.gson.annotations.SerializedName;


public class MonitorDetailRequest {
    @SerializedName("infoid")
    private int infoid;

    @SerializedName("idinfodetail")
    private int idinfodetail;

    @SerializedName("infoanswer")
    private String infoanswer;


    public MonitorDetailRequest() {
    }


    public int getInfoid() {
        return infoid;
    }

    public void setInfoid(int infoid) {
        this.infoid = infoid;
    }

    public int getIdinfodetail() {
        return idinfodetail;
    }

    public void setIdinfodetail(int idinfodetail) {
        this.idinfodetail = idinfodetail;
    }

    public String getInfoanswer() {
        return infoanswer;
    }

    public void setInfoanswer(String infoanswer) {
        this.infoanswer = infoanswer;
    }
}
