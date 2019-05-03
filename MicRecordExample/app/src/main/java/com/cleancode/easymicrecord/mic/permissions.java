package com.cleancode.easymicrecord.mic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class permissions {
    static public final String PERM_READ_EXTERNAL = Manifest.permission.READ_EXTERNAL_STORAGE;
    static public final String PERM_WRITE_EXTERNAL = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    static public final String PERM_MIC = Manifest.permission.RECORD_AUDIO;

    static private void getPermission(Activity context, String[] PERMISSION){
        ActivityCompat.requestPermissions(context, PERMISSION, 1);
    }

    static public boolean checkPermission(Activity context, String... PERMISSIONS){
        boolean canGo = true;
        for (String perm : PERMISSIONS){
            if(ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED){
                getPermission(context, PERMISSIONS);
                canGo = false;
                break;
            }
        }

        return canGo;
    }
}
