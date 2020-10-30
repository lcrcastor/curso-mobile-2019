package bolts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MeasurementEvent {
    public static final String APP_LINK_NAVIGATE_IN_EVENT_NAME = "al_nav_in";
    public static final String APP_LINK_NAVIGATE_OUT_EVENT_NAME = "al_nav_out";
    public static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    public static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    public static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private Context a;
    private String b;
    private Bundle c;

    static void a(Context context, String str, Intent intent, Map<String, String> map) {
        Bundle bundle = new Bundle();
        if (intent != null) {
            Bundle appLinkData = AppLinks.getAppLinkData(intent);
            if (appLinkData != null) {
                bundle = a(context, str, appLinkData, intent);
            } else {
                Uri data = intent.getData();
                if (data != null) {
                    bundle.putString("intentData", data.toString());
                }
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str2 : extras.keySet()) {
                        bundle.putString(str2, a(extras.get(str2)));
                    }
                }
            }
        }
        if (map != null) {
            for (String str3 : map.keySet()) {
                bundle.putString(str3, (String) map.get(str3));
            }
        }
        new MeasurementEvent(context, str, bundle).a();
    }

    private MeasurementEvent(Context context, String str, Bundle bundle) {
        this.a = context.getApplicationContext();
        this.b = str;
        this.c = bundle;
    }

    private void a() {
        if (this.b == null) {
            Log.d(getClass().getName(), "Event name is required");
        }
        try {
            Class cls = Class.forName("android.support.v4.content.LocalBroadcastManager");
            Method method = cls.getMethod("getInstance", new Class[]{Context.class});
            Method method2 = cls.getMethod("sendBroadcast", new Class[]{Intent.class});
            Object invoke = method.invoke(null, new Object[]{this.a});
            Intent intent = new Intent(MEASUREMENT_EVENT_NOTIFICATION_NAME);
            intent.putExtra(MEASUREMENT_EVENT_NAME_KEY, this.b);
            intent.putExtra(MEASUREMENT_EVENT_ARGS_KEY, this.c);
            method2.invoke(invoke, new Object[]{intent});
        } catch (Exception unused) {
            Log.d(getClass().getName(), "LocalBroadcastManager in android support library is required to raise bolts event.");
        }
    }

    private static Bundle a(Context context, String str, Bundle bundle, Intent intent) {
        Bundle bundle2 = new Bundle();
        ComponentName resolveActivity = intent.resolveActivity(context.getPackageManager());
        if (resolveActivity != null) {
            bundle2.putString("class", resolveActivity.getShortClassName());
        }
        if (APP_LINK_NAVIGATE_OUT_EVENT_NAME.equals(str)) {
            if (resolveActivity != null) {
                bundle2.putString("package", resolveActivity.getPackageName());
            }
            if (intent.getData() != null) {
                bundle2.putString("outputURL", intent.getData().toString());
            }
            if (intent.getScheme() != null) {
                bundle2.putString("outputURLScheme", intent.getScheme());
            }
        } else if (APP_LINK_NAVIGATE_IN_EVENT_NAME.equals(str)) {
            if (intent.getData() != null) {
                bundle2.putString("inputURL", intent.getData().toString());
            }
            if (intent.getScheme() != null) {
                bundle2.putString("inputURLScheme", intent.getScheme());
            }
        }
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof Bundle) {
                Bundle bundle3 = (Bundle) obj;
                for (String str3 : bundle3.keySet()) {
                    String a2 = a(bundle3.get(str3));
                    if (str2.equals("referer_app_link")) {
                        if (str3.equalsIgnoreCase("url")) {
                            bundle2.putString("refererURL", a2);
                        } else if (str3.equalsIgnoreCase(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING)) {
                            bundle2.putString("refererAppName", a2);
                        } else if (str3.equalsIgnoreCase("package")) {
                            bundle2.putString("sourceApplication", a2);
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append("/");
                    sb.append(str3);
                    bundle2.putString(sb.toString(), a2);
                }
            } else {
                String a3 = a(obj);
                if (str2.equals("target_url")) {
                    Uri parse = Uri.parse(a3);
                    bundle2.putString("targetURL", parse.toString());
                    bundle2.putString("targetURLHost", parse.getHost());
                } else {
                    bundle2.putString(str2, a3);
                }
            }
        }
        return bundle2;
    }

    private static String a(Object obj) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof JSONArray) || (obj instanceof JSONObject)) {
            return obj.toString();
        }
        try {
            if (obj instanceof Collection) {
                return new JSONArray((Collection) obj).toString();
            }
            if (obj instanceof Map) {
                return new JSONObject((Map) obj).toString();
            }
            return obj.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
