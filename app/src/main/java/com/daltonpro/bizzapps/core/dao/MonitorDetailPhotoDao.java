package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daltonpro.bizzapps.model.database.MonitorDetail;
import com.daltonpro.bizzapps.model.database.MonitorDetailPhoto;
import com.daltonpro.bizzapps.model.request.upload.MonitorDetailPhotoUpload;

import java.util.List;

@Dao
public interface MonitorDetailPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitordetailPhoto(MonitorDetailPhoto monitorDetailPhoto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitordetailPhotoList(List<MonitorDetailPhoto> monitorDetailPhotoList);

    @Query("Delete FROM monitor_detail_photo")
    void deleteAll();

    @Query("Delete FROM monitor_detail_photo  WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto")
    void deleteAllByID(int monitor_header_id, int idinfophoto);

    @Query("Delete FROM monitor_detail_photo  WHERE monitor_header_id =:monitor_header_id ")
    void deleteAllByMonitorHeaderID(int monitor_header_id);

    @Delete()
    void deleteMonitordetailPhoto(MonitorDetailPhoto monitorDetailPhoto);

    @Delete()
    void deleteMonitordetailPhotoList(List<MonitorDetailPhoto> monitorDetailPhotoList);

    @Query("SELECT * FROM monitor_detail_photo")
    List<MonitorDetailPhoto> getAllList();

    @Query("select CASE WHEN max(idinfophoto)+1 IS NULL THEN 1 ELSE max(idinfophoto)+1 END as newID from monitor_detail_photo")
    int getIdMax();

    @Query("SELECT * FROM monitor_detail_photo WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto")
    List<MonitorDetailPhoto> getAllListByCode(int monitor_header_id, int idinfophoto);

    @Query("SELECT * FROM monitor_detail_photo WHERE monitor_header_id =:monitor_header_id ")
    List<MonitorDetailPhoto> getAllListByMonitorHeaderID(int monitor_header_id);

    @Query("select distinct monitor_header_id,idinfophoto," +
            "(select photo1 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo1 NOT Null) as photo1," +
            "(select photo2 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo2 NOT Null) as photo2," +
            "(select photo3 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo3 NOT Null) as photo3," +
            "(select photo4 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo4 NOT Null) as photo4," +
            "(select photo5 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo5 NOT Null) as photo5," +
            "(select photo6 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo6 NOT Null) as photo6," +
            "(select photo7 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo7 NOT Null) as photo7," +
            "(select photo8 from monitor_detail_photo where monitor_header_id = :monitor_header_id AND photo8 NOT Null) as photo8" +
            " from monitor_detail_photo where monitor_header_id =:monitor_header_id GROUP by monitor_header_id")
    MonitorDetailPhoto getAllListUploadByMonitorHeaderID(int monitor_header_id);

    @Query("SELECT * FROM monitor_detail_photo WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    MonitorDetailPhoto getMonitorDetailPhotoByMonitorHeaderIDAndInfoHeader(int monitor_header_id, int idinfophoto);

    @Query("select count(monitor_header_id) as totalPhoto from monitor_detail_photo where monitor_header_id =:monitor_header_id")
    int getTotalPhotoByMonitorHeaderID(int monitor_header_id);

    @Query("SELECT * FROM monitor_detail_photo WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    MonitorDetailPhoto getMonitorDetailPhotoByMonitorHeaderIDAndInfoHeaderDetail(int monitor_header_id, int idinfophoto);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo1 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto1(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo2 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto2(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo3 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto3(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo4 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto4(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo5 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto5(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo6 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto6(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo7 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto7(int monitor_header_id, int idinfophoto, byte[] data);

    @Query("UPDATE monitor_detail_photo SET " +
            "photo8 =:data " +
            "WHERE monitor_header_id =:monitor_header_id AND idinfophoto =:idinfophoto ")
    void updatePhoto8(int monitor_header_id, int idinfophoto, byte[] data);

    @Update
    void updateMonitordetail(MonitorDetail monitorDetail);

}
