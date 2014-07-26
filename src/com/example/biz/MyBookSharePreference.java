package com.example.biz;

import android.content.Context;
import android.content.SharedPreferences;

public class MyBookSharePreference {
	
	public static void save(Context context,String key,Object value){ 
		SharedPreferences sp = context.getSharedPreferences(Const.SFNAME, 0);
		if(value instanceof String){
			sp.edit().putString(key, (String)value).commit();
		}
	}
	
	public static String getStr(Context context,String key){
		SharedPreferences sp = context.getSharedPreferences(Const.SFNAME, 0);
		return sp.getString(key, "");
	}
	public static void clearDate(Context context){
		SharedPreferences sp = context.getSharedPreferences(Const.SFNAME, 0);
		sp.edit().clear().commit();
	}
}
