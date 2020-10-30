package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import org.json.JSONArray;
import org.json.JSONException;

class zza {
    static zza a;
    private final Context b;

    /* renamed from: com.google.android.gms.gcm.zza$zza reason: collision with other inner class name */
    class C0019zza extends IllegalArgumentException {
    }

    private zza(Context context) {
        this.b = context.getApplicationContext();
    }

    private int a() {
        return (int) SystemClock.uptimeMillis();
    }

    static synchronized zza a(Context context) {
        zza zza;
        synchronized (zza.class) {
            if (a == null) {
                a = new zza(context);
            }
            zza = a;
        }
        return zza;
    }

    static String a(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private String a(String str) {
        return str.substring("gcm.n.".length());
    }

    private void a(String str, Notification notification) {
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager) this.b.getSystemService(NotificationIntentService.EXTRA_NOTIFICATION);
        if (TextUtils.isEmpty(str)) {
            long uptimeMillis = SystemClock.uptimeMillis();
            StringBuilder sb = new StringBuilder(37);
            sb.append("GCM-Notification:");
            sb.append(uptimeMillis);
            str = sb.toString();
        }
        notificationManager.notify(str, 0, notification);
    }

    static boolean a(Bundle bundle) {
        return "1".equals(a(bundle, "gcm.n.e")) || a(bundle, "gcm.n.icon") != null;
    }

