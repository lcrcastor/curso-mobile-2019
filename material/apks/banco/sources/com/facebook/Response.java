package com.facebook;

import android.content.Context;
import android.support.v4.os.EnvironmentCompat;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import com.facebook.model.GraphObjectList;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Response {
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    public static final String SUCCESS_KEY = "success";
    static final /* synthetic */ boolean a = true;
    private static FileLruCache i;
    private final HttpURLConnection b;
    private final GraphObject c;
    private final GraphObjectList<GraphObject> d;
    private final boolean e;
    private final FacebookRequestError f;
    private final String g;
    private final Request h;

    interface PagedResults extends GraphObject {
        PagingInfo a();
    }

    public enum PagingDirection {
        NEXT,
        PREVIOUS
    }

    interface PagingInfo extends GraphObject {
        String a();

        String b();
    }

    Response(Request request, HttpURLConnection httpURLConnection, String str, GraphObject graphObject, boolean z) {
        this(request, httpURLConnection, str, graphObject, null, z, null);
    }

    Response(Request request, HttpURLConnection httpURLConnection, String str, GraphObjectList<GraphObject> graphObjectList, boolean z) {
        this(request, httpURLConnection, str, null, graphObjectList, z, null);
    }

    Response(Request request, HttpURLConnection httpURLConnection, FacebookRequestError facebookRequestError) {
        this(request, httpURLConnection, null, null, null, false, facebookRequestError);
    }

    Response(Request request, HttpURLConnection httpURLConnection, String str, GraphObject graphObject, GraphObjectList<GraphObject> graphObjectList, boolean z, FacebookRequestError facebookRequestError) {
        this.h = request;
        this.b = httpURLConnection;
        this.g = str;
        this.c = graphObject;
        this.d = graphObjectList;
        this.e = z;
        this.f = facebookRequestError;
    }

    public final FacebookRequestError getError() {
        return this.f;
    }

    public final GraphObject getGraphObject() {
        return this.c;
    }

    public final <T extends GraphObject> T getGraphObjectAs(Class<T> cls) {
        if (this.c == null) {
            return null;
        }
        if (cls != null) {
            return this.c.cast(cls);
        }
        throw new NullPointerException("Must pass in a valid interface that extends GraphObject");
    }

    public final GraphObjectList<GraphObject> getGraphObjectList() {
        return this.d;
    }

    public final <T extends GraphObject> GraphObjectList<T> getGraphObjectListAs(Class<T> cls) {
        if (this.d == null) {
            return null;
        }
        return this.d.castToListOf(cls);
    }

    public final HttpURLConnection getConnection() {
        return this.b;
    }

    public Request getRequest() {
        return this.h;
    }

    public String getRawResponse() {
        return this.g;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.Request getRequestForPagedResults(com.facebook.Response.PagingDirection r5) {
        /*
            r4 = this;
            com.facebook.model.GraphObject r0 = r4.c
            r1 = 0
            if (r0 == 0) goto L_0x0023
            com.facebook.model.GraphObject r0 = r4.c
            java.lang.Class<com.facebook.Response$PagedResults> r2 = com.facebook.Response.PagedResults.class
            com.facebook.model.GraphObject r0 = r0.cast(r2)
            com.facebook.Response$PagedResults r0 = (com.facebook.Response.PagedResults) r0
            com.facebook.Response$PagingInfo r0 = r0.a()
            if (r0 == 0) goto L_0x0023
            com.facebook.Response$PagingDirection r2 = com.facebook.Response.PagingDirection.NEXT
            if (r5 != r2) goto L_0x001e
            java.lang.String r5 = r0.a()
            goto L_0x0024
        L_0x001e:
            java.lang.String r5 = r0.b()
            goto L_0x0024
        L_0x0023:
            r5 = r1
        L_0x0024:
            boolean r0 = com.facebook.internal.Utility.isNullOrEmpty(r5)
            if (r0 == 0) goto L_0x002b
            return r1
        L_0x002b:
            if (r5 == 0) goto L_0x003a
            com.facebook.Request r0 = r4.h
            java.lang.String r0 = r0.b()
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x003a
            return r1
        L_0x003a:
            com.facebook.Request r0 = new com.facebook.Request     // Catch:{ MalformedURLException -> 0x004b }
            com.facebook.Request r2 = r4.h     // Catch:{ MalformedURLException -> 0x004b }
            com.facebook.Session r2 = r2.getSession()     // Catch:{ MalformedURLException -> 0x004b }
            java.net.URL r3 = new java.net.URL     // Catch:{ MalformedURLException -> 0x004b }
            r3.<init>(r5)     // Catch:{ MalformedURLException -> 0x004b }
            r0.<init>(r2, r3)     // Catch:{ MalformedURLException -> 0x004b }
            return r0
        L_0x004b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Response.getRequestForPagedResults(com.facebook.Response$PagingDirection):com.facebook.Request");
    }

    public String toString() {
        String str;
        String str2 = "%d";
        try {
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(this.b != null ? this.b.getResponseCode() : 200);
            str = String.format(str2, objArr);
        } catch (IOException unused) {
            str = EnvironmentCompat.MEDIA_UNKNOWN;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{Response: ");
        sb.append(" responseCode: ");
        sb.append(str);
        sb.append(", graphObject: ");
        sb.append(this.c);
        sb.append(", error: ");
        sb.append(this.f);
        sb.append(", isFromCache:");
        sb.append(this.e);
        sb.append("}");
        return sb.toString();
    }

    public final boolean getIsFromCache() {
        return this.e;
    }

    static FileLruCache a() {
        if (i == null) {
            Context a2 = Session.a();
            if (a2 != null) {
                i = new FileLruCache(a2, "ResponseCache", new Limits());
            }
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007d, code lost:
        if (r1 != null) goto L_0x0098;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0069 A[Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006f A[Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:59:0x00a3=Splitter:B:59:0x00a3, B:74:0x00f4=Splitter:B:74:0x00f4, B:69:0x00d9=Splitter:B:69:0x00d9, B:64:0x00be=Splitter:B:64:0x00be} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.util.List<com.facebook.Response> a(java.net.HttpURLConnection r10, com.facebook.RequestBatch r11) {
        /*
            boolean r0 = r11 instanceof com.facebook.internal.CacheableRequestBatch
            r1 = 0
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L_0x005f
            r0 = r11
            com.facebook.internal.CacheableRequestBatch r0 = (com.facebook.internal.CacheableRequestBatch) r0
            com.facebook.internal.FileLruCache r4 = a()
            java.lang.String r5 = r0.getCacheKeyOverride()
            boolean r6 = com.facebook.internal.Utility.isNullOrEmpty(r5)
            if (r6 == 0) goto L_0x0030
            int r6 = r11.size()
            if (r6 != r3) goto L_0x0027
            com.facebook.Request r5 = r11.get(r2)
            java.lang.String r5 = r5.b()
            goto L_0x0030
        L_0x0027:
            com.facebook.LoggingBehavior r6 = com.facebook.LoggingBehavior.REQUESTS
            java.lang.String r7 = "ResponseCache"
            java.lang.String r8 = "Not using cache for cacheable request because no key was specified"
            com.facebook.internal.Logger.log(r6, r7, r8)
        L_0x0030:
            boolean r0 = r0.getForceRoundTrip()
            if (r0 != 0) goto L_0x0061
            if (r4 == 0) goto L_0x0061
            boolean r0 = com.facebook.internal.Utility.isNullOrEmpty(r5)
            if (r0 != 0) goto L_0x0061
            java.io.InputStream r0 = r4.get(r5)     // Catch:{ FacebookException | IOException | JSONException -> 0x005b, all -> 0x0055 }
            if (r0 == 0) goto L_0x0050
            java.util.List r1 = a(r0, r1, r11, r3)     // Catch:{ FacebookException | IOException | JSONException -> 0x004e, all -> 0x004c }
            com.facebook.internal.Utility.closeQuietly(r0)
            return r1
        L_0x004c:
            r10 = move-exception
            goto L_0x0057
        L_0x004e:
            r1 = r0
            goto L_0x005b
        L_0x0050:
            com.facebook.internal.Utility.closeQuietly(r0)
            r1 = r0
            goto L_0x0061
        L_0x0055:
            r10 = move-exception
            r0 = r1
        L_0x0057:
            com.facebook.internal.Utility.closeQuietly(r0)
            throw r10
        L_0x005b:
            com.facebook.internal.Utility.closeQuietly(r1)
            goto L_0x0061
        L_0x005f:
            r4 = r1
            r5 = r4
        L_0x0061:
            int r0 = r10.getResponseCode()     // Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }
            r6 = 400(0x190, float:5.6E-43)
            if (r0 < r6) goto L_0x006f
            java.io.InputStream r0 = r10.getErrorStream()     // Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }
        L_0x006d:
            r1 = r0
            goto L_0x0098
        L_0x006f:
            java.io.InputStream r0 = r10.getInputStream()     // Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }
            if (r4 == 0) goto L_0x006d
            if (r5 == 0) goto L_0x006d
            if (r0 == 0) goto L_0x006d
            java.io.InputStream r1 = r4.interceptAndPut(r5, r0)     // Catch:{ FacebookException -> 0x0093, JSONException -> 0x008e, IOException -> 0x0089, SecurityException -> 0x0084, all -> 0x0080 }
            if (r1 == 0) goto L_0x006d
            goto L_0x0098
        L_0x0080:
            r10 = move-exception
            r1 = r0
            goto L_0x0109
        L_0x0084:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00a3
        L_0x0089:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00be
        L_0x008e:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00d9
        L_0x0093:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00f4
        L_0x0098:
            java.util.List r0 = a(r1, r10, r11, r2)     // Catch:{ FacebookException -> 0x00f3, JSONException -> 0x00d8, IOException -> 0x00bd, SecurityException -> 0x00a2 }
            com.facebook.internal.Utility.closeQuietly(r1)
            return r0
        L_0x00a0:
            r10 = move-exception
            goto L_0x0109
        L_0x00a2:
            r0 = move-exception
        L_0x00a3:
            com.facebook.LoggingBehavior r4 = com.facebook.LoggingBehavior.REQUESTS     // Catch:{ all -> 0x00a0 }
            java.lang.String r5 = "Response"
            java.lang.String r6 = "Response <Error>: %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00a0 }
            r3[r2] = r0     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Logger.log(r4, r5, r6, r3)     // Catch:{ all -> 0x00a0 }
            com.facebook.FacebookException r2 = new com.facebook.FacebookException     // Catch:{ all -> 0x00a0 }
            r2.<init>(r0)     // Catch:{ all -> 0x00a0 }
            java.util.List r10 = a(r11, r10, r2)     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Utility.closeQuietly(r1)
            return r10
        L_0x00bd:
            r0 = move-exception
        L_0x00be:
            com.facebook.LoggingBehavior r4 = com.facebook.LoggingBehavior.REQUESTS     // Catch:{ all -> 0x00a0 }
            java.lang.String r5 = "Response"
            java.lang.String r6 = "Response <Error>: %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00a0 }
            r3[r2] = r0     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Logger.log(r4, r5, r6, r3)     // Catch:{ all -> 0x00a0 }
            com.facebook.FacebookException r2 = new com.facebook.FacebookException     // Catch:{ all -> 0x00a0 }
            r2.<init>(r0)     // Catch:{ all -> 0x00a0 }
            java.util.List r10 = a(r11, r10, r2)     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Utility.closeQuietly(r1)
            return r10
        L_0x00d8:
            r0 = move-exception
        L_0x00d9:
            com.facebook.LoggingBehavior r4 = com.facebook.LoggingBehavior.REQUESTS     // Catch:{ all -> 0x00a0 }
            java.lang.String r5 = "Response"
            java.lang.String r6 = "Response <Error>: %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00a0 }
            r3[r2] = r0     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Logger.log(r4, r5, r6, r3)     // Catch:{ all -> 0x00a0 }
            com.facebook.FacebookException r2 = new com.facebook.FacebookException     // Catch:{ all -> 0x00a0 }
            r2.<init>(r0)     // Catch:{ all -> 0x00a0 }
            java.util.List r10 = a(r11, r10, r2)     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Utility.closeQuietly(r1)
            return r10
        L_0x00f3:
            r0 = move-exception
        L_0x00f4:
            com.facebook.LoggingBehavior r4 = com.facebook.LoggingBehavior.REQUESTS     // Catch:{ all -> 0x00a0 }
            java.lang.String r5 = "Response"
            java.lang.String r6 = "Response <Error>: %s"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x00a0 }
            r3[r2] = r0     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Logger.log(r4, r5, r6, r3)     // Catch:{ all -> 0x00a0 }
            java.util.List r10 = a(r11, r10, r0)     // Catch:{ all -> 0x00a0 }
            com.facebook.internal.Utility.closeQuietly(r1)
            return r10
        L_0x0109:
            com.facebook.internal.Utility.closeQuietly(r1)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Response.a(java.net.HttpURLConnection, com.facebook.RequestBatch):java.util.List");
    }

    static List<Response> a(InputStream inputStream, HttpURLConnection httpURLConnection, RequestBatch requestBatch, boolean z) {
        String readStreamToString = Utility.readStreamToString(inputStream);
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, "Response", "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf(readStreamToString.length()), readStreamToString);
        return a(readStreamToString, httpURLConnection, requestBatch, z);
    }

    static List<Response> a(String str, HttpURLConnection httpURLConnection, RequestBatch requestBatch, boolean z) {
        List<Response> a2 = a(httpURLConnection, (List<Request>) requestBatch, new JSONTokener(str).nextValue(), z);
        Logger.log(LoggingBehavior.REQUESTS, "Response", "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", requestBatch.a(), Integer.valueOf(str.length()), a2);
        return a2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0064  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.List<com.facebook.Response> a(java.net.HttpURLConnection r7, java.util.List<com.facebook.Request> r8, java.lang.Object r9, boolean r10) {
        /*
            boolean r0 = a
            if (r0 != 0) goto L_0x000e
            if (r7 != 0) goto L_0x000e
            if (r10 != 0) goto L_0x000e
            java.lang.AssertionError r7 = new java.lang.AssertionError
            r7.<init>()
            throw r7
        L_0x000e:
            int r0 = r8.size()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
            r2 = 0
            r3 = 1
            if (r0 != r3) goto L_0x005f
            java.lang.Object r3 = r8.get(r2)
            com.facebook.Request r3 = (com.facebook.Request) r3
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            r4.<init>()     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            java.lang.String r5 = "body"
            r4.put(r5, r9)     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            if (r7 == 0) goto L_0x0032
            int r5 = r7.getResponseCode()     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            goto L_0x0034
        L_0x0032:
            r5 = 200(0xc8, float:2.8E-43)
        L_0x0034:
            java.lang.String r6 = "code"
            r4.put(r6, r5)     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            org.json.JSONArray r5 = new org.json.JSONArray     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            r5.<init>()     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            r5.put(r4)     // Catch:{ JSONException -> 0x0051, IOException -> 0x0042 }
            goto L_0x0060
        L_0x0042:
            r4 = move-exception
            com.facebook.Response r5 = new com.facebook.Response
            com.facebook.FacebookRequestError r6 = new com.facebook.FacebookRequestError
            r6.<init>(r7, r4)
            r5.<init>(r3, r7, r6)
            r1.add(r5)
            goto L_0x005f
        L_0x0051:
            r4 = move-exception
            com.facebook.Response r5 = new com.facebook.Response
            com.facebook.FacebookRequestError r6 = new com.facebook.FacebookRequestError
            r6.<init>(r7, r4)
            r5.<init>(r3, r7, r6)
            r1.add(r5)
        L_0x005f:
            r5 = r9
        L_0x0060:
            boolean r3 = r5 instanceof org.json.JSONArray
            if (r3 == 0) goto L_0x00a6
            org.json.JSONArray r5 = (org.json.JSONArray) r5
            int r3 = r5.length()
            if (r3 == r0) goto L_0x006d
            goto L_0x00a6
        L_0x006d:
            int r0 = r5.length()
            if (r2 >= r0) goto L_0x00a5
            java.lang.Object r0 = r8.get(r2)
            com.facebook.Request r0 = (com.facebook.Request) r0
            java.lang.Object r3 = r5.get(r2)     // Catch:{ JSONException -> 0x0094, FacebookException -> 0x0085 }
            com.facebook.Response r3 = a(r0, r7, r3, r10, r9)     // Catch:{ JSONException -> 0x0094, FacebookException -> 0x0085 }
            r1.add(r3)     // Catch:{ JSONException -> 0x0094, FacebookException -> 0x0085 }
            goto L_0x00a2
        L_0x0085:
            r3 = move-exception
            com.facebook.Response r4 = new com.facebook.Response
            com.facebook.FacebookRequestError r6 = new com.facebook.FacebookRequestError
            r6.<init>(r7, r3)
            r4.<init>(r0, r7, r6)
            r1.add(r4)
            goto L_0x00a2
        L_0x0094:
            r3 = move-exception
            com.facebook.Response r4 = new com.facebook.Response
            com.facebook.FacebookRequestError r6 = new com.facebook.FacebookRequestError
            r6.<init>(r7, r3)
            r4.<init>(r0, r7, r6)
            r1.add(r4)
        L_0x00a2:
            int r2 = r2 + 1
            goto L_0x006d
        L_0x00a5:
            return r1
        L_0x00a6:
            com.facebook.FacebookException r7 = new com.facebook.FacebookException
            java.lang.String r8 = "Unexpected number of results"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.Response.a(java.net.HttpURLConnection, java.util.List, java.lang.Object, boolean):java.util.List");
    }

    private static Response a(Request request, HttpURLConnection httpURLConnection, Object obj, boolean z, Object obj2) {
        if (obj instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) obj;
            FacebookRequestError a2 = FacebookRequestError.a(jSONObject, obj2, httpURLConnection);
            if (a2 != null) {
                if (a2.getErrorCode() == 190) {
                    Session session = request.getSession();
                    if (session != null) {
                        session.closeAndClearTokenInformation();
                    }
                }
                return new Response(request, httpURLConnection, a2);
            }
            Object stringPropertyAsJSON = Utility.getStringPropertyAsJSON(jSONObject, "body", NON_JSON_RESPONSE_PROPERTY);
            if (stringPropertyAsJSON instanceof JSONObject) {
                Request request2 = request;
                HttpURLConnection httpURLConnection2 = httpURLConnection;
                Response response = new Response(request2, httpURLConnection2, stringPropertyAsJSON.toString(), Factory.create((JSONObject) stringPropertyAsJSON), z);
                return response;
            } else if (stringPropertyAsJSON instanceof JSONArray) {
                Request request3 = request;
                HttpURLConnection httpURLConnection3 = httpURLConnection;
                Response response2 = new Response(request3, httpURLConnection3, stringPropertyAsJSON.toString(), Factory.createList((JSONArray) stringPropertyAsJSON, GraphObject.class), z);
                return response2;
            } else {
                obj = JSONObject.NULL;
            }
        }
        if (obj == JSONObject.NULL) {
            Response response3 = new Response(request, httpURLConnection, obj.toString(), (GraphObject) null, z);
            return response3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Got unexpected object type in response, class: ");
        sb.append(obj.getClass().getSimpleName());
        throw new FacebookException(sb.toString());
    }

    static List<Response> a(List<Request> list, HttpURLConnection httpURLConnection, FacebookException facebookException) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            arrayList.add(new Response((Request) list.get(i2), httpURLConnection, new FacebookRequestError(httpURLConnection, facebookException)));
        }
        return arrayList;
    }
}
