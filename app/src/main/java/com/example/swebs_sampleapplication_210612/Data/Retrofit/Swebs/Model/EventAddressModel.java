package com.example.swebs_sampleapplication_210612.Data.Retrofit.Swebs.Model;

public class EventAddressModel {
    private boolean success;
    private String reason;
    private String name;
    private String phone_number;
    private String address1;
    private String address2;

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }
}
