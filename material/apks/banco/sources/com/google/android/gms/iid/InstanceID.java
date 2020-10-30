package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class InstanceID {
    public static final String ERROR_BACKOFF = "RETRY_LATER";
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    static Map<String, InstanceID> a = new HashMap();
    static String f;
    private static zzd g;
    private static zzc h;
    Context b;
    KeyPair c;
    String d = "";
    long e;

    protected InstanceID(Context context, String str, Bundle bundle) {
        this.b = context.getApplicationContext();
        this.d = str;
    }

    static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e2) {
            String valueOf = String.valueOf(e2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38);
            sb.append("Never happens: can't find own package ");
            sb.append(valueOf);
            Log.w("InstanceID", sb.toString());
            return 0;
        }
    }

    static String a(KeyPair keyPair) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA1").digest(keyPair.getPublic().getEncoded());
            digest[0] = (byte) (((digest[0] & Ascii.SI) + 112) & 255);
            return Base64.encodeToString(digest, 0, 8, 11);
        } catch (NoSuchAlgorithmException unused) {
            Log.w("InstanceID", "Unexpected error, device missing required alghorithms");
            return null;
        }
    }

    static String a(byte[] bArr) {
        return Base64.encodeToString(bArr, 11);
    }

    static String b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e2) {
            String valueOf = String.valueOf(e2);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38);
            sb.append("Never happens: can't find own package ");
            sb.append(valueOf);
            Log.w("InstanceID", sb.toString());
            return null;
        }
    }

    public static InstanceID getInstance(Context context) {
        return zza(context, null);
    }

    public static synchronized InstanceID zza(Context context, Bundle bundle) {
        InstanceID instanceID;
        synchronized (InstanceID.class) {
            String string = bundle == null ? "" : bundle.getString("subtype");
            if (string == null) {
                string = "";
            }
            Context applicationContext = context.getApplicationContext();
            if (g == null) {
                g = new zzd(applicationContext);
                h = new zzc(applicationContext);
            }
            f = Integer.toString(a(applicationContext));
            instanceID = (InstanceID) a.get(string);
            if (instanceID == null) {
                instanceID = new InstanceID(applicationContext, string, bundle);
                a.put(string, instanceID);
            }
        }
        return instanceID;
    }

    /* access modifiers changed from: 0000 */
    public KeyPair a() {
        if (this.c == null) {
            this.c = g.zzks(this.d);
        }
        if (this.c == null) {
            this.e = System.currentTimeMillis();
            this.c = g.a(this.d, this.e);
        }
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        String a2 = g.a("appVersion");
        if (a2 == null || !a2.equals(f)) {
            return true;
        }
        String a3 = g.a("lastToken");
        if (a3 == null) {
            return true;
        }
        return (System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong(a3)).longValue() > 604800;
    }

    public void deleteInstanceID() {
        zzb("*", "*", null);
        zzboq();
    }

    public void deleteToken(String str, String str2) {
        zzb(str, str2, null);
    }

    public long getCreationTime() {
        if (this.e == 0) {
            String a2 = g.a(this.d, "cre");
            if (a2 != null) {
                this.e = Long.parseLong(a2);
            }
        }
        return this.e;
    }

    public String getId() {
        return a(a());
    }

    public String getToken(String str, String str2) {
        return getToken(str, str2, null);
    }

    public String getToken(String str, String str2, Bundle bundle) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        boolean z = true;
        String zzh = b() ? null : g.zzh(this.d, str, str2);
        if (zzh != null) {
            return zzh;
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (bundle.getString("ttl") != null) {
            z = false;
        }
        if ("jwt".equals(bundle.getString("type"))) {
            z = false;
        }
        String zzc = zzc(str, str2, bundle);
        if (zzc != null && z) {
            g.zza(this.d, str, str2, zzc, f);
        }
        return zzc;
    }

    public void zzb(String str, String str2, Bundle bundle) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        g.zzi(this.d, str, str2);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("sender", str);
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("subscription", str);
        bundle.putString("delete", "1");
        bundle.putString("X-delete", "1");
        bundle.putString("subtype", "".equals(this.d) ? str : this.d);
        String str3 = "X-subtype";
        if (!"".equals(this.d)) {
            str = this.d;
        }
        bundle.putString(str3, str);
        h.b(h.a(bundle, a()));
    }

    public void zzboq() {
        this.e = 0;
        g.b(this.d);
        this.c = null;
    }

    public zzd zzbor() {
        return g;
    }

    public zzc zzbos() {
        return h;
    }

    public String zzc(String str, String str2, Bundle bundle) {
        if (str2 != null) {
            bundle.putString("scope", str2);
        }
        bundle.putString("sender", str);
        String str3 = "".equals(this.d) ? str : this.d;
        if (!bundle.containsKey("legacy.register")) {
            bundle.putString("subscription", str);
            bundle.putString("subtype", str3);
            bundle.putString("X-subscription", str);
            bundle.putString("X-subtype", str3);
        }
        return h.b(h.a(bundle, a()));
    }
}
