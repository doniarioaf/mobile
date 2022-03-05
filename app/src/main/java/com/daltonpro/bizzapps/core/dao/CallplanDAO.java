package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daltonpro.bizzapps.model.database.Callplan;

import java.util.List;

@Dao
public interface CallplanDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCallplan(Callplan callplan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCallplanList(List<Callplan> callplanList);

    @Query("Delete FROM callplan")
    void deleteAll();

    @Delete()
    void deleteCallplan(Callplan callplan);

    @Delete()
    void deleteCallplanList(List<Callplan> callplanList);

    @Query("SELECT * FROM callplan")
    List<Callplan> getAllList();

    @Query("SELECT nama FROM callplan")
    List<String> getAllListArrayNama();

    @Query("SELECT * FROM callplan WHERE idcallplan =:id")
    List<Callplan> getAllListByCode(int id);

    @Query("SELECT idproject FROM callplan WHERE idcallplan =:id")
    int getIdProject(int id);

    @Query("UPDATE callplan SET nama =:nama WHERE idcallplan = :id")
    void updateNama(int id, String nama);

}
