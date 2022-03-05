package com.daltonpro.bizzapps.modul.monitoring.photo.presenter;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.daltonpro.bizzapps.core.DaltonDatabase;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhotoShow;
import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.modul.monitoring.photo.view.PhotoViewContract;
import com.daltonpro.bizzapps.util.NullEmptyChecker;

import java.util.ArrayList;
import java.util.List;

public class PhotoPresenter implements PhotoPresenterContract {
    private final PhotoViewContract photoViewContract;
    private Context context;
    private DaltonDatabase daltonDatabase;

    public PhotoPresenter(PhotoViewContract photoViewContract, Context context) {
        this.photoViewContract = photoViewContract;
        daltonDatabase = DaltonDatabase.getInstance(context);
    }

    @Override
    public void doGetData(int customerID, int callPlanID) {
        MonitorHeader monitorHeader = daltonDatabase.monitorHeaderDao().getAllListByCustomerID(customerID,callPlanID);
        photoViewContract.getData(monitorHeader);

    }

    @Override
    public void doGetDataDetailPhoto(int monitorHeaderID) {
        //GET DB
        List<MonitorDetailPhoto> monitorDetailPhotoListDB = daltonDatabase.monitorDetailPhotoDao().getAllListByMonitorHeaderID(monitorHeaderID);

        //SETUP DATA ADD DI RECYCLER
        List<MonitorDetailPhoto> monitorDetailPhotoList = new ArrayList<>();
        MonitorDetailPhoto monitorDetailPhoto = new MonitorDetailPhoto();
        monitorDetailPhoto.setMonitor_header_id(monitorHeaderID);
        if (monitorDetailPhotoList.size() > 0) {
            monitorDetailPhoto.setIdinfophoto(-1);
        }
        monitorDetailPhoto.setTypePhoto("");
        monitorDetailPhotoList.add(monitorDetailPhoto);


        for (int i = 0; i < monitorDetailPhotoListDB.size(); i++) {
            monitorDetailPhotoList.add(monitorDetailPhotoListDB.get(i));
        }

        List<MonitorDetailPhotoShow> monitorDetailPhotoShowList = new ArrayList<>();
        for (int i = 0; i < monitorDetailPhotoList.size(); i++) {
            MonitorDetailPhotoShow monitorDetailPhotoShow = new MonitorDetailPhotoShow();
            monitorDetailPhotoShow.setMonitor_header_id(monitorHeaderID);
            monitorDetailPhotoShow.setIdinfophoto(monitorDetailPhotoList.get(i).getIdinfophoto());
            monitorDetailPhotoShow.setIndexPhoto(i);

            byte[] photo = getPhotoByIndex(i, monitorDetailPhotoList.get(i));
            if (NullEmptyChecker.isNotNullOrNotEmpty(photo)) {
                //jika ada isi maka gambar ditampilkan dari db
                monitorDetailPhotoShow.setTypePhotoShow("SHOW");
                monitorDetailPhotoShow.setDataPhoto(photo);
                monitorDetailPhotoShow.setDataPhotoBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            } else {
                //jika kosong maka gambar add
                monitorDetailPhotoShow.setTypePhotoShow("");
            }

            monitorDetailPhotoShowList.add(monitorDetailPhotoShow);
        }


        photoViewContract.getDataDetailPhoto(monitorDetailPhotoShowList);
    }

    @Override
    public void doAddPhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position) {
        photoViewContract.onAddPhoto(monitorDetailPhotoShowList, position);
    }

    @Override
    public void doUpdatePhoto(List<MonitorDetailPhotoShow> monitorDetailPhotoShowList, int position) {
        photoViewContract.onUpdatePhoto(monitorDetailPhotoShowList, position);
    }


    @Override
    public void doNext() {
        photoViewContract.onNext();
    }

    @Override
    public void doReset(int monitorHeaderID) {
        daltonDatabase.monitorDetailPhotoDao().deleteAllByMonitorHeaderID(monitorHeaderID);
        photoViewContract.onReset();
    }


    private byte[] getPhotoByIndex(int i, MonitorDetailPhoto monitorDetailPhoto) {
        byte[] photo;

        switch (i) {
            case 1:
                photo = monitorDetailPhoto.getPhoto1();
                break;
            case 2:
                photo = monitorDetailPhoto.getPhoto2();
                break;
            case 3:
                photo = monitorDetailPhoto.getPhoto3();
                break;
            case 4:
                photo = monitorDetailPhoto.getPhoto4();
                break;
            case 5:
                photo = monitorDetailPhoto.getPhoto5();
                break;
            case 6:
                photo = monitorDetailPhoto.getPhoto6();
                break;
            case 7:
                photo = monitorDetailPhoto.getPhoto7();
                break;
            case 8:
                photo = monitorDetailPhoto.getPhoto8();
                break;
            default:
                photo = monitorDetailPhoto.getPhoto1();
        }

        return photo;
    }
}
