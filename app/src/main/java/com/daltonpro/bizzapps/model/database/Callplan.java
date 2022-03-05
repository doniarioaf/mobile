package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "callplan")
public class Callplan {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idcallplan")
    @SerializedName("idcallplan")
    @Expose
    private int idcallplan;

    @ColumnInfo(name = "nama")
    @SerializedName("nama")
    @Expose
    private String nama;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @NonNull
    @ColumnInfo(name = "idproject")
    @SerializedName("idproject")
    @Expose
    private int idproject;

    @ColumnInfo(name = "projectname")
    @SerializedName("projectname")
    @Expose
    private String projectname;

    public Callplan() {
    }

    public int getIdcallplan() {
        return idcallplan;
    }

    public void setIdcallplan(int idcallplan) {
        this.idcallplan = idcallplan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdproject() {
        return idproject;
    }

    public void setIdproject(int idproject) {
        this.idproject = idproject;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
}
