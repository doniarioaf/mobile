package com.daltonpro.bizzapps.model.database;

import android.graphics.Bitmap;

public class MonitorDetailPhotoShow {

    private int monitor_header_id;

    private int idinfophoto;

    private int indexPhoto;

    private byte[] dataPhoto;

    private Bitmap dataPhotoBitmap;

    private String typePhotoShow;

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

    public int getIndexPhoto() {
        return indexPhoto;
    }

    public void setIndexPhoto(int indexPhoto) {
        this.indexPhoto = indexPhoto;
    }

    public byte[] getDataPhoto() {
        return dataPhoto;
    }

    public void setDataPhoto(byte[] dataPhoto) {
        this.dataPhoto = dataPhoto;
    }

    public String getTypePhotoShow() {
        return typePhotoShow;
    }

    public void setTypePhotoShow(String typePhotoShow) {
        this.typePhotoShow = typePhotoShow;
    }

    public Bitmap getDataPhotoBitmap() {
        return dataPhotoBitmap;
    }

    public void setDataPhotoBitmap(Bitmap dataPhotoBitmap) {
        this.dataPhotoBitmap = dataPhotoBitmap;
    }
}
