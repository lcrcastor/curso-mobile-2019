package com.facebook;

import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import cz.msebera.android.httpclient.HttpStatus;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError {
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private static final Range a = new Range(200, 299);
    private static final Range b = new Range(200, 299);
    private static final Range c = new Range(HttpStatus.SC_BAD_REQUEST, 499);
    private static final Range d = new Range(HttpStatus.SC_INTERNAL_SERVER_ERROR, 599);
    private final int e;
    private final boolean f;
    private final Category g;
    private final int h;
    private final int i;
    private final int j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final boolean o;
    private final JSONObject p;
    private final JSONObject q;
    private final Object r;
    private final HttpURLConnection s;
    private final FacebookException t;

    public enum Category {
        AUTHENTICATION_RETRY,
        AUTHENTICATION_REOPEN_SESSION,
        PERMISSION,
        SERVER,
        THROTTLING,
        OTHER,
        BAD_REQUEST,
        CLIENT
    }

    static class Range {
        private final int a;
        private final int b;

        private Range(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i) {
            return this.a <= i && i <= this.b;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00a2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private FacebookRequestError(int r1, int r2, int r3, java.lang.String r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, boolean r8, org.json.JSONObject r9, org.json.JSONObject r10, java.lang.Object r11, java.net.HttpURLConnection r12, com.facebook.FacebookException r13) {
        /*
            r0 = this;
            r0.<init>()
            r0.h = r1
            r0.i = r2
            r0.j = r3
            r0.k = r4
            r0.l = r5
            r0.q = r9
            r0.p = r10
            r0.r = r11
            r0.s = r12
            r0.m = r6
            r0.n = r7
            r0.o = r8
            r4 = 1
            r6 = 0
            if (r13 == 0) goto L_0x0023
            r0.t = r13
            r5 = 1
            goto L_0x002b
        L_0x0023:
            com.facebook.FacebookServiceException r8 = new com.facebook.FacebookServiceException
            r8.<init>(r0, r5)
            r0.t = r8
            r5 = 0
        L_0x002b:
            r8 = 0
            if (r5 == 0) goto L_0x0033
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.CLIENT
            r2 = 0
            goto L_0x00a3
        L_0x0033:
            if (r2 == r4) goto L_0x0084
            r5 = 2
            if (r2 != r5) goto L_0x0039
            goto L_0x0084
        L_0x0039:
            r5 = 4
            if (r2 == r5) goto L_0x0081
            r5 = 17
            if (r2 != r5) goto L_0x0041
            goto L_0x0081
        L_0x0041:
            r5 = 10
            if (r2 == r5) goto L_0x007c
            com.facebook.FacebookRequestError$Range r5 = a
            boolean r5 = r5.a(r2)
            if (r5 == 0) goto L_0x004e
            goto L_0x007c
        L_0x004e:
            r5 = 102(0x66, float:1.43E-43)
            if (r2 == r5) goto L_0x0056
            r5 = 190(0xbe, float:2.66E-43)
            if (r2 != r5) goto L_0x0086
        L_0x0056:
            r2 = 459(0x1cb, float:6.43E-43)
            if (r3 == r2) goto L_0x0077
            r2 = 464(0x1d0, float:6.5E-43)
            if (r3 != r2) goto L_0x005f
            goto L_0x0077
        L_0x005f:
            com.facebook.FacebookRequestError$Category r8 = com.facebook.FacebookRequestError.Category.AUTHENTICATION_REOPEN_SESSION
            r2 = 458(0x1ca, float:6.42E-43)
            if (r3 == r2) goto L_0x0074
            r2 = 463(0x1cf, float:6.49E-43)
            if (r3 != r2) goto L_0x006a
            goto L_0x0074
        L_0x006a:
            r2 = 460(0x1cc, float:6.45E-43)
            if (r3 != r2) goto L_0x0071
            int r2 = com.facebook.android.R.string.com_facebook_requesterror_password_changed
            goto L_0x0087
        L_0x0071:
            int r2 = com.facebook.android.R.string.com_facebook_requesterror_reconnect
            goto L_0x0087
        L_0x0074:
            int r2 = com.facebook.android.R.string.com_facebook_requesterror_relogin
            goto L_0x0087
        L_0x0077:
            com.facebook.FacebookRequestError$Category r8 = com.facebook.FacebookRequestError.Category.AUTHENTICATION_RETRY
            int r2 = com.facebook.android.R.string.com_facebook_requesterror_web_login
            goto L_0x0087
        L_0x007c:
            com.facebook.FacebookRequestError$Category r8 = com.facebook.FacebookRequestError.Category.PERMISSION
            int r2 = com.facebook.android.R.string.com_facebook_requesterror_permissions
            goto L_0x0087
        L_0x0081:
            com.facebook.FacebookRequestError$Category r8 = com.facebook.FacebookRequestError.Category.THROTTLING
            goto L_0x0086
        L_0x0084:
            com.facebook.FacebookRequestError$Category r8 = com.facebook.FacebookRequestError.Category.SERVER
        L_0x0086:
            r2 = 0
        L_0x0087:
            if (r8 != 0) goto L_0x00a2
            com.facebook.FacebookRequestError$Range r3 = c
            boolean r3 = r3.a(r1)
            if (r3 == 0) goto L_0x0094
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.BAD_REQUEST
            goto L_0x00a3
        L_0x0094:
            com.facebook.FacebookRequestError$Range r3 = d
            boolean r1 = r3.a(r1)
            if (r1 == 0) goto L_0x009f
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.SERVER
            goto L_0x00a3
        L_0x009f:
            com.facebook.FacebookRequestError$Category r1 = com.facebook.FacebookRequestError.Category.OTHER
            goto L_0x00a3
        L_0x00a2:
            r1 = r8
        L_0x00a3:
            if (r7 == 0) goto L_0x00ac
            int r3 = r7.length()
            if (r3 <= 0) goto L_0x00ac
            goto L_0x00ad
        L_0x00ac:
            r4 = 0
        L_0x00ad:
            r0.g = r1
            r0.e = r2
            r0.f = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookRequestError.<init>(int, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, org.json.JSONObject, org.json.JSONObject, java.lang.Object, java.net.HttpURLConnection, com.facebook.FacebookException):void");
    }

    private FacebookRequestError(int i2, int i3, int i4, String str, String str2, String str3, String str4, boolean z, JSONObject jSONObject, JSONObject jSONObject2, Object obj, HttpURLConnection httpURLConnection) {
        this(i2, i3, i4, str, str2, str3, str4, z, jSONObject, jSONObject2, obj, httpURLConnection, null);
    }

    FacebookRequestError(HttpURLConnection httpURLConnection, Exception exc) {
        Exception exc2 = exc;
        this(-1, -1, -1, null, null, null, null, false, null, null, null, httpURLConnection, exc2 instanceof FacebookException ? (FacebookException) exc2 : new FacebookException((Throwable) exc2));
    }

    public FacebookRequestError(int i2, String str, String str2) {
        this(-1, i2, -1, str, str2, null, null, false, null, null, null, null, null);
    }

    public int getUserActionMessageId() {
        return this.e;
    }

    public boolean shouldNotifyUser() {
        return this.f;
    }

    public Category getCategory() {
        return this.g;
    }

    public int getRequestStatusCode() {
        return this.h;
    }

    public int getErrorCode() {
        return this.i;
    }

    public int getSubErrorCode() {
        return this.j;
    }

    public String getErrorType() {
        return this.k;
    }

    public String getErrorMessage() {
        if (this.l != null) {
            return this.l;
        }
        return this.t.getLocalizedMessage();
    }

    public String getErrorUserMessage() {
        return this.n;
    }

    public String getErrorUserTitle() {
        return this.m;
    }

    public boolean getErrorIsTransient() {
        return this.o;
    }

    public JSONObject getRequestResultBody() {
        return this.q;
    }

    public JSONObject getRequestResult() {
        return this.p;
    }

    public Object getBatchRequestResult() {
        return this.r;
    }

    public HttpURLConnection getConnection() {
        return this.s;
    }

    public FacebookException getException() {
        return this.t;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{HttpStatus: ");
        sb.append(this.h);
        sb.append(", errorCode: ");
        sb.append(this.i);
        sb.append(", errorType: ");
        sb.append(this.k);
        sb.append(", errorMessage: ");
        sb.append(getErrorMessage());
        sb.append("}");
        return sb.toString();
    }

    static FacebookRequestError a(JSONObject jSONObject, Object obj, HttpURLConnection httpURLConnection) {
        String str;
        boolean z;
        String str2;
        String str3;
        String str4;
        int i2;
        JSONObject jSONObject2 = jSONObject;
        try {
            if (jSONObject2.has("code")) {
                int i3 = jSONObject2.getInt("code");
                Object stringPropertyAsJSON = Utility.getStringPropertyAsJSON(jSONObject2, "body", Response.NON_JSON_RESPONSE_PROPERTY);
                if (stringPropertyAsJSON != null && (stringPropertyAsJSON instanceof JSONObject)) {
                    JSONObject jSONObject3 = (JSONObject) stringPropertyAsJSON;
                    boolean z2 = true;
                    int i4 = -1;
                    if (jSONObject3.has("error")) {
                        JSONObject jSONObject4 = (JSONObject) Utility.getStringPropertyAsJSON(jSONObject3, "error", null);
                        str4 = jSONObject4.optString("type", null);
                        str3 = jSONObject4.optString("message", null);
                        int optInt = jSONObject4.optInt("code", -1);
                        int optInt2 = jSONObject4.optInt(NativeProtocol.BRIDGE_ARG_ERROR_SUBCODE, -1);
                        String optString = jSONObject4.optString("error_user_msg", null);
                        str = jSONObject4.optString("error_user_title", null);
                        i2 = optInt2;
                        i4 = optInt;
                        str2 = optString;
                        z = jSONObject4.optBoolean("is_transient", false);
                    } else {
                        if (!jSONObject3.has(NativeProtocol.BRIDGE_ARG_ERROR_CODE) && !jSONObject3.has("error_msg")) {
                            if (!jSONObject3.has("error_reason")) {
                                str4 = null;
                                str3 = null;
                                str2 = null;
                                str = null;
                                z2 = false;
                                i2 = -1;
                                z = false;
                            }
                        }
                        String optString2 = jSONObject3.optString("error_reason", null);
                        String optString3 = jSONObject3.optString("error_msg", null);
                        int optInt3 = jSONObject3.optInt(NativeProtocol.BRIDGE_ARG_ERROR_CODE, -1);
                        i2 = jSONObject3.optInt(NativeProtocol.BRIDGE_ARG_ERROR_SUBCODE, -1);
                        i4 = optInt3;
                        str2 = null;
                        str = null;
                        z = false;
                        str3 = optString3;
                        str4 = optString2;
                    }
                    if (z2) {
                        FacebookRequestError facebookRequestError = new FacebookRequestError(i3, i4, i2, str4, str3, str, str2, z, jSONObject3, jSONObject2, obj, httpURLConnection);
                        return facebookRequestError;
                    }
                }
                if (!b.a(i3)) {
                    FacebookRequestError facebookRequestError2 = new FacebookRequestError(i3, -1, -1, null, null, null, null, false, jSONObject2.has("body") ? (JSONObject) Utility.getStringPropertyAsJSON(jSONObject2, "body", Response.NON_JSON_RESPONSE_PROPERTY) : null, jSONObject2, obj, httpURLConnection);
                    return facebookRequestError2;
                }
            }
        } catch (JSONException unused) {
        }
        return null;
    }
}
