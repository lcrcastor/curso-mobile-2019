package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink.Target;
import com.facebook.Response;
import com.facebook.internal.NativeProtocol;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppLinkNavigation {
    private static AppLinkResolver a;
    private final AppLink b;
    private final Bundle c;
    private final Bundle d;

    public enum NavigationResult {
        FAILED("failed", false),
        WEB("web", true),
        APP("app", true);
        
        private String a;
        private boolean b;

        public String getCode() {
            return this.a;
        }

        public boolean isSucceeded() {
            return this.b;
        }

        private NavigationResult(String str, boolean z) {
            this.a = str;
            this.b = z;
        }
    }

    public AppLinkNavigation(AppLink appLink, Bundle bundle, Bundle bundle2) {
        if (appLink == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        this.b = appLink;
        this.c = bundle;
        this.d = bundle2;
    }

    public AppLink getAppLink() {
        return this.b;
    }

    public Bundle getAppLinkData() {
        return this.d;
    }

    public Bundle getExtras() {
        return this.c;
    }

    private Bundle a(Context context) {
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        if (context != null) {
            String packageName = context.getPackageName();
            if (packageName != null) {
                bundle2.putString("package", packageName);
            }
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                String string = context.getString(applicationInfo.labelRes);
                if (string != null) {
                    bundle2.putString(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, string);
                }
            }
        }
        bundle.putAll(getAppLinkData());
        bundle.putString("target_url", getAppLink().getSourceUrl().toString());
        bundle.putString("version", "1.0");
        bundle.putString("user_agent", "Bolts Android 1.1.2");
        bundle.putBundle("referer_app_link", bundle2);
        bundle.putBundle("extras", getExtras());
        return bundle;
    }

    private Object a(Object obj) {
        if (obj instanceof Bundle) {
            return a((Bundle) obj);
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        if (obj instanceof List) {
            JSONArray jSONArray = new JSONArray();
            for (Object a2 : (List) obj) {
                jSONArray.put(a(a2));
            }
            return jSONArray;
        }
        int i = 0;
        if (obj instanceof SparseArray) {
            JSONArray jSONArray2 = new JSONArray();
            SparseArray sparseArray = (SparseArray) obj;
            while (i < sparseArray.size()) {
                jSONArray2.put(sparseArray.keyAt(i), a(sparseArray.valueAt(i)));
                i++;
            }
            return jSONArray2;
        } else if (obj instanceof Character) {
            return obj.toString();
        } else {
            if (obj instanceof Boolean) {
                return obj;
            }
            if (obj instanceof Number) {
                if ((obj instanceof Double) || (obj instanceof Float)) {
                    return Double.valueOf(((Number) obj).doubleValue());
                }
                return Long.valueOf(((Number) obj).longValue());
            } else if (obj instanceof boolean[]) {
                JSONArray jSONArray3 = new JSONArray();
                boolean[] zArr = (boolean[]) obj;
                int length = zArr.length;
                while (i < length) {
                    jSONArray3.put(a((Object) Boolean.valueOf(zArr[i])));
                    i++;
                }
                return jSONArray3;
            } else if (obj instanceof char[]) {
                JSONArray jSONArray4 = new JSONArray();
                char[] cArr = (char[]) obj;
                int length2 = cArr.length;
                while (i < length2) {
                    jSONArray4.put(a((Object) Character.valueOf(cArr[i])));
                    i++;
                }
                return jSONArray4;
            } else if (obj instanceof CharSequence[]) {
                JSONArray jSONArray5 = new JSONArray();
                CharSequence[] charSequenceArr = (CharSequence[]) obj;
                int length3 = charSequenceArr.length;
                while (i < length3) {
                    jSONArray5.put(a((Object) charSequenceArr[i]));
                    i++;
                }
                return jSONArray5;
            } else if (obj instanceof double[]) {
                JSONArray jSONArray6 = new JSONArray();
                double[] dArr = (double[]) obj;
                int length4 = dArr.length;
                while (i < length4) {
                    jSONArray6.put(a((Object) Double.valueOf(dArr[i])));
                    i++;
                }
                return jSONArray6;
            } else if (obj instanceof float[]) {
                JSONArray jSONArray7 = new JSONArray();
                float[] fArr = (float[]) obj;
                int length5 = fArr.length;
                while (i < length5) {
                    jSONArray7.put(a((Object) Float.valueOf(fArr[i])));
                    i++;
                }
                return jSONArray7;
            } else if (obj instanceof int[]) {
                JSONArray jSONArray8 = new JSONArray();
                int[] iArr = (int[]) obj;
                int length6 = iArr.length;
                while (i < length6) {
                    jSONArray8.put(a((Object) Integer.valueOf(iArr[i])));
                    i++;
                }
                return jSONArray8;
            } else if (obj instanceof long[]) {
                JSONArray jSONArray9 = new JSONArray();
                long[] jArr = (long[]) obj;
                int length7 = jArr.length;
                while (i < length7) {
                    jSONArray9.put(a((Object) Long.valueOf(jArr[i])));
                    i++;
                }
                return jSONArray9;
            } else if (obj instanceof short[]) {
                JSONArray jSONArray10 = new JSONArray();
                short[] sArr = (short[]) obj;
                int length8 = sArr.length;
                while (i < length8) {
                    jSONArray10.put(a((Object) Short.valueOf(sArr[i])));
                    i++;
                }
                return jSONArray10;
            } else if (!(obj instanceof String[])) {
                return null;
            } else {
                JSONArray jSONArray11 = new JSONArray();
                String[] strArr = (String[]) obj;
                int length9 = strArr.length;
                while (i < length9) {
                    jSONArray11.put(a((Object) strArr[i]));
                    i++;
                }
                return jSONArray11;
            }
        }
    }

    private JSONObject a(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            jSONObject.put(str, a(bundle.get(str)));
        }
        return jSONObject;
    }

    public NavigationResult navigate(Context context) {
        Intent intent;
        Intent intent2;
        PackageManager packageManager = context.getPackageManager();
        Bundle a2 = a(context);
        Iterator it = getAppLink().getTargets().iterator();
        while (true) {
            if (!it.hasNext()) {
                intent = null;
                break;
            }
            Target target = (Target) it.next();
            intent = new Intent("android.intent.action.VIEW");
            if (target.getUrl() != null) {
                intent.setData(target.getUrl());
            } else {
                intent.setData(this.b.getSourceUrl());
            }
            intent.setPackage(target.getPackageName());
            if (target.getClassName() != null) {
                intent.setClassName(target.getPackageName(), target.getClassName());
            }
            intent.putExtra("al_applink_data", a2);
            if (packageManager.resolveActivity(intent, 65536) != null) {
                break;
            }
        }
        NavigationResult navigationResult = NavigationResult.FAILED;
        if (intent != null) {
            navigationResult = NavigationResult.APP;
            intent2 = intent;
        } else {
            Uri webUrl = getAppLink().getWebUrl();
            if (webUrl != null) {
                try {
                    intent2 = new Intent("android.intent.action.VIEW", webUrl.buildUpon().appendQueryParameter("al_applink_data", a(a2).toString()).build());
                    navigationResult = NavigationResult.WEB;
                } catch (JSONException e) {
                    a(context, intent, NavigationResult.FAILED, e);
                    throw new RuntimeException(e);
                }
            } else {
                intent2 = null;
            }
        }
        a(context, intent2, navigationResult, null);
        if (intent2 != null) {
            context.startActivity(intent2);
        }
        return navigationResult;
    }

    private void a(Context context, Intent intent, NavigationResult navigationResult, JSONException jSONException) {
        HashMap hashMap = new HashMap();
        if (jSONException != null) {
            hashMap.put("error", jSONException.getLocalizedMessage());
        }
        hashMap.put(Response.SUCCESS_KEY, navigationResult.isSucceeded() ? "1" : "0");
        hashMap.put("type", navigationResult.getCode());
        MeasurementEvent.a(context, MeasurementEvent.APP_LINK_NAVIGATE_OUT_EVENT_NAME, intent, (Map<String, String>) hashMap);
    }

    public static void setDefaultResolver(AppLinkResolver appLinkResolver) {
        a = appLinkResolver;
    }

    public static AppLinkResolver getDefaultResolver() {
        return a;
    }

    private static AppLinkResolver b(Context context) {
        if (getDefaultResolver() != null) {
            return getDefaultResolver();
        }
        return new WebViewAppLinkResolver(context);
    }

    public static NavigationResult navigate(Context context, AppLink appLink) {
        return new AppLinkNavigation(appLink, null, null).navigate(context);
    }

    public static Task<NavigationResult> navigateInBackground(final Context context, Uri uri, AppLinkResolver appLinkResolver) {
        return appLinkResolver.getAppLinkFromUrlInBackground(uri).onSuccess(new Continuation<AppLink, NavigationResult>() {
            /* renamed from: a */
            public NavigationResult then(Task<AppLink> task) {
                return AppLinkNavigation.navigate(context, (AppLink) task.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(url.toString()), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(str), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, Uri uri) {
        return navigateInBackground(context, uri, b(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url) {
        return navigateInBackground(context, url, b(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str) {
        return navigateInBackground(context, str, b(context));
    }
}