    private int b(String str) {
        if (!TextUtils.isEmpty(str)) {
            Resources resources = this.b.getResources();
            int identifier = resources.getIdentifier(str, "drawable", this.b.getPackageName());
            if (identifier != 0) {
                return identifier;
            }
            int identifier2 = resources.getIdentifier(str, "mipmap", this.b.getPackageName());
            if (identifier2 != 0) {
                return identifier2;
            }
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 57);
            sb.append("Icon resource ");
            sb.append(str);
            sb.append(" not found. Notification will use app icon.");
            Log.w("GcmNotification", sb.toString());
        }
        int i = this.b.getApplicationInfo().icon;
        if (i == 0) {
            i = 17301651;
        }
        return i;
    }

    private String b(Bundle bundle, String str) {
        String a2 = a(bundle, str);
        if (!TextUtils.isEmpty(a2)) {
            return a2;
        }
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        String a3 = a(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(a3)) {
            return null;
        }
        Resources resources = this.b.getResources();
        int identifier = resources.getIdentifier(a3, "string", this.b.getPackageName());
        if (identifier == 0) {
            String str2 = "GcmNotification";
            String valueOf3 = String.valueOf(str);
            String valueOf4 = String.valueOf("_loc_key");
            String valueOf5 = String.valueOf(a(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)));
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf5).length() + 49 + String.valueOf(a3).length());
            sb.append(valueOf5);
            sb.append(" resource not found: ");
            sb.append(a3);
            sb.append(" Default value will be used.");
            Log.w(str2, sb.toString());
            return null;
        }
        String valueOf6 = String.valueOf(str);
        String valueOf7 = String.valueOf("_loc_args");
        String a4 = a(bundle, valueOf7.length() != 0 ? valueOf6.concat(valueOf7) : new String(valueOf6));
        if (TextUtils.isEmpty(a4)) {
            return resources.getString(identifier);
        }
        try {
            JSONArray jSONArray = new JSONArray(a4);
            Object[] objArr = new String[jSONArray.length()];
            for (int i = 0; i < objArr.length; i++) {
                objArr[i] = jSONArray.opt(i);
            }
            return resources.getString(identifier, objArr);
        } catch (JSONException unused) {
            String str3 = "GcmNotification";
            String valueOf8 = String.valueOf(str);
            String valueOf9 = String.valueOf("_loc_args");
            String valueOf10 = String.valueOf(a(valueOf9.length() != 0 ? valueOf8.concat(valueOf9) : new String(valueOf8)));
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf10).length() + 41 + String.valueOf(a4).length());
            sb2.append("Malformed ");
            sb2.append(valueOf10);
            sb2.append(": ");
            sb2.append(a4);
            sb2.append("  Default value will be used.");
            Log.w(str3, sb2.toString());
            return null;
        } catch (MissingFormatArgumentException e) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(a3).length() + 58 + String.valueOf(a4).length());
            sb3.append("Missing format argument for ");
            sb3.append(a3);
            sb3.append(": ");
            sb3.append(a4);
            sb3.append(" Default value will be used.");
            Log.w("GcmNotification", sb3.toString(), e);
            return null;
        }
    }

    static void b(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String string = bundle.getString(str);
            if (str.startsWith("gcm.notification.")) {
                str = str.replace("gcm.notification.", "gcm.n.");
            }
            if (str.startsWith("gcm.n.")) {
                if (!"gcm.n.e".equals(str)) {
                    bundle2.putString(str.substring("gcm.n.".length()), string);
                }
                it.remove();
            }
        }
        String string2 = bundle2.getString("sound2");
        if (string2 != null) {
            bundle2.remove("sound2");
            bundle2.putString("sound", string2);
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle(NotificationIntentService.EXTRA_NOTIFICATION, bundle2);
        }
    }

    static boolean b(Context context) {
        boolean z = false;
        if (((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int myPid = Process.myPid();
        List runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            Iterator it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                RunningAppProcessInfo runningAppProcessInfo = (RunningAppProcessInfo) it.next();
                if (runningAppProcessInfo.pid == myPid) {
                    if (runningAppProcessInfo.importance == 100) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    private Uri c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if ("default".equals(str) || this.b.getResources().getIdentifier(str, "raw", this.b.getPackageName()) == 0) {
            return RingtoneManager.getDefaultUri(2);
        }
        String valueOf = String.valueOf("android.resource://");
        String valueOf2 = String.valueOf(this.b.getPackageName());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 5 + String.valueOf(valueOf2).length() + String.valueOf(str).length());
        sb.append(valueOf);
        sb.append(valueOf2);
        sb.append("/raw/");
        sb.append(str);
        return Uri.parse(sb.toString());
    }

    private Notification d(Bundle bundle) {
        CharSequence b2 = b(bundle, "gcm.n.title");
        String b3 = b(bundle, "gcm.n.body");
        int b4 = b(a(bundle, "gcm.n.icon"));
        String a2 = a(bundle, "gcm.n.color");
        Uri c = c(a(bundle, "gcm.n.sound2"));
        PendingIntent e = e(bundle);
        Builder smallIcon = new Builder(this.b).setAutoCancel(true).setSmallIcon(b4);
        if (TextUtils.isEmpty(b2)) {
            b2 = this.b.getApplicationInfo().loadLabel(this.b.getPackageManager());
        }
        smallIcon.setContentTitle(b2);
        if (!TextUtils.isEmpty(b3)) {
            smallIcon.setContentText(b3);
        }
        if (!TextUtils.isEmpty(a2)) {
            smallIcon.setColor(Color.parseColor(a2));
        }
        if (c != null) {
            smallIcon.setSound(c);
        }
        if (e != null) {
            smallIcon.setContentIntent(e);
        }
        return smallIcon.build();
    }

    private PendingIntent e(Bundle bundle) {
        Intent intent;
        String a2 = a(bundle, "gcm.n.click_action");
        if (!TextUtils.isEmpty(a2)) {
            intent = new Intent(a2);
            intent.setPackage(this.b.getPackageName());
            intent.setFlags(268435456);
        } else {
            intent = this.b.getPackageManager().getLaunchIntentForPackage(this.b.getPackageName());
            if (intent == null) {
                Log.w("GcmNotification", "No activity found to launch app");
                return null;
            }
        }
        Bundle bundle2 = new Bundle(bundle);
        GcmListenerService.a(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.b, a(), intent, 1073741824);
    }

    /* access modifiers changed from: 0000 */
    public boolean c(Bundle bundle) {
        try {
            a(a(bundle, "gcm.n.tag"), d(bundle));
            return true;
        } catch (C0019zza e) {
            String str = "GcmNotification";
            String str2 = "Failed to show notification: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return false;
        }
    }
}
