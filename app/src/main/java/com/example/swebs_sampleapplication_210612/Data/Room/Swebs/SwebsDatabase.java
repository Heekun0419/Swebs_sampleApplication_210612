package com.example.swebs_sampleapplication_210612.Data.Room.Swebs;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;

@Database(
        entities = {MyInfo.class},
        version = 1,
        exportSchema = false
)
public abstract class SwebsDatabase extends RoomDatabase {
    public abstract SwebsDao swebsDao();

    private static SwebsDatabase INSTANCE;

    public static SwebsDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (SwebsDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SwebsDatabase.class, "swebs")
                                   .fallbackToDestructiveMigration()
                                   .build();
            }
        return INSTANCE;
    }
}
