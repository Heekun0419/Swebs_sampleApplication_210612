package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.swebs_sampleapplication_210612.Activity.Login_Signup.MakeSNSAccountActivity;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventAddressModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LoginModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.Entity.MyInfo;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDao;
import com.example.swebs_sampleapplication_210612.Data.Room.Swebs.SwebsDatabase;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.UserConfigModify;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class MyInfoRepository {
    public SwebsDao swebsDao;
    private final SwebsAPI retroAPI;
    LiveData<List<MyInfo>> mMyInfoAll;

    public MyInfoRepository(Application application) {
        SwebsDatabase swebsDatabase = SwebsDatabase.getDatabase(application);
        this.swebsDao = swebsDatabase.swebsDao();
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);

        mMyInfoAll = this.swebsDao.getAllLiveDataForMyInfo();
    }

    // 일반 회원 로그인
    public Call<LoginModel> getLoginForNormal(String email, String password) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputEmail", RequestBody.create(email, MediaType.parse("text/plane")));
        formData.put("inputPassword", RequestBody.create(password, MediaType.parse("text/plane")));

        return retroAPI.loginNormalUser(formData);
    }

    // 소셜 회원 로그인
    public Call<LoginModel> getLoginForSocial(String userType, String uid) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserType", RequestBody.create(userType, MediaType.parse("text/plane")));
        formData.put("inputUid", RequestBody.create(uid, MediaType.parse("text/plane")));

        return retroAPI.loginSocialUser(formData);
    }

    // 소셜 회원 가입
    public Call<LoginModel> getSignupForSocial(String userSrl, String email, String nickname, String uid, String userType, String receiveReferralCode) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formData.put("inputEmail", RequestBody.create(email, MediaType.parse("text/plane")));
        formData.put("inputNickname", RequestBody.create(nickname, MediaType.parse("text/plane")));
        formData.put("inputUid", RequestBody.create(uid, MediaType.parse("text/plane")));
        formData.put("inputUserType", RequestBody.create(userType, MediaType.parse("text/plane")));
        if (receiveReferralCode != null)
            formData.put("inputReceiveReferralCode", RequestBody.create(receiveReferralCode, MediaType.parse("text/plane")));

        return retroAPI.socialSignup(formData);
    }

    // 일반 회원 정보 수정 전체...
    public Call<UserConfigModify> pushUserConfigModify(String userSrl, String password, String phoneNumber, String name, String nickname, String birthday, String gender, String country, String region, String profilePath) {
        MultipartBody.Part multipartBody = null;
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (password != null)
            formData.put("inputPassword", RequestBody.create(password, MediaType.parse("text/plane")));
        formData.put("inputPhoneNumber", RequestBody.create(phoneNumber, MediaType.parse("text/plane")));
        formData.put("inputName", RequestBody.create(name, MediaType.parse("text/plane")));
        formData.put("inputNickName", RequestBody.create(nickname, MediaType.parse("text/plane")));
        formData.put("inputBirthday", RequestBody.create(birthday, MediaType.parse("text/plane")));
        formData.put("inputGender", RequestBody.create(gender, MediaType.parse("text/plane")));
        formData.put("inputCountry", RequestBody.create(country, MediaType.parse("text/plane")));
        if (region != null)
            formData.put("inputRegion", RequestBody.create(region, MediaType.parse("text/plane")));

        // 파일
        if (profilePath != null) {
            File file = new File(profilePath);
            multipartBody = MultipartBody.Part.createFormData(
                    "files",
                    file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
            );
        }

        return retroAPI.normalUserConfigModify(multipartBody, formData);
    }

    // 게스트 회원 정보 수정...
    public Call<Boolean> pushGuestConfigModify(String userSrl, String nickname, String birthday, String gender, String country) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (nickname != null)
            formData.put("inputNickName", RequestBody.create(nickname, MediaType.parse("text/plane")));
        if (birthday != null)
            formData.put("inputBirthday", RequestBody.create(birthday, MediaType.parse("text/plane")));
        if (gender != null)
            formData.put("inputGender", RequestBody.create(gender, MediaType.parse("text/plane")));
        if (country != null)
            formData.put("inputCountry", RequestBody.create(country, MediaType.parse("text/plane")));

        return retroAPI.guestUserConfigModify(formData);
    }

    // 이벤트 정보 수정..
    public Call<Boolean> pushAddressModify(String userSrl, String name, String phoneNumber, String address1, String address2) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (name != null)
            formData.put("inputName", RequestBody.create(name, MediaType.parse("text/plane")));
        if (phoneNumber != null)
            formData.put("inputPhone", RequestBody.create(phoneNumber, MediaType.parse("text/plane")));
        if (address1 != null)
            formData.put("inputAddress1", RequestBody.create(address1, MediaType.parse("text/plane")));
        if (address2 != null)
            formData.put("inputAddress2", RequestBody.create(address2, MediaType.parse("text/plane")));

        return retroAPI.pushAddressModify(formData);
    }

    // 이벤트 정보 얻오기.
    public Call<EventAddressModel> getAddressModify(String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

        return retroAPI.getAddressModify(formData);
    }

    public LiveData<List<MyInfo>> getAllToLiveData() {
        return mMyInfoAll;
    }

    public LiveData<String> getValueToLiveData(String key) {
        return swebsDao.getValueLiveDataForMyInfo(key);
    }

    public void insertMyInfo(String key, String value) {
        if (key != null && value != null) {
            MyInfo myInfo = new MyInfo(key, value);
            new insertAsyncTask(swebsDao).execute(myInfo);
        } else {
            Log.d("Dao", key + "(key) " + value + "(value) is null");
        }
    }

    public void deleteAllMyInfo() {
        new deleteAllAsyncTask(swebsDao).execute();
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
                Log.d("Dao", "update");
                mDao.myInfo_update(myInfos[0]);
            }
            return null;
        }
    }

    // Delete All AsyncTask
    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private SwebsDao swebsDao;

        public deleteAllAsyncTask(SwebsDao swebsDao) {
            this.swebsDao = swebsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            swebsDao.deleteAllForMyInfo();
            return null;
        }
    }
}
