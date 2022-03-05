package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daltonpro.bizzapps.model.database.MonitorHeader;
import com.daltonpro.bizzapps.model.request.upload.MonitorHeaderRequest;

import java.util.List;

@Dao
public interface MonitorHeaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitorHeader(MonitorHeader monitorHeader);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitorHeaderList(List<MonitorHeader> monitorHeaderList);

    @Query("Delete FROM monitor_header")
    void deleteAll();

    @Delete()
    void deleteMonitorHeader(MonitorHeader monitorHeader);

    @Delete()
    void deleteMonitorHeaderList(List<MonitorHeader> monitorHeaderList);

    @Query("SELECT * FROM monitor_header where checkouttime <> ''")
    List<MonitorHeader> getAllList();


    @Query("SELECT * FROM monitor_header where checkouttime <> ''")
    List<MonitorHeaderRequest> getAllListReq();

    @Query("SELECT * FROM Monitor_header WHERE idproject =:idproject AND idcustomer =:idcustomer AND tanggal =:tanggal")
    List<MonitorHeader> getAllListByCode(int idproject, int idcustomer, String tanggal);

    @Query("SELECT * FROM monitor_header WHERE idcustomer =:id AND idcallplan=:idcallplan")
    MonitorHeader getAllListByCustomerID(int id, int idcallplan);

    @Query("SELECT CASE WHEN statusUploaded IS NULL THEN 'N' ELSE statusUploaded END as statusUploaded FROM monitor_header WHERE idcustomer =:id AND idcallplan = :idcallplan")
    String getStatusUploaded(int id, int idcallplan);

    @Query("SELECT * FROM monitor_header WHERE id =:id")
    MonitorHeaderRequest getAllListByMonitorHeaderID(int id);

    @Query("UPDATE monitor_header SET " +
            "latitudeout =:latOut ," +
            "longitudeout =:longOut ," +
            "checkouttime =:checkoutTime " +
            "WHERE idproject =:idproject AND idcustomer =:idcustomer AND tanggal =:tanggal")
    void updateCheckOut(int idproject, int idcustomer, String tanggal, String latOut, String longOut, long checkoutTime);

    @Update
    void updateMonitorHeader(MonitorHeader monitorHeader);

    //untuk upload data diupdate berdasarkan data request ada idcall ada idcustomer karena blm ada balikan response
    @Query("UPDATE monitor_header SET " +
            "statusUploaded =:statusUpload " +
            "WHERE idCustomer=:idCustomer AND idcallplan=:idCallplan AND idproject=:idProject ")
    void updateStatusSyncData(int idCustomer, int idCallplan, int idProject, String statusUpload);


    //untuk checkout diupdate berdasarkan response
    @Query("UPDATE monitor_header SET " +
            "statusUploaded =:statusUpload " +
            "WHERE idmonitoruser=:idMonitorUser")
    void updateStatusSyncByIdMonitorUser(int idMonitorUser, String statusUpload);

}
