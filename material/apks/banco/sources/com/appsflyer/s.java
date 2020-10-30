package com.appsflyer;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

final class s extends OneLinkHttpTask {
    private b b;
    private String c;

    interface b {
        void a(String str);

        void a(Map<String, String> map);
    }

    s(Uri uri, AppsFlyerLib appsFlyerLib) {
        super(appsFlyerLib);
        if (!TextUtils.isEmpty(uri.getHost()) && !TextUtils.isEmpty(uri.getPath())) {
            String[] split = uri.getPath().split("/");
            if (uri.getHost().contains("onelink.me") && split.length == 3) {
                this.a = split[1];
                this.c = split[2];
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(@NonNull b bVar) {
        this.b = bVar;
    }

    /* access modifiers changed from: 0000 */
    public final boolean c() {
        return !TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(this.c);
    }

    /* access modifiers changed from: 0000 */
    public final void a(HttpsURLConnection httpsURLConnection) {
        httpsURLConnection.setRequestMethod("GET");
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfigHandler.getUrl("https://onelink.%s/shortlink-sdk/v1"));
        sb.append("/");
        sb.append(this.a);
        sb.append("?id=");
        sb.append(this.c);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        try {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                hashMap.put(str2, jSONObject.optString(str2));
            }
            this.b.a((Map<String, String>) hashMap);
        } catch (JSONException e) {
            this.b.a("Can't parse one link data");
            AFLogger.afErrorLog("Error while parsing to json ".concat(String.valueOf(str)), e);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        this.b.a("Can't get one link data");
    }
}
