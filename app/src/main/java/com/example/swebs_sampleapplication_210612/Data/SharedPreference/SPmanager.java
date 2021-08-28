package com.example.swebs_sampleapplication_210612.Data.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SPmanager {

    Context context;

    public SPmanager(Context context){this.context = context;}


    // START - UserSrl
    public void setUserSrl(String userSrl){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_SRL, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_SRL, userSrl);
        editor.apply();
    }
    public void removeUserSrl(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_SRL, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_SRL);
        editor.apply();
    }

    public String getUserSrl(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_SRL, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_SRL,"notFound");
    }
    // END - UserSrl


    // START - Token
    public void setUserToken(String token){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_TOKEN, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_TOKEN,token);
        editor.apply();
    }
    public void removeUserToken(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_TOKEN, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_TOKEN);
        editor.apply();
    }

    public String getUserToken(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_TOKEN, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_TOKEN,"notFound");
    }
    // END - Token


    // START - User ReferralCode
    public void setUserReferralCode(String userSrl){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_REFERRAL_CODE, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_REFERRAL_CODE, userSrl);
        editor.apply();
    }
    public void removeUserReferralCode(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_REFERRAL_CODE, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_REFERRAL_CODE);
        editor.apply();
    }

    public String getUserReferralCode(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_REFERRAL_CODE, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_REFERRAL_CODE,"notFound");
    }
    // END - User ReferralCode

    // START - User Type
    public void setUserType(String type){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USERTYPE, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USERTYPE,type);
        editor.apply();
    }

    public void removeUserType(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USERTYPE, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USERTYPE);
        editor.apply();
    }

    public String getUserType(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USERTYPE, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USERTYPE,"notFound");
    }
    // END - User Type


    public void setScanTutorialExit(boolean exit){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.SCAN_TUTORIAL, Context.MODE_PRIVATE)).edit();
        editor.putBoolean(SP_data.SCAN_TUTORIAL,exit);
        editor.apply();
    }

    public void removeScanTutorialExit(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.SCAN_TUTORIAL, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.SCAN_TUTORIAL);
        editor.apply();
    }

    public boolean getScanTutorialExit(){
        return (context.getSharedPreferences(SP_data.SCAN_TUTORIAL, Context.MODE_PRIVATE))
                .getBoolean(SP_data.SCAN_TUTORIAL,false);
    }


    public void setMyTutorialExit(boolean exit){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.MY_TUTORIAL, Context.MODE_PRIVATE)).edit();
        editor.putBoolean(SP_data.MY_TUTORIAL,exit);
        editor.apply();
    }

    public void removeMyTutorialExit(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.MY_TUTORIAL, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.MY_TUTORIAL);
        editor.apply();
    }

    public boolean getMyTutorialExit(){
        return (context.getSharedPreferences(SP_data.MY_TUTORIAL, Context.MODE_PRIVATE))
                .getBoolean(SP_data.MY_TUTORIAL,false);
    }

    public void setIntroPage(boolean exit){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.INTRO_PAGE, Context.MODE_PRIVATE)).edit();
        editor.putBoolean(SP_data.INTRO_PAGE,exit);
        editor.apply();
    }

    public void removeIntroPage(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.INTRO_PAGE, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.INTRO_PAGE);
        editor.apply();
    }

    public boolean getIntroPage(){
        return (context.getSharedPreferences(SP_data.INTRO_PAGE, Context.MODE_PRIVATE))
                .getBoolean(SP_data.INTRO_PAGE,false);
    }


}
