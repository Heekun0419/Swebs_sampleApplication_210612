package com.example.swebs_sampleapplication_210612.Data.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SPmanager {

    Context context;

    public SPmanager(Context context){this.context = context;}


    //userSrl - START
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
    // userSrl - END

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

    public void setPermissionIsChecked(boolean checked){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.PERMISSION, Context.MODE_PRIVATE)).edit();
        editor.putBoolean(SP_data.PERMISSION,checked);
        editor.apply();
    }
    public boolean getPermission(){
        return (context.getSharedPreferences(SP_data.PERMISSION, Context.MODE_PRIVATE))
                .getBoolean(SP_data.PERMISSION,false);
    }
    public void setUserName(String userName){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_NAME, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_NAME,userName);
        editor.apply();
    }
    public void removeUserName(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_NAME, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_NAME);
        editor.apply();
    }
    public String getUserName(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_NAME, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_NAME,"notFound");
    }

    public void setUserBirth(String birth){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_BIRTHDAY, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_BIRTHDAY,birth);
        editor.apply();
    }
    public void removeUserBirth(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_BIRTHDAY, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_BIRTHDAY);
        editor.apply();
    }
    public String getUserBirth(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_BIRTHDAY, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_BIRTHDAY,"notFound");
    }

    public void setUserGender(String gender){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_GENDER, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_GENDER,gender);
        editor.apply();
    }
    public void removeUserGender(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_GENDER, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_GENDER);
        editor.apply();
    }
    public String getUserGender(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_GENDER, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_GENDER,"notFound");
    }

    public void setUserRegion(String Region){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_REGION, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_REGION,Region);
        editor.apply();
    }
    public String getUserRegion(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_REGION, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_REGION,"notFound");
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_EMAIL, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_EMAIL,email);
        editor.apply();
    }
    public void removeUserInfo(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_EMAIL, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_EMAIL);
        editor.apply();
    }
    public String getUserEmail(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_EMAIL, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_EMAIL,"notFound");
    }

    public void setUserPoint(String email){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_POINT, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_POINT,email);
        editor.apply();
    }
    public void removeUserPoint(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_POINT, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USER_INFO.USER_POINT);
        editor.apply();
    }
    public String getUserPoint(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_POINT, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_POINT,"notFound");
    }
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

}
