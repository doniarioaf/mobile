package com.daltonpro.bizzapps.model.response.download;

import com.daltonpro.bizzapps.model.database.InfoDetail;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListInfo {
    @SerializedName("infoheader")
    @Expose
    private InfoHeader infoheader;

    @SerializedName("detail")
    @Expose
    private List<InfoDetail> detail;

    public ListInfo() {
    }


    public InfoHeader getInfoheader() {
        return infoheader;
    }

    public void setInfoheader(InfoHeader infoheader) {
        this.infoheader = infoheader;
    }

    public List<InfoDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<InfoDetail> detail) {
        this.detail = detail;
    }
}
