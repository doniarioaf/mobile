package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daltonpro.bizzapps.model.database.InfoDetail;

import java.util.List;

@Dao
public interface InfoDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfoDetail(InfoDetail infoDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfoDetailList(List<InfoDetail> InfoDetailList);

    @Query("Delete FROM info_detail")
    void deleteAll();

    @Delete()
    void deleteInfoDetail(InfoDetail infoDetail);

    @Delete()
    void deleteInfoDetailList(List<InfoDetail> InfoDetailList);

    @Query("SELECT * FROM info_detail")
    List<InfoDetail> getAllList();

    @Query("SELECT * FROM info_detail WHERE id =:id")
    List<InfoDetail> getAllListByCode(int id);

    @Query("SELECT * FROM info_detail WHERE id =:id")
    InfoDetail getInfoDetailByCode(int id);

    @Query("SELECT * FROM info_detail WHERE info_header_id =:id")
    List<InfoDetail> getAllListByHeaderID(int id);

    @Query("SELECT id FROM info_detail WHERE info_header_id =:id")
    List<Integer> getListID(int id);

    @Query("SELECT answer FROM info_detail WHERE info_header_id =:id")
    List<String> getListNameByHeaderID(int id);

    @Query("UPDATE info_detail SET answer =:answer WHERE id = :id")
    void updateAnswer(int id, String answer);

}
