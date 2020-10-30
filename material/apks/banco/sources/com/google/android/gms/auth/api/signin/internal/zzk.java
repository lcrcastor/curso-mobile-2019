package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class zzk {
    private static final Lock a = new ReentrantLock();
    private static zzk b;
    private final Lock c = new ReentrantLock();
    private final SharedPreferences d;

    zzk(Context context) {
        this.d = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    private String a(String str, String str2) {
        String valueOf = String.valueOf(":");
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 0 + String.valueOf(valueOf).length() + String.valueOf(str2).length());
        sb.append(str);
        sb.append(valueOf);
        sb.append(str2);
        return sb.toString();
    }

    public static zzk zzbd(Context context) {
        zzac.zzy(context);
        a.lock();
        try {
            if (b == null) {
                b = new zzk(context.getApplicationContext());
            }
            return b;
        } finally {
            a.unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public GoogleSignInAccount a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String zzgc = zzgc(a("googleSignInAccount", str));
        if (zzgc == null) {
            return null;
        }
        try {
            return GoogleSignInAccount.zzfw(zzgc);
        } catch (JSONException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzy(googleSignInAccount);
        zzac.zzy(googleSignInOptions);
        String zzahf = googleSignInAccount.zzahf();
        zzx(a("googleSignInAccount", zzahf), googleSignInAccount.zzahh());
        zzx(a("googleSignInOptions", zzahf), googleSignInOptions.zzahg());
    }

    /* access modifiers changed from: 0000 */
    public GoogleSignInOptions b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String zzgc = zzgc(a("googleSignInOptions", str));
        if (zzgc == null) {
            return null;
        }
        try {
            return GoogleSignInOptions.zzfy(zzgc);
        } catch (JSONException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            zzge(a("googleSignInAccount", str));
            zzge(a("googleSignInOptions", str));
        }
    }

    public GoogleSignInAccount zzaic() {
        return a(zzgc("defaultGoogleSignInAccount"));
    }

    public GoogleSignInOptions zzaid() {
        return b(zzgc("defaultGoogleSignInAccount"));
    }

    public void zzaie() {
        String zzgc = zzgc("defaultGoogleSignInAccount");
        zzge("defaultGoogleSignInAccount");
        c(zzgc);
    }

    public void zzb(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzy(googleSignInAccount);
        zzac.zzy(googleSignInOptions);
        zzx("defaultGoogleSignInAccount", googleSignInAccount.zzahf());
        a(googleSignInAccount, googleSignInOptions);
    }

    /* access modifiers changed from: protected */
    public String zzgc(String str) {
        this.c.lock();
        try {
            return this.d.getString(str, null);
        } finally {
            this.c.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void zzge(String str) {
        this.c.lock();
        try {
            this.d.edit().remove(str).apply();
        } finally {
            this.c.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void zzx(String str, String str2) {
        this.c.lock();
        try {
            this.d.edit().putString(str, str2).apply();
        } finally {
            this.c.unlock();
        }
    }
}
