package com.google.android.gms.iid;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;

public class InstanceIDListenerService extends Service {
    static String a = "action";
    private static String f = "google.com/iid";
    private static String g = "CMD";
    private static String h = "gcm.googleapis.com/refresh";
    MessengerCompat b = new MessengerCompat((Handler) new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            InstanceIDListenerService.this.a(message, MessengerCompat.zzc(message));
        }
    });
    BroadcastReceiver c = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (Log.isLoggable("InstanceID", 3)) {
                intent.getStringExtra("registration_id");
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
                sb.append("Received GSF callback using dynamic receiver: ");
                sb.append(valueOf);
                Log.d("InstanceID", sb.toString());
            }
            InstanceIDListenerService.this.zzn(intent);
            InstanceIDListenerService.this.a();
        }
    };
    int d;
    int e;

    static void a(Context context) {
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.setPackage(context.getPackageName());
        intent.putExtra(g, "SYNC");
        context.startService(intent);
    }

    static void a(Context context, zzd zzd) {
        zzd.zzbow();
        Intent intent = new Intent("com.google.android.gms.iid.InstanceID");
        intent.putExtra(g, "RST");
        intent.setPackage(context.getPackageName());
        context.startService(intent);
    }

    /* access modifiers changed from: private */
    public void a(Message message, int i) {
        zzc.zzdj(this);
        getPackageManager();
        if (i == zzc.c || i == zzc.b) {
            zzn((Intent) message.obj);
            return;
        }
        int i2 = zzc.b;
        int i3 = zzc.c;
        StringBuilder sb = new StringBuilder(77);
        sb.append("Message from unexpected caller ");
        sb.append(i);
        sb.append(" mine=");
        sb.append(i2);
        sb.append(" appid=");
        sb.append(i3);
        Log.w("InstanceID", sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        synchronized (this) {
            this.d--;
            if (this.d == 0) {
                stopSelf(this.e);
            }
            if (Log.isLoggable("InstanceID", 3)) {
                int i = this.d;
                int i2 = this.e;
                StringBuilder sb = new StringBuilder(28);
                sb.append("Stop ");
                sb.append(i);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(i2);
                Log.d("InstanceID", sb.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        synchronized (this) {
            this.d++;
            if (i > this.e) {
                this.e = i;
            }
        }
    }

    public IBinder onBind(Intent intent) {
        if (intent == null || !"com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
            return null;
        }
        return this.b.getBinder();
    }

    public void onCreate() {
        IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.REGISTRATION");
        intentFilter.addCategory(getPackageName());
        registerReceiver(this.c, intentFilter, "com.google.android.c2dm.permission.RECEIVE", null);
    }

    public void onDestroy() {
        unregisterReceiver(this.c);
    }

    /* JADX INFO: finally extract failed */
    public int onStartCommand(Intent intent, int i, int i2) {
        a(i2);
        if (intent == null) {
            a();
            return 2;
        }
        try {
            if ("com.google.android.gms.iid.InstanceID".equals(intent.getAction())) {
                if (VERSION.SDK_INT <= 18) {
                    Intent intent2 = (Intent) intent.getParcelableExtra("GSF");
                    if (intent2 != null) {
                        startService(intent2);
                        a();
                        return 1;
                    }
                }
                zzn(intent);
            }
            a();
            if (intent.getStringExtra("from") != null) {
                WakefulBroadcastReceiver.completeWakefulIntent(intent);
            }
            return 2;
        } catch (Throwable th) {
            a();
            throw th;
        }
    }

    public void onTokenRefresh() {
    }

    public void zzcb(boolean z) {
        onTokenRefresh();
    }

    public void zzn(Intent intent) {
        InstanceID instanceID;
        String stringExtra = intent.getStringExtra("subtype");
        if (stringExtra == null) {
            instanceID = InstanceID.getInstance(this);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("subtype", stringExtra);
            instanceID = InstanceID.zza(this, bundle);
        }
        String stringExtra2 = intent.getStringExtra(g);
        if (intent.getStringExtra("error") == null && intent.getStringExtra("registration_id") == null) {
            if (Log.isLoggable("InstanceID", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder sb = new StringBuilder(String.valueOf(stringExtra).length() + 18 + String.valueOf(stringExtra2).length() + String.valueOf(valueOf).length());
                sb.append("Service command ");
                sb.append(stringExtra);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(stringExtra2);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(valueOf);
                Log.d("InstanceID", sb.toString());
            }
            if (intent.getStringExtra("unregistered") != null) {
                zzd zzbor = instanceID.zzbor();
                if (stringExtra == null) {
                    stringExtra = "";
                }
                zzbor.zzku(stringExtra);
                instanceID.zzbos().zzv(intent);
            } else if (h.equals(intent.getStringExtra("from"))) {
                instanceID.zzbor().zzku(stringExtra);
                zzcb(false);
            } else {
                if ("RST".equals(stringExtra2)) {
                    instanceID.zzboq();
                } else {
                    if ("RST_FULL".equals(stringExtra2)) {
                        if (!instanceID.zzbor().isEmpty()) {
                            instanceID.zzbor().zzbow();
                        }
                    } else if ("SYNC".equals(stringExtra2)) {
                        instanceID.zzbor().zzku(stringExtra);
                        zzcb(false);
                        return;
                    } else {
                        "PING".equals(stringExtra2);
                    }
                    return;
                }
                zzcb(true);
            }
        } else {
            if (Log.isLoggable("InstanceID", 3)) {
                String str = "InstanceID";
                String str2 = "Register result in service ";
                String valueOf2 = String.valueOf(stringExtra);
                Log.d(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
            }
            instanceID.zzbos().zzv(intent);
        }
    }
}
