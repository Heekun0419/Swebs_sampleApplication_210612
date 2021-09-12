package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.EventRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventApplyModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailInfoModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventOptionModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.LikeApplyModel;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.MyEventListModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventViewModel extends AndroidViewModel {
    private SPmanager sPmanager;
    private EventRepository eventRepository;

    private final MutableLiveData<ArrayList<EventModel>> liveEventList = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<EventDetailInfoModel> liveEventDetailInfo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isEventCanLike = new MutableLiveData<>();
    private final MutableLiveData<List<EventOptionModel>> liveEventOption = new MutableLiveData<>();
    private final MutableLiveData<String> liveApplyEventPartSrl = new MutableLiveData<>();
    private final MutableLiveData<EventApplyInfoModel> liveEventApplyInfo = new MutableLiveData<>();

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        sPmanager = new SPmanager(application.getApplicationContext());
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<ArrayList<EventModel>> getLiveEventList() {
        return liveEventList;
    }
    public MutableLiveData<EventDetailInfoModel> getLiveEventDetailInfo() {
        return liveEventDetailInfo;
    }
    public MutableLiveData<Boolean> getIsEventCanLike() {
        return isEventCanLike;
    }
    public MutableLiveData<List<EventOptionModel>> getLiveEventOption() {
        return liveEventOption;
    }
    public MutableLiveData<String> getLiveApplyEventPartSrl() {
        return liveApplyEventPartSrl;
    }
    public MutableLiveData<EventApplyInfoModel> getLiveEventApplyInfo() {
        return liveEventApplyInfo;
    }

    public void pushEventLike(String eventSrl) {
        eventRepository.pushLike(eventSrl, sPmanager.getUserSrl(), "event")
                .enqueue(new Callback<LikeApplyModel>() {
                    @Override
                    public void onResponse(Call<LikeApplyModel> call, Response<LikeApplyModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess()) {
                                if (response.body().getState().equals("Insert"))
                                    isEventCanLike.setValue(false);
                                else
                                    isEventCanLike.setValue(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LikeApplyModel> call, Throwable t) {
                    }
                });
    }

    public void getEventDetailFromServer(String eventSrl) {
        isLoading.setValue(true);

        eventRepository.getEventDetail(eventSrl, sPmanager.getUserSrl())
                .enqueue(new Callback<EventDetailModel>() {
                    @Override
                    public void onResponse(Call<EventDetailModel> call, Response<EventDetailModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            liveEventDetailInfo.setValue(response.body().getEvent_info());
                            isEventCanLike.setValue(response.body().getEvent_info().isCan_like());
                        }
                        isLoading.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<EventDetailModel> call, Throwable t) {
                        isLoading.setValue(false);
                    }
                });
    }

    public void getEventListFromServer(String categorySrl, String userSrl) {
        isLoading.setValue(true);
        eventRepository.getEventList(categorySrl, userSrl)
                .enqueue(new Callback<EventListModel>() {
                    @Override
                    public void onResponse(Call<EventListModel> call, Response<EventListModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {

                            ArrayList<EventModel> tempModel = new ArrayList<>();

                            int enableCount = 0;
                            int disableCount = 0;
                            for(EventListDetailModel detailModel : response.body().getEvent()) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date startDate = null, endDate = null, nowDate = null;
                                try {
                                    startDate = simpleDateFormat.parse(detailModel.getStart_date());
                                    endDate = simpleDateFormat.parse(detailModel.getEnd_date());
                                    nowDate = simpleDateFormat.parse(response.body().getNow_date());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (startDate != null && endDate != null && nowDate != null) {
                                    if (nowDate.compareTo(startDate) >= 0
                                            && nowDate.compareTo(endDate) <= 0) {
                                        //
                                        tempModel.add(enableCount++,
                                                new EventModel(
                                                        1
                                                        , detailModel.getCategory_title()
                                                        , detailModel.getEvent_srl()
                                                        , detailModel.getFile_srl()
                                                        , detailModel.getCorp_name()
                                                        , detailModel.getEvent_title()
                                                        , (endDate.getTime() - nowDate.getTime())/86400000 + "일 남음"
                                                )
                                        );
                                    } else {
                                        int statusType;
                                        String statusText;
                                        if (nowDate.compareTo(endDate) <= 0) {
                                            statusType = 2;
                                            statusText = "미 진행";
                                        } else {
                                            statusType = 3;
                                            statusText = "모집 종료";
                                        }
                                        tempModel.add(enableCount+(disableCount++),
                                                new EventModel(
                                                        statusType
                                                        , statusText
                                                        , detailModel.getEvent_srl()
                                                        , detailModel.getFile_srl()
                                                        , detailModel.getCorp_name()
                                                        , detailModel.getEvent_title()
                                                        , simpleDateFormat.format(startDate) + " ~ " + simpleDateFormat.format(endDate)
                                                )
                                        );
                                    }
                                }
                            }

                            liveEventList.setValue(tempModel);
                            isLoading.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<EventListModel> call, Throwable t) {

                    }
                });
    }

    public void getEventOption(String eventSrl) {
        eventRepository.getEventOption(eventSrl)
                .enqueue(new Callback<List<EventOptionModel>>() {
                    @Override
                    public void onResponse(Call<List<EventOptionModel>> call, Response<List<EventOptionModel>> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            liveEventOption.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EventOptionModel>> call, Throwable t) {

                    }
                });
    }

    // 이벤트 신청
    public void pushEventApply(String eventSrl, String name, String phoneNumber, String address1, String address2, String selectOptionSrl) {
        eventRepository.pushEventApply(
                sPmanager.getUserSrl(),
                eventSrl,
                name,
                phoneNumber,
                address1,
                address2,
                selectOptionSrl
        ).enqueue(new Callback<EventApplyModel>() {
            @Override
            public void onResponse(Call<EventApplyModel> call, Response<EventApplyModel> response) {
                if (response.isSuccessful()
                && response.body() != null) {
                    if (response.body().isSuccess()) {
                        liveApplyEventPartSrl.setValue(response.body().getPartSrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<EventApplyModel> call, Throwable t) {

            }
        });

    }

    // 이벤트 신청 정보
    public void getEventApplyInfo(String partSrl) {
        eventRepository.getEventApplyInfo(partSrl)
                .enqueue(new Callback<EventApplyInfoModel>() {
                    @Override
                    public void onResponse(Call<EventApplyInfoModel> call, Response<EventApplyInfoModel> response) {
                        if (response.isSuccessful()
                        && response.body() != null) {
                            if (response.body().isSuccess()) {
                                liveEventApplyInfo.setValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<EventApplyInfoModel> call, Throwable t) {

                    }
                });
    }

}
