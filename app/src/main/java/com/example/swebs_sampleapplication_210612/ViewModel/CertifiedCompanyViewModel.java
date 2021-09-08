package com.example.swebs_sampleapplication_210612.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.swebs_sampleapplication_210612.Data.Repository.ProductRepository;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListlModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertifiedCompanyViewModel extends AndroidViewModel {
    private final ProductRepository productRepository;
    private MutableLiveData<ArrayList<ProductListlModel>> liveCompanyModelList = new MutableLiveData<>();
    private ArrayList<ProductListlModel> arrayList = new ArrayList<>();
    private MutableLiveData<ProductDetailModel> liveProductDetail = new MutableLiveData<>();


    // 서버에서 한번에 해당 값 만큼만 불러오기
    private final int LIST_LOAD_COUNT =9999;
    public CertifiedCompanyViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public MutableLiveData<ArrayList<ProductListlModel>> getLiveCompanyModelList() {
        return liveCompanyModelList;
    }

    public void setLiveComapnyModelList(ArrayList<ProductListlModel> liveComapnyModelList) {
        this.liveCompanyModelList.postValue(liveComapnyModelList);
    }

    public MutableLiveData<ProductDetailModel> getLiveProductDetail() {
        return liveProductDetail;
    }

    public void getProductListFromServer(String categorySrl, String LastIndex) {
        productRepository.getProductList(categorySrl, LastIndex, Integer.toString(LIST_LOAD_COUNT))
                .enqueue(new Callback<List<ProductListlModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductListlModel>> call, Response<List<ProductListlModel>> response) {
                        if (response.isSuccessful()
                            && response.body() != null) {
                            arrayList.addAll(response.body());
                            setLiveComapnyModelList(arrayList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductListlModel>> call, Throwable t) {

                    }
                });
    }

    public void getProductDetailFromServer(String productSrl) {
        productRepository.getProductDetail(productSrl)
                .enqueue(new Callback<ProductDetailModel>() {
                    @Override
                    public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                        if (response.isSuccessful()
                            && response.body() != null) {
                            if (response.body().isSuccess()) {
                                liveProductDetail.setValue(response.body());
                            } else {
                                // 데이터 받아오기 실패.
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDetailModel> call, Throwable t) {

                    }
                });
    }
}
