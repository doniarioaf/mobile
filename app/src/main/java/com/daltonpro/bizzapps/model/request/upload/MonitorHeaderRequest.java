package com.daltonpro.bizzapps.model.request.upload;

import androidx.room.Ignore;

import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MonitorHeaderRequest {
    @SerializedName("id")
    private int id;

    @SerializedName("idproject")
    private int idproject;

    @SerializedName("idcustomer")
    private int idcustomer;

    @SerializedName("idcallplan")
    private int idcallplan;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("chekintime")
    private String checkintime;

    @SerializedName("chekouttime")
    private String checkouttime;

    @SerializedName("latitudein")
    private double latitudein;

    @SerializedName("latitudeout")
    private double latitudeout;

    @SerializedName("longitudein")
    private double longitudein;

    @SerializedName("longitudeout")
    private double longitudeout;

    @Ignore
    @SerializedName("infodetails")
    List<MonitorDetail> monitorDetailList;

    public MonitorHeaderRequest() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getCheckintime() {
        return checkintime;
    }

    public void setCheckintime(String checkintime) {
        this.checkintime = checkintime;
    }

    public String getCheckouttime() {
        return checkouttime;
    }

    public void setCheckouttime(String checkouttime) {
        this.checkouttime = checkouttime;
    }

    public double getLatitudein() {
        return latitudein;
    }

    public void setLatitudein(double latitudein) {
        this.latitudein = latitudein;
    }

    public double getLatitudeout() {
        return latitudeout;
    }

    public void setLatitudeout(double latitudeout) {
        this.latitudeout = latitudeout;
    }

    public double getLongitudein() {
        return longitudein;
    }

    public void setLongitudein(double longitudein) {
        this.longitudein = longitudein;
    }

    public double getLongitudeout() {
        return longitudeout;
    }

    public void setLongitudeout(double longitudeout) {
        this.longitudeout = longitudeout;
    }

    public List<MonitorDetail> getMonitorDetailList() {
        return monitorDetailList;
    }

    public void setMonitorDetailList(List<MonitorDetail> monitorDetailList) {
        this.monitorDetailList = monitorDetailList;
    }

    public int getIdcallplan() {
        return idcallplan;
    }

    public void setIdcallplan(int idcallplan) {
        this.idcallplan = idcallplan;
    }

}
