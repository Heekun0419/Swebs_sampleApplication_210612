package com.example.swebs_sampleapplication_210612.Data.Room.MyInfo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyInfoDao {
    @Query("SELECT * FROM myinfo")
    LiveData<List<MyInfo>> getALL();

    @Query("SELECT value FROM myinfo WHERE `Key` = :inputKey")
    LiveData<String> getValueFromKey(String inputKey);

    @Query("DELETE FROM myinfo")
    void deleteALL();

    @Insert
    void insert(MyInfo myInfo);

    @Update
    void update(MyInfo myInfo);

    @Delete
    void delete(MyInfo myInfo);
}
