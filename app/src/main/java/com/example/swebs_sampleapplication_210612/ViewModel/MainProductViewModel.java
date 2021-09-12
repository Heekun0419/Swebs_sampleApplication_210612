package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.EventRepository;
import com.example.swebs_sampleapplication_210612.Data.Repository.ProductRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.EventListModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListlModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.EventModel;
import com.example.swebs_sampleapplication_210612.ViewModel.Model.ReviewListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainProductViewModel extends AndroidViewModel {

    private ProductRepository productRepository;
    private EventRepository eventRepository;
    private MutableLiveData<List<ProductListlModel>> LiveProductList = new MutableLiveData<>();
    private MutableLiveData<List<EventModel>> LiveEventList = new MutableLiveData<>();

    public MainProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
        eventRepository = new EventRepository(application);
    }

    public void getProductList(String lastIndex) {
        productRepository.getProductList("0", lastIndex, "10").enqueue(new Callback<List<ProductListlModel>>() {
            @Override
            public void onResponse(Call<List<ProductListlModel>> call, Response<List<ProductListlModel>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    setLiveProductList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<ProductListlModel>> call, Throwable t) {
            }
        });
    }



    public MutableLiveData<List<ProductListlModel>> getLiveProductList() {
        return LiveProductList;
    }

    public void setLiveProductList(List<ProductListlModel> liveProductList) {
        LiveProductList.setValue(liveProductList);
    }
}
