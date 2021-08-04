package com.example.swebs_sampleapplication_210612.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SPmanager {

    Context context;

    public SPmanager(Context context){this.context = context;}

    public void setUserType(int userType){
        //1일때 GUEST ,2일때 NORMAL, 3일떄 COMPANY
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USERTYPE, Context.MODE_PRIVATE)).edit();
        editor.putInt(SP_data.USERTYPE,userType);
        editor.apply();
    }

    public void removeUserType(){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.USERTYPE, Context.MODE_PRIVATE)).edit();
        editor.remove(SP_data.USERTYPE);
        editor.apply();
    }
    public int getUserType(){
        return (context.getSharedPreferences(SP_data.USERTYPE, Context.MODE_PRIVATE)).getInt(SP_data.USERTYPE,1);
    }

    public void setPermissionIsChecked(boolean checked){
        SharedPreferences.Editor editor = (context.getSharedPreferences(SP_data.PERMISSION, Context.MODE_PRIVATE)).edit();
        editor.putBoolean(SP_data.PERMISSION,checked);
        editor.apply();
    }
    public boolean getPermission(){
        return (context.getSharedPreferences(SP_data.PERMISSION, Context.MODE_PRIVATE)).getBoolean(SP_data.PERMISSION,false);
    }

}
