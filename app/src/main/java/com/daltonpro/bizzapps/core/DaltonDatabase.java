package com.daltonpro.bizzapps.core;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.daltonpro.bizzapps.core.dao.CallplanDAO;
import com.daltonpro.bizzapps.core.dao.CustomerDao;
import com.daltonpro.bizzapps.core.dao.InfoDetailDao;
import com.daltonpro.bizzapps.core.dao.InfoHeaderDao;
import com.daltonpro.bizzapps.core.dao.MonitorDetailDao;
import com.daltonpro.bizzapps.core.dao.MonitorDetailPhotoDao;
import com.daltonpro.bizzapps.core.dao.MonitorHeaderDao;
import com.daltonpro.bizzapps.model.database.Callplan;
import com.daltonpro.bizzapps.model.database.Customer;
import com.daltonpro.bizzapps.model.database.InfoDetail;
import com.daltonpro.bizzapps.model.database.InfoHeader;
import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.database.MonitorHeader;


@Database(entities = {Customer.class, Callplan.class,
        InfoHeader.class, InfoDetail.class, MonitorHeader.class, MonitorDetail.class, MonitorDetailPhoto.class}, version = 1)
public abstract class DaltonDatabase extends RoomDatabase {
    private static DaltonDatabase daltonDatabase;

    private static String DATABASE_NAME = "daltondb";

    public synchronized static DaltonDatabase getInstance(Context context) {
        if (daltonDatabase == null) {
            daltonDatabase = Room.databaseBuilder(context.getApplicationContext(), DaltonDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return daltonDatabase;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static void setDatabaseName(String databaseName) {
        DATABASE_NAME = databaseName;
    }

    public abstract CallplanDAO callplanDAO();

    public abstract CustomerDao customerDao();

    public abstract InfoDetailDao infoDetailDao();

    public abstract InfoHeaderDao infoHeaderDao();

    public abstract MonitorHeaderDao monitorHeaderDao();

    public abstract MonitorDetailDao monitorDetailDao();

    public abstract MonitorDetailPhotoDao monitorDetailPhotoDao();
}
