package com.facebook;

import android.net.Uri;
import android.os.Bundle;
import bolts.AppLink;
import bolts.AppLink.Target;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import com.facebook.Request.Callback;
import com.facebook.internal.NativeProtocol;
import com.facebook.model.GraphObject;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAppLinkResolver implements AppLinkResolver {
    /* access modifiers changed from: private */
    public final HashMap<Uri, AppLink> a = new HashMap<>();

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(uri);
        return getAppLinkFromUrlsInBackground(arrayList).onSuccess(new Continuation<Map<Uri, AppLink>, AppLink>() {
            /* renamed from: a */
            public AppLink then(Task<Map<Uri, AppLink>> task) {
                return (AppLink) ((Map) task.getResult()).get(uri);
            }
        });
    }

    public Task<Map<Uri, AppLink>> getAppLinkFromUrlsInBackground(List<Uri> list) {
        AppLink appLink;
        final HashMap hashMap = new HashMap();
        final HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (Uri uri : list) {
            synchronized (this.a) {
                appLink = (AppLink) this.a.get(uri);
            }
            if (appLink != null) {
                hashMap.put(uri, appLink);
            } else {
                if (!hashSet.isEmpty()) {
                    sb.append(',');
                }
                sb.append(uri.toString());
                hashSet.add(uri);
            }
        }
        if (hashSet.isEmpty()) {
            return Task.forResult(hashMap);
        }
        final TaskCompletionSource create = Task.create();
        Bundle bundle = new Bundle();
        bundle.putString("ids", sb.toString());
        bundle.putString("fields", String.format("%s.fields(%s,%s)", new Object[]{"app_links", AbstractSpiCall.ANDROID_CLIENT_TYPE, "web"}));
        Request request = new Request(null, "", bundle, null, new Callback() {
            public void onCompleted(Response response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    create.setError(error.getException());
                    return;
                }
                GraphObject graphObject = response.getGraphObject();
                JSONObject innerJSONObject = graphObject != null ? graphObject.getInnerJSONObject() : null;
                if (innerJSONObject == null) {
                    create.setResult(hashMap);
                    return;
                }
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    Uri uri = (Uri) it.next();
                    if (innerJSONObject.has(uri.toString())) {
                        try {
                            JSONObject jSONObject = innerJSONObject.getJSONObject(uri.toString()).getJSONObject("app_links");
                            JSONArray jSONArray = jSONObject.getJSONArray(AbstractSpiCall.ANDROID_CLIENT_TYPE);
                            int length = jSONArray.length();
                            ArrayList arrayList = new ArrayList(length);
                            for (int i = 0; i < length; i++) {
                                Target a2 = FacebookAppLinkResolver.b(jSONArray.getJSONObject(i));
                                if (a2 != null) {
                                    arrayList.add(a2);
                                }
                            }
                            AppLink appLink = new AppLink(uri, arrayList, FacebookAppLinkResolver.b(uri, jSONObject));
                            hashMap.put(uri, appLink);
                            synchronized (FacebookAppLinkResolver.this.a) {
                                FacebookAppLinkResolver.this.a.put(uri, appLink);
                            }
                        } catch (JSONException unused) {
                        }
                    }
                }
                create.setResult(hashMap);
            }
        });
        request.executeAsync();
        return create.getTask();
    }

    /* access modifiers changed from: private */
    public static Target b(JSONObject jSONObject) {
        Uri uri = null;
        String a2 = a(jSONObject, "package", (String) null);
        if (a2 == null) {
            return null;
        }
        String a3 = a(jSONObject, "class", (String) null);
        String a4 = a(jSONObject, NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, (String) null);
        String a5 = a(jSONObject, "url", (String) null);
        if (a5 != null) {
            uri = Uri.parse(a5);
        }
        return new Target(a2, a3, uri, a4);
    }

    /* access modifiers changed from: private */
    public static Uri b(Uri uri, JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("web");
            if (!a(jSONObject2, "should_fallback", true)) {
                return null;
            }
            String a2 = a(jSONObject2, "url", (String) null);
            Uri parse = a2 != null ? Uri.parse(a2) : null;
            if (parse != null) {
                uri = parse;
            }
            return uri;
        } catch (JSONException unused) {
            return uri;
        }
    }

    private static String a(JSONObject jSONObject, String str, String str2) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException unused) {
            return str2;
        }
    }

    private static boolean a(JSONObject jSONObject, String str, boolean z) {
        try {
            return jSONObject.getBoolean(str);
        } catch (JSONException unused) {
            return z;
        }
    }
}
