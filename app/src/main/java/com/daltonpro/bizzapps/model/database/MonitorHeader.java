package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "monitor_header", indices = {@Index(value =
        {"idproject", "idcustomer", "tanggal"}, unique = true)})
public class MonitorHeader {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    @Expose
    private int id;


    @NonNull
    @ColumnInfo(name = "idproject", defaultValue = "1")
    @SerializedName("idproject")
    @Expose
    private int idproject;

    @NonNull
    @ColumnInfo(name = "idcustomer")
    @SerializedName("idcustomer")
    @Expose
    private int idcustomer;

    @NonNull
    @ColumnInfo(name = "idcallplan")
    @SerializedName("idcallplan")
    @Expose
    private int idcallplan;

    @ColumnInfo(name = "idmonitoruser")
    @SerializedName("idmonitoruser")
    @Expose
    private int idmonitoruser;


    @NonNull
    @ColumnInfo(name = "tanggal")
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    @ColumnInfo(name = "checkintime")
    @SerializedName("checkintime")
    @Expose
    private String checkintime;

    @ColumnInfo(name = "checkouttime", defaultValue = "")
    @SerializedName("checkouttime")
    @Expose
    private String checkouttime;

    @ColumnInfo(name = "latitudein")
    @SerializedName("latitudein")
    @Expose
    private double latitudein;

    @ColumnInfo(name = "latitudeout", defaultValue = "")
    @SerializedName("latitudeout")
    @Expose
    private double latitudeout;

    @ColumnInfo(name = "longitudein")
    @SerializedName("longitudein")
    @Expose
    private double longitudein;

    @ColumnInfo(name = "longitudeout", defaultValue = "")
    @SerializedName("longitudeout")
    @Expose
    private double longitudeout;

    @ColumnInfo(name = "statusUploaded", defaultValue = "")
    @Expose
    private String statusUploaded;

    //additional variable
    @Ignore
    @SerializedName("infodetails")
    List<MonitorDetail> monitorDetailList;

    public MonitorHeader() {
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

    public int getIdmonitoruser() {
        return idmonitoruser;
    }

    public void setIdmonitoruser(int idmonitoruser) {
        this.idmonitoruser = idmonitoruser;
    }

    public String getStatusUploaded() {
        return statusUploaded;
    }

    public void setStatusUploaded(String statusUploaded) {
        this.statusUploaded = statusUploaded;
    }
}
