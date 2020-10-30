package com.facebook;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import com.facebook.Request.Callback;
import com.facebook.android.Facebook;
import com.facebook.android.R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.Builder;
import com.facebook.widget.WebDialog.OnCompleteListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class AuthorizationClient implements Serializable {
    private static final long serialVersionUID = 1;
    List<AuthHandler> a;
    AuthHandler b;
    transient Context c;
    transient StartActivityDelegate d;
    transient OnCompletedListener e;
    transient BackgroundProcessingListener f;
    transient boolean g;
    AuthorizationRequest h;
    Map<String, String> i;
    private transient AppEventsLogger j;

    static class AuthDialogBuilder extends Builder {
        private String a;
        private boolean b;

        public AuthDialogBuilder(Context context, String str, Bundle bundle) {
            super(context, str, "oauth", bundle);
        }

        public AuthDialogBuilder a(String str) {
            this.a = str;
            return this;
        }

        public AuthDialogBuilder a(boolean z) {
            this.b = z;
            return this;
        }

        public WebDialog build() {
            Bundle parameters = getParameters();
            parameters.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, Facebook.REDIRECT_URI);
            parameters.putString("client_id", getApplicationId());
            parameters.putString("e2e", this.a);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "token");
            parameters.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, "true");
            if (this.b && !Settings.getPlatformCompatibilityEnabled()) {
                parameters.putString(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
            }
            WebDialog webDialog = new WebDialog(getContext(), "oauth", parameters, getTheme(), getListener());
            return webDialog;
        }
    }

    abstract class AuthHandler implements Serializable {
        private static final long serialVersionUID = 1;
        Map<String, String> a;

        /* access modifiers changed from: 0000 */
        public abstract String a();

        /* access modifiers changed from: 0000 */
        public boolean a(int i, int i2, Intent intent) {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public abstract boolean a(AuthorizationRequest authorizationRequest);

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return false;
        }

        /* access modifiers changed from: 0000 */
        public void d() {
        }

        AuthHandler() {
        }

        /* access modifiers changed from: protected */
        public void a(String str, Object obj) {
            if (this.a == null) {
                this.a = new HashMap();
            }
            this.a.put(str, obj == null ? null : obj.toString());
        }
    }

    static class AuthorizationRequest implements Serializable {
        private static final long serialVersionUID = 1;
        private final transient StartActivityDelegate a;
        private final SessionLoginBehavior b;
        private final int c;
        private boolean d = false;
        private List<String> e;
        private final SessionDefaultAudience f;
        private final String g;
        private final String h;
        private final String i;
        private boolean j = false;

        AuthorizationRequest(SessionLoginBehavior sessionLoginBehavior, int i2, boolean z, List<String> list, SessionDefaultAudience sessionDefaultAudience, String str, String str2, StartActivityDelegate startActivityDelegate, String str3) {
            this.b = sessionLoginBehavior;
            this.c = i2;
            this.d = z;
            this.e = list;
            this.f = sessionDefaultAudience;
            this.g = str;
            this.h = str2;
            this.a = startActivityDelegate;
            this.i = str3;
        }

        /* access modifiers changed from: 0000 */
        public StartActivityDelegate a() {
            return this.a;
        }

        /* access modifiers changed from: 0000 */
        public List<String> b() {
            return this.e;
        }

        /* access modifiers changed from: 0000 */
        public void a(List<String> list) {
            this.e = list;
        }

        /* access modifiers changed from: 0000 */
        public SessionLoginBehavior c() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public int d() {
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public SessionDefaultAudience e() {
            return this.f;
        }

        /* access modifiers changed from: 0000 */
        public String f() {
            return this.g;
        }

        /* access modifiers changed from: 0000 */
        public boolean g() {
            return this.d;
        }

        /* access modifiers changed from: 0000 */
        public String h() {
            return this.h;
        }

        /* access modifiers changed from: 0000 */
        public boolean i() {
            return this.h != null && !this.d;
        }

        /* access modifiers changed from: 0000 */
        public String j() {
            return this.i;
        }

        /* access modifiers changed from: 0000 */
        public boolean k() {
            return this.j;
        }

        /* access modifiers changed from: 0000 */
        public void a(boolean z) {
            this.j = z;
        }
    }

    interface BackgroundProcessingListener {
        void a();

        void b();
    }

    class GetTokenAuthHandler extends AuthHandler {
        private static final long serialVersionUID = 1;
        private transient GetTokenClient d;

        /* access modifiers changed from: 0000 */
        public String a() {
            return "get_token";
        }

        GetTokenAuthHandler() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (this.d != null) {
                this.d.cancel();
                this.d = null;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return this.d == null;
        }

        /* access modifiers changed from: 0000 */
        public boolean a(final AuthorizationRequest authorizationRequest) {
            this.d = new GetTokenClient(AuthorizationClient.this.c, authorizationRequest.f());
            if (!this.d.start()) {
                return false;
            }
            AuthorizationClient.this.k();
            this.d.setCompletedListener(new CompletedListener() {
                public void completed(Bundle bundle) {
                    GetTokenAuthHandler.this.a(authorizationRequest, bundle);
                }
            });
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void a(AuthorizationRequest authorizationRequest, Bundle bundle) {
            this.d = null;
            AuthorizationClient.this.l();
            if (bundle != null) {
                ArrayList stringArrayList = bundle.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
                List<String> b = authorizationRequest.b();
                if (stringArrayList == null || (b != null && !stringArrayList.containsAll(b))) {
                    ArrayList arrayList = new ArrayList();
                    for (String str : b) {
                        if (!stringArrayList.contains(str)) {
                            arrayList.add(str);
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        a("new_permissions", TextUtils.join(",", arrayList));
                    }
                    authorizationRequest.a((List<String>) arrayList);
                } else {
                    AuthorizationClient.this.a(Result.a(AuthorizationClient.this.h, AccessToken.a(bundle, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE)));
                    return;
                }
            }
            AuthorizationClient.this.e();
        }
    }

    abstract class KatanaAuthHandler extends AuthHandler {
        private static final long serialVersionUID = 1;

        KatanaAuthHandler() {
            super();
        }

        /* access modifiers changed from: protected */
        public boolean a(Intent intent, int i) {
            if (intent == null) {
                return false;
            }
            try {
                AuthorizationClient.this.g().a(intent, i);
                return true;
            } catch (ActivityNotFoundException unused) {
                return false;
            }
        }
    }

    class KatanaProxyAuthHandler extends KatanaAuthHandler {
        private static final long serialVersionUID = 1;
        private String e;

        /* access modifiers changed from: 0000 */
        public String a() {
            return "katana_proxy_auth";
        }

        KatanaProxyAuthHandler() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public boolean a(AuthorizationRequest authorizationRequest) {
            this.e = authorizationRequest.f();
            String h = AuthorizationClient.m();
            Intent createProxyAuthIntent = NativeProtocol.createProxyAuthIntent(AuthorizationClient.this.c, authorizationRequest.f(), authorizationRequest.b(), h, authorizationRequest.k(), authorizationRequest.e());
            a("e2e", h);
            return a(createProxyAuthIntent, authorizationRequest.d());
        }

        /* access modifiers changed from: 0000 */
        public boolean a(int i, int i2, Intent intent) {
            Result result;
            if (intent == null) {
                result = Result.a(AuthorizationClient.this.h, "Operation canceled");
            } else if (i2 == 0) {
                result = Result.a(AuthorizationClient.this.h, intent.getStringExtra("error"));
            } else if (i2 != -1) {
                result = Result.a(AuthorizationClient.this.h, "Unexpected resultCode from authorization.", null);
            } else {
                result = a(intent);
            }
            if (result != null) {
                AuthorizationClient.this.a(result);
            } else {
                AuthorizationClient.this.e();
            }
            return true;
        }

        private Result a(Intent intent) {
            Bundle extras = intent.getExtras();
            String string = extras.getString("error");
            if (string == null) {
                string = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_TYPE);
            }
            String string2 = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_CODE);
            String string3 = extras.getString("error_message");
            if (string3 == null) {
                string3 = extras.getString(NativeProtocol.BRIDGE_ARG_ERROR_DESCRIPTION);
            }
            String string4 = extras.getString("e2e");
            if (!Utility.isNullOrEmpty(string4)) {
                AuthorizationClient.this.a(this.e, string4);
            }
            if (string == null && string2 == null && string3 == null) {
                return Result.a(AuthorizationClient.this.h, AccessToken.a(AuthorizationClient.this.h.b(), extras, AccessTokenSource.FACEBOOK_APPLICATION_WEB));
            } else if (ServerProtocol.errorsProxyAuthDisabled.contains(string)) {
                return null;
            } else {
                if (ServerProtocol.errorsUserCanceled.contains(string)) {
                    return Result.a(AuthorizationClient.this.h, (String) null);
                }
                return Result.a(AuthorizationClient.this.h, string, string3, string2);
            }
        }
    }

    interface OnCompletedListener {
        void a(Result result);
    }

    static class Result implements Serializable {
        private static final long serialVersionUID = 1;
        final Code a;
        final AccessToken b;
        final String c;
        final String d;
        final AuthorizationRequest e;
        Map<String, String> f;

        enum Code {
            SUCCESS(Response.SUCCESS_KEY),
            CANCEL(FacebookDialog.COMPLETION_GESTURE_CANCEL),
            ERROR("error");
            
            private final String d;

            private Code(String str) {
                this.d = str;
            }

            /* access modifiers changed from: 0000 */
            public String a() {
                return this.d;
            }
        }

        private Result(AuthorizationRequest authorizationRequest, Code code, AccessToken accessToken, String str, String str2) {
            this.e = authorizationRequest;
            this.b = accessToken;
            this.c = str;
            this.a = code;
            this.d = str2;
        }

        static Result a(AuthorizationRequest authorizationRequest, AccessToken accessToken) {
            Result result = new Result(authorizationRequest, Code.SUCCESS, accessToken, null, null);
            return result;
        }

        static Result a(AuthorizationRequest authorizationRequest, String str) {
            Result result = new Result(authorizationRequest, Code.CANCEL, null, str, null);
            return result;
        }

        static Result a(AuthorizationRequest authorizationRequest, String str, String str2) {
            return a(authorizationRequest, str, str2, null);
        }

        static Result a(AuthorizationRequest authorizationRequest, String str, String str2, String str3) {
            AuthorizationRequest authorizationRequest2 = authorizationRequest;
            Result result = new Result(authorizationRequest2, Code.ERROR, null, TextUtils.join(": ", Utility.asListNoNulls(str, str2)), str3);
            return result;
        }
    }

    interface StartActivityDelegate {
        Activity a();

        void a(Intent intent, int i);
    }

    class WebViewAuthHandler extends AuthHandler {
        private static final long serialVersionUID = 1;
        private transient WebDialog d;
        private String e;
        private String f;

        /* access modifiers changed from: 0000 */
        public String a() {
            return "web_view";
        }

        /* access modifiers changed from: 0000 */
        public boolean b() {
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean c() {
            return true;
        }

        WebViewAuthHandler() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public void d() {
            if (this.d != null) {
                this.d.setOnCompleteListener(null);
                this.d.dismiss();
                this.d = null;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean a(final AuthorizationRequest authorizationRequest) {
            this.e = authorizationRequest.f();
            Bundle bundle = new Bundle();
            if (!Utility.isNullOrEmpty((Collection<T>) authorizationRequest.b())) {
                String join = TextUtils.join(",", authorizationRequest.b());
                bundle.putString("scope", join);
                a("scope", join);
            }
            bundle.putString(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, authorizationRequest.e().getNativeProtocolAudience());
            String h = authorizationRequest.h();
            if (Utility.isNullOrEmpty(h) || !h.equals(e())) {
                Utility.clearFacebookCookies(AuthorizationClient.this.c);
                a("access_token", "0");
            } else {
                bundle.putString("access_token", h);
                a("access_token", "1");
            }
            AnonymousClass1 r1 = new OnCompleteListener() {
                public void onComplete(Bundle bundle, FacebookException facebookException) {
                    WebViewAuthHandler.this.a(authorizationRequest, bundle, facebookException);
                }
            };
            this.f = AuthorizationClient.m();
            a("e2e", this.f);
            this.d = ((Builder) new AuthDialogBuilder(AuthorizationClient.this.g().a(), this.e, bundle).a(this.f).a(authorizationRequest.k()).setOnCompleteListener(r1)).build();
            this.d.show();
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void a(AuthorizationRequest authorizationRequest, Bundle bundle, FacebookException facebookException) {
            Result result;
            String str;
            if (bundle != null) {
                if (bundle.containsKey("e2e")) {
                    this.f = bundle.getString("e2e");
                }
                AccessToken a = AccessToken.a(authorizationRequest.b(), bundle, AccessTokenSource.WEB_VIEW);
                result = Result.a(AuthorizationClient.this.h, a);
                CookieSyncManager.createInstance(AuthorizationClient.this.c).sync();
                a(a.getToken());
            } else if (facebookException instanceof FacebookOperationCanceledException) {
                result = Result.a(AuthorizationClient.this.h, "User canceled log in.");
            } else {
                this.f = null;
                String message = facebookException.getMessage();
                if (facebookException instanceof FacebookServiceException) {
                    FacebookRequestError requestError = ((FacebookServiceException) facebookException).getRequestError();
                    str = String.format("%d", new Object[]{Integer.valueOf(requestError.getErrorCode())});
                    message = requestError.toString();
                } else {
                    str = null;
                }
                result = Result.a(AuthorizationClient.this.h, null, message, str);
            }
            if (!Utility.isNullOrEmpty(this.f)) {
                AuthorizationClient.this.a(this.e, this.f);
            }
            AuthorizationClient.this.a(result);
        }

        private void a(String str) {
            AuthorizationClient.this.g().a().getSharedPreferences("com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).edit().putString("TOKEN", str).apply();
        }

        private String e() {
            return AuthorizationClient.this.g().a().getSharedPreferences("com.facebook.AuthorizationClient.WebViewAuthHandler.TOKEN_STORE_KEY", 0).getString("TOKEN", "");
        }
    }

    AuthorizationClient() {
    }

    /* access modifiers changed from: 0000 */
    public void a(Context context) {
        this.c = context;
        this.d = null;
    }

    /* access modifiers changed from: 0000 */
    public void a(final Activity activity) {
        this.c = activity;
        this.d = new StartActivityDelegate() {
            public void a(Intent intent, int i) {
                activity.startActivityForResult(intent, i);
            }

            public Activity a() {
                return activity;
            }
        };
    }

    /* access modifiers changed from: 0000 */
    public void a(AuthorizationRequest authorizationRequest) {
        if (b()) {
            a();
        } else {
            b(authorizationRequest);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(AuthorizationRequest authorizationRequest) {
        if (authorizationRequest != null) {
            if (this.h != null) {
                throw new FacebookException("Attempted to authorize while a request is pending.");
            } else if (!authorizationRequest.i() || d()) {
                this.h = authorizationRequest;
                this.a = c(authorizationRequest);
                e();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        if (this.h == null || this.b == null) {
            throw new FacebookException("Attempted to continue authorization without a pending request.");
        } else if (this.b.b()) {
            this.b.d();
            f();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean b() {
        return (this.h == null || this.b == null) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (this.b != null) {
            this.b.d();
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean a(int i2, int i3, Intent intent) {
        if (this.h == null || i2 != this.h.d()) {
            return false;
        }
        return this.b.a(i2, i3, intent);
    }

    private List<AuthHandler> c(AuthorizationRequest authorizationRequest) {
        ArrayList arrayList = new ArrayList();
        SessionLoginBehavior c2 = authorizationRequest.c();
        if (c2.a()) {
            if (!authorizationRequest.g()) {
                arrayList.add(new GetTokenAuthHandler());
            }
            arrayList.add(new KatanaProxyAuthHandler());
        }
        if (c2.b()) {
            arrayList.add(new WebViewAuthHandler());
        }
        return arrayList;
    }

    /* access modifiers changed from: 0000 */
    public boolean d() {
        if (this.g) {
            return true;
        }
        if (a("android.permission.INTERNET") != 0) {
            b(Result.a(this.h, this.c.getString(R.string.com_facebook_internet_permission_error_title), this.c.getString(R.string.com_facebook_internet_permission_error_message)));
            return false;
        }
        this.g = true;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void e() {
        if (this.b != null) {
            a(this.b.a(), "skipped", null, null, this.b.a);
        }
        while (this.a != null && !this.a.isEmpty()) {
            this.b = (AuthHandler) this.a.remove(0);
            if (f()) {
                return;
            }
        }
        if (this.h != null) {
            i();
        }
    }

    private void i() {
        b(Result.a(this.h, "Login attempt failed.", null));
    }

    private void a(String str, String str2, boolean z) {
        if (this.i == null) {
            this.i = new HashMap();
        }
        if (this.i.containsKey(str) && z) {
            StringBuilder sb = new StringBuilder();
            sb.append((String) this.i.get(str));
            sb.append(",");
            sb.append(str2);
            str2 = sb.toString();
        }
        this.i.put(str, str2);
    }

    /* access modifiers changed from: 0000 */
    public boolean f() {
        if (!this.b.c() || d()) {
            boolean a2 = this.b.a(this.h);
            if (a2) {
                e(this.b.a());
            } else {
                a("not_tried", this.b.a(), true);
            }
            return a2;
        }
        a("no_internet_permission", "1", false);
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void a(Result result) {
        if (result.b == null || !this.h.i()) {
            b(result);
        } else {
            c(result);
        }
    }

    /* access modifiers changed from: 0000 */
    public void b(Result result) {
        if (this.b != null) {
            a(this.b.a(), result, this.b.a);
        }
        if (this.i != null) {
            result.f = this.i;
        }
        this.a = null;
        this.b = null;
        this.h = null;
        this.i = null;
        e(result);
    }

    /* access modifiers changed from: 0000 */
    public void a(OnCompletedListener onCompletedListener) {
        this.e = onCompletedListener;
    }

    /* access modifiers changed from: 0000 */
    public void a(BackgroundProcessingListener backgroundProcessingListener) {
        this.f = backgroundProcessingListener;
    }

    /* access modifiers changed from: 0000 */
    public StartActivityDelegate g() {
        if (this.d != null) {
            return this.d;
        }
        if (this.h != null) {
            return new StartActivityDelegate() {
                public void a(Intent intent, int i) {
                    AuthorizationClient.this.h.a().a(intent, i);
                }

                public Activity a() {
                    return AuthorizationClient.this.h.a().a();
                }
            };
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public int a(String str) {
        return this.c.checkCallingOrSelfPermission(str);
    }

    /* access modifiers changed from: 0000 */
    public void c(Result result) {
        if (result.b == null) {
            throw new FacebookException("Can't validate without a token");
        }
        RequestBatch d2 = d(result);
        k();
        d2.executeAsync();
    }

    /* access modifiers changed from: 0000 */
    public RequestBatch d(Result result) {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        String token = result.b.getToken();
        AnonymousClass3 r1 = new Callback() {
            public void onCompleted(Response response) {
                try {
                    GraphUser graphUser = (GraphUser) response.getGraphObjectAs(GraphUser.class);
                    if (graphUser != null) {
                        arrayList.add(graphUser.getId());
                    }
                } catch (Exception unused) {
                }
            }
        };
        String h2 = this.h.h();
        Request c2 = c(h2);
        c2.setCallback(r1);
        Request c3 = c(token);
        c3.setCallback(r1);
        Request b2 = b(h2);
        b2.setCallback(new Callback() {
            public void onCompleted(Response response) {
                try {
                    PermissionsPair a2 = Session.a(response);
                    if (a2 != null) {
                        arrayList2.addAll(a2.a());
                        arrayList3.addAll(a2.b());
                    }
                } catch (Exception unused) {
                }
            }
        });
        RequestBatch requestBatch = new RequestBatch(c2, c3, b2);
        requestBatch.a(this.h.f());
        final Result result2 = result;
        AnonymousClass5 r0 = new RequestBatch.Callback() {
            public void onBatchCompleted(RequestBatch requestBatch) {
                Result result;
                try {
                    if (arrayList.size() != 2 || arrayList.get(0) == null || arrayList.get(1) == null || !((String) arrayList.get(0)).equals(arrayList.get(1))) {
                        result = Result.a(AuthorizationClient.this.h, "User logged in as different Facebook user.", null);
                    } else {
                        result = Result.a(AuthorizationClient.this.h, AccessToken.a(result2.b, (List<String>) arrayList2, (List<String>) arrayList3));
                    }
                    AuthorizationClient.this.b(result);
                } catch (Exception e2) {
                    AuthorizationClient.this.b(Result.a(AuthorizationClient.this.h, "Caught exception", e2.getMessage()));
                } catch (Throwable th) {
                    AuthorizationClient.this.l();
                    throw th;
                }
                AuthorizationClient.this.l();
            }
        };
        requestBatch.addCallback(r0);
        return requestBatch;
    }

    /* access modifiers changed from: 0000 */
    public Request b(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("access_token", str);
        Request request = new Request(null, "me/permissions", bundle, HttpMethod.GET, null);
        return request;
    }

    /* access modifiers changed from: 0000 */
    public Request c(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("fields", "id");
        bundle.putString("access_token", str);
        Request request = new Request(null, "me", bundle, HttpMethod.GET, null);
        return request;
    }

    private AppEventsLogger j() {
        if (this.j == null || !this.j.getApplicationId().equals(this.h.f())) {
            this.j = AppEventsLogger.newLogger(this.c, this.h.f());
        }
        return this.j;
    }

    private void e(Result result) {
        if (this.e != null) {
            this.e.a(result);
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        if (this.f != null) {
            this.f.a();
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.f != null) {
            this.f.b();
        }
    }

    private void e(String str) {
        Bundle d2 = d(this.h.j());
        d2.putLong("1_timestamp_ms", System.currentTimeMillis());
        d2.putString("3_method", str);
        j().logSdkEvent("fb_mobile_login_method_start", null, d2);
    }

    private void a(String str, Result result, Map<String, String> map) {
        a(str, result.a.a(), result.c, result.d, map);
    }

    private void a(String str, String str2, String str3, String str4, Map<String, String> map) {
        Bundle bundle;
        if (this.h == null) {
            bundle = d("");
            bundle.putString("2_result", Code.ERROR.a());
            bundle.putString("5_error_message", "Unexpected call to logAuthorizationMethodComplete with null pendingRequest.");
        } else {
            Bundle d2 = d(this.h.j());
            if (str2 != null) {
                d2.putString("2_result", str2);
            }
            if (str3 != null) {
                d2.putString("5_error_message", str3);
            }
            if (str4 != null) {
                d2.putString("4_error_code", str4);
            }
            if (map != null && !map.isEmpty()) {
                d2.putString("6_extras", new JSONObject(map).toString());
            }
            bundle = d2;
        }
        bundle.putString("3_method", str);
        bundle.putLong("1_timestamp_ms", System.currentTimeMillis());
        j().logSdkEvent("fb_mobile_login_method_complete", null, bundle);
    }

    static Bundle d(String str) {
        Bundle bundle = new Bundle();
        bundle.putLong("1_timestamp_ms", System.currentTimeMillis());
        bundle.putString("0_auth_logger_id", str);
        bundle.putString("3_method", "");
        bundle.putString("2_result", "");
        bundle.putString("5_error_message", "");
        bundle.putString("4_error_code", "");
        bundle.putString("6_extras", "");
        return bundle;
    }

    /* access modifiers changed from: private */
    public static String m() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("init", System.currentTimeMillis());
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        AppEventsLogger newLogger = AppEventsLogger.newLogger(this.c, str);
        Bundle bundle = new Bundle();
        bundle.putString(AnalyticsEvents.PARAMETER_WEB_LOGIN_E2E, str2);
        bundle.putLong(AnalyticsEvents.PARAMETER_WEB_LOGIN_SWITCHBACK_TIME, System.currentTimeMillis());
        bundle.putString("app_id", str);
        newLogger.logSdkEvent(AnalyticsEvents.EVENT_WEB_LOGIN_COMPLETE, null, bundle);
    }
}
