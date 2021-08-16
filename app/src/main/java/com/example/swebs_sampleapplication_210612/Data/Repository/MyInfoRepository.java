package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDao;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDatabase;

import java.util.HashMap;
import java.util.List;

public class MyInfoRepository {
    public SwebsDao swebsDao;
    LiveData<List<MyInfo>> mMyInfoAll;

    public MyInfoRepository(Application application) {
        SwebsDatabase swebsDatabase = SwebsDatabase.getDatabase(application);
        this.swebsDao = swebsDatabase.swebsDao();

        mMyInfoAll = this.swebsDao.getAllLiveDataForMyInfo();
    }

    public LiveData<List<MyInfo>> getAllToLiveData() {
        return mMyInfoAll;
    }

    public LiveData<String> getValueToLiveData(String key) {
        return swebsDao.getValueLiveDataForMyInfo(key);
    }

    public void insertMyInfo(String key, String value) {
        MyInfo myInfo = new MyInfo(key, value);
        new insertAsyncTask(swebsDao).execute(myInfo);
    }

    // Insert AsyncTask
    private static class insertAsyncTask extends AsyncTask<MyInfo, Void, Void> {
        private SwebsDao mDao;

        public insertAsyncTask(SwebsDao swebsDao) {
            this.mDao = swebsDao;
        }

        @Override
        protected Void doInBackground(MyInfo... myInfos) {
            // 해당 값이 있다면.. update 하기..
            if (mDao.getValueForMyInfo(myInfos[0].getKey()) == null) {
                Log.d("Dao", "insert");
                mDao.myInfo_insert(myInfos[0]);
            } else {
                Log.d("Dao", "Update");
                mDao.myInfo_update(myInfos[0]);
            }
            return null;
        }
    }
}
