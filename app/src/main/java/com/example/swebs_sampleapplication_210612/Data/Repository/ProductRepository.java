package com.example.swebs_sampleapplication_210612.Data.Repository;

import android.app.Application;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductDetailModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ProductListlModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

public class ProductRepository {
    private final Application application;
    private final SwebsAPI retroAPI;

    public ProductRepository(Application application) {
        this.application = application;
        this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
    }

    public Call<List<ProductListlModel>> getProductList(String categorySrl, String lastIndex, String loadCount) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputCategorySrl", RequestBody.create(categorySrl, MediaType.parse("text/plane")));
        if (lastIndex != null)
            formBody.put("inputLastIndex", RequestBody.create(lastIndex, MediaType.parse("text/plane")));
        if (loadCount != null)
            formBody.put("inputListCount", RequestBody.create(loadCount, MediaType.parse("text/plane")));

       return retroAPI.getCertifiedList(formBody);
    }

    public Call<ProductDetailModel> getProductDetail(String productSrl) {
        HashMap<String, RequestBody> formBody = new HashMap<>();
        formBody.put("inputProductSrl", RequestBody.create(productSrl, MediaType.parse("text/plane")));

        return retroAPI.getCertifiedDetail(formBody);
    }
}
