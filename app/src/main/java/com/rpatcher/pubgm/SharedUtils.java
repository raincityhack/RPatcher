package com.rpatcher.pubgm;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {
    private static String NAMA_SHARED = "RIC2210";
    private Context context;
    private SharedPreferences shared;
    public SharedUtils (Context context){
        this.context = context;
        this.shared = context.getSharedPreferences(NAMA_SHARED, Context.MODE_PRIVATE);
    }
    public void initShared(){
        /** Shared ini mengatur saat pertama kali apliaksi di install dan dibuka, diselanjutnya sudah tidak diatur **/
        if (getBoolean("SHARED_FIRST")){
           setBoolean("SHARED_FIRST", false);
           setInt("PUBGM_VERSION", 1); // Otomatis Global dahulu
        }
    }
    /** Memasukan value booelan ke shared **/
    public void setBoolean(String nama_value, boolean result){
        this.shared.edit().putBoolean(nama_value, result).apply();
    }
    /** Mengambil value booelan di shared **/
    public boolean getBoolean(String name_value){
        return this.shared.getBoolean(name_value, true);
    }
    /** Memasukan value int ke shared **/
    public void setInt(String nama_value, int result){
        this.shared.edit().putInt(nama_value, result).apply();
    }
    /** Mengambil value int di shared **/
    public int getInt(String name_value){
        return this.shared.getInt(name_value, 0);
    }
    /** Memasukan value String ke shared **/
    public void setString(String nama_value, String result){
        this.shared.edit().putString(nama_value, result).apply();
    }
    /** Mengambil value String di shared **/
    public String getString(String name_value){
        return this.shared.getString(name_value, "");
    }
}
