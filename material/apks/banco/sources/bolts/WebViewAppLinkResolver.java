package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import bolts.Task.TaskCompletionSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver implements AppLinkResolver {
    /* access modifiers changed from: private */
    public final Context a;

    public WebViewAppLinkResolver(Context context) {
        this.a = context;
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(final Uri uri) {
        final Capture capture = new Capture();
        final Capture capture2 = new Capture();
        return Task.callInBackground(new Callable<Void>() {
            /* renamed from: a */
            public Void call() {
                URL url = new URL(uri.toString());
                URLConnection uRLConnection = null;
                while (url != null) {
                    uRLConnection = url.openConnection();
                    boolean z = uRLConnection instanceof HttpURLConnection;
                    if (z) {
                        ((HttpURLConnection) uRLConnection).setInstanceFollowRedirects(true);
                    }
                    uRLConnection.setRequestProperty("Prefer-Html-Meta-Tags", "al");
                    uRLConnection.connect();
                    if (z) {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnection;
                        if (httpURLConnection.getResponseCode() >= 300 && httpURLConnection.getResponseCode() < 400) {
                            URL url2 = new URL(httpURLConnection.getHeaderField("Location"));
                            httpURLConnection.disconnect();
                            url = url2;
                        }
                    }
                    url = null;
                }
                try {
                    capture.set(WebViewAppLinkResolver.b(uRLConnection));
                    capture2.set(uRLConnection.getContentType());
                    return null;
                } finally {
                    if (uRLConnection instanceof HttpURLConnection) {
                        ((HttpURLConnection) uRLConnection).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() {
            /* renamed from: a */
            public Task<JSONArray> then(Task<Void> task) {
                final TaskCompletionSource create = Task.create();
                WebView webView = new WebView(WebViewAppLinkResolver.this.a);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setNetworkAvailable(false);
                webView.setWebViewClient(new WebViewClient() {
                    private boolean b = false;

                    private void a(WebView webView) {
                        if (!this.b) {
                            this.b = true;
                            webView.loadUrl("javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())");
                        }
                    }

                    public void onPageFinished(WebView webView, String str) {
                        super.onPageFinished(webView, str);
                        a(webView);
                    }

                    public void onLoadResource(WebView webView, String str) {
                        super.onLoadResource(webView, str);
                        a(webView);
                    }
                });
                webView.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void setValue(String str) {
                        try {
                            create.trySetResult(new JSONArray(str));
                        } catch (JSONException e) {
                            create.trySetError(e);
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                webView.loadDataWithBaseURL(uri.toString(), (String) capture.get(), capture2.get() != null ? ((String) capture2.get()).split(";")[0] : null, null, null);
                return create.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() {
            /* renamed from: a */
            public AppLink then(Task<JSONArray> task) {
                return WebViewAppLinkResolver.b(WebViewAppLinkResolver.b((JSONArray) task.getResult()), uri);
            }
        });
    }

    /* access modifiers changed from: private */
    public static Map<String, Object> b(JSONArray jSONArray) {
        Map hashMap = new HashMap();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String[] split = jSONObject.getString("property").split(":");
            if (split[0].equals("al")) {
                Map map = hashMap;
                int i2 = 1;
                while (true) {
                    Map map2 = null;
                    if (i2 >= split.length) {
                        break;
                    }
                    List list = (List) map.get(split[i2]);
                    if (list == null) {
                        list = new ArrayList();
                        map.put(split[i2], list);
                    }
                    if (list.size() > 0) {
                        map2 = (Map) list.get(list.size() - 1);
                    }
                    if (map2 == null || i2 == split.length - 1) {
                        map = new HashMap();
                        list.add(map);
                    } else {
                        map = map2;
                    }
                    i2++;
                }
                if (jSONObject.has("content")) {
                    if (jSONObject.isNull("content")) {
                        map.put(TarjetasConstants.VALUE, null);
                    } else {
                        map.put(TarjetasConstants.VALUE, jSONObject.getString("content"));
                    }
                }
            }
        }
        return hashMap;
    }

    private static List<Map<String, Object>> a(Map<String, Object> map, String str) {
        List<Map<String, Object>> list = (List) map.get(str);
        return list == null ? Collections.emptyList() : list;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x011b, code lost:
        if (java.util.Arrays.asList(new java.lang.String[]{"no", ar.com.santander.rio.mbanking.app.ui.constants.MarcacionViajeConstants.Reintento.Reintento_Falso, "0"}).contains(((java.lang.String) ((java.util.Map) r14.get(0)).get(ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants.VALUE)).toLowerCase()) != false) goto L_0x011f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static bolts.AppLink b(java.util.Map<java.lang.String, java.lang.Object> r14, android.net.Uri r15) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r1 = "android"
            java.lang.Object r1 = r14.get(r1)
            java.util.List r1 = (java.util.List) r1
            if (r1 != 0) goto L_0x0013
            java.util.List r1 = java.util.Collections.emptyList()
        L_0x0013:
            java.util.Iterator r1 = r1.iterator()
        L_0x0017:
            boolean r2 = r1.hasNext()
            r3 = 0
            r4 = 0
            if (r2 == 0) goto L_0x00c2
            java.lang.Object r2 = r1.next()
            java.util.Map r2 = (java.util.Map) r2
            java.lang.String r5 = "url"
            java.util.List r5 = a(r2, r5)
            java.lang.String r6 = "package"
            java.util.List r6 = a(r2, r6)
            java.lang.String r7 = "class"
            java.util.List r7 = a(r2, r7)
            java.lang.String r8 = "app_name"
            java.util.List r2 = a(r2, r8)
            int r8 = r5.size()
            int r9 = r6.size()
            int r10 = r7.size()
            int r11 = r2.size()
            int r10 = java.lang.Math.max(r10, r11)
            int r9 = java.lang.Math.max(r9, r10)
            int r8 = java.lang.Math.max(r8, r9)
        L_0x0059:
            if (r4 >= r8) goto L_0x0017
            int r9 = r5.size()
            if (r9 <= r4) goto L_0x006e
            java.lang.Object r9 = r5.get(r4)
            java.util.Map r9 = (java.util.Map) r9
            java.lang.String r10 = "value"
            java.lang.Object r9 = r9.get(r10)
            goto L_0x006f
        L_0x006e:
            r9 = r3
        L_0x006f:
            java.lang.String r9 = (java.lang.String) r9
            android.net.Uri r9 = a(r9)
            int r10 = r6.size()
            if (r10 <= r4) goto L_0x0088
            java.lang.Object r10 = r6.get(r4)
            java.util.Map r10 = (java.util.Map) r10
            java.lang.String r11 = "value"
            java.lang.Object r10 = r10.get(r11)
            goto L_0x0089
        L_0x0088:
            r10 = r3
        L_0x0089:
            java.lang.String r10 = (java.lang.String) r10
            int r11 = r7.size()
            if (r11 <= r4) goto L_0x009e
            java.lang.Object r11 = r7.get(r4)
            java.util.Map r11 = (java.util.Map) r11
            java.lang.String r12 = "value"
            java.lang.Object r11 = r11.get(r12)
            goto L_0x009f
        L_0x009e:
            r11 = r3
        L_0x009f:
            java.lang.String r11 = (java.lang.String) r11
            int r12 = r2.size()
            if (r12 <= r4) goto L_0x00b4
            java.lang.Object r12 = r2.get(r4)
            java.util.Map r12 = (java.util.Map) r12
            java.lang.String r13 = "value"
            java.lang.Object r12 = r12.get(r13)
            goto L_0x00b5
        L_0x00b4:
            r12 = r3
        L_0x00b5:
            java.lang.String r12 = (java.lang.String) r12
            bolts.AppLink$Target r13 = new bolts.AppLink$Target
            r13.<init>(r10, r11, r9, r12)
            r0.add(r13)
            int r4 = r4 + 1
            goto L_0x0059
        L_0x00c2:
            java.lang.String r1 = "web"
            java.lang.Object r14 = r14.get(r1)
            java.util.List r14 = (java.util.List) r14
            if (r14 == 0) goto L_0x013e
            int r1 = r14.size()
            if (r1 <= 0) goto L_0x013e
            java.lang.Object r14 = r14.get(r4)
            java.util.Map r14 = (java.util.Map) r14
            java.lang.String r1 = "url"
            java.lang.Object r1 = r14.get(r1)
            java.util.List r1 = (java.util.List) r1
            java.lang.String r2 = "should_fallback"
            java.lang.Object r14 = r14.get(r2)
            java.util.List r14 = (java.util.List) r14
            if (r14 == 0) goto L_0x011e
            int r2 = r14.size()
            if (r2 <= 0) goto L_0x011e
            java.lang.Object r14 = r14.get(r4)
            java.util.Map r14 = (java.util.Map) r14
            java.lang.String r2 = "value"
            java.lang.Object r14 = r14.get(r2)
            java.lang.String r14 = (java.lang.String) r14
            r2 = 3
            java.lang.String[] r2 = new java.lang.String[r2]
            java.lang.String r5 = "no"
            r2[r4] = r5
            java.lang.String r5 = "false"
            r6 = 1
            r2[r6] = r5
            r5 = 2
            java.lang.String r6 = "0"
            r2[r5] = r6
            java.util.List r2 = java.util.Arrays.asList(r2)
            java.lang.String r14 = r14.toLowerCase()
            boolean r14 = r2.contains(r14)
            if (r14 == 0) goto L_0x011e
            goto L_0x011f
        L_0x011e:
            r3 = r15
        L_0x011f:
            if (r3 == 0) goto L_0x013c
            if (r1 == 0) goto L_0x013c
            int r14 = r1.size()
            if (r14 <= 0) goto L_0x013c
            java.lang.Object r14 = r1.get(r4)
            java.util.Map r14 = (java.util.Map) r14
            java.lang.String r1 = "value"
            java.lang.Object r14 = r14.get(r1)
            java.lang.String r14 = (java.lang.String) r14
            android.net.Uri r14 = a(r14)
            goto L_0x013f
        L_0x013c:
            r14 = r3
            goto L_0x013f
        L_0x013e:
            r14 = r15
        L_0x013f:
            bolts.AppLink r1 = new bolts.AppLink
            r1.<init>(r15, r0, r14)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.WebViewAppLinkResolver.b(java.util.Map, android.net.Uri):bolts.AppLink");
    }

    private static Uri a(String str) {
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }

    /* access modifiers changed from: private */
    public static String b(URLConnection uRLConnection) {
        InputStream inputStream;
        int i;
        if (uRLConnection instanceof HttpURLConnection) {
            try {
                inputStream = uRLConnection.getInputStream();
            } catch (Exception unused) {
                inputStream = ((HttpURLConnection) uRLConnection).getErrorStream();
            }
        } else {
            inputStream = uRLConnection.getInputStream();
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr);
                i = 0;
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            String contentEncoding = uRLConnection.getContentEncoding();
            if (contentEncoding == null) {
                String[] split = uRLConnection.getContentType().split(";");
                int length = split.length;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String trim = split[i].trim();
                    if (trim.startsWith("charset=")) {
                        contentEncoding = trim.substring("charset=".length());
                        break;
                    }
                    i++;
                }
                if (contentEncoding == null) {
                    contentEncoding = "UTF-8";
                }
            }
            return new String(byteArrayOutputStream.toByteArray(), contentEncoding);
        } finally {
            inputStream.close();
        }
    }
}
