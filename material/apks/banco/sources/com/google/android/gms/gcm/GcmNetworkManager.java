package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzac;
import java.util.Iterator;
import java.util.List;

public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmNetworkManager a;
    private Context b;
    private final PendingIntent c = PendingIntent.getBroadcast(this.b, 0, new Intent(), 0);

    private GcmNetworkManager(Context context) {
        this.b = context;
    }

    private Intent a() {
        String zzde = GoogleCloudMessaging.zzde(this.b);
        int zzdf = zzde != null ? GoogleCloudMessaging.zzdf(this.b) : -1;
        if (zzde == null || zzdf < GoogleCloudMessaging.aeP) {
            StringBuilder sb = new StringBuilder(91);
            sb.append("Google Play Services is not available, dropping GcmNetworkManager request. code=");
            sb.append(zzdf);
            Log.e("GcmNetworkManager", sb.toString());
            return null;
        }
        Intent intent = new Intent("com.google.android.gms.gcm.ACTION_SCHEDULE");
        intent.setPackage(zzde);
        intent.putExtra("app", this.c);
        return intent;
    }

    private void a(ComponentName componentName) {
        b(componentName.getClassName());
        Intent a2 = a();
        if (a2 != null) {
            a2.putExtra("scheduler_action", "CANCEL_ALL");
            a2.putExtra("component", componentName);
            this.b.sendBroadcast(a2);
        }
    }

    static void a(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        } else if (100 < str.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private void a(String str, ComponentName componentName) {
        a(str);
        b(componentName.getClassName());
        Intent a2 = a();
        if (a2 != null) {
            a2.putExtra("scheduler_action", "CANCEL_TASK");
            a2.putExtra("tag", str);
            a2.putExtra("component", componentName);
            this.b.sendBroadcast(a2);
        }
    }

    private void b(String str) {
        zzac.zzb(str, (Object) "GcmTaskService must not be null.");
        Intent intent = new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK);
        intent.setPackage(this.b.getPackageName());
        List queryIntentServices = this.b.getPackageManager().queryIntentServices(intent, 0);
        boolean z = true;
        zzac.zzb((queryIntentServices == null || queryIntentServices.size() == 0) ? false : true, (Object) "There is no GcmTaskService component registered within this package. Have you extended GcmTaskService correctly?");
        Iterator it = queryIntentServices.iterator();
        while (true) {
            if (it.hasNext()) {
                if (((ResolveInfo) it.next()).serviceInfo.name.equals(str)) {
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 119);
        sb.append("The GcmTaskService class you provided ");
        sb.append(str);
        sb.append(" does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY.");
        zzac.zzb(z, (Object) sb.toString());
    }

    public static GcmNetworkManager getInstance(Context context) {
        GcmNetworkManager gcmNetworkManager;
        synchronized (GcmNetworkManager.class) {
            if (a == null) {
                a = new GcmNetworkManager(context.getApplicationContext());
            }
            gcmNetworkManager = a;
        }
        return gcmNetworkManager;
    }

    public void cancelAllTasks(Class<? extends GcmTaskService> cls) {
        zze(cls);
    }

    public void cancelTask(String str, Class<? extends GcmTaskService> cls) {
        zzb(str, cls);
    }

    public void schedule(Task task) {
        b(task.getServiceName());
        Intent a2 = a();
        if (a2 != null) {
            Bundle extras = a2.getExtras();
            extras.putString("scheduler_action", "SCHEDULE_TASK");
            task.toBundle(extras);
            a2.putExtras(extras);
            this.b.sendBroadcast(a2);
        }
    }

    public void zzb(String str, Class<? extends Service> cls) {
        a(str, new ComponentName(this.b, cls));
    }

    public void zze(Class<? extends Service> cls) {
        a(new ComponentName(this.b, cls));
    }
}
