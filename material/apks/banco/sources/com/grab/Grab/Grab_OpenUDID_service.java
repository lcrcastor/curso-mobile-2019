package com.grab.Grab;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

public class Grab_OpenUDID_service extends Service {
    public IBinder onBind(Intent intent) {
        return new Binder() {
            public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
                SharedPreferences sharedPreferences = Grab_OpenUDID_service.this.getSharedPreferences(Grab_OpenUDID_manager.PREFS_NAME, 0);
                parcel2.writeInt(parcel.readInt());
                parcel2.writeString(sharedPreferences.getString(Grab_OpenUDID_manager.PREF_KEY, null));
                return true;
            }
        };
    }
}
