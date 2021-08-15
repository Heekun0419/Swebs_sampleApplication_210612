package com.example.swebs_sampleapplication_210612.Data.Room.Swebs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;

import java.util.List;

@Dao
public interface SwebsDao {
    // MyInfo Table Start
    @Query("SELECT * FROM myinfo")
    LiveData<List<MyInfo>> myInfo_getAll();

    @Query("SELECT value FROM myinfo WHERE 'key' = :inputKey")
    LiveData<String> myInfo_getValue(String inputKey);

    @Insert
    void myInfo_insert(MyInfo myInfo);

    @Update
    void myInfo_update(MyInfo myInfo);

    @Delete
    void myInfo_delete(MyInfo myInfo);
    // MyInfo Table End


}
