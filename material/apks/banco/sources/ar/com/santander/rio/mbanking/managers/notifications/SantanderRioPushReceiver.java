package ar.com.santander.rio.mbanking.managers.notifications;

import android.content.Context;

public class SantanderRioPushReceiver {
    /* access modifiers changed from: protected */
    public String getGCMIntentServiceClassName(Context context) {
        return PushIntentService.class.getName();
    }
}
