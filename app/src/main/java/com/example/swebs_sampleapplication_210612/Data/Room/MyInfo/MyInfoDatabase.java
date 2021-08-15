package com.example.swebs_sampleapplication_210612.Data.Room.MyInfo;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {MyInfo.class},
        version = 1
)
public abstract class MyInfoDatabase extends RoomDatabase {
    public abstract MyInfoDao myInfoDao();

    private static MyInfoDatabase INSTANCE;

    public static MyInfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null)
            synchronized (MyInfoDatabase.class) {
                Log.d("Dao", "생성 됨");
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MyInfoDatabase.class, "swebs")
                                   .fallbackToDestructiveMigration()
                                   .build();
            }
        return INSTANCE;
    }
}
