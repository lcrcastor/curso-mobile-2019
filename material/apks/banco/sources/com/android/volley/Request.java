package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.android.volley.Cache.Entry;
import com.android.volley.Response.ErrorListener;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class Request<T> implements Comparable<Request<T>> {
    private static long p;
    /* access modifiers changed from: private */
    public final MarkerLog a;
    private final int b;
    private final String c;
    private String d;
    private String e;
    private final int f;
    private ErrorListener g;
    private Integer h;
    private RequestQueue i;
    private boolean j;
    private boolean k;
    private boolean l;
    private RetryPolicy m;
    private Entry n;
    private Object o;

    public interface Method {
        public static final int DELETE = 3;
        public static final int DEPRECATED_GET_OR_POST = -1;
        public static final int GET = 0;
        public static final int HEAD = 4;
        public static final int OPTIONS = 5;
        public static final int PATCH = 7;
        public static final int POST = 1;
        public static final int PUT = 2;
        public static final int TRACE = 6;
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public abstract void deliverResponse(T t);

    /* access modifiers changed from: protected */
    public Map<String, String> getParams() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getParamsEncoding() {
        return "UTF-8";
    }

    /* access modifiers changed from: protected */
    public VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    public abstract Response<T> parseNetworkResponse(NetworkResponse networkResponse);

    @Deprecated
    public Request(String str, ErrorListener errorListener) {
        this(-1, str, errorListener);
    }

    public Request(int i2, String str, ErrorListener errorListener) {
        this.a = MarkerLog.a ? new MarkerLog() : null;
        this.j = true;
        this.k = false;
        this.l = false;
        this.n = null;
        this.b = i2;
        this.c = str;
        this.e = a(i2, str);
        this.g = errorListener;
        setRetryPolicy(new DefaultRetryPolicy());
        this.f = b(str);
    }

    public int getMethod() {
        return this.b;
    }

    public Request<?> setTag(Object obj) {
        this.o = obj;
        return this;
    }

    public Object getTag() {
        return this.o;
    }

    public ErrorListener getErrorListener() {
        return this.g;
    }

    public int getTrafficStatsTag() {
        return this.f;
    }

    private static int b(String str) {
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        this.m = retryPolicy;
        return this;
    }

    public void addMarker(String str) {
        if (MarkerLog.a) {
            this.a.a(str, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(final String str) {
        if (this.i != null) {
            this.i.a(this);
            onFinish();
        }
        if (MarkerLog.a) {
            final long id2 = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        Request.this.a.a(str, id2);
                        Request.this.a.a(toString());
                    }
                });
            } else {
                this.a.a(str, id2);
                this.a.a(toString());
            }
        }
    }

    public void onFinish() {
        this.g = null;
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        this.i = requestQueue;
        return this;
    }

    public final Request<?> setSequence(int i2) {
        this.h = Integer.valueOf(i2);
        return this;
    }

    public final int getSequence() {
        if (this.h != null) {
            return this.h.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public String getUrl() {
        return this.d != null ? this.d : this.c;
    }

    public String getOriginUrl() {
        return this.c;
    }

    public String getIdentifier() {
        return this.e;
    }

    public void setRedirectUrl(String str) {
        this.d = str;
    }

    public String getCacheKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(":");
        sb.append(this.c);
        return sb.toString();
    }

    public Request<?> setCacheEntry(Entry entry) {
        this.n = entry;
        return this;
    }

    public Entry getCacheEntry() {
        return this.n;
    }

    public void cancel() {
        this.k = true;
    }

    public boolean isCanceled() {
        return this.k;
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public Map<String, String> getPostParams() {
        return getParams();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public byte[] getPostBody() {
        Map postParams = getPostParams();
        if (postParams == null || postParams.size() <= 0) {
            return null;
        }
        return a(postParams, getPostParamsEncoding());
    }

    public String getBodyContentType() {
        StringBuilder sb = new StringBuilder();
        sb.append("application/x-www-form-urlencoded; charset=");
        sb.append(getParamsEncoding());
        return sb.toString();
    }

    public byte[] getBody() {
        Map params = getParams();
        if (params == null || params.size() <= 0) {
            return null;
        }
        return a(params, getParamsEncoding());
    }

    private byte[] a(Map<String, String> map, String str) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry entry : map.entrySet()) {
                sb.append(URLEncoder.encode((String) entry.getKey(), str));
                sb.append('=');
                sb.append(URLEncoder.encode((String) entry.getValue(), str));
                sb.append('&');
            }
            return sb.toString().getBytes(str);
        } catch (UnsupportedEncodingException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Encoding not supported: ");
            sb2.append(str);
            throw new RuntimeException(sb2.toString(), e2);
        }
    }

    public final Request<?> setShouldCache(boolean z) {
        this.j = z;
        return this;
    }

    public final boolean shouldCache() {
        return this.j;
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public final int getTimeoutMs() {
        return this.m.getCurrentTimeout();
    }

    public RetryPolicy getRetryPolicy() {
        return this.m;
    }

    public void markDelivered() {
        this.l = true;
    }

    public boolean hasHadResponseDelivered() {
        return this.l;
    }

    public void deliverError(VolleyError volleyError) {
        if (this.g != null) {
            this.g.onErrorResponse(volleyError);
        }
    }

    public int compareTo(Request<T> request) {
        Priority priority = getPriority();
        Priority priority2 = request.getPriority();
        return priority == priority2 ? this.h.intValue() - request.h.intValue() : priority2.ordinal() - priority.ordinal();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0x");
        sb.append(Integer.toHexString(getTrafficStatsTag()));
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.k ? "[X] " : "[ ] ");
        sb3.append(getUrl());
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(sb2);
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(getPriority());
        sb3.append(UtilsCuentas.SEPARAOR2);
        sb3.append(this.h);
        return sb3.toString();
    }

    private static String a(int i2, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("Request:");
        sb.append(i2);
        sb.append(":");
        sb.append(str);
        sb.append(":");
        sb.append(System.currentTimeMillis());
        sb.append(":");
        long j2 = p;
        p = j2 + 1;
        sb.append(j2);
        return InternalUtils.a(sb.toString());
    }
}
