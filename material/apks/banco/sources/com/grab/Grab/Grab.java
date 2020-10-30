package com.grab.Grab;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.grab.android.vending.billing.util.IabHelper;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Grab extends BroadcastReceiver {
    public static final String GA_GATEWAY_URL_DEV = "https://dev1.grab-apps.com:3001";
    public static final String GA_GATEWAY_URL_LIVE = "https://grabanalytics.grab-apps.com:3001";
    public static final String GA_GATEWAY_URL_LOCAL = "http://10.0.11.217:3001";
    public static final String GA_GATEWAY_URL_STAGING = "https://gateway-staging.grab-apps.com:3001";
    public static final int GA_VALUE_DEFAULT_INTERVAL = 30;
    public static final long GA_VALUE_DEFAULT_STORAGE = 2000000;
    /* access modifiers changed from: private */
    public static String a = "Android_3.0.4";
    private static final String b;
    /* access modifiers changed from: private */
    public static Activity c = null;
    private static Grab d = null;
    /* access modifiers changed from: private */
    public static IInAppBillingService e = null;
    private static JSONObject f = null;
    private static Object g = new Object();
    /* access modifiers changed from: private */
    public static Object h = new Object();
    private static ServiceConnection i = null;
    private static SharedPreferences j = null;
    private static Timer k = new Timer();
    /* access modifiers changed from: private */
    public static Vector<JSONObject> l = new Vector<>();
    /* access modifiers changed from: private */
    public static String m = "";
    /* access modifiers changed from: private */
    public static String n = "";
    /* access modifiers changed from: private */
    public static String o = "";
    /* access modifiers changed from: private */
    public static String p = "";
    /* access modifiers changed from: private */
    public static String q = "https://grabanalytics.grab-apps.com:3001";
    /* access modifiers changed from: private */
    public static String r = "";
    private static boolean s = false;
    private static boolean t = false;
    private static long u = -1;
    private static long v = 0;
    private static long w = 2000000;
    private static long x = 30000;
    /* access modifiers changed from: private */
    public static Thread y;

    class AnalyticTimerTask extends TimerTask {
        private AnalyticTimerTask() {
        }

        public void run() {
            if (Grab.W() && !Grab.o.equalsIgnoreCase("") && !Grab.r.equalsIgnoreCase("") && !Grab.p.equalsIgnoreCase("")) {
                Grab.Y();
                synchronized (Grab.h) {
                    if (!Grab.l.isEmpty()) {
                        Grab.G();
                        Grab.y = new Thread(new Runnable() {
                            public void run() {
                                Exception exc;
                                String c;
                                String d;
                                String str;
                                String f;
                                String j;
                                int size;
                                String str2;
                                String str3;
                                String sb;
                                String str4;
                                boolean z;
                                JSONArray jSONArray;
                                String str5;
                                String str6;
                                String str7;
                                StringBuilder sb2;
                                String sb3;
                                Grab.e("Attempting to send event queue...");
                                Grab.Y();
                                synchronized (Grab.h) {
                                    try {
                                        Grab.e("Sending event queue...");
                                        JSONObject A = Grab.K();
                                        String str8 = "{}";
                                        if (A.length() > 0) {
                                            str8 = A.toString();
                                        }
                                        c = Grab.M();
                                        d = Grab.L();
                                        str = Grab.N() ? "1" : "0";
                                        f = Grab.O();
                                        String g = Grab.R();
                                        String i = Grab.P();
                                        j = Grab.U();
                                        size = Grab.l.size();
                                        String str9 = "[";
                                        int i2 = 0;
                                        while (i2 < size) {
                                            JSONObject jSONObject = (JSONObject) Grab.l.elementAt(i2);
                                            StringBuilder sb4 = new StringBuilder();
                                            sb4.append("Adding to batch ");
                                            sb4.append(i2);
                                            sb4.append(UtilsCuentas.SEPARAOR2);
                                            sb4.append(jSONObject.toString());
                                            Grab.e(sb4.toString());
                                            try {
                                                String string = jSONObject.getString("type");
                                                String string2 = jSONObject.getString("sequence");
                                                str6 = i;
                                                try {
                                                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                                    str7 = "{}";
                                                    if (jSONObject2.length() > 0) {
                                                        str7 = jSONObject2.toString();
                                                    }
                                                    sb2 = new StringBuilder();
                                                    sb2.append(str9);
                                                    str5 = g;
                                                } catch (Exception e) {
                                                    e = e;
                                                    str5 = g;
                                                    Exception exc2 = e;
                                                    StringBuilder sb5 = new StringBuilder();
                                                    sb5.append("Error adding event to batch: ");
                                                    sb5.append(exc2.toString());
                                                    Grab.e(sb5.toString());
                                                    i2++;
                                                    i = str6;
                                                    g = str5;
                                                }
                                                try {
                                                    sb2.append("{\"type\":\"");
                                                    sb2.append(string);
                                                    sb2.append("\",\"data\":");
                                                    sb2.append(str7);
                                                    sb2.append(",\"sequence\":\"");
                                                    sb2.append(string2);
                                                    sb2.append("\",\"tags\":");
                                                    sb2.append(str8);
                                                    String sb6 = sb2.toString();
                                                    if (i2 == size - 1) {
                                                        try {
                                                            StringBuilder sb7 = new StringBuilder();
                                                            sb7.append(sb6);
                                                            sb7.append("}");
                                                            sb3 = sb7.toString();
                                                        } catch (Exception e2) {
                                                            e = e2;
                                                            str9 = sb6;
                                                            Exception exc22 = e;
                                                            StringBuilder sb52 = new StringBuilder();
                                                            sb52.append("Error adding event to batch: ");
                                                            sb52.append(exc22.toString());
                                                            Grab.e(sb52.toString());
                                                            i2++;
                                                            i = str6;
                                                            g = str5;
                                                        }
                                                    } else {
                                                        StringBuilder sb8 = new StringBuilder();
                                                        sb8.append(sb6);
                                                        sb8.append("},");
                                                        sb3 = sb8.toString();
                                                    }
                                                } catch (Exception e3) {
                                                    e = e3;
                                                    Exception exc222 = e;
                                                    StringBuilder sb522 = new StringBuilder();
                                                    sb522.append("Error adding event to batch: ");
                                                    sb522.append(exc222.toString());
                                                    Grab.e(sb522.toString());
                                                    i2++;
                                                    i = str6;
                                                    g = str5;
                                                }
                                            } catch (Exception e4) {
                                                e = e4;
                                                str5 = g;
                                                str6 = i;
                                                Exception exc2222 = e;
                                                StringBuilder sb5222 = new StringBuilder();
                                                sb5222.append("Error adding event to batch: ");
                                                sb5222.append(exc2222.toString());
                                                Grab.e(sb5222.toString());
                                                i2++;
                                                i = str6;
                                                g = str5;
                                            }
                                            i2++;
                                            i = str6;
                                            g = str5;
                                        }
                                        str2 = g;
                                        str3 = i;
                                        StringBuilder sb9 = new StringBuilder();
                                        sb9.append(str9);
                                        sb9.append("]");
                                        sb = sb9.toString();
                                        e = URLEncoder.encode(sb, "UTF-8");
                                    } catch (Exception e5) {
                                        StringBuilder sb10 = new StringBuilder();
                                        sb10.append("Exception url encoding batch ");
                                        sb10.append(exc);
                                        Grab.e(sb10.toString());
                                    } finally {
                                        exc = e5;
                                    }
                                    String str10 = "";
                                    try {
                                        StringBuilder sb11 = new StringBuilder();
                                        sb11.append("advertisingId=");
                                        sb11.append(c);
                                        sb11.append("&androidId=");
                                        sb11.append(d);
                                        sb11.append("&ate=");
                                        sb11.append(str);
                                        sb11.append("&batch=");
                                        sb11.append("&consumer_key=");
                                        sb11.append(Grab.m);
                                        sb11.append("&device=");
                                        sb11.append(j);
                                        sb11.append("&gav=");
                                        sb11.append(Grab.a);
                                        sb11.append("&meid=");
                                        sb11.append(f);
                                        sb11.append("&oudid=");
                                        sb11.append(str2);
                                        sb11.append("&platform=");
                                        sb11.append(AbstractSpiCall.ANDROID_CLIENT_TYPE);
                                        sb11.append("&refCode=");
                                        sb11.append(Grab.p);
                                        sb11.append("&refId=");
                                        sb11.append(str3);
                                        sb11.append("&timestamp=");
                                        sb11.append(Grab.X());
                                        str4 = sb11.toString();
                                    } catch (Exception e6) {
                                        Exception exc3 = e6;
                                        StringBuilder sb12 = new StringBuilder();
                                        sb12.append("Error forming param string: ");
                                        sb12.append(exc3.toString());
                                        Grab.e(sb12.toString());
                                        str4 = str10;
                                    }
                                    int indexOf = str4.indexOf("&consumer_key=");
                                    String substring = str4.substring(0, indexOf);
                                    String substring2 = str4.substring(indexOf);
                                    StringBuilder sb13 = new StringBuilder();
                                    sb13.append(substring);
                                    sb13.append(sb);
                                    sb13.append(substring2);
                                    String sb14 = sb13.toString();
                                    String substring3 = Grab.q.substring(Grab.q.indexOf("://") + "://".length());
                                    StringBuilder sb15 = new StringBuilder();
                                    sb15.append("POST&");
                                    sb15.append(substring3);
                                    sb15.append("&/v1/apps/");
                                    sb15.append(Grab.n);
                                    sb15.append("/events/analytic&");
                                    sb15.append(sb14);
                                    String b = Grab.j(sb15.toString());
                                    StringBuilder sb16 = new StringBuilder();
                                    sb16.append(sb14);
                                    sb16.append("&signature=");
                                    sb16.append(b);
                                    String sb17 = sb16.toString();
                                    StringBuilder sb18 = new StringBuilder();
                                    sb18.append(Grab.q);
                                    sb18.append("/v1/apps/");
                                    sb18.append(Grab.n);
                                    sb18.append("/events/analytic");
                                    String sb19 = sb18.toString();
                                    StringBuilder sb20 = new StringBuilder();
                                    sb20.append("Sending batch with ");
                                    sb20.append(size);
                                    sb20.append(" events...");
                                    Grab.e(sb20.toString());
                                    sb17.trim();
                                    JSONObject a2 = Grab.f(sb19, sb17);
                                    try {
                                        jSONArray = a2.getJSONArray("notifications");
                                        z = false;
                                    } catch (Exception e7) {
                                        Exception exc4 = e7;
                                        StringBuilder sb21 = new StringBuilder();
                                        sb21.append("Error getting notifications: ");
                                        sb21.append(exc4.toString());
                                        Grab.e(sb21.toString());
                                        z = true;
                                        jSONArray = null;
                                    }
                                    if (!z && jSONArray != null && jSONArray.length() == 0) {
                                        StringBuilder sb22 = new StringBuilder();
                                        sb22.append("Successful batch response: ");
                                        sb22.append(a2.toString());
                                        Grab.e(sb22.toString());
                                        Grab.l.clear();
                                        Grab.b("Grab_QUEUE_COUNT", 0);
                                    } else if (!(z || jSONArray == null || jSONArray.length() == 0)) {
                                        StringBuilder sb23 = new StringBuilder();
                                        sb23.append("Batch response with notifications: ");
                                        sb23.append(jSONArray.toString());
                                        Grab.e(sb23.toString());
                                    }
                                    Grab.y = null;
                                }
                            }
                        });
                        Grab.y.start();
                    }
                }
            }
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("Grab_store");
        sb.append(a);
        b = sb.toString();
    }

    public static void init(Activity activity, String str, boolean z, int i2, long j2) {
        if (i2 > 10) {
            x = (long) (i2 * 1000);
            StringBuilder sb = new StringBuilder();
            sb.append("Setting batch interval to ");
            sb.append(x);
            e(sb.toString());
        }
        if (j2 >= 0) {
            w = j2;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Setting offline storage to ");
            sb2.append(w);
            e(sb2.toString());
        }
        init(activity, str, z);
    }

    public static void init(Activity activity, String str, boolean z) {
        String[] split;
        if (d == null) {
            d = new Grab();
            e("Grab instance created");
            int i2 = 0;
            for (String str2 : str.split(":")) {
                switch (i2) {
                    case 0:
                        n = str2;
                        break;
                    case 1:
                        m = str2;
                        break;
                    case 2:
                        o = str2;
                        break;
                }
                i2++;
            }
            c = activity;
            t = z;
            Grab_OpenUDID_manager.sync(c);
            j = c.getSharedPreferences(b, 0);
            e("Storage initialized");
            if (t) {
                E();
            }
            H();
            J();
            return;
        }
        e("WARNING init() being called twice!");
    }

    public static void init(Activity activity, String str, boolean z, int i2, long j2, String str2) {
        q = str2;
        StringBuilder sb = new StringBuilder();
        sb.append("Setting gateway URL to ");
        sb.append(q);
        e(sb.toString());
        init(activity, str, z, i2, j2);
    }

    public static void handleStop() {
        g("app_terminate");
    }

    public static void handleStart() {
        f("app_start");
    }

    public static void handlePause() {
        g("app_suspend");
    }

    public static void handleResume() {
        f("app_resume");
    }

    public static void customEvent(String str, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("grabEventName", str);
            if (jSONObject != null) {
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    Object obj = jSONObject.get(str2);
                    if (str2.equals("grabEventName")) {
                        e("Invalid JSONObject for custom event: The key 'grabEventName' is a reserved key.");
                        return;
                    }
                    if (!(obj instanceof JSONObject)) {
                        if (!(obj instanceof JSONArray)) {
                            jSONObject2.put(str2, obj);
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Invalid JSONObject for custom event: depth > 1 Object/Array: ");
                    sb.append(str2);
                    e(sb.toString());
                    return;
                }
            }
            a("custom", jSONObject2);
        } catch (JSONException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Error parsing JSONObject for custom event: ");
            sb2.append(e2.getMessage());
            e(sb2.toString());
        }
    }

    public static void _customEvent(String str, String str2) {
        try {
            customEvent(str, new JSONObject(str2));
        } catch (JSONException e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error parsing JSONObject for custom event: ");
            sb.append(e2.getMessage());
            e(sb.toString());
        }
    }

    public static void verifyPurchase(String str, JSONObject jSONObject, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calling verifyPurchase for order ");
        sb.append(jSONObject.toString());
        e(sb.toString());
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("evt", "iap");
            jSONObject2.put("price", str2);
            jSONObject2.put("manual_price", str2);
            jSONObject2.put("manual_currency_code", str3);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("signature", str);
            jSONObject3.put("orderData", jSONObject);
            jSONObject2.put("receiptData", jSONObject3);
            a("iap", jSONObject2);
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Exception sending IAP analytic: ");
            sb2.append(e2.toString());
            e(sb2.toString());
        }
    }

    public static void verifyPurchase(String str, JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calling verifyPurchase for order ");
        sb.append(jSONObject.toString());
        e(sb.toString());
        if (t) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                JSONObject h2 = h(jSONObject.getString("productId"));
                jSONObject2.put("evt", "iap");
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(((double) ((float) h2.getInt("price_amount_micros"))) / 1000000.0d);
                jSONObject2.put("price", sb2.toString());
                jSONObject2.put("localized_price", h2.get("localized_price"));
                jSONObject2.put("price_currency_code", h2.get("price_currency_code"));
                jSONObject2.put("price_amount_micros", h2.getInt("price_amount_micros"));
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("signature", str);
                jSONObject3.put("orderData", jSONObject);
                jSONObject2.put("receiptData", jSONObject3);
                a("iap", jSONObject2);
            } catch (Exception e2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Exception sending IAP analytic: ");
                sb3.append(e2.toString());
                e(sb3.toString());
            }
        } else {
            e("Please use the verifyPurchase function with price param for in-app billing v2");
        }
    }

    public static void _verifyPurchase(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append("Calling _verifyPurchase for order ");
        sb.append(str2);
        e(sb.toString());
        try {
            verifyPurchase(str, new JSONObject(str2), str3, str4);
        } catch (JSONException unused) {
            e("Invalid orderData passsed from Unity: JSON Exception");
        }
    }

    private static void C() {
        a("re_engage", new JSONObject());
    }

    public static void signUp(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sign_up_method", str);
        } catch (Exception unused) {
        }
        a("sign_up", jSONObject);
    }

    public static void login(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("login_method", str);
        } catch (Exception unused) {
        }
        a("login", jSONObject);
    }

    public static void levelAchieved(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("level_achieved_level", str);
        } catch (Exception unused) {
        }
        a("level_achieved", jSONObject);
    }

    public static void addedPaymentInfo() {
        a("added_payment_info", new JSONObject());
    }

    public static void addToCart(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("add_to_cart_id", str);
            jSONObject.put("add_to_cart_type", str2);
            jSONObject.put("add_to_cart_currency", str3);
            jSONObject.put("add_to_cart_price", str4);
        } catch (Exception unused) {
        }
        a("add_to_cart", jSONObject);
    }

    public static void addToWishlist(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("add_to_wishlist_id", str);
            jSONObject.put("add_to_wishlist_type", str2);
            jSONObject.put("add_to_wishlist_currency", str3);
            jSONObject.put("add_to_wishlist_price", str4);
        } catch (Exception unused) {
        }
        a("add_to_wishlist", jSONObject);
    }

    public static void tutorialComplete(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tutorial_complete_id", str);
        } catch (Exception unused) {
        }
        a("tutorial_complete", jSONObject);
    }

    public static void checkoutInitiated(String str, String str2, String str3, String str4, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("checkout_initiated_id", str);
            jSONObject.put("checkout_initiated_type", str2);
            jSONObject.put("checkout_initiated_numItems", str3);
            jSONObject.put("checkout_initiated_currency", str4);
            jSONObject.put("checkout_initiated_paymentInfo", z ? "true" : Reintento.Reintento_Falso);
        } catch (Exception unused) {
        }
        a("checkout_initiated", jSONObject);
    }

    public static void rated(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("rated_id", str);
            jSONObject.put("rated_type", str2);
            jSONObject.put("rated_maxRating", str3);
        } catch (Exception unused) {
        }
        a("rated", jSONObject);
    }

    public static void contentView(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("content_view_id", str);
            jSONObject.put("content_view_type", str2);
            jSONObject.put("content_view_currency", str3);
            jSONObject.put("content_view_price", str4);
        } catch (Exception unused) {
        }
        a("content_view", jSONObject);
    }

    public static void achievementUnlocked(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("achievement_unlocked_id", str);
            jSONObject.put("achievement_unlocked_type", str2);
            jSONObject.put("achievement_unlocked_currency", str3);
            jSONObject.put("achievement_unlocked_desc", str4);
        } catch (Exception unused) {
        }
        a("achievement_unlocked", jSONObject);
    }

    public static void spentCredits(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("spent_credits_id", str);
            jSONObject.put("spent_credits_type", str2);
            jSONObject.put("spent_credits_value", str3);
        } catch (Exception unused) {
        }
        a("spent_credits", jSONObject);
    }

    public static void search(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("search_type", str);
            jSONObject.put("search_string", str2);
        } catch (Exception unused) {
        }
        a("search", jSONObject);
    }

    public static void reservation(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("reservation_date", str);
        } catch (Exception unused) {
        }
        a("reservation", jSONObject);
    }

    public static void share(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("share_id", str);
            jSONObject.put("share_type", str2);
        } catch (Exception unused) {
        }
        a("share", jSONObject);
    }

    public static void invite(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("invite_id", str);
            jSONObject.put("invite_type", str2);
        } catch (Exception unused) {
        }
        a("invite", jSONObject);
    }

    public static void _appendGAV(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(";");
        sb.append(str);
        a = sb.toString();
    }

    public static void toggleLog(boolean z) {
        s = z;
    }

    private static void D() {
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        c.bindService(intent, i, 1);
    }

    private static void E() {
        e("Initializing Billing Service");
        i = new ServiceConnection() {
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                int i;
                Grab.e("Billing onServiceConnected");
                Grab.e = Stub.asInterface(iBinder);
                Grab.e("Checking for in-app billing 3 support.");
                try {
                    i = Grab.e.isBillingSupported(3, Grab.c.getPackageName(), IabHelper.ITEM_TYPE_INAPP);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    i = 0;
                }
                if (i != 0) {
                    Grab.e("Billing v3 not supported");
                } else {
                    Grab.e("Billing service connected");
                }
            }

            public void onServiceDisconnected(ComponentName componentName) {
                Grab.e("Billing onServiceDisconnected");
                Grab.e = null;
            }
        };
        D();
    }

    /* access modifiers changed from: private */
    public static void e(String str) {
        if (s) {
            Log.i("Grab", str);
        }
    }

    private static void f(String str) {
        if (u == -1) {
            u = F();
            H();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("evt", str);
            } catch (Exception unused) {
            }
            if (!r.equalsIgnoreCase("")) {
                C();
            }
            a("start", jSONObject);
            k = new Timer();
            Timer timer = k;
            Grab grab = d;
            grab.getClass();
            timer.schedule(new AnalyticTimerTask(), x, x);
        }
    }

    private static long F() {
        return ((System.nanoTime() / 1000) / 1000) / 1000;
    }

    private static void g(String str) {
        if (u != -1) {
            long F = F() - u;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("evt", str);
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(F);
                jSONObject.put("length", sb.toString());
            } catch (Exception unused) {
            }
            a("end", jSONObject);
            k.cancel();
            I();
            u = -1;
        }
    }

    /* access modifiers changed from: private */
    public static void G() {
        int i2;
        long j2;
        long j3;
        Exception e2;
        e("Attempting to save queue to storage...");
        Y();
        synchronized (h) {
            int size = l.size();
            StringBuilder sb = new StringBuilder();
            sb.append("Saving ");
            sb.append(size);
            sb.append(" events...");
            e(sb.toString());
            i2 = 0;
            j2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                try {
                    if (j2 >= w) {
                        break;
                    }
                    String jSONObject = ((JSONObject) l.elementAt(i2)).toString();
                    j3 = j2 + i(jSONObject);
                    try {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Grab_QUEUE_JSON");
                        sb2.append(i2);
                        d(sb2.toString(), jSONObject);
                    } catch (Exception e3) {
                        e2 = e3;
                    }
                    j2 = j3;
                    i2++;
                } catch (Exception e4) {
                    j3 = j2;
                    e2 = e4;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Exeception saving batch: ");
                    sb3.append(e2.toString());
                    e(sb3.toString());
                    j2 = j3;
                    i2++;
                }
            }
            b("Grab_QUEUE_COUNT", i2);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append("Saved ");
        sb4.append(i2);
        sb4.append(" events with total size of ");
        sb4.append(j2);
        sb4.append(" bytes");
        e(sb4.toString());
    }

    private static void H() {
        e("Attempting to load queue from storage...");
        Y();
        synchronized (h) {
            int c2 = c("Grab_QUEUE_COUNT", 0);
            if (c2 != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Loading ");
                sb.append(c2);
                sb.append(" events...");
                e(sb.toString());
                l.clear();
                int i2 = 0;
                while (i2 < c2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Grab_QUEUE_JSON");
                    sb2.append(i2);
                    try {
                        l.add(new JSONObject(e(sb2.toString(), "{}")));
                    } catch (Exception e2) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Exeception loading batch: ");
                        sb3.append(e2.toString());
                        e(sb3.toString());
                    }
                    i2++;
                }
                b("Grab_QUEUE_COUNT", 0);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Loaded ");
                sb4.append(i2);
                sb4.append(" events");
                e(sb4.toString());
            }
        }
    }

    private static void I() {
        e("Attempting to flush the queue...");
        if (!W() || r.equalsIgnoreCase("")) {
            e("Unable to flush");
            G();
            return;
        }
        Grab grab = d;
        grab.getClass();
        new AnalyticTimerTask().run();
    }

    private static void J() {
        if (!o.equalsIgnoreCase("")) {
            r = e("gaPostInstallId", "");
            p = e("grabRefCode", "");
            if (r.equalsIgnoreCase("")) {
                new Thread(new Runnable() {
                    /* JADX WARNING: Removed duplicated region for block: B:24:0x01c9 A[Catch:{ Exception -> 0x0218 }, RETURN] */
                    /* JADX WARNING: Removed duplicated region for block: B:25:0x01ca A[Catch:{ Exception -> 0x0218 }] */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                            r17 = this;
                            java.lang.String r1 = "Attempting install event..."
                            com.grab.Grab.Grab.e(r1)
                            java.lang.String r1 = com.grab.Grab.Grab.M()
                            java.lang.String r2 = com.grab.Grab.Grab.L()
                            boolean r3 = com.grab.Grab.Grab.N()
                            if (r3 == 0) goto L_0x0016
                            java.lang.String r3 = "1"
                            goto L_0x0018
                        L_0x0016:
                            java.lang.String r3 = "0"
                        L_0x0018:
                            java.lang.String r4 = com.grab.Grab.Grab.O()
                            java.lang.String r5 = com.grab.Grab.Grab.R()
                            java.lang.String r6 = com.grab.Grab.Grab.Q()
                            java.lang.String r7 = com.grab.Grab.Grab.P()
                            java.lang.String r8 = android.os.Build.VERSION.RELEASE
                            java.lang.String r9 = com.grab.Grab.Grab.U()
                            java.lang.String r10 = com.grab.Grab.Grab.S()
                            java.lang.String r11 = com.grab.Grab.Grab.T()
                            java.lang.String r12 = com.grab.Grab.Grab.V()
                            java.lang.String r13 = com.grab.Grab.Grab.q
                            java.lang.String r14 = com.grab.Grab.Grab.q
                            java.lang.String r15 = "://"
                            int r14 = r14.indexOf(r15)
                            java.lang.String r15 = "://"
                            int r15 = r15.length()
                            int r14 = r14 + r15
                            java.lang.String r13 = r13.substring(r14)
                            java.lang.String r14 = ""
                            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00fd }
                            r15.<init>()     // Catch:{ Exception -> 0x00fd }
                            r16 = r14
                            java.lang.String r14 = "advertisingId="
                            r15.append(r14)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&androidId="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r2)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&ate="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r3)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&appVersion="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r12)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&consumer_key="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = com.grab.Grab.Grab.m     // Catch:{ Exception -> 0x00fb }
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&device="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "UTF-8"
                            java.lang.String r1 = java.net.URLEncoder.encode(r9, r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&gav="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = com.grab.Grab.Grab.a     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r2 = "UTF-8"
                            java.lang.String r1 = java.net.URLEncoder.encode(r1, r2)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&language="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r11)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&locale="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r10)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&meid="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r4)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&os="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r8)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&oudid="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r5)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&platform="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "android"
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&refCode="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r6)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&refId="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            r15.append(r7)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = "&timestamp="
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r1 = com.grab.Grab.Grab.X()     // Catch:{ Exception -> 0x00fb }
                            r15.append(r1)     // Catch:{ Exception -> 0x00fb }
                            java.lang.String r14 = r15.toString()     // Catch:{ Exception -> 0x00fb }
                            goto L_0x011b
                        L_0x00fb:
                            r0 = move-exception
                            goto L_0x0100
                        L_0x00fd:
                            r0 = move-exception
                            r16 = r14
                        L_0x0100:
                            r1 = r0
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            java.lang.String r3 = "Error forming params "
                            r2.append(r3)
                            java.lang.String r1 = r1.toString()
                            r2.append(r1)
                            java.lang.String r1 = r2.toString()
                            com.grab.Grab.Grab.e(r1)
                            r14 = r16
                        L_0x011b:
                            java.lang.StringBuilder r1 = new java.lang.StringBuilder
                            r1.<init>()
                            java.lang.String r2 = "POST&"
                            r1.append(r2)
                            r1.append(r13)
                            java.lang.String r2 = "&/v1/apps/"
                            r1.append(r2)
                            java.lang.String r2 = com.grab.Grab.Grab.n
                            r1.append(r2)
                            java.lang.String r2 = "/events/postInstall&"
                            r1.append(r2)
                            r1.append(r14)
                            java.lang.String r1 = r1.toString()
                            java.lang.String r1 = com.grab.Grab.Grab.j(r1)
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            r2.append(r14)
                            java.lang.String r3 = "&signature="
                            r2.append(r3)
                            r2.append(r1)
                            java.lang.String r1 = r2.toString()
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            java.lang.String r3 = com.grab.Grab.Grab.q
                            r2.append(r3)
                            java.lang.String r3 = "/v1/apps/"
                            r2.append(r3)
                            java.lang.String r3 = com.grab.Grab.Grab.n
                            r2.append(r3)
                            java.lang.String r3 = "/events/postInstall"
                            r2.append(r3)
                            java.lang.String r2 = r2.toString()
                            org.json.JSONObject r1 = com.grab.Grab.Grab.f(r2, r1)
                            org.json.JSONObject r2 = new org.json.JSONObject
                            r2.<init>()
                            java.lang.String r2 = "data"
                            org.json.JSONObject r2 = r1.getJSONObject(r2)     // Catch:{ Exception -> 0x024e }
                            java.lang.String r1 = "postInstallId"
                            java.lang.String r1 = r2.getString(r1)     // Catch:{ Exception -> 0x0233 }
                            com.grab.Grab.Grab.r = r1     // Catch:{ Exception -> 0x0233 }
                            java.lang.String r1 = "gaPostInstallId"
                            java.lang.String r3 = com.grab.Grab.Grab.r     // Catch:{ Exception -> 0x0233 }
                            com.grab.Grab.Grab.d(r1, r3)     // Catch:{ Exception -> 0x0233 }
                            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0233 }
                            r1.<init>()     // Catch:{ Exception -> 0x0233 }
                            java.lang.String r3 = "Got install ID: "
                            r1.append(r3)     // Catch:{ Exception -> 0x0233 }
                            java.lang.String r3 = com.grab.Grab.Grab.r     // Catch:{ Exception -> 0x0233 }
                            r1.append(r3)     // Catch:{ Exception -> 0x0233 }
                            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0233 }
                            com.grab.Grab.Grab.e(r1)     // Catch:{ Exception -> 0x0233 }
                            java.lang.String r1 = "grabRefCode"
                            java.lang.String r3 = ""
                            java.lang.String r1 = com.grab.Grab.Grab.e(r1, r3)     // Catch:{ Exception -> 0x0218 }
                            com.grab.Grab.Grab.p = r1     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r1 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r3 = ""
                            boolean r1 = r1.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x0218 }
                            if (r1 != 0) goto L_0x01ca
                            return
                        L_0x01ca:
                            java.lang.String r1 = "refCode"
                            java.lang.String r1 = r2.getString(r1)     // Catch:{ Exception -> 0x0218 }
                            com.grab.Grab.Grab.p = r1     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r1 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            if (r1 == 0) goto L_0x01f1
                            java.lang.String r1 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r2 = ""
                            boolean r1 = r1.equals(r2)     // Catch:{ Exception -> 0x0218 }
                            if (r1 != 0) goto L_0x01f1
                            java.lang.String r1 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r2 = "null"
                            boolean r1 = r1.equalsIgnoreCase(r2)     // Catch:{ Exception -> 0x0218 }
                            if (r1 == 0) goto L_0x01f6
                        L_0x01f1:
                            java.lang.String r1 = "empty"
                            com.grab.Grab.Grab.p = r1     // Catch:{ Exception -> 0x0218 }
                        L_0x01f6:
                            java.lang.String r1 = "grabRefCode"
                            java.lang.String r2 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            com.grab.Grab.Grab.d(r1, r2)     // Catch:{ Exception -> 0x0218 }
                            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0218 }
                            r1.<init>()     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r2 = "Got referral code: "
                            r1.append(r2)     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r2 = com.grab.Grab.Grab.p     // Catch:{ Exception -> 0x0218 }
                            r1.append(r2)     // Catch:{ Exception -> 0x0218 }
                            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0218 }
                            com.grab.Grab.Grab.e(r1)     // Catch:{ Exception -> 0x0218 }
                            goto L_0x0232
                        L_0x0218:
                            r0 = move-exception
                            r1 = r0
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            java.lang.String r3 = "exception when getting refCode id: "
                            r2.append(r3)
                            java.lang.String r1 = r1.toString()
                            r2.append(r1)
                            java.lang.String r1 = r2.toString()
                            com.grab.Grab.Grab.e(r1)
                        L_0x0232:
                            return
                        L_0x0233:
                            r0 = move-exception
                            r1 = r0
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            java.lang.String r3 = "exception when getting post install id: "
                            r2.append(r3)
                            java.lang.String r1 = r1.toString()
                            r2.append(r1)
                            java.lang.String r1 = r2.toString()
                            com.grab.Grab.Grab.e(r1)
                            return
                        L_0x024e:
                            r0 = move-exception
                            r2 = r0
                            java.lang.StringBuilder r3 = new java.lang.StringBuilder
                            r3.<init>()
                            java.lang.String r4 = "exception reading data object on post-install "
                            r3.append(r4)
                            java.lang.String r2 = r2.toString()
                            r3.append(r2)
                            java.lang.String r2 = r3.toString()
                            com.grab.Grab.Grab.e(r2)
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder
                            r2.<init>()
                            java.lang.String r3 = "post install returned "
                            r2.append(r3)
                            java.lang.String r1 = r1.toString()
                            r2.append(r1)
                            java.lang.String r1 = r2.toString()
                            com.grab.Grab.Grab.e(r1)
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.grab.Grab.Grab.AnonymousClass2.run():void");
                    }
                }).start();
            }
        }
    }

    private static void a(String str, JSONObject jSONObject) {
        if (!o.equalsIgnoreCase("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Attempting to send event ");
            sb.append(str);
            e(sb.toString());
            JSONObject jSONObject2 = new JSONObject();
            long timeInMillis = Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
            try {
                jSONObject2.put("type", str);
                jSONObject2.put("data", jSONObject);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(timeInMillis);
                sb2.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                long j2 = v;
                v = j2 + 1;
                sb2.append(j2);
                jSONObject2.put("sequence", sb2.toString());
            } catch (Exception e2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Error sending analytic: ");
                sb3.append(e2.toString());
                e(sb3.toString());
            }
            Y();
            synchronized (h) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Adding to queue: ");
                sb4.append(jSONObject2.toString());
                e(sb4.toString());
                l.add(jSONObject2);
            }
        }
    }

    private static JSONObject h(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(IabHelper.GET_SKU_DETAILS_ITEM_LIST, arrayList);
        JSONObject jSONObject = new JSONObject();
        Bundle skuDetails = e.getSkuDetails(3, c.getPackageName(), IabHelper.ITEM_TYPE_INAPP, bundle);
        if (skuDetails.getInt(IabHelper.RESPONSE_CODE) == 0) {
            Iterator it = skuDetails.getStringArrayList(IabHelper.RESPONSE_GET_SKU_DETAILS_LIST).iterator();
            String str2 = null;
            while (it.hasNext()) {
                JSONObject jSONObject2 = new JSONObject((String) it.next());
                if (str2 != null) {
                    throw new Exception("Received multiple details for sku");
                }
                str2 = jSONObject2.getString("price");
                String string = jSONObject2.getString("price_currency_code");
                int i2 = jSONObject2.getInt("price_amount_micros");
                jSONObject.put("localized_price", str2);
                jSONObject.put("price_currency_code", string);
                jSONObject.put("price_amount_micros", i2);
            }
            if (str2 != null) {
                return jSONObject;
            }
            throw new Exception("Received no price for sku");
        }
        throw new Exception("Bad response from getSkuDetails");
    }

    /* access modifiers changed from: private */
    public static JSONObject K() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("refCode", Q());
            jSONObject.put("device", U());
            jSONObject.put("platform", AbstractSpiCall.ANDROID_CLIENT_TYPE);
            jSONObject.put("os", VERSION.RELEASE);
            jSONObject.put("locale", S());
            jSONObject.put("language", T());
            jSONObject.put("appVersion", V());
            return jSONObject;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception getting tags: ");
            sb.append(e2.toString());
            e(sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void d(String str, String str2) {
        if (str2 == null) {
            str2 = "";
        }
        synchronized (g) {
            j.edit().putString(str, str2).commit();
            StringBuilder sb = new StringBuilder();
            sb.append("Saved string with key ");
            sb.append(str);
            e(sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public static String e(String str, String str2) {
        String string;
        synchronized (g) {
            string = j.getString(str, str2);
            StringBuilder sb = new StringBuilder();
            sb.append("Loaded string with key ");
            sb.append(str);
            e(sb.toString());
        }
        return string;
    }

    /* access modifiers changed from: private */
    public static void b(String str, int i2) {
        synchronized (g) {
            j.edit().putInt(str, i2).commit();
            StringBuilder sb = new StringBuilder();
            sb.append("Saved int with key ");
            sb.append(str);
            e(sb.toString());
        }
    }

    private static int c(String str, int i2) {
        int i3;
        synchronized (g) {
            i3 = j.getInt(str, i2);
            StringBuilder sb = new StringBuilder();
            sb.append("Loaded int with key ");
            sb.append(str);
            e(sb.toString());
        }
        return i3;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r4 = r0.getErrorStream();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x00d0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.json.JSONObject f(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.String r5 = r5.trim()     // Catch:{ Exception -> 0x00ed }
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x00ed }
            r0.<init>(r4)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "Attempting connection..."
            e(r1)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "https"
            boolean r1 = r4.contains(r1)     // Catch:{ Exception -> 0x00ed }
            if (r1 == 0) goto L_0x001d
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00ed }
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0     // Catch:{ Exception -> 0x00ed }
            goto L_0x0023
        L_0x001d:
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x00ed }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00ed }
        L_0x0023:
            r1 = 5000(0x1388, float:7.006E-42)
            r0.setConnectTimeout(r1)     // Catch:{ Exception -> 0x00ed }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ed }
            r1.<init>()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r2 = "Connection open: "
            r1.append(r2)     // Catch:{ Exception -> 0x00ed }
            r1.append(r4)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = r1.toString()     // Catch:{ Exception -> 0x00ed }
            e(r4)     // Catch:{ Exception -> 0x00ed }
            r4 = 1
            r0.setDoOutput(r4)     // Catch:{ Exception -> 0x00ed }
            r0.setDoInput(r4)     // Catch:{ Exception -> 0x00ed }
            r4 = 0
            r0.setInstanceFollowRedirects(r4)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "POST"
            r0.setRequestMethod(r1)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/x-www-form-urlencoded"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "accept-language"
            java.lang.String r2 = "en-us"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "accept"
            java.lang.String r2 = "*/*"
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r1 = "Content-Length"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ed }
            r2.<init>()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r3 = ""
            r2.append(r3)     // Catch:{ Exception -> 0x00ed }
            byte[] r3 = r5.getBytes()     // Catch:{ Exception -> 0x00ed }
            int r3 = r3.length     // Catch:{ Exception -> 0x00ed }
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ Exception -> 0x00ed }
            r2.append(r3)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00ed }
            r0.setRequestProperty(r1, r2)     // Catch:{ Exception -> 0x00ed }
            r0.setUseCaches(r4)     // Catch:{ Exception -> 0x00ed }
            java.io.DataOutputStream r4 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x00ed }
            java.io.OutputStream r1 = r0.getOutputStream()     // Catch:{ Exception -> 0x00ed }
            r4.<init>(r1)     // Catch:{ Exception -> 0x00ed }
            r4.writeBytes(r5)     // Catch:{ Exception -> 0x00ed }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ed }
            r1.<init>()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r2 = "Wrote params: "
            r1.append(r2)     // Catch:{ Exception -> 0x00ed }
            r1.append(r5)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x00ed }
            e(r5)     // Catch:{ Exception -> 0x00ed }
            r4.flush()     // Catch:{ Exception -> 0x00ed }
            r4.close()     // Catch:{ Exception -> 0x00ed }
            int r4 = r0.getResponseCode()     // Catch:{ Exception -> 0x00ed }
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 == r5) goto L_0x00cb
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ed }
            r5.<init>()     // Catch:{ Exception -> 0x00ed }
            java.lang.String r0 = "Send error: "
            r5.append(r0)     // Catch:{ Exception -> 0x00ed }
            r5.append(r4)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = r5.toString()     // Catch:{ Exception -> 0x00ed }
            e(r4)     // Catch:{ Exception -> 0x00ed }
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x00ed }
            r4.<init>()     // Catch:{ Exception -> 0x00ed }
            return r4
        L_0x00cb:
            java.io.InputStream r4 = r0.getInputStream()     // Catch:{ Exception -> 0x00d0 }
            goto L_0x00d4
        L_0x00d0:
            java.io.InputStream r4 = r0.getErrorStream()     // Catch:{ Exception -> 0x00ed }
        L_0x00d4:
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00ed }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00ed }
            java.lang.String r2 = "UTF-8"
            r1.<init>(r4, r2)     // Catch:{ Exception -> 0x00ed }
            r5.<init>(r1)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = r5.readLine()     // Catch:{ Exception -> 0x00ed }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x00ed }
            r5.<init>(r4)     // Catch:{ Exception -> 0x00ed }
            r0.disconnect()     // Catch:{ Exception -> 0x00ed }
            return r5
        L_0x00ed:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r0 = "exception while making http request: "
            r5.append(r0)
            java.lang.String r4 = r4.toString()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            e(r4)
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grab.Grab.Grab.f(java.lang.String, java.lang.String):org.json.JSONObject");
    }

    public void onReceive(Context context, Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                extras.containsKey(null);
            }
            if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
                if (j == null) {
                    j = context.getSharedPreferences(b, 0);
                }
                String stringExtra = intent.getStringExtra("referrer");
                if (stringExtra != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("onReceive found a referrerString: ");
                    sb.append(stringExtra);
                    e(sb.toString());
                    d("grabReferrerString", stringExtra);
                    int indexOf = stringExtra.indexOf("refCode=");
                    if (indexOf != -1) {
                        String substring = stringExtra.substring(indexOf + "refCode=".length());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("onReceive found a refCode: ");
                        sb2.append(substring);
                        e(sb2.toString());
                        if (substring != null && !substring.equals("") && !substring.equalsIgnoreCase("null")) {
                            p = substring;
                            d("grabRefCode", substring);
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public static String L() {
        String str = "0";
        try {
            String string = Secure.getString(c.getContentResolver(), "android_id");
            return string == null ? "0" : string;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception getting androidId id: ");
            sb.append(e2.toString());
            e(sb.toString());
            return str;
        }
    }

    /* access modifiers changed from: private */
    public static String M() {
        String str = "unavailable";
        Info info = null;
        try {
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(c) == 0) {
                info = AdvertisingIdClient.getAdvertisingIdInfo(c);
            }
        } catch (IOException e2) {
            e(e2.toString());
        } catch (GooglePlayServicesRepairableException e3) {
            e(e3.toString());
        } catch (GooglePlayServicesNotAvailableException e4) {
            e(e4.toString());
        } catch (Exception e5) {
            e(e5.toString());
        }
        return info != null ? info.getId() : str;
    }

    /* access modifiers changed from: private */
    public static boolean N() {
        Info info = null;
        try {
            if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(c) == 0) {
                info = AdvertisingIdClient.getAdvertisingIdInfo(c);
            }
        } catch (IOException e2) {
            e(e2.toString());
        } catch (GooglePlayServicesRepairableException e3) {
            e(e3.toString());
        } catch (GooglePlayServicesNotAvailableException e4) {
            e(e4.toString());
        } catch (Exception e5) {
            e(e5.toString());
        }
        if (info != null) {
            return true ^ info.isLimitAdTrackingEnabled();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static String O() {
        String str = "";
        try {
            return ((TelephonyManager) c.getSystemService("phone")).getDeviceId();
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception getting meid: ");
            sb.append(e2.toString());
            e(sb.toString());
            return str;
        }
    }

    /* access modifiers changed from: private */
    public static String P() {
        String e2 = e("grabReferrerString", "");
        try {
            return URLEncoder.encode(e2, "UTF-8");
        } catch (Exception e3) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception encoding refId ");
            sb.append(e3);
            e(sb.toString());
            return e2;
        }
    }

    /* access modifiers changed from: private */
    public static String Q() {
        return p.equalsIgnoreCase("empty") ? "" : p;
    }

    /* access modifiers changed from: private */
    public static String R() {
        String str = "";
        try {
            if (Grab_OpenUDID_manager.isInitialized()) {
                return Grab_OpenUDID_manager.getOpenUDID();
            }
            return str;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Exception getting oudid: ");
            sb.append(e2.toString());
            e(sb.toString());
            return str;
        }
    }

    /* access modifiers changed from: private */
    public static String S() {
        return Locale.getDefault().getCountry().toUpperCase();
    }

    /* access modifiers changed from: private */
    public static String T() {
        StringBuilder sb = new StringBuilder();
        sb.append(Locale.getDefault().getLanguage());
        sb.append("-");
        sb.append(Locale.getDefault().getCountry());
        return sb.toString().toLowerCase();
    }

    /* access modifiers changed from: private */
    public static String U() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(UtilsCuentas.SEPARAOR2);
        sb.append(Build.MODEL);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String V() {
        try {
            return c.getPackageManager().getPackageInfo(c.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e(e2.toString());
            return "";
        }
    }

    /* access modifiers changed from: private */
    public static boolean W() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) c.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        e("Network is NOT reachable...");
        return false;
    }

    private static long i(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes != null) {
                return (long) bytes.length;
            }
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return (long) str.length();
        }
    }

    private static String a(int i2) {
        StringBuilder sb;
        String str;
        if (i2 < 10) {
            sb = new StringBuilder();
            str = "0";
        } else {
            sb = new StringBuilder();
            str = "";
        }
        sb.append(str);
        sb.append(i2);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String j(String str) {
        String str2 = "";
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(o.getBytes("UTF-8"), "HmacSHA1");
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            return new String(Base64.encodeToString(instance.doFinal(str.getBytes("UTF-8")), 0));
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception generating signature: ");
            sb.append(e2.toString());
            e(sb.toString());
            return str2;
        }
    }

    /* access modifiers changed from: private */
    public static String X() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        String a2 = a(instance.get(2) + 1);
        String a3 = a(instance.get(5));
        String a4 = a(instance.get(11));
        String a5 = a(instance.get(12));
        String a6 = a(instance.get(13));
        StringBuilder sb = new StringBuilder();
        sb.append(instance.get(1));
        sb.append("");
        sb.append(a2);
        sb.append(a3);
        sb.append(a4);
        sb.append(a5);
        sb.append(a6);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static void Y() {
        if (y != null) {
            State state = y.getState();
            StringBuilder sb = new StringBuilder();
            sb.append("Batch thread state ");
            sb.append(state);
            e(sb.toString());
            if (State.WAITING == state) {
                e("Attempting to destroy network thread...");
                Thread.yield();
                if (y != null) {
                    try {
                        Thread thread = y;
                        Thread.yield();
                        y.interrupt();
                    } catch (Exception e2) {
                        e(e2.toString());
                    }
                    y = null;
                }
                h = new Object();
                return;
            }
            return;
        }
        e("Batch thread null...");
    }
}
