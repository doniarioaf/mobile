package com.daltonpro.bizzapps.model.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "info_header")
public class InfoHeader {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = "question")
    @SerializedName("question")
    @Expose
    private String question;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

    @ColumnInfo(name = "sequence")
    @SerializedName("sequence")
    @Expose
    private int sequence;

    @NonNull
    @ColumnInfo(name = "idcustomertype")
    @SerializedName("idcustomertype")
    @Expose
    private int idcustomertype;

    @ColumnInfo(name = "customertypename")
    @SerializedName("customertypename")
    @Expose
    private String customertypename;

    @NonNull
    @ColumnInfo(name = "idproject")
    @SerializedName("idproject")
    @Expose
    private int idproject;

    @ColumnInfo(name = "projectname")
    @SerializedName("projectname")
    @Expose
    private String projectname;


    //additional Data
    @Ignore
    List<InfoDetail> detailList;

    @Ignore
    boolean isInputed =false;

    public InfoHeader() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<InfoDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<InfoDetail> detailList) {
        this.detailList = detailList;
    }

    public boolean isInputed() {
        return isInputed;
    }

    public void setInputed(boolean inputed) {
        isInputed = inputed;
    }

    public int getIdcustomertype() {
        return idcustomertype;
    }

    public void setIdcustomertype(int idcustomertype) {
        this.idcustomertype = idcustomertype;
    }

    public String getCustomertypename() {
        return customertypename;
    }

    public void setCustomertypename(String customertypename) {
        this.customertypename = customertypename;
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
