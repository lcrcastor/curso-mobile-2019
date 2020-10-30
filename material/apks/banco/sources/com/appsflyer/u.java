package com.appsflyer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.lang.ref.WeakReference;

final class u {

    static class c extends AsyncTask<Void, Void, String> {
        private final WeakReference<Context> a;
        private String b;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return a();
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                String string = AppsFlyerProperties.getInstance().getString("afUninstallToken");
                d dVar = new d(str);
                if (string == null) {
                    u.a((Context) this.a.get(), dVar);
                    return;
                }
                d a2 = d.a(string);
                if (a2.a(dVar)) {
                    u.a((Context) this.a.get(), a2);
                }
            }
        }

        c(WeakReference<Context> weakReference) {
            this.a = weakReference;
        }

        /* access modifiers changed from: protected */
        public final void onPreExecute() {
            super.onPreExecute();
            this.b = AppsFlyerProperties.getInstance().getString("gcmProjectNumber");
        }

        private String a() {
            String str = null;
            try {
                if (this.b != null) {
                    str = u.b(this.a, this.b);
                }
                return str;
            } catch (Throwable th) {
                AFLogger.afErrorLog("Error registering for uninstall feature", th);
                return null;
            }
        }
    }

    u() {
    }

    static boolean a(Context context) {
        return c(context) | b(context);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        if ((r6.getPackageManager().queryIntentServices(r2, 0).size() > 0) != false) goto L_0x004b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(android.content.Context r6) {
        /*
            com.appsflyer.AppsFlyerLib r0 = com.appsflyer.AppsFlyerLib.getInstance()
            boolean r0 = r0.isTrackingStopped()
            r1 = 0
            if (r0 == 0) goto L_0x000c
            return r1
        L_0x000c:
            java.lang.String r0 = "com.google.android.gms.iid.InstanceIDListenerService"
            java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            android.content.Intent r0 = new android.content.Intent     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.String r2 = "com.google.android.gms.iid.InstanceID"
            java.lang.Class<com.appsflyer.GcmInstanceIdListener> r3 = com.appsflyer.GcmInstanceIdListener.class
            r4 = 0
            r0.<init>(r2, r4, r6, r3)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            android.content.Intent r2 = new android.content.Intent     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.String r3 = "com.google.android.gms.iid.InstanceID"
            java.lang.Class<com.google.android.gms.iid.InstanceIDListenerService> r5 = com.google.android.gms.iid.InstanceIDListenerService.class
            r2.<init>(r3, r4, r6, r5)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            android.content.pm.PackageManager r3 = r6.getPackageManager()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.util.List r0 = r3.queryIntentServices(r0, r1)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            int r0 = r0.size()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            r3 = 1
            if (r0 <= 0) goto L_0x0035
            r0 = 1
            goto L_0x0036
        L_0x0035:
            r0 = 0
        L_0x0036:
            if (r0 != 0) goto L_0x004b
            android.content.pm.PackageManager r0 = r6.getPackageManager()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.util.List r0 = r0.queryIntentServices(r2, r1)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            int r0 = r0.size()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            if (r0 <= 0) goto L_0x0048
            r0 = 1
            goto L_0x0049
        L_0x0048:
            r0 = 0
        L_0x0049:
            if (r0 == 0) goto L_0x00a2
        L_0x004b:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.String r2 = "com.google.android.c2dm.intent.RECEIVE"
            java.lang.String r5 = "com.google.android.gms.gcm.GcmReceiver"
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            r0.<init>(r2, r4, r6, r5)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            android.content.pm.PackageManager r2 = r6.getPackageManager()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.util.List r0 = r2.queryBroadcastReceivers(r0, r1)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            int r0 = r0.size()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            if (r0 <= 0) goto L_0x0068
            r0 = 1
            goto L_0x0069
        L_0x0068:
            r0 = 0
        L_0x0069:
            if (r0 == 0) goto L_0x008d
            java.lang.String r0 = r6.getPackageName()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            r2.<init>()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            r2.append(r0)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.String r0 = ".permission.C2D_MESSAGE"
            r2.append(r0)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            java.lang.String r0 = r2.toString()     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            boolean r6 = com.appsflyer.g.a.a(r6, r0)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            if (r6 == 0) goto L_0x0087
            return r3
        L_0x0087:
            java.lang.String r6 = "Cannot verify existence of the app's \"permission.C2D_MESSAGE\" permission in the manifest. Please refer to documentation."
            com.appsflyer.AFLogger.afWarnLog(r6)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            goto L_0x00a2
        L_0x008d:
            java.lang.String r6 = "Cannot verify existence of GcmReceiver receiver in the manifest. Please refer to documentation."
            com.appsflyer.AFLogger.afWarnLog(r6)     // Catch:{ ClassNotFoundException -> 0x009a, Throwable -> 0x0093 }
            goto L_0x00a2
        L_0x0093:
            r6 = move-exception
            java.lang.String r0 = "An error occurred while trying to verify manifest declarations: "
            com.appsflyer.AFLogger.afErrorLog(r0, r6)
            goto L_0x00a2
        L_0x009a:
            r6 = move-exception
            java.lang.String r6 = r6.getMessage()
            com.appsflyer.AFLogger.afRDLog(r6)
        L_0x00a2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appsflyer.u.b(android.content.Context):boolean");
    }

    private static boolean c(Context context) {
        if (AppsFlyerLib.getInstance().isTrackingStopped()) {
            return false;
        }
        try {
            Class.forName("com.google.firebase.iid.FirebaseInstanceIdService");
            Intent intent = new Intent("com.google.firebase.INSTANCE_ID_EVENT", null, context, FirebaseInstanceIdListener.class);
            Intent intent2 = new Intent("com.google.firebase.INSTANCE_ID_EVENT", null, context, FirebaseInstanceIdService.class);
            if (!(context.getPackageManager().queryIntentServices(intent, 0).size() > 0)) {
                if (!(context.getPackageManager().queryIntentServices(intent2, 0).size() > 0)) {
                    AFLogger.afWarnLog("Cannot verify existence of our InstanceID Listener Service in the manifest. Please refer to documentation.");
                    return false;
                }
            }
            return true;
        } catch (ClassNotFoundException unused) {
        } catch (Throwable th) {
            AFLogger.afErrorLog("An error occurred while trying to verify manifest declarations: ", th);
        }
    }

    /* access modifiers changed from: private */
    public static String b(WeakReference<Context> weakReference, String str) {
        try {
            Class cls = Class.forName("com.google.android.gms.iid.InstanceID");
            Class.forName("com.google.android.gms.gcm.GcmReceiver");
            Object invoke = cls.getDeclaredMethod("getInstance", new Class[]{Context.class}).invoke(cls, new Object[]{weakReference.get()});
            String str2 = (String) cls.getDeclaredMethod("getToken", new Class[]{String.class, String.class}).invoke(invoke, new Object[]{str, GoogleCloudMessaging.INSTANCE_ID_SCOPE});
            if (str2 != null) {
                return str2;
            }
            AFLogger.afWarnLog("Couldn't get token using reflection.");
            return null;
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (Throwable th) {
            AFLogger.afErrorLog("Couldn't get token using GoogleCloudMessaging. ", th);
            return null;
        }
    }

    static void a(Context context, d dVar) {
        StringBuilder sb = new StringBuilder("updateServerUninstallToken called with: ");
        sb.append(dVar.toString());
        AFLogger.afInfoLog(sb.toString());
        d a = d.a(AppsFlyerProperties.getInstance().getString("afUninstallToken"));
        if (!context.getSharedPreferences("appsflyer-data", 0).getBoolean("sentRegisterRequestToAF", false) || a.a() == null || !a.a().equals(dVar.a())) {
            AppsFlyerProperties.getInstance().set("afUninstallToken", dVar.toString());
            AppsFlyerLib.getInstance().b(context, dVar.a());
        }
    }
}
