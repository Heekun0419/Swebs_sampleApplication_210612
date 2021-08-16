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
    Application application;
    SwebsDao swebsDao;
    List<MyInfo> mMyInfoAll;

    HashMap<String, String> mMyInfoAllFormHashMap;

    public MyInfoRepository(Application application) {
        this.application = application;
        SwebsDatabase swebsDatabase = SwebsDatabase.getDatabase(this.application);
        this.swebsDao = swebsDatabase.swebsDao();

        new getAllFromDao(this.swebsDao).execute();
    }

    public void insertMyInfo(String key, String value) {
        MyInfo myInfo = new MyInfo(key, value);
        new insertAsyncTask(swebsDao).execute(myInfo);
    }

    public List<MyInfo> getMyInfo() {
        return mMyInfoAll;
    }


    public String getValue(String key) {
        String returnData;
        Log.d("testtest", "발싸");
        if (mMyInfoAllFormHashMap.get(key) != null)
            returnData = mMyInfoAllFormHashMap.get(key);
        else
            returnData = "NotFoundKey";

        return returnData;
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


    private class getAllFromDao extends AsyncTask<Void, Void, Void> {
        private SwebsDao dao;

        public getAllFromDao(SwebsDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mMyInfoAll = dao.getAllForMyInfo();

            Log.d("testtest", "히힛 : " + mMyInfoAll.size());
            for(int i=0; i<mMyInfoAll.size(); i++) {
                mMyInfoAllFormHashMap.put(mMyInfoAll.get(i).getKey(), mMyInfoAll.get(i).getValue());
            }
            return null;
        }
    }
}
