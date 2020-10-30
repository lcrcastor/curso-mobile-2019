package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.util.HashSet;
import java.util.Set;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private final Set<String> a = new HashSet();
    private int b;

    class zza extends Thread {
        private final String b;
        private final zzb c;
        private final Bundle d;

        zza(String str, IBinder iBinder, Bundle bundle) {
            super(String.valueOf(str).concat(" GCM Task"));
            this.b = str;
            this.c = com.google.android.gms.gcm.zzb.zza.zzgs(iBinder);
            this.d = bundle;
        }

        public void run() {
            Process.setThreadPriority(10);
            try {
                this.c.zztm(GcmTaskService.this.onRunTask(new TaskParams(this.b, this.d)));
            } catch (RemoteException unused) {
                String str = "GcmTaskService";
                String str2 = "Error reporting result of operation to scheduler for ";
                String valueOf = String.valueOf(this.b);
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            } catch (Throwable th) {
                GcmTaskService.this.a(this.b);
                throw th;
            }
            GcmTaskService.this.a(this.b);
        }
    }

    private void a(int i) {
        synchronized (this.a) {
            this.b = i;
            if (this.a.size() == 0) {
                stopSelf(this.b);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        synchronized (this.a) {
            this.a.remove(str);
            if (this.a.size() == 0) {
                stopSelf(this.b);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    @CallSuper
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            a(i2);
            return 2;
        }
        try {
            intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
            String action = intent.getAction();
            if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                String stringExtra = intent.getStringExtra("tag");
                Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                Bundle bundle = (Bundle) intent.getParcelableExtra("extras");
                if (parcelableExtra != null) {
                    if (parcelableExtra instanceof PendingCallback) {
                        synchronized (this.a) {
                            this.a.add(stringExtra);
                        }
                        new zza(stringExtra, ((PendingCallback) parcelableExtra).getIBinder(), bundle).start();
                    }
                }
                String valueOf = String.valueOf(getPackageName());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47 + String.valueOf(stringExtra).length());
                sb.append(valueOf);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(stringExtra);
                sb.append(": Could not process request, invalid callback.");
                Log.e("GcmTaskService", sb.toString());
                a(i2);
                return 2;
            } else if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                onInitializeTasks();
            } else {
                StringBuilder sb2 = new StringBuilder(String.valueOf(action).length() + 37);
                sb2.append("Unknown action received ");
                sb2.append(action);
                sb2.append(", terminating");
                Log.e("GcmTaskService", sb2.toString());
            }
            a(i2);
            return 2;
        } catch (Throwable th) {
            a(i2);
            throw th;
        }
    }
}
