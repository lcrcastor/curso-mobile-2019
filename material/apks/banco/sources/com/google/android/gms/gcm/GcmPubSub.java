package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.iid.InstanceID;
import java.util.regex.Pattern;

public class GcmPubSub {
    private static GcmPubSub a;
    private static final Pattern c = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
    private InstanceID b;

    private GcmPubSub(Context context) {
        this.b = InstanceID.getInstance(context);
    }

    public static synchronized GcmPubSub getInstance(Context context) {
        GcmPubSub gcmPubSub;
        synchronized (GcmPubSub.class) {
            if (a == null) {
                a = new GcmPubSub(context);
            }
            gcmPubSub = a;
        }
        return gcmPubSub;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void subscribe(String str, String str2, Bundle bundle) {
        if (str == null || str.isEmpty()) {
            String str3 = "Invalid appInstanceToken: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        } else if (str2 == null || !c.matcher(str2).matches()) {
            String str4 = "Invalid topic name: ";
            String valueOf2 = String.valueOf(str2);
            throw new IllegalArgumentException(valueOf2.length() != 0 ? str4.concat(valueOf2) : new String(str4));
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("gcm.topic", str2);
            this.b.getToken(str, str2, bundle);
        }
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void unsubscribe(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("gcm.topic", str2);
        this.b.zzb(str, str2, bundle);
    }
}
