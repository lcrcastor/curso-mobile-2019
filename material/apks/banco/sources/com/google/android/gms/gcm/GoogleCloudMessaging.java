package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzc;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    static GoogleCloudMessaging a = null;
    public static int aeP = 5000000;
    public static int aeQ = 6500000;
    public static int aeR = 7000000;
    private static final AtomicInteger f = new AtomicInteger(1);
    final Messenger b = new Messenger(new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message == null || !(message.obj instanceof Intent)) {
                Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
            }
            Intent intent = (Intent) message.obj;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                GoogleCloudMessaging.this.g.add(intent);
                return;
            }
            if (!GoogleCloudMessaging.this.b(intent)) {
                intent.setPackage(GoogleCloudMessaging.this.c.getPackageName());
                GoogleCloudMessaging.this.c.sendBroadcast(intent);
            }
        }
    });
    /* access modifiers changed from: private */
    public Context c;
    private PendingIntent d;
    private Map<String, Handler> e = Collections.synchronizedMap(new HashMap());
    /* access modifiers changed from: private */
    public final BlockingQueue<Intent> g = new LinkedBlockingQueue();

    static String a(Intent intent, String str) {
        if (intent == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String stringExtra = intent.getStringExtra(str);
        if (stringExtra != null) {
            return stringExtra;
        }
        String stringExtra2 = intent.getStringExtra("error");
        if (stringExtra2 != null) {
            throw new IOException(stringExtra2);
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private String a(String str) {
        int indexOf = str.indexOf(64);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return InstanceID.getInstance(this.c).zzbor().zzh("", str, INSTANCE_ID_SCOPE);
    }

    private void a(String str, String str2, long j, int i, Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        String zzde = zzde(this.c);
        if (zzde == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        a(intent);
        intent.setPackage(zzde);
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        intent.putExtra("google.delay", Integer.toString(i));
        intent.putExtra("google.from", a(str));
        if (zzde.contains(".gsf")) {
            Bundle bundle2 = new Bundle();
            for (String str3 : bundle.keySet()) {
                Object obj = bundle.get(str3);
                if (obj instanceof String) {
                    String str4 = "gcm.";
                    String valueOf = String.valueOf(str3);
                    bundle2.putString(valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4), (String) obj);
                }
            }
            bundle2.putString("google.to", str);
            bundle2.putString("google.message_id", str2);
            InstanceID.getInstance(this.c).zzc(INSTANCE_ID_SCOPE, "upstream", bundle2);
            return;
        }
        this.c.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    private String b() {
        String valueOf = String.valueOf("google.rpc");
        String valueOf2 = String.valueOf(String.valueOf(f.getAndIncrement()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    /* access modifiers changed from: private */
    public boolean b(Intent intent) {
        String stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.e.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (a == null) {
                a = new GoogleCloudMessaging();
                a.c = context.getApplicationContext();
            }
            googleCloudMessaging = a;
        }
        return googleCloudMessaging;
    }

    public static String zzde(Context context) {
        return zzc.zzdj(context);
    }

    public static int zzdf(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String zzde = zzde(context);
        if (zzde != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(zzde, 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException unused) {
            }
        }
        return -1;
    }

    /* access modifiers changed from: 0000 */
    @Deprecated
    public Intent a(Bundle bundle) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzdf(this.c) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzde(this.c));
            a(intent);
            intent.putExtra("google.message_id", b());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.b);
            this.c.startService(intent);
            try {
                return (Intent) this.g.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                throw new IOException(e2.getMessage());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public String a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(',');
            sb.append(strArr[i]);
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a() {
        if (this.d != null) {
            this.d.cancel();
            this.d = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(Intent intent) {
        if (this.d == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.d = PendingIntent.getBroadcast(this.c, 0, intent2, 0);
        }
        intent.putExtra("app", this.d);
    }

    public void close() {
        a = null;
        zza.a = null;
        a();
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra != null ? stringExtra : MESSAGE_TYPE_MESSAGE;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized String register(String... strArr) {
        String zzde = zzde(this.c);
        if (zzde == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String a2 = a(strArr);
        Bundle bundle = new Bundle();
        if (zzde.contains(".gsf")) {
            bundle.putString("legacy.sender", a2);
            return InstanceID.getInstance(this.c).getToken(a2, INSTANCE_ID_SCOPE, bundle);
        }
        bundle.putString("sender", a2);
        return a(a(bundle), "registration_id");
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, long j, Bundle bundle) {
        a(str, str2, j, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, Bundle bundle) {
        send(str, str2, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public synchronized void unregister() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        InstanceID.getInstance(this.c).deleteInstanceID();
    }
}
