package com.daltonpro.bizzapps.model.response.download.info;

import com.daltonpro.bizzapps.model.response.download.ListCallPlan;
import com.daltonpro.bizzapps.model.response.download.ListInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponseData {


    @SerializedName("listinfo")
    @Expose
    private List<ListInfo> listinfo;

    @SerializedName("listcallplan")
    @Expose
    private ListCallPlan listcallplan;


    public List<ListInfo> getListinfo() {
        return listinfo;
    }

    public void setListinfo(List<ListInfo> listinfo) {
        this.listinfo = listinfo;
    }

    public ListCallPlan getListcallplan() {
        return listcallplan;
    }

    public void setListcallplan(ListCallPlan listcallplan) {
        this.listcallplan = listcallplan;
    }
}