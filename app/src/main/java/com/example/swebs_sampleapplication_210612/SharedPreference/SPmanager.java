package com.example.swebs_sampleapplication_210612.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SPmanager {

    Context context;

    public SPmanager(Context context){this.context = context;}

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
                .getString(SP_data.USER_INFO.USERTYPE,"guest");
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
                .getString(SP_data.USER_INFO.USER_NAME,"GUEST");
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
                .getString(SP_data.USER_INFO.USER_BIRTHDAY,"미등록");
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
                .getString(SP_data.USER_INFO.USER_GENDER,"미등록");
    }

    public void setUserRegion(String Region){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USER_INFO.USER_REGION, Context.MODE_PRIVATE)).edit();
        editor.putString(SP_data.USER_INFO.USER_REGION,Region);
        editor.apply();
    }
    public String getUserRegion(){
        return (context.getSharedPreferences(SP_data.USER_INFO.USER_REGION, Context.MODE_PRIVATE))
                .getString(SP_data.USER_INFO.USER_REGION,"");
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
                .getString(SP_data.USER_INFO.USER_EMAIL,"미등록");
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
                .getString(SP_data.USER_INFO.USER_POINT,"0");
    }


}
