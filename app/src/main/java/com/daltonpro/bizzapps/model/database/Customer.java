package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "customer", primaryKeys = {"id", "idcallplan"})

public class Customer {

    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @NonNull
    @ColumnInfo(name = "idcallplan")
    @SerializedName("idcallplan")
    @Expose
    private int idcallplan;

    @NonNull
    @ColumnInfo(name = "idcustomertype")
    @SerializedName("idcustomertype")
    @Expose
    private int idcustomertype;

    @NonNull
    @ColumnInfo(name = "nama")
    @SerializedName("nama")
    @Expose
    private String nama;

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    private String address;

    @ColumnInfo(name = "city")
    @SerializedName("city")
    @Expose
    private String city;

    @ColumnInfo(name = "areaname")
    @SerializedName("areaname")
    @Expose
    private String areaname;

    @ColumnInfo(name = "subarename")
    @SerializedName("subarename")
    @Expose
    private String subarename;

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    @Expose
    private double latitude;

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    @Expose
    private double longitude;

    @Ignore
    private String statusUploaded;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcallplan() {
        return idcallplan;
    }

    public void setIdcallplan(int idcallplan) {
        this.idcallplan = idcallplan;
    }

    public int getIdcustomertype() {
        return idcustomertype;
    }

    public void setIdcustomertype(int idcustomertype) {
        this.idcustomertype = idcustomertype;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getSubarename() {
        return subarename;
    }

    public void setSubarename(String subarename) {
        this.subarename = subarename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatusUploaded() {
        return statusUploaded;
    }

    public void setStatusUploaded(String statusUploaded) {
        this.statusUploaded = statusUploaded;
    }
}
