package com.rsking175453.com.sgh_try1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by user on 15/08/2018.
 */

public class premissions {

    Context c;

    premissions(Context c){
        this.c = c;
    }

    public void permissionmethod(){
        int Permission_All = 1;

        String[] Permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.MEDIA_CONTENT_CONTROL,
                android.Manifest.permission.CAMERA, };
        if(!hasPermissions(c, Permissions)){
            ActivityCompat.requestPermissions((Activity) c, Permissions, Permission_All);
        }

    }

    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }
}
