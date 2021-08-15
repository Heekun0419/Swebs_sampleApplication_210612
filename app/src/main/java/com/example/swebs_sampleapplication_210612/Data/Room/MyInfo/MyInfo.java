package com.example.swebs_sampleapplication_210612.Data.Room.MyInfo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myinfo")
public class MyInfo {
    public MyInfo (String key, String value) {
        this.key = key;
        this.value = value;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "key")
    private String key;

    @NonNull
    @ColumnInfo(name = "value")
    private String value;


    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }

    @NonNull
    public String getValue() {
        return value;
    }

    public void setValue(@NonNull String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyInfo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
