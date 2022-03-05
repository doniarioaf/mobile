package com.daltonpro.bizzapps.model.request.upload;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MonitorDetailPhotoUpload {
    @SerializedName("idusermonitor")
    private int idusermonitor;

    @SerializedName("photo1")
    private String photo1;

    @SerializedName("photo2")
    private String photo2;

    @SerializedName("photo3")
    private String photo3;

    @SerializedName("photo4")
    private String photo4;

    @SerializedName("photo5")
    private String photo5;

    @SerializedName("photo6")
    private String photo6;

    @SerializedName("photo7")
    private String photo7;

    @SerializedName("photo8")
    private String photo8;

    @SerializedName("tophoto")
    private long[] tophoto;

    public MonitorDetailPhotoUpload() {
    }


    public int getIdusermonitor() {
        return idusermonitor;
    }

    public void setIdusermonitor(int idusermonitor) {
        this.idusermonitor = idusermonitor;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getPhoto5() {
        return photo5;
    }

    public void setPhoto5(String photo5) {
        this.photo5 = photo5;
    }

    public String getPhoto6() {
        return photo6;
    }

    public void setPhoto6(String photo6) {
        this.photo6 = photo6;
    }

    public String getPhoto7() {
        return photo7;
    }

    public void setPhoto7(String photo7) {
        this.photo7 = photo7;
    }

    public String getPhoto8() {
        return photo8;
    }

    public void setPhoto8(String photo8) {
        this.photo8 = photo8;
    }

    public long[] getTophoto() {
        return tophoto;
    }

    public void setTophoto(long[] tophoto) {
        this.tophoto = tophoto;
    }
}
