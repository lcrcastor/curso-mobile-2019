package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzaj;

@Deprecated
public final class zzqw {
    private static Object a = new Object();
    private static zzqw b;
    private final String c;
    private final String d;
    private final Status e;
    private final String f;
    private final String g;
    private final String h;
    private final boolean i;
    private final boolean j;

    zzqw(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean z = true;
        if (identifier != 0) {
            if (resources.getInteger(identifier) == 0) {
                z = false;
            }
            this.j = !z;
        } else {
            this.j = false;
        }
        this.i = z;
        zzaj zzaj = new zzaj(context);
        this.f = zzaj.getString("firebase_database_url");
        this.h = zzaj.getString("google_storage_bucket");
        this.g = zzaj.getString("gcm_defaultSenderId");
        this.d = zzaj.getString("google_api_key");
        String zzcg = zzaa.zzcg(context);
        if (zzcg == null) {
            zzcg = zzaj.getString("google_app_id");
        }
        if (TextUtils.isEmpty(zzcg)) {
            this.e = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.c = null;
            return;
        }
        this.c = zzcg;
        this.e = Status.vY;
    }

    zzqw(String str, boolean z) {
        this(str, z, null, null, null);
    }

    zzqw(String str, boolean z, String str2, String str3, String str4) {
        this.c = str;
        this.d = null;
        this.e = Status.vY;
        this.i = z;
        this.j = !z;
        this.f = str2;
        this.g = str4;
        this.h = str3;
    }

    private static zzqw b(String str) {
        zzqw zzqw;
        synchronized (a) {
            if (b == null) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Initialize must be called before ");
                sb.append(str);
                sb.append(".");
                throw new IllegalStateException(sb.toString());
            }
            zzqw = b;
        }
        return zzqw;
    }

    public static String zzasl() {
        return b("getGoogleAppId").c;
    }

    public static boolean zzasm() {
        return b("isMeasurementExplicitlyDisabled").j;
    }

    public static Status zzb(Context context, String str, boolean z) {
        zzac.zzb(context, (Object) "Context must not be null.");
        zzac.zzh(str, "App ID must be nonempty.");
        synchronized (a) {
            if (b != null) {
                Status a2 = b.a(str);
                return a2;
            }
            b = new zzqw(str, z);
            Status status = b.e;
            return status;
        }
    }

    public static Status zzcb(Context context) {
        Status status;
        zzac.zzb(context, (Object) "Context must not be null.");
        synchronized (a) {
            if (b == null) {
                b = new zzqw(context);
            }
            status = b.e;
        }
        return status;
    }

    /* access modifiers changed from: 0000 */
    public Status a(String str) {
        if (this.c == null || this.c.equals(str)) {
            return Status.vY;
        }
        String str2 = this.c;
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 97);
        sb.append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '");
        sb.append(str2);
        sb.append("'.");
        return new Status(10, sb.toString());
    }
}
