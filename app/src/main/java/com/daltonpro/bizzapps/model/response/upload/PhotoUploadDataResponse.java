package com.daltonpro.bizzapps.model.response.upload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoUploadDataResponse {

    @SerializedName("idmonitoruser")
    @Expose
    private int idmonitoruser;

    @SerializedName("idproject")
    @Expose
    private int idproject;

    @SerializedName("idcustomer")
    @Expose
    private int idcustomer;

    @SerializedName("tanggal")
    @Expose
    private long tanggal;

    public int getIdmonitoruser() {
        return idmonitoruser;
    }

    public void setIdmonitoruser(int idmonitoruser) {
        this.idmonitoruser = idmonitoruser;
    }

    public int getIdproject() {
        return idproject;
    }

    public void setIdproject(int idproject) {
        this.idproject = idproject;
    }

    public int getIdcustomer() {
        return idcustomer;
    }

    public void setIdcustomer(int idcustomer) {
        this.idcustomer = idcustomer;
    }

    public long getTanggal() {
        return tanggal;
    }

    public void setTanggal(long tanggal) {
        this.tanggal = tanggal;
    }
}