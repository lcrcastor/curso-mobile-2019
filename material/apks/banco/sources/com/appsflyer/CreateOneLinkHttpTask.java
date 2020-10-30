package com.appsflyer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import ar.com.santander.rio.mbanking.managers.checkversion.CheckVersionManager.RESPONSE_CODES;
import com.appsflyer.share.Constants;
import com.appsflyer.share.LinkGenerator;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class CreateOneLinkHttpTask extends OneLinkHttpTask {
    private ResponseListener b;
    private Map<String, String> c;
    private String d;
    private String e = "";
    private Context f;
    private boolean g = false;

    public interface ResponseListener {
        @WorkerThread
        void onResponse(String str);

        @WorkerThread
        void onResponseError(String str);
    }

    public CreateOneLinkHttpTask(@NonNull String str, @NonNull Map<String, String> map, AppsFlyerLib appsFlyerLib, @NonNull Context context, boolean z) {
        super(appsFlyerLib);
        this.g = z;
        this.f = context;
        if (this.f != null) {
            this.e = context.getPackageName();
        } else {
            AFLogger.afWarnLog("CreateOneLinkHttpTask: context can't be null");
        }
        this.a = str;
        this.d = RESPONSE_CODES.ERROR;
        this.c = map;
    }

    public void setListener(@NonNull ResponseListener responseListener) {
        this.b = responseListener;
    }

    /* access modifiers changed from: 0000 */
    public final void a(HttpsURLConnection httpsURLConnection) {
        if (!this.g) {
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setUseCaches(false);
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject(this.c);
            jSONObject.put("ttl", this.d);
            jSONObject.put("data", jSONObject2);
            httpsURLConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(httpsURLConnection.getOutputStream());
            dataOutputStream.writeBytes(jSONObject.toString());
            dataOutputStream.flush();
            dataOutputStream.close();
        }
    }

    /* access modifiers changed from: 0000 */
    public final String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConfigHandler.getUrl("https://onelink.%s/shortlink-sdk/v1"));
        sb.append("/");
        sb.append(this.a);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public final void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                this.b.onResponse(jSONObject.optString((String) keys.next()));
            }
        } catch (JSONException e2) {
            this.b.onResponseError("Can't parse one link data");
            AFLogger.afErrorLog("Error while parsing to json ".concat(String.valueOf(str)), e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        LinkGenerator addParameters = new LinkGenerator(Constants.USER_INVITE_LINK_TYPE).setBaseURL(this.a, AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.ONELINK_DOMAIN), this.e).addParameter(Constants.URL_SITE_ID, this.e).addParameters(this.c);
        String string = AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.APP_USER_ID);
        if (string != null) {
            addParameters.setReferrerCustomerId(string);
        }
        this.b.onResponse(addParameters.generateLink());
    }
}
