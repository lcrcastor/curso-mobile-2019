package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.common.util.zzy;
import com.google.android.gms.internal.zzsh;
import com.google.android.gms.internal.zzsi;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class zze {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zzapk();
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    public static boolean uX = false;
    public static boolean uY = false;
    static boolean uZ = false;
    private static String va = null;
    private static int vb = 0;
    private static boolean vc = false;
    static final AtomicBoolean vd = new AtomicBoolean();
    private static final AtomicBoolean ve = new AtomicBoolean();

    zze() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zzc.zzapd().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.a(i);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        InputStream openInputStream;
        try {
            openInputStream = context.getContentResolver().openInputStream(new Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("raw").appendPath("oss_notice").build());
            String next = new Scanner(openInputStream).useDelimiter("\\A").next();
            if (openInputStream != null) {
                openInputStream.close();
            }
            return next;
        } catch (NoSuchElementException unused) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            return null;
        } catch (Exception unused2) {
            return null;
        } catch (Throwable th) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            throw th;
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    /* JADX INFO: used method not loaded: com.google.android.gms.common.zzf.a(android.content.pm.PackageInfo, com.google.android.gms.common.zzd$zza[]):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0064, code lost:
        if (r7.a(r5, r1) == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        if (r7.a(r5, com.google.android.gms.common.zzd.C0014zzd.a) == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0080, code lost:
        if (com.google.android.gms.common.util.zzl.zzhj(r5.versionCode) >= com.google.android.gms.common.util.zzl.zzhj(GOOGLE_PLAY_SERVICES_VERSION_CODE)) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0082, code lost:
        r0 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
        r1 = r5.versionCode;
        r2 = new java.lang.StringBuilder(77);
        r2.append("Google Play services out of date.  Requires ");
        r2.append(r0);
        r2.append(" but found ");
        r2.append(r1);
        android.util.Log.w("GooglePlayServicesUtil", r2.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a7, code lost:
        return 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a8, code lost:
        r7 = r5.applicationInfo;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00aa, code lost:
        if (r7 != null) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r7 = r0.getApplicationInfo("com.google.android.gms", 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b3, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b4, code lost:
        android.util.Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bb, code lost:
        return 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00be, code lost:
        if (r7.enabled != false) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c1, code lost:
        return 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c2, code lost:
        return 0;
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int isGooglePlayServicesAvailable(android.content.Context r7) {
        /*
            android.content.pm.PackageManager r0 = r7.getPackageManager()
            android.content.res.Resources r1 = r7.getResources()     // Catch:{ Throwable -> 0x000e }
            int r2 = com.google.android.gms.R.string.common_google_play_services_unknown_issue     // Catch:{ Throwable -> 0x000e }
            r1.getString(r2)     // Catch:{ Throwable -> 0x000e }
            goto L_0x0015
        L_0x000e:
            java.lang.String r1 = "GooglePlayServicesUtil"
            java.lang.String r2 = "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included."
            android.util.Log.e(r1, r2)
        L_0x0015:
            java.lang.String r1 = "com.google.android.gms"
            java.lang.String r2 = r7.getPackageName()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x0024
            zzbt(r7)
        L_0x0024:
            boolean r1 = com.google.android.gms.common.util.zzi.zzcl(r7)
            r2 = 1
            r1 = r1 ^ r2
            r3 = 0
            r4 = 9
            if (r1 == 0) goto L_0x0040
            java.lang.String r3 = "com.android.vending"
            r5 = 8256(0x2040, float:1.1569E-41)
            android.content.pm.PackageInfo r3 = r0.getPackageInfo(r3, r5)     // Catch:{ NameNotFoundException -> 0x0038 }
            goto L_0x0040
        L_0x0038:
            java.lang.String r7 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play Store is missing."
        L_0x003c:
            android.util.Log.w(r7, r0)
            return r4
        L_0x0040:
            java.lang.String r5 = "com.google.android.gms"
            r6 = 64
            android.content.pm.PackageInfo r5 = r0.getPackageInfo(r5, r6)     // Catch:{ NameNotFoundException -> 0x00c3 }
            com.google.android.gms.common.zzf r7 = com.google.android.gms.common.zzf.zzbz(r7)
            r6 = 0
            if (r1 == 0) goto L_0x006b
            com.google.android.gms.common.zzd$zza[] r1 = com.google.android.gms.common.zzd.C0014zzd.a
            com.google.android.gms.common.zzd$zza r1 = r7.a(r3, r1)
            if (r1 != 0) goto L_0x005c
            java.lang.String r7 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play Store signature invalid."
            goto L_0x003c
        L_0x005c:
            com.google.android.gms.common.zzd$zza[] r3 = new com.google.android.gms.common.zzd.zza[r2]
            r3[r6] = r1
            com.google.android.gms.common.zzd$zza r7 = r7.a(r5, r3)
            if (r7 != 0) goto L_0x0074
        L_0x0066:
            java.lang.String r7 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play services signature invalid."
            goto L_0x003c
        L_0x006b:
            com.google.android.gms.common.zzd$zza[] r1 = com.google.android.gms.common.zzd.C0014zzd.a
            com.google.android.gms.common.zzd$zza r7 = r7.a(r5, r1)
            if (r7 != 0) goto L_0x0074
            goto L_0x0066
        L_0x0074:
            int r7 = GOOGLE_PLAY_SERVICES_VERSION_CODE
            int r7 = com.google.android.gms.common.util.zzl.zzhj(r7)
            int r1 = r5.versionCode
            int r1 = com.google.android.gms.common.util.zzl.zzhj(r1)
            if (r1 >= r7) goto L_0x00a8
            java.lang.String r7 = "GooglePlayServicesUtil"
            int r0 = GOOGLE_PLAY_SERVICES_VERSION_CODE
            int r1 = r5.versionCode
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 77
            r2.<init>(r3)
            java.lang.String r3 = "Google Play services out of date.  Requires "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = " but found "
            r2.append(r0)
            r2.append(r1)
            java.lang.String r0 = r2.toString()
            android.util.Log.w(r7, r0)
            r7 = 2
            return r7
        L_0x00a8:
            android.content.pm.ApplicationInfo r7 = r5.applicationInfo
            if (r7 != 0) goto L_0x00bc
            java.lang.String r7 = "com.google.android.gms"
            android.content.pm.ApplicationInfo r7 = r0.getApplicationInfo(r7, r6)     // Catch:{ NameNotFoundException -> 0x00b3 }
            goto L_0x00bc
        L_0x00b3:
            r7 = move-exception
            java.lang.String r0 = "GooglePlayServicesUtil"
            java.lang.String r1 = "Google Play services missing when getting application info."
            android.util.Log.wtf(r0, r1, r7)
            return r2
        L_0x00bc:
            boolean r7 = r7.enabled
            if (r7 != 0) goto L_0x00c2
            r7 = 3
            return r7
        L_0x00c2:
            return r6
        L_0x00c3:
            java.lang.String r7 = "GooglePlayServicesUtil"
            java.lang.String r0 = "Google Play services is missing."
            android.util.Log.w(r7, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zze.isGooglePlayServicesAvailable(android.content.Context):int");
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        if (i != 9) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    private static int zzapk() {
        return zzf.BA;
    }

    @Deprecated
    public static boolean zzapl() {
        return "user".equals(Build.TYPE);
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzy.zzb(context, i, str);
    }

    @Deprecated
    public static void zzbc(Context context) {
        int isGooglePlayServicesAvailable = zzc.zzapd().isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            Intent zza = zzc.zzapd().zza(context, isGooglePlayServicesAvailable, "e");
            StringBuilder sb = new StringBuilder(57);
            sb.append("GooglePlayServices not available due to error ");
            sb.append(isGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", sb.toString());
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static int zzbo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (NameNotFoundException unused) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    @Deprecated
    public static void zzbq(Context context) {
        if (!vd.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationIntentService.EXTRA_NOTIFICATION);
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException unused) {
            }
        }
    }

    private static void zzbt(Context context) {
        if (!ve.get()) {
            zzbx(context);
            if (vb == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (vb != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                int i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                int i2 = vb;
                String valueOf = String.valueOf("com.google.android.gms.version");
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 290);
                sb.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
                sb.append(i);
                sb.append(" but found ");
                sb.append(i2);
                sb.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"");
                sb.append(valueOf);
                sb.append("\" android:value=\"@integer/google_play_services_version\" />");
                throw new IllegalStateException(sb.toString());
            }
        }
    }

    public static boolean zzbu(Context context) {
        zzbx(context);
        return uZ;
    }

    public static boolean zzbv(Context context) {
        return zzbu(context) || !zzapl();
    }

    @TargetApi(18)
    public static boolean zzbw(Context context) {
        if (zzs.zzaxq()) {
            Bundle applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName());
            if (applicationRestrictions != null && "true".equals(applicationRestrictions.getString("restricted_profile"))) {
                return true;
            }
        }
        return false;
    }

    private static void zzbx(Context context) {
        if (!vc) {
            zzby(context);
        }
    }

    private static void zzby(Context context) {
        try {
            va = context.getPackageName();
            zzsh zzcr = zzsi.zzcr(context);
            vb = zzaa.zzch(context);
            PackageInfo packageInfo = zzcr.getPackageInfo("com.google.android.gms", 64);
            if (packageInfo != null) {
                if (zzf.zzbz(context).a(packageInfo, C0014zzd.a[1]) != null) {
                    uZ = true;
                    vc = true;
                }
            }
            uZ = false;
        } catch (NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
        } catch (Throwable th) {
            vc = true;
            throw th;
        }
        vc = true;
    }

    @Deprecated
    public static boolean zzd(Context context, int i) {
        if (i == 18) {
            return true;
        }
        if (i == 1) {
            return zzs(context, "com.google.android.gms");
        }
        return false;
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        if (i == 9) {
            return zzs(context, "com.android.vending");
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int i) {
        return zzy.zzf(context, i);
    }

    @Deprecated
    public static Intent zzfm(int i) {
        return zzc.zzapd().zza(null, i, null);
    }

    static boolean zzfn(int i) {
        if (!(i == 18 || i == 42)) {
            switch (i) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @TargetApi(21)
    static boolean zzs(Context context, String str) {
        boolean equals = str.equals("com.google.android.gms");
        boolean z = false;
        if (equals && zzd.zzact()) {
            return false;
        }
        if (zzs.zzaxu()) {
            for (SessionInfo appPackageName : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                if (str.equals(appPackageName.getAppPackageName())) {
                    return true;
                }
            }
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
            if (equals) {
                return applicationInfo.enabled;
            }
            if (applicationInfo.enabled && !zzbw(context)) {
                z = true;
            }
            return z;
        } catch (NameNotFoundException unused) {
        }
    }
}
