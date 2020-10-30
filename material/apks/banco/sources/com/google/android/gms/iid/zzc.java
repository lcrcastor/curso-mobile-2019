package com.google.android.gms.iid;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.appsflyer.AppsFlyerProperties;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class zzc {
    static String a;
    static int b;
    static int c;
    static int d;
    Context e;
    Map<String, Object> f = new HashMap();
    Messenger g;
    Messenger h;
    MessengerCompat i;
    PendingIntent j;
    long k;
    long l;
    int m;
    int n;
    long o;

    public zzc(Context context) {
        this.e = context;
    }

    private static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzdj(context), 0).versionCode;
        } catch (NameNotFoundException unused) {
            return -1;
        }
    }

    static String a(KeyPair keyPair, String... strArr) {
        String str;
        String str2;
        try {
            byte[] bytes = TextUtils.join("\n", strArr).getBytes("UTF-8");
            try {
                PrivateKey privateKey = keyPair.getPrivate();
                Signature instance = Signature.getInstance(privateKey instanceof RSAPrivateKey ? "SHA256withRSA" : "SHA256withECDSA");
                instance.initSign(privateKey);
                instance.update(bytes);
                return InstanceID.a(instance.sign());
            } catch (GeneralSecurityException e2) {
                e = e2;
                str = "InstanceID/Rpc";
                str2 = "Unable to sign registration request";
                Log.e(str, str2, e);
                return null;
            }
        } catch (UnsupportedEncodingException e3) {
            e = e3;
            str = "InstanceID/Rpc";
            str2 = "Unable to encode string";
            Log.e(str, str2, e);
            return null;
        }
    }

    private void a(Object obj) {
        synchronized (getClass()) {
            for (String str : this.f.keySet()) {
                Object obj2 = this.f.get(str);
                this.f.put(str, obj);
                a(obj2, obj);
            }
        }
    }

    private void a(Object obj, Object obj2) {
        if (obj instanceof ConditionVariable) {
            ((ConditionVariable) obj).open();
        }
        if (obj instanceof Messenger) {
            Messenger messenger = (Messenger) obj;
            Message obtain = Message.obtain();
            obtain.obj = obj2;
            try {
                messenger.send(obtain);
            } catch (RemoteException e2) {
                String valueOf = String.valueOf(e2);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Failed to send response ");
                sb.append(valueOf);
                Log.w("InstanceID/Rpc", sb.toString());
            }
        }
    }

    private void a(String str) {
        if ("com.google.android.gsf".equals(a)) {
            this.m++;
            if (this.m >= 3) {
                if (this.m == 3) {
                    this.n = new Random().nextInt(1000) + 1000;
                }
                this.n *= 2;
                this.o = SystemClock.elapsedRealtime() + ((long) this.n);
                int i2 = this.n;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 31);
                sb.append("Backoff due to ");
                sb.append(str);
                sb.append(" for ");
                sb.append(i2);
                Log.w("InstanceID/Rpc", sb.toString());
            }
        }
    }

    private void a(String str, Object obj) {
        synchronized (getClass()) {
            Object obj2 = this.f.get(str);
            this.f.put(str, obj);
            a(obj2, obj);
        }
    }

    private Intent b(Bundle bundle, KeyPair keyPair) {
        Intent intent;
        ConditionVariable conditionVariable = new ConditionVariable();
        String zzbov = zzbov();
        synchronized (getClass()) {
            this.f.put(zzbov, conditionVariable);
        }
        a(bundle, keyPair, zzbov);
        conditionVariable.block(30000);
        synchronized (getClass()) {
            Object remove = this.f.remove(zzbov);
            if (remove instanceof Intent) {
                intent = (Intent) remove;
            } else if (remove instanceof String) {
                throw new IOException((String) remove);
            } else {
                String valueOf = String.valueOf(remove);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 12);
                sb.append("No response ");
                sb.append(valueOf);
                Log.w("InstanceID/Rpc", sb.toString());
                throw new IOException(InstanceID.ERROR_TIMEOUT);
            }
        }
        return intent;
    }

    public static synchronized String zzbov() {
        String num;
        synchronized (zzc.class) {
            int i2 = d;
            d = i2 + 1;
            num = Integer.toString(i2);
        }
        return num;
    }

    public static String zzdj(Context context) {
        if (a != null) {
            return a;
        }
        b = Process.myUid();
        PackageManager packageManager = context.getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentServices(new Intent("com.google.android.c2dm.intent.REGISTER"), 0)) {
            if (packageManager.checkPermission("com.google.android.c2dm.permission.RECEIVE", resolveInfo.serviceInfo.packageName) == 0) {
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resolveInfo.serviceInfo.packageName, 0);
                    int i2 = applicationInfo.uid;
                    StringBuilder sb = new StringBuilder(17);
                    sb.append("Found ");
                    sb.append(i2);
                    Log.w("InstanceID/Rpc", sb.toString());
                    c = applicationInfo.uid;
                    a = resolveInfo.serviceInfo.packageName;
                    return a;
                } catch (NameNotFoundException unused) {
                    continue;
                }
            } else {
                String valueOf = String.valueOf(resolveInfo.serviceInfo.packageName);
                String valueOf2 = String.valueOf("com.google.android.c2dm.intent.REGISTER");
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 56 + String.valueOf(valueOf2).length());
                sb2.append("Possible malicious package ");
                sb2.append(valueOf);
                sb2.append(" declares ");
                sb2.append(valueOf2);
                sb2.append(" without permission");
                Log.w("InstanceID/Rpc", sb2.toString());
            }
        }
        Log.w("InstanceID/Rpc", "Failed to resolve REGISTER intent, falling back");
        try {
            ApplicationInfo applicationInfo2 = packageManager.getApplicationInfo("com.google.android.gms", 0);
            a = applicationInfo2.packageName;
            c = applicationInfo2.uid;
            return a;
        } catch (NameNotFoundException unused2) {
            try {
                ApplicationInfo applicationInfo3 = packageManager.getApplicationInfo("com.google.android.gsf", 0);
                a = applicationInfo3.packageName;
                c = applicationInfo3.uid;
                return a;
            } catch (NameNotFoundException unused3) {
                Log.w("InstanceID/Rpc", "Both Google Play Services and legacy GSF package are missing");
                return null;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public Intent a(Bundle bundle, KeyPair keyPair) {
        Intent b2 = b(bundle, keyPair);
        return (b2 == null || !b2.hasExtra("google.messenger")) ? b2 : b(bundle, keyPair);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.g == null) {
            zzdj(this.e);
            this.g = new Messenger(new Handler(Looper.getMainLooper()) {
                public void handleMessage(Message message) {
                    zzc.this.zze(message);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(Intent intent) {
        if (this.j == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.j = PendingIntent.getBroadcast(this.e, 0, intent2, 0);
        }
        intent.putExtra("app", this.j);
    }

    /* access modifiers changed from: 0000 */
    public void a(Bundle bundle, KeyPair keyPair, String str) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.o == 0 || elapsedRealtime > this.o) {
            a();
            if (a == null) {
                throw new IOException(InstanceID.ERROR_MISSING_INSTANCEID_SERVICE);
            }
            this.k = SystemClock.elapsedRealtime();
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(a);
            bundle.putString("gmsv", Integer.toString(a(this.e)));
            bundle.putString("osv", Integer.toString(VERSION.SDK_INT));
            bundle.putString("app_ver", Integer.toString(InstanceID.a(this.e)));
            bundle.putString("app_ver_name", InstanceID.b(this.e));
            bundle.putString("cliv", "iid-9683000");
            bundle.putString(AppsFlyerProperties.APP_ID, InstanceID.a(keyPair));
            String a2 = InstanceID.a(keyPair.getPublic().getEncoded());
            bundle.putString("pub2", a2);
            bundle.putString("sig", a(keyPair, this.e.getPackageName(), a2));
            intent.putExtras(bundle);
            a(intent);
            zzb(intent, str);
            return;
        }
        long j2 = this.o - elapsedRealtime;
        int i2 = this.n;
        StringBuilder sb = new StringBuilder(78);
        sb.append("Backoff mode, next request attempt: ");
        sb.append(j2);
        sb.append(" interval: ");
        sb.append(i2);
        Log.w("InstanceID/Rpc", sb.toString());
        throw new IOException(InstanceID.ERROR_BACKOFF);
    }

    /* access modifiers changed from: 0000 */
    public String b(Intent intent) {
        if (intent == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String stringExtra = intent.getStringExtra("registration_id");
        if (stringExtra == null) {
            stringExtra = intent.getStringExtra("unregistered");
        }
        intent.getLongExtra("Retry-After", 0);
        if (stringExtra != null) {
            return stringExtra;
        }
        String stringExtra2 = intent.getStringExtra("error");
        if (stringExtra2 != null) {
            throw new IOException(stringExtra2);
        }
        String valueOf = String.valueOf(intent.getExtras());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 29);
        sb.append("Unexpected response from GCM ");
        sb.append(valueOf);
        Log.w("InstanceID/Rpc", sb.toString(), new Throwable());
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    /* access modifiers changed from: 0000 */
    public void c(Intent intent) {
        String stringExtra = intent.getStringExtra("error");
        if (stringExtra == null) {
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("Unexpected response, no error or registration id ");
            sb.append(valueOf);
            Log.w("InstanceID/Rpc", sb.toString());
            return;
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String str = "InstanceID/Rpc";
            String str2 = "Received InstanceID error ";
            String valueOf2 = String.valueOf(stringExtra);
            Log.d(str, valueOf2.length() != 0 ? str2.concat(valueOf2) : new String(str2));
        }
        String str3 = null;
        if (stringExtra.startsWith("|")) {
            String[] split = stringExtra.split("\\|");
            if (!"ID".equals(split[1])) {
                String str4 = "InstanceID/Rpc";
                String str5 = "Unexpected structured response ";
                String valueOf3 = String.valueOf(stringExtra);
                Log.w(str4, valueOf3.length() != 0 ? str5.concat(valueOf3) : new String(str5));
            }
            if (split.length > 2) {
                String str6 = split[2];
                String str7 = split[3];
                if (str7.startsWith(":")) {
                    str7 = str7.substring(1);
                }
                String str8 = str7;
                str3 = str6;
                stringExtra = str8;
            } else {
                stringExtra = "UNKNOWN";
            }
            intent.putExtra("error", stringExtra);
        }
        if (str3 == null) {
            a((Object) stringExtra);
        } else {
            a(str3, (Object) stringExtra);
        }
        long longExtra = intent.getLongExtra("Retry-After", 0);
        if (longExtra > 0) {
            this.l = SystemClock.elapsedRealtime();
            this.n = ((int) longExtra) * 1000;
            this.o = SystemClock.elapsedRealtime() + ((long) this.n);
            int i2 = this.n;
            StringBuilder sb2 = new StringBuilder(52);
            sb2.append("Explicit request from server to backoff: ");
            sb2.append(i2);
            Log.w("InstanceID/Rpc", sb2.toString());
            return;
        }
        if ("SERVICE_NOT_AVAILABLE".equals(stringExtra) || "AUTHENTICATION_FAILED".equals(stringExtra)) {
            a(stringExtra);
        }
    }

    /* access modifiers changed from: protected */
    public void zzb(Intent intent, String str) {
        this.k = SystemClock.elapsedRealtime();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 5);
        sb.append("|ID|");
        sb.append(str);
        sb.append("|");
        intent.putExtra("kid", sb.toString());
        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 5);
        sb2.append("|ID|");
        sb2.append(str);
        sb2.append("|");
        intent.putExtra("X-kid", sb2.toString());
        boolean equals = "com.google.android.gsf".equals(a);
        String stringExtra = intent.getStringExtra("useGsf");
        if (stringExtra != null) {
            equals = "1".equals(stringExtra);
        }
        if (Log.isLoggable("InstanceID/Rpc", 3)) {
            String valueOf = String.valueOf(intent.getExtras());
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf).length() + 8);
            sb3.append("Sending ");
            sb3.append(valueOf);
            Log.d("InstanceID/Rpc", sb3.toString());
        }
        if (this.h != null) {
            intent.putExtra("google.messenger", this.g);
            Message obtain = Message.obtain();
            obtain.obj = intent;
            try {
                this.h.send(obtain);
                return;
            } catch (RemoteException unused) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        if (equals) {
            Intent intent2 = new Intent("com.google.android.gms.iid.InstanceID");
            intent2.setPackage(this.e.getPackageName());
            intent2.putExtra("GSF", intent);
            this.e.startService(intent2);
            return;
        }
        intent.putExtra("google.messenger", this.g);
        intent.putExtra("messenger2", "1");
        if (this.i != null) {
            Message obtain2 = Message.obtain();
            obtain2.obj = intent;
            try {
                this.i.send(obtain2);
                return;
            } catch (RemoteException unused2) {
                if (Log.isLoggable("InstanceID/Rpc", 3)) {
                    Log.d("InstanceID/Rpc", "Messenger failed, fallback to startService");
                }
            }
        }
        this.e.startService(intent);
    }

    public void zze(Message message) {
        if (message != null) {
            if (message.obj instanceof Intent) {
                Intent intent = (Intent) message.obj;
                intent.setExtrasClassLoader(MessengerCompat.class.getClassLoader());
                if (intent.hasExtra("google.messenger")) {
                    Parcelable parcelableExtra = intent.getParcelableExtra("google.messenger");
                    if (parcelableExtra instanceof MessengerCompat) {
                        this.i = (MessengerCompat) parcelableExtra;
                    }
                    if (parcelableExtra instanceof Messenger) {
                        this.h = (Messenger) parcelableExtra;
                    }
                }
                zzv((Intent) message.obj);
                return;
            }
            Log.w("InstanceID/Rpc", "Dropping invalid message");
        }
    }

    public void zzv(Intent intent) {
        String str;
        if (intent == null) {
            if (Log.isLoggable("InstanceID/Rpc", 3)) {
                Log.d("InstanceID/Rpc", "Unexpected response: null");
            }
            return;
        }
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action) || "com.google.android.gms.iid.InstanceID".equals(action)) {
            String stringExtra = intent.getStringExtra("registration_id");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("unregistered");
            }
            if (stringExtra == null) {
                c(intent);
                return;
            }
            this.k = SystemClock.elapsedRealtime();
            this.o = 0;
            this.m = 0;
            this.n = 0;
            if (Log.isLoggable("InstanceID/Rpc", 3)) {
                String valueOf = String.valueOf(intent.getExtras());
                StringBuilder sb = new StringBuilder(String.valueOf(stringExtra).length() + 16 + String.valueOf(valueOf).length());
                sb.append("AppIDResponse: ");
                sb.append(stringExtra);
                sb.append(UtilsCuentas.SEPARAOR2);
                sb.append(valueOf);
                Log.d("InstanceID/Rpc", sb.toString());
            }
            if (stringExtra.startsWith("|")) {
                String[] split = stringExtra.split("\\|");
                if (!"ID".equals(split[1])) {
                    String str2 = "InstanceID/Rpc";
                    String str3 = "Unexpected structured response ";
                    String valueOf2 = String.valueOf(stringExtra);
                    Log.w(str2, valueOf2.length() != 0 ? str3.concat(valueOf2) : new String(str3));
                }
                str = split[2];
                if (split.length > 4) {
                    if ("SYNC".equals(split[3])) {
                        InstanceIDListenerService.a(this.e);
                    } else if ("RST".equals(split[3])) {
                        InstanceIDListenerService.a(this.e, InstanceID.getInstance(this.e).zzbor());
                        intent.removeExtra("registration_id");
                        a(str, (Object) intent);
                        return;
                    }
                }
                String str4 = split[split.length - 1];
                if (str4.startsWith(":")) {
                    str4 = str4.substring(1);
                }
                intent.putExtra("registration_id", str4);
            } else {
                str = null;
            }
            if (str == null) {
                a((Object) intent);
            } else {
                a(str, (Object) intent);
            }
        } else {
            if (Log.isLoggable("InstanceID/Rpc", 3)) {
                String str5 = "InstanceID/Rpc";
                String str6 = "Unexpected response ";
                String valueOf3 = String.valueOf(intent.getAction());
                Log.d(str5, valueOf3.length() != 0 ? str6.concat(valueOf3) : new String(str6));
            }
        }
    }
}
