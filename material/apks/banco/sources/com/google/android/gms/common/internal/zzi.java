package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzsi;

public final class zzi {
    private static final SimpleArrayMap<String, String> a = new SimpleArrayMap<>();

    @Nullable
    private static String a(Context context, String str) {
        synchronized (a) {
            String str2 = (String) a.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", "com.google.android.gms");
            if (identifier == 0) {
                String str3 = "GoogleApiAvailability";
                String str4 = "Missing resource: ";
                String valueOf = String.valueOf(str);
                Log.w(str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4));
                return null;
            }
            String string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                String str5 = "GoogleApiAvailability";
                String str6 = "Got empty resource: ";
                String valueOf2 = String.valueOf(str);
                Log.w(str5, valueOf2.length() != 0 ? str6.concat(valueOf2) : new String(str6));
                return null;
            }
            a.put(str, string);
            return string;
        }
    }

    private static String a(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String a2 = a(context, str);
        if (a2 == null) {
            a2 = resources.getString(R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, a2, new Object[]{str2});
    }

    public static String zzce(Context context) {
        String str = context.getApplicationInfo().name;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String packageName = context.getPackageName();
        context.getApplicationContext().getPackageManager();
        try {
            return zzsi.zzcr(context).zzik(context.getPackageName()).toString();
        } catch (NameNotFoundException unused) {
            return packageName;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0028, code lost:
        android.util.Log.e(r3, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002b, code lost:
        return null;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zzg(android.content.Context r3, int r4) {
        /*
            android.content.res.Resources r0 = r3.getResources()
            r1 = 20
            if (r4 == r1) goto L_0x0095
            r1 = 42
            if (r4 == r1) goto L_0x008e
            r1 = 0
            switch(r4) {
                case 1: goto L_0x0087;
                case 2: goto L_0x008e;
                case 3: goto L_0x0080;
                case 4: goto L_0x007f;
                case 5: goto L_0x0071;
                case 6: goto L_0x007f;
                case 7: goto L_0x0063;
                case 8: goto L_0x005e;
                case 9: goto L_0x0050;
                case 10: goto L_0x004b;
                case 11: goto L_0x0046;
                default: goto L_0x0010;
            }
        L_0x0010:
            switch(r4) {
                case 16: goto L_0x0041;
                case 17: goto L_0x0033;
                case 18: goto L_0x002c;
                default: goto L_0x0013;
            }
        L_0x0013:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r2 = 33
            r0.<init>(r2)
            java.lang.String r2 = "Unexpected error code "
            r0.append(r2)
            r0.append(r4)
            java.lang.String r4 = r0.toString()
        L_0x0028:
            android.util.Log.e(r3, r4)
            return r1
        L_0x002c:
            int r3 = com.google.android.gms.R.string.common_google_play_services_updating_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x0033:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "The specified account could not be signed in."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_sign_in_failed_title"
            java.lang.String r3 = a(r3, r4)
            return r3
        L_0x0041:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "One of the API components you attempted to connect to is not available."
            goto L_0x0028
        L_0x0046:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "The application is not licensed to the user."
            goto L_0x0028
        L_0x004b:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Developer error occurred. Please see logs for detailed information"
            goto L_0x0028
        L_0x0050:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Google Play services is invalid. Cannot recover."
            android.util.Log.e(r3, r4)
            int r3 = com.google.android.gms.R.string.common_google_play_services_unsupported_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x005e:
            java.lang.String r3 = "GoogleApiAvailability"
            java.lang.String r4 = "Internal error occurred. Please see logs for detailed information"
            goto L_0x0028
        L_0x0063:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "Network error occurred. Please retry request later."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_network_error_title"
            java.lang.String r3 = a(r3, r4)
            return r3
        L_0x0071:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "An invalid account was specified when connecting. Please provide a valid account."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_invalid_account_title"
            java.lang.String r3 = a(r3, r4)
            return r3
        L_0x007f:
            return r1
        L_0x0080:
            int r3 = com.google.android.gms.R.string.common_google_play_services_enable_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x0087:
            int r3 = com.google.android.gms.R.string.common_google_play_services_install_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x008e:
            int r3 = com.google.android.gms.R.string.common_google_play_services_update_title
            java.lang.String r3 = r0.getString(r3)
            return r3
        L_0x0095:
            java.lang.String r4 = "GoogleApiAvailability"
            java.lang.String r0 = "The current user profile is restricted and could not use authenticated features."
            android.util.Log.e(r4, r0)
            java.lang.String r4 = "common_google_play_services_restricted_profile_title"
            java.lang.String r3 = a(r3, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzi.zzg(android.content.Context, int):java.lang.String");
    }

    @NonNull
    public static String zzh(Context context, int i) {
        String a2 = i == 6 ? a(context, "common_google_play_services_resolution_required_title") : zzg(context, i);
        return a2 == null ? context.getResources().getString(R.string.common_google_play_services_notification_ticker) : a2;
    }

    @NonNull
    public static String zzi(Context context, int i) {
        Resources resources = context.getResources();
        String zzce = zzce(context);
        if (i == 5) {
            return a(context, "common_google_play_services_invalid_account_text", zzce);
        }
        if (i == 7) {
            return a(context, "common_google_play_services_network_error_text", zzce);
        }
        if (i == 9) {
            return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{zzce});
        } else if (i == 20) {
            return a(context, "common_google_play_services_restricted_profile_text", zzce);
        } else {
            if (i == 42) {
                return resources.getString(R.string.common_google_play_services_wear_update_text);
            }
            switch (i) {
                case 1:
                    if (com.google.android.gms.common.util.zzi.zzb(resources)) {
                        return resources.getString(R.string.common_google_play_services_install_text_tablet, new Object[]{zzce});
                    }
                    return resources.getString(R.string.common_google_play_services_install_text_phone, new Object[]{zzce});
                case 2:
                    return resources.getString(R.string.common_google_play_services_update_text, new Object[]{zzce});
                case 3:
                    return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{zzce});
                default:
                    switch (i) {
                        case 16:
                            return a(context, "common_google_play_services_api_unavailable_text", zzce);
                        case 17:
                            return a(context, "common_google_play_services_sign_in_failed_text", zzce);
                        case 18:
                            return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{zzce});
                        default:
                            return resources.getString(R.string.common_google_play_services_unknown_issue, new Object[]{zzce});
                    }
            }
        }
    }

    @NonNull
    public static String zzj(Context context, int i) {
        return i == 6 ? a(context, "common_google_play_services_resolution_required_text", zzce(context)) : zzi(context, i);
    }

    @NonNull
    public static String zzk(Context context, int i) {
        int i2;
        Resources resources = context.getResources();
        switch (i) {
            case 1:
                i2 = R.string.common_google_play_services_install_button;
                break;
            case 2:
                i2 = R.string.common_google_play_services_update_button;
                break;
            case 3:
                i2 = R.string.common_google_play_services_enable_button;
                break;
            default:
                i2 = 17039370;
                break;
        }
        return resources.getString(i2);
    }
}
