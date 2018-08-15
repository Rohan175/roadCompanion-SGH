package com.rsking175453.com.sgh_try1.old;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.security.Provider;

/**
 * Created by user on 11/08/2018.
 */

public class LocationService extends Service
{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
