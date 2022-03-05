package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "monitor_detail", primaryKeys = {"monitor_header_id", "infoid","idinfodetail"})

public class MonitorDetail {
    @NonNull
    @ColumnInfo(name = "monitor_header_id")
    @Expose
    private int monitor_header_id;

    @NonNull
    @ColumnInfo(name = "infoid")
    @SerializedName("infoid")
    @Expose
    private int infoid;

    @NonNull
    @ColumnInfo(name = "idinfodetail")
    @SerializedName("idinfodetail")
    @Expose
    private int idinfodetail;

    @ColumnInfo(name = "infoanswer")
    @SerializedName("infoanswer")
    @Expose
    private String infoanswer;


    public MonitorDetail() {
    }


    public int getMonitor_header_id() {
        return monitor_header_id;
    }

    public void setMonitor_header_id(int monitor_header_id) {
        this.monitor_header_id = monitor_header_id;
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
