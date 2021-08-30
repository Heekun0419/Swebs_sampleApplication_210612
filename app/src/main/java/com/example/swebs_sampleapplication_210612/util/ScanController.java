package com.example.swebs_sampleapplication_210612.util;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model.ScanDataPushModel;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsAPI;
import com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.SwebsClient;
import com.example.swebs_sampleapplication_210612.Data.SharedPreference.SPmanager;
import com.example.swebs_sampleapplication_210612.util.Listener.onScanListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanController {
    private final Application application;

    // 스웹스 화이트 리스트 준비...

    public static ScanDataBuilder scanDataBuilder(Application application) {
        return new ScanDataBuilder(application);
    }

    public ScanController(Application application) {
        this.application = application;
    }


    public static class ScanDataBuilder {
        private final SwebsAPI retroAPI;
        private final Application application;
        private final SPmanager sPmanager;
        private onScanListener scanListener;
        private String scanData, company, code;
        private final String[] whiteListFromSwebs = {
                "swebs.co.kr",
                "swebspro.com",
                "swebs.kr",
                "swebspro.net",
                "swebspro.co.kr",
                "swebspro.kr",
                "sealticker.kr",
                "secutechcert.com"
        };
        private boolean isSwebsUrl;
        private String gpsLatitude, gpsLongitude;
        private List<Address> locationForGps;

        public ScanDataBuilder(Application application) {
            this.retroAPI = SwebsClient.getRetrofitClient().create(SwebsAPI.class);
            this.application = application;
            this.sPmanager = new SPmanager(application.getApplicationContext());
            this.company = null;
            this.code = null;
            this.scanData = null;
            this.isSwebsUrl = false;
            this.gpsLatitude = null;
            this.gpsLongitude = null;
            this.locationForGps = new ArrayList<>();
        }

        public ScanDataBuilder setScanData(String data) {
            this.scanData = data;
            return this;
        }

        public ScanDataBuilder setListener(onScanListener scanListener) {
            this.scanListener = scanListener;
            return this;
        }

        public void progressScanAnalysis() {
            setWhiteListForUrl();
            setLocation();
            if (isSwebsUrl && isUrlForm())
                setInfoFromSwebs();

            // scan 데이터 업로드.
            HashMap<String, RequestBody> formData = new HashMap<>();
            formData.put("inputUserSrl", RequestBody.create(sPmanager.getUserSrl(), MediaType.parse("text/plane")));
            formData.put("inputOsType", RequestBody.create("Android", MediaType.parse("text/plane")));
            if (this.scanData != null && this.company == null && this.code == null)
                formData.put("inputQrData", RequestBody.create(this.scanData, MediaType.parse("text/plane")));
            if (this.company != null)
                formData.put("inputCompany", RequestBody.create(this.company, MediaType.parse("text/plane")));
            if (this.code != null)
                formData.put("inputCode", RequestBody.create(this.code, MediaType.parse("text/plane")));
            if (this.gpsLatitude != null)
                formData.put("inputLocationLatitude", RequestBody.create(this.gpsLatitude, MediaType.parse("text/plane")));
            if (this.gpsLongitude != null)
                formData.put("inputLocationLongitude", RequestBody.create(this.gpsLongitude, MediaType.parse("text/plane")));
            if (this.locationForGps.size() > 0) {
                formData.put("inputLangCode", RequestBody.create(new getRegionFromSystem(application.getApplicationContext()).getCountry(), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getAddressLine(0) != null)
                    formData.put("inputAddressFullName", RequestBody.create(this.locationForGps.get(0).getAddressLine(0), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getAdminArea() != null)
                    formData.put("inputAddressAdminArea", RequestBody.create(this.locationForGps.get(0).getAdminArea(), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getLocality() != null)
                    formData.put("inputAddressLocality", RequestBody.create(this.locationForGps.get(0).getLocality(), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getSubLocality() != null)
                    formData.put("inputAddressSubLocality", RequestBody.create(this.locationForGps.get(0).getSubLocality(), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getThoroughfare() != null)
                    formData.put("inputAddressThoroughfare", RequestBody.create(this.locationForGps.get(0).getThoroughfare(), MediaType.parse("text/plane")));
                if (this.locationForGps.get(0).getFeatureName() != null)
                    formData.put("inputAddressFeatureName", RequestBody.create(this.locationForGps.get(0).getFeatureName(), MediaType.parse("text/plane")));
            }


            Call<ScanDataPushModel> call = retroAPI.pushScanHistoryAllData(formData);
            call.enqueue(new Callback<ScanDataPushModel>() {
                @Override
                public void onResponse(Call<ScanDataPushModel> call, Response<ScanDataPushModel> response) {
                    if (response.isSuccessful()
                    && response.body() != null
                    && response.body().isSuccess()) {
                        if (company != null && code != null)
                            scanListener.onSwebs(isSwebsUrl, response.body().getScanSrl(), company, code);
                        else
                            scanListener.onOther();
                    } else {
                        scanListener.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<ScanDataPushModel> call, Throwable t) {
                    scanListener.onFailed();
                }
            });
        }

        private void setLocation() {
            GpsTracker gpsTracker = new GpsTracker(application.getApplicationContext());
            this.gpsLatitude = Double.toString(gpsTracker.getLatitude());
            this.gpsLongitude = Double.toString(gpsTracker.getLongitude());

            Geocoder geocoder = new Geocoder(application.getApplicationContext());
            try {
                this.locationForGps = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean isUrlForm() {
            try {
                new URL(this.scanData);
            } catch (Exception e) {
                return false;
            }
            return true;
        }


        private void setWhiteListForUrl() {
            for (String whiteUrl : whiteListFromSwebs) {
                if (this.scanData.contains(whiteUrl)) {
                    this.isSwebsUrl = true;
                    break;
                }
            }
        }

        private void setInfoFromSwebs() {
            Pattern pattern = Pattern.compile("certchk/(([^/])*)?");
            Matcher matcher = pattern.matcher(this.scanData);
            if (matcher.find()) {
                this.company = matcher.group(1);
            }

            pattern = Pattern.compile("\\?q=((([^/])?)*)");
            matcher = pattern.matcher(this.scanData);
            if (matcher.find()) {
                this.code = matcher.group(1).substring(0, 14);
            }
        }
    }

}
