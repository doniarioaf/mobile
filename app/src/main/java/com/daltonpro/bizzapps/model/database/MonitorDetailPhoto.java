package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

@Entity(tableName = "monitor_detail_photo", primaryKeys = {"monitor_header_id", "idinfophoto"})

public class MonitorDetailPhoto {
    @NonNull
    @ColumnInfo(name = "monitor_header_id")
    @SerializedName("idusermonitor")
    @Expose
    private int monitor_header_id;

    @NonNull
    @ColumnInfo(name = "idinfophoto")
    @SerializedName("idinfophoto")
    @Expose
    private int idinfophoto;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo1")
    @Expose
    private byte[] photo1;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo2")
    @Expose
    private byte[] photo2;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo3")
    @Expose
    private byte[] photo3;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo4")
    @Expose
    private byte[] photo4;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo5")
    @Expose
    private byte[] photo5;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo6")
    @Expose
    private byte[] photo6;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo7")
    @Expose
    private byte[] photo7;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("photo8")
    @Expose
    private byte[] photo8;

    //Additional Variable  Add/Photo
    @Ignore
    private String typePhoto;


    public MonitorDetailPhoto() {
    }


    public int getMonitor_header_id() {
        return monitor_header_id;
    }

    public void setMonitor_header_id(int monitor_header_id) {
        this.monitor_header_id = monitor_header_id;
    }


    public int getIdinfophoto() {
        return idinfophoto;
    }

    public void setIdinfophoto(int idinfophoto) {
        this.idinfophoto = idinfophoto;
    }

    @NonNull
    public byte[] getPhoto1() {
        return photo1;
    }

    public void setPhoto1(@NonNull byte[] photo1) {
        this.photo1 = photo1;
    }

    @NonNull
    public byte[] getPhoto2() {
        return photo2;
    }

    public void setPhoto2(@NonNull byte[] photo2) {
        this.photo2 = photo2;
    }

    @NonNull
    public byte[] getPhoto3() {
        return photo3;
    }

    public void setPhoto3(@NonNull byte[] photo3) {
        this.photo3 = photo3;
    }

    @NonNull
    public byte[] getPhoto4() {
        return photo4;
    }

    public void setPhoto4(@NonNull byte[] photo4) {
        this.photo4 = photo4;
    }

    @NonNull
    public byte[] getPhoto5() {
        return photo5;
    }

    public void setPhoto5(@NonNull byte[] photo5) {
        this.photo5 = photo5;
    }

    @NonNull
    public byte[] getPhoto6() {
        return photo6;
    }

    public void setPhoto6(@NonNull byte[] photo6) {
        this.photo6 = photo6;
    }

    @NonNull
    public byte[] getPhoto7() {
        return photo7;
    }

    public void setPhoto7(@NonNull byte[] photo7) {
        this.photo7 = photo7;
    }

    @NonNull
    public byte[] getPhoto8() {
        return photo8;
    }

    public void setPhoto8(@NonNull byte[] photo8) {
        this.photo8 = photo8;
    }

    public String getTypePhoto() {
        return typePhoto;
    }

    public void setTypePhoto(String typePhoto) {
        this.typePhoto = typePhoto;
    }
}
