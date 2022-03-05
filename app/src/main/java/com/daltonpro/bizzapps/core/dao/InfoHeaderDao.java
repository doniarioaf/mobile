package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daltonpro.bizzapps.model.database.InfoHeader;

import java.util.List;

@Dao
public interface InfoHeaderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfoHeader(InfoHeader infoHeader);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfoHeaderList(List<InfoHeader> InfoHeaderList);

    @Query("Delete FROM info_header")
    void deleteAll();

    @Delete()
    void deleteInfoHeader(InfoHeader infoHeader);

    @Delete()
    void deleteInfoHeaderList(List<InfoHeader> InfoHeaderList);

    @Query("SELECT * FROM info_header where idproject = :idproject Order by sequence DESC")
    List<InfoHeader> getAllList(int idproject);

    @Query("SELECT * FROM info_header WHERE id =:id AND idproject = :idproject ")
    List<InfoHeader> getAllListByCode(int id, int idproject);

    @Query("UPDATE info_header SET question =:question WHERE id = :id")
    void updateQuestion(int id, String question);

}
