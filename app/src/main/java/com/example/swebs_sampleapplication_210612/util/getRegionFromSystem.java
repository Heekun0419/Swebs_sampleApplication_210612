package com.example.swebs_sampleapplication_210612.util;

import android.content.Context;

import java.util.Locale;

public class getRegionFromSystem {
    String Country;

    public getRegionFromSystem(Context context) {
        Locale locale;
        locale = context.getResources().getConfiguration().getLocales().get(0);
        Country = locale.getCountry();
    }

    public String getCountry() {
        return Country;
    }

    public String getCountryFullName() {
        String CountryFullName;
        if (Country.equals("KR"))
            CountryFullName = "대한민국";
        else if (Country.equals("US"))
            CountryFullName = "United States";
        else
            CountryFullName = "中國";

        return CountryFullName;
    }
}
