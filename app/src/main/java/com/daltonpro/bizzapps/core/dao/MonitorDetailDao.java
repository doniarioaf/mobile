package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daltonpro.bizzapps.model.database.MonitorDetail;

import java.util.List;

@Dao
public interface MonitorDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitordetail(MonitorDetail monitorDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMonitordetailList(List<MonitorDetail> monitorDetailList);

    @Query("Delete FROM monitor_detail")
    void deleteAll();

    @Query("Delete FROM monitor_detail  WHERE monitor_header_id =:monitor_header_id AND infoid =:infoid")
    void deleteAllByID(int monitor_header_id, int infoid);

    @Delete()
    void deleteMonitordetail(MonitorDetail monitorDetail);

    @Delete()
    void deleteMonitordetailList(List<MonitorDetail> monitorDetailList);

    @Query("SELECT * FROM monitor_detail")
    List<MonitorDetail> getAllList();

    @Query("SELECT * FROM monitor_detail WHERE monitor_header_id =:monitor_header_id AND infoid =:infoid AND idinfodetail =:idinfodetail")
    List<MonitorDetail> getAllListByCode(int monitor_header_id, int infoid, int idinfodetail);

    @Query("SELECT * FROM monitor_detail WHERE monitor_header_id =:monitor_header_id AND infoid =:infoid ")
    MonitorDetail getMonitorDetailByMonitorHeaderIDAndInfoHeader(int monitor_header_id, int infoid);


    @Query("SELECT * FROM monitor_detail WHERE monitor_header_id =:monitor_header_id ")
    List<MonitorDetail> getAllListByMonitorHeaderID(int monitor_header_id);

    @Query("select count(monitor_header_id) as totalSurvey from monitor_detail where monitor_header_id =:monitor_header_id")
    int getTotalSurveyByMonitorHeaderID(int monitor_header_id);

    @Query("SELECT * FROM monitor_detail WHERE monitor_header_id =:monitor_header_id AND infoid =:infoID AND idinfodetail =:infoDetailID")
    MonitorDetail getMonitorDetailByMonitorHeaderIDAndInfoHeaderDetail(int monitor_header_id, int infoID, int infoDetailID);

    @Query("UPDATE monitor_detail SET " +
            "infoanswer =:infoAnswer " +
            "WHERE monitor_header_id =:monitor_header_id AND infoid =:infoid AND idinfodetail =:idinfodetail")
    void updateCheckOut(int monitor_header_id, int infoid, int idinfodetail, String infoAnswer);

    @Update
    void updateMonitordetail(MonitorDetail monitorDetail);

}
