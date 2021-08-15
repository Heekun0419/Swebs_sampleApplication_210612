package com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myinfo")
public class MyInfo {
    @PrimaryKey
    @NonNull
    private String key;

    @NonNull
    private String value;

    public MyInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
