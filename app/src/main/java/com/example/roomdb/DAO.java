package com.example.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insertUser(User u);

    @Query("select * from user")
    List<User> getListUser();

    @Query("select * from user where name=:username")
    List<User> checkUser(String username);

    @Update
    void updateUser(User u);

    @Delete
    void deleteUser(User u);
    @Query("delete from user")
    void deleteAll();
    @Query("select * from user where name like '%' || :name|| '%'")
    List<User> searchUser(String name);
}
