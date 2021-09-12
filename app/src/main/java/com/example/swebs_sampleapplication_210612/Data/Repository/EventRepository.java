package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventOptionModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class EventRepository {
    private final SwebsAPI retroAPI;

    public EventRepository(Application application) {
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<EventListModel> getEventList(String categorySrl, String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputCategorySrl", RequestBody.create(categorySrl, MediaType.parse("text/plane")));
        if (userSrl != null)
            formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventList(formData);
    }

    public Call<EventDetailModel> getEventDetail(String eventSrl, String userSrl) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputEventSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventDetail(formData);
    }

    public Call<LikeApplyModel> pushLike(String eventSrl, String userSrl, String targetTable) {
        HashMap<String, RequestBody> formData = new HashMap<>();
        formData.put("inputTargetSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formData.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formData.put("inputTargetTable", RequestBody.create(targetTable, MediaType.parse("text/plane")));

        return retroAPI.pushLike(formData);
    }

    public Call<MyEventListModel> getMyEventList(String userSrl, String lastIndex, String loadCount) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        if (lastIndex != null)
            formBody.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if (loadCount != null)
            formBody.put("inputListCount", RequestBody.create(loadCount, MediaType.parse("text/plane")));

        return retroAPI.getMyEventList(formBody);
    }

    public Call<List<EventOptionModel>> getEventOption(String eventSrl) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputEventSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventOption(formBody);
    }

    // 이벤트 신청
    public Call<EventApplyModel> pushEventApply(String userSrl, String eventSrl, String name, String phoneNumber, String address1, String address2, String selectOptionSrl) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputAgreement", RequestBody.create("Y", MediaType.parse("text/plane")));
        formBody.put("inputUserSrl", RequestBody.create(userSrl, MediaType.parse("text/plane")));
        formBody.put("inputEventSrl", RequestBody.create(eventSrl, MediaType.parse("text/plane")));
        formBody.put("inputReceiver", RequestBody.create(name, MediaType.parse("text/plane")));
        formBody.put("inputPhoneNumber", RequestBody.create(phoneNumber, MediaType.parse("text/plane")));
        formBody.put("inputAddress1", RequestBody.create(address1, MediaType.parse("text/plane")));
        if (address2 != null && !address2.equals("")) formBody.put("inputAddress2", RequestBody.create(address2, MediaType.parse("text/plane")));
        if (selectOptionSrl != null) formBody.put("inputOptionSrl", RequestBody.create(selectOptionSrl, MediaType.parse("text/plane")));

        return retroAPI.pushEventApply(formBody);
    }

    // 이벤트 신청 정보...
    public Call<EventApplyInfoModel> getEventApplyInfo(String partSrl) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputPartSrl", RequestBody.create(partSrl, MediaType.parse("text/plane")));

        return retroAPI.getEventApplyInfo(formBody);
    }

    // 이벤트 신청 취소
    public Call<Boolean> pushEventApplyDelete(String partSrl) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputPartSrl", RequestBody.create(partSrl, MediaType.parse("text/plane")));

        return retroAPI.pushEventApplyDelete(formBody);
    }
}
