package com.appsflyer;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;

final class l extends AsyncTask<String, Void, String> {
    Map<String, String> a;
    String b;
    private boolean c = false;
    private String d = "";
    private boolean e = false;
    private WeakReference<Context> f;
    private URL g;
    private boolean h;
    private HttpURLConnection i;
    private boolean j;

    /* access modifiers changed from: protected */
    public final void onCancelled() {
    }

    l(Context context, boolean z) {
        this.f = new WeakReference<>(context);
        this.h = true;
        this.j = true;
        this.c = z;
    }

    /* access modifiers changed from: protected */
    public final void onPreExecute() {
        if (this.b == null) {
            this.b = new JSONObject(this.a).toString();
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final String doInBackground(String... strArr) {
        if (this.c) {
            return null;
        }
        try {
            this.g = new URL(strArr[0]);
            if (this.h) {
                y.a().a(this.g.toString(), this.b);
                int length = this.b.getBytes("UTF-8").length;
                StringBuilder sb = new StringBuilder("call = ");
                sb.append(this.g);
                sb.append("; size = ");
                sb.append(length);
                sb.append(" byte");
                sb.append(length > 1 ? "s" : "");
                sb.append("; body = ");
                sb.append(this.b);
                AnonymousClass5.b(sb.toString());
            }
            this.i = (HttpURLConnection) this.g.openConnection();
            this.i.setReadTimeout(30000);
            this.i.setConnectTimeout(30000);
            this.i.setRequestMethod("POST");
            this.i.setDoInput(true);
            this.i.setDoOutput(true);
            this.i.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = this.i.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(this.b);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            this.i.connect();
            int responseCode = this.i.getResponseCode();
            if (this.j) {
                AppsFlyerLib.getInstance();
                this.d = AppsFlyerLib.a(this.i);
            }
            if (this.h) {
                y.a().a(this.g.toString(), responseCode, this.d);
            }
            if (responseCode == 200) {
                AFLogger.afInfoLog("Status 200 ok");
                Context context = (Context) this.f.get();
                if (this.g.toString().startsWith(ServerConfigHandler.getUrl(AppsFlyerLib.b)) && context != null) {
                    Editor edit = context.getSharedPreferences("appsflyer-data", 0).edit();
                    edit.putBoolean("sentRegisterRequestToAF", true);
                    edit.apply();
                    AFLogger.afDebugLog("Successfully registered for Uninstall Tracking");
                }
            } else {
                this.e = true;
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("Error while calling ");
            sb2.append(this.g.toString());
            AFLogger.afErrorLog(sb2.toString(), th);
            this.e = true;
        }
        return this.d;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void onPostExecute(String str) {
        if (this.e) {
            AFLogger.afInfoLog("Connection error: ".concat(String.valueOf(str)));
        } else {
            AFLogger.afInfoLog("Connection call succeeded: ".concat(String.valueOf(str)));
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.h = false;
    }

    /* access modifiers changed from: 0000 */
    public final HttpURLConnection b() {
        return this.i;
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        this.j = false;
    }
}
