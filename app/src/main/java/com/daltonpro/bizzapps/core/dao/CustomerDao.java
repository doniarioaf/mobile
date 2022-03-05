package com.daltonpro.bizzapps.core.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daltonpro.bizzapps.model.database.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomer(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCustomerList(List<Customer> customerList);

    @Query("Delete FROM customer")
    void deleteAll();

    @Delete()
    void deleteCustomer(Customer customer);

    @Delete()
    void deleteCustomerList(List<Customer> customerList);

    @Query("SELECT * FROM customer")
    List<Customer> getAllList();

    @Query("SELECT * FROM Customer WHERE id =:id AND idcallplan =:idcallplan ")
    List<Customer> getAllListByCode(int id, int idcallplan);

    @Query("SELECT * FROM Customer WHERE id =:id AND idcallplan =:idcallplan ")
    Customer getCustomerByCode(int id, int idcallplan);

    @Query("SELECT * FROM Customer c " +
            " WHERE idcallplan =:id")
    List<Customer> getAllListByIdCallplan(int id);

    @Query("UPDATE Customer SET nama =:nama WHERE id = :id")
    void updateNama(int id, String nama);

}
